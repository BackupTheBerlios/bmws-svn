package de.mbws.server.utils;

/**
 * Description: 
 * @author Azarai
 *
 */
public class IdHelper {

    public static long removePrefix(String id) {
        return Long.valueOf(id.substring(1));
    }
    
}
