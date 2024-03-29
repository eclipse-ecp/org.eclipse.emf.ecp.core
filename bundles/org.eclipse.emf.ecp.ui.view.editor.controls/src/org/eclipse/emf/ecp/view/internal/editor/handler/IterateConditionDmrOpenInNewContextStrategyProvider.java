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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.ui.view.swt.reference.OpenInNewContextStrategy.Provider;
import org.eclipse.emf.ecp.view.spi.editor.controls.EStructuralFeatureSelectionValidator;
import org.eclipse.emf.ecp.view.spi.rule.model.IterateCondition;
import org.eclipse.emf.ecp.view.spi.rule.model.RulePackage;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Creates segment based item domain model references for {@link IterateCondition IterateConditions}.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "IterateConditionDmrOpenInNewContextStrategyProvider", property = "service.ranking:Integer=50", service = {
	Provider.class })
public class IterateConditionDmrOpenInNewContextStrategyProvider
	extends RuleConditionDmrOpenInNewContextStrategyProvider {

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
			&& owner instanceof IterateCondition
			&& reference == RulePackage.Literals.ITERATE_CONDITION__ITEM_REFERENCE;
	}

	@Override
	protected EStructuralFeatureSelectionValidator getSelectionValidator() {
		return feature -> feature instanceof EReference && EReference.class.cast(feature).isMany()
			? null
			: "An iterate condition's domain model reference must point to a multi reference."; //$NON-NLS-1$
	}
}
