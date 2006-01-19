package org.perpetuum.core.services;

import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.perpetuum.command.CommandFinder;

public abstract class AbstractService implements Service {
	public Log log = null;
	public ResourceBundle bundle = null;
	public String status = Service.STOPPED;
	public static String NAME;
	
	/**
	 * Prepares each Service
	 */
	public void prepare(Class clazz) {
		NAME = clazz.getName().substring(clazz.getName().lastIndexOf(".") + 1);
		
		log = LogFactory.getLog(clazz);
		
		CommandFinder finder = new CommandFinder(System.getProperty("perpetuum.services.path"));

		bundle = finder.doFindCommandBundle(NAME.toLowerCase());
		
		ServiceRegistry.getDefault().register(NAME, this);
	}
	
	/**
	 * Initializes the service
	 */
	public abstract void init() throws Exception;

	/**
	 * Starts the service
	 */
	public abstract void start() throws Exception;

	/**
	 * Stops the service
	 */
	public abstract void stop();
}
