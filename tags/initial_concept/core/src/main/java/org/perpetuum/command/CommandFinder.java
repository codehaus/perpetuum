package org.perpetuum.command;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * This utility class is used to find the dynamic commands and their properties 
 * files.  This is also used to find ResourceBundles for i18n.
 */
public class CommandFinder {
	private String path;
	private Map classMap = Collections.synchronizedMap(new HashMap());
	
	/**
	 * Constructor
	 * @param path The path to find all commands
	 */
	public CommandFinder(String path) {
		this.path = path;
	}
	
	/**
	 * String signifying the command to look for
	 * @param key Command's key.  Example start.
	 * @return Properties object
	 */
    public Properties doFindCommandProperies(String key) {
        Properties p = null;
        
        try {
        	String uri = path + key + ".properties";
            
            // lets try the thread context class loader first
            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(uri);
            if (in == null) {
                in = CommandFinder.class.getClassLoader().getResourceAsStream(uri);
                if (in == null) {
                    throw new IOException("Could not find command in : " + uri);
                }
            }

            // lets load the file
            BufferedInputStream reader = null;
            try {
                reader = new BufferedInputStream(in);
                Properties properties = new Properties();
                properties.load(reader);
                
                p = properties;
            } finally {
                try {
                    reader.close();
                } catch (Exception e) {
                	e.printStackTrace();
                }
            }
        } catch (IOException ioe) {
        	ioe.printStackTrace();
        }
        
        return p;
    }
    
    /**
     * Used to allow for an i18n implementation of the command's properties
     * @param key Command's key.  Example: start
     * @return
     */
    public ResourceBundle doFindCommandBundle(String key) {
        	ResourceBundle bundle = null;
        	
        	try {
        		bundle = ResourceBundle.getBundle(path.replaceAll("/", ".") + key);
        	} catch (Exception e) {
        		e.printStackTrace();
        	}
    	
        return bundle;
    }
    
    /**
     * Locates all commands available for the specified path.
     * @return Enumeration of the commands
     * @throws IOException
     */
    public Enumeration doFindCommands() throws IOException {
    	    return Thread.currentThread().getContextClassLoader().getResources(path);
    }
}