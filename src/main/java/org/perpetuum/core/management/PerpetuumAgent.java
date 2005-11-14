package org.perpetuum.core.management;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.management.Attribute;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

import mx4j.log.Log4JLogger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.perpetuum.command.CommandFinder;

public class PerpetuumAgent {
	public static final String RMI_JNDI_NAME = "RMIConnector";
	public static final String DOMAIN_NAME = "PerpetuumDomain";
	public static final String NAME = "JMXServer";
	private Log log = null;
	private MBeanServer mbs = null;
	private ObjectName rmiRegistry = null;
	private ObjectName httpAdaptor = null;
	private ObjectName xsltProcessor = null;
	private JMXConnectorServer connectorServer = null;
	private ResourceBundle bundle = null;
	private int httpPort = 0;
	private int rmiPort = 0;
	
	public PerpetuumAgent() {
		CommandFinder finder = new CommandFinder(System.getProperty("perpetuum.commands.path"));
		
		System.setProperty("mx4j.log.priority", "debug");
		mx4j.log.Log.redirectTo(new Log4JLogger());
		
		bundle = finder.doFindCommandBundle("start");
		log = LogFactory.getLog(PerpetuumAgent.class);
	}
	
	public void start() {
		initializeMBeanServer();
		initializeRMIRegistry();
		initializeHttpAdaptor();
	}
	
	public void initializeRMIRegistry() {
		try {
			rmiRegistry = ObjectName.getInstance("naming:type=rmiregistry");
			mbs.createMBean("mx4j.tools.naming.NamingService", rmiRegistry, null, new Object[] {new Integer(rmiPort)}, new String[] {"int"});
		    mbs.invoke(rmiRegistry, "start", null, null);
		    String jndiPath = "/" + RMI_JNDI_NAME;
		    JMXServiceURL url = new JMXServiceURL("service:jmx:rmi://localhost/jndi/rmi://localhost:" + rmiPort + jndiPath);

		    connectorServer = JMXConnectorServerFactory.newJMXConnectorServer(url, null, mbs);
		    connectorServer.start();
		    
		    log.info(MessageFormat.format(bundle.getString("jmx.rmi.started"), new Object[] { String.valueOf(rmiPort) }));
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}
	
	public void initializeMBeanServer() {
		mbs = MBeanServerFactory.createMBeanServer(DOMAIN_NAME);
		log.info(bundle.getString("jmx.started"));
	}
	
	public void initializeHttpAdaptor() {
		try {
			httpAdaptor = new ObjectName("Server:name=HttpAdaptor");
			xsltProcessor = new ObjectName("Server:name=XSLTProcessor");
			
			mbs.createMBean("mx4j.tools.adaptor.http.HttpAdaptor", httpAdaptor, null);
			mbs.setAttribute(httpAdaptor, new Attribute("Port", new Integer(httpPort)));
			mbs.setAttribute(httpAdaptor, new Attribute("Host", "localhost"));
			
			mbs.createMBean("mx4j.tools.adaptor.http.XSLTProcessor", xsltProcessor, null);
			mbs.setAttribute(httpAdaptor, new Attribute("ProcessorName", xsltProcessor));
			
			mbs.invoke(httpAdaptor, "start", null, null);
		} catch (MalformedObjectNameException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} catch (InstanceAlreadyExistsException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} catch (MBeanRegistrationException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} catch (NotCompliantMBeanException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} catch (ReflectionException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} catch (MBeanException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} catch (AttributeNotFoundException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} catch (InvalidAttributeValueException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		
		log.info(MessageFormat.format(bundle.getString("jmx.http.started"), new Object[] { String.valueOf(httpPort) }));
	}
	
	public void stop() {
		try {
			connectorServer.stop();
			mbs.invoke(rmiRegistry, "stop", null, null);
			mbs.unregisterMBean(rmiRegistry);
			log.info(bundle.getString("jmx.rmi.stopped"));
			
			mbs.invoke(httpAdaptor, "stop", null, null);
			mbs.unregisterMBean(httpAdaptor);
			log.info(bundle.getString("jmx.http.stopped"));
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		
		MBeanServerFactory.releaseMBeanServer(mbs);
		log.info(bundle.getString("jmx.stopped"));
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