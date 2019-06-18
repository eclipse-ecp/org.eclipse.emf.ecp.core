/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.swt;

// import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;
import org.eclipse.swt.widgets.Control;

/**
 * @author Alexandra Buzila
 * @deprecated use the GenericEditor instead
 */
@Deprecated
public abstract class ContextMenuViewModelService implements ViewModelService {

	private Control parentControl;

	/**
	 * Set the control to which the context menu should be added.
	 *
	 * @param parentControl - the control
	 */
	public void setParentControl(Control parentControl) {
		this.parentControl = parentControl;
	}

	/**
	 * Get the control to which the context menu should be added.
	 *
	 * @return the control
	 */
	public Control getParentControl() {
		return parentControl;
	}

	/**
	 * Implement to register the context menu to the parent control.
	 * 
	 * @return true if registration was successful
	 */
	public abstract boolean registerContextMenu();

}
