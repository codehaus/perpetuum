package org.perpetuum.core.services;

import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.perpetuum.command.CommandFinder;

public class AbstractService implements Service {
	public Log log = null;
	public ResourceBundle startBundle = null;
	public ResourceBundle stopBundle = null;
	
	public void prepare(String name) {
		log = LogFactory.getLog(SchedulerService.class);
		
		CommandFinder finder = new CommandFinder(System
				.getProperty("perpetuum.commands.path"));

		startBundle = finder.doFindCommandBundle("start");
		stopBundle = finder.doFindCommandBundle("stop");
		
		ServiceRegistry.getDefault().register(name, this);
	}

	public void start() throws Exception {
		
	}

	public void stop() {
		
	}
}
