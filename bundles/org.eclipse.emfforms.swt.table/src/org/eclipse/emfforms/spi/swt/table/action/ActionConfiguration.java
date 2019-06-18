/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Mat Hansen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.table.action;

import java.util.Collection;

import org.eclipse.emfforms.common.Optional;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.swt.widgets.Control;

/**
 * The action configuration interface.
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 */
public interface ActionConfiguration {

	/**
	 * Return an action callback by its action id.
	 * See {@link Action}.
	 *
	 * @param id the ID of the action to return
	 * @return the action instance or null
	 */
	Optional<Action> getActionById(String id);

	/**
	 * Return all actions contained in this configuration.
	 *
	 * @return a collection of actions
	 */
	Collection<Action> getActions();

	/**
	 * Returns whether key sequences for a given action exist.
	 *
	 * @param action the action to get the key sequences for.
	 * @return true in case there are key sequences, otherwise false
	 */
	boolean hasKeyStrokesFor(Action action);

	/**
	 * Returns the list of key sequences for a given action.
	 *
	 * @param action the action to return key sequences for.
	 * @return a collection of key sequences
	 */
	Collection<KeyStroke> getKeyStrokesFor(Action action);

	/**
	 * Returns whether an action has a control.
	 *
	 * @param action the action to check for a control
	 * @return true in case there is a control configured, otherwise false
	 */
	boolean hasControlFor(Action action);

	/**
	 * Returns the control creator callback for a given action.
	 *
	 * @param action the action to return the callback for
	 * @return a control creator callback (if any), null otherwise
	 */
	ActionControlCreator<? extends Control> getControlCreator(Action action);

}
