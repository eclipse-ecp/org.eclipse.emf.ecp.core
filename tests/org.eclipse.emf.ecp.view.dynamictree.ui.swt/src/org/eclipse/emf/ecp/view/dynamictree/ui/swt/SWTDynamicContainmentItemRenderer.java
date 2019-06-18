/**
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
 * Edgar Mueller - initial API and implementation
 */
package org.eclipse.emf.ecp.view.dynamictree.ui.swt;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.ecp.view.dynamictree.model.DynamicContainmentItem;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.ContainerSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.VContainedElement;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererFactory;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * SWT renderer for {@link DynamicContainmentItem}s.
 *
 * @author emueller
 */
public class SWTDynamicContainmentItemRenderer extends ContainerSWTRenderer<DynamicContainmentItem> {

	private static final EMFFormsRendererFactory EMFFORMS_RENDERER_FACTORY;

	static {
		final BundleContext bundleContext = FrameworkUtil.getBundle(SWTDynamicContainmentTreeRenderer.class)
			.getBundleContext();

		final ServiceReference<EMFFormsRendererFactory> emfFormsLabelProviderServiceReference = bundleContext
			.getServiceReference(EMFFormsRendererFactory.class);
		EMFFORMS_RENDERER_FACTORY = bundleContext.getService(emfFormsLabelProviderServiceReference);
	}

	/**
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService the {@link ReportService}
	 */
	public SWTDynamicContainmentItemRenderer(DynamicContainmentItem vElement, ViewModelContext viewContext,
		ReportService reportService) {
		super(vElement, viewContext, reportService, EMFFORMS_RENDERER_FACTORY);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.ContainerSWTRenderer#getCustomVariant()
	 */
	@Override
	protected String getCustomVariant() {
		// TODO Auto-generated method stub
		return "test";
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.ContainerSWTRenderer#getChildren()
	 */

	@Override
	protected Collection<VContainedElement> getChildren() {
		return Collections.singleton(getVElement().getComposite());
	}

}
