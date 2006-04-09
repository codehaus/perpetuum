package org.codehaus.perpetuum.persistence;

/**
 * Class used when raising an Exception when an unexpected object type is passed 
 * to any of the PerpetuumDAO methods
 */
public class NotPersistableException extends RuntimeException {
    
    /**
     * Constructor
     */
    public NotPersistableException(String reason) {
        super(reason);
    }
}
