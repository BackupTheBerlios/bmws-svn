package de.mbws.server;


/**
 * Description: 
 * @author Azarai
 *
 */
public class ServerConfig {
    int c2sport;
    int queueWorkerSize;
    String myPublicIP;
    String accountServerIp;
    int accountServerPort;
    
    /**
     * @param ip
     * @param port
     * @param c2sport
     * @param size
     */
    public ServerConfig(String ip, int port, int c2sport, int size) {
        super();
        accountServerIp = ip;
        accountServerPort = port;
        this.c2sport = c2sport;
        queueWorkerSize = size;
    }
    /**
     * @param c2sport
     * @param size
     */
    public ServerConfig(int c2sport,int size) {
        super();
        this.c2sport = c2sport;
        queueWorkerSize = size;
    }
    public int getC2sport() {
        return c2sport;
    }
    public int getQueueWorkerSize() {
        return queueWorkerSize;
    }
    public String getAccountServerIp() {
        return accountServerIp;
    }
    public int getAccountServerPort() {
        return accountServerPort;
    }
    public String getMyPublicIP() {
        return myPublicIP;
    }
    public void setMyPublicIP(String myPublicIP) {
        this.myPublicIP = myPublicIP;
    }    
}
