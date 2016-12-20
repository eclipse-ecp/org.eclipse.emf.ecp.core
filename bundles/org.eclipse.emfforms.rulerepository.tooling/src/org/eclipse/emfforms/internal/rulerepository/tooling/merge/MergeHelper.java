/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.rulerepository.tooling.merge;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.view.spi.model.VAttachment;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.rule.model.AndCondition;
import org.eclipse.emf.ecp.view.spi.rule.model.OrCondition;
import org.eclipse.emf.ecp.view.spi.rule.model.Rule;
import org.eclipse.emf.ecp.view.spi.rule.model.RuleFactory;
import org.eclipse.emfforms.spi.rulerepository.model.MergeType;
import org.eclipse.emfforms.spi.rulerepository.model.VRuleEntry;
import org.eclipse.emfforms.spi.rulerepository.model.VRuleRepository;

/**
 * Helper class to merge the linked View model with the rule repository into a new view model.
 *
 * @author Eugen Neufeld
 *
 */
public final class MergeHelper {

	private MergeHelper() {
	}

	/**
	 * Merges the {@link VRuleRepository} into the linked {@link org.eclipse.emf.ecp.view.spi.model.VView VView}.
	 *
	 * @param ruleRepository The {@link VRuleRepository} to merge from
	 */
	public static void merge(VRuleRepository ruleRepository) {
		final Map<VElement, OrCondition> initialMerged = new LinkedHashMap<VElement, OrCondition>();
		for (final VRuleEntry ruleEntry : ruleRepository.getRuleEntries()) {
			for (final VElement vElement : ruleEntry.getElements()) {
				if (vElement.eIsProxy()) {
					// FIXME: log error
					continue;
				}
				mergeRules(vElement, ruleEntry, initialMerged);
			}
		}

	}

	private static void mergeRules(VElement vElement, VRuleEntry ruleEntry, Map<VElement, OrCondition> initialMerged) {
		final Rule existingRule = getExistingRule(vElement.getAttachments());
		final Rule newRule = EcoreUtil.copy(ruleEntry.getRule());
		if (existingRule == null) {
			vElement.getAttachments().add(newRule);
			return;
		}

		if (!initialMerged.containsKey(vElement)) {
			// initial merge
			final AndCondition and = RuleFactory.eINSTANCE.createAndCondition();
			final OrCondition or = RuleFactory.eINSTANCE.createOrCondition();
			and.getConditions().add(or);
			or.getConditions().add(existingRule.getCondition());
			existingRule.setCondition(and);
			initialMerged.put(vElement, or);
		}
		if (ruleEntry.getMergeType() == MergeType.OR) {
			final OrCondition or = initialMerged.get(vElement);
			or.getConditions().add(newRule.getCondition());
		} else if (ruleEntry.getMergeType() == MergeType.AND) {
			final AndCondition and = (AndCondition) existingRule.getCondition();
			and.getConditions().add(newRule.getCondition());
		}
	}

	private static Rule getExistingRule(EList<VAttachment> attachments) {
		for (final VAttachment attachment : attachments) {
			if (Rule.class.isInstance(attachment)) {
				return Rule.class.cast(attachment);
			}
		}
		return null;
	}

}
