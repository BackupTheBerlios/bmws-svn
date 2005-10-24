package de.mwbs.server.exceptions;

/**
 * Description: 
 * @author azarai
 *
 */
public class PersistenceException extends Exception {

    /**
     * 
     */
    public PersistenceException() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     */
    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     */
    public PersistenceException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param cause
     */
    public PersistenceException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

}
