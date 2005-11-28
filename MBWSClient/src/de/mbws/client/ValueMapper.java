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
            System.exit(1);
        }       
    }

    public static String getRaceName(int raceId) {
        return props.getProperty(new StringBuffer("race.").append(raceId).toString());
    }
}
