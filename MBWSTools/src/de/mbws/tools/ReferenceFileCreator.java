package de.mbws.tools;

import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import de.mbws.common.data.xml.RaceElement;
import de.mbws.common.data.xml.RacesDocument;
import de.mbws.server.data.db.generated.Race;
import de.mbws.server.persistence.BasePersistenceManager;

/**
 * Description:
 * 
 * @author Azarai
 * 
 */
public class ReferenceFileCreator {
	private static Logger logger = Logger.getLogger(ReferenceFileCreator.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		BasePersistenceManager.init();
		org.hibernate.Session session = null;
		try {
			BasicConfigurator.configure();
			PropertyConfigurator.configure("log4j.properties");
			logger.info("Init log4j ... done");
			Configuration cfg = new Configuration().configure(new File(
					"../MBWSServers/config/hibernate.cfg.xml"));
			SessionFactory sessions = cfg.buildSessionFactory();
			session = sessions.openSession();
			List races = session.createQuery("from Race").list();
			RacesDocument finalRaces = new RacesDocument();
            for (Iterator iter = races.iterator(); iter.hasNext();) {
				Race element = (Race) iter.next();
				RaceElement r = new RaceElement();
                r.setId(element.getId());
                r.setPlayable(Boolean.valueOf(Byte.toString(element.getIsplayable())));
                finalRaces.addRaceElement(r);
			}
            finalRaces.marshal(new FileWriter("../MBWSClient/config/Races.xml"));
		} catch (Exception e) {
			logger.error("Error during ReferenceFileCreation ", e);
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

}
