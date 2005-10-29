package de.mbws.server.persistence;

import java.io.File;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import de.mbws.common.exceptions.InitializationException;

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
            cfg = new Configuration().configure(new File("../MBWSCommons/mapping/hibernate.cfg.xml"));
            sessions = cfg.buildSessionFactory();
        } catch (Throwable e) {
            e.printStackTrace();
            logger.error("Error during Persistence initializing", e);
            throw new InitializationException("Error during Persistence initializing. See logfile for further information");
        }

    }
    
    protected SessionFactory getSessionFactory() {
        return sessions;
    }
}
