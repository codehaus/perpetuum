package org.perpetuum.core.services;

import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.perpetuum.command.CommandFinder;

public abstract class AbstractService implements Service {
	public Log log = null;
	public ResourceBundle bundle = null;
	
	public void prepare(String name) {
		log = LogFactory.getLog(SchedulerService.class);
		
		CommandFinder finder = new CommandFinder(System
				.getProperty("perpetuum.services.path"));

		bundle = finder.doFindCommandBundle(name.toLowerCase());
		
		ServiceRegistry.getDefault().register(name, this);
	}

	public abstract void start() throws Exception;

	public abstract void stop();
}
