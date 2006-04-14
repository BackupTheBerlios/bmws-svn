package de.mbws.common;

/**
 * Globals.java
 *
 * Common constants for client and server
 * 
 * @version 1.0
 */
public class Globals {

    /** size of ByteBuffer for reading/writing from channels */
    public static final int NET_BUFFER_SIZE=512;

    /** maximum event size in bytes */
    public static final int MAX_EVENT_SIZE=5000;

    /** interval to sleep between attempts to write to a channel. */
    public static final long CHANNEL_WRITE_SLEEP = 10L;

    /** number of worker threads for EventWriter */
    public static final int EVENT_WRITER_WORKERS = 5;

    /** default number of workers for GameControllers */
    public static final int DEFAULT_CONTROLLER_WORKERS = 5;
    
    /** default status code for standing models */
    public static final byte STANDING = 0;
    
    /** default status code for waling models */
	public static final byte WALKING = 1;
	
	/** default status code for running models */
	public static final byte RUNNING = 2;
	
	/** default status code for walking backwards models */
	public static final byte WALKING_BACKWARD = 3;
	
	/** default status code for left turning models */
	public static final byte TURN_LEFT = 1;
	
	/** default status code for not turning models */
	public static final byte NO_TURN = 0;
	
	/** default status code for right turning models */
	public static final byte TURN_RIGHT = 2;
    
    public static final String OBJECT_ID_PREFIX_CHARACTER = "C";
    
    public static final char GENDER_MALE = 'M';
    public static final char GENDER_FEMALE = 'F';
    
    public static final byte ON = 1;
    public static final byte OFF = 0;
}




