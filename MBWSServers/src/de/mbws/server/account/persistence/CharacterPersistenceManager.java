package de.mbws.server.account.persistence;

import java.util.List;

import org.apache.log4j.Logger;

import de.mbws.common.data.generated.Characterdata;
import de.mbws.server.persistence.BasePersistenceManager;

/**
 * Description:
 * 
 * @author Azarai
 */
public class CharacterPersistenceManager extends BasePersistenceManager {
    private static Logger logger = Logger.getLogger(CharacterPersistenceManager.class);

    private static CharacterPersistenceManager instance = new CharacterPersistenceManager();

    public static CharacterPersistenceManager getInstance() {
        return instance;
    }

    public Characterdata getCharacter(String userName, long charId) {
        org.hibernate.Session session = null;
        Characterdata cdata = null;
        try {
            session = getSessionFactory().openSession();
            cdata = (Characterdata) session.createQuery("from Characterdata as cd where cd.account.username = ? and cd.id = ?")
                    .setString(0, userName).setLong(1, charId).uniqueResult();

        } catch (Exception e) {
            logger.error("Error during Character retrieving", e);
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

    public List getAllCharacters(String userName) {
        org.hibernate.Session session = null;
        try {
            session = getSessionFactory().openSession();
            List list = session.createQuery("from Characterdata as cd where " + "cd.account.username = ?").setString(0, userName).list();

            return list;

        } catch (Exception e) {
            logger.error("Error during Character retrieving", e);
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
            } catch (Exception e) {
                logger.error("Error during session closing", e);
            }
        }
        return null;
    }
}
