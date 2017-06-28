/*******************************************************************************
 * Copyright (c) 2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.controls;

import javax.inject.Inject;

import org.eclipse.emf.ecp.edit.spi.ReferenceService;
import org.eclipse.emf.ecp.view.internal.core.swt.renderer.LinkControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.table.model.VWidthConfiguration;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.localization.EMFFormsLocalizationService;
import org.eclipse.swt.widgets.Composite;

/**
 * Special {@link LinkControlSWTRenderer} which will streamline the column dmr selection.
 *
 * @author Edgar Mueller
 *
 */
public class WidthConfigurationDMRRenderer extends LinkControlSWTRenderer {

	private ColumnConfigurationDMRRendererReferenceService referenceService;

	/**
	 * @param vElement the element to render
	 * @param viewContext the view model context
	 * @param reportService the report service
	 * @param emfFormsDatabinding the data binding service
	 * @param emfFormsLabelProvider the label provider
	 * @param vtViewTemplateProvider the view template provider
	 * @param localizationService the localization service
	 * @param imageRegistryService the image registry service
	 * @param emfFormsEditSuppport the edit support
	 */
	@Inject
	// CHECKSTYLE.OFF: ParameterNumber
	public WidthConfigurationDMRRenderer(
		VControl vElement,
		ViewModelContext viewContext,
		ReportService reportService,
		EMFFormsDatabinding emfFormsDatabinding,
		EMFFormsLabelProvider emfFormsLabelProvider,
		VTViewTemplateProvider vtViewTemplateProvider,
		EMFFormsLocalizationService localizationService,
		ImageRegistryService imageRegistryService,
		EMFFormsEditSupport emfFormsEditSuppport) {
		// CHECKSTYLE.ON: ParameterNumber
		super(
			vElement,
			viewContext,
			reportService,
			emfFormsDatabinding,
			emfFormsLabelProvider,
			vtViewTemplateProvider,
			localizationService,
			imageRegistryService,
			emfFormsEditSuppport);
	}

	@Override
	protected void createNewReferenceButton(Composite parent, String elementDisplayName) {
		/* we only want users to select existing DMRs -> no-op */
	}

	@Override
	protected ReferenceService getReferenceService() {
		if (referenceService == null) {
			referenceService = new ColumnConfigurationDMRRendererReferenceService(VWidthConfiguration.class);
		}
		return referenceService;
	}

}
