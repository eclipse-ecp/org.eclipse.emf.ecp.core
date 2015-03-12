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
package org.eclipse.emf.ecp.view.spi.categorization.swt;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.categorization.model.VAbstractCategorization;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorization;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationElement;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.reporting.ReportService;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererFactory;

/**
 * Tree renderer for composite category.
 *
 * @author Eugen Neufeld
 *
 */
public class CompositeCategoryJFaceTreeRenderer extends AbstractJFaceTreeRenderer<VCategorization> {

	/**
	 * Default constructor.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService the {@link ReportService}
	 * @param emfFormsRendererFactory The {@link EMFFormsRendererFactory}
	 */
	public CompositeCategoryJFaceTreeRenderer(VCategorization vElement, ViewModelContext viewContext,
		ReportService reportService,
		EMFFormsRendererFactory emfFormsRendererFactory) {
		super(vElement, viewContext, reportService, emfFormsRendererFactory);
	}

	private VCategorizationElement categorizationElement;

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
		if (categorizationElement == null) {
			EObject parent = getVElement().eContainer();
			while (!VCategorizationElement.class.isInstance(parent)) {
				parent = parent.eContainer();
			}
			categorizationElement = (VCategorizationElement) parent;
		}
		return categorizationElement;
	}

}
