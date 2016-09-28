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

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.view.spi.model.VElement;
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
		for (final VRuleEntry ruleEntry : ruleRepository.getRuleEntries()) {
			for (final VElement vElement : ruleEntry.getElements()) {
				if (vElement.eIsProxy()) {
					// FIXME: log error
					continue;
				}
				vElement.getAttachments().add(EcoreUtil.copy(ruleEntry.getRule()));
			}
		}
	}

}
