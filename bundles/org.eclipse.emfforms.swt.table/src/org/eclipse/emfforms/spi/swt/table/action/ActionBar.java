/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 * Mat Hansen - action bar refactorings
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.table.action;

import org.eclipse.emfforms.common.Optional;
import org.eclipse.jface.viewers.AbstractTableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * Callback interface for the creation of action bar composites.
 *
 * @param <V> the viewer type
 * @author Johannes Faltermeier
 *
 */
public interface ActionBar<V extends Viewer> {

	/**
	 * This method is called to fill the given {@link Composite} with action controls.
	 *
	 * @param composite the composite
	 * @param viewer the {@link AbstractTableViewer}
	 */
	void fillComposite(Composite composite, V viewer);

	/**
	 * Updates the controls of this action bar. For instance, this can include enabling or disabling controls dependent
	 * on whether actions can be executed.
	 */
	void updateActionBar();

	/**
	 * Get a control by its action ID.
	 *
	 * @param id the action ID to get the control for
	 * @return the control for the given action ID (if any)
	 */
	Optional<Control> getControlById(String id);

	/**
	 * Get the number of currently registered controls.
	 *
	 * @return the number of controls
	 */
	int getControlCount();

	/**
	 * Get the number of currently registered actions.
	 *
	 * @return the number of actions
	 */
	int getActionCount();
}