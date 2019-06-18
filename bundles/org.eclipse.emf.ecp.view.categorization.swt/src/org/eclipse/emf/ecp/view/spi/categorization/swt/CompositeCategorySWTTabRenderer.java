/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.categorization.swt;

import javax.inject.Inject;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecp.view.spi.categorization.model.VAbstractCategorization;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorization;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererFactory;

/**
 * Tab renderer for composite category.
 *
 * @author Eugen Neufeld
 *
 */
public class CompositeCategorySWTTabRenderer extends AbstractSWTTabRenderer<VCategorization> {

	/**
	 * Default constructor.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService the {@link ReportService}
	 * @param emfFormsRendererFactory The {@link EMFFormsRendererFactory}
	 * @param viewTemplateProvider the {@link VTViewTemplateProvider}
	 * @since 1.8
	 */
	@Inject
	public CompositeCategorySWTTabRenderer(
		VCategorization vElement,
		ViewModelContext viewContext,
		ReportService reportService,
		EMFFormsRendererFactory emfFormsRendererFactory,
		VTViewTemplateProvider viewTemplateProvider) {
		super(vElement, viewContext, reportService, emfFormsRendererFactory, viewTemplateProvider);
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
