package de.mbws.server.persistence;

import java.util.List;

import org.apache.log4j.Logger;

import de.mbws.common.data.generated.Characterdata;

/**
 * Description: 
 * @author Azarai
 *
 */
public class CharacterPersistenceManager extends BasePersistenceManager {
    private static Logger logger = Logger.getLogger(CharacterPersistenceManager.class);
    private static CharacterPersistenceManager instance = new CharacterPersistenceManager();
    
    public static CharacterPersistenceManager getInstance() {
        return instance;
    }

    public Characterdata getCharacter(String userName) {
        org.hibernate.Session session = null;
        Characterdata cdata = null;
        try {
            session = getSessionFactory().openSession(); 
            List list = session.createQuery("from Characterdata as cd where " + 
                    "cd.account.username = ?").setString(0,userName).list();
            
            if(list.size() != 1) return null;
            cdata = (Characterdata) list.get(0);
            
        } catch (Exception e) {
            logger.error("Error during Account retrieving", e);
        } finally {
            try {
                if (session != null) {
                    session.close();
                }                
            } catch (Exception e) {
                logger.error("Error during session closing", e);
            }
        }
        
        return cdata;
    } 
}
