package org.perpetuum.command;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

import junit.framework.TestCase;

import org.perpetuum.Main;

public class CommandFinderTest extends TestCase {

	private CommandFinder cf;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		cf = new CommandFinder(Main.COMMANDS_PATH);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/*
	 * Test method for 'org.perpetuum.command.CommandFinder.doFindCommandProperies(String)'
	 */
	public void testDoFindCommandProperies() {
		assertNotNull("CommandFinder should always find the start command.", cf.doFindCommandProperies("start"));
		assertNotNull("CommandFinder should always find the stop command.", cf.doFindCommandProperies("stop"));
		assertNull("CommandFinder should never find the start0 command.", cf.doFindCommandProperies("start0"));
	}

	/*
	 * Test method for 'org.perpetuum.command.CommandFinder.doFindCommandBundle(String)'
	 */
	public void testDoFindCommandBundle() {
		assertNotNull("CommandFinder should always find the start command.", cf.doFindCommandBundle("start"));
		assertNotNull("CommandFinder should always find the stop command.", cf.doFindCommandBundle("stop"));
		assertNull("CommandFinder should never find the start0 command.", cf.doFindCommandBundle("start0"));
	}

	/*
	 * Test method for 'org.perpetuum.command.CommandFinder.doFindCommands()'
	 */
	public void testDoFindCommands() {
		int commands = 0;
		Enumeration es = null;
		
		try {
			es = cf.doFindCommands();
			
			while (es.hasMoreElements()) {
				URL u = (URL)es.nextElement();
				File dir = new File(u.getFile());
				
				commands = dir.list().length;
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		assertEquals("CommandFinder should return 2 commands.", 2, commands);
	}
}
