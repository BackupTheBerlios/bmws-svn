package de.mbws.server.persistence;

import java.util.List;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.exception.ConstraintViolationException;
import net.sf.hibernate.type.Type;

import org.apache.log4j.Logger;

import de.mbws.common.data.generated.Account;
import de.mbws.server.exceptions.DuplicateKeyException;
import de.mbws.server.exceptions.PersistenceException;

/**
 * Description: 
 * @author azarai
 *
 */
public class AccountPersistenceManager extends BasePersistenceManager {
    private static Logger logger = Logger.getLogger(AccountPersistenceManager.class);
    
    private static AccountPersistenceManager instance = new AccountPersistenceManager();
    
    public static AccountPersistenceManager getInstance() {
        return instance;
    }
    
    public void createAccount(Account account) throws PersistenceException, DuplicateKeyException {
        Session session = null;
        try {
            session = getSessionFactory().openSession(); 
            Transaction tx = session.beginTransaction();
            session.save(account);
//            session.flush();
//            session.connection().commit();
            tx.commit();
        } catch (ConstraintViolationException cve) {
            if (logger.isDebugEnabled()) {
                logger.debug("Account already taken, username=" + account.getUsername());
            }
            throw new DuplicateKeyException();
        } catch (Exception e) {
            logger.error("Error during Account creation", e);
            throw new PersistenceException("Error during Account creation. see log file for further information");
        } finally {
            try {
                if (session != null) {
                    session.close();
                }                
            } catch (Exception e) {
                logger.error("Error during session closing", e);
            }
        }
    }
    
    public Account getAccount(String userName, String password) {
        Session session = null;
        Account account = null;
        try {
            session = getSessionFactory().openSession(); 
            List list = session.find("from Account as acc where " + 
                    "acc.username = ? and acc.password = ?",
                    new Object[] { userName, password },
                    new Type[] { Hibernate.STRING, Hibernate.STRING }
                );
            
            if(list.size() != 1) return null;
            account = (Account) list.get(0);
            
        } catch (Exception e) {
            logger.error("Error during Account retrieving", e);
        } finally {
            try {
                if (session != null) {
                    session.close();
                }                
            } catch (Exception e) {
                logger.error("Error during session closing", e);
            }
        }
        
        return account;
    }    
}
