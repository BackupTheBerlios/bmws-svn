////////////////////////////////////////////////////////////////////////
// socketclass.cpp
//
// (C)2000 by Axel Sammet
//
// Klasse für einfache Socketverbindungen
//
////////////////////////////////////////////////////////////////////////
#include "socket.h"
#include <iostream>
#include <stdio.h>
#include <time.h>
#ifdef UNIX
#include <strings.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/select.h>
#include <netinet/in.h>
#include <netdb.h>
#include <unistd.h>
#define CLOSE(arg) ::close(arg)
#endif

#ifdef WIN32
#include <string>
#include <winsock2.h>
#include <io.h>
#define CLOSE(arg) ::closesocket(arg)
#endif

extern int errno; 
#define BUFSIZE 2048


namespace util {
/////////////////////////////////////////////////////////////////
int Socket::s_NrOfInstances = 0;
int Socket::s_Initialized = 0;

/////////////////////////////////////////////////////////////////
void Socket::Init()
{
#ifdef WIN32
	if(!s_Initialized) {
		WSADATA wsaData; 
		WORD wVersionRequested = MAKEWORD( 1, 1 );
		// dll initialisieren für Windows
		if (WSAStartup(wVersionRequested,&wsaData)!=0) 
			throw SocketException(
					"Socket.Init()",
					"could not init Winsock-Library!");
		s_Initialized = 1;
	}
#endif
}

/////////////////////////////////////////////////////////////////
void Socket::Cleanup()
{
#ifdef WIN32
	if(!s_Initialized)  
		throw SocketException(
				"Socket.Cleanup()",
				"Winsock-Library not initialized!"
		);
#endif
}

/////////////////////////////////////////////////////////////////
Socket::Socket() 
{
#ifdef WIN32
	char FAR  opt;
	if(!s_Initialized) 
		throw SocketException(
					"Socket.Socket()",
					"Winsock not initialized, use Socket.Init()!"
		);
#else
	int opt;
#endif
	s_NrOfInstances++;
	// Socket generieren
	if( (m_Socket = socket(AF_INET,SOCK_STREAM,0))==-1) {
		throw SocketException(	
					"Socket.Socket()",
					"couldn't create socket !"
		);
	}
	if( setsockopt(m_Socket,SOL_SOCKET,SO_REUSEADDR,&opt,sizeof(int))<0 ) {
		throw	SocketException(
					"Socket.Socket()",
					"could not set socket options" 
				);
	}
} 

//////////////////////////////////////////////////////////////////
Socket::~Socket() 
{
	s_NrOfInstances--;
	if(m_Socket>0) {
		CLOSE(m_Socket);
		m_Socket = -1;
	}
#ifdef XWIN32
	if (s_NrOfInstances==0 && s_Initialized)
		WSACleanup();
#endif
}

///////////////////////////////////////////////////////////////////
int Socket::GetSocket() 
{
	return m_Socket;
}

////////////////////////////////////////////////////////////////////
void Socket::SetSocket(int soc) 
{
	m_Socket = soc;
}

/////////////////////////////////////////////////////////////////////
void Socket::SetSockAddr(sockaddr_in sa) {
	m_Sockaddr = sa;
}

/////////////////////////////////////////////////////////////////////
void Socket::Close() 
{
	if(CLOSE(m_Socket)<0) {
		String met,mes;
		met = "Socket.Close()";
		switch (errno) {
			case 9 : { mes = "bad socket "+m_Socket; break; }
			default :{ mes = "error code: "+errno; }
		}
		throw SocketException(met,mes);
	}
	m_Socket = -1;
}

/////////////////////////////////////////////////////////////////////
void Socket::Connect(char* address, int port) 
{
	// Namen in Adresse konvertieren
	hostent* 	p_Host;
	p_Host = gethostbyname(address);
	if (p_Host==NULL) 
		throw SocketException(
					String("Socket.Connect('")+address+
					"',"+String(port)+")",
					"could not resolve address"
		);
	// sockaddr-Struktur fuellen
	m_Sockaddr.sin_family = AF_INET;
	m_Sockaddr.sin_port   = htons(port);
#ifndef WIN32
   	m_Sockaddr.sin_len    = sizeof(m_Sockaddr);
#endif
 	memcpy( &m_Sockaddr.sin_addr, p_Host->h_addr, p_Host->h_length);
	// und connect versuchen
	if( (connect(m_Socket, (sockaddr *) &m_Sockaddr, sizeof(sockaddr))) <0 ) {
		String met,mes;
		met = String("Socket.Connect('")+String(address)+String("',")
			+String(port)+String(")");
		switch (errno) {
		case 78: { mes = "connection timed out"; break; }
		case 81: { mes = "host unreachable";break; }
		case 79: { mes = "connection refused"; break; }
		default :{ mes = String("error code is ")+String(errno); };
		}
		throw SocketException(met,mes);
	}
}

/////////////////////////////////////////////////////////////////
void Socket::WriteString(String s) 
{
	WriteLong(s.Size()+1);
	if( send(m_Socket, s, s.Size()+1,0)<s.Size()+1) {
		throw SocketBrokenException("Socket.WriteString()","connection broken") ;
	}
}

//////////////////////////////////////////////////////////////////
String Socket::ReadString(long timeout) 
{
	char buf[BUFSIZE];
	int nr=0;
	String ret("");
	long bufsize;
	long len = ReadLong(timeout);
	do {
		if( !WaitForData(timeout) ) throw SocketTimeoutException(
			String("Socket.ReadString(")+String(timeout)+
			String(")"), "method timed out");
		bufsize = (len-nr>BUFSIZE)?BUFSIZE:(len-nr);
		nr = recv(m_Socket,&buf[0],bufsize,0);
		len -= nr;
		ret += String(&buf[0]);	
		if(nr<0) {
			String met = "Socket.ReadString()";
			throw( SocketBrokenException(met,"connection broken") );
		}
	} while(len>0);
	return ret;
}

///////////////////////////////////////////////////////////////////
String Socket::ReadLine(long timeout) 
{
	char buf[BUFSIZE];
	int nr;
	String ret("");
	do {
		if( !WaitForData(timeout) ) 
			throw SocketTimeoutException(String("Socket.ReadLine(")+String(timeout)
				+String(")"), "method timed out");
		nr = recv(m_Socket, &buf[0], BUFSIZE, 0);
		if(nr<=0) throw SocketBrokenException( "Socket.ReadLine()", "connection broken" );
		buf[nr] = '\0';
		ret += String(&buf[0]);	
	}
	while( buf[nr-1]!='\n' && buf[nr-2]!='\n' ); // line may be \0 terminated
	return ret;
}

///////////////////////////////////////////////////////////////////
void Socket::WriteLong(long nr)
{
	long number = htonl(nr);
	if( send(m_Socket, (char*) &number, sizeof(number),0)<sizeof(number) ) {
		throw SocketBrokenException("Socket.WriteInt()","connection broken") ;
	}
}

///////////////////////////////////////////////////////////////////
long Socket::ReadLong(long timeout)
{
	long ret,temp,nr;
	if( !WaitForData( timeout ) ) 
		throw SocketTimeoutException(String("Socket.ReadLong(")+String(timeout)
				+String(")"), "method timed out");
	nr = recv(m_Socket, (char*) &temp, sizeof(long), 0);
	if(nr<=0) throw SocketBrokenException( "Socket.ReadLong()", "connection broken" );
	ret = ntohl(temp);
	return ret;	
}

/*
///////////////////////////////////////////////////////////////////
void Socket::Write(const ByteArray& ba) {
	if( send(m_Socket, (char*) &ba, ba.GetLength(), 0)<0 ) {
		throw( SocketException("Socket.Write(ByteArray &)","") );
	}		
}
*/

////////////////////////////////////////////////////////////////////
void Socket::Write(char* pt,int len) {
	int ct, count = 0;
	if (len == -1) len = strlen(pt);
	do {
		if ((ct = send(m_Socket, pt, len, 0))<0 ) {
			String met;
			met = "Socket.Write(char*,"+String(len)+")";
			throw( SocketBrokenException(met,"connection broken") );
		}
		count += ct;
	} while(count<len);
}

/////////////////////////////////////////////////////////////////////	
int Socket::Read(char* pt, int len, long timeout) {
	int nr;
	int total = 0;
	do {
		if( !WaitForData( timeout ) ) 
			throw SocketTimeoutException(String("Socket::ReadLine(")+String(timeout)
				+String(")"), "method timed out");
		nr = recv(m_Socket,pt,len,0);
		if(nr<=0) throw SocketException( "Socket.Read()", "connection broken :"
			                              + String(nr) );
		total += nr;
	}
	while( total<len ); // line may be \0 terminated
	
	return total;
}

//////////////////////////////////////////////////////////////////////
String Socket::GetAddressString() 
{
	char* pAddr;
	pAddr = (char*) &m_Sockaddr.sin_addr;
	int a = pAddr[0];
	int b = pAddr[1];
	int c = pAddr[2];
	int d = pAddr[3];
	String ret = String(a)+String(".")+String(b)+String(".")+
				String(c)+String(".")+String(d);
	return ret;
}


///////////////////////////////////////////////////////////////////////
int Socket::WaitForData(long wait)
{
	// Byte-Array, jedes Bit entspricht einem Socketdeskriptor
	fd_set flagset; 
	FD_ZERO(&flagset);
	// setze Bit, das dem Socket entspricht
	FD_SET(m_Socket, &flagset);
	wait *= 1000; // msecs -> microsecs
	struct timeval tval;
	tval.tv_sec  = wait/1000000;					// seconds
	tval.tv_usec = wait%1000000;					// microseconds
	struct timeval *ptVal = (wait == -1000) ? NULL : &tval;
	if(select(m_Socket+1, &flagset, NULL, NULL, ptVal) == -1)
		throw SocketException("Socket.WaitForData()",String("errno is ")+String(errno));
	if( FD_ISSET(m_Socket, &flagset) ) return true;
	else return false; 
}

////////////////////////////////////////////////////////////////////////
int Socket::ConnectionBroken() 
{
	int ret;
	char buf;
	if( WaitForData(0) ) {
		ret = recv(m_Socket,&buf,1,MSG_PEEK);
		if( ret == 0 || ret == SOCKET_ERROR ) return true;
	}
	return false;
}

//////////////////////////////////////////////////////////////////////////
// class ServerSocket 

//////////////////////////////////////////////////////////////////////////
ServerSocket::ServerSocket(int port) 
{
	m_Sockaddr.sin_family = AF_INET;
	m_Sockaddr.sin_addr.s_addr = htonl(INADDR_ANY);
	m_Sockaddr.sin_port = htons(port);
#ifdef WIN32
	char FAR  opt;
	if(!s_Initialized) 
		throw SocketException("ServerSocket.ServerSocket()","Winsock not initialized, use Socket.Init()!");
#else
	int opt;
#endif
	s_NrOfInstances++;
	if( setsockopt(m_Socket,SOL_SOCKET,SO_REUSEADDR,&opt,sizeof(int))<0 ) {
		String met = String("ServerSocket.ServerSocket(")+String(port)+
				String(")");
		String mes = "could not set socket options";
		throw SocketException(met,mes);
	}

	if( bind( m_Socket, (sockaddr*) &m_Sockaddr, sizeof(m_Sockaddr) )<0 ) {
		String met,mes;
		met  = "ServerSocket::ServerSocket("+String(port)+")";
		mes = "could not bind socket: ";
		switch (errno) {
			case 67: {mes += "address already in use";break;}
			default: {mes += String(errno);}
		}
		throw SocketException(met,mes);
	}
	if(listen(m_Socket,1024)<0) {
		String met = String("ServerSocket.ServerSocket(")+String(port)+
				String(")");
		String mes = String("could not change to a listening socket:")+
				String(errno);
		throw SocketException(met,mes);
	}
}

///////////////////////////////////////////////////////////////////////////////
ServerSocket::~ServerSocket()
{
	if(m_Socket>0) {
		CLOSE(m_Socket);
		m_Socket = -1;
	}
	s_NrOfInstances--;
#ifdef WIN32
	if( !s_Initialized && s_NrOfInstances==0 ) 
		WSACleanup();
#endif
}

///////////////////////////////////////////////////////////////////////////////
int ServerSocket::Accept(Socket& soc, long wait)
{
	int con; 
	sockaddr_in sa;
	int len=sizeof(sa);
	memset( (void *) &sa, 0, sizeof(sa) );
	if( !WaitForData(wait) ) return false;
	if( (con = accept(m_Socket,(sockaddr *) &sa,&len))<0 ) {
		String met = "ServerSocket.Accept()";
		String mes = "accept failed "+String(errno);
		throw SocketException(met,mes);
	}
	soc.SetSockAddr(sa);
	soc.SetSocket(con);
	return true;
}		


}
