/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Jonas - initial API and implementation
 * Christian W. Damus - bugs 527753, 530900
 ******************************************************************************/
package org.eclipse.emf.ecp.view.rule.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	RuleServiceGC_PTest.class,
	RuleService_PTest.class,
	ConditionEvaluator_PTest.class,
	DynamicRuleService_PTest.class,
	RuleRegistry_PTest.class,
	TrueCondition_PTest.class, FalseCondition_PTest.class,
	NotCondition_PTest.class, IterateCondition_PTest.class,
	IsProxyCondition_PTest.class,
})
public class AllTests {

}
