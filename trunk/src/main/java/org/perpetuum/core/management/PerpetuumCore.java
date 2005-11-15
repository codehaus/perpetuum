package org.perpetuum.core.management;

import java.util.ResourceBundle;

import javax.management.ObjectName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.perpetuum.command.CommandFinder;
import org.perpetuum.core.services.JMXService;
import org.perpetuum.core.services.SchedulerService;

public class PerpetuumCore implements PerpetuumCoreMBean {
	private String status = ServiceMBean.STOPPED;
	private String name = System.getProperty("application.id") + " Server";
	private ObjectName objectName = null;
	private Log log = null;
	private JMXService jmxService = null;
	private SchedulerService schedulerService = null;
	private int httpPort = 0;
	private int rmiPort = 0;
	private ResourceBundle startBundle = null;
	private ResourceBundle stopBundle = null;
	
	public PerpetuumCore() {
		log = LogFactory.getLog(PerpetuumCore.class);
		jmxService = new JMXService();
		
		CommandFinder finder = new CommandFinder(System.getProperty("perpetuum.commands.path"));
		
		startBundle = finder.doFindCommandBundle("start");
		stopBundle = finder.doFindCommandBundle("stop");
		
		try {
			objectName = new ObjectName(JMXService.DOMAIN_NAME + ":type=PerpetuumCore");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}
	
	public void start() {
		if (!status.equals(ServiceMBean.STARTED)) {
			try {
				initializeScheduler();
				initializeJMX();
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
				
				stop();
			}
		} else {
			log.warn(startBundle.getString("perpetuum.core.already.started"));
		}
		
		status = ServiceMBean.STARTED;
	}

	public void stop() {
		if (!status.equals(ServiceMBean.STOPPED)) {
			uninitializeScheduler();
			uninitializeJMX();
		} else {
			log.warn(stopBundle.getString("perpetuum.core.already.stopped"));
		}
		
		status = ServiceMBean.STOPPED;
	}
	
	public void uninitializeJMX() {
		try {
			jmxService.getMBeanServer().unregisterMBean(objectName);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		
		jmxService.stop();
	}
	
	public void initializeJMX() throws Exception {
		jmxService.setHttpPort(httpPort);
		jmxService.setRmiPort(rmiPort);
		
		try {
			jmxService.start();
			
			jmxService.getMBeanServer().registerMBean(this, objectName);
		} catch (Exception e) {
			throw new Exception (e);
		}
	}

	public void initializeScheduler() throws Exception {
		schedulerService = new SchedulerService();
		
		try {
			schedulerService.start();
		} catch (Exception e) {
			throw new Exception (e);
		}
	}
	
	public void uninitializeScheduler() {
		schedulerService.stop();
	}
	
	public String getStatus() {
		return status;
	}

	public String getName() {
		return name;
	}

	public ObjectName getObjectName() {
		return objectName;
	}
	
	public void setHttpPort(int httpPort) {
		this.httpPort = httpPort;
	}
	
	public void setRmiPort(int rmiPort) {
		this.rmiPort = rmiPort;
	}
}
