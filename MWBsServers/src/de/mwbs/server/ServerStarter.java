package de.mwbs.server;

import java.io.FileReader;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import de.mwbs.server.account.AccountServer;
import de.mwbs.server.configuration.Configuration;
import de.mwbs.server.persistence.BasePersistenceManager;

public class ServerStarter {
    private static Logger logger = Logger.getLogger(ServerStarter.class);
    private static Configuration config = null;
    /**
     * @param args
     */
    public static void main(String[] args) throws Exception{
        BasicConfigurator.configure();
        PropertyConfigurator.configure("log4j.properties");
        logger.info("Init log4j ... done");

        logger.info("Read configuration file");
        config = (Configuration) Configuration.unmarshal(new FileReader("configuration.xml"));
        logger.info("Read configuration file .. done");
        
        logger.info("Init Hibernate");
        BasePersistenceManager.init();
        logger.info("Init Hibernate ... done");
        
        if (config.getAccountserver().getStartup()) {
            startAccountServer();
        }
    }

    
    private static void startAccountServer() {
        AccountServer gs = new AccountServer(config.getAccountserver().getC2sport(), config.getAccountserver().getS2sport(),
                config.getAccountserver().getQueueworkersize());
        gs.start();
    }
}
