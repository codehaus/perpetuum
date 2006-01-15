package org.perpetuum.classpath;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import org.perpetuum.Main;

public class ClasspathUtils {
	private static ClasspathUtils cpu;
	private ResourceBundle pBundle;
	
	private ClasspathUtils() {
		pBundle = ResourceBundle.getBundle("perpetuum");
	}
	
	public static ClasspathUtils getInstance() {
		if (cpu == null) {
			cpu = new ClasspathUtils();
		}
		
		return cpu;
	}
	
	public void setupClasspath() {
		ClassLoader current = Thread.currentThread().getContextClassLoader();
		URL classURL = Thread.currentThread().getContextClassLoader().getResource(Main.COMMANDS_PATH + "start.properties");
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
                
                if (jarFile.getName().equals(pBundle.getString("jar.name"))) {
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
}
