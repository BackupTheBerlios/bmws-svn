// Thread.cpp: implementation of the CThread class.
//
//////////////////////////////////////////////////////////////////////

#include "thread.h"

namespace util {

#ifdef UNIX
//////////////////////////////////////////////////////////////////////
// Code für UNIX
//////////////////////////////////////////////////////////////////////

#include <sys/types.h>
#include <unistd.h>
#include <stdlib.h>

int Thread::m_MaxNrOfThreads = 512;
int Thread::m_NrOfThreads = 0;

//////////////////////////////////////////////////////////////////////
Thread::Thread()
{
	m_PID = 0;
}

//////////////////////////////////////////////////////////////////////
Thread::~Thread()
{

}

//////////////////////////////////////////////////////////////////////
void Thread::Start() 
{
	m_Thread = NULL;
	pthread_attr_t *attr = NULL;
	pthread_attr_init(attr);
	int err;
	if(m_NrOfThreads>=m_MaxNrOfThreads) 
			throw ThreadException("Thread::Start()","maximum number of threads reached");
	if(err=pthread_create(&m_Thread,NULL,Thread::CreateThread,this)) {
		String mes;
		switch(err) {
			case EAGAIN: {
				mes = "too many threads, system cannot start another";
				break;
			}
			case EINVAL: {
				mes = "input parameters not valid"; 
			}
		}
		throw ThreadException(												
								"Thread::Start()",
								mes
		);
	}
}

//////////////////////////////////////////////////////////////////////
void* Thread::CreateThread(void *thread)
{
	m_NrOfThreads++;
	((Thread *) thread)->Run(); 
	m_NrOfThreads--;
	return NULL;
}

//////////////////////////////////////////////////////////////////////
void Thread::Execute(String command)
{
	int iRC = 0;
	pid_t pid = 0;
  
	if ((pid = fork()) == 0) {
    		// CHILD 
  		  execl( "/usr/bin/sh", "sh", "-c", command, (char *) 0 );
   		 iRC = -1;
  	}
	else if (pid == -1) {
 		// ERROR
 		iRC = -1;
	}
	else {
		// PARENT 
		int iExit = 0;
		int i = waitpid (pid, &iExit, 0);
		if (i != -1) 
			iRC = WEXITSTATUS (iExit);
		else 
			iRC = i;
	}
}

int Thread::Join(long wait_msec)
{
	pthread_join(m_Thread, NULL);
	return true;
}

#endif


#ifdef WIN32
//////////////////////////////////////////////////////////////////////
// Code für Windows
//////////////////////////////////////////////////////////////////////

#include <process.h>
#include <errno.h>


int Thread::m_MaxNrOfThreads = 512;
volatile int Thread::m_NrOfThreads = 0;

extern int errno;
//////////////////////////////////////////////////////////////////////
Thread::Thread()
{
	m_PID = 0;
	m_started = false;
}

//////////////////////////////////////////////////////////////////////
Thread::~Thread()
{

}

//////////////////////////////////////////////////////////////////////
void Thread::Start() 
{
	m_NrOfThreads++;
	int err;
	if(m_NrOfThreads>=m_MaxNrOfThreads) 
			throw ThreadException("Thread::Start()","maximum number of threads reached");
	if( _beginthread(Thread::CreateThread,0,this)<0 ) {
		m_NrOfThreads--;
		String mes;
		switch(errno) {
			case EAGAIN: 
				mes = "too many threads, system cannot start another";
				break;
			case EINVAL: 
				mes = "input parameters not valid"; 
				break;
			default: 
				mes = String("errorcode: ")+String(err);
		}
		throw ThreadException(												
								"Thread::Start()",
								mes
		);
	}

}

//////////////////////////////////////////////////////////////////////
int Thread::Join(long waittime_msec)
{
	if (!m_started ) {
		Sleep(100);
	}
	if (m_started && m_join.WaitForMutex(waittime_msec)) {
		m_join.ReleaseMutex();
		return true;
	}
	else return false;
}

//////////////////////////////////////////////////////////////////////
void __cdecl Thread::CreateThread(void *thread)
{
	((Thread *) thread)->m_join.WaitForMutex();
	((Thread *) thread)->m_started = true;
	((Thread *) thread)->Run();
	m_NrOfThreads--;
	((Thread *) thread)->m_join.ReleaseMutex();
	return;
}

}
#endif
