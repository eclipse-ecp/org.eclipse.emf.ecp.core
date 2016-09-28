/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Martin Fleck - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.editor.genmodel.service;

import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;

/**
 * This {@link ViewModelService} sets all views not related to the {@link GenModelPackage} to read-only.
 * This avoids, for instance, that the user can make changes to the referenced Ecore model shown in the GenModel editor.
 *
 * @author Martin Fleck
 *
 */
public class GenModelReadonlyViewModelService implements ViewModelService {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#instantiate(org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public void instantiate(ViewModelContext context) {
		final VElement viewModel = context.getViewModel();
		if (!(viewModel instanceof VView)) {
			return; // this service only works on views
		}

		final VView view = (VView) viewModel;
		if (!GenModelPackage.eNS_URI.equals(view.getRootEClass().getEPackage().getNsURI())) {
			view.setReadonly(true);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#dispose()
	 */
	@Override
	public void dispose() {
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#getPriority()
	 */
	@Override
	public int getPriority() {
		return 0;
	}

}
