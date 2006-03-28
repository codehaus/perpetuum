package org.codehaus.perpetuum.services;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.codehaus.perpetuum.states.State;
import org.codehaus.perpetuum.states.StateChangedEvent;
import org.codehaus.perpetuum.states.StateChangedListener;
import org.codehaus.perpetuum.states.StateChangedNotifier;

/**
 * AbstractService is the class that all Services should extend.  This class 
 * takes care of the mundane interface implementations.
 */
public abstract class AbstractService implements Service, StateChangedNotifier, StateChangedListener {
	private Log log = null;
	private ResourceBundle bundle = null;
	private State state = State.STOPPED;
	private ArrayList<StateChangedListener> listeners = new ArrayList<StateChangedListener>();
	
	/**
	 * Sets the log
	 */
	public void setLog(Log log) {
		this.log = log;
	}
	
	/**
	 * Sets the bundle
	 */
	public void setResourceBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}
	
	/**
	 * Used to get a reference to the Log object
	 * @return log
	 */
	public Log getLog() {
		return log;
	}
	
	/**
	 * Used to get a reference to the ResourceBundle object
	 * @return bundle
	 */
	public ResourceBundle getBundle() {
		return bundle;
	}
	
	/**
	 * @see org.codehaus.perpetuum.states.StateChangedNotifier#addListener(org.codehaus.perpetuum.states.StateChangedListener)
	 */
	public void addListener(StateChangedListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	/**
	 * @see org.codehaus.perpetuum.states.StateChangedNotifier#removeListener(org.codehaus.perpetuum.states.StateChangedListener)
	 */
	public void removeListener(StateChangedListener listener) {
		if (listeners.contains(listener)) {
			listeners.remove(listener);
		}
	}

	/**
	 * @see org.codehaus.perpetuum.states.StateChangedNotifier#notifyListeners(org.codehaus.perpetuum.states.StateChangedEvent)
	 */
	public void notifyListeners(StateChangedEvent event) {
		for (StateChangedListener listener : listeners) {
			listener.stateChanged(event);
		}
	}

	/**
	 * @see org.codehaus.perpetuum.states.StateChangedListener#stateChanged(org.codehaus.perpetuum.states.StateChangedEvent)
	 */
	public void stateChanged(StateChangedEvent event) {
		ResourceBundle pBundle = ResourceBundle.getBundle("perpetuum");
		getLog().info(MessageFormat.format(pBundle.getString("perpetuum.service.transition"), new Object[] { event.getClazz().getName().substring(getClass().getName().lastIndexOf(".") + 1), event.getFromState(), event.getToState() }));
		
		notifyListeners(event);
	}

	/**
	 * @return Returns the state.
	 */
	public State getState() {
		return state;
	}

	/**
	 * @param state The state to set.
	 */
	public void setState(State state) {
		stateChanged(new StateChangedEvent(getClass(), getState(), state));
		
		this.state = state;
	}
}
