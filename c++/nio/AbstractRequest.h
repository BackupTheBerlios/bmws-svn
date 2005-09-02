#ifndef ABSTRACT_REQUEST_H
#define ABSTRACT_REQUEST_H

#include "NetworkManager.h"
#include "Payload.h"

class AbstractRequest {
protected:
    Payload payload;
public:
    void Send();
    Payload GetPayload() {return payload;};
};

#endif
