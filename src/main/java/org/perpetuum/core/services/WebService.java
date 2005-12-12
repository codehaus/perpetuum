package org.perpetuum.core.services;

public class WebService extends AbstractService {
	private static final String NAME = "WebService";
	
	public WebService() {
		prepare(NAME);
	}
	
	public void init() {
		
	}

	public void start() throws Exception {
		try {
			init();
			
			log.info(bundle.getString("web.started"));
		} catch (Exception e) {
			throw e;
		}
	}

	public void stop() {
		log.info(bundle.getString("web.stopped"));
	}
}
