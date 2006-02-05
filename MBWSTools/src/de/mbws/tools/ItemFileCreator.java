package de.mbws.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;

import de.mbws.common.data.db.Item;
import de.mbws.server.persistence.BasePersistenceManager;

/**
 * Description: This file extracts all entries in the Items Table and creates a
 * file for the client containing all needed informations for the client to load
 * and display icons and models of the item.
 * At the moment we only extract 
 * -name
 * -description
 * -location of the model
 * -location of the icon
 * -id of the item
 * -itemtypeID
 * @author Kerim
 * 
 */
public class ItemFileCreator {
	private static Logger logger = Logger.getLogger(ItemFileCreator.class);

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
			Document doc = new Document();
			Element root = new Element("items");
			doc.setRootElement(root);
			List items = session.createQuery("from Item").list();
			// TODO: We do not serialize any more data than we MUST know ? Do we
			// need to know more ?
			for (Iterator iter = items.iterator(); iter.hasNext();) {
				Item item = (Item) iter.next();
				Element element = new Element("ID" + item.getId().toString());
				Attribute name = new Attribute("name", item.getName());
				Attribute descr = new Attribute("description", item
						.getDescription());
				Attribute model = new Attribute("model", item.getModelName());
				Attribute icon = new Attribute("icon", item.getIconName());
				Attribute type = new Attribute("itemtype", item.getItemType().getId().toString());
				element.setAttribute(name);
				element.setAttribute(descr);
				element.setAttribute(model);
				element.setAttribute(icon);
				element.setAttribute(type);
				root.addContent(element);
			}

			File out = new File("../MBWSClient/config/Items.xml");
			FileOutputStream fout = new FileOutputStream(out);
			XMLOutputter serializer = new XMLOutputter();
			serializer.output(doc, fout);
			fout.flush();
			fout.close();

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
