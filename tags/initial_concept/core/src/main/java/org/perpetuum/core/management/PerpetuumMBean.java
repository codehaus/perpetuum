package org.perpetuum.core.management;

import javax.management.ObjectName;

/**
 * All MBeans for Perpetuum will extend this interface.  Its uses are for 
 * having all MBeans provide certain functionality like logging.
 */
public interface PerpetuumMBean {
	
	/**
	 * Returns the name of the MBean to be used in the ObjectName
	 * @return String name of the MBean
	 */
	public String getName();
	
	/**
	 * Returns the ObjectName of the MBean
	 * @return ObjectName Object name of the MBean
	 */
	public ObjectName getObjectName();
}
