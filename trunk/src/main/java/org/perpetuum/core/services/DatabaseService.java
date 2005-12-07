package org.perpetuum.core.services;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

import org.perpetuum.command.CommandFinder;

public class DatabaseService extends AbstractService {
	public static final String NAME = "DatabaseService";
	private final String DATABASE_HOME = System.getProperty("perpetuum.home") + File.separator + "data";
	
	public DatabaseService() {
		prepare(NAME);
	}
	
	public void init() {
		setDatabaseProperties();
		initializeDatabase();
	}
	
	public void initializeDatabase() {
		executeDDLIfNecessary();
	}
	
	public void executeDDLIfNecessary() {
		
	}
	
	public void setDatabaseProperties() {
		File derby = new File(System.getProperty("perpetuum.home") + 
				File.separator + "conf" + File.separator + "derby.conf");
		
		File logDir = new File(System.getProperty("perpetuum.home") + 
				File.separator + "logs");
		Properties props = new Properties();
		
		if (!logDir.exists()) {
			logDir.mkdirs();
		}
		
		if (derby.exists()) {
			try {
				props.load(new FileInputStream(derby));
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
		} else {
			URL lu = DatabaseService.class.getClassLoader().getResource("derby.conf");
			
			if (lu != null) {
				try {
					props.load(lu.openStream());
				} catch (Exception e) {
					e.printStackTrace();
					log.error(e.getMessage());
				}
			}
		}
		
		if (props.size() > 0) {
			Enumeration keys = props.keys();
			
			while (keys.hasMoreElements()) {
				String key = (String)keys.nextElement();
				
				System.setProperty(key, props.getProperty(key));
			}
		}
		
		if (System.getProperty("derby.system.home") == null) {
			System.setProperty("derby.system.home", DATABASE_HOME);
		}
		
		File dbHome = new File(DATABASE_HOME);
		CommandFinder finder = new CommandFinder(System.getProperty("perpetuum.commands.path"));
		ResourceBundle pBundle = ResourceBundle.getBundle("perpetuum");
		
		
		if (!dbHome.exists()) {
			log.info(pBundle.getString("create.dir") + " " + dbHome.getAbsolutePath());
			dbHome.mkdirs();
		} else {
			log.info(startBundle.getString("database.home.found") + ": " + dbHome.getAbsolutePath());
		}
	}

	public void start() throws Exception {
		init();
		
		log.info(startBundle.getString("database.started"));
	}

	public void stop() {
		log.info(startBundle.getString("database.stopped"));
	}
}
