package org.perpetuum.core.management;

public interface PerpetuumCoreMBean extends ServiceMBean {

	/**
	 * Starts the core and all of its dependencies
	 */
	public void start();
	
	/**
	 * Stops the core and all of its dependencies
	 */
	public void stop();
}
