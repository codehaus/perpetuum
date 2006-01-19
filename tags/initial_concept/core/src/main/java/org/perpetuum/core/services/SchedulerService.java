package org.perpetuum.core.services;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

/**
 * This class is used for all scheduling needs for Perpetuum.
 */
public class SchedulerService extends AbstractService {
	private SchedulerFactory sf = null;
	private Scheduler s = null;

	/**
	 * Constructor
	 */
	public SchedulerService() {
		prepare(SchedulerService.class);
	}
	
	/*
	 *  (non-Javadoc)
	 * @see org.perpetuum.core.services.Service#init()
	 */
	public void init() throws Exception {
		sf = new StdSchedulerFactory();
	}

	/*
	 *  (non-Javadoc)
	 * @see org.perpetuum.core.services.Service#start()
	 */
	public void start() throws Exception {
		init();
		
		s = sf.getScheduler();
		
		log.info(bundle.getString("scheduler.started"));
		status = Service.STARTED;
	}
	
	/*
	 *  (non-Javadoc)
	 * @see org.perpetuum.core.services.Service#stop()
	 */
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
	
	/**
	 * Returns the Scheduler
	 * @return Scheduler The Scheduler
	 */
	public Scheduler getScheduler() {
		return s;
	}
}
