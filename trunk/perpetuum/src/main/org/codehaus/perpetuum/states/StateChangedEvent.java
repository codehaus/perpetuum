package org.codehaus.perpetuum.states;

/**
 * Class used to generate a state changed notification
 */
public class StateChangedEvent {
	private Class clazz;
	private States fromState;
	private States toState;

	public StateChangedEvent(Class clazz, States fromState, States toState) {
		this.clazz = clazz;
		this.fromState = fromState;
		this.toState = toState;
	}

	/**
	 * @return Returns the clazz.
	 */
	public Class getClazz() {
		return clazz;
	}

	/**
	 * @return Returns the fromState.
	 */
	public States getFromState() {
		return fromState;
	}

	/**
	 * @return Returns the toState.
	 */
	public States getToState() {
		return toState;
	}
}
