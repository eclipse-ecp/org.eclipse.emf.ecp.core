/*******************************************************************************
 * Copyright (c) 2018 Christian W. Damus and others.
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
package org.eclipse.emfforms.internal.core.services.datatemplate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.regex.Pattern;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AuditPackage;
import org.eclipse.emfforms.spi.localization.EMFFormsLocalizationService;
import org.hamcrest.CustomTypeSafeMatcher;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.osgi.framework.Bundle;

/**
 * Unit tests for the {@link EClassLabelProvider} class.
 */
@SuppressWarnings("nls")
@RunWith(MockitoJUnitRunner.class)
public class EClassLabelProvider_PTest {

	@Mock
	private EMFFormsLocalizationService l10nService;

	private EClassLabelProvider labelProvider;

	/**
	 * Initializes me.
	 */
	public EClassLabelProvider_PTest() {
		super();
	}

	/**
	 * Test the provision of text for classes that have edit bundles.
	 */
	@Test
	public void getText_editLabelsAvailable() {
		assertThat(labelProvider.getText(EcorePackage.Literals.EDATA_TYPE), is("PASS"));
	}

	/**
	 * Test the provision of text for classes that do not have edit bundles.
	 */
	@Test
	public void getText_editLabelsUnavailable() {
		final EPackage ePackage = spy(EcoreFactory.eINSTANCE.createEPackage());
		ePackage.setNsURI("http://www.example.com/test/foo");
		ePackage.setName("foo");
		final EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		eClass.setName("FooCamelCase");
		ePackage.getEClassifiers().add(eClass);

		assertThat(labelProvider.getText(eClass), is("FooCamelCase"));

		verify(ePackage, atLeastOnce()).getNsURI();

		// We didn't attempt to get a string from any bundle
		verify(l10nService, never()).getString(any(Bundle.class), anyString());
	}

	/**
	 * Test the provision of text for non-classes.
	 */
	@Test
	public void getText_nonEClass() {
		assertThat(labelProvider.getText(new Object()), containsRegex("@\\p{XDigit}+"));

		// We didn't attempt to get a string from any bundle
		verify(l10nService, never()).getString(any(Bundle.class), anyString());
	}

	/**
	 * Test the provision of images for classes that have edit bundles.
	 */
	@Test
	public void getImage_editImagesAvailable() {
		assertThat(labelProvider.getImage(EcorePackage.Literals.EDATA_TYPE), notNullValue());
	}

	/**
	 * Test the provision of images for classes that do not have edit bundles.
	 */
	@Test
	public void getImage_editImagesUnavailable() {
		assertThat(labelProvider.getImage(AuditPackage.Literals.GUEST_USER), nullValue());
	}

	/**
	 * Test the provision of images for non-classes.
	 */
	@Test
	public void getImage_editImagesNonEClass() {
		assertThat(labelProvider.getImage(new Object()), nullValue());
	}

	//
	// Test framework
	//

	@Before
	public void createLabelProvider() {
		labelProvider = new EClassLabelProvider(l10nService);
	}

	@Before
	public void configureL10nService() {
		when(l10nService.getString(any(Bundle.class), anyString())).thenReturn("PASS");
	}

	@After
	public void verifyL10nService() {
		verify(l10nService, never()).getString(any(Class.class), anyString());
	}

	static Matcher<String> containsRegex(String regex) {
		final Pattern pattern = Pattern.compile(regex);
		return new CustomTypeSafeMatcher<String>("=~ " + pattern) {
			@Override
			protected boolean matchesSafely(String item) {
				return pattern.matcher(item).find();
			}
		};
	}
}
