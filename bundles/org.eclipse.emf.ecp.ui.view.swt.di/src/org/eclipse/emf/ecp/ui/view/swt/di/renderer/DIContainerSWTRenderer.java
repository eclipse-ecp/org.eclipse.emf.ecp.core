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
import org.eclipse.emf.ecp.view.model.common.di.renderer.DIRendererUtil;
import org.eclipse.emf.ecp.view.model.common.di.renderer.POJORendererFactory;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.ContainerSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererFactory;
import org.eclipse.swt.widgets.Composite;

/**
 * DI Renderer for Container.
 *
 * @author jfaltermeier
 *
 */
@SuppressWarnings("restriction")
public class DIContainerSWTRenderer extends ContainerSWTRenderer<VElement> {

	/**
	 * Default constructor.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService the {@link ReportService}
	 * @param factory the {@link EMFFormsRendererFactory}
	 */
	public DIContainerSWTRenderer(VElement vElement, ViewModelContext viewContext, ReportService reportService,
		EMFFormsRendererFactory factory) {
		super(vElement, viewContext, reportService, factory);
	}

	/**
	 * Named string for the children of a container.
	 */
	public static final String CHILDREN = "containerChildren"; //$NON-NLS-1$
	private Object pojo;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.ContainerSWTRenderer#getComposite(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Composite getComposite(Composite parent) {
		final IEclipseContext childContext = DIRendererUtil.getContextForElement(getVElement(), getViewModelContext());
		SWTContextUtil.setAbstractSWTRendererObjects(childContext, getVElement(), getViewModelContext(), parent);
		childContext.set(CHILDREN, getChildren());
		putInContext(childContext, getVElement());
		return doGetComposite();
	}

	private void putInContext(IEclipseContext context, VElement element) {
		for (final Class<?> clazz : element.getClass().getInterfaces()) {
			context.set(clazz.getName(), element);
		}
	}

	/**
	 * Invokes the get composite call on the pojo.
	 *
	 * @return the composite
	 */
	protected Composite doGetComposite() {
		pojo = POJORendererFactory.getInstance().getRenderer(getVElement(), getViewModelContext());
		return (Composite) DIRendererUtil.render(pojo, getVElement(), getViewModelContext());
	}
}
