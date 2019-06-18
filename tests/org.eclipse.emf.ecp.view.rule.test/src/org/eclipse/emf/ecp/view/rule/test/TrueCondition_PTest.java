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
package org.eclipse.emf.ecp.view.rule.test;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.rule.model.RuleFactory;
import org.eclipse.emf.ecp.view.spi.rule.model.True;
import org.junit.Test;

/**
 * Test the {@link True} condition and its service.
 *
 * @author Christian W. Damus
 */
public class TrueCondition_PTest extends AbstractConditionTest<True> {

	/**
	 * Initializes me.
	 */
	public TrueCondition_PTest() {
		super();
	}

	@Test
	public void evaluate() {
		assertThat(evaluate(getModel()), is(true));
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
	protected True createCondition() {
		return RuleFactory.eINSTANCE.createTrue();
	}
}
