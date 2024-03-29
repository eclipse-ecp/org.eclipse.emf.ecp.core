/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * jfaltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.ui.view.swt.di.renderer;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.emf.ecp.ui.view.swt.di.util.SWTContextUtil;
import org.eclipse.emf.ecp.view.internal.core.swt.renderer.ViewSWTRenderer;
import org.eclipse.emf.ecp.view.model.common.di.renderer.DIRendererUtil;
import org.eclipse.emf.ecp.view.model.common.di.renderer.POJORendererFactory;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emfforms.spi.common.locale.EMFFormsLocaleProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererFactory;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * @author jfaltermeier
 *
 */
@SuppressWarnings("restriction")
public class DIViewSWTRenderer extends ViewSWTRenderer {

	/**
	 * Default constructor.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService the {@link ReportService}
	 * @param factory the {@link EMFFormsRendererFactory}
	 * @param localeProvider The {@link EMFFormsLocaleProvider}
	 */
	public DIViewSWTRenderer(VView vElement, ViewModelContext viewContext, ReportService reportService,
		EMFFormsRendererFactory factory, EMFFormsLocaleProvider localeProvider) {
		super(vElement, viewContext, reportService, factory, localeProvider);
	}

	private Object pojo;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer#render(org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell,
	 *      org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public Control render(SWTGridCell cell, Composite parent)
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		pojo = POJORendererFactory.getInstance().getRenderer(getVElement(), getViewModelContext());
		final IEclipseContext childContext = DIRendererUtil.getContextForElement(getVElement(), getViewModelContext());
		SWTContextUtil.setAbstractSWTRendererObjects(childContext, getVElement(), getViewModelContext(), parent);
		childContext.set(SWTGridCell.class, cell);
		DIRendererUtil.render(pojo, getVElement(), getViewModelContext());
		return parent;
	}

}
