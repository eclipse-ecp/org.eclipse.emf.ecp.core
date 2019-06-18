/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * lucas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.controls;

import javax.inject.Inject;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.rule.RuleConditionDmrUtil;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;

/**
 * Renderer for {@link org.eclipse.emf.ecp.view.spi.rule.model.Condition rule condition} domain model references. This
 * renderer can determine the correct root EClass for nested conditions.
 *
 * @author Lucas Koehler
 *
 */
public class RuleConditionDmrControlSWTRenderer extends DomainModelReferenceControlSWTRenderer {

	private final EMFFormsDatabindingEMF databindingEMF;

	/**
	 * Default constructor.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService The {@link ReportService}
	 * @param emfFormsDatabindingEMF The {@link EMFFormsDatabinding}
	 * @param emfFormsLabelProvider The {@link EMFFormsLabelProvider}
	 * @param vtViewTemplateProvider The {@link VTViewTemplateProvider}
	 * @param emfFormsEditSupport The {@link EMFFormsEditSupport}
	 */
	@Inject
	public RuleConditionDmrControlSWTRenderer(VControl vElement, ViewModelContext viewContext,
		ReportService reportService, EMFFormsDatabindingEMF emfFormsDatabindingEMF,
		EMFFormsLabelProvider emfFormsLabelProvider, VTViewTemplateProvider vtViewTemplateProvider,
		EMFFormsEditSupport emfFormsEditSupport) {
		super(vElement, viewContext, reportService, emfFormsDatabindingEMF, emfFormsLabelProvider,
			vtViewTemplateProvider,
			emfFormsEditSupport);
		databindingEMF = emfFormsDatabindingEMF;
	}

	@Override
	protected EClass getDmrRootEClass() {
		// Domain model should be the rule containing the DMR
		return RuleConditionDmrUtil.getDmrRootEClass(databindingEMF, getReportService(),
			getViewModelContext().getDomainModel()).orElse(null);
	}

}
