// Mutex.cpp: Implementierung der Klasse Mutex.
//
//////////////////////////////////////////////////////////////////////

#include "mutex.h"

#ifdef WIN32
#include <process.h>
#endif

#include <errno.h>
#include <iostream>
#include "thread.h"

namespace util {

//////////////////////////////////////////////////////////////////////
// Konstruktion/Destruktion
//////////////////////////////////////////////////////////////////////

Mutex::Mutex()
{
	// inaktiven Mutex erzeugen
#ifdef WIN32
	mutex = CreateMutex (NULL, FALSE, NULL);
#endif
#ifdef UNIX
   mutex = new pthread_mutex_t;
	if (pthread_mutex_init(mutex, NULL))
		throw ThreadException("Mutex.Mutex()","cannot create mutex.");
#endif
}

//////////////////////////////////////////////////////////////////////
Mutex::~Mutex()
{
	// Mutex vernichten
#ifdef WIN32
	::CloseHandle(mutex);
#endif
#ifdef UNIX
	delete mutex;
#endif
}

//////////////////////////////////////////////////////////////////////
int Mutex::WaitForMutex(int time_msec)
{
#ifdef WIN32
	DWORD ret = ::WaitForSingleObject(mutex, (DWORD) time_msec);
	if (ret != WAIT_TIMEOUT) {
		return true;
	}
	else {
		return false;
	}
#endif
#ifdef UNIX
	if (pthread_mutex_lock(mutex)) cerr << "lock" << endl;
	return true;
#endif
}

//////////////////////////////////////////////////////////////////////
void Mutex::ReleaseMutex()
{
#ifdef WIN32
	::ReleaseMutex( mutex );
#endif
#ifdef UNIX
	if (pthread_mutex_unlock(mutex)) cerr << "unlock" << endl;
#endif
}
}
