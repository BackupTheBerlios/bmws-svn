// Synchronizable.h: Schnittstelle für die Klasse Synchronizable.
//
// (C)2000 by Axel Sammet
//
// Synchronisierbare Objekte müssen von dieser Klasse abgeleitet werden.
// Wird eine Methode eines Objekts synchronisiert und von einem Thread
// genutzt, so sind alle synchronisierten Code-Abschnitte dieses Objekts
// für andere Threads gesperrt. Sie müssen am Beginn des Abschnitts warten.
// 
// Codeabschnitte werden durch den Gebrauch der Makros synchronize und 
// synchronizeObject() synchronisiert.
//
// Beispiel:
//   
//   { synchronize; 
//      ...  // synchronisierte Codeabschnitte eines Objektes
//      ...  // können immer nur von einem Thread ausgeführt werden
//   }
//
//   { synchonizeObject(obj);
//      ...  // alle Codeabschnitte die obj synchronisieren und
//      ...  // synchronisierte Methoden von obj können nur von
//      ...  // jeweils einem Thread ausgeführt werden
//   }
// Ein Thread der in einen synchronisierten Codeabschnitt hinein-
// läuft, ohne den Lock erhalten zu können, stoppt und wartet bis
// der Lock wieder freigegeben wird.
// 
//////////////////////////////////////////////////////////////////////

#ifndef SYNCHRONIZABLE_H
#define SYNCHRONIZABLE_H


#include "mutex.h"
#include <iostream>

namespace util {


//////////////////////////////////////////////////////////////////////
// Klasse für internen Gebrauch von Synchronizable
class Synchronizer
{
private:
	Mutex	&extMutex;
public:

	Synchronizer(Mutex &mut) :
		extMutex(mut)
	{
		extMutex.WaitForMutex();
	};

	~Synchronizer() 
	{
		extMutex.ReleaseMutex();
	};
};


//////////////////////////////////////////////////////////////////////
class Synchronizable  
{
public:
	Mutex		  __syncMutex;
    HANDLE        __handle;
	volatile bool __notify;
	
				Synchronizable() {
    __handle = CreateEvent( 
      NULL,    // default security attribute
      TRUE,    // manual reset event 
      FALSE,    // initial state = signaled 
      NULL);
    };
	virtual		~Synchronizable() {
        // TODO ???
	};
	
    virtual void NotifyAll();
    virtual bool Wait(int msecs);

};

#define synchronize Synchronizer __syncObject(__syncMutex);


#define synchronizeObject(ob) Synchronizer __syncObject(ob.__syncMutex);

}
#endif 
