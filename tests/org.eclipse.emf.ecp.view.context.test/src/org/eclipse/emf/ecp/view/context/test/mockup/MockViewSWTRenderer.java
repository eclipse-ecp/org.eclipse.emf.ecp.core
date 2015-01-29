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

import static org.mockito.Mockito.mock;

import org.eclipse.emf.ecp.view.internal.core.swt.renderer.ViewSWTRenderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.swt.SWTRendererFactory;

public final class MockViewSWTRenderer {

	private static ViewModelContext viewModelContext = mock(ViewModelContext.class);
	private static VView vElement = mock(VView.class);
	private static SWTRendererFactory rendererFactory = mock(SWTRendererFactory.class);

	public MockViewSWTRenderer(VView vElement, ViewModelContext viewModelContext, SWTRendererFactory rendererFactory) {

	}

	public static ViewSWTRenderer withInvalidGridDescription() {
		return new ViewSWTRendererWithInvalidGridDescription(vElement, viewModelContext, rendererFactory);
	}

	public static ViewSWTRenderer withoutPropertyDescriptor() {
		return new ViewSWTRendererWithNoPropertyDescriptorFoundException(vElement, viewModelContext, rendererFactory);
	}

	public static ViewSWTRenderer withoutRenderer() {
		return new ViewSWTRendererWithNoRendererFoundException(vElement, viewModelContext, rendererFactory);
	}

	public static ViewSWTRenderer newRenderer() {
		return new ViewSWTRenderer(vElement, viewModelContext, rendererFactory);
	}

}
