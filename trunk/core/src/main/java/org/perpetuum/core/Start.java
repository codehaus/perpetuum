package org.perpetuum.core;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.perpetuum.command.CommandFinder;
import org.perpetuum.core.management.PerpetuumCore;
import org.perpetuum.core.services.JMXService;

/**
 * Entry point to start the Perpetuum Server
 */
public class Start {
	private static Options options = new Options();
	private static ResourceBundle bundle;
	private static ResourceBundle sBundle;
	private static JMXService jmxServer;
	private static Log log = null;
	private static boolean help = false;
	private static CommandFinder finder = null;
	private static CommandFinder sFinder = null;
	
	/**
	 * Constructor
	 */
	private Start() {
		log = LogFactory.getLog(Start.class);
		
		finder = new CommandFinder(System.getProperty("perpetuum.commands.path"));
		sFinder = new CommandFinder(System.getProperty("perpetuum.services.path"));
		
		bundle = finder.doFindCommandBundle("start");
		sBundle = sFinder.doFindCommandBundle("jmxservice");
		
		createOptions();
	}
	
	/**
	 * Creates the command line arguments and options for this command
	 */
	private void createOptions() {
		Option httpport = new Option("h", "httpPort", true, bundle.getString("options.httpport.description"));
		Option rmiport = new Option("r", "rmiPort", true, bundle.getString("options.rmiport.description"));
		Option webport = new Option("w", "webPort", true, bundle.getString("options.webport.description"));
		Option help = new Option("help", "help", false, bundle.getString("options.help.description"));
		
		httpport.setArgName("httpport");
		httpport.setValueSeparator(' ');
		rmiport.setArgName("rmiport");
		rmiport.setValueSeparator(' ');
		webport.setArgName("webport");
		webport.setValueSeparator(' ');
		
		options.addOption(httpport);
		options.addOption(rmiport);
		options.addOption(webport);
		options.addOption(help);
	}
	
	/**
	 * Entry point called by org.perpetuum.Main
	 * @param args Arguments passed from org.perpetuum.Main
	 */
	public static void main(String[] args) {
		Start s = new Start();
		CommandLine line = null;
		
		if (args.length > 0 && (args[0].equals("help") || args[0].equals("--help"))) {
			printHelp();
		} else {
			CommandLineParser parser = new PosixParser();
			try {
				line = parser.parse(options, args);
			} catch (ParseException e) {
				e.printStackTrace();
				log.error(e.getMessage());
				
				printHelp();
			}
			
			int httpPort = 0;
			int rmiPort = 0;
			int webPort = 0;
			
			log.info(bundle.getString("start.header"));
			
			try {
				httpPort = Integer.parseInt(line.getOptionValue("h", sBundle.getString("default.httpport")));
			} catch (NumberFormatException nfe) {
				log.warn(MessageFormat.format(bundle.getString("invalid.port"), new Object[] {line.getOptionValue("h"), "JMX HTTP", sBundle.getString("default.httpport")}));
			}
			
			try {
				rmiPort = Integer.parseInt(line.getOptionValue("r", sBundle.getString("default.rmiport")));
			} catch (NumberFormatException nfe) {
				log.warn(MessageFormat.format(bundle.getString("invalid.port"), new Object[] {line.getOptionValue("r"), "JMX RMI", sBundle.getString("default.rmiport")}));
			}
			
			sBundle = sFinder.doFindCommandBundle("webservice");
			
			try {
				webPort = Integer.parseInt(line.getOptionValue("w", sBundle.getString("default.webport")));
			} catch (NumberFormatException nfe) {
				log.warn(MessageFormat.format(bundle.getString("invalid.port"), new Object[] {line.getOptionValue("w"), "Web Server", sBundle.getString("default.webport")}));
			}
			
			PerpetuumCore pc = new PerpetuumCore();
			
			pc.setHttpPort(httpPort);
			pc.setRmiPort(rmiPort);
			pc.setWebPort(webPort);
			
			pc.start();
		}
	}
	
	/**
	 * Prints usage and help for this command
	 */
	private static void printHelp() {
		HelpFormatter formatter = new HelpFormatter();
		
		formatter.defaultLongOptPrefix = "  " + HelpFormatter.DEFAULT_LONG_OPT_PREFIX;
		formatter.printHelp(formatter.defaultWidth
						  , bundle.getString("command.line.syntax")
						  , bundle.getString("help.header")
						  , options
						  , bundle.getString("help.footer")
						  , true);
		
		help = true;
		
		System.exit(1);
	}
}
