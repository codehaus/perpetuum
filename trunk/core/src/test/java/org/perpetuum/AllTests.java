package org.perpetuum;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.perpetuum");
		//$JUnit-BEGIN$
		suite.addTestSuite(MainTest.class);
		//$JUnit-END$
		return suite;
	}

}
