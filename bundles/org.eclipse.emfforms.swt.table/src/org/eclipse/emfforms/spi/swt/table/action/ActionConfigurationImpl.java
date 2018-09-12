/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Mat Hansen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.table.action;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.eclipse.emfforms.common.Optional;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.swt.widgets.Control;

/**
 * A static action configuration implementation.
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 */
class ActionConfigurationImpl implements ActionConfiguration {

	private final Map<String, Action> actions;
	private final Map<String, Collection<KeyStroke>> keyStrokes;
	private final Map<String, ActionControlCreator<? extends Control>> controlCreatorCallbacks;

	/**
	 * The constructor.
	 *
	 * @param actions the action callbacks to use
	 * @param keySequences the key sequences map
	 * @param controlCreatorCallbacks the control creators to use
	 */
	ActionConfigurationImpl(Map<String, Action> actions, Map<String, Collection<KeyStroke>> keySequences,
		Map<String, ActionControlCreator<? extends Control>> controlCreatorCallbacks) {
		this.actions = actions;
		keyStrokes = keySequences;
		this.controlCreatorCallbacks = controlCreatorCallbacks;
	}

	/**
	 * Return an action callback by its action id.
	 * See {@link Action}.
	 *
	 * @param id the ID of the action to return
	 * @return the action instance or null
	 */
	@Override
	public Optional<Action> getActionById(String id) {
		return Optional.ofNullable(actions.get(id));
	}

	/**
	 * Return all actions contained in this configuration.
	 *
	 * @return a collection of actions
	 */
	@Override
	public Collection<Action> getActions() {
		return Collections.unmodifiableCollection(actions.values());
	}

	/**
	 * Returns whether key sequences for a given action exist.
	 *
	 * @param action the action to get the key sequences for.
	 * @return true in case there are key sequences, otherwise false
	 */
	@Override
	public boolean hasKeyStrokesFor(Action action) {
		return keyStrokes.containsKey(action.getId());
	}

	/**
	 * Returns the list of key sequences for a given action.
	 *
	 * @param action the action to return key sequences for.
	 * @return a collection of key sequences
	 */
	@Override
	public Collection<KeyStroke> getKeyStrokesFor(Action action) {
		return Collections.unmodifiableCollection(keyStrokes.get(action.getId()));
	}

	/**
	 * Returns whether an action has a control.
	 *
	 * @param action the action to check for a control
	 * @return true in case there is a control configured, otherwise false
	 */
	@Override
	public boolean hasControlFor(Action action) {
		return controlCreatorCallbacks.containsKey(action.getId());
	}

	/**
	 * Returns the control creator callback for a given action.
	 *
	 * @param action the action to return the callback for
	 * @return a control creator callback (if any), null otherwise
	 */
	@Override
	public ActionControlCreator<? extends Control> getControlCreator(Action action) {
		return controlCreatorCallbacks.get(action.getId());
	}

}
