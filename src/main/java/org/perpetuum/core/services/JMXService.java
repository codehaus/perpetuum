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

public class JMXService extends AbstractService {
	public static final String RMI_JNDI_NAME = "RMIConnector";
	public static final String DOMAIN_NAME = "PerpetuumDomain";
	public static final String NAME = "JMXServer";
	private MBeanServer mbs = null;
	private ObjectName rmiRegistry = null;
	private ObjectName httpAdaptor = null;
	private ObjectName xsltProcessor = null;
	private JMXConnectorServer connectorServer = null;
	private int httpPort = 0;
	private int rmiPort = 0;
	private String rmiStatus = Service.STOPPED;
	private String jmxStatus = Service.STOPPED;
	private String httpStatus = Service.STOPPED;
	
	public JMXService() {
		System.setProperty("mx4j.log.priority", "debug");
		mx4j.log.Log.redirectTo(new Log4JLogger());
		
		prepare(NAME);
	}
	
	public void start() throws Exception {
		try {
			initializeMBeanServer();
			jmxStatus = Service.STARTED;
			log.info(bundle.getString("jmx.started"));
			
			initializeRMIRegistry();
			rmiStatus = Service.STARTED;
			log.info(MessageFormat.format(bundle.getString("jmx.rmi.started"), new Object[] { String.valueOf(rmiPort) }));
			
			initializeHttpAdaptor();
			httpStatus = Service.STARTED;
			log.info(MessageFormat.format(bundle.getString("jmx.http.started"), new Object[] { String.valueOf(httpPort) }));
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void initializeRMIRegistry() throws Exception {
		rmiRegistry = ObjectName.getInstance("naming:type=rmiregistry");
		mbs.createMBean("mx4j.tools.naming.NamingService", rmiRegistry, null, new Object[] {new Integer(rmiPort)}, new String[] {"int"});
	    mbs.invoke(rmiRegistry, "start", null, null);
	    String jndiPath = "/" + RMI_JNDI_NAME;
	    JMXServiceURL url = new JMXServiceURL("service:jmx:rmi://localhost/jndi/rmi://localhost:" + rmiPort + jndiPath);

	    connectorServer = JMXConnectorServerFactory.newJMXConnectorServer(url, null, mbs);
	    connectorServer.start();
	}
	
	public void initializeMBeanServer() throws Exception {
		mbs = MBeanServerFactory.createMBeanServer(DOMAIN_NAME);
	}
	
	public void initializeHttpAdaptor()  throws Exception {
		httpAdaptor = new ObjectName("Server:name=HttpAdaptor");
		xsltProcessor = new ObjectName("Server:name=XSLTProcessor");
		
		mbs.createMBean("mx4j.tools.adaptor.http.HttpAdaptor", httpAdaptor, null);
		mbs.setAttribute(httpAdaptor, new Attribute("Port", new Integer(httpPort)));
		mbs.setAttribute(httpAdaptor, new Attribute("Host", "localhost"));
		
		mbs.createMBean("mx4j.tools.adaptor.http.XSLTProcessor", xsltProcessor, null);
		mbs.setAttribute(httpAdaptor, new Attribute("ProcessorName", xsltProcessor));
		
		mbs.invoke(httpAdaptor, "start", null, null);
	}
	
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
		
		if (jmxStatus.equals(Service.STARTED)) {
			MBeanServerFactory.releaseMBeanServer(mbs);
			log.info(bundle.getString("jmx.stopped"));
		}
		
		ServiceRegistry.getDefault().unRegister(NAME);
	}
	
	public MBeanServer getMBeanServer() {
		return mbs;
	}
	
	public int getHttpPort() {
		return httpPort;
	}
	
	public void setHttpPort(int httpPort) {
		this.httpPort = httpPort;
	}
	
	public void setRmiPort(int rmiPort) {
		this.rmiPort = rmiPort;
	}
}