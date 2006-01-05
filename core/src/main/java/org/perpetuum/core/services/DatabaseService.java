package org.perpetuum.core.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.derby.jdbc.EmbeddedDataSource;
import org.perpetuum.command.CommandFinder;

public class DatabaseService extends AbstractService {
	public static final String NAME = "DatabaseService";
	private final String DATABASE_HOME = System.getProperty("perpetuum.home") + File.separator + "data";
	private EmbeddedDataSource dataSource = null;
	private Connection conn = null;
	
	/**
     * SQLSTATE String that according to Derby documentation means
     * that the table queried does not exist.
     */
    private static final String TABLE_DOES_NOT_EXIST = "42X05";
	
	public DatabaseService() {
		prepare(NAME);
	}
	
	public void init() throws Exception {
		setDatabaseProperties();
		initializeDatabase();
	}
	
	public void initializeDatabase() throws Exception {
		dataSource = new EmbeddedDataSource();
		
		dataSource.setCreateDatabase("create");
		dataSource.setDatabaseName("PERPETUUM");
		
		conn = dataSource.getConnection();
		
		executeDDLIfNecessary();
	}
	
	public void executeDDLIfNecessary() {
		Statement stmt = null;
		String[] tables = new String[] { "USERS", "REALMS", "ROLES", "REALM_USERS" };
		
		for (int i = 0; i < tables.length; i++) {
			try {
				stmt = conn.createStatement();
				
				stmt.executeQuery("SELECT COUNT(1) FROM " + tables[i]);
			} catch (SQLException sqle) {
				String state = sqle.getSQLState();
				
				if (state.equals(TABLE_DOES_NOT_EXIST)) {
					StringBuffer ddl = new StringBuffer();
					InputStream in = DatabaseService.class.getClassLoader().getResourceAsStream("ddl/" + tables[i].toLowerCase() + ".ddl");
					BufferedReader bin = new BufferedReader(new InputStreamReader(in));
					String cLine;
					
					try {
						while ((cLine = bin.readLine()) != null) {
							ddl.append(cLine);
						}
					} catch (IOException ioe) {
						log.error(ioe.getMessage());
						ioe.printStackTrace();
					} finally {
						try {
							in.close();
						} catch (IOException ioe) {
							log.error(ioe.getMessage());
							ioe.printStackTrace();
						}
					}
					
					if (ddl.length() > 0) {
						try {
							stmt = conn.createStatement();
							
							stmt.executeUpdate(ddl.toString());
							
							stmt.close();
						} catch (SQLException sqle2) {
							log.error(sqle2);
							sqle2.printStackTrace();
						}
					}
				}
			} finally {
				try {
					stmt.close();
				} catch (SQLException sqle) {
					log.error(sqle.getMessage());
					sqle.printStackTrace();
				}
			}
		}
	}
	
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

	public void start() throws Exception {
		init();
		
		log.info(bundle.getString("database.started"));
		status = Service.STARTED;
	}

	public void stop() {
		status = Service.STOPPED;
		log.info(bundle.getString("database.stopped"));
	}
}
