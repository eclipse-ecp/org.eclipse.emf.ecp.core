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

import static org.eclipse.emf.ecp.view.spi.model.ModelReferenceHelper.createDomainModelReference;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.rule.model.Condition;
import org.eclipse.emf.ecp.view.spi.rule.model.LeafCondition;
import org.eclipse.emf.ecp.view.spi.rule.model.NotCondition;
import org.eclipse.emf.ecp.view.spi.rule.model.RuleFactory;
import org.junit.Test;

/**
 * Test the {@link NotCondition} and its service.
 *
 * @author Christian W. Damus
 */
public class NotCondition_PTest extends AbstractConditionTest<NotCondition> {

	private Condition inner;

	/**
	 * Initializes me.
	 */
	public NotCondition_PTest() {
		super();
	}

	@Test
	public void evaluate() {
		// Remember that this is a negation
		assertThat(evaluate(getModel()), is(false));

		getModel().setName("Test");

		assertThat(evaluate(getModel()), is(true));
	}

	@Test
	public void settings() {
		assertThat(getConditionSettings(getModel()), is(getConditionSettings(inner, getModel())));
	}

	@Test
	public void references() {
		assertThat(getDomainModelReferences(), is(getDomainModelReferences(inner)));
	}

	@Test
	public void preEvaluateChanges() {
		final Map<EStructuralFeature.Setting, Object> possibleNewValues = new HashMap<EStructuralFeature.Setting, Object>();

		final EStructuralFeature.Setting name = nameSetting(getModel());

		// Remember that this is a negation
		possibleNewValues.put(name, "Test");
		assertThat(evaluateChangedValues(getModel(), possibleNewValues), is(true));

		possibleNewValues.put(name, "Test123");
		assertThat(evaluateChangedValues(getModel(), possibleNewValues), is(false));
	}

	//
	// Test framework
	//

	@Override
	protected NotCondition createCondition() {
		final NotCondition result = RuleFactory.eINSTANCE.createNotCondition();

		final LeafCondition inner = RuleFactory.eINSTANCE.createLeafCondition();
		result.setCondition(inner);

		inner.setExpectedValue("Test123");

		final VDomainModelReference ref = createDomainModelReference(
			EcorePackage.Literals.ENAMED_ELEMENT__NAME);
		inner.setDomainModelReference(ref);
		this.inner = inner;

		return result;
	}

	@Override
	protected EPackage createModel() {
		final EPackage result = super.createModel();

		result.setName("Test123");

		return result;
	}
}
