package org.perpetuum.classpath;

import java.io.File;
import java.net.URL;

/**
 * This class is used to modify the System ClassLoader for dynamic support for 
 * new commands and features without having to reboot the server.
 * 
 * This code was originally in OpenEJB (http://www.openejb.org)
 */
public interface ClassPath {

    ClassLoader getClassLoader();

    void addJarsToPath(File dir) throws Exception;

    void addJarToPath(URL dir) throws Exception;
}
