package org.perpetuum.core.management;

import java.util.ResourceBundle;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.perpetuum.command.CommandFinder;

public class PerpetuumCore implements PerpetuumCoreMBean {
	private String status = ServiceMBean.STOPPED;
	private String name = System.getProperty("application.id") + " Server";
	private ObjectName objectName = null;
	private Log log = null;
	private PerpetuumAgent pa = null;
	private int httpPort = 0;
	private int rmiPort = 0;
	private ResourceBundle startBundle = null;
	private ResourceBundle stopBundle = null;

	public PerpetuumCore() {
		log = LogFactory.getLog(PerpetuumCore.class);
		pa = new PerpetuumAgent();
		
		CommandFinder finder = new CommandFinder(System.getProperty("perpetuum.commands.path"));
		
		startBundle = finder.doFindCommandBundle("start");
		stopBundle = finder.doFindCommandBundle("stop");
		
		try {
			objectName = new ObjectName(PerpetuumAgent.DOMAIN_NAME + ":type=PerpetuumCore");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}
	
	public void start() {
		if (!status.equals(ServiceMBean.STARTED)) {
			initializeJMX();
		} else {
			log.warn(startBundle.getString("perpetuum.core.already.started"));
		}
		
		status = ServiceMBean.STARTED;
	}

	public void stop() {
		if (!status.equals(ServiceMBean.STOPPED)) {
			uninitializeJMX();
		} else {
			log.warn(stopBundle.getString("perpetuum.core.already.stopped"));
		}
		
		status = ServiceMBean.STOPPED;
	}
	
	public void uninitializeJMX() {
		try {
			pa.getMBeanServer().unregisterMBean(objectName);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		
		pa.stop();
	}
	
	public void initializeJMX() {
		pa.setHttpPort(httpPort);
		pa.setRmiPort(rmiPort);
		
		pa.start();
		
		try {
			pa.getMBeanServer().registerMBean(this, objectName);
		} catch (InstanceAlreadyExistsException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} catch (MBeanRegistrationException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} catch (NotCompliantMBeanException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
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
