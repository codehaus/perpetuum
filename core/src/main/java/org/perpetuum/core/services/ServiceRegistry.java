package org.perpetuum.core.services;

import java.util.HashMap;

public class ServiceRegistry {
	public static ServiceRegistry sr = null;
	private HashMap services = null;
	
	private ServiceRegistry() {
		// Singleton
		
		services = new HashMap();
	}
	
	public static ServiceRegistry getDefault() {
		if (sr == null) {
			sr = new ServiceRegistry();
		}
		
		return sr;
	}
	
	public void register(String name, Service service) {
		if (!services.containsKey(name)) {
			services.put(name, service);
		}
	}
	
	public void unRegister(String name) {
		if (services.containsKey(name)) {
			services.remove(name);
		}
	}
}
