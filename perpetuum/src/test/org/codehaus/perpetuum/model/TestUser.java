package org.codehaus.perpetuum.model;

import junit.framework.TestCase;

public class TestUser extends TestCase {
    private User u;
    
    private static final String DEFAULT_EMAIL = "jcscoobyrs@perpetuum.codehaus.org";
    private static final boolean DEFAULT_ENABLED = true;
    private static final Long DEFAULT_ID = 1L;
    private static final String DEFAULT_PASSWORD = "password";
    private static final String DEFAULT_REALNAME = "Jeremy Whitlock";
    private static final String DEFAULT_USERNAME = "jcscoobyrs";

    protected void setUp() throws Exception {
        super.setUp();
        
        u = new User();
        
        u.setEmail(DEFAULT_EMAIL);
        u.setEnabled(DEFAULT_ENABLED);
        u.setId(DEFAULT_ID);
        u.setPassword(DEFAULT_PASSWORD);
        u.setRealname(DEFAULT_REALNAME);
        u.setUsername(DEFAULT_USERNAME);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /*
     * Test method for 'org.codehaus.perpetuum.model.User.getEmail()'
     */
    public void testGetEmail() {
        assertEquals("Default email should be " + DEFAULT_EMAIL + ".", DEFAULT_EMAIL, u.getEmail());
    }

    /*
     * Test method for 'org.codehaus.perpetuum.model.User.setEmail(String)'
     */
    public void testSetEmail() {
        String nEmail = "jcscoobyrs@codehaus.org";
        
        u.setEmail(nEmail);
        
        assertEquals("New email should be " + nEmail + ".", nEmail, u.getEmail());
    }

    /*
     * Test method for 'org.codehaus.perpetuum.model.User.isEnabled()'
     */
    public void testIsEnabled() {
        assertEquals("Default enablement should be " + DEFAULT_ENABLED + ".", DEFAULT_ENABLED, u.isEnabled());
    }

    /*
     * Test method for 'org.codehaus.perpetuum.model.User.setEnabled(boolean)'
     */
    public void testSetEnabled() {
        boolean nEnabled = false;
        
        u.setEnabled(nEnabled);
        
        assertEquals("New enablement should be " + nEnabled + ".", nEnabled, u.isEnabled());
    }

    /*
     * Test method for 'org.codehaus.perpetuum.model.User.getId()'
     */
    public void testGetId() {
        assertEquals("Default id should be " + DEFAULT_ID + ".", DEFAULT_ID, u.getId());
    }

    /*
     * Test method for 'org.codehaus.perpetuum.model.User.setId(Long)'
     */
    public void testSetId() {
        Long nId = 2L;
        
        u.setId(nId);
        
        assertEquals("New id should be " + nId + ".", nId, u.getId());
    }

    /*
     * Test method for 'org.codehaus.perpetuum.model.User.getPassword()'
     */
    public void testGetPassword() {
        assertEquals("Default password should be " + DEFAULT_PASSWORD + ".", DEFAULT_PASSWORD, u.getPassword());
    }

    /*
     * Test method for 'org.codehaus.perpetuum.model.User.setPassword(String)'
     */
    public void testSetPassword() {
        String nPassword = "newpassword";
        
        u.setPassword(nPassword);
        
        assertEquals("New password should be " + nPassword + ".", nPassword, u.getPassword());
    }

    /*
     * Test method for 'org.codehaus.perpetuum.model.User.getUsername()'
     */
    public void testGetUsername() {
        assertEquals("Default username should be " + DEFAULT_USERNAME + ".", DEFAULT_USERNAME, u.getUsername());
    }

    /*
     * Test method for 'org.codehaus.perpetuum.model.User.setUsername(String)'
     */
    public void testSetUsername() {
        String nUsername = "admin";
        
        u.setUsername(nUsername);
        
        assertEquals("New username should be " + nUsername + ".", nUsername, u.getUsername());
    }

    /*
     * Test method for 'org.codehaus.perpetuum.model.User.getRealname()'
     */
    public void testGetRealname() {
        assertEquals("Default real name should be " + DEFAULT_REALNAME + ".", DEFAULT_REALNAME, u.getRealname());
    }

    /*
     * Test method for 'org.codehaus.perpetuum.model.User.setRealname(String)'
     */
    public void testSetRealname() {
        String nRealname = "Perpetuum Admin";
        
        u.setRealname(nRealname);
        
        assertEquals("New real name should be " + nRealname + ".", nRealname, u.getRealname());
    }
}
