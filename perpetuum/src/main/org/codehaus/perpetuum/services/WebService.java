package org.codehaus.perpetuum.services;

import java.io.File;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mortbay.http.HttpContext;
import org.mortbay.http.SocketListener;
import org.mortbay.http.handler.ResourceHandler;
import org.mortbay.jetty.Server;

public class WebService implements Service {
	private Log log = LogFactory.getLog(getClass());
	private ResourceBundle bundle = ResourceBundle.getBundle(Service.RESOURCE_PATH + getClass().getName().substring(getClass().getName().lastIndexOf(".") + 1).toLowerCase());
	private int port = 5000;
	private Server server = null;
	private SocketListener listener = null;
	private HttpContext rootContext = null;
	private ResourceHandler resourceHandler = null;
	
	/**
	 * @see org.codehaus.perpetuum.services.Service#init()
	 */
	public void init() {
		System.setProperty("org.mortbay.log.LogFactory.noDiscovery", "false");
		
		server = new Server();
		listener = new SocketListener();
		resourceHandler = new ResourceHandler();
		
		listener.setPort(port);
		server.addListener(listener);
		
		resourceHandler.setDirAllowed(true);
		
		rootContext = new HttpContext();
		
		rootContext.setClassLoaderJava2Compliant(true);
		rootContext.setContextPath("/");
		rootContext.setResourceBase(System.getProperty("perpetuum.home") + File.separator + "docroot");
		rootContext.addHandler(resourceHandler);
		
		server.addContext(rootContext);
		
		// System.setProperty("perpetuum.webapps.home",System.getProperty("perpetuum.home") + File.separator + "docroot" + File.separator + "webapps");
		
		server.setStopAtShutdown(true);
	}
	
	/**
	 * @see org.codehaus.perpetuum.services.Service#start()
	 */
	public void start() throws Exception {
		init();
		
		server.start();
	}

	/**
	 * @see org.codehaus.perpetuum.services.Service#stop()
	 */
	public void stop() {
		try {
			server.stop();
		} catch (InterruptedException e) {
			log.error(e);
		}
	}
	
	/**
	 * Used to get a reference to the Log object
	 * @return log
	 */
	public Log getLog() {
		return log;
	}
	
	/**
	 * Used to get a reference to the ResourceBundle object
	 * @return bundle
	 */
	public ResourceBundle getBundle() {
		return bundle;
	}

	/**
	 * @return Returns the port.
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port The port to set.
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return Returns the resourceHandler.
	 */
	public ResourceHandler getResourceHandler() {
		return resourceHandler;
	}

	/**
	 * @return Returns the rootContext.
	 */
	public HttpContext getRootContext() {
		return rootContext;
	}

	/**
	 * @return Returns the server.
	 */
	public Server getServer() {
		return server;
	}
}
