// Thread.h: interface for the CThread class.
//
//////////////////////////////////////////////////////////////////////

#ifndef THREAD_H
#define THREAD_H

#if _MSC_VER >= 1000
#pragma once
#endif // _MSC_VER >= 1000

// immer zuerst
#ifdef UNIX
#include <pthread.h>
#include <errno.h>
#endif

#include "Synchronizable.h"
#include "string.h"
#include "excclass.h"
#include "mutex.h"

namespace util {

class ThreadException : public Exception {
public:
	ThreadException(String met,String mes) {
		method = met;
		message = mes;
	}
};


///////////////////////////////////////////////////////////////////////////
//                            abstrakte Threadklasse
//
// ein neuer Thread wird von Thread abgeleitet und die Run()-Methode
// implementiert. Der neue Thread wird durch die Methode Start() gestartet.
// Windows: mutithreading beim Bau aktivieren -> Option _MT 
///////////////////////////////////////////////////////////////////////////


class Thread : public Synchronizable {
private:
	int					m_PID;
	static int			m_MaxNrOfThreads;
	Mutex				m_join;
#ifdef UNIX
	static int			m_NrOfThreads;
	static void *		CreateThread (void *);
	pthread_t			m_Thread;
#endif
#ifdef WIN32
	static volatile int	m_NrOfThreads;
	static void __cdecl CreateThread(void *);
#endif
	volatile int		m_started;
public:
						Thread();
	virtual 			~Thread();
	static int			GetNrOfThreads() { return m_NrOfThreads; };
	static void			SetMaxNrOfThreads(int max) { m_MaxNrOfThreads = max; };
	static int			GetMaxNrOfThreads() { return m_MaxNrOfThreads; };
	void				Start();
	virtual void		Run()=0;
	int					GetPID() {return m_PID;};
	static void			Execute(String command);
	int					Join(long wait_msec=INFINITE);
	void				Reset() {m_started=0; }
};

}
#endif
