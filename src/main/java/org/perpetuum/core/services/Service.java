package org.perpetuum.core.services;

public interface Service {
	/**
	 * Service states
	 * We used Strings since these are visible in the JMX Web Console and 
	 * are more human readable
	 */
	public static final String STOPPED = "Stopped";
	public static final String STARTED = "Started";
	
	/**
	 * All services have to be prepared so that they are ready to be used
	 */
	public void prepare(String name);
	
	/**
	 * All services have to be able to be started
	 */
	public void start() throws Exception;
	
	/**
	 * All services have to be able to be stopped
	 */
	public void stop();
}
