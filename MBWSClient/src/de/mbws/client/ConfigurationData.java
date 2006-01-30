package de.mbws.client;

import java.util.ArrayList;
import java.util.List;

import de.mbws.common.data.race.Race;
import de.mbws.common.data.race.Races;

/**
 * Description: 
 * @author Azarai
 *
 */
public class ConfigurationData {

    private static Races races;
    
    
    public static List<Race> getAllRaces() {
        if (races == null) {
            races = ReferenceFileReader.getRaces();
        }
        List<Race> result = new ArrayList<Race>();
        for (int i = 0; i < races.getRace().length; i++) {
            result.add(races.getRace()[i]);
        }
        return result;
    }
    
    public static List<Race> getAllPLayableRaces() {
        if (races == null) {
            races = ReferenceFileReader.getRaces();
        }
        List<Race> result = new ArrayList<Race>();
        for (int i = 0; i < races.getRace().length; i++) {
            Race r = races.getRace()[i];
            if (r.getPlayable()) {
                result.add(r);    
            }
        }
        return result;
    }    
}
