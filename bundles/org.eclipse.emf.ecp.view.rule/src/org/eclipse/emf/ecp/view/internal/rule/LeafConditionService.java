/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.rule;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.rule.ConditionService;
import org.eclipse.emf.ecp.view.spi.rule.model.LeafCondition;
import org.eclipse.emf.ecp.view.spi.rule.model.RulePackage;
import org.eclipse.emf.ecp.view.spi.rule.model.impl.LeafConditionSettingIterator;
import org.osgi.service.component.annotations.Component;

/** {@link ConditionService} for conditions of type {@link LeafCondition}. */
@Component
public class LeafConditionService implements ConditionService<LeafCondition> {

	@Override
	public EClass getConditionType() {
		return RulePackage.eINSTANCE.getLeafCondition();
	}

	@Override
	public Set<UniqueSetting> getConditionSettings(LeafCondition condition, EObject domainModel) {
		final Set<UniqueSetting> registeredSettings = new LinkedHashSet<UniqueSetting>();
		if (condition == null) {
			return registeredSettings;
		}
		final LeafConditionSettingIterator iterator = new LeafConditionSettingIterator(condition,
			domainModel, true);
		while (iterator.hasNext()) {
			final Setting setting = iterator.next();
			final UniqueSetting uniqueSetting = UniqueSetting.createSetting(setting);
			registeredSettings.add(uniqueSetting);
		}
		iterator.dispose();
		return registeredSettings;
	}

	@Override
	public Set<VDomainModelReference> getDomainModelReferences(LeafCondition condition) {
		final Set<VDomainModelReference> references = new LinkedHashSet<VDomainModelReference>();
		if (condition == null) {
			return references;
		}
		final VDomainModelReference domainModelReference = condition.getDomainModelReference();
		references.add(domainModelReference);
		// FIXME
		//
		// if a value dmr exists -> create a new dmr that combines the rule value dmr with the rule dmr
		// in case the rule dmr is a multi reference, a table reference needs to be returned, with a column ref poining
		// to the value dmr

		// references.add(condition.getValueDomainModelReference());

		return references;
	}

	@Override
	public boolean evaluate(LeafCondition condition, EObject domainModel) {
		return condition.evaluate(domainModel);
	}

	@Override
	public boolean evaluateChangedValues(LeafCondition condition, EObject domainModel,
		Map<Setting, Object> possibleNewValues) {
		return condition.evaluateChangedValues(domainModel, possibleNewValues);
	}

}
