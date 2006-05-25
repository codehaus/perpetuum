package org.codehaus.perpetuum.utils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.xml.DOMConfigurator;
import org.codehaus.perpetuum.classpath.SystemClassPath;
import org.codehaus.perpetuum.commands.Command;
import org.codehaus.perpetuum.model.User;
import org.codehaus.perpetuum.persistence.UserDAO;
import org.springframework.context.ApplicationContext;

/**
 * This is a utility class for performing mundane tasks
 */
public class PerpetuumUtil {
	
	/**
	 * Sets up Perpetuum by configuring the classpath and logging
	 */
	public static void setup() {
		setupClasspath();
		setupLogging();
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
                
                if (jarFile.getName().equals(ResourceBundle.getBundle("perpetuum").getString("perpetuum.jar.name") + ".jar")) {
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
            
			systemCP.addPathToPath(new File(System.getProperty("perpetuum.home") + File.separator + "conf"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    /**
     * Tests for whether or not Perpetuum is in debug mode or not
     */
	public static boolean isDebugOn() {
		boolean debug = false;
		String debugAsString = System.getProperty("perpetuum.debug");
		
		if (debugAsString != null && debugAsString.equalsIgnoreCase("true")) {
			debug = true;
		}
		
		return debug;
	}
	
    /**
     * Tests for whether or not Perpetuum is in verbose mode or not
     */
	public static boolean isVerboseOn() {
		boolean verbose = false;
		String verboseAsString = System.getProperty("perpetuum.verbose");
		
		if (verboseAsString != null && verboseAsString.equalsIgnoreCase("true")) {
			verbose = true;
		}
		
		return verbose;
	}
    
    /**
     * Creats the default Administrator user for Perpetuum
     */
    public static void createAdminUser(ApplicationContext factory) {
        UserDAO ud = (UserDAO)factory.getBean("userDAO");
        
        if (ud.getCount() < 1) {
            User admin = new User();
            
            admin.setUsername("admin");
            admin.setPassword("perpetuum");
            admin.setEmail("invalid@perpetuum.codehaus.org");
            admin.setRealname("Perpetuum Administrator");
            admin.setEnabled(true);
            
            ud.add(admin);
        }
    }
}
