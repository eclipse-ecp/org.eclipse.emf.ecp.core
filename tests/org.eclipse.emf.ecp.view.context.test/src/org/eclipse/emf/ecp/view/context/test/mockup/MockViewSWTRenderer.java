/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.context.test.mockup;

import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecp.view.internal.context.ViewModelContextImpl;
import org.eclipse.emf.ecp.view.internal.core.swt.renderer.ViewSWTRenderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emfforms.spi.common.locale.EMFFormsLocaleProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererFactory;
import org.mockito.Mockito;

public final class MockViewSWTRenderer {

	// private static ViewModelContext viewModelContext = mock(ViewModelContext.class);
	// private static VView vElement = mock(VView.class);
	// private static SWTRendererFactory rendererFactory = mock(SWTRendererFactory.class);

	private static VView vElement = VViewFactory.eINSTANCE.createView();
	private static ViewModelContext viewModelContext = new ViewModelContextImpl(vElement,
		EcoreFactory.eINSTANCE.createEObject());
	private static ReportService reportService = Mockito.mock(ReportService.class);

	private static EMFFormsDatabinding emfFormsDatabinding = Mockito.mock(EMFFormsDatabinding.class);
	private static EMFFormsRendererFactory emfFormsRendererFactory = Mockito.mock(EMFFormsRendererFactory.class);
	private static EMFFormsLocaleProvider emfFormsLocaleProvider = Mockito.mock(EMFFormsLocaleProvider.class);

	public MockViewSWTRenderer(VView vElement, ViewModelContext viewModelContext, ReportService rendererFactory) {

	}

	public static ViewSWTRenderer withInvalidGridDescription() {
		return new ViewSWTRendererWithInvalidGridDescription(vElement, viewModelContext, reportService,
			emfFormsLocaleProvider);
	}

	public static ViewSWTRenderer withoutPropertyDescriptor() {
		return new ViewSWTRendererWithNoPropertyDescriptorFoundException(vElement, viewModelContext, reportService,
			emfFormsLocaleProvider);
	}

	public static ViewSWTRenderer withoutRenderer() {
		return new ViewSWTRendererWithNoRendererFoundException(vElement, viewModelContext, reportService,
			emfFormsLocaleProvider);
	}

	public static ViewSWTRenderer newRenderer() {
		return new ViewSWTRenderer(vElement, viewModelContext, reportService, emfFormsRendererFactory,
			emfFormsDatabinding, emfFormsLocaleProvider);
	}

}
