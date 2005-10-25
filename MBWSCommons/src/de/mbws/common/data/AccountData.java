package de.mbws.common.data;

import de.mbws.common.data.generated.Account;


/**
 * Description: 
 * @author azarai
 *
 */
public class AccountData extends Account {
    private String passwordConfirmation = null;
    private String passwordold = null;

   public String getPasswordConfirmation() {
        return passwordConfirmation;
    }
    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }
    public String getPasswordold() {
        return passwordold;
    }
    public void setPasswordold(String passwordold) {
        this.passwordold = passwordold;
    }
}
