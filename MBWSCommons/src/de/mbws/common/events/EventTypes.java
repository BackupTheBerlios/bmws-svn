package de.mbws.common.events;

/**
 * Description: Defines all Event/Paket-Types
 * 
 * @author Azarai
 * 
 */
public class EventTypes {
    
    // Numbers up to 10 are reserved for Login related Events
    public static final int GROUPID_EVENT_LOGIN = 0;
    public static final int C2S_LOGIN = 1;
    public static final int C2S_LOGOUT = 2;
    public static final int S2C_LOGIN_FAILED = 3;
    public static final int S2C_LOGIN_OK = 4;
    public static final int S2C_LOGOUT_OK = 5;
    public static final int S2S_LOGIN = 6;
    public static final int S2S_LOGIN_OK = 7;

    // Numbers 10 to 20 are reserved for Account related Events
    public static final int GROUPID_EVENT_ACCOUNT = 1;
    public static final int C2S_ACCOUNT_CREATE = 11;
    public static final int S2C_ACCOUNT_CREATE_FAIL = 12;
    public static final int S2C_ACCOUNT_CREATE_OK = 13;
    
    public static final int GROUPID_EVENT_CHARACTER = 2;
    public static final int C2S_CHARACTER_RECEIVE_REQUEST = 21;
    public static final int S2C_CHARACTER_RECEIVE = 22;
    public static final int C2S_CHARACTER_LIST_RECEIVE_REQUEST = 23;
    public static final int S2C_CHARACTER_LIST_RECEIVE = 24;
    public static final int C2S_CHARACTER_START_PLAYING_REQUEST = 25;
    public static final int S2C_CHARACTER_START_PLAYING = 26;
    public static final int C2S_CHARACTER_DELETE_REQUEST = 27;
    public static final int C2S_CHARACTER_CREATE_REQUEST = 28;
    public static final int S2C_CHARACTER_CREATE_OK = 29;
    public static final int S2C_CHARACTER_CREATE_FAIL = 30;
    public static final int C2S_CHARACTER_ENTERS_WORLD_REQUEST = 31;
    public static final int S2C_CHARACTER_ENTERS_WORLD = 32;
    public static final int S2S_CHARACTER_NEW_CHARACTER_ENTERS_WORLD = 33;
    public static final int S2S_CHARACTER_NEW_CHARACTER_ENTERS_WORLD_OK = 34;
    
    public static final int GROUPID_EVENT_REDIRECT = 4;
    public static final int S2C_REDIRECT_TO_WORLDSERVER = 41;
    
    //Numbers 100 to 150 are reserved for movement related events
    public static final int GROUPID_EVENT_MOVEMENT = 10;
    public static final int MOVEMENT_START_WALK = 100;
    public static final int MOVEMENT_STOP_WALK = 101;
    public static final int MOVEMENT_START_RUN = 102;
    //TODO: is that needed ?
    public static final int MOVEMENT_STOP_RUN = 103;
    public static final int MOVEMENT_START_TURN_RIGHT = 104;
    public static final int MOVEMENT_STOP_TURN = 105;
    public static final int MOVEMENT_START_TURN_LEFT = 106;
    public static final int MOVEMENT_START_WALK_BACKWARDS = 107;
    public static final int MOVEMENT_START_WALK_AND_TURN_RIGHT = 108;
    public static final int MOVEMENT_START_WALK_AND_TURN_LEFT = 109;
    public static final int MOVEMENT_START_WALK_BACKWARDS_AND_TURN_RIGHT = 109;
    public static final int MOVEMENT_START_WALK_BACKWARDS_AND_TURN_LEFT = 110;
    public static final int MOVEMENT_UPDATE_LOCATION = 150;
    
    //Numbers 200 to 250 are reserved to object related events
    public static final int GROUPID_EVENT_OBJECT = 20;
    public static final int OBJECT_CREATE = 200;
    public static final int OBJECT_DESTROY = 201;
    public static final int OBJECT_TAKEN = 202;
    public static final int OBJECT_DROPPED = 203;
    
    public static final int S2C_MOVABLE_OBJECT_CREATE = 204;
    public static final int S2C_MOVABLE_OBJECT_DESTROY = 205;
    
    //numbers 300 to 310 are reserved to chat related events
    public static final int GROUPID_EVENT_CHAT = 30;
    public static final int CHAT_WHISPER = 300;
    public static final int CHAT_SAY = 301;
    public static final int CHAT_SHOUT = 302;
    public static final int CHAT_GROUP_SAY = 303;
    public static final int CHAT_PM = 304;
    public static final int CHAT_EMOTE = 305;
    public static final int CHAT_ADMIN_COMMAND = 306;
    
    public static final int S2C_SYSTEM_MESSAGE = 307;
}