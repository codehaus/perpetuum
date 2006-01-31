package org.codehaus.perpetuum.commands;

import java.io.File;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Start extends AbstractCommand {
	private Log log = LogFactory.getLog(getClass());
	private ResourceBundle bundle = ResourceBundle.getBundle(Command.RESOURCE_PATH + getClass().getName().substring(getClass().getName().lastIndexOf(".") + 1).toLowerCase());
	
	/**
	 * @see org.codehaus.perpetuum.commands.AbstractCommand#run()
	 */
	public void run() {
        String configPath = System.getProperty("perpetuum.home") + File.separator + "conf" + File.separator + "springStart.xml";
        
        if (!new File(configPath).exists()) {
            configPath = "springStart.xml";
        }
        
		ApplicationContext factory = new FileSystemXmlApplicationContext(configPath);
	}
	
	/**
	 * @see org.codehaus.perpetuum.commands.AbstractCommand#getLog()
	 */
	public Log getLog() {
		return log;
	}
	
	/**
	 * @see org.codehaus.perpetuum.commands.AbstractCommand#getBundle()
	 */
	public ResourceBundle getBundle() {
		return bundle;
	}
}
