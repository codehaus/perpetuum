package org.codehaus.perpetuum.utils;

import junit.framework.TestCase;

public class TestPerpetuumUtil extends TestCase {

	/**
	 * Test method for 'org.codehaus.perpetuum.utils.PerpetuumUtil.setup()'
	 */
	public void testSetup() {
		PerpetuumUtil.setup();
		
		assertNotNull("perpetuum.logs.home should be set after setup() is called!", System.getProperty("perpetuum.logs.home"));
		assertNotNull("perpetuum.home should be set after setup() is called!", System.getProperty("perpetuum.home"));
	}

	/**
	 * Test method for 'org.codehaus.perpetuum.utils.PerpetuumUtil.setupLogging()'
	 */
	public void testSetupLogging() {
		PerpetuumUtil.setupLogging();
		
		assertNotNull("perpetuum.logs.home should be set after setup() is called!", System.getProperty("perpetuum.logs.home"));
	}

	/**
	 * Test method for 'org.codehaus.perpetuum.utils.PerpetuumUtil.setupClasspath()'
	 */
	public void testSetupClasspath() {
		PerpetuumUtil.setupClasspath();
		
		assertNotNull("perpetuum.home should be set after setup() is called!", System.getProperty("perpetuum.home"));
	}

	/**
	 * Test method for 'org.codehaus.perpetuum.utils.PerpetuumUtil.isDebugOn()'
	 */
	public void testIsDebugOn() {
		System.setProperty("perpetuum.debug", "true");
		
		assertTrue("Should return true since perpetuum.debug is set to \"true\"!", PerpetuumUtil.isDebugOn());
		
		System.setProperty("perpetuum.debug", "false");
		
		assertFalse("Should return false since perpetuum.debug is set to something besides \"true\"!", PerpetuumUtil.isDebugOn());
	}
    
    /**
     * Test method for 'org.codehaus.perpetuum.utils.PerpetuumUtil.isVerboseOn()'
     */
    public void testIsVerboseOn() {
        System.setProperty("perpetuum.verbose", "true");
        
        assertTrue("Should return true since perpetuum.verbose is set to \"true\"!", PerpetuumUtil.isVerboseOn());
        
        System.setProperty("perpetuum.verbose", "false");
        
        assertFalse("Should return false since perpetuum.verbose is set to something besides \"true\"!", PerpetuumUtil.isVerboseOn());
    }
}
