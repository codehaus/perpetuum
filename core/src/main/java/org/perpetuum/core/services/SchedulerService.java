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
		init();
		
		s = sf.getScheduler();
		
		log.info(bundle.getString("scheduler.started"));
		status = Service.STARTED;
	}
	
	public void stop() {
		if (s != null) {
			if (!status.equals(Service.STOPPED)) {
				try {
					s.shutdown();
					
					status = Service.STOPPED;
					
					log.info(bundle.getString("scheduler.stopped"));
				} catch (SchedulerException e) {
					e.printStackTrace();
					log.error(e.getMessage());
				}
			} else {
				log.info(bundle.getString("scheduler.already.stopped"));
			}
		}
		
		ServiceRegistry.getDefault().unRegister(NAME);
	}
	
	public Scheduler getScheduler() {
		return s;
	}
}
