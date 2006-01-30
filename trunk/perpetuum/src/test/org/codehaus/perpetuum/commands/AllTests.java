package org.codehaus.perpetuum.commands;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for org.codehaus.perpetuum.commands");
		//$JUnit-BEGIN$
		suite.addTestSuite(StartTest.class);
		suite.addTestSuite(StopTest.class);
		//$JUnit-END$
		return suite;
	}

}
