package org.perpetuum.core.services;

import java.text.MessageFormat;

import javax.management.Attribute;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

import mx4j.log.Log4JLogger;

/**
 * This class is used to manage the JMX implementation for the management and 
 * monitoring of Perpetuum.
 */
public class JMXService extends AbstractService {
	public static final String RMI_JNDI_NAME = "RMIConnector";
	public static final String DOMAIN_NAME = "PerpetuumDomain";
	private MBeanServer mbs = null;
	private ObjectName rmiRegistry = null;
	private ObjectName httpAdaptor = null;
	private ObjectName xsltProcessor = null;
	private JMXConnectorServer connectorServer = null;
	private int httpPort = 0;
	private int rmiPort = 0;
	private String rmiStatus = Service.STOPPED;
	private String httpStatus = Service.STOPPED;
	
	/**
	 * Constructor
	 */
	public JMXService() {
		System.setProperty("mx4j.log.priority", "debug");
		mx4j.log.Log.redirectTo(new Log4JLogger());
		
		prepare(JMXService.class);
	}
	
	/*
	 *  (non-Javadoc)
	 * @see org.perpetuum.core.services.Service#init()
	 */
	public void init() throws Exception {
		initializeMBeanServer();
		initializeRMIRegistry();
		initializeHttpAdaptor();
	}
	
	/*
	 *  (non-Javadoc)
	 * @see org.perpetuum.core.services.Service#start()
	 */
	public void start() throws Exception {
		init();
		
		connectorServer.start();
		rmiStatus = Service.STARTED;
		log.info(MessageFormat.format(bundle.getString("jmx.rmi.started"), new Object[] { String.valueOf(rmiPort) }));
		
		mbs.invoke(httpAdaptor, "start", null, null);
		httpStatus = Service.STARTED;
		log.info(MessageFormat.format(bundle.getString("jmx.http.started"), new Object[] { String.valueOf(httpPort) }));
	}
	
	/**
	 * Initializes the RMI-backed registry
	 * @throws Exception
	 */
	public void initializeRMIRegistry() throws Exception {
		rmiRegistry = ObjectName.getInstance("naming:type=rmiregistry");
		mbs.createMBean("mx4j.tools.naming.NamingService", rmiRegistry, null, new Object[] {new Integer(rmiPort)}, new String[] {"int"});
	    mbs.invoke(rmiRegistry, "start", null, null);
	    String jndiPath = "/" + RMI_JNDI_NAME;
	    JMXServiceURL url = new JMXServiceURL("service:jmx:rmi://localhost/jndi/rmi://localhost:" + rmiPort + jndiPath);

	    connectorServer = JMXConnectorServerFactory.newJMXConnectorServer(url, null, mbs);
	}
	
	/**
	 * Initializes the MBeanServer
	 * @throws Exception
	 */
	public void initializeMBeanServer() throws Exception {
		mbs = MBeanServerFactory.createMBeanServer(DOMAIN_NAME);
		
		status = Service.STARTED;
		log.info(bundle.getString("jmx.started"));
	}
	
	/**
	 * Initializes the HTTP Console
	 * @throws Exception
	 */
	public void initializeHttpAdaptor()  throws Exception {
		httpAdaptor = new ObjectName("Server:name=HttpAdaptor");
		xsltProcessor = new ObjectName("Server:name=XSLTProcessor");
		
		mbs.createMBean("mx4j.tools.adaptor.http.HttpAdaptor", httpAdaptor, null);
		mbs.setAttribute(httpAdaptor, new Attribute("Port", new Integer(httpPort)));
		mbs.setAttribute(httpAdaptor, new Attribute("Host", "localhost"));
		
		mbs.createMBean("mx4j.tools.adaptor.http.XSLTProcessor", xsltProcessor, null);
		mbs.setAttribute(httpAdaptor, new Attribute("ProcessorName", xsltProcessor));
	}
	
	/*
	 *  (non-Javadoc)
	 * @see org.perpetuum.core.services.Service#stop()
	 */
	public void stop() {
		try {
			if (httpStatus.equals(Service.STARTED)) {
				mbs.invoke(httpAdaptor, "stop", null, null);
				mbs.unregisterMBean(httpAdaptor);
				log.info(bundle.getString("jmx.http.stopped"));
			}
			
			if (rmiStatus.equals(Service.STARTED)) {
				connectorServer.stop();
				mbs.invoke(rmiRegistry, "stop", null, null);
				mbs.unregisterMBean(rmiRegistry);
				log.info(bundle.getString("jmx.rmi.stopped"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		
		if (status.equals(Service.STARTED)) {
			MBeanServerFactory.releaseMBeanServer(mbs);
			log.info(bundle.getString("jmx.stopped"));
		}
		
		ServiceRegistry.getDefault().unRegister(NAME);
	}
	
	/**
	 * Returns reference to the MBeanServer
	 * @return MBeanServer The MBeanServer
	 */
	public MBeanServer getMBeanServer() {
		return mbs;
	}
	
	/**
	 * Returns the HTTP port that JMX's console is listening on
	 * @return
	 */
	public int getHttpPort() {
		return httpPort;
	}
	
	/**
	 * Sets the HTTP port that JMX listens on
	 * @param httpPort Listening Port
	 */
	public void setHttpPort(int httpPort) {
		this.httpPort = httpPort;
	}
	
	/**
	 * Sets the RMI port that JMX listens on
	 * @param rmiPort Listening port
	 */
	public void setRmiPort(int rmiPort) {
		this.rmiPort = rmiPort;
	}
}