/**
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.separator.swt;

import java.util.List;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.renderer.RenderingResultRow;
import org.eclipse.emf.ecp.view.spi.separator.model.VSeparator;
import org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

/**
 * Renders an {@link VSeparator} to a SWT {@link Label}.
 * 
 */
public class SWTSeparatorRenderer extends AbstractSWTRenderer<VSeparator> {
	/**
	 * The instance.
	 */
	public static final SWTSeparatorRenderer INSTANCE = new SWTSeparatorRenderer();

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer#renderModel(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.emf.ecp.view.spi.model.VElement, org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	protected List<RenderingResultRow<Control>> renderModel(Composite parent, VSeparator separator,
		ViewModelContext viewContext)
		throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption {
		final Label label = new Label(parent, SWT.NONE);
		label.setText(separator.getName());
		label.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_ui_seperator"); //$NON-NLS-1$

		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER)
			.grab(true, true).span(2, 1).applyTo(label);

		return createResult(label);
	}

}
