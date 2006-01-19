package org.perpetuum.utils;

import org.perpetuum.Main;
import org.perpetuum.classpath.ClasspathUtils;

/**
 * Utility method that provides methods useful to many classes.
 */
public class PerpetuumUtils {
	private static PerpetuumUtils pu;
	
	/**
	 * Constructor (singleton)
	 */
	private PerpetuumUtils() {};
	
	/**
	 * Gets singleton instance
	 * @return singleton instance
	 */
	public static PerpetuumUtils getInstance() {
		if (pu == null) {
			pu = new PerpetuumUtils();
		}
		
		return pu;
	}
	
	/**
	 * Sets up the classpath and necessary system properties
	 */
	public void prepareSystem() {
		if (System.getProperty("perpetuum.commands.path") == null) {
			System.setProperty("perpetuum.commands.path", Main.COMMANDS_PATH);
		}
		
		if (System.getProperty("perpetuum.services.path") == null) {
			System.setProperty("perpetuum.services.path", Main.SERVICES_PATH);
		}
		
		ClasspathUtils.getInstance().setupClasspath();
	}
}
