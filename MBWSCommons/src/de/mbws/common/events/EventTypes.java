package de.mbws.common.events;

/**
 * Description: Defines all Event/Paket-Types
 * 
 * @author Azarai
 * 
 */
public class EventTypes {
    
    // Numbers up to 10 are reserved for Login related Events
    public static final int LOGIN = 1;
    public static final int LOGOUT = 2;
    public static final int LOGIN_FAILED = 3;
    public static final int LOGIN_OK = 4;
    public static final int LOGOUT_OK = 5;

    // Numbers 10 to 20 are reserved for Account related Events
    public static final int ACCOUNT_CREATE = 11;
    public static final int ACCOUNT_CREATE_FAIL = 12;
    public static final int ACCOUNT_CREATE_OK = 13;
}