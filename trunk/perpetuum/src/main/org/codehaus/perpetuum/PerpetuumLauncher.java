package org.codehaus.perpetuum;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.codehaus.perpetuum.commands.Command;
import org.codehaus.perpetuum.utils.PerpetuumUtil;

/**
 * PerpetuumLauncher is the class responsible for launching Perpetuum and all 
 * accompanying tools.
 */
public class PerpetuumLauncher {
	private ResourceBundle bundle;
	
	/**
	 * Public constructor
	 */
	public PerpetuumLauncher() {
		try {
			bundle = ResourceBundle.getBundle("perpetuum");
			
			PerpetuumUtil.setup();
			
			
		} catch (Exception e) {
			// Should never happen but just in case...
			
			System.out.println("Unable to find the Perpetuum ResourceBundle");
			
			System.exit(1);
		}
	}
	
	/**
	 * Returns the ResourceBundle object
	 * @return bundle
	 */
	public ResourceBundle getBundle() {
		return bundle;
	}

	/**
	 * Entry point for PerpetuumLauncher
	 * @param args Arguments usually passed on the command line
	 */
	public static void main(String[] args) {
		PerpetuumLauncher pl = new PerpetuumLauncher();

		if (args.length == 0 || args[0].equals("--help") || args[0].equals("help")) {
		        pl.printHelp();

		        return;
		}

		String[] trimmedArgs = null;

		if (args.length > 1) {
		        trimmedArgs = new String[args.length - 1];

		        System.arraycopy(args, 1, trimmedArgs, 0, args.length - 1);
		} else {
		        trimmedArgs = new String[0];
		}

		pl.runCommand(args);
	}
	
	/**
	 * Runs the proper Command one the command has been validated
	 * @param args Arguments to pass to the Command
	 */
	private void runCommand(String[] args) {
		ResourceBundle cb = null;
		Command command = null;
		
		try {
			cb = ResourceBundle.getBundle(Command.RESOURCE_PATH + args[0]);
		} catch (MissingResourceException mre) {
			System.out.println(args[0] + " " + bundle.getString("perpetuum.warning.invalid.command") + "\n");
			
			printHelp();
		}
		
		try {
			command = (Command)Thread.currentThread().getContextClassLoader().loadClass(cb.getString("command.class")).newInstance();
		} catch (Exception e) {
			// Should never happen
			e.printStackTrace();
			
			System.exit(1);
		}
		
		try {
			String[] trimmedArgs = null;
			
			if (args.length > 1) {
				trimmedArgs = new String[args.length - 1];
				
				System.arraycopy(args, 1, trimmedArgs, 0, args.length - 1);
			} else {
				trimmedArgs = new String[0];
			}
			
			command.run(trimmedArgs);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Used in conjunction with prinHelp to print the available commands
	 */
	private void printCommands() {
		try {
			Enumeration commandHomes = getClass().getClassLoader().getResources(Command.PATH);
			
			if (commandHomes != null && commandHomes.hasMoreElements()) {
				while (commandHomes.hasMoreElements()) {
					URL cHomeURL = (URL)commandHomes.nextElement();
					File cHomeFile = new File(cHomeURL.getFile());
                    
                     if (cHomeFile.isDirectory()) {
                         File[] commands = cHomeFile.listFiles();
                         
                         for (int i = 0; i < commands.length; i++) {
                             File cC = commands[i];
                             
                             if (cC.isFile() && cC.getName().endsWith(".properties")) {
                                 String command = cC.getName();
                                 ResourceBundle bundle = ResourceBundle.getBundle(Command.RESOURCE_PATH + command.substring(0, command.indexOf("."))); 
                                 System.out.println("\n  " + bundle.getString("command.name") + " - " + bundle.getString("command.description"));
                             }
                         }
                     } else {
                         JarURLConnection conn = (JarURLConnection)cHomeURL.openConnection();
                        JarFile jarfile = conn.getJarFile();
                        Enumeration commands = jarfile.entries();
                        
                        if (commands != null) {
                            while (commands.hasMoreElements()) {
                                JarEntry je = (JarEntry)commands.nextElement();
                                
                                if (je.getName().indexOf(Command.PATH) > -1 && !je.getName().equals(Command.PATH)) {
                                    String command = je.getName().substring(je.getName().lastIndexOf("/") + 1);
                                    ResourceBundle bundle = ResourceBundle.getBundle(Command.RESOURCE_PATH + command.substring(0, command.indexOf("."))); 
                                    System.out.println("\n  " + bundle.getString("command.name") + " - " + bundle.getString("command.description"));
                                }
                            }
                        }
                     }
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Prints the available Commands found on the classpath
	 */
	private void printHelp() {
		System.out.println(bundle.getString("perpetuum.name") + " [" + bundle.getString("perpetuum.version") + "]");
		System.out.println(bundle.getString("perpetuum.help.header.0"));
		System.out.println(bundle.getString("perpetuum.help.header.1"));
		System.out.println(bundle.getString("perpetuum.help.header.2"));
		
		printCommands();
		
		System.out.println("\n" + bundle.getString("perpetuum.help.footer.0"));
		System.out.println(bundle.getString("perpetuum.help.footer.1"));
	}
}
