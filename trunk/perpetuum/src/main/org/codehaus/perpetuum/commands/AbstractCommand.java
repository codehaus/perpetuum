package org.codehaus.perpetuum.commands;

import java.util.ResourceBundle;

import org.apache.commons.logging.Log;

public abstract class AbstractCommand implements Command {
	public boolean help = false;
	private ResourceBundle pBundle = ResourceBundle.getBundle("perpetuum");
	
	/**
	 *  Used to get a reference to the Perpetuum ResourceBundle
	 */
	public ResourceBundle getPBundle() {
		return pBundle;
	}
	
	/**
	 * Used to get a reference to the Log object
	 * @return log
	 */
	public abstract Log getLog();
	
	/**
	 * Used to get a reference to the ResourceBundle object
	 * @return bundle
	 */
	public abstract ResourceBundle getBundle();
	
	/**
	 * Used to run the command if help was not ran
	 */
	public abstract void run();
	
	/**
	 * Returns whether or not printHelp() was ran
	 * @return help
	 */
	public boolean ranHelp() {
		return help;
	}
	
	/**
	 * @see org.codehaus.perpetuum.commands.Command#run(java.lang.String[])
	 */
	public void run(String args[]) {
		parseArguments(args);
		
		if (!ranHelp()) {
			getLog().info(getBundle().getString("command.info"));
			
			run();
		}
	}
	
	/**
	 * @see org.codehaus.perpetuum.commands.Command#parseArguments(java.lang.String[])
	 */
	public void parseArguments(String[] args) {
		if (args.length > 0 && (args[0].equals("help") || args[0].equals("--help"))) {
			printHelp();	
		}	
	}
	
	/**
	 * @see org.codehaus.perpetuum.commands.Command#printHelp()
	 */
	public void printHelp() {
		ResourceBundle bundle = ResourceBundle.getBundle("perpetuum");
		System.out.println(bundle.getString("perpetuum.name") + " [" + bundle.getString("perpetuum.version") + "]");
		System.out.println(getBundle().getString("command.help.header.0"));
		System.out.println(getBundle().getString("command.help.header.1"));
		System.out.println(getBundle().getString("command.help.description") + " " + getBundle().getString("command.description"));
		System.out.println(getBundle().getString("command.help.footer.0"));
		System.out.println(getBundle().getString("command.help.footer.1"));
		
		help = true;
	}
}
