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
package org.eclipse.emfforms.internal.swt.table.action;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import org.eclipse.emfforms.spi.swt.table.action.Action;
import org.eclipse.emfforms.spi.swt.table.action.ActionConfiguration;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;

/**
 * Key binding management class for viewer actions.
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 * @since 1.18
 *
 */
public class KeyBindingManager {

	/**
	 * Internal key binding adapter.
	 */
	private class ActionKeyBinding {

		private final KeyStroke keyStroke;
		private final Action action;

		ActionKeyBinding(Action action, KeyStroke keyStroke) {
			this.action = action;
			this.keyStroke = keyStroke;
		}

		public void keyPressed(KeyEvent e) {
			final KeyStroke current = KeyStroke.getInstance(e.stateMask, e.keyCode);
			if (keyStroke.equals(current)) {
				invokeAction();
			}
		}

		private void invokeAction() {
			if (action.canExecute()) {
				action.execute();
			}
		}

	}

	private final Collection<ActionKeyBinding> keyBindings = new LinkedList<ActionKeyBinding>();
	private final Map<Viewer, KeyListener> viewerBindings = new LinkedHashMap<Viewer, KeyListener>();

	/**
	 * Bind a new key binding to an {@link Action}.
	 *
	 * @param action the action to bind
	 * @param keyStroke the key stroke to bind to
	 */
	public void bindAction(Action action, KeyStroke keyStroke) {
		keyBindings.add(new ActionKeyBinding(action, keyStroke));
	}

	/**
	 * Apply action bindings stored within an {@link ActionConfiguration}.
	 *
	 * @param configuration the {@link ActionConfiguration} to apply
	 */
	public void applyActionConfiguration(ActionConfiguration configuration) {
		for (final Action action : configuration.getActions()) {
			if (configuration.hasKeyStrokesFor(action)) {
				for (final KeyStroke keyStroke : configuration.getKeyStrokesFor(action)) {
					bindAction(action, keyStroke);
				}
			}
		}
	}

	/**
	 * Bind all known actions to a viewer.
	 *
	 * @param viewer the viewer to bind to
	 */
	public void bindToViewer(Viewer viewer) {
		if (viewerBindings.containsKey(viewer)) {
			throw new IllegalArgumentException("Already bound to the given viewer"); //$NON-NLS-1$
		}

		final KeyAdapter listener = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				for (final ActionKeyBinding binding : keyBindings) {
					binding.keyPressed(e);
				}
			}
		};
		viewer.getControl().addKeyListener(listener);
		viewerBindings.put(viewer, listener);
	}

	/**
	 * Unbind from viewer.
	 *
	 * @param viewer the viewer to unbind from
	 */
	public void unbindFromViewer(Viewer viewer) {
		if (!viewerBindings.containsKey(viewer)) {
			return; // silently ignore this
		}
		final KeyListener listener = viewerBindings.get(viewer);
		viewer.getControl().removeKeyListener(listener);
		viewerBindings.remove(viewer);
	}

}
