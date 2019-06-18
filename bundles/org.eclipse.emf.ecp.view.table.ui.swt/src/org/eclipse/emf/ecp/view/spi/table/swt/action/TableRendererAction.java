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
package org.eclipse.emf.ecp.view.spi.table.swt.action;

import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emfforms.spi.swt.table.action.AbstractTableAction;
import org.eclipse.emfforms.spi.swt.table.action.ViewerActionContext;
import org.eclipse.jface.viewers.AbstractTableViewer;

/**
 * Common base class for table renderer actions.
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 * @since 1.18
 *
 */
public abstract class TableRendererAction extends AbstractTableAction<AbstractTableViewer> {

	/**
	 * The constructor.
	 *
	 * @param actionContext the {@link ViewerActionContext}
	 */
	public TableRendererAction(TableRendererViewerActionContext actionContext) {
		super(actionContext);
	}

	/**
	 * Returns true when the table is disabled.
	 *
	 * Note: EMF Forms distinguishes between read-only and enabled.
	 * Read-only is a declarative state defined by the view model and cannot
	 * be overwritten during runtime whereas the enabled state can.
	 * Therefore we only have to check for the enabled state here.
	 *
	 * @return true when the table is disabled, false otherwise
	 */
	@Override
	protected boolean isTableDisabled() {
		final VTableControl vTableControl = getVTableControl();
		return !vTableControl.isEffectivelyEnabled();
	}

	@Override
	public TableRendererViewerActionContext getActionContext() {
		return TableRendererViewerActionContext.class.cast(super.getActionContext());
	}

	/**
	 * Returns the table control VElement.
	 *
	 * @return the {@link VTableControl}
	 */
	protected VTableControl getVTableControl() {
		return VTableControl.class.cast(getActionContext().getVElement());
	}

}
