package org.codehaus.perpetuum.states;

import junit.framework.TestCase;

public class TestStateChangedEvent extends TestCase {
	private StateChangedEvent sce;

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		
		sce = new StateChangedEvent(getClass(), State.STOPPED, State.STARTING);
	}

	public void testGetClazz() {
		assertEquals("The classes should be the same!", getClass(), sce.getClazz());
	}
	
	public void testGetFromState() {
		assertEquals("The from states should be the same!", State.STOPPED, sce.getFromState());
	}
	
	public void testGetToState() {
		assertEquals("The to states should be the same!", State.STARTING, sce.getToState());
	}
}
