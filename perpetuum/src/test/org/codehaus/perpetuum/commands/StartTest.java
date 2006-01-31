package org.codehaus.perpetuum.commands;

import junit.framework.TestCase;

public class StartTest extends TestCase {
	private Start s;
	
	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		s = new Start();
	}

	/*
	 * Test method for 'org.codehaus.perpetuum.commands.Start.run()'
	 */
	public void testRun() {
		try {
			s.run(new String[] { "--help" });
		} catch (Exception e) {
			fail("An exception should never be thrown from Start.run()!");
		}
	}

	/*
	 * Test method for 'org.codehaus.perpetuum.commands.Start.getLog()'
	 */
	public void testGetLog() {
		assertNotNull("Start.getLog() should not return null!", s.getLog());
	}

	/*
	 * Test method for 'org.codehaus.perpetuum.commands.Start.getBundle()'
	 */
	public void testGetBundle() {
		assertNotNull("Start.getBundle() should not return null!", s.getBundle());
	}

	/*
	 * Test method for 'org.codehaus.perpetuum.commands.AbstractCommand.ranHelp()'
	 */
	public void testRanHelp() {
		assertFalse("Start.ranHelp() should return false unless Start.printHelp() was ran!", s.ranHelp());
		
		s.printHelp();
		
		assertTrue("Start.ranHelp() should return true after Start.printHelp() was ran!", s.ranHelp());
	}

	/*
	 * Test method for 'org.codehaus.perpetuum.commands.AbstractCommand.parseArguments(String[])'
	 */
	public void testParseArguments() {
		try {
			s.parseArguments(new String[0]);
		} catch (Exception e) {
			fail("An exception should never be thrown from Start.parseArguments(String[])!");
		}
	}

	/*
	 * Test method for 'org.codehaus.perpetuum.commands.AbstractCommand.printHelp()'
	 */
	public void testPrintHelp() {
		try {
			s.printHelp();
		} catch (Exception e) {
			fail("An exception should never be thrown from Start.printHelp()!");
		}
	}
}
