/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.swt.core.di.extension;

import org.eclipse.emf.ecp.view.model.common.ECPRendererTester;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.di.EMFFormsDIRendererService;

/**
 * A {@link EMFFormsDIWrapperRendererService} wraps the renderer's class and a corresponding {@link ECPRendererTester}.
 *
 * @author Lucas Koehler
 *
 */
public class EMFFormsDIWrapperRendererService implements EMFFormsDIRendererService<VElement> {

	private final Class<AbstractSWTRenderer<VElement>> rendererClass;
	private final ECPRendererTester rendererTester;

	/**
	 * Creates a new instance of {@link EMFFormsDIWrapperRendererService}.
	 *
	 * @param rendererClass The class of the renderer of this service
	 * @param rendererTester The {@link ECPRendererTester} for the renderer
	 */
	public EMFFormsDIWrapperRendererService(Class<AbstractSWTRenderer<VElement>> rendererClass,
		ECPRendererTester rendererTester) {
		this.rendererClass = rendererClass;
		this.rendererTester = rendererTester;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.di.EMFFormsDIRendererService#isApplicable(org.eclipse.emf.ecp.view.spi.model.VElement,
	 *      org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public double isApplicable(VElement vElement, ViewModelContext viewModelContext) {
		return rendererTester.isApplicable(vElement, viewModelContext);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.di.EMFFormsDIRendererService#getRendererClass()
	 */
	@Override
	public Class<? extends AbstractSWTRenderer<VElement>> getRendererClass() {
		return rendererClass;
	}

}
