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

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emfforms.common.Optional;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.AbstractTableViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * The table control action bar.
 *
 * @param <V> the concrete viewer type
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 * @since 1.18
 *
 */
public class TableActionBar<V extends AbstractTableViewer> implements ActionBar<V> {

	private final ActionConfiguration configuration;

	private final Map<String, Control> controls = new LinkedHashMap<String, Control>();

	/**
	 * The constructor.
	 *
	 * @param configuration the {@link ActionConfiguration} to use
	 */
	public TableActionBar(ActionConfiguration configuration) {
		super();
		this.configuration = configuration;
	}

	@Override
	public void fillComposite(Composite composite, V viewer) {

		for (final Action action : configuration.getActions()) {
			if (configuration.hasControlFor(action)) {
				final ActionControlCreator<? extends Control> controlCreator = //
					configuration.getControlCreator(action);
				final Control control = controlCreator.createControl(composite, action);
				addControl(action.getId(), control);
			}
		}
		applyLayout(composite);
	}

	/**
	 * Applies a layout to the action bar composite.
	 *
	 * @param composite the composite to apply a layout to
	 */
	protected void applyLayout(Composite composite) {
		GridLayoutFactory.fillDefaults().numColumns(getControlCount())
			.equalWidth(false).applyTo(composite);
	}

	/**
	 * Returns a list of known actions.
	 *
	 * @return list of actions
	 */
	protected Collection<Action> getActions() {
		return Collections.unmodifiableCollection(configuration.getActions());
	}

	/**
	 * Add a control to the action bar.
	 *
	 * @param id the ID of the control (typically the same as the ID of the bound action)
	 * @param control the control instance
	 */
	protected void addControl(String id, Control control) {
		if (controls.containsKey(id)) {
			throw new IllegalArgumentException(
				MessageFormat.format("A control with id {0} already exists", id)); //$NON-NLS-1$
		}
		controls.put(id, control);
	}

	@Override
	public Optional<Control> getControlById(String id) {
		return Optional.ofNullable(controls.get(id));
	}

	@Override
	public int getControlCount() {
		return controls.size();
	}

	@Override
	public int getActionCount() {
		return controls.size();
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Invokes canExecute() on all bound actions and updates the enabled state for each control accordingly.
	 */
	@Override
	public void updateActionBar() {
		for (final Action action : configuration.getActions()) {
			boolean canExecute = false;
			try {
				canExecute = action.canExecute();
			} finally {
				final Optional<Control> control = getControlById(action.getId());
				if (control.isPresent()) {
					control.get().setEnabled(canExecute);
				}
			}
		}
	}

}