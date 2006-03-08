package org.codehaus.perpetuum.utils;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.codehaus.perpetuum.utils");
		//$JUnit-BEGIN$
		suite.addTestSuite(TestPerpetuumUtil.class);
		//$JUnit-END$
		return suite;
	}

}
