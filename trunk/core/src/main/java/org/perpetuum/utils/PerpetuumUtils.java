package org.perpetuum.utils;

import org.perpetuum.Main;
import org.perpetuum.classpath.ClasspathUtils;

public class PerpetuumUtils {
	private static PerpetuumUtils pu;
	
	private PerpetuumUtils() {};
	
	public static PerpetuumUtils getInstance() {
		if (pu == null) {
			pu = new PerpetuumUtils();
		}
		
		return pu;
	}
	
	public void prepareSystem() {
		ClasspathUtils.getInstance().setupClasspath();
		
		if (System.getProperty("perpetuum.commands.path") == null) {
			System.setProperty("perpetuum.commands.path", Main.COMMANDS_PATH);
		}
		
		if (System.getProperty("perpetuum.services.path") == null) {
			System.setProperty("perpetuum.services.path", Main.SERVICES_PATH);
		}
	}
}
