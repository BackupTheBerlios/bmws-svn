package de.mbws.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import de.mbws.common.data.generated.Language;
import de.mbws.common.data.generated.LanguageTextMapping;
import de.mbws.server.persistence.BasePersistenceManager;

/**
 * Description:
 * 
 * @author Azarai
 * 
 */
public class LanguageFileCreator {
	private static Logger logger = Logger.getLogger(LanguageFileCreator.class);

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
			List languages = session.createQuery("from Language").list();
			for (Iterator iter = languages.iterator(); iter.hasNext();) {
				Language element = (Language) iter.next();
				Set map = null;
				map = element.getLanguageTextMappings();
				Properties props = new Properties();

				for (Iterator iterator = map.iterator(); iterator.hasNext();) {
					LanguageTextMapping ltm = (LanguageTextMapping) iterator
							.next();
					logger.info(element.getShortname() + "  " + ltm.getText());
					props.put(ltm.getTextKey(), ltm.getText());
				}
				File out = new File("../MBWSClient/config/LanguageBundle_"
						+ element.getShortname() + ".properties");
				FileOutputStream fout = new FileOutputStream(out);
				props.store(fout, "");
			}

		} catch (Exception e) {
			logger.error("Error during LanguageMapping retrieving", e);
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
