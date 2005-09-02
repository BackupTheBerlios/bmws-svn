#ifndef PAYLOAD_H
#define PAYLOAD_H

#include "util/string.h"

#define MAX_LEN 1024

using namespace util;

class Payload {
private:
    char* payload;
    short length;
    int cursor;
public:
    Payload() {payload = new char[MAX_LEN]; length = 0; cursor = 2;};
    Payload(char* buf, int len);
    void AddShort(int i);
    void AddString(char* s);
    void AddString(String s);
    int ReadShort();
    char* ReadString();
    short GetLength() { return length;};
    char* GetByteBuffer();
};

#endif
