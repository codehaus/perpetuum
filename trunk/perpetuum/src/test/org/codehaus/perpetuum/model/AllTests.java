package org.codehaus.perpetuum.model;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite("Test for org.codehaus.perpetuum.model");
        //$JUnit-BEGIN$
        suite.addTestSuite(TestUser.class);
        //$JUnit-END$
        return suite;
    }

}
