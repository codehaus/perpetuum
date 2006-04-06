package org.codehaus.perpetuum.utils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.xml.DOMConfigurator;
import org.codehaus.perpetuum.classpath.SystemClassPath;
import org.codehaus.perpetuum.commands.Command;

public class PerpetuumUtil {
	
	/**
	 * Sets up Perpetuum by configuring the classpath and logging
	 */
	public static void setup() {
		setupClasspath();
		setupLogging();
		setupDatabase();
	}
	
	/**
	 * Used to set some Derby properties
	 *
	 */
	public static void setupDatabase() {
		System.setProperty("derby.system.home", 
			System.getProperty("perpetuum.home") + File.separator + "data");
		
		File derbyHome = new File(System.getProperty("perpetuum.home") + File.separator + "data");
		
		if (!derbyHome.exists()) {
			derbyHome.mkdirs();
		}
		
        System.setProperty("derby.stream.error.file", 
        	System.getProperty("perpetuum.logs.home") + File.separator + "derby.log");
	}
	
	/**
	 * Configures logging
	 */
	public static void setupLogging() {
		File log4j = new File(System.getProperty("perpetuum.home") + 
				File.separator + "conf" + File.separator + "log4j.xml");
		
		File logDir = new File(System.getProperty("perpetuum.home") + 
				File.separator + "logs");
		
		if (!logDir.exists()) {
			System.out.println(ResourceBundle.getBundle("perpetuum").
				getString("perpetuum.action.create.dir") + " " + 
				logDir.getAbsolutePath());
			logDir.mkdirs();
		}
		
		System.setProperty("perpetuum.logs.home", logDir.getAbsolutePath());
		
		if (log4j.exists()) {
			DOMConfigurator.configure(log4j.getAbsolutePath());
		} else {
			URL lu = PerpetuumUtil.class.getClassLoader().getResource("log4j.xml");
			
			if (lu != null) {
				DOMConfigurator.configure(lu);
			}
		}
	}

	/**
	 * Loads all jars in perpetuum.home/lib and perpetuum.home/lib/ext onto 
	 * the classpath
	 */
	public static void setupClasspath() {
		ClassLoader current = Thread.currentThread().getContextClassLoader();
		URL classURL = Thread.currentThread().getContextClassLoader().getResource(Command.PATH + "start.properties");
        String propsString = classURL.getFile();
        URL jarURL = null;
        File jarFile = null;
        
        if (System.getProperty("perpetuum.home") == null) {
            if (propsString.indexOf("!") > -1) {
                propsString = propsString.substring(0, propsString.indexOf("!"));
                
                try {
                    jarURL = new URL(propsString);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                jarFile = new File(jarURL.getFile());
                
                if (jarFile.getName().equals(ResourceBundle.getBundle("perpetuum").getString("jar.name"))) {
                    File lib = jarFile.getParentFile();
                    File home = lib.getParentFile();
                    
                    System.setProperty("perpetuum.home", home.getAbsolutePath());
                }
            } else {
                System.setProperty("perpetuum.home", System.getProperty("user.dir"));
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
	
	public static boolean isDebugOn() {
		boolean debug = false;
		String debugAsString = System.getProperty("perpetuum.debug");
		
		if (debugAsString != null && debugAsString.equalsIgnoreCase("true")) {
			debug = true;
		}
		
		return debug;
	}
	
	public static boolean isVerboseOn() {
		boolean verbose = false;
		String verboseAsString = System.getProperty("perpetuum.verbose");
		
		if (verboseAsString != null && verboseAsString.equalsIgnoreCase("true")) {
			verbose = true;
		}
		
		return verbose;
	}
}
