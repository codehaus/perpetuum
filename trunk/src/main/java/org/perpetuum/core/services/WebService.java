package org.perpetuum.core.services;

import java.text.MessageFormat;

public class WebService extends AbstractService {
	private static final String NAME = "WebService";
	private int port = 0;
	
	public WebService() {
		prepare(NAME);
	}
	
	public void init() {
		
	}

	public void start() throws Exception {
		try {
			init();
			
			log.info(MessageFormat.format(bundle.getString("web.started"), new Object[] { String.valueOf(port) }));
		} catch (Exception e) {
			throw e;
		}
	}

	public void stop() {
		log.info(bundle.getString("web.stopped"));
	}
	
	public void setPort(int port) {
		this.port = port;
	}
}
