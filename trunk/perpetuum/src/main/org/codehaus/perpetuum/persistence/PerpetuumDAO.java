package org.codehaus.perpetuum.persistence;

/**
 * Generic interface for all Perpetuum DAO objects
 */
public interface PerpetuumDAO {

    /**
     * Used to get the cound of objects implementing this interface
     */
    public int getCount();
    
    /**
     * Used to create a new object
     */
    public void add(Object persistable) throws NotPersistableException;
}
