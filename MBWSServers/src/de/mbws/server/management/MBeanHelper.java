package de.mbws.server.management;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.log4j.Logger;

/**
 * Description: 
 * @author Azarai
 *
 */
public class MBeanHelper {
    private static Logger logger = Logger.getLogger(MBeanHelper.class);
    private static MBeanServer ourMbeanServer = null;
    
    public static MBeanServer getMBeanServer() {
        
        if (ourMbeanServer == null) {

            synchronized (MBeanHelper.class) {
                if (ourMbeanServer == null) {
                    ourMbeanServer = MBeanServerFactory.createMBeanServer("MBWS");
                    try {
                        ObjectName namingName = ObjectName.getInstance("naming:type=rmiregistry");
                        ourMbeanServer.createMBean("mx4j.tools.naming.NamingService", namingName, null);
                        ourMbeanServer.invoke(namingName, "start", null, null);
                        int namingPort = ((Integer)ourMbeanServer.getAttribute(namingName, "Port")).intValue();
                        String jndiPath = "/jmxconnector";
                        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi://localhost/jndi/rmi://localhost:" + namingPort + jndiPath);
                        // Create and start the RMIConnectorServer
                        JMXConnectorServer connectorServer = JMXConnectorServerFactory.newJMXConnectorServer(url, null, ourMbeanServer);
                        connectorServer.start();                        
                    } catch (Exception e) {
                        logger.error("MX4J JMXConnector cannot be started", e);
                    }
                }
            }       
        }       
        return ourMbeanServer;
    }
}