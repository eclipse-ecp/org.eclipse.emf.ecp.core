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
import org.eclipse.emf.ecp.ui.view.swt.reference.CreateNewModelElementStrategy.Provider;
import org.eclipse.emf.ecp.view.spi.editor.controls.EStructuralFeatureSelectionValidator;
import org.eclipse.emf.ecp.view.spi.rule.model.IterateCondition;
import org.eclipse.emf.ecp.view.spi.rule.model.RulePackage;
import org.osgi.service.component.annotations.Component;

/**
 * Edits segment based item domain model references for {@link IterateCondition IterateConditions}.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "IterateConditionDmrNewModelElementStrategyProvider", property = "service.ranking:Integer=50", service = {
	Provider.class })
public class IterateConditionDmrNewModelElementStrategyProvider
	extends RuleConditionDmrNewModelElementStrategyProvider {

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
