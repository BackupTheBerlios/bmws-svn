#ifndef REQUEST_H
#define REQUEST_H

#include "Payload.h"
#include "AbstractRequest.h"

#define CHALLENGE 0
#define LOGIN 1
/**
 * Data classes like this one should be generated in a final solution. Lateron 
 * requests should be seperated later.
 */
class ChallengeRequest : public AbstractRequest {
public:
    ChallengeRequest(char* username) {
        payload.AddShort(CHALLENGE);
        payload.AddString(username);
    }
};

#endif
