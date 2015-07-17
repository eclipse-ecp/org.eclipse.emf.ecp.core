/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
 *
 */
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

	public abstract boolean registerContextMenu();

}
