package de.mbws.client;

import java.io.File;
import java.io.FileInputStream;
import java.util.Locale;
import java.util.Properties;

/**
 * Description: 
 * @author Azarai
 *
 */
public class ValueMapper {

    private static Properties props;
    /**
     * 
     */
    public ValueMapper() {
        super();
        String fileName = "config/LanguageBundle_" + Locale.getDefault().getCountry() + ".properties";
        props = new Properties();
        try {
            props.load(new FileInputStream(new File(fileName)));    
        } catch (Exception e) {
            e.printStackTrace();
            MBWSClient.exit();
        }       
    }

    public static String getRaceName(int raceId) {
        return props.getProperty(new StringBuffer("race.name.").append(raceId).toString());
    }

    public static String getRaceDescription(int raceId) {
        return props.getProperty(new StringBuffer("race.description.").append(raceId).toString());
    }

    
    public static String getText(String key) {
        try {
            return props.getProperty(key);    
        } catch (Exception e) {
            // TODO: handle exception
        }
    	return "not found";
    }
}
