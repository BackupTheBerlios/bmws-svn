// Mutex.h: Schnittstelle für die Klasse Mutex.
//
//////////////////////////////////////////////////////////////////////

#ifndef MUTEX_H
#define MUTEX_H

#if _MSC_VER >= 1000
#pragma once
#endif // _MSC_VER >= 1000

#ifdef WIN32

#include <process.h>
#include <errno.h>
#include <wtypes.h>
#define _MUTEX HANDLE

#endif

#ifdef UNIX

#define INFINITE 0
#include <pthread.h>
#include "constants.h"
#define _MUTEX pthread_mutex_t*

#endif

namespace util {

class Mutex
{
private:
	_MUTEX		mutex;
public:
				Mutex();
				Mutex(Mutex& mut) {mutex = mut.mutex;}
	virtual		~Mutex();
	
	int			WaitForMutex(int time_msec=INFINITE);
	// returns true if mutex is received, false on timeout

	void		ReleaseMutex();
};

}
#endif // !defined(AFX_MUTEX_H__2C71E252_1698_11D4_AF03_00609432486F__INCLUDED_)
