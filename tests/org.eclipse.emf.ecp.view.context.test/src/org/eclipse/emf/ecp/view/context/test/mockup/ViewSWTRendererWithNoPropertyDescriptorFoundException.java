/*****************************************************************************
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

import org.eclipse.emf.ecp.view.internal.core.swt.renderer.ViewSWTRenderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.swt.SWTRendererFactory;
import org.eclipse.emf.emfstore.bowling.BowlingPackage;
import org.eclipse.emfforms.spi.common.locale.EMFFormsLocaleProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class ViewSWTRendererWithNoPropertyDescriptorFoundException extends ViewSWTRenderer {

	/**
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param factory the {@link SWTRendererFactory}
	 */
	public ViewSWTRendererWithNoPropertyDescriptorFoundException(VView vElement, ViewModelContext viewContext,
		ReportService factory, EMFFormsLocaleProvider localeProvider) {
		super(vElement, viewContext, factory, null, null, localeProvider);
	}

	@Override
	public Control render(SWTGridCell cell, Composite parent)
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		throw new NoPropertyDescriptorFoundExeption(getViewModelContext().getDomainModel(),
			BowlingPackage.eINSTANCE.getLeague_Name());
	}
}