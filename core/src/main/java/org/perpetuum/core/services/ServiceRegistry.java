package org.perpetuum.core.services;

import java.util.HashMap;

/**
 * Helper class for all Services in Perpetuum
 */
public class ServiceRegistry {
	public static ServiceRegistry sr = null;
	private HashMap services = null;
	
	/**
	 * Constructor (singleton)
	 */
	private ServiceRegistry() {
		// Singleton
		
		services = new HashMap();
	}
	
	/**
	 * Returns an instance of the ServiceRegistry singleton
	 * @return ServiceRegistry singleton
	 */
	public static ServiceRegistry getDefault() {
		if (sr == null) {
			sr = new ServiceRegistry();
		}
		
		return sr;
	}
	
	/**
	 * Adds a service to the registry
	 * @param name The service name
	 * @param service The service
	 */
	public void register(String name, Service service) {
		if (!services.containsKey(name)) {
			services.put(name, service);
		}
	}
	
	/**
	 * Removes a service from the registry
	 * @param name The service name
	 */
	public void unRegister(String name) {
		if (services.containsKey(name)) {
			services.remove(name);
		}
	}
}
