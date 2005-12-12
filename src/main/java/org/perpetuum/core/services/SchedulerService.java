package org.perpetuum.core.services;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

public class SchedulerService extends AbstractService {
	public static final String NAME = "SchedulerService";
	private SchedulerFactory sf = null;
	private Scheduler s = null;

	public SchedulerService() {
		prepare(NAME);
	}
	
	public void init() throws Exception {
		sf = new StdSchedulerFactory();
	}

	public void start() throws Exception {
		try {
			init();
			
			s = sf.getScheduler();
		} catch (SchedulerException e) {
			throw e;
		}
		
		log.info(bundle.getString("scheduler.started"));
	}
	
	public void stop() {
		if (s != null) {
			try {
				s.shutdown();
			} catch (SchedulerException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
			
			log.info(bundle.getString("scheduler.stopped"));
		}
		
		ServiceRegistry.getDefault().unRegister(NAME);
	}
	
	public Scheduler getScheduler() {
		return s;
	}
}
