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
import static org.mockito.Mockito.atLeastOnce;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.rule.model.Condition;
import org.eclipse.emf.ecp.view.spi.rule.model.IterateCondition;
import org.eclipse.emf.ecp.view.spi.rule.model.LeafCondition;
import org.eclipse.emf.ecp.view.spi.rule.model.Quantifier;
import org.eclipse.emf.ecp.view.spi.rule.model.RuleFactory;
import org.junit.Test;

/**
 * Test the {@link IterateCondition} and its service.
 *
 * @author Christian W. Damus
 */
public class IterateCondition_PTest extends AbstractConditionTest<IterateCondition> {

	private Condition inner;

	/**
	 * Initializes me.
	 */
	public IterateCondition_PTest() {
		super();
	}

	@Test
	public void evaluateAllTrue() {
		getFixture().setQuantifier(Quantifier.ALL);
		assertThat(evaluate(getNested(1)), is(true));
	}

	@Test
	public void evaluateAllFalse() {
		getFixture().setQuantifier(Quantifier.ALL);
		assertThat(evaluate(getNested(2)), is(false));
		assertThat(evaluate(getNested(3)), is(false));
		assertThat(evaluate(getNested(4)), is(false));
	}

	@Test
	public void evaluateAnyTrue() {
		getFixture().setQuantifier(Quantifier.ANY);
		assertThat(evaluate(getNested(1)), is(true));
		assertThat(evaluate(getNested(3)), is(true));
		assertThat(evaluate(getNested(4)), is(true));
	}

	@Test
	public void evaluateAnyFalse() {
		getFixture().setQuantifier(Quantifier.ANY);
		assertThat(evaluate(getNested(2)), is(false));
	}

	@Test
	public void evaluateEmptyTrue() {
		getFixture().setIfEmpty(true);
		getFixture().setQuantifier(Quantifier.ALL);

		getNested(1).getEClassifiers().clear();
		assertThat(evaluate(getNested(1)), is(true));
	}

	@Test
	public void evaluateIfEmptyFalse() {
		getFixture().setIfEmpty(false);
		getFixture().setQuantifier(Quantifier.ANY);

		getNested(1).getEClassifiers().clear();
		assertThat(evaluate(getNested(1)), is(false));
	}

	@Test
	public void settings() {
		final Set<UniqueSetting> expected = new HashSet<UniqueSetting>();

		final EPackage nested = getNested(1);

		// The setting for the reference path
		expected.add(UniqueSetting.createSetting(nested,
			EcorePackage.Literals.EPACKAGE__ECLASSIFIERS));

		// And the settings for the classes' names
		expected.add(UniqueSetting.createSetting(nested.getEClassifiers().get(0),
			EcorePackage.Literals.ENAMED_ELEMENT__NAME));
		expected.add(UniqueSetting.createSetting(nested.getEClassifiers().get(1),
			EcorePackage.Literals.ENAMED_ELEMENT__NAME));

		assertThat(getConditionSettings(getNested(1)), is(expected));
	}

	@Test
	public void references() {
		assertThat(getDomainModelReferences(), is(getDomainModelReferences(inner)));
	}

	/**
	 * Pre-evaluate proposed changes in the constrained values of the iteration targets.
	 */
	@Test
	public void preEvaluateChangesInTargets() {
		getFixture().setIfEmpty(false);
		getFixture().setQuantifier(Quantifier.ALL);

		final Map<EStructuralFeature.Setting, Object> possibleNewValues = new HashMap<EStructuralFeature.Setting, Object>();

		final EPackage ePackage = getNested(1);
		final EClassifier eClass2 = ePackage.getEClassifiers().get(1);
		final EStructuralFeature.Setting eClass2Name = nameSetting(eClass2);

		possibleNewValues.put(eClass2Name, "X");
		assertThat(evaluateChangedValues(ePackage, possibleNewValues), is(false));

		// This is the value that they're supposed to have
		possibleNewValues.put(eClass2Name, "A");
		assertThat(evaluateChangedValues(ePackage, possibleNewValues), is(true));
	}

	/**
	 * Pre-evaluate proposed changes in the set of iteration targets that are constrained.
	 */
	@Test
	public void preEvaluateChangesOfTargets() {
		getFixture().setIfEmpty(false);
		getFixture().setQuantifier(Quantifier.ALL);

		final Map<EStructuralFeature.Setting, Object> possibleNewValues = new HashMap<EStructuralFeature.Setting, Object>();

		// The classifiers in this package are all named "A"
		final EPackage ePackage = getNested(1);
		final EStructuralFeature.Setting classifiers = classifiersSetting(ePackage);

		final EClass newEClass = EcoreFactory.eINSTANCE.createEClass();
		newEClass.setName("X");
		final List<EClassifier> newValue = new ArrayList<EClassifier>(ePackage.getEClassifiers());
		newValue.add(newEClass);

		possibleNewValues.put(classifiers, newValue);
		assertThat(evaluateChangedValues(ePackage, possibleNewValues), is(false));

		// Give the new classifier the same name as the others
		newEClass.setName("A");
		assertThat(evaluateChangedValues(ePackage, possibleNewValues), is(true));
	}

	/**
	 * Verify proper handling of data-binding failures.
	 */
	@Test
	public void dataBindingFailure() {
		final VDomainModelReference fake = (VDomainModelReference) Proxy.newProxyInstance(getClass().getClassLoader(),
			new Class<?>[] { VDomainModelReference.class, InternalEObject.class },
			new InvocationHandler() {

				@Override
				public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
					return null;
				}
			});
		getFixture().setItemReference(fake);

		try {
			getConditionSettings(getModel());
			// BEGIN COMPLEX CODE
		} catch (final Exception e) {
			// END COMPLEX CODE
			// Expected for this fake reference
		} finally {
			assertReports(atLeastOnce());
		}
	}

	//
	// Test framework
	//

	@Override
	protected IterateCondition createCondition() {
		final IterateCondition result = RuleFactory.eINSTANCE.createIterateCondition();

		final LeafCondition inner = RuleFactory.eINSTANCE.createLeafCondition();
		result.setItemCondition(inner);

		inner.setExpectedValue("A");

		final VDomainModelReference innerRef = createDomainModelReference(
			EcorePackage.Literals.ENAMED_ELEMENT__NAME);
		inner.setDomainModelReference(innerRef);

		final VFeaturePathDomainModelReference ref = (VFeaturePathDomainModelReference) createDomainModelReference(
			EcorePackage.Literals.EPACKAGE__ECLASSIFIERS);
		result.setItemReference(ref);

		this.inner = inner;

		return result;
	}

	@Override
	protected EPackage createModel() {
		final EPackage result = super.createModel();

		for (final String name : Arrays.asList("A", "A", "B", "B", "A", "B", "B", "A")) {
			createEClass(result, name);
		}

		for (int index = 0; index < 4; index++) {
			createNestedPackage(result, index);
		}

		return result;
	}

	private EClass createEClass(EPackage ePackage, String name) {
		final EClass result = EcoreFactory.eINSTANCE.createEClass();
		ePackage.getEClassifiers().add(result);
		result.setName(name);
		return result;
	}

	private EPackage createNestedPackage(EPackage nesting, int index) {
		final EPackage result = EcoreFactory.eINSTANCE.createEPackage();
		nesting.getESubpackages().add(result);
		result.setName("nested" + index);

		final int offset = 2 * index;
		for (int subindex = 0; subindex < 2; subindex++) {
			result.getEClassifiers().add(EcoreUtil.copy(nesting.getEClassifiers().get(offset + subindex)));
		}

		return result;
	}

	EPackage getNested(int index) {
		return getModel().getESubpackages().get(index - 1);
	}

	EClass getEClass(int index) {
		return (EClass) getModel().getEClassifiers().get(index - 1);
	}

	EStructuralFeature.Setting classifiersSetting(EPackage ePackage) {
		return ((InternalEObject) ePackage).eSetting(EcorePackage.Literals.EPACKAGE__ECLASSIFIERS);
	}
}
