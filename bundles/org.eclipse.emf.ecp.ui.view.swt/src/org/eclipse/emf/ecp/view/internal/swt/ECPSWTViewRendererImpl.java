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

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTView;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextFactory;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.provider.ViewProviderHelper;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.swt.reporting.InvalidGridDescriptionReport;
import org.eclipse.emf.ecp.view.spi.swt.reporting.RenderingFailedReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.EMFFormsNoRendererException;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererFactory;
import org.eclipse.emfforms.spi.swt.core.layout.GridDescriptionFactory;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Jonas
 *
 */
public class ECPSWTViewRendererImpl implements ECPSWTViewRenderer {

	private final EMFFormsRendererFactory factory;

	/**
	 * Constructor.
	 */
	public ECPSWTViewRendererImpl() {
		factory = Activator.getDefault().getEMFFormsRendererFactory();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer#render(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public ECPSWTView render(Composite parent, EObject domainObject) throws ECPRendererException {
		return render(parent, domainObject, ViewProviderHelper.getView(domainObject, null));
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
		AbstractSWTRenderer<VElement> renderer;
		try {
			renderer = factory.getRendererInstance(
				viewModelContext.getViewModel(),
				viewModelContext);
		} catch (final EMFFormsNoRendererException ex) {
			throw new ECPRendererException(ex.getMessage());
		}

		final ReportService reportService = Activator.getDefault().getReportService();

		final SWTGridDescription gridDescription = renderer.getGridDescription(GridDescriptionFactory.INSTANCE
			.createEmptyGridDescription());
		if (gridDescription.getGrid().size() != 1) {
			reportService.report(
				new InvalidGridDescriptionReport("Invalid number of cells, expected exactly one cell!")); //$NON-NLS-1$
			// TODO: RS
			// do sth. if wrong number of controls
			// throw new IllegalStateException("Invalid number of cells, expected exactly one cell!"); //$NON-NLS-1$
		}

		// a view returns always a composite and always only one row with one control
		ECPSWTView swtView = null;
		try {
			final Composite composite = (Composite) renderer.render(gridDescription.getGrid().get(0), parent);
			renderer.finalizeRendering(parent);
			final GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
			composite.setLayoutData(gridData);
			swtView = new ECPSWTViewImpl(composite, viewModelContext);
		} catch (final NoRendererFoundException e) {
			reportService.report(new RenderingFailedReport(e));
		} catch (final NoPropertyDescriptorFoundExeption e) {
			reportService.report(new RenderingFailedReport(e));
		}

		return swtView;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer#render(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.emf.ecore.EObject, java.util.Map)
	 */
	@Override
	public ECPSWTView render(Composite parent, EObject domainObject, Map<String, Object> context)
		throws ECPRendererException {
		final VView view = ViewProviderHelper.getView(domainObject, context);
		return render(parent, domainObject, view);
	}

	// /**
	// * Returns the {@link SWTRendererFactory} used to obtain any SWT renderer.
	// * Clients may override.
	// *
	// * @return the {@link SWTRendererFactory}
	// */
	// protected SWTRendererFactory createFactory() {
	// return new SWTRendererFactoryImpl();
	// }
}
