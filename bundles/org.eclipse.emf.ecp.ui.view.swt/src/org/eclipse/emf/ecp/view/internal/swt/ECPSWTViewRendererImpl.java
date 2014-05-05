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
package org.eclipse.emf.ecp.view.internal.swt;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTView;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextFactory;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.provider.ViewProviderHelper;
import org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer;
import org.eclipse.emf.ecp.view.spi.swt.SWTRendererFactory;
import org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridDescription;
import org.eclipse.emf.ecp.view.spi.swt.layout.GridDescriptionFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Jonas
 * 
 */
public class ECPSWTViewRendererImpl implements ECPSWTViewRenderer {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer#render(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public ECPSWTView render(Composite parent, EObject domainObject) throws ECPRendererException {
		return render(parent, domainObject, ViewProviderHelper.getView(domainObject));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer#render(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecp.view.spi.model.VView)
	 */
	@Override
	public ECPSWTView render(Composite parent, EObject domainObject, VView viewModel) throws ECPRendererException {
		final ViewModelContext viewContext = ViewModelContextFactory.INSTANCE.createViewModelContext(viewModel,
			domainObject);
		return render(parent, viewContext);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer#render(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public ECPSWTView render(Composite parent, ViewModelContext viewModelContext) throws ECPRendererException {
		final SWTRendererFactory factory = new SWTRendererFactoryImpl();
		final AbstractSWTRenderer<VElement> renderer = factory.getRenderer(
			viewModelContext.getViewModel(),
			viewModelContext);
		final SWTGridDescription gridDescription = renderer.getGridDescription(GridDescriptionFactory.INSTANCE
			.createEmptyGridDescription());
		if (gridDescription.getGrid().size() != 1) {
			// do sth. if wrong number of controls
			throw new IllegalStateException("Invalid number of cells, expected exactly one cell!"); //$NON-NLS-1$
		}
		// a view returns always a composite and always only one row with one control
		final Composite composite = (Composite) renderer.render(gridDescription.getGrid().get(0), parent);
		renderer.finalizeRendering(parent);

		final GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		composite.setLayoutData(gridData);

		final ECPSWTView swtView = new ECPSWTViewImpl(composite, viewModelContext);
		return swtView;
	}

}
