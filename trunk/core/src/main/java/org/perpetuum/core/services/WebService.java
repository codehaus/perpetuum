package org.perpetuum.core.services;

import java.io.File;
import java.text.MessageFormat;

import org.mortbay.http.HttpContext;
import org.mortbay.http.SocketListener;
import org.mortbay.http.handler.ResourceHandler;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.WebApplicationContext;

public class WebService extends AbstractService {
	private static final String NAME = "WebService";
	private int port = 0;
	private Server server = null;
	private SocketListener listener = null;
	private HttpContext rootContext = null;
	private ResourceHandler resourceHandler = null;
	private WebApplicationContext console = null;
	
	public WebService() {
		prepare(NAME);
	}
	
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
	
	public void initConsole() throws Exception {
		
	}

	public void start() throws Exception {
		init();
		
		server.start();
		
		status = Service.STARTED;
		
		log.info(MessageFormat.format(bundle.getString("web.started"), new Object[] { String.valueOf(port) }));
	}

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
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public Server getServer() {
		return server;
	}
}
