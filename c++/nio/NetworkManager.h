
#ifndef NETWORK_MANAGER_H
#define NETWORK_MANAGER_H

#include "util/socket.h"
#include "util/thread.h"

using namespace util;

class AbstractRequest;

class ReceiverThread : public Thread {
    private:
    Socket* soc;
    
    public:
    ReceiverThread(Socket* soc) {
        this->soc = soc;
    }
    
    virtual void Run();
};

class NetworkManager {
private:
    Socket soc;
    static NetworkManager* instance;
public:
    void Connect(char* address, int port);
    static NetworkManager* GetInstance();
    void SendRequest(AbstractRequest* req);
    void Close();
    
    void WriteShort(short i);
    void WriteString(char* s);
    void WriteString(String s);
    

};

#endif
