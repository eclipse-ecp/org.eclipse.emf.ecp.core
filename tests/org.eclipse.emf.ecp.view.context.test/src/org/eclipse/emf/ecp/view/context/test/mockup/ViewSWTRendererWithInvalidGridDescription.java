/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edgar - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.context.test.mockup;

import org.eclipse.emf.ecp.view.internal.core.swt.renderer.ViewSWTRenderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.swt.SWTRendererFactory;
import org.eclipse.emfforms.spi.common.locale.EMFFormsLocaleProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.swt.core.layout.GridDescriptionFactory;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;

public class ViewSWTRendererWithInvalidGridDescription extends ViewSWTRenderer {

	/**
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param factory the {@link SWTRendererFactory}
	 */
	public ViewSWTRendererWithInvalidGridDescription(VView vElement, ViewModelContext viewContext,
		ReportService factory, EMFFormsLocaleProvider localeProvider) {
		super(vElement, viewContext, factory, null, null, localeProvider);
	}

	@Override
	public SWTGridDescription getGridDescription(
		SWTGridDescription gridDescription) {
		return GridDescriptionFactory.INSTANCE.createSimpleGrid(2, 1, this);
	}
}