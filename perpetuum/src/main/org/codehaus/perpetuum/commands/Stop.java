package org.codehaus.perpetuum.commands;

import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Stop extends AbstractCommand {
	private Log log = LogFactory.getLog(getClass());
	private ResourceBundle bundle = ResourceBundle.getBundle(Command.RESOURCE_PATH + getClass().getName().substring(getClass().getName().lastIndexOf(".") + 1).toLowerCase());

	/**
	 * @see org.codehaus.perpetuum.commands.AbstractCommand#run()
	 */
	public void run() {
		
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
