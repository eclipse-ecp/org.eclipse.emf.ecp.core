/*******************************************************************************
 * Copyright (c) 2019 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.ui.view.swt;

import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.view.internal.swt.Activator;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emfforms.spi.localization.EMFFormsLocalizationService;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

/**
 * A specialized view presenting a rendering exception.
 *
 * @since 1.22
 */
public class RenderFailureView implements ECPSWTView {

	private final ViewModelContext context;
	private final ECPRendererException exception;
	private final Label label;

	/**
	 * Initializes me with the {@code exception} to present.
	 *
	 * @param parent the parent composite in which to present the {@code exception}
	 * @param context the view context
	 * @param exception the rendering exception
	 */
	public RenderFailureView(Composite parent, ViewModelContext context, ECPRendererException exception) {
		super();

		this.context = context;
		this.exception = exception;

		final EMFFormsLocalizationService l10n = context.getService(EMFFormsLocalizationService.class);
		final String message = NLS.bind(l10n.getString(Activator.getDefault().getBundle(), "renderFailed"), //$NON-NLS-1$
			exception.getMessage());
		label = new Label(parent, SWT.NONE);
		label.setText(message);
	}

	@Override
	public void dispose() {
		context.dispose();
		label.dispose();
	}

	/**
	 * Obtains the SWT control presenting the exception.
	 */
	@Override
	public Control getSWTControl() {
		return label;
	}

	@Override
	public ViewModelContext getViewModelContext() {
		return context;
	}

	/**
	 * Queries the exception that I present.
	 *
	 * @return the rendering exception
	 */
	public Throwable getException() {
		return exception;
	}
}
