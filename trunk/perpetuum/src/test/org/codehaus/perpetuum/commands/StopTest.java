package org.codehaus.perpetuum.commands;

import junit.framework.TestCase;

public class StopTest extends TestCase {
private Stop s;
	
	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		s = new Stop();
	}

	/*
	 * Test method for 'org.codehaus.perpetuum.commands.Stop.run()'
	 */
	public void testRun() {
		try {
			s.run(new String[0]);
		} catch (Exception e) {
			fail("An exception should never be thrown from Stop.run()!");
		}
	}

	/*
	 * Test method for 'org.codehaus.perpetuum.commands.Stop.getLog()'
	 */
	public void testGetLog() {
		assertNotNull("Stop.getLog() should not return null!", s.getLog());
	}

	/*
	 * Test method for 'org.codehaus.perpetuum.commands.Stop.getBundle()'
	 */
	public void testGetBundle() {
		assertNotNull("Stop.getBundle() should not return null!", s.getBundle());
	}

	/*
	 * Test method for 'org.codehaus.perpetuum.commands.AbstractCommand.ranHelp()'
	 */
	public void testRanHelp() {
		assertFalse("Stop.ranHelp() should return false unless Stop.printHelp() was ran!", s.ranHelp());
		
		s.printHelp();
		
		assertTrue("Stop.ranHelp() should return true after Stop.printHelp() was ran!", s.ranHelp());
	}

	/*
	 * Test method for 'org.codehaus.perpetuum.commands.AbstractCommand.parseArguments(String[])'
	 */
	public void testParseArguments() {
		try {
			s.parseArguments(new String[0]);
		} catch (Exception e) {
			fail("An exception should never be thrown from Stop.parseArguments(String[])!");
		}
	}

	/*
	 * Test method for 'org.codehaus.perpetuum.commands.AbstractCommand.printHelp()'
	 */
	public void testPrintHelp() {
		try {
			s.printHelp();
		} catch (Exception e) {
			fail("An exception should never be thrown from Stop.printHelp()!");
		}
	}
}
