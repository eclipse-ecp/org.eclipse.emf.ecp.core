/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
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
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.swt;

import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTView;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextFactory;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;
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
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * @author Jonas
 *
 */
public class ECPSWTViewRendererImpl implements ECPSWTViewRenderer {

	/**
	 * Constructor.
	 */
	public ECPSWTViewRendererImpl() {
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
		final BundleContext bundleContext = FrameworkUtil.getBundle(getClass()).getBundleContext();
		final ServiceReference<EMFFormsRendererFactory> serviceReference = bundleContext
			.getServiceReference(EMFFormsRendererFactory.class);
		final EMFFormsRendererFactory factory = bundleContext.getService(serviceReference);

		AbstractSWTRenderer<VElement> renderer;
		try {
			renderer = factory.getRendererInstance(
				viewModelContext.getViewModel(),
				viewModelContext);
		} catch (final EMFFormsNoRendererException ex) {
			throw new ECPRendererException(ex.getMessage());
		} finally {
			bundleContext.ungetService(serviceReference);
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

	@Deprecated
	@Override
	public ECPSWTView render(Composite parent, EObject domainObject, Map<String, Object> context)
		throws ECPRendererException {
		final VViewModelProperties properties = VViewFactory.eINSTANCE.createViewModelLoadingProperties();
		for (final Entry<String, Object> entry : context.entrySet()) {
			properties.addNonInheritableProperty(entry.getKey(), entry.getValue());
		}
		return render(parent, domainObject, properties);
	}

	@Override
	public ECPSWTView render(Composite parent, EObject domainObject, VViewModelProperties properties)
		throws ECPRendererException {
		if (properties == null) {
			properties = VViewFactory.eINSTANCE.createViewModelLoadingProperties();
		}
		final VView view = ViewProviderHelper.getView(domainObject, properties);
		return render(parent, domainObject, view);
	}

}
