#include "AbstractRequest.h"

void AbstractRequest::Send() {
    NetworkManager::GetInstance()->SendRequest(this);
}
