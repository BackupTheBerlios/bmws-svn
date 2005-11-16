package de.mbws.server.management;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;

/**
 * Description: 
 * @author Azarai
 *
 */
public class MBeanHelper {
    private static MBeanServer ourMbeanServer = null;
    
    public static MBeanServer getMBeanServer() {
        
        if (ourMbeanServer == null) {

            synchronized (MBeanHelper.class) {
                if (ourMbeanServer == null) {
                    ourMbeanServer = MBeanServerFactory.createMBeanServer("MBWS"); 
                }
            }       
        }       
        return ourMbeanServer;
    }
}