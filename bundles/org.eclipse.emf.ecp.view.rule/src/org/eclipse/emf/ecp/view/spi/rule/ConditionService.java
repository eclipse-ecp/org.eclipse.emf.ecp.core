/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.rule;

import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.rule.model.Condition;

/**
 * The {@link ConditionService} is used to retrieve the relevant information for conditions.
 *
 * @param <T> The type of the Condition this service applies to
 * @author Eugen Neufeld
 * @since 1.10
 */
public interface ConditionService<T extends Condition> {

	/**
	 * The EClass this ConditionService is implemented for.
	 *
	 * @return The EClass of the {@link Condition}
	 */
	EClass getConditionType();

	/**
	 * The set of {@link UniqueSetting} that are relevant for the provided condition and domain model.
	 *
	 * @param condition The {@link Condition} to get the UniqueSettings for
	 * @param domainModel The {@link EObject} to use for retrieving
	 * @return The Set of UniqueSettings. This Set must not be null.
	 */
	Set<UniqueSetting> getConditionSettings(T condition, EObject domainModel);

	/**
	 * Evaluates the given condition.
	 *
	 * @param condition The Condition to evaluate
	 * @param domainModel The root domain object of this condition.
	 * @return {@code true}, if the condition matches, {@code false} otherwise
	 */
	boolean evaluate(T condition, EObject domainModel);

	/**
	 * Evaluates the given condition.
	 *
	 * @param condition The Condition to evaluate
	 * @param domainModel The root domain object of this condition.
	 * @param possibleNewValues
	 *            the new value that should be compared against the expected value of the condition
	 * @return {@code true}, if the condition matches, {@code false} otherwise
	 */
	boolean evaluateChangedValues(T condition, EObject domainModel, Map<Setting, Object> possibleNewValues);

	/**
	 * The Set of {@link VDomainModelReference} that are relevant for the condition.
	 *
	 * @param condition The {@link Condition} to retrieve the VDMRs for
	 * @return The Set of VDomainModelReferences. This Set must not be null.
	 */
	Set<VDomainModelReference> getDomainModelReferences(T condition);
}
