/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Jonas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.ui.view.swt;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.swt.widgets.Control;

/**
 * This is the result of a Rendering call.
 * It contains the created SWT Control and the {@link ViewModelContext}.
 *
 * @author Jonas
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface ECPSWTView {

	/**
	 * @return the root SWT Control of this view.
	 */
	Control getSWTControl();

	/**
	 * Disposes the view.
	 */
	void dispose();

	/**
	 * Returns the {@link ViewModelContext} which was used to create the current view.
	 *
	 * @return the {@link ViewModelContext}
	 * @since 1.2
	 */
	ViewModelContext getViewModelContext();
}
