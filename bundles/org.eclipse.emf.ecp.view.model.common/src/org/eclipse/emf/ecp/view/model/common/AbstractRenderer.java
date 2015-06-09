/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.common;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.spi.common.report.ReportService;

/**
 * Common super class for renderer.
 *
 * @author Eugen Neufeld
 * @param <VELEMENT> the {@link VElement} this renderer is applicable for
 *
 */
public abstract class AbstractRenderer<VELEMENT extends VElement> {

	private final VELEMENT vElement;
	private final ViewModelContext viewModelContext;
	private boolean disposed;
	private final ReportService reportService;

	/**
	 * Default constructor.
	 *
	 * @param vElement the {@link VElement} to be rendered
	 * @param viewContext the {@link ViewModelContext} to use
	 * @param reportService The {@link ReportService} to use
	 * @since 1.6
	 */
	public AbstractRenderer(final VELEMENT vElement, final ViewModelContext viewContext, ReportService reportService) {

		if (vElement == null) {
			throw new IllegalArgumentException("vElement must not be null"); //$NON-NLS-1$
		}
		if (viewContext == null) {
			throw new IllegalArgumentException("vContext must not be null"); //$NON-NLS-1$
		}
		if (reportService == null) {
			throw new IllegalArgumentException("reportService must not be null"); //$NON-NLS-1$
		}
		this.vElement = vElement;
		this.viewModelContext = viewContext;
		this.reportService = reportService;
	}

	/**
	 * The {@link ViewModelContext} to use.
	 *
	 * @return the {@link ViewModelContext}
	 */
	public final ViewModelContext getViewModelContext() {
		checkRenderer();
		return viewModelContext;
	}

	/**
	 * The {@link VElement} instance to use.
	 *
	 * @return the {@link VElement}
	 */
	public final VELEMENT getVElement() {
		checkRenderer();
		return vElement;
	}

	/**
	 * Disposes all resources used by the renderer.
	 * Don't forget to call super.dispose if overwriting this method.
	 */
	protected void dispose() {
		disposed = true;
	}

	/**
	 * Checks whether the renderer is disposed and if so throws an {@link IllegalStateException}.
	 *
	 * @since 1.6
	 */
	protected void checkRenderer() {
		if (disposed) {
			throw new IllegalStateException("Renderer is disposed"); //$NON-NLS-1$
		}

	}

	/**
	 * The {@link SWTRendererFactory} instance to use.
	 *
	 * @return the {@link SWTRendererFactory}
	 * @since 1.6
	 */
	protected final ReportService getReportService() {
		checkRenderer();
		return reportService;
	}

}
