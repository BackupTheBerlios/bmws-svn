package de.mbws.server.utils;

import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import de.mbws.server.core.MBWSServerPlugin;

/**
 * Description: 
 * @author Azarai
 *
 */
public class ResourceLocator {

    
    public static URL getResource(String name) throws IOException {
        Bundle b = Platform.getBundle(MBWSServerPlugin.ID);
        return Platform.asLocalURL(b.getEntry(name));
    }
}
