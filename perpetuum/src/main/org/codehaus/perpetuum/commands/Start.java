package org.codehaus.perpetuum.commands;

import java.io.File;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.perpetuum.model.User;
import org.codehaus.perpetuum.persistence.UserDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Start extends AbstractCommand {
	private Log log = LogFactory.getLog(getClass());
	private ResourceBundle bundle = ResourceBundle.getBundle(Command.RESOURCE_PATH + getClass().getName().substring(getClass().getName().lastIndexOf(".") + 1).toLowerCase());
	private ApplicationContext factory;
    
	/**
	 * @see org.codehaus.perpetuum.commands.AbstractCommand#run()
	 */
	public void run() {
        String configPath = System.getProperty("perpetuum.home") + File.separator + "conf" + File.separator + "springStart.xml";
        
        if (!new File(configPath).exists()) {
            configPath = "springStart.xml";
        }
        
        if (!new File(configPath).exists()) {
        	System.out.println(MessageFormat.format(getPBundle().getString("perpetuum.warning.not.found"), new Object[] {  new File(configPath).getAbsolutePath() }));
            
            return;	
        }
        
		factory = new FileSystemXmlApplicationContext(configPath);
        
        createAdminUser();
	}
    
    /**
     * Creates administrator account if not present
     */
    public void createAdminUser() {
        UserDAO ud = (UserDAO)factory.getBean("userDAO");
        
        if (ud.getCount() < 1) {
            User admin = new User();
            
            admin.setUsername("admin");
            admin.setPassword("perpetuum");
            admin.setEmail("invalid@perpetuum.codehaus.org");
            admin.setRealname("Perpetuum Administrator");
            admin.setEnabled(true);
            
            ud.add(admin);
        }
    }
    
	/**
	 * @see org.codehaus.perpetuum.commands.AbstractCommand#getLog()
	 */
	public Log getLog() {
		return log;
	}
	
	/**
	 * @see org.codehaus.perpetuum.commands.AbstractCommand#getBundle()
	 */
	public ResourceBundle getBundle() {
		return bundle;
	}
}
