/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edagr Mueller - initial API and implementation
 * Eugen Neufeld - Refactoring
 * Johannes Falterimeier - Refactoring
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.categorization.swt;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecp.view.spi.categorization.model.VAbstractCategorization;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationElement;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.swt.SWTRendererFactory;

/**
 * The Class ViewSWTRenderer.
 *
 * @author Eugen Neufeld
 */
public class SWTCategorizationElementRenderer extends AbstractJFaceTreeRenderer<VCategorizationElement> {

	/**
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param factory the {@link SWTRendererFactory}
	 */
	public SWTCategorizationElementRenderer(VCategorizationElement vElement, ViewModelContext viewContext,
		SWTRendererFactory factory) {
		super(vElement, viewContext, factory);
		// TODO Auto-generated constructor stub
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.categorization.swt.AbstractJFaceTreeRenderer#getCategorizations()
	 */
	@Override
	protected EList<VAbstractCategorization> getCategorizations() {
		return getVElement().getCategorizations();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.categorization.swt.AbstractJFaceTreeRenderer#getCategorizationElement()
	 */
	@Override
	protected VCategorizationElement getCategorizationElement() {
		return getVElement();
	}

}
