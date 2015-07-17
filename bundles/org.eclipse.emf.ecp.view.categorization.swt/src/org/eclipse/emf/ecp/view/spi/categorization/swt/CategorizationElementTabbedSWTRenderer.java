/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.categorization.swt;

import javax.inject.Inject;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecp.view.spi.categorization.model.VAbstractCategorization;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationElement;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererFactory;

/**
 * Tab renderer for VCategorizationElement.
 *
 * @author Eugen Neufeld
 *
 */
public class CategorizationElementTabbedSWTRenderer extends AbstractSWTTabRenderer<VCategorizationElement> {

	/**
	 * Default constructor.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService the {@link ReportService}
	 * @param emfFormsRendererFactory The {@link EMFFormsRendererFactory}
	 * @since 1.6
	 */
	@Inject
	public CategorizationElementTabbedSWTRenderer(VCategorizationElement vElement, ViewModelContext viewContext,
		ReportService reportService,
		EMFFormsRendererFactory emfFormsRendererFactory) {
		super(vElement, viewContext, reportService, emfFormsRendererFactory);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.categorization.swt.AbstractSWTTabRenderer#getCategorizations()
	 */
	@Override
	protected EList<VAbstractCategorization> getCategorizations() {
		return getVElement().getCategorizations();
	}

}
