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

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.bindings.keys.ParseException;
import org.eclipse.swt.widgets.Control;

/**
 * The action configuration builder.
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 *
 */
public final class ActionConfigurationBuilder {

	private final Map<String, Action> actions = new LinkedHashMap<String, Action>();
	private final Map<String, Collection<KeyStroke>> keyStrokes = new LinkedHashMap<String, Collection<KeyStroke>>();
	private final Map<String, ActionControlCreator<? extends Control>> controlCreatorCallbacks = //
		new LinkedHashMap<String, ActionControlCreator<? extends Control>>();

	/**
	 * The constructor.
	 */
	private ActionConfigurationBuilder() {
	}

	/**
	 * The alternative constructor.
	 *
	 * @param configuration a configuration to re-use
	 */
	private ActionConfigurationBuilder(ActionConfiguration configuration) {
		for (final Action action : configuration.getActions()) {
			addAction(action);
			for (final KeyStroke keyStroke : configuration.getKeyStrokesFor(action)) {
				addKeyStrokeFor(action.getId(), keyStroke);
			}
			final ActionControlCreator<? extends Control> controlCreator = //
				configuration.getControlCreator(action);
			if (controlCreator != null) {
				addControlFor(action, controlCreator);
			}
		}
	}

	/**
	 * Create an empty builder instance.
	 *
	 * @return an empty {@link ActionConfigurationBuilder}
	 */
	public static ActionConfigurationBuilder usingDefaults() {
		return new ActionConfigurationBuilder();
	}

	/**
	 * Initialize a new {@link ActionConfigurationBuilder} using a an existing {@link ActionConfiguration}.
	 *
	 * @param configuration the configuration to re-use.
	 * @return a new {@link ActionConfigurationBuilder}
	 */
	public static ActionConfigurationBuilder usingConfiguration(ActionConfiguration configuration) {
		return new ActionConfigurationBuilder(configuration);
	}

	private boolean actionExists(Action action) {
		return actions.containsKey(action.getId());
	}

	/**
	 * Add a new action to this builder.
	 *
	 * @param action the action to add
	 * @return self
	 */
	public ActionConfigurationBuilder addAction(Action action) {
		if (actionExists(action)) {
			throw new IllegalArgumentException(
				MessageFormat.format("Action with id {0} already exists", action.getId())); //$NON-NLS-1$
		}
		actions.put(action.getId(), action);
		return this;
	}

	/**
	 * Add a key sequence for a registered action.
	 *
	 * @param action the action to register a key sequence for
	 * @param sequences the key sequences to use
	 * @return self
	 */
	public ActionConfigurationBuilder addKeySequenceFor(Action action, String... sequences) {
		verifyActionExists(action);
		final Set<KeyStroke> strokes = new LinkedHashSet<KeyStroke>();
		try {
			for (final String sequence : sequences) {
				strokes.add(KeyStroke.getInstance(sequence));
			}
		} catch (final ParseException ex) {
			throw new IllegalArgumentException("Invalid key sequence"); //$NON-NLS-1$
		}
		addKeyStrokeFor(action.getId(), strokes.toArray(new KeyStroke[] {}));
		return this;
	}

	private void addKeyStrokeFor(String actionId, KeyStroke... strokes) {
		if (keyStrokes.get(actionId) == null) {
			keyStrokes.put(actionId, new LinkedHashSet<KeyStroke>());
		}
		keyStrokes.get(actionId).addAll(Arrays.asList(strokes));
	}

	private void verifyActionExists(Action action) {
		if (!actionExists(action)) {
			throw new IllegalArgumentException(
				MessageFormat.format("Action with id {0} does not exist. Use addAction() first,", action.getId())); //$NON-NLS-1$
		}
	}

	/**
	 * Add a new control creator to an action.
	 *
	 * @param action the action to register a control creator for.
	 * @param controlCreator the creator
	 * @param <Creator> a creator
	 * @return self
	 */
	public <Creator extends ActionControlCreator<? extends Control>> ActionConfigurationBuilder //
		addControlFor(Action action, Creator controlCreator) {

		verifyActionExists(action);
		controlCreatorCallbacks.put(action.getId(), controlCreator);
		return this;
	}

	/**
	 * Return an action configuration based on the current builder state.
	 *
	 * @return an action configuration
	 */
	public ActionConfiguration build() {
		return new ActionConfigurationImpl(actions, keyStrokes, controlCreatorCallbacks);
	}

}
