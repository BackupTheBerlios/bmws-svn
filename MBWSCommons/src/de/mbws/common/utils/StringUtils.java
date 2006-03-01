package de.mbws.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Description:
 * 
 * @author Azarai
 */
public class StringUtils {

    private static final char[] HexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    /**
     * @param payload
     * @return byte array as string
     */
    public static String bytesToString(byte[] payload) {
        int length = payload.length > 80 ? 80 : payload.length;
        String ret = "";
        for (int i = 0; i < length; i++) {
            ret += (char) payload[i];
        }
        return ret;
    }

    public static String hashAndHex(String str) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA1");
        byte[] passwordBytes = str.getBytes();
        md.update(passwordBytes);
        return new String(toHexString(md.digest()));
    }

    public static String hashAndHex(char[] chars) throws NoSuchAlgorithmException {
        return hashAndHex(new String(chars));
    }

    public static final String toHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        int i;
        for (i = 0; i < bytes.length; i++) {
            sb.append(HexChars[(bytes[i] >> 4) & 0xf]);
            sb.append(HexChars[bytes[i] & 0xf]);
        }
        return new String(sb);
    }
}
