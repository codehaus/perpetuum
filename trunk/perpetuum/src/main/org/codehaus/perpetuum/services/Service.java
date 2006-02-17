package org.codehaus.perpetuum.services;

import org.codehaus.perpetuum.states.State;

/**
 * This interface will be implemented by ever Service
 */
public interface Service {
	public static final String PATH = "services/";
	public static final String RESOURCE_PATH = "services.";
	
	/**
	 * Initializes the Service
	 */
	public void init();
	
	/**
	 * Starts the Service
	 * @throws Exception Thrown if there is an error during startup
	 */
	public void start() throws Exception;
	
	/**
	 * Stops the Service
     * @throws Exception Thrown if there is an error during shutdown
	 */
	public void stop() throws Exception;
	
	/**
	 * Sets the Service State
	 */
	public void setState(State state);
	
	/**
	 * Gets the Service State
	 */
	public State getState();
}
