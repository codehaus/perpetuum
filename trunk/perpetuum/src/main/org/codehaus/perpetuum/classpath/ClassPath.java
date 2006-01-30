package org.codehaus.perpetuum.classpath;

import java.io.File;
import java.net.URL;

/**
 * This class is used to modify the System ClassLoader for dynamic support for 
 * new commands and features without having to reboot the server.
 * 
 * This code was originally in OpenEJB (http://www.openejb.org)
 */
public interface ClassPath {

	/**
	 * Returns the System ClassLoader
	 */
    ClassLoader getClassLoader();

    /**
     * Adds the jars in the directory to the Classpath
     */
    void addJarsToPath(File dir) throws Exception;

    /**
     * Adds the jars in the directory the URL points to to the Classpath
     */
    void addJarToPath(URL dir) throws Exception;
}
