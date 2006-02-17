package org.codehaus.perpetuum.states;

/**
 * Class used to generate a state changed notification
 */
public class StateChangedEvent {
	private Class clazz;
	private State fromState;
	private State toState;

	public StateChangedEvent(Class clazz, State fromState, State toState) {
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
	public State getFromState() {
		return fromState;
	}

	/**
	 * @return Returns the toState.
	 */
	public State getToState() {
		return toState;
	}
}
