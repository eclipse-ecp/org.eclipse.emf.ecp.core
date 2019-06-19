/*******************************************************************************
 * Copyright (c) 2019 Christian W. Damus and others.
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
package org.eclipse.emfforms.swt.internal.reference.table;

import static org.eclipse.emfforms.spi.core.services.databinding.DomainModelReferenceConverter.NOT_APPLICABLE;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.list.IListProperty;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.test.common.DefaultRealm;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for the {@link DelegatingDomainModelReferenceConverter} class.
 */
public class DelegatingDomainModelReferenceConverter_PTest {

	private DefaultRealm realm;

	private EPackage textPackage;
	private EClass text;
	private EAttribute segments;

	private EObject doc1;
	private EObject doc2;

	private VFeaturePathDomainModelReference segmentsDMR;
	private DelegatingDomainModelReferenceConverter fixture;

	private VFeaturePathDomainModelReference ecoreNameDMR;

	private IObservable observable;

	/**
	 * Initializes me.
	 */
	public DelegatingDomainModelReferenceConverter_PTest() {
		super();
	}

	@Test
	public void isApplicable() {
		assertThat("Should be applicable", fixture.isApplicable(segmentsDMR), not(NOT_APPLICABLE));

		final VDomainModelReference other = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		segmentsDMR.setDomainModelEFeature(EcorePackage.Literals.ECLASS__ESUPER_TYPES);
		assertThat("Should not be applicable", fixture.isApplicable(other), is(NOT_APPLICABLE));
	}

	@Test
	public void convertToListProperty() throws DatabindingFailedException {
		@SuppressWarnings("unchecked")
		final IListProperty<EObject, String> property = fixture.convertToListProperty(segmentsDMR, doc2);

		final IObservableList<String> list = property.observe(doc2);
		observable = list;
		assertThat(list.getElementType(), is(segments));
		assertThat(list, hasItem("world"));

		list.set(0, "Goodbye");

		assertThat(doc1.eGet(segments), is(Arrays.asList("Goodbye", "world")));
	}

	@Test
	public void convertToValueProperty_EObject() throws DatabindingFailedException {
		createFixture(ecoreNameDMR, text);

		@SuppressWarnings("unchecked")
		final IValueProperty<ENamedElement, String> property = fixture.convertToValueProperty(ecoreNameDMR,
			EcorePackage.Literals.EOBJECT, null);

		// If this doesn't work, Ecore package is frozen, so it will throw on attempt to change the
		// name of the EObject EClass
		final IObservableValue<String> value = property.observe(EcorePackage.Literals.EOBJECT);
		observable = value;

		assertThat(value.getValueType(), is(EcorePackage.Literals.ENAMED_ELEMENT__NAME));
		assertThat(value.getValue(), is("Text"));

		value.setValue("Document");

		assertThat(text.getName(), is("Document"));

		// Just in case ;-)
		assertThat(EcorePackage.Literals.EOBJECT.getName(), not("Document"));
	}

	@Test
	public void convertToValueProperty_EClass_EditingDomain() throws DatabindingFailedException {
		createFixture(ecoreNameDMR, text);

		@SuppressWarnings("unchecked")
		final IValueProperty<ENamedElement, String> property = fixture.convertToValueProperty(ecoreNameDMR,
			EcorePackage.Literals.ECLASS, null); // The editing domain is not used

		// If this doesn't work, Ecore package is frozen, so it will throw on attempt to change the
		// name of the EObject EClass
		final IObservableValue<String> value = property.observe(EcorePackage.Literals.EOBJECT);
		observable = value;

		assertThat(value.getValueType(), is(EcorePackage.Literals.ENAMED_ELEMENT__NAME));
		assertThat(value.getValue(), is("Text"));

		value.setValue("Document");

		assertThat(text.getName(), is("Document"));

		// Just in case ;-)
		assertThat(EcorePackage.Literals.EOBJECT.getName(), not("Document"));
	}

	@Test
	public void getSetting() throws DatabindingFailedException {
		final EStructuralFeature.Setting setting = fixture.getSetting(segmentsDMR, doc2);
		assertThat("Wrong feature", setting.getEStructuralFeature(), is(segments));
		assertThat("Wrong owner", setting.getEObject(), is(doc1));
	}

	//
	// Test framework
	//

	@Before
	public void createRealm() {
		realm = new DefaultRealm();
	}

	@Before
	public void createFixture() {
		textPackage = EcoreFactory.eINSTANCE.createEPackage();
		textPackage.setName("text");
		textPackage.setNsURI("http://text");
		text = EcoreFactory.eINSTANCE.createEClass();
		text.setName("Text");
		textPackage.getEClassifiers().add(text);
		segments = EcoreFactory.eINSTANCE.createEAttribute();
		segments.setName("segments");
		segments.setEType(EcorePackage.Literals.ESTRING);
		segments.setLowerBound(1);
		segments.setUpperBound(ETypedElement.UNBOUNDED_MULTIPLICITY);
		text.getEStructuralFeatures().add(segments);

		doc1 = EcoreUtil.create(text);
		doc1.eSet(segments, Arrays.asList("Hello", "world"));
		doc2 = EcoreUtil.create(text);
		doc2.eSet(segments, Arrays.asList("This", "is", "a", "test"));

		segmentsDMR = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		segmentsDMR.setDomainModelEFeature(segments);

		createFixture(segmentsDMR, doc1);

		ecoreNameDMR = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		ecoreNameDMR.setDomainModelEFeature(EcorePackage.Literals.ENAMED_ELEMENT__NAME);
	}

	@After
	public void destroyRealm() {
		if (observable != null) {
			observable.dispose();
		}
		realm.dispose();
	}

	void createFixture(VFeaturePathDomainModelReference dmr, EObject delegate) {
		fixture = new DelegatingDomainModelReferenceConverter(dmr, __ -> delegate);
	}

}
