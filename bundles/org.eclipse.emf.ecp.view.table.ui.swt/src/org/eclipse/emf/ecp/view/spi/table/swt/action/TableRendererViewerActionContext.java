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
package org.eclipse.emf.ecp.view.spi.table.swt.action;

import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emfforms.spi.swt.table.action.ViewerActionContext;
import org.eclipse.jface.viewers.AbstractTableViewer;

/**
 * The concrete action context for the table renderer.
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 * @since 1.18
 *
 */
public interface TableRendererViewerActionContext extends ViewerActionContext<AbstractTableViewer> {

	/**
	 * Returns the {@link VTableControl}.
	 *
	 * @return the {@link VTableControl}
	 */
	VTableControl getVElement();

}
