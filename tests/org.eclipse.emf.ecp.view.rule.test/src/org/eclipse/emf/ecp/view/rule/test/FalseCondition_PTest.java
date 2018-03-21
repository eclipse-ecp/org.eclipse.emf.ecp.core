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
package org.eclipse.emf.ecp.view.rule.test;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.rule.model.False;
import org.eclipse.emf.ecp.view.spi.rule.model.RuleFactory;
import org.junit.Test;

/**
 * Test the {@link False} condition and its service.
 *
 * @author Christian W. Damus
 */
public class FalseCondition_PTest extends AbstractConditionTest<False> {

	/**
	 * Initializes me.
	 */
	public FalseCondition_PTest() {
		super();
	}

	@Test
	public void evaluate() {
		assertThat(evaluate(getModel()), is(false));
	}

	@Test
	public void settings() {
		assertThat(getConditionSettings(getModel()), not(hasItem(any(UniqueSetting.class))));
	}

	@Test
	public void references() {
		assertThat(getDomainModelReferences(), not(hasItem(any(VDomainModelReference.class))));
	}

	//
	// Test framework
	//

	@Override
	protected False createCondition() {
		return RuleFactory.eINSTANCE.createFalse();
	}
}
