/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.datatemplate.tooling.editor;

import javax.inject.Inject;

import org.eclipse.emf.ecp.view.internal.core.swt.renderer.LinkControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.localization.EMFFormsLocalizationService;
import org.eclipse.swt.widgets.Composite;

/**
 * A custom class for rendering the TemplateInstance control.
 * This controls does not allow to add an existing Reference only to create new ones.
 *
 * @author Eugen Neufeld
 *
 */
@SuppressWarnings("restriction")
public class TemplateInstanceRenderer extends LinkControlSWTRenderer {

	/**
	 * Default constructor.
	 *
	 * @param vElement The {@link VControl}
	 * @param viewContext The {@link ViewModelContext}
	 * @param reportService The {@link ReportService}
	 * @param emfFormsDatabinding The {@link EMFFormsDatabinding}
	 * @param emfFormsLabelProvider The {@link EMFFormsLabelProvider}
	 * @param vtViewTemplateProvider The {@link VTViewTemplateProvider}
	 * @param localizationService The {@link EMFFormsLocalizationService}
	 * @param imageRegistryService The {@link ImageRegistryService}
	 * @param emfFormsEditSuppport The {@link EMFFormsEditSupport}
	 */
	@Inject
	// CHECKSTYLE:OFF: ParameterNumber
	public TemplateInstanceRenderer(VControl vElement, ViewModelContext viewContext, ReportService reportService,
		EMFFormsDatabinding emfFormsDatabinding, EMFFormsLabelProvider emfFormsLabelProvider,
		VTViewTemplateProvider vtViewTemplateProvider, EMFFormsLocalizationService localizationService,
		ImageRegistryService imageRegistryService, EMFFormsEditSupport emfFormsEditSuppport) {
		super(vElement, viewContext, reportService, emfFormsDatabinding, emfFormsLabelProvider, vtViewTemplateProvider,
			localizationService, imageRegistryService, emfFormsEditSuppport);
	}

	// CHECKSTYLE:ON
	@Override
	protected void createAddReferenceButton(Composite parent, String elementDisplayName) {
		// do nothing
	}

	@Override
	protected boolean openNewReferenceInContext() {
		return false;
	}

}
