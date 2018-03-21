/*******************************************************************************
 * Copyright (c) 2017 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.rule;

import java.util.Collections;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecp.view.spi.rule.ConditionService;
import org.eclipse.emf.ecp.view.spi.rule.model.Condition;
import org.eclipse.emf.ecp.view.spi.rule.model.NotCondition;
import org.eclipse.emf.ecp.view.spi.rule.model.RulePackage;
import org.osgi.service.component.annotations.Component;

/**
 * Condition service for the {@link NotCondition}.
 *
 * @author Christian W. Damus
 */
@Component(service = ConditionService.class)
public class NotConditionService extends CompositeConditionService<NotCondition> {

	/**
	 * Initializes me.
	 */
	public NotConditionService() {
		super();
	}

	@Override
	public EClass getConditionType() {
		return RulePackage.Literals.NOT_CONDITION;
	}

	@Override
	protected Iterable<? extends Condition> components(NotCondition condition) {
		final Condition result = condition.getCondition();
		if (result == null) {
			return Collections.emptyList();
		}
		return Collections.singletonList(result);
	}

}
