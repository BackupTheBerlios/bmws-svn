package de.mbws.server.data;

import de.mbws.common.data.AbstractPlayerData;
import de.mbws.common.events.data.generated.ServerRedirectData;

/**
 * Description: 
 * @author Azarai
 *
 */
public class ServerCommunicationData extends AbstractPlayerData {

    private ServerRedirectData hostInfo;

    public ServerRedirectData getHostInfo() {
        return hostInfo;
    }

    public void setHostInfo(ServerRedirectData hostInfo) {
        this.hostInfo = hostInfo;
    }
}
