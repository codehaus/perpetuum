package org.codehaus.perpetuum.commands;

import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.perpetuum.utils.PerpetuumUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Command used to start Perpetuum
 */
public class Start extends AbstractCommand {
	private Log log = LogFactory.getLog(getClass());
	private ResourceBundle bundle = ResourceBundle.getBundle(Command.RESOURCE_PATH + getClass().getName().substring(getClass().getName().lastIndexOf(".") + 1).toLowerCase());
	private ApplicationContext factory;
    
	/**
	 * @see org.codehaus.perpetuum.commands.AbstractCommand#run()
	 */
	public void run() {
        String configPath = "springStart.xml";
        
		factory = new ClassPathXmlApplicationContext(configPath);
        
        PerpetuumUtil.createAdminUser(factory);
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
