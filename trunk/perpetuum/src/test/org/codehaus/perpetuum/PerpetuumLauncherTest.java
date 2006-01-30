package org.codehaus.perpetuum;

import junit.framework.TestCase;

public class PerpetuumLauncherTest extends TestCase {
	private PerpetuumLauncher pl;
	
	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		pl = new PerpetuumLauncher();
	}

	/**
	 * Test method for 'org.codehaus.perpetuum.PerpetuumLauncher.PerpetuumLauncher()'
	 */
	public void testPerpetuumLauncher() {
		assertNotNull("PerpetuumLauncher.getBundle() should not return null!", pl.getBundle());
	}

	/**
	 * Test method for 'org.codehaus.perpetuum.PerpetuumLauncher.main(String[])'
	 */
	public void testMain() {
		try {
			PerpetuumLauncher.main(new String[0]);
			
			PerpetuumLauncher.main(new String[] { "start", "--help" });
		} catch (Exception e) {
			fail("No exception should ever be thrown from PerpetuumLauncher.main(String[])!");
		}
	}
}
