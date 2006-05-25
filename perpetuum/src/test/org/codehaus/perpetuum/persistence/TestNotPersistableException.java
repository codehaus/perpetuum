package org.codehaus.perpetuum.persistence;

import junit.framework.TestCase;

public class TestNotPersistableException extends TestCase {
    /*
     * Test method for 'org.codehaus.perpetuum.persistence.NotPersistableException.NotPersistableException(String)'
     */
    public void testNotPersistableException() {
        assertNotNull("Should return a NotPersistableException object.", new NotPersistableException("Test Exception"));
    }
}