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

import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.rule.model.Condition;

/**
 * The ConditionServiceManager is responsible to delegate evaluation calls about conditions to the corresponding
 * ConditionServices.
 *
 * @author Eugen Neufeld
 *
 */
public interface ConditionServiceManager {

	/**
	 * Returns the conditionSetting for a condition and the corresponding domain model.
	 *
	 * @param condition The condition to get the settings for
	 * @param domainModel The domain model this condition applies to
	 * @return The Set of Settings. This set cannot be null.
	 */
	Set<UniqueSetting> getConditionSettings(Condition condition, EObject domainModel);

	/**
	 * Evaluates the given condition using the provided domain model.
	 *
	 * @param condition The condition to evaluate.
	 * @param domainModel The root domain object of this condition.
	 * @return {@code true}, if the condition matches, {@code false} otherwise
	 */
	boolean evaluate(Condition condition, EObject domainModel);

	/**
	 * Evaluates whether the given condition using the provided domain model will change if a specific setting will be
	 * set to a specific value.
	 *
	 * @param condition The condition to evaluate.
	 * @param domainModel The root domain object of this condition.
	 * @param possibleNewValues
	 *            the new value that should be compared against the expected value of the condition
	 * @return {@code true}, if the condition matches, {@code false} otherwise
	 */
	boolean evaluateChangedValues(Condition condition, EObject domainModel, Map<Setting, Object> possibleNewValues);

	/**
	 * The Set of DomainModelReferences this condition needs to evaluate.
	 *
	 * @param condition The condition to retrieve the VDomainModelReferences for
	 * @return The Set of DomainModelReferences. The set cannot be null.
	 */
	Set<VDomainModelReference> getDomainModelReferences(Condition condition);
}
