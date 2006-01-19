package org.perpetuum.command;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.perpetuum.command");
		//$JUnit-BEGIN$
		suite.addTestSuite(CommandFinderTest.class);
		//$JUnit-END$
		return suite;
	}

}
