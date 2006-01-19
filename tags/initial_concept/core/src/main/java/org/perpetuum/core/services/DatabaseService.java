package org.perpetuum.core.services;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.Connection;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.perpetuum.command.CommandFinder;

/**
 * This class is used to manage the database object used as the backend for all 
 * persisted data for Perpetuum
 */
public class DatabaseService extends AbstractService {
	private final String DATABASE_HOME = System.getProperty("perpetuum.home") + File.separator + "data";
	private final String DATABASE_NAME = "PERPETUUM";
	private Connection conn = null;
	private Configuration cfg = null;
	private SessionFactory sf = null;
	
	/**
	 * Constructor
	 */
	public DatabaseService() {
		prepare(DatabaseService.class);
	}
	
	/*
	 *  (non-Javadoc)
	 * @see org.perpetuum.core.services.Service#init()
	 */
	public void init() throws Exception {
		setDatabaseProperties();
		initializeORM();
	}
	
	/**
	 * Returns an instance of Hibernate's SessionFactory
	 * @return SessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sf;
	}
	
	/**
	 * Initializes ORM System
	 * @throws Exception
	 */
	public void initializeORM() throws Exception {
		cfg = new Configuration();
		
		cfg.setProperty("hibernate.dialect", "org.hibernate.dialect.DerbyDialect");
		cfg.setProperty("hibernate.connection.url", "jdbc:derby:" + DATABASE_NAME + ";create=true");
		cfg.setProperty("hibernate.connection.driver_class", "org.apache.derby.jdbc.EmbeddedDriver");
		cfg.setProperty("hibernate.hbm2ddl.auto", "update");
		// cfg.setProperty("hibernate.show_sql", "true");
		
		cfg.addResource("META-INF/perpetuum/mapping/mapping.conf");
		
		sf = cfg.buildSessionFactory();
	}
	
	/**
	 * Sets database properties
	 * @throws Exception
	 */
	public void setDatabaseProperties() throws Exception {
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
		}
		
		log.info(bundle.getString("database.home.found") + ": " + dbHome.getAbsolutePath());
		
		if (System.getProperty("derby.stream.error.file") == null) {
			System.setProperty("derby.stream.error.file", System.getProperty("perpetuum.logs.home") + File.separator + "derby.log");
		}
	}

	/*
	 *  (non-Javadoc)
	 * @see org.perpetuum.core.services.Service#start()
	 */
	public void start() throws Exception {
		init();
		
		log.info(bundle.getString("database.started"));
		status = Service.STARTED;
	}

	/*
	 *  (non-Javadoc)
	 * @see org.perpetuum.core.services.Service#stop()
	 */
	public void stop() {
		sf.close();
		
		status = Service.STOPPED;
		log.info(bundle.getString("database.stopped"));
	}
}
