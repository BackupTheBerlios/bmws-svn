package de.mbws.server.persistence;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

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
            Query q = session.createQuery("from Account as acc where " + 
            "acc.username = ? and acc.password = ?").setString(0,userName).setString(1,password);
            List list = q.list();
            
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
