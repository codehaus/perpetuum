package org.codehaus.perpetuum.persistence;

import junit.framework.TestCase;

import org.codehaus.perpetuum.commands.Command;
import org.codehaus.perpetuum.services.WebService;
import org.codehaus.perpetuum.utils.PerpetuumUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestUserDAO extends TestCase {
    private Command start;
    private String configPath = "springStart.xml";
    private ApplicationContext factory;
    private UserDAO ud;

    protected void setUp() throws Exception {
        super.setUp();
        
        factory = new ClassPathXmlApplicationContext(configPath);
        
        ud = (UserDAO)factory.getBean("userDAO");
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        
        WebService ws = (WebService)factory.getBean("webService");
        
        ws.stop();
    }

    /*
     * Test method for 'org.codehaus.perpetuum.persistence.UserDAO.getCount()'
     */
    public void testGetCount() {
        assertEquals("There should be no users yet.", 0, ud.getCount());
    }

    /*
     * Test method for 'org.codehaus.perpetuum.persistence.UserDAO.add(Object)'
     */
    public void testAdd() {
        PerpetuumUtil.createAdminUser(factory);
        
        assertEquals("There should be 1 user.", 1, ud.getCount());
    }
}