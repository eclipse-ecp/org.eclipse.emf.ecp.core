/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * jfaltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.rule.model.util;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.view.spi.rule.model.LeafCondition;
import org.eclipse.emf.ecp.view.spi.rule.model.impl.LeafConditionSettingIterator;

/**
 * Util class for evaluating conditions.
 *
 * @author jfaltermeier
 * @since 1.5
 *
 */
public final class ConditionEvaluationUtil {

	private ConditionEvaluationUtil() {
		// util
	}

	/**
	 * Whether the leaf condition evaluates objects of the given possible new values.
	 *
	 * @param condition the condition
	 * @param domainModel The root domain object of the given {@link LeafCondition}
	 * @param possibleNewValues the new value map
	 * @return <code>true</code> if setting part of condition, <code>false</code> otherwise
	 * @since 1.9
	 */
	public static boolean isLeafConditionForSetting(LeafCondition condition, EObject domainModel,
		Map<Setting, Object> possibleNewValues) {
		final Set<UniqueSetting> newValueSettings = new LinkedHashSet<UniqueSetting>();
		for (final Setting setting : possibleNewValues.keySet()) {
			newValueSettings.add(UniqueSetting.createSetting(setting));
		}
		final LeafConditionSettingIterator iterator = new LeafConditionSettingIterator(condition, domainModel, true);
		while (iterator.hasNext()) {
			final UniqueSetting next = UniqueSetting.createSetting(iterator.next());
			if (newValueSettings.contains(next)) {
				iterator.dispose();
				return true;
			}
		}
		iterator.dispose();
		return false;
	}

}
