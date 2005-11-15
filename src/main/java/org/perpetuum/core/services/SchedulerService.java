package org.perpetuum.core.services;

import java.util.ResourceBundle;

import mx4j.log.Log4JLogger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.perpetuum.command.CommandFinder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

public class SchedulerService implements Service {
	public static final String NAME = "SchedulerService";
	private Log log;
	private ResourceBundle startBundle = null;
	private ResourceBundle stopBundle = null;
	private SchedulerFactory sf = null;
	private Scheduler s = null;

	public SchedulerService() {
		log = LogFactory.getLog(SchedulerService.class);
		
		CommandFinder finder = new CommandFinder(System
				.getProperty("perpetuum.commands.path"));

		System.setProperty("mx4j.log.priority", "debug");
		mx4j.log.Log.redirectTo(new Log4JLogger());

		startBundle = finder.doFindCommandBundle("start");
		stopBundle = finder.doFindCommandBundle("stop");

		ServiceRegistry.getDefault().register(NAME, this);
	}

	public void start() throws Exception {
		sf = new StdSchedulerFactory();

		try {
			s = sf.getScheduler();
		} catch (SchedulerException e) {
			stop();
			
			throw new Exception (e);
		}
		
		log.info(startBundle.getString("scheduler.started"));
	}
	
	public void stop() {
		if (s != null) {
			try {
				s.shutdown();
			} catch (SchedulerException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
			
			log.info(startBundle.getString("scheduler.stopped"));
		}
		
		ServiceRegistry.getDefault().unRegister(NAME);
	}
	
	public Scheduler getScheduler() {
		return s;
	}
}
