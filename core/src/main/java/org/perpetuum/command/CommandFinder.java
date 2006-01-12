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

public class CommandFinder {
	private String path;
	private Map classMap = Collections.synchronizedMap(new HashMap());
	
	public CommandFinder(String path) {
		this.path = path;
	}
	
    public Properties doFindCommandProperies(String key) {
        Properties p = null;
        
        try {
        	String uri = path + key;
            
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
    
    public ResourceBundle doFindCommandBundle(String key) {
        return ResourceBundle.getBundle(path.replaceAll("/", ".") + key);
    }
    
    public Enumeration doFindCommands() throws IOException {
    	return Thread.currentThread().getContextClassLoader().getResources(path);
    }
}