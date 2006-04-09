package org.codehaus.perpetuum.persistence;

import java.util.Iterator;

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
        Iterator i =  getSession().createQuery("from User").list().iterator();
        int count = 0;
        
        if (i.hasNext()) {
            Object[] row = (Object[])i.next();
            
            count = Integer.parseInt((String)row[0]);
        }
        
        return count;
    }
    
    public void add(Object persistable) throws NotPersistableException {
        if (persistable instanceof User) {
            User user = (User)persistable;
            
            getSession().save(user);
        } else {
            throw new NotPersistableException("You passed a " + persistable.getClass().getName() + " instead of a User!");
        }
    }
}
