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
package org.eclipse.emf.ecp.view.spi.swt;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.swt.layout.GridDescriptionFactory;
import org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridCell;
import org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridDescription;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.osgi.framework.FrameworkUtil;

/**
 * This is a renderer, which is used to displays something usefull instead of an error if a {@link VElement} is missing.
 *
 * @author Eugen Neufeld
 * @since 1.3
 *
 */
public final class UnknownVElementSWTRenderer extends AbstractSWTRenderer<VElement> {

	private final SWTGridDescription gridDescription;

	/**
	 * Default constructor.
	 */
	public UnknownVElementSWTRenderer(VElement vElement, ViewModelContext viewContext, SWTRendererFactory factory) {
		super(vElement, viewContext, factory);
		gridDescription = GridDescriptionFactory.INSTANCE.createEmptyGridDescription();
		final SWTGridCell gc = new SWTGridCell(0, 0, this);
		gc.setHorizontalFill(true);
		gc.setHorizontalGrab(true);
		gc.setVerticalFill(false);
		gc.setVerticalGrab(false);
		final List<SWTGridCell> grid = new ArrayList<SWTGridCell>();
		grid.add(gc);
		gridDescription.setGrid(grid);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer#getGridDescription(org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridDescription)
	 */
	@Override
	public SWTGridDescription getGridDescription(SWTGridDescription gridDescription) {
		return this.gridDescription;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer#renderControl(org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridCell,
	 *      org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control renderControl(SWTGridCell cell, Composite parent) throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption {
		final Composite composite = new Composite(parent, SWT.BORDER);
		composite.setLayout(new FillLayout());
		final Label label = new Label(composite, SWT.NONE);
		final String bundleModelName = FrameworkUtil.getBundle(getVElement().getClass()).getSymbolicName();
		final String bundleBaseName = bundleModelName.substring(0, bundleModelName.length() - 5);
		final String bundleSWTPlainName = bundleBaseName + "swt"; //$NON-NLS-1$
		final String bundleSWTOldName = bundleBaseName + "ui.swt"; //$NON-NLS-1$
		label.setText("There is no renderer for " + getVElement().eClass().getName() //$NON-NLS-1$
			+ ". Please check your launch configuration for a bundle " + bundleSWTPlainName + " or " + bundleSWTOldName //$NON-NLS-1$ //$NON-NLS-2$
			+ "."); //$NON-NLS-1$

		return composite;
	}

}
