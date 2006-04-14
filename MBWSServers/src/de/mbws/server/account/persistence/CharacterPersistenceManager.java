package de.mbws.server.account.persistence;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import de.mbws.server.data.db.generated.CharacterData;
import de.mbws.server.data.db.generated.Map;
import de.mbws.server.data.db.generated.Race;
import de.mbws.server.exceptions.DuplicateKeyException;
import de.mbws.server.exceptions.PersistenceException;
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

    public CharacterData getCharacter(String userName, long charId) {
        org.hibernate.Session session = null;
        CharacterData cdata = null;
        try {
            session = getSessionFactory().openSession();
            cdata = (CharacterData) session.createQuery("from CharacterData as cd where cd.account.username = ? and cd.id = ?")
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
    public CharacterData getCharacterByID(long charId) {
        org.hibernate.Session session = null;
        CharacterData cdata = null;
        try {
            session = getSessionFactory().openSession();
            cdata = (CharacterData) session.createQuery("from CharacterData as cd where cd.id = ?")
                    .setLong(0, charId).uniqueResult();

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
            List list = session.createQuery("from CharacterData as cd where " + "cd.account.username = ?").setString(0, userName).list();

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
    
    public void createCharacter(CharacterData character) throws PersistenceException, DuplicateKeyException {
        Session session = null;
        try {
            session = getSessionFactory().openSession(); 
            Transaction tx = session.beginTransaction();
            Long key = (Long) session.save(character);
            character.getCharacterStatus().setId(key);
            session.save(character.getCharacterStatus());
            character.getCharacterVisualappearance().setId(key);
            session.save(character.getCharacterVisualappearance());
//            session.flush();
//            session.connection().commit();
            tx.commit();
        } catch (ConstraintViolationException cve) {
            if (logger.isDebugEnabled()) {
                logger.debug("CharacterName already taken, characterName=" + character.getCharactername());
            }
            throw new DuplicateKeyException();
        } catch (Exception e) {
            logger.error("Error during Character creation", e);
            throw new PersistenceException("Error during Character creation. see log file for further information");
        } finally {
            try {
                if (session != null) {
                    session.close();
                }                
            } catch (Exception e) {
                logger.error("Error during session closing", e);
            }
        }
    }
    
    public Race getRace(int id) {
        org.hibernate.Session session = null;
        try {
            session = getSessionFactory().openSession();
           return (Race) session.createQuery("from Race as r where " + "r.id = ?").setInteger(0, id).uniqueResult();

        } catch (Exception e) {
            logger.error("Error during Race retrieving", e);
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
    
    public Map getMap(int id) {
        org.hibernate.Session session = null;
        try {
            session = getSessionFactory().openSession();
           return (Map) session.createQuery("from Map as m where " + "m.id = ?").setInteger(0, id).uniqueResult();

        } catch (Exception e) {
            logger.error("Error during Map retrieving", e);
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
