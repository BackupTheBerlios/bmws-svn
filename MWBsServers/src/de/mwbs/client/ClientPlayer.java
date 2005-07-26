package de.mwbs.client;

import de.mwbs.common.Player;
import de.mwbs.common.data.AccountData;

public class ClientPlayer extends Player {

    private AccountData accountData = null;
    
    public AccountData getAccountData() {
        return accountData;
    }

    public void setAccountData(AccountData accountData) {
        this.accountData = accountData;
    }
}
