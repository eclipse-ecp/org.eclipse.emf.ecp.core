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
package org.eclipse.emf.ecp.view.internal.rule;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.rule.ConditionService;
import org.eclipse.emf.ecp.view.spi.rule.model.Condition;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * The internal component implementation of the ConditionServiceManager.
 *
 * @author Eugen Neufeld
 *
 */
@Component
public class ConditionServiceManagerImpl implements ConditionServiceManager {

	private final Map<EClass, ConditionService<Condition>> conditionServices = new LinkedHashMap<EClass, ConditionService<Condition>>();

	/**
	 * Called by the framework to add a ConditionService.
	 *
	 * @param <T> The type of the added ConditionService
	 * @param conditionService The ConditionService to add
	 */
	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC, unbind = "removeConditionService")
	protected <T extends Condition> void addConditionService(ConditionService<Condition> conditionService) {
		conditionServices.put(conditionService.getConditionType(), conditionService);
	}

	/**
	 * Called by the framework to remove a ConditionService.
	 *
	 * @param conditionService The ConditionService to remove
	 */
	protected void removeConditionService(ConditionService<Condition> conditionService) {
		conditionServices.remove(conditionService.getConditionType());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.internal.rule.ConditionServiceManager#getConditionSettings(org.eclipse.emf.ecp.view.spi.rule.model.Condition,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public Set<UniqueSetting> getConditionSettings(Condition condition, EObject domainModel) {
		if (condition == null) {
			return Collections.emptySet();
		}
		return conditionServices.get(condition.eClass()).getConditionSettings(condition, domainModel);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.internal.rule.ConditionServiceManager#evaluate(org.eclipse.emf.ecp.view.spi.rule.model.Condition,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public boolean evaluate(Condition condition, EObject domainModel) {
		if (condition == null) {
			return false;
		}
		return conditionServices.get(condition.eClass()).evaluate(condition, domainModel);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.internal.rule.ConditionServiceManager#evaluateChangedValues(org.eclipse.emf.ecp.view.spi.rule.model.Condition,
	 *      org.eclipse.emf.ecore.EObject, java.util.Map)
	 */
	@Override
	public boolean evaluateChangedValues(Condition condition, EObject domainModel,
		Map<Setting, Object> possibleNewValues) {
		if (condition == null) {
			return false;
		}
		return conditionServices.get(condition.eClass()).evaluateChangedValues(condition, domainModel,
			possibleNewValues);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.internal.rule.ConditionServiceManager#getDomainModelReferences(org.eclipse.emf.ecp.view.spi.rule.model.Condition)
	 */
	@Override
	public Set<VDomainModelReference> getDomainModelReferences(Condition condition) {
		if (condition == null) {
			return Collections.emptySet();
		}
		return conditionServices.get(condition.eClass()).getDomainModelReferences(condition);
	}

}
