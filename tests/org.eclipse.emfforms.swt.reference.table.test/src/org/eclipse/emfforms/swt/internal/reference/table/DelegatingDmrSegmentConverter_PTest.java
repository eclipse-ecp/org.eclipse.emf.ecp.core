/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
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
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.SegmentConverterListResultEMF;
import org.eclipse.emfforms.spi.core.services.databinding.emf.SegmentConverterValueResultEMF;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for {@link DelegatingDmrSegmentConverter}.
 *
 * @author Lucas Koehler
 *
 */
public class DelegatingDmrSegmentConverter_PTest {

	private DefaultRealm realm;

	private EPackage textPackage;
	private EClass text;
	private EAttribute words;

	private EObject doc1;
	private EObject doc2;

	private DelegatingDmrSegmentConverter fixture;

	private IObservable observable;

	private VFeatureDomainModelReferenceSegment ecoreNameSegment;
	private VFeatureDomainModelReferenceSegment wordsSegment;

	@Test
	public void isApplicable() {
		assertThat("Should be applicable", fixture.isApplicable(wordsSegment), not(NOT_APPLICABLE));

		final VFeatureDomainModelReferenceSegment other = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		other.setDomainModelFeature(words.getName());
		assertThat("Should not be applicable", fixture.isApplicable(other), is(NOT_APPLICABLE));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void convertToListProperty() throws DatabindingFailedException {
		final SegmentConverterListResultEMF result = fixture.convertToListProperty(wordsSegment, text, null);

		final IObservableList<String> list = result.getListProperty().observe(doc2);
		observable = list;
		assertThat(list.getElementType(), is(words));
		assertThat(list, hasItem("world"));

		list.set(0, "Goodbye");

		assertThat(doc1.eGet(words), is(Arrays.asList("Goodbye", "world")));
	}

	@Test
	public void convertToValueProperty() throws DatabindingFailedException {
		createFixture(ecoreNameSegment, EcorePackage.Literals.ENAMED_ELEMENT__NAME, text);

		// The editing domain is not used
		final SegmentConverterValueResultEMF result = fixture.convertToValueProperty(wordsSegment, text, null);
		@SuppressWarnings("unchecked")
		final IValueProperty<ENamedElement, String> property = result.getValueProperty();
		// If this doesn't work, Ecore package is frozen, so it will throw on attempt to change the
		// name of the EObject EClass
		final IObservableValue<String> value = property.observe(EcorePackage.Literals.EOBJECT);
		observable = value;

		assertThat(value.getValueType(), is(EcorePackage.Literals.ENAMED_ELEMENT__NAME));
		assertThat(value.getValue(), is("Text"));

		value.setValue("Document");

		assertThat(text.getName(), is("Document"));

		assertThat(EcorePackage.Literals.EOBJECT.getName(), not("Document"));
	}

	@Test
	public void getSetting() throws DatabindingFailedException {
		final EStructuralFeature.Setting setting = fixture.getSetting(wordsSegment, doc2);
		assertThat("Wrong feature", setting.getEStructuralFeature(), is(words));
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
		words = EcoreFactory.eINSTANCE.createEAttribute();
		words.setName("words");
		words.setEType(EcorePackage.Literals.ESTRING);
		words.setLowerBound(1);
		words.setUpperBound(ETypedElement.UNBOUNDED_MULTIPLICITY);
		text.getEStructuralFeatures().add(words);

		doc1 = EcoreUtil.create(text);
		doc1.eSet(words, Arrays.asList("Hello", "world"));
		doc2 = EcoreUtil.create(text);
		doc2.eSet(words, Arrays.asList("This", "is", "a", "test"));

		wordsSegment = VViewFactory.eINSTANCE.createFeatureDomainModelReferenceSegment();
		wordsSegment.setDomainModelFeature(words.getName());

		createFixture(wordsSegment, words, doc1);

		ecoreNameSegment = VViewFactory.eINSTANCE.createFeatureDomainModelReferenceSegment();
		ecoreNameSegment.setDomainModelFeature(EcorePackage.Literals.ENAMED_ELEMENT__NAME.getName());
	}

	@After
	public void destroyRealm() {
		if (observable != null) {
			observable.dispose();
		}
		realm.dispose();
	}

	void createFixture(VFeatureDomainModelReferenceSegment segment, EStructuralFeature feature, EObject delegate) {
		fixture = new DelegatingDmrSegmentConverter(segment, feature, __ -> delegate);
	}

}
