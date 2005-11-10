package de.mbws.server;

/**
 * Description: 
 * @author Azarai
 *
 */
public class ServerConfig {
    int c2sport;
    int s2sport;
    int queueWorkerSize;

    /**
     * @param c2sport
     * @param size
     * @param s2sport
     */
    public ServerConfig(int c2sport, int s2sport, int size) {
        super();
        // TODO Auto-generated constructor stub
        this.c2sport = c2sport;
        queueWorkerSize = size;
        this.s2sport = s2sport;
    }
    public int getC2sport() {
        return c2sport;
    }
    public void setC2sport(int c2sport) {
        this.c2sport = c2sport;
    }
    public int getQueueWorkerSize() {
        return queueWorkerSize;
    }
    public void setQueueWorkerSize(int queueWorkerSize) {
        this.queueWorkerSize = queueWorkerSize;
    }
    public int getS2sport() {
        return s2sport;
    }
    public void setS2sport(int s2sport) {
        this.s2sport = s2sport;
    }    
}
