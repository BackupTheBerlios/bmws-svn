package de.mbws.server;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.core.runtime.IPlatformRunnable;

import de.mbws.server.account.AccountServer;
import de.mbws.server.configuration.Configuration;
import de.mbws.server.persistence.BasePersistenceManager;
import de.mbws.server.utils.ResourceLocator;
import de.mbws.server.world.WorldServer;

public class ServerStarter implements IPlatformRunnable {
    private static Logger logger = Logger.getLogger(ServerStarter.class);

    private ArrayList<AbstractTcpServer> servers = new ArrayList<AbstractTcpServer>();

    private AbstractTcpServer startAccountServer(Configuration config) {
        ServerConfig sc = new ServerConfig(config.getAccountserver().getBasicAttributes().getC2sport(), config.getAccountserver()
                .getBasicAttributes().getQueueworkersize());
        AccountServer as = new AccountServer(sc);
        as.start();
        return as;
    }

    private AbstractTcpServer startWorldServer(Configuration config) throws Exception {
        ServerConfig sc = new ServerConfig(config.getWorldserver().getAccountserverip(), config.getWorldserver().getAccountserverport(), config
                .getWorldserver().getBasicAttributes().getC2sport(), config.getWorldserver().getBasicAttributes().getQueueworkersize());
        sc.setMyPublicIP(config.getWorldserver().getPublicip());
        WorldServer ws = new WorldServer(sc);
        ws.start();
        ws.connect();
        return ws;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.IPlatformRunnable#run(java.lang.Object)
     */
    public Object run(Object args) throws Exception {
        BasicConfigurator.configure();
        PropertyConfigurator.configure(ResourceLocator.getResource("config/log4j.properties"));
        logger.info("Init log4j ... done");

        logger.info("Read configuration file");
        Configuration config = (Configuration) Configuration.unmarshal(new FileReader(ResourceLocator.getResource("config/configuration.xml")
                .getFile()));
        logger.info("Read configuration file .. done");

        logger.info("Init Hibernate");
        BasePersistenceManager.init(ResourceLocator.getResource("config/hibernate.cfg.xml"));
        logger.info("Init Hibernate ... done");

        if (config.getAccountserver().getBasicAttributes().getStartup()) {
            servers.add(startAccountServer(config));
        }
        if (config.getWorldserver().getBasicAttributes().getStartup()) {
            servers.add(startWorldServer(config));
        }
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                shutdown();
            }
        });

        boolean running = true;

        while (running) {
            Thread.sleep(100);

        }

        // restart und error einbauen
        return 0;
    }

    private void shutdown() {
        for (Iterator iter = servers.iterator(); iter.hasNext();) {
            AbstractTcpServer server = (AbstractTcpServer) iter.next();
            server.shutdown();
        }
    }
}