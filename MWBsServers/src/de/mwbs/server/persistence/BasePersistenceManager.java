package de.mwbs.server.persistence;

import java.io.File;

import net.sf.hibernate.SessionFactory;
import net.sf.hibernate.cfg.Configuration;

import org.apache.log4j.Logger;

import de.mwbs.server.exceptions.InitializationException;

/**
 * Description: BasePersistenceManager; Only the persistence Managers should use
 * hibernate directly.
 * 
 * @author azarai
 */
public class BasePersistenceManager {

    private static Logger logger = Logger.getLogger(BasePersistenceManager.class);

    static Configuration cfg = null;

    static SessionFactory sessions = null;

    public static void init() throws InitializationException {
        try {
            cfg = new Configuration().configure(new File("mapping/hibernate.cfg.xml"));
            sessions = cfg.buildSessionFactory();
        } catch (Exception e) {
            logger.error("Error during Persistence initializing", e);
            throw new InitializationException("Error during Persistence initializing. See logfile for further information");
        }

    }
    
    protected SessionFactory getSessionFactory() {
        return sessions;
    }
}
