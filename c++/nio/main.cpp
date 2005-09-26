#include <iostream>
#include <string>
#include "util/string.h"
#include "util/socket.h"
#include <stdlib.h>
#include "util/thread.h"
#include "NetworkManager.h"
#include "Security.h"

using namespace util;
using namespace std;

class MyThread : public Thread {
public:
    void Run() {
        cout << "thread started" << endl;
        while (!Wait(2000)) 
            cout << "waiting..." << endl;
        cout << "got notification!" << endl;
        while (!Wait(2000)) 
            cout << "waiting..." << endl;
        cout << "got 2nd notification!" << endl;
        
    }
};

int main()
{

    try {
 /*       MyThread mt;
        mt.Start();
        Sleep(5000);
        cout << "send notification" << endl;
        mt.NotifyAll();
        Sleep(5000);
        cout << "send notification" << endl;
        mt.NotifyAll();
         cout << "finished" << endl;
*/
        std::cout << "opening socket" << std::endl;
        NetworkManager::GetInstance()->Connect("localhost", 5000);
        Security sec;
        sec.Login();
        Sleep(10000);
        NetworkManager::GetInstance()->Close();
    }
    catch (Exception exc) {
        cout << "ERROR: " << exc.ToString() << endl;
    }
    catch (...) {
        cout << "ERROR: uncaught and unknown exception occured" << endl;
    }
    char d; 
    std::cin >> d;
    return 0;
}
