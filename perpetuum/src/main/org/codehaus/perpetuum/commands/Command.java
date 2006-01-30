package org.codehaus.perpetuum.commands;

/**
 * Interface implemented by all Perpetuum Commands
 */
public interface Command {
	public static final String PATH = "commands/";
	public static final String RESOURCE_PATH = "commands.";
	
	/**
	 * Runs the Command
	 */
	public void run(String[] args);
	
	/**
	 * Parses the command line arguments and prints help if needed
	 */
	public void parseArguments(String[] args);
	
	/**
	 * Prints help for this Command
	 */
	public void printHelp();
}
