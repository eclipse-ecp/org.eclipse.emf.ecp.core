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
 * Mat Hansen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.table.action;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 *
 * Used to create new action controls.
 *
 * @author Mat Hansen
 *
 * @param <C> the type of the control to be created
 */
public interface ActionControlCreator<C extends Control> {

	/**
	 * Creates a new control.
	 *
	 * @param parent the parent composite to create on
	 * @param action the action to bind the control to
	 * @return a new control
	 */
	C createControl(Composite parent, Action action);

}
