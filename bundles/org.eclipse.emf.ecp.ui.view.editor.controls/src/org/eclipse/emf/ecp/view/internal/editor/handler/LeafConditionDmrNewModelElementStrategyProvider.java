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
package org.eclipse.emf.ecp.view.internal.editor.handler;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.ui.view.swt.reference.CreateNewModelElementStrategy;
import org.eclipse.emf.ecp.ui.view.swt.reference.CreateNewModelElementStrategy.Provider;
import org.eclipse.emf.ecp.view.spi.editor.controls.EStructuralFeatureSelectionValidator;
import org.eclipse.emf.ecp.view.spi.rule.model.LeafCondition;
import org.eclipse.emf.ecp.view.spi.rule.model.RulePackage;
import org.osgi.service.component.annotations.Component;

/**
 * This provider registers a {@link CreateNewModelElementStrategy} to create <strong>segment based</strong> domain model
 * references for {@link org.eclipse.emf.ecp.view.spi.rule.model.LeafCondition LeafConditions}.
 *
 * @author Lucas Koehler
 */
@Component(name = "LeafConditionDmrNewModelElementStrategyProvider", property = "service.ranking:Integer=50", service = {
	Provider.class })
public class LeafConditionDmrNewModelElementStrategyProvider
	extends RuleConditionDmrNewModelElementStrategyProvider {

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
