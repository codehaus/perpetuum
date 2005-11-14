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
import org.perpetuum.core.management.PerpetuumAgent;
import org.perpetuum.core.management.PerpetuumCore;

/**
 * Entry point to start the Perpetuum Server
 */
public class Start {
	private static Options options = new Options();
	private static ResourceBundle bundle;
	private static PerpetuumAgent jmxServer;
	private static Log log = null;
	private static boolean help = false;
	
	private Start() {
		log = LogFactory.getLog(Start.class);
		
		CommandFinder finder = new CommandFinder(System.getProperty("perpetuum.commands.path"));
		
		bundle = finder.doFindCommandBundle("start");
		
		createOptions();
	}
	
	private void createOptions() {
		Option httpport = new Option("h", "httpPort", true, bundle.getString("options.httpport.description"));
		Option rmiport = new Option("r", "rmiPort", true, bundle.getString("options.rmiport.description"));
		Option help = new Option("help", "help", false, bundle.getString("options.help.description"));
		
		httpport.setArgName("httpport");
		httpport.setValueSeparator(' ');
		rmiport.setArgName("rmiport");
		rmiport.setValueSeparator(' ');
		
		options.addOption(httpport);
		options.addOption(rmiport);
		options.addOption(help);
	}
	
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
			
			log.info(bundle.getString("start.header"));
			
			try {
				httpPort = Integer.parseInt(line.getOptionValue("h", bundle.getString("default.httpport")));
			} catch (NumberFormatException nfe) {
				log.warn(MessageFormat.format(bundle.getString("invalid.port"), new Object[] {line.getOptionValue("h"), "JMX HTTP", bundle.getString("default.httpport")}));
			}
			
			try {
				rmiPort = Integer.parseInt(line.getOptionValue("r", bundle.getString("default.rmiport")));
			} catch (NumberFormatException nfe) {
				log.warn(MessageFormat.format(bundle.getString("invalid.port"), new Object[] {line.getOptionValue("r"), "JMX RMI", bundle.getString("default.rmiport")}));
			}
			
			PerpetuumCore pc = new PerpetuumCore();
			
			pc.setHttpPort(httpPort);
			pc.setRmiPort(rmiPort);
			
			pc.start();
		}
	}
	
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
