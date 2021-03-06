package org.codehaus.perpetuum.classpath;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * This class is used to modify the System ClassLoader for dynamic support for 
 * new commands and features without having to reboot the server.
 * 
 * This code was originally in OpenEJB (http://www.openejb.org)
 */
public class SystemClassPath extends BasicURLClassPath {

    private URLClassLoader sysLoader;
    
    /**
     * @see org.perpetuum.classpath.ClassPath#addPathToPath(java.io.File)
     */
    public void addPathToPath(File dir) throws Exception {
        this.addPathToPath(dir, getSystemLoader());
        this.rebuildJavaClassPathVariable();
    }

    /**
     * @see org.perpetuum.classpath.ClassPath#addJarsToPath(java.io.File)
     */
    public void addJarsToPath(File dir) throws Exception {
        this.addJarsToPath(dir, getSystemLoader());
        this.rebuildJavaClassPathVariable();
    }

    /**
     * @see org.perpetuum.classpath.ClassPath#getClassLoader()
     */
    public ClassLoader getClassLoader() {
        try {
            return getSystemLoader();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the System ClassLoader
     */
    private URLClassLoader getSystemLoader() throws Exception {
        if (sysLoader == null) {
            sysLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        }
        return sysLoader;
    }

    /**
     * Rebuilds the Classpath variable with the new Classpath
     */
    private void rebuildJavaClassPathVariable() throws Exception {
        sun.misc.URLClassPath cp = getURLClassPath(getSystemLoader());
        URL[] urls = cp.getURLs();
        //for (int i=0; i < urls.length; i++){
        //    System.out.println(urls[i].toExternalForm());
        //}
        if (urls.length < 1)
            return;

        StringBuffer path = new StringBuffer(urls.length * 32);

        File s = new File(urls[0].getFile());
        path.append(s.getPath());
        //System.out.println(s.getPath());

        for (int i = 1; i < urls.length; i++) {
            path.append(File.pathSeparator);

            s = new File(urls[i].getFile());
            //System.out.println(s.getPath());
            path.append(s.getPath());
        }
        try {
            System.setProperty("java.class.path", path.toString());
        } catch (Exception e) {
        }
    }
}
