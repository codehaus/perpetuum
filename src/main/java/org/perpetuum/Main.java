package org.perpetuum;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.log4j.xml.DOMConfigurator;
import org.perpetuum.classpath.SystemClassPath;
import org.perpetuum.command.CommandFinder;

/**
 * Entry point for Perpetuum.  This class will parse the command line arguments 
 * then call the appropriate command based on the availability of the command.
 */
public class Main {
	private static CommandFinder finder = null;
	private static final String BASE_PATH = "META-INF/perpetuum/commands/";
	private static ResourceBundle pBundle = null;
	private static ResourceBundle cBundle = null;
	
	public static void init() {
		System.setProperty("perpetuum.commands.path", BASE_PATH);
		
		finder = new CommandFinder(BASE_PATH);
		pBundle = ResourceBundle.getBundle("perpetuum");
		
		setupClasspath();
	}
	
	public static void setupClasspath() {
		ClassLoader current = Thread.currentThread().getContextClassLoader();
		URL classURL = Thread.currentThread().getContextClassLoader().getResource(BASE_PATH + "start.properties");
        String propsString = classURL.getFile();
        URL jarURL = null;
        File jarFile = null;
        
        if (System.getProperty("perpetuum.home") == null) {
        	propsString = propsString.substring(0, propsString.indexOf("!"));
            
            try {
    			jarURL = new URL(propsString);
    		} catch (MalformedURLException e) {
    			e.printStackTrace();
    		}
            jarFile = new File(jarURL.getFile());
            
            if (jarFile.getName().equals(pBundle.getString("jar.name"))) {
            	File lib = jarFile.getParentFile();
            	File home = lib.getParentFile();
            	
            	System.setProperty("perpetuum.home", home.getAbsolutePath());
            }
        }
		
		File lib = new File(System.getProperty("perpetuum.home") + 
				File.separator + "lib");
		File ext = new File(System.getProperty("perpetuum.home") + 
				File.separator + "lib" + File.separator + "ext");
		SystemClassPath systemCP = new SystemClassPath();
		
		try {
			systemCP.addJarsToPath(lib);
			
			systemCP.addJarsToPath(ext);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public static void main(String[] args) {
		init();
		
		if (args.length > 0) {
			if (args[0].equals("--help")) {
				printAvailableCommands();
			}
			
			String mainClass = null;
			Class clazz = null;
			
			try {
				cBundle = finder.doFindCommandBundle(args[0]);
			} catch (MissingResourceException mre) {
				System.out.println(args[0] + pBundle.getString("invalid.command") + "\n");
				
				printAvailableCommands();
			}
			
			mainClass = cBundle.getString("main.class");
			
			try {
				clazz = Thread.currentThread().getContextClassLoader().loadClass(mainClass);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			Method mainMethod = null;
			
			try {
				mainMethod = clazz.getMethod("main", new Class[]{String[].class});
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
			
			String[] trimmedArgs = null;
			
			if (args.length > 1) {
				trimmedArgs = new String[args.length - 1];
				
				System.arraycopy(args, 1, trimmedArgs, 0, args.length - 1);
			} else {
				trimmedArgs = new String[0];
			}
			
			try {
				File log4j = new File(System.getProperty("perpetuum.home") + 
						File.separator + "conf" + File.separator + "log4j.conf");
				
				File logDir = new File(System.getProperty("perpetuum.home") + 
						File.separator + "logs");
				
				if (!logDir.exists()) {
					System.out.println(pBundle.getString("create.dir") + " " + logDir.getAbsolutePath());
					logDir.mkdirs();
				}
				
				System.setProperty("perpetuum.logs.home", logDir.getAbsolutePath());
				
				if (log4j.exists()) {
					DOMConfigurator.configure(log4j.getAbsolutePath());
				} else {
					URL lu = Main.class.getClassLoader().getResource("log4j.conf");
					
					if (lu != null) {
						DOMConfigurator.configure(lu);
					}
				}
				
				mainMethod.invoke(clazz, new Object[] { trimmedArgs });
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		} else {
			printAvailableCommands();
		}
	}
	
	private static void printAvailableCommands() {
		System.out.println(pBundle.getString("application.name") + " - " + pBundle.getString("application.version"));
		System.out.println(pBundle.getString("usage.header.0"));
		System.out.println(pBundle.getString("usage.header.1") + "\n");
		System.out.println(pBundle.getString("usage.header.2"));
		
		try {
			Enumeration commandHomes = finder.doFindCommands();
			
			if (commandHomes != null) {
				for (; commandHomes.hasMoreElements(); ) {
					URL cHomeURL = (URL)commandHomes.nextElement();
					JarURLConnection conn = (JarURLConnection)cHomeURL.openConnection();
			        JarFile jarfile = conn.getJarFile();
			        Enumeration commands = jarfile.entries();
			        
			        if (commands != null) {
			        	while (commands.hasMoreElements()) {
			        		JarEntry je = (JarEntry)commands.nextElement();
				        	
				        	if (je.getName().indexOf(BASE_PATH) > -1 && !je.getName().equals(BASE_PATH)) {
				        		String command = je.getName().substring(je.getName().lastIndexOf("/") + 1);
				        		ResourceBundle bundle = finder.doFindCommandBundle(command.substring(0, command.indexOf(".")));
				        		 
								System.out.println("\n  " + bundle.getString("name") + " - " + bundle.getString("description"));
				        	}
			        	}
			        }
				}
			} else {
				System.out.println(pBundle.getString("no.commands"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.exit(0);
	}
}