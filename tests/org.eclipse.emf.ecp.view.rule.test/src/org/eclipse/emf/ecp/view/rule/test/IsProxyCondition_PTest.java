/*******************************************************************************
 * Copyright (c) 2018 Christian W. Damus and others.
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
import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.rule.model.IsProxyCondition;
import org.eclipse.emf.ecp.view.spi.rule.model.RuleFactory;
import org.junit.Test;

/**
 * Test the {@link IsProxyCondition} and its service.
 *
 * @author Christian W. Damus
 */
public class IsProxyCondition_PTest extends AbstractConditionTest<IsProxyCondition> {
	private static final String ANNOTATION_SOURCE = IsProxyCondition_PTest.class.getName();

	/**
	 * Initializes me.
	 */
	public IsProxyCondition_PTest() {
		super();
	}

	@Test
	public void evaluate() {
		assertThat(evaluate(getModel()), is(false));
	}

	@Test
	public void evaluateOnProxy() {
		final EObject model = getModel();
		makeProxy(model, "bogus://test/model#object");
		assertThat(evaluate(model), is(true));
	}

	@Test
	public void evaluateWithDMR() {
		createDMR();
		assertThat(evaluate(getAnnotation()), is(false));
	}

	@Test
	public void evaluateWithDMRContainingNothing() {
		createDMR();
		getAnnotation().getReferences().clear();
		assertThat(evaluate(getAnnotation()), is(false));
	}

	@Test
	public void evaluateWithDMRContainingProxy() {
		createDMR();
		final EObject ref = getAnnotation().getReferences().get(0);
		makeProxy(ref, "bogus://test/model#object");
		assertThat(evaluate(getAnnotation()), is(true));
	}

	@Test
	public void evaluateWithScalarDMR() {
		createScalarDMR();
		assertThat(evaluate(getAttribute()), is(false));
	}

	@Test
	public void evaluateWithScalarDMRContainingNothing() {
		createScalarDMR();
		getAttribute().setEType(null);
		assertThat(evaluate(getAttribute()), is(false));
	}

	@Test
	public void evaluateWithScalarDMRContainingProxy() {
		createScalarDMR();
		getAttribute().setEType(makeProxy(EcorePackage.Literals.EDATA_TYPE, "bogus://test/model#type",
			EDataType.class));
		assertThat(evaluate(getAttribute()), is(true));
	}

	/**
	 * Pre-evaluate proposed changes reference.
	 */
	@Test
	public void preEvaluateChangesWithDMR() {
		createDMR();

		final Map<EStructuralFeature.Setting, Object> possibleNewValues = new HashMap<EStructuralFeature.Setting, Object>();

		final EObject ref = EcoreFactory.eINSTANCE.createEObject();

		// Propose addition of a new reference
		final List<EObject> proposedRefs = new ArrayList<EObject>(getAnnotation().getReferences());
		proposedRefs.add(ref);

		possibleNewValues.put(referencesSetting(getAnnotation()), proposedRefs);
		assertThat(evaluateChangedValues(getAnnotation(), possibleNewValues), is(false));

		// And make the proposed new object a proxy
		makeProxy(ref, "bogus://test/model#object");
		assertThat(evaluateChangedValues(getAnnotation(), possibleNewValues), is(true));
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
	protected IsProxyCondition createCondition() {
		return RuleFactory.eINSTANCE.createIsProxyCondition();
	}

	@Override
	protected EPackage createModel() {
		final EPackage result = super.createModel();

		final EAnnotation annotation = EcoreFactory.eINSTANCE.createEAnnotation();
		annotation.setSource(ANNOTATION_SOURCE);
		annotation.getReferences().add(EcoreFactory.eINSTANCE.createEObject());
		result.getEAnnotations().add(annotation);

		final EClass foo = EcoreFactory.eINSTANCE.createEClass();
		foo.setName("Foo");
		result.getEClassifiers().add(foo);
		final EAttribute attr = EcoreFactory.eINSTANCE.createEAttribute();
		attr.setName("attr");
		attr.setEType(EcorePackage.Literals.ESTRING);
		foo.getEStructuralFeatures().add(attr);

		return result;
	}

	EAnnotation getAnnotation() {
		return getModel().getEAnnotation(ANNOTATION_SOURCE);
	}

	EAttribute getAttribute() {
		return (EAttribute) ((EClass) getModel().getEClassifier("Foo")).getEStructuralFeature("attr");
	}

	void makeProxy(EObject object, String uri) {
		((InternalEObject) object).eSetProxyURI(URI.createURI(uri));
	}

	<T extends EObject> T makeProxy(EClass eClass, String uri, Class<T> type) {
		final EObject result = EcoreUtil.create(eClass);
		makeProxy(result, uri);
		return type.cast(result);
	}

	void createDMR() {
		getFixture().setDomainModelReference(createDomainModelReference(
			EcorePackage.Literals.EANNOTATION__REFERENCES));
	}

	void createScalarDMR() {
		getFixture().setDomainModelReference(createDomainModelReference(
			EcorePackage.Literals.ETYPED_ELEMENT__ETYPE));
	}

	protected EStructuralFeature.Setting referencesSetting(EAnnotation element) {
		return ((InternalEObject) element).eSetting(EcorePackage.Literals.EANNOTATION__REFERENCES);
	}

}
