package org.codehaus.perpetuum.persistence;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite(
                "Test for org.codehaus.perpetuum.persistence");
        //$JUnit-BEGIN$
        suite.addTestSuite(TestUserDAO.class);
        suite.addTestSuite(TestNotPersistableException.class);
        //$JUnit-END$
        return suite;
    }

}
