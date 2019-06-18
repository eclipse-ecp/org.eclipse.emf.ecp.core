/*******************************************************************************
 * Copyright (c) 2017 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.rule;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.rule.ConditionService;
import org.eclipse.emf.ecp.view.spi.rule.model.False;
import org.eclipse.emf.ecp.view.spi.rule.model.RulePackage;
import org.osgi.service.component.annotations.Component;

/**
 * A trivial condition service for the {@link False} condition.
 *
 * @author Christian W. Damus
 */
@Component
public class FalseConditionService implements ConditionService<False> {

	/**
	 * Initializes me.
	 */
	public FalseConditionService() {
		super();
	}

	@Override
	public EClass getConditionType() {
		return RulePackage.Literals.FALSE;
	}

	@Override
	public boolean evaluate(False condition, EObject domainModel) {
		return condition.evaluate(domainModel);
	}

	@Override
	public boolean evaluateChangedValues(False condition, EObject domainModel, Map<Setting, Object> possibleNewValues) {
		return condition.evaluateChangedValues(domainModel, possibleNewValues);
	}

	@Override
	public Set<VDomainModelReference> getDomainModelReferences(False condition) {
		return Collections.emptySet(); // It's a literal value, not really a condition
	}

	@Override
	public Set<UniqueSetting> getConditionSettings(False condition, EObject domainModel) {
		return Collections.emptySet(); // It's a literal value, not really a condition
	}

}
