package de.mbws.client;

import java.io.FileReader;

import org.apache.log4j.Logger;

import de.mbws.common.data.race.Races;

/**
 * Description: 
 * @author Azarai
 *
 */
public class ReferenceFileReader {
    private static Logger logger = Logger.getLogger(ReferenceFileReader.class);
    
    public static Races getRaces() {
        try {
            return (Races) Races.unmarshal(new FileReader("config/Races.xml"));
        } catch (Exception e) {
            logger.error("Couldnt read races.xml...serious problem", e);
            return null;
        }
    }
}
