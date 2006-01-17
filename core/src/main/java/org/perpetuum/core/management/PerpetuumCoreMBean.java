package org.perpetuum.core.management;

public interface PerpetuumCoreMBean extends ServiceMBean {

	/**
	 * Stops the core and all of its dependencies
	 */
	public void stop();
}
