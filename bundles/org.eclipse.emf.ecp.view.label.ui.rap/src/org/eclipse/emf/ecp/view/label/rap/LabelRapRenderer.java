/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 */
package org.eclipse.emf.ecp.view.label.rap;

import javax.inject.Inject;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.label.model.VLabel;
import org.eclipse.emf.ecp.view.spi.label.swt.LabelSWTRenderer;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.swt.widgets.Label;

/**
 * @author Alexandra Buzila
 *
 */
public class LabelRapRenderer extends LabelSWTRenderer {

	/**
	 * Default Constructor.
	 *
	 * @param vElement the view element to be rendered
	 * @param viewContext The view model context
	 * @param reportService the ReportService to use
	 * @param emfFormsDatabinding the EMFFormsDatabinding to use
	 * @param vtViewTemplateProvider the VTViewTemplateProvider to use
	 */
	@Inject
	public LabelRapRenderer(final VLabel vElement, final ViewModelContext viewContext, ReportService reportService,
		EMFFormsDatabinding emfFormsDatabinding, VTViewTemplateProvider vtViewTemplateProvider) {
		super(vElement, viewContext, reportService, emfFormsDatabinding, vtViewTemplateProvider);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecp.view.spi.label.swt.LabelSWTRenderer#applyStyle(org.eclipse.swt.widgets.Label)
	 */
	@Override
	protected void applyStyle(Label label) {
		label.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_ui_" + getVElement().getStyle()); //$NON-NLS-1$
	}

}
