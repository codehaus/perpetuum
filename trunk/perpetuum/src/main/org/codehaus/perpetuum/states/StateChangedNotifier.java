package org.codehaus.perpetuum.states;

/**
 * Interface used by those that want to notify of state changes
 */
public interface StateChangedNotifier {
	/**
	 * Method for registering listeners for state changed events
	 * @param listener Listening class
	 */
	public void addListener(StateChangedListener listener);
	
	/**
	 * Method for unregistering listeners
	 * @param listener Listening class
	 */
	public void removeListener(StateChangedListener listener);
	
	/**
	 * Used to notify state changed listeners
	 * @param event StateChangedEvent
	 */
	public void notifyListeners(StateChangedEvent event);
}
