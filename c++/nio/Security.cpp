#include "Security.h"
#include "Request.h"

bool Security::Login() {
    // TODO evaluate session id from challenge and password
    ChallengeRequest req("axel");
    req.Send();
    return true;
}

