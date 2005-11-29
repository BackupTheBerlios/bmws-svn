package de.mbws.server;

import java.io.FileReader;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import de.mbws.server.account.AccountServer;
import de.mbws.server.configuration.Configuration;
import de.mbws.server.persistence.BasePersistenceManager;

public class ServerStarter {
    private static Logger logger = Logger.getLogger(ServerStarter.class);
    private static Configuration config = null;
    /**
     * @param args
     */
    public static void main(String[] args) throws Exception{
              
         BasicConfigurator.configure();
        PropertyConfigurator.configure("config/log4j.properties");
        logger.info("Init log4j ... done");

        logger.info("Read configuration file");
        config = (Configuration) Configuration.unmarshal(new FileReader("config/configuration.xml"));
        logger.info("Read configuration file .. done");
        
        logger.info("Init Hibernate");
        BasePersistenceManager.init();
        logger.info("Init Hibernate ... done");
        
        if (config.getAccountserver().getBasicAttributes().getStartup()) {
            startAccountServer();
        }
    }

    
    private static void startAccountServer() {
        ServerConfig sc = new ServerConfig(config.getAccountserver().getBasicAttributes().getC2sport(), config.getAccountserver().getS2sport(),
                config.getAccountserver().getBasicAttributes().getQueueworkersize(), config.getAccountserver().getEventControllers());
        AccountServer as = new AccountServer(sc);
        as.start();
    }
}
