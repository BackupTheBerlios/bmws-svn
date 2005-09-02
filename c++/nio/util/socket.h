////////////////////////////////////////////////////////////////////////
// socketclass.h
//
// (C)2000 by Axel Sammet
//
// Klasse für einfache Socketverbindungen
//
// unter Win mit ws2_32.lib  zu linken
//
////////////////////////////////////////////////////////////////////////
#ifndef SOCKETCL_H
#define SOCKETCL_H

#include "string.h"
#include "excclass.h"
//#include "bytearrayclass.h"

#ifdef UNIX

#include <sys/types.h>
#include <sys/socket.h>
#include <sys/socketvar.h>
#include <netdb.h>

#endif

#ifdef WIN32

#include <winsock2.h>

#endif

namespace util {
////////////////////////////////////////////////////////////////////////
class SocketException : public Exception 
{
public:
	SocketException(String met, String mes) {
		method  = met;
		message = mes;
	}
};

class SocketTimeoutException : public SocketException 
{
public:
	SocketTimeoutException(String met, String mes) :
			SocketException(met,mes)
	{
	}
};

class SocketBrokenException : public SocketException 
{
public:
	SocketBrokenException(String met, String mes) :
			SocketException(met,mes)
	{
	}
};


/////////////////////////////////////////////////////////////////////////
class Socket 
{
protected: 		// Membervariablen
	int		            m_Socket;
	sockaddr_in			m_Sockaddr;
	static int			s_NrOfInstances;
	static int			s_Initialized;
public:				// Methoden

	// Konstruktor / Destruktor
						Socket();
	virtual				~Socket();
	
	static void			Init();
	static void			Cleanup();
	// initialize and cleanup winsocks
	// only required for WIN32, calling doesn't harm though

	void				Connect(char* address, int port);
	// connect to a listening server socket
	// address	: hostname or ip
	// port     : port number

	void				WriteString(String s);
	// sends a String to the other side of the socket connection
	// the String can contain \0. Thus the length is send first.
	// Do not use to send ASCII, use Write(char*) instead.
	// s				: string to send

	String				ReadString(long timeout=-1); 
	// reads a String from the socket connection
	// timeout	: in msecs, -1 indefinetly 
	// returns	: String read from socket
 	// throws	: SocketException, SocketTimeoutException					

	String				ReadLine(long timeout=-1);	
	// reads characters from the socket up to the next \n
	// timeout	: in msecs, -1 indefinetly
	// returns  : String read from socket
	// throws	: SocketException, SocketTimeoutException 

	void				WriteLong(long nr);

	long				ReadLong(long timeout=-1);

/*
	void				Write(const ByteArray &ba);
	// not yet implemented
	ByteArray			Read(long timeout=-1);
	// not yet implemented
*/
	void				Write(char* pt,int len=-1);
	// writes a character array with the given length.
	// If no length is given, a String is assumed and strlen(pt)
	// bytes will be send.
	// pt		: first character
	// len		: length of array
	int					Read(char* pt,int len, long timeout=-1);
	// reads a charater array of a given length
	// pt		: first character of the array
	// len		: length of array		
	// timeout	: in msec

	int					DataTransferred();
	// checks if written data has been transmitted

	int					WaitForData(long wait=-1);
	// checks if there is data available on the connection
	// wait			: time to wait in msec, if -1 wait indefinetly
	// return		: true if data available else false

	int					ConnectionBroken();
	// returns true if the other side has disconnected.
	// WORKS ONLY IF no more data in the queue is available. 
	// If data exists in the queue or connection is online 
	// returns false. Unfortunately breaking the connection looks
	// like new data. Best method to check for broken connection is:
	// try { WaitForData(100); Read??? } catch(SocketBrokenException exc)

	String				GetAddressString();
	// returns ip-address of connected socket
	// seems to have a bug sometimes

	int					GetSocket();
	// returns socket handle

	void				SetSocket(int soc);
	// sets a previous created socket

	void 				SetSockAddr(sockaddr_in sa);
	// sets an inet socket address

	void				Close();
	// closes the socket (will also be called by destructor)
};


//////////////////////////////////////////////////////////////////////////
// class ServerSocket 

class ServerSocket : public Socket
{
public:
						ServerSocket(int port);
	// creates a server socket which can accept connections on
	// the given port
	// port		: number of the port

	virtual		~ServerSocket();
	// destructor

	int				Accept(Socket& soc, long wait=-1);
	// waits for a client to connect 
	// soc		: holds the socket for the accepted connection
	// 			  after Accept() returns
	// wait		: time the server waits for a client connection in msec
	// returns	: true if a new connection was accepted, false otherwise
};

}
#endif
