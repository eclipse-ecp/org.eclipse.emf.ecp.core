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
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.handler;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.ui.view.swt.reference.OpenInNewContextStrategy.Provider;
import org.eclipse.emf.ecp.view.spi.editor.controls.EStructuralFeatureSelectionValidator;
import org.eclipse.emf.ecp.view.spi.rule.model.LeafCondition;
import org.eclipse.emf.ecp.view.spi.rule.model.RulePackage;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * This provider registers a {@link org.eclipse.emf.ecp.ui.view.swt.reference.OpenInNewContextStrategy
 * OpenInNewContextStrategy} to edit <strong>segment based</strong> domain model
 * references for {@link org.eclipse.emf.ecp.view.spi.rule.model.LeafCondition LeafConditions}.
 *
 * @author Lucas Koehler
 */
@Component(name = "LeafConditionDmrOpenInNewContextStrategyProvider", property = "service.ranking:Integer=50", service = {
	Provider.class })
public class LeafConditionDmrOpenInNewContextStrategyProvider extends RuleConditionDmrOpenInNewContextStrategyProvider {

	@Override
	@Reference(unbind = "-")
	protected void setEMFFormsDatabindingEMF(EMFFormsDatabindingEMF databinding) {
		super.setEMFFormsDatabindingEMF(databinding);
	}

	@Override
	@Reference(unbind = "-")
	protected void setReportService(ReportService reportService) {
		super.setReportService(reportService);
	}

	@Override
	protected boolean handles(EObject owner, EReference reference) {
		return isSegmentToolingEnabled()
			&& owner instanceof LeafCondition
			&& reference == RulePackage.Literals.LEAF_CONDITION__DOMAIN_MODEL_REFERENCE;
	}

	@Override
	protected EStructuralFeatureSelectionValidator getSelectionValidator() {
		return feature -> feature instanceof EAttribute ? null
			: "A leaf condition's domain model reference must point to an attribute."; //$NON-NLS-1$
	}
}
