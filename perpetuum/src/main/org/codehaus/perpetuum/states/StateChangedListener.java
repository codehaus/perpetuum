package org.codehaus.perpetuum.states;

/**
 * Interface for those wanting to be notified when a state changes
 */
public interface StateChangedListener {
	/**
	 * Used to listen for a state change
	 * @param event StateChangedEvent
	 */
	public void stateChanged(StateChangedEvent event);
}
