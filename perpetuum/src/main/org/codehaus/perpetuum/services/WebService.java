package org.codehaus.perpetuum.services;

import java.io.File;
import java.util.ResourceBundle;

import org.apache.commons.logging.LogFactory;
import org.codehaus.perpetuum.states.State;
import org.codehaus.perpetuum.utils.PerpetuumUtil;
import org.mortbay.http.HttpContext;
import org.mortbay.http.SocketListener;
import org.mortbay.http.handler.ResourceHandler;
import org.mortbay.jetty.Server;

public class WebService extends AbstractService {
	private int port = 5000;
	private Server server = null;
	private SocketListener listener = null;
	private HttpContext rootContext = null;
	private ResourceHandler resourceHandler = null;
	
	/**
	 * @see org.codehaus.perpetuum.services.Service#init()
	 */
	public void init() {
		setLog(LogFactory.getLog(getClass()));
		setResourceBundle(ResourceBundle.getBundle(Service.RESOURCE_PATH + getClass().getName().substring(getClass().getName().lastIndexOf(".") + 1).toLowerCase()));
		
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
		
		if (new File(System.getProperty("perpetuum.home") + File.separator + "docroot").exists()) {
			rootContext.setResourceBase(System.getProperty("perpetuum.home") + File.separator + "docroot");
		} else {
			// Workaround for running in an IDE
			rootContext.setResourceBase(System.getProperty("perpetuum.home") + File.separator + ".." + File.separator + "docroot");
		}
		
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
		
		setState(State.STARTING);
		
        try {
            server.start();
        } catch (Exception e) {
            if (PerpetuumUtil.isDebugOn()) {
                e.printStackTrace();
            }
            
            getLog().error(e.getMessage());
            
            throw new Exception(e);
        }
            
        setState(State.STARTED);
	}

	/**
	 * @see org.codehaus.perpetuum.services.Service#stop()
	 */
	public void stop() throws Exception {
		setState(State.STOPPING);
		
		try {
            server.stop();
            
            server.destroy();
        } catch (Exception e) {
            if (PerpetuumUtil.isDebugOn()) {
                e.printStackTrace();
            }
            
            getLog().error(e.getMessage());
            
            throw new Exception(e);
        }
        
        setState(State.STOPPED);
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
