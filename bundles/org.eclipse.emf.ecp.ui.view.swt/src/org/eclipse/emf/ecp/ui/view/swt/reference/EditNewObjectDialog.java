/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 * Christian W. Damus - bug 529542
 ******************************************************************************/
package org.eclipse.emf.ecp.ui.view.swt.reference;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.emf.ecp.view.internal.swt.Activator;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextFactory;
import org.eclipse.emf.ecp.view.spi.provider.ViewProviderHelper;
import org.eclipse.emf.ecp.view.spi.swt.reporting.RenderingFailedReport;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/** Custom dialog used for displaying a newly created EObject. */
class EditNewObjectDialog extends Dialog {

	private final EObject eObject;

	/**
	 * Initializes me.
	 *
	 * @param parentshell my parent shell
	 * @param eObject the object to present for editing
	 */
	EditNewObjectDialog(Shell parentshell, EObject eObject) {
		super(parentshell);

		this.eObject = eObject;
	}

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(eObject.eClass().getName());
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		final Composite composite = (Composite) super.createDialogArea(parent);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).hint(450, 250)
			.applyTo(composite);

		final ScrolledComposite scrolledComposite = new ScrolledComposite(composite, SWT.H_SCROLL
			| SWT.V_SCROLL);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(scrolledComposite);
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setExpandHorizontal(true);

		final Composite content = new Composite(scrolledComposite, SWT.NONE);
		GridLayoutFactory.fillDefaults().applyTo(content);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(content);

		// Create a new root context. It will get the best available reference service,
		// which usually is the default
		final ViewModelContext vmc = ViewModelContextFactory.INSTANCE.createViewModelContext(
			ViewProviderHelper.getView(eObject, null), eObject);
		try {
			ECPSWTViewRenderer.INSTANCE.render(content, vmc);
		} catch (final ECPRendererException ex) {
			Activator.getDefault().getReportService().report(new RenderingFailedReport(ex));
		}

		scrolledComposite.setContent(content);
		final Point point = content.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		scrolledComposite.setMinSize(point);
		scrolledComposite.layout(true);

		return composite;
	}

}