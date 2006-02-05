package de.mbws.client;

import java.util.ArrayList;
import java.util.List;

import de.mbws.client.data.Race;
import de.mbws.common.data.xml.RaceElement;
import de.mbws.common.data.xml.RacesDocument;

/**
 * Description: 
 * @author Azarai
 *
 */
public class ConfigurationData {

    private static RacesDocument races;
    
    
    public static List<Race> getAllRaces() {
        if (races == null) {
            races = ReferenceFileReader.getRaces();
        }
        List<Race> result = new ArrayList<Race>();
        for (int i = 0; i < races.getRaceElement().length; i++) {
            RaceElement elem = races.getRaceElement()[i];
            Race race = new Race(elem.getId());
            race.setName(ValueMapper.getRaceName(elem.getId()));
            race.setDescription(ValueMapper.getRaceDescription(elem.getId()));
            race.setPlayable(elem.getPlayable());
            result.add(race);
        }
        return result;
    }
    
    public static List<Race> getAllPLayableRaces() {
        if (races == null) {
            races = ReferenceFileReader.getRaces();
        }
        List<Race> result = new ArrayList<Race>();
        for (int i = 0; i < races.getRaceElement().length; i++) {
            RaceElement elem = races.getRaceElement()[i];
            if (elem.getPlayable()) {
                  Race race = new Race(elem.getId());
                race.setName(ValueMapper.getRaceName(elem.getId()));
                race.setDescription(ValueMapper.getRaceDescription(elem.getId()));
                race.setPlayable(elem.getPlayable());
                result.add(race);  
            }
        }
        return result;
    }    
}
