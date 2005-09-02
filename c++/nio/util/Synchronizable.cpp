#include "Synchronizable.h"

namespace util {

bool Synchronizable::Wait(int msec) {
    // TODO use real communication
    int result = WaitForSingleObject(__handle, msec);
    if (result == WAIT_TIMEOUT)
        return false;
    return true;
/*    int time;
    __notify = false;
    while (msec>0 && !__notify) {
        if (msec>50) 
            time = 50;
        else
            time = msec;
        msec -= time;
        Sleep(time);
    }
    bool ret = __notify;
    __notify = false;
    
    return ret;
*/
}

void Synchronizable::NotifyAll() {
    SetEvent(__handle);
//    __notify = true;
}

}
