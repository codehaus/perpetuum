package org.perpetuum.core;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

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

/**
 * Entry point to stop the Perpetuum Server
 */
public class Stop {
	private static Options options = new Options();
	private static ResourceBundle bundle;
	private static Log log = null;
	
	public Stop() {
		log = LogFactory.getLog(Stop.class);
		
		CommandFinder finder = new CommandFinder(System.getProperty("perpetuum.commands.path"));
		
		bundle = finder.doFindCommandBundle("stop");
		
		createOptions();
	}
	
	private void createOptions() {
		Option help = new Option("help", "help", false, bundle.getString("options.help.description"));
		Option rmiport = new Option("r", "rmiPort", true, bundle.getString("options.rmiport.description"));
		
		rmiport.setArgName("rmiport");
		rmiport.setValueSeparator(' ');
		
		options.addOption(rmiport);
		options.addOption(help);
	}

	public static void main(String[] args) {
		Stop s = new Stop();
		CommandLine line = null;
		int adminPort = 0;
		
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
			
			int rmiPort = 0;
			
			try {
				rmiPort = Integer.parseInt(line.getOptionValue("r", bundle.getString("default.rmiport")));
			} catch (NumberFormatException nfe) {
				log.warn(MessageFormat.format(bundle.getString("invalid.port"), new Object[] {line.getOptionValue("r"), "JMX RMI", bundle.getString("default.rmiport")}));
			}
			
			try {
				JMXServiceURL jmxServiceUrl = new JMXServiceURL("service:jmx:rmi://localhost/jndi/rmi://localhost:" + rmiPort + "/" + PerpetuumAgent.RMI_JNDI_NAME);
				JMXConnector cntor = JMXConnectorFactory.connect(jmxServiceUrl, null);
				MBeanServerConnection mbsc = cntor.getMBeanServerConnection();
				ObjectName perpetuumCore = new ObjectName(PerpetuumAgent.DOMAIN_NAME + ":type=PerpetuumCore");
				
				mbsc.invoke(perpetuumCore, "stop", null, null);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
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
		
		System.exit(1);
	}
}
