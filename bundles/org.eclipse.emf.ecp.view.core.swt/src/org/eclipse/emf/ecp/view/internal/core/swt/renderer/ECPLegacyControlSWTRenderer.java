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
 * Edagr Mueller - initial API and implementation
 * Eugen Neufeld - Refactoring
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.core.swt.renderer;

import org.eclipse.core.databinding.Binding;
import org.eclipse.emf.ecp.edit.internal.swt.util.SWTControl;
import org.eclipse.emf.ecp.edit.spi.ECPControlFactory;
import org.eclipse.emf.ecp.view.internal.core.swt.Activator;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * Renderer for {@link org.eclipse.swt.widgets.Control Controls}.
 * This renderer will be removed with the next major release.
 *
 * @author Eugen Neufeld
 *
 */
@Deprecated
public class ECPLegacyControlSWTRenderer extends SimpleControlSWTControlSWTRenderer {

	private static final EMFFormsDatabinding EMFFORMS_DATABINDING;
	private static final EMFFormsLabelProvider EMFFORMS_LABELPROVIDER;
	private static final VTViewTemplateProvider VTVIEW_TEMPLATEPROVIDER;

	static {
		final BundleContext bundleContext = FrameworkUtil.getBundle(ECPLegacyControlSWTRenderer.class)
			.getBundleContext();
		final ServiceReference<EMFFormsDatabinding> emfFormsDatabindingServiceReference = bundleContext
			.getServiceReference(EMFFormsDatabinding.class);
		EMFFORMS_DATABINDING = bundleContext.getService(emfFormsDatabindingServiceReference);
		final ServiceReference<EMFFormsLabelProvider> emfFormsLabelProviderServiceReference = bundleContext
			.getServiceReference(EMFFormsLabelProvider.class);
		EMFFORMS_LABELPROVIDER = bundleContext.getService(emfFormsLabelProviderServiceReference);
		final ServiceReference<VTViewTemplateProvider> vtViewTemplateProviderServiceReference = bundleContext
			.getServiceReference(VTViewTemplateProvider.class);
		VTVIEW_TEMPLATEPROVIDER = bundleContext.getService(vtViewTemplateProviderServiceReference);
	}

	/**
	 * Default constructor.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService The {@link ReportService}
	 */
	public ECPLegacyControlSWTRenderer(VControl vElement, ViewModelContext viewContext,
		ReportService reportService) {
		super(vElement, viewContext, reportService, EMFFORMS_DATABINDING, EMFFORMS_LABELPROVIDER,
			VTVIEW_TEMPLATEPROVIDER);
	}

	private SWTControl control;

	@Override
	protected Control createSWTControl(Composite parent) {
		final ECPControlFactory controlFactory = Activator.getDefault().getECPControlFactory();

		if (controlFactory == null) {
			Activator.getDefault().ungetECPControlFactory();
			return null;
		}

		control = controlFactory.createControl(SWTControl.class, getViewModelContext().getDomainModel(), getVElement()
			.getDomainModelReference());

		Activator.getDefault().ungetECPControlFactory();
		if (control == null) {
			return null;
		}
		control.init(getViewModelContext(), getVElement());

		return control.createControl(parent);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.AbstractControlSWTRenderer#dispose()
	 */
	@Override
	protected void dispose() {
		if (control != null) {
			control.dispose();
		}
		super.dispose();
	}

	@Override
	protected Binding[] createBindings(Control control) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTRenderer#getUnsetText()
	 */
	@Override
	protected String getUnsetText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean isUnsettable() {
		return false;
	}

}
