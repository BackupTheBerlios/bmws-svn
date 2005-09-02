#include "Payload.h"
#include "string.h"

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
    payload[0] = (length-2) & 0x00ff;
    payload[1] = (length-2) & 0xff00;
    return &payload[0];
}

