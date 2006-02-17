package org.codehaus.perpetuum.classpath;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;

import sun.misc.URLClassPath;

/**
 * This class is used to modify the System ClassLoader for dynamic support for 
 * new commands and features without having to reboot the server.
 * 
 * This code was originally in OpenEJB (http://www.openejb.org)
 */
public abstract class BasicURLClassPath implements ClassPath {
    private java.lang.reflect.Field ucpField;

    /**
     * Adds jars to the Classpath loaded from the directory
     */
    protected void addJarsToPath(final File dir, final URLClassLoader loader) throws Exception {
        if (dir == null || !dir.exists()) return;
        
        // Get the list of jars and zips
        String[] jarNames = dir.list(new java.io.FilenameFilter() {
            public boolean accept(File dir, String name) {
                //System.out.println("FILE "+name);
                return (name.endsWith(".jar") || name.endsWith(".zip"));
            }
        });

        // Create URLs from them
        final URL[] jars = new URL[jarNames.length];
        for (int j = 0; j < jarNames.length; j++) {
            jars[j] = new File(dir, jarNames[j]).toURL();
        }

        sun.misc.URLClassPath path = getURLClassPath(loader);
        for (int i = 0; i < jars.length; i++) {
            //System.out.println("URL "+jars[i]);
            path.addURL(jars[i]);
        }
    }

    /**
     * Returns the Classpath
     */
    protected URLClassPath getURLClassPath(URLClassLoader loader) throws Exception {
        return (sun.misc.URLClassPath) getUcpField().get(loader);
    }

    /**
     * Gets the ucp field
     */
    private java.lang.reflect.Field getUcpField() throws Exception {
        if (ucpField == null) {
            // Add them to the URLClassLoader's classpath
            ucpField = AccessController.doPrivileged(new PrivilegedAction<Field>() {
                public Field run() {
                    Field ucp = null;
                    
                    try {
                        ucp = URLClassLoader.class.getDeclaredField("ucp");
                        ucp.setAccessible(true);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    return ucp;
                }
            });
        }

        return ucpField;
    }

}
