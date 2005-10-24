package de.mwbs.client.gui;

import java.awt.HeadlessException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JDialog;

import org.apache.log4j.Logger;

import de.mwbs.client.Client;

public abstract class AbstractDialog extends JDialog {
    private Logger logger = Logger.getLogger(AbstractDialog.class);
    private Client client = null;

    /**
     * @throws HeadlessException
     */
    public AbstractDialog(Client client) throws HeadlessException {
        super();
        this.client = client;
        initialize();
    }

    protected abstract void initialize();

    protected Client getClient() {
        return client;
    }

    protected Timer startTimeTask(long timeout) {
        Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                timerRun();
            }
        };
        t.schedule(tt, timeout);
        return t;
    }

    protected abstract void timerRun();
}
