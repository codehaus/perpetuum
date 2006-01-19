package org.perpetuum.core.services;

import java.io.File;
import java.text.MessageFormat;

import org.mortbay.http.HttpContext;
import org.mortbay.http.SocketListener;
import org.mortbay.http.handler.ResourceHandler;
import org.mortbay.jetty.Server;

/**
 * This class is used to serve all web content for Perpetuum
 */
public class WebService extends AbstractService {
	private int port = 0;
	private Server server = null;
	private SocketListener listener = null;
	private HttpContext rootContext = null;
	private ResourceHandler resourceHandler = null;
	
	/**
	 * Constructor
	 */
	public WebService() {
		prepare(WebService.class);
	}
	
	/*
	 *  (non-Javadoc)
	 * @see org.perpetuum.core.services.Service#init()
	 */
	public void init() throws Exception {
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
		
		initConsole();
		server.setStopAtShutdown(true);
	}
	
	/**
	 * Used to setup the Perpetuum web console
	 * @throws Exception
	 */
	public void initConsole() throws Exception {
		
	}

	/*
	 *  (non-Javadoc)
	 * @see org.perpetuum.core.services.Service#start()
	 */
	public void start() throws Exception {
		init();
		
		server.start();
		
		status = Service.STARTED;
		
		log.info(MessageFormat.format(bundle.getString("web.started"), new Object[] { String.valueOf(port) }));
	}

	/*
	 *  (non-Javadoc)
	 * @see org.perpetuum.core.services.Service#stop()
	 */
	public void stop() {
		if (!status.equals(Service.STOPPED)) {
			try {
				server.stop();
				
				status = Service.STOPPED;
				
				log.info(bundle.getString("web.stopped"));
			} catch (Exception e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
		} else {
			log.info(bundle.getString("web.already.stopped"));
		}
	}
	
	/**
	 * Sets the port that the web server listens on
	 * @param port The port to listen on
	 */
	public void setPort(int port) {
		this.port = port;
	}
	
	/**
	 * Returns an instance of the web server
	 * @return
	 */
	public Server getServer() {
		return server;
	}
}
