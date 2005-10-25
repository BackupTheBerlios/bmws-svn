package de.mbws.client;

import de.mbws.common.Player;
import de.mbws.common.data.AccountData;

public class ClientPlayer extends Player {

    private AccountData accountData = null;
    
    public AccountData getAccountData() {
        return accountData;
    }

    public void setAccountData(AccountData accountData) {
        this.accountData = accountData;
    }
}
