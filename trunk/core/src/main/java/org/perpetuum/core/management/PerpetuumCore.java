package org.perpetuum.core.management;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javax.management.ObjectName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.perpetuum.command.CommandFinder;
import org.perpetuum.core.services.DatabaseService;
import org.perpetuum.core.services.JMXService;
import org.perpetuum.core.services.SchedulerService;
import org.perpetuum.core.services.Service;
import org.perpetuum.core.services.WebService;

/**
 * Management bean for Perpetuum.  Used to manage a running server instance.
 */
public class PerpetuumCore implements PerpetuumCoreMBean {
	private String status = ServiceMBean.STOPPED;
	private String name = System.getProperty("application.id") + " Server";
	private ObjectName objectName = null;
	private Log log = null;
	private JMXService jmxService = null;
	private SchedulerService schedulerService = null;
	private DatabaseService databaseService = null;
	private WebService webService = null;
	private int httpPort = 0;
	private int rmiPort = 0;
	private int webPort = 0;
	private ResourceBundle startBundle = null;
	private ResourceBundle stopBundle = null;
	private LinkedList dependencies = null;
	
	/**
	 * Constructor
	 */
	public PerpetuumCore() {
		log = LogFactory.getLog(PerpetuumCore.class);
		init();
		
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
	
	/**
	 * Initializes the dependencies
	 */
	public void init() {
		dependencies = new LinkedList();
		
		jmxService = new JMXService();
		schedulerService = new SchedulerService();
		databaseService = new DatabaseService();
		webService = new WebService();
		
		dependencies.add(jmxService);
		dependencies.add(schedulerService);
		dependencies.add(databaseService);
		dependencies.add(webService);
	}
	
	/**
	 * Starts each dependency
	 */
	public void start() {
		if (!status.equals(ServiceMBean.STARTED)) {
			try {
				for (Iterator i = dependencies.iterator(); i.hasNext();) {
					Service cS = (Service)i.next();
					
					if (cS instanceof JMXService) {
						jmxService.setHttpPort(httpPort);
						jmxService.setRmiPort(rmiPort);
					} else if (cS instanceof WebService) {
						webService.setPort(webPort);
					}
					
					cS.start();
					
					if (cS instanceof JMXService) {
						jmxService.getMBeanServer().registerMBean(this, objectName);
					}
				}
				
				status = ServiceMBean.STARTED;
				
				log.info(startBundle.getString("start.complete"));
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
				
				stop();
			}
		} else {
			log.warn(startBundle.getString("perpetuum.core.already.started"));
		}
	}

	/**
	 * Stops each dependency
	 */
	public void stop() {
		log.info(startBundle.getString("stop.header"));
		
		for (int i = dependencies.size() - 1; i >= 0; i--) {
			Service cS = (Service)dependencies.get(i);
			
			if (cS instanceof JMXService) {
				try {
					jmxService.getMBeanServer().unregisterMBean(objectName);
				} catch (Exception e) {
					log.error(e.getMessage());
					e.printStackTrace();
				}
			}
			
			cS.stop();
		}
		
		status = ServiceMBean.STOPPED;
		
		log.info(startBundle.getString("stop.complete"));
	}
	
	/**
	 * Returns Perpetuum's status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Returns the application name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the JMX ObjectName for easy reference
	 */
	public ObjectName getObjectName() {
		return objectName;
	}
	
	/**
	 * Sets the HTTP port for JMX's web console
	 * @param httpPort Listening port
	 */
	public void setHttpPort(int httpPort) {
		this.httpPort = httpPort;
	}
	
	/**
	 * Sets the RMI port for JMX's naming service
	 * @param rmiPort Listening port
	 */
	public void setRmiPort(int rmiPort) {
		this.rmiPort = rmiPort;
	}
	
	/**
	 * Sets the Web Server port to listen on
	 * @param webPort Listening port
	 */
	public void setWebPort(int webPort) {
		this.webPort = webPort;
	}
}
