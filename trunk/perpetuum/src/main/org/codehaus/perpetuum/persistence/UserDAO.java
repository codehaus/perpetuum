package org.codehaus.perpetuum.persistence;

import java.util.List;

import org.codehaus.perpetuum.model.User;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Class used to communicate with the database for the User class
 */
public class UserDAO extends HibernateDaoSupport implements PerpetuumDAO {
    
    /**
     * @see org.codehaus.perpetuum.persistence.PerpetuumDAO#getCount()
     */
    public int getCount() {
        List users =  null;
        int count = 0;
        
        try {
            users = getSession().createQuery("from User").list();
        } catch (Exception e) {
            // Do nothing as this logic is here only for counting users and is 
            // handled by the calling object
        }
        
        if (users != null) {
            count = users.size();
        }
        
        return count;
    }
    
    /**
     * Persists a user to the database
     */
    public void add(Object persistable) throws NotPersistableException {
        if (persistable instanceof User) {
            User user = (User)persistable;
            
            getSession().save(user);
        } else {
            throw new NotPersistableException("You passed a " + persistable.getClass().getName() + " instead of a User!");
        }
    }
}
