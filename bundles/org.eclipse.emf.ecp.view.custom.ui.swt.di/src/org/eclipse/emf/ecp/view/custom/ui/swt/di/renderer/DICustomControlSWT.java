/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.custom.ui.swt.di.renderer;

import javax.annotation.PreDestroy;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.emf.ecp.view.spi.custom.swt.ECPAbstractCustomControlSWT;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emfforms.spi.swt.core.layout.GridDescriptionFactory;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * {@link ECPAbstractCustomControlSWT} that will delegate its calls to a POJO.
 *
 * @author jfaltermeier
 *
 */
public class DICustomControlSWT extends ECPAbstractCustomControlSWT {

	private final Object pojo;
	private final IEclipseContext eclipseContext;
	private SWTGridDescription rendererGridDescription;

	/**
	 * Constructs a new {@link DICustomControlSWT}.
	 *
	 * @param pojo the pojo custom control
	 * @param eclipseContext the eclipse context
	 */
	/* package */DICustomControlSWT(Object pojo, IEclipseContext eclipseContext) {
		super();
		this.pojo = pojo;
		this.eclipseContext = eclipseContext;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.custom.swt.ECPAbstractCustomControlSWT#postInit()
	 */
	@Override
	protected void postInit() {
		super.postInit();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.custom.swt.ECPAbstractCustomControlSWT#disposeCustomControl()
	 */
	@Override
	protected void disposeCustomControl() {
		ContextInjectionFactory.invoke(pojo, PreDestroy.class, eclipseContext, null);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.custom.swt.ECPAbstractCustomControlSWT#handleContentValidation()
	 */
	@Override
	protected void handleContentValidation() {
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.custom.swt.ECPAbstractCustomControlSWT#getGridDescription()
	 */
	@Override
	public SWTGridDescription getGridDescription() {
		if (rendererGridDescription == null) {
			rendererGridDescription = GridDescriptionFactory.INSTANCE.createSimpleGrid(1, 1, null);
		}
		return rendererGridDescription;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.custom.swt.ECPAbstractCustomControlSWT#renderControl(org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell,
	 *      org.eclipse.swt.widgets.Composite)
	 */
	@SuppressWarnings("restriction")
	@Override
	public Control renderControl(SWTGridCell cell, Composite parent) throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption {
		return (Control) org.eclipse.emf.ecp.view.model.common.di.renderer.DIRendererUtil.render(pojo,
			getCustomControl(), getViewModelContext());
	}

}
