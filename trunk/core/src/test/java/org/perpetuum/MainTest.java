package org.perpetuum;

import java.lang.reflect.Method;

import junit.framework.TestCase;

public class MainTest extends TestCase {
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/*
	 * Test method for 'org.perpetuum.Main.init()'
	 */
	public void testInit() {
		Main.init();
		
		assertEquals("System property \"perpetuum.commands.path\" should be \"" + Main.COMMANDS_PATH + "\".", System.getProperty("perpetuum.commands.path"), Main.COMMANDS_PATH);
		assertEquals("System property \"perpetuum.services.path\" should be \"" + Main.SERVICES_PATH + "\".", System.getProperty("perpetuum.services.path"), Main.SERVICES_PATH);
	}

	/*
	 * Test method for 'org.perpetuum.Main.main(String[])'
	 */
	public void testMain() {
		try {
			Class clazz = Thread.currentThread().getContextClassLoader().loadClass("org.perpetuum.Main");
			Method m = clazz.getMethod("main", new Class[] { String[].class });
			
			m.invoke(clazz, new Object[] { new String[0] });
		} catch (Exception e) {
			fail("Main.main(String[]) should never throw an exception.");
		}
	}

}
