package org.codehaus.perpetuum.services;

import junit.framework.TestCase;

public class TestWebService extends TestCase {
	private WebService ws = null;

	protected void setUp() throws Exception {
		super.setUp();
        
        ws = new WebService();
        
        System.setProperty("perpetuum.debug", "true");
	}
    
    protected void tearDown() throws Exception {
        super.tearDown();
        
        if (ws.getServer() != null) {
            ws.stop();
        }
    }

	/*
	 * Test method for 'org.codehaus.perpetuum.services.WebService.init()'
	 */
	public void testInit() {
	    assertNull("Should return null if init() has not been called!", ws.getServer());
        
        ws.init();
        
        assertNotNull("Should not return null after init() has been called!", ws.getServer());
	}

	/*
	 * Test method for 'org.codehaus.perpetuum.services.WebService.getLog()'
	 */
	public void testGetLog() {
	    assertNull("Should return null!", ws.getLog());
	    
	    ws.init();
	    
	    assertNotNull("Should not return null after init()!", ws.getLog());
	}

	/*
	 * Test method for 'org.codehaus.perpetuum.services.WebService.getBundle()'
	 */
	public void testGetBundle() {
		assertNull("Should return null!", ws.getBundle());
	    
	    ws.init();
	    
	    assertNotNull("Should not return null after init()!", ws.getBundle());
	}

	/*
	 * Test method for 'org.codehaus.perpetuum.services.WebService.getPort()'
	 */
	public void testGetPort() {
	    assertEquals("Port should be 5555!", 5555, ws.getPort());
	}

	/*
	 * Test method for 'org.codehaus.perpetuum.services.WebService.setPort(int)'
	 */
	public void testSetPort() {
	    ws.setPort(5556);
        
        assertEquals("Port should be 5556!", 5556, ws.getPort());
	}

	/*
	 * Test method for 'org.codehaus.perpetuum.services.WebService.getResourceHandler()'
	 */
	public void testGetResourceHandler() {
	    assertNull("Should return null if init() has not been called!", ws.getResourceHandler());
        
        ws.init();
        
        assertNotNull("Should not return null after init() has been called!", ws.getResourceHandler());
	}

	/*
	 * Test method for 'org.codehaus.perpetuum.services.WebService.getRootContext()'
	 */
	public void testGetRootContext() {
        assertNull("Should return null if init() has not been called!", ws.getRootContext());
        
        ws.init();
        
        assertNotNull("Should not return null after init() has been called!", ws.getRootContext());
	}

	/*
	 * Test method for 'org.codehaus.perpetuum.services.WebService.getServer()'
	 */
	public void testGetServer() {
        assertNull("Should return null if init() has not been called!", ws.getServer());
        
        ws.init();
        
        assertNotNull("Should not return null after init() has been called!", ws.getServer());
	}

    /*
     * Test method for 'org.codehaus.perpetuum.services.WebService.start()'
     */
    public void testStart() {
        try {
            ws.start();
        } catch (Exception e) {
            fail("Should not fail in this environment!");
        }
        
        try {
            ws.start();
            
            fail("Should fail in this environment!");
        } catch (Exception e) {
            // Handled by test
        }
    }

    /*
     * Test method for 'org.codehaus.perpetuum.services.WebService.stop()'
     */
    public void testStop() {
        try {
            ws.stop();
            fail("Should fail in this environment!");
        } catch (Exception e) {
            // Handled by test
        }
        
        try {
            // Workaround for JUnit threading issue...
            ws.setPort(5556);
            
            ws.start();
            
            ws.stop();
        } catch (Exception e) {
            fail("Should not fail in this environment!");
        }
    }
}
