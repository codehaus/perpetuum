package org.perpetuum.core.management;

/**
 * This interface will be extended by all ServiceMBeans.
 */
public interface ServiceMBean extends PerpetuumMBean {
	/**
	 * Service states
	 * We used Strings since these are visible in the JMX Web Console and 
	 * are more human readable
	 */
	public static final String STOPPED = "Stopped";
	public static final String STARTED = "Started";
	
	/**
	 * Returns the status of the service
	 * @return int specified by the ServiceMBean
	 */
	public String getStatus();
}
