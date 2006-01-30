package org.codehaus.perpetuum;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.codehaus.perpetuum");
		//$JUnit-BEGIN$
		suite.addTestSuite(PerpetuumLauncherTest.class);
		//$JUnit-END$
		return suite;
	}
}
