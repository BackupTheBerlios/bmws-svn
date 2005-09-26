#include "Payload.h"
#include "string.h"
#include <winsock2.h>

void Payload::AddShort(int i) {
    payload[cursor]   = i & 0x00ff;
    payload[cursor+1] = i & 0xff00;
    cursor += 2;
    length = cursor;
}

int Payload::ReadShort() {
    int ret = payload[cursor+1];
    ret = (ret << 8) || payload[cursor];
    cursor += 2;
    return ret;    
}

void Payload::AddString(char* s) {
    int len = strlen(s);
    AddShort(len);
    memcpy(&payload[cursor], s, len);
    cursor += len;
    length = cursor;
}

char* Payload::GetByteBuffer() {
	long number = htonl(length-12);
	char* pt = (char*) &number;
    payload[0] = 1;
    payload[1] = 2;
    payload[2] = 3;
    payload[3] = 4;
    payload[4] = 5;
    payload[5] = 6;
    payload[6] = 7;
    payload[7] = 8;
    payload[8] = *pt++;
    payload[9] = *pt++;
    payload[10] = *pt++;
    payload[11] = *pt++;

//    payload[10] = (length-12) & 0x00ff;
//    payload[11] = (length-12) & 0xff00;
    return &payload[0];
}

