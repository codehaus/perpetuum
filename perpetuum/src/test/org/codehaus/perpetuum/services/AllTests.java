package org.codehaus.perpetuum.services;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite(
                "Test for org.codehaus.perpetuum.services");
        //$JUnit-BEGIN$
        suite.addTestSuite(TestWebService.class);
        //$JUnit-END$
        return suite;
    }

}
