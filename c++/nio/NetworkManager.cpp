#include "NetworkManager.h"
#include "util/excclass.h"
#include <iostream>
#include "AbstractRequest.h"

using namespace std;


void ReceiverThread::Run() {
    try {
        while (true) {
            char buf[1024];
            int no = soc->Read(buf, 2);
            if (no != 2) throw Exception("ReceiverThread.Run", "broken message");
            short* len = (short*) &buf[0];
            no = soc->Read(buf, *len);
            cout << "got server message with " << len << " bytes" << endl;
        }
    }
    catch (Exception &exc) {
        cout << "ERROR: " << exc.ToString() << endl;
    }
}

NetworkManager* NetworkManager::instance = NULL;

NetworkManager* NetworkManager::GetInstance() {
    if (NetworkManager::instance == NULL) {
        Socket::Init();
        NetworkManager::instance = new NetworkManager();
        cout << "creating instance for NetworkManager" << endl;
    }
    return NetworkManager::instance;
}

void NetworkManager::Connect(char* address, int port) {
    soc.Connect(address, port);
    if (soc.GetSocket() != 0) {
        ReceiverThread receiver(&soc);
        receiver.Start();
        cout << "connected to " << address << ":" << port << endl;
    }
    else throw Exception("", "NetworkManager not connected");
}

void NetworkManager::SendRequest(AbstractRequest* req) {
    short length = req->GetPayload().GetLength();
    cout << "sending request " << (long) req->GetPayload().GetByteBuffer()[11] << " with " 
         << length<< " bytes" << endl;
    soc.Write(req->GetPayload().GetByteBuffer(), length);
}

void NetworkManager::Close() {
    soc.Close();
}
