package de.mbws.server.core;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class MBWSServerPlugin extends Plugin {

    private static MBWSServerPlugin plugin;
    public static final String ID = "de.mbws.servers.core";
    
    public static final String ACCOUNT_SERVER_EXTENSION_NAME = "accountServer";
    public static final String WORLD_SERVER_EXTENSION_NAME = "worldServer";
    
    public MBWSServerPlugin() {
        plugin = this;
    }

    /**
     * This method is called upon plug-in activation
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);
    }

    /**
     * This method is called when the plug-in is stopped
     */
    public void stop(BundleContext context) throws Exception {
        super.stop(context);
        plugin = null;
    }

    /**
     * Returns the shared instance.
     */
    public static MBWSServerPlugin getDefault() {
        return plugin;
    }
}