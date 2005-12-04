package de.mbws.server;

import de.mbws.server.configuration.EventControllers;

/**
 * Description: 
 * @author Azarai
 *
 */
public class ServerConfig {
    int c2sport;
    int s2sport;
    int queueWorkerSize;
    EventControllers eventControllers;
    String accountServerIp;
    int accountServerPort;
    
    /**
     * @param ip
     * @param port
     * @param c2sport
     * @param controllers
     * @param size
     */
    public ServerConfig(String ip, int port, int c2sport, EventControllers controllers, int size) {
        super();
        // TODO Auto-generated constructor stub
        accountServerIp = ip;
        accountServerPort = port;
        this.c2sport = c2sport;
        eventControllers = controllers;
        queueWorkerSize = size;
    }
    /**
     * @param c2sport
     * @param size
     * @param s2sport
     */
    public ServerConfig(int c2sport, int s2sport, int size, EventControllers ecs) {
        super();
        // TODO Auto-generated constructor stub
        this.c2sport = c2sport;
        queueWorkerSize = size;
        this.s2sport = s2sport;
        this.eventControllers = ecs;
    }
    public int getC2sport() {
        return c2sport;
    }
    public int getQueueWorkerSize() {
        return queueWorkerSize;
    }
    public int getS2sport() {
        return s2sport;
    }
    public EventControllers getEventControllers() {
        return eventControllers;
    }
    public String getAccountServerIp() {
        return accountServerIp;
    }
    public int getAccountServerPort() {
        return accountServerPort;
    }    
}
