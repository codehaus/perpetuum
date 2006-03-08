package org.codehaus.perpetuum.states;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for org.codehaus.perpetuum.states");
		//$JUnit-BEGIN$
		suite.addTestSuite(TestStateChangedEvent.class);
		//$JUnit-END$
		return suite;
	}

}
