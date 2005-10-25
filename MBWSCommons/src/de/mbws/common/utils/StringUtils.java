package de.mbws.common.utils;

/**
 * Description: 
 * @author Azarai
 *
 */
public class StringUtils {
    /**
     * @param payload
     * @return
     */
    public static String bytesToString(byte[] payload) {
        int length = payload.length > 80 ? 80 : payload.length;
        String ret = "";
        for (int i = 0; i < length; i++) {
            ret += (char) payload[i];
        }
        return ret;
    }
}
