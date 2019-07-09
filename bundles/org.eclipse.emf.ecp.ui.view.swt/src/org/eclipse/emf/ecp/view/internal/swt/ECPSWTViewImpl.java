/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Jonas - initial API and implementation
 * Christian W. Damus - bug 527686
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.swt;

import org.eclipse.emf.ecp.ui.view.swt.ECPSWTView;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * @author Jonas
 *
 */
public class ECPSWTViewImpl implements ECPSWTView {

	private final Composite composite;
	private final ViewModelContext viewContext;

	/**
	 * @param composite the composite containing the view
	 * @param viewContext the view context of the view
	 */
	public ECPSWTViewImpl(Composite composite, ViewModelContext viewContext) {
		this.composite = composite;
		this.viewContext = viewContext;

		// I don't own this context so I cannot dispose it, but I need it while I am active
		viewContext.addContextUser(this);
	}

	@Override
	public Control getSWTControl() {
		return composite;
	}

	@Override
	public void dispose() {
		// I don't own this context, so I cannot dispose it (unless there are no other users)
		viewContext.removeContextUser(this);

		composite.dispose();
	}

	@Override
	public ViewModelContext getViewModelContext() {
		return viewContext;
	}

}
