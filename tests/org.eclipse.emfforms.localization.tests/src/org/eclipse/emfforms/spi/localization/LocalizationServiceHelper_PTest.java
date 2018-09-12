/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.localization;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.eclipse.emfforms.spi.common.locale.EMFFormsLocaleProvider;
import org.junit.Test;
import org.mockito.Mockito;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * Plugin tests for the LocalizationServiceHelper.
 *
 * @author Eugen Neufeld
 *
 */
public class LocalizationServiceHelper_PTest {

	private static final String TEST_KEY = "testKey"; //$NON-NLS-1$
	private static final String TEST_VALUE = "Test Value"; //$NON-NLS-1$
	private static final String TEST_VALUE_LANGUAGE = "Test Value Language"; //$NON-NLS-1$
	private static final String TEST_VALUE_LANGUAGE_COUNTRY = "Test Value Country"; //$NON-NLS-1$
	private static final String TEST_VALUE_LANGUAGE_COUNTRY_VARIANT = "Test Value Variant"; //$NON-NLS-1$
	private static final String TEST_VALUE_FOO = "Test Value Foo"; //$NON-NLS-1$
	private static final String TEST_VALUE_BAR = "Test Value Bar"; //$NON-NLS-1$
	private static final String LOCALE_LANGUAGE = "test"; //$NON-NLS-1$
	private static final String LOCALE_COUNTRY = "country"; //$NON-NLS-1$
	private static final String LOCALE_VARIANT = "variant"; //$NON-NLS-1$
	private static final String LOCALE_FOO = "foo"; //$NON-NLS-1$
	private static final String LOCALE_BAR = "bar"; //$NON-NLS-1$

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.spi.localization.LocalizationServiceHelper#getString(java.lang.Class, java.lang.String)}
	 * .
	 */
	@Test
	public void testGetStringWithoutLocaleProvider() {
		final String string = LocalizationServiceHelper.getString(getClass(), TEST_KEY);
		assertEquals(TEST_VALUE, string);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.spi.localization.LocalizationServiceHelper#getString(java.lang.Class, java.lang.String)}
	 * .
	 */
	@Test
	public void testGetUndefinedStringWithoutLocaleProvider() {
		final String myKey = "MyKey"; //$NON-NLS-1$
		final String string = LocalizationServiceHelper.getString(getClass(), myKey);
		assertEquals(myKey, string);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.spi.localization.LocalizationServiceHelper#getString(java.lang.Class, java.lang.String)}
	 * .
	 */
	@Test
	public void testGetStringWithLocaleProvider_Language() {
		final Locale locale = new Locale(LOCALE_LANGUAGE);
		final ServiceRegistration<EMFFormsLocaleProvider> registerService = setupLocale(locale);

		final String string = LocalizationServiceHelper.getString(getClass(), TEST_KEY);

		assertEquals(TEST_VALUE_LANGUAGE, string);
		registerService.unregister();
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.spi.localization.LocalizationServiceHelper#getString(java.lang.Class, java.lang.String)}
	 * .
	 */
	@Test
	public void testGetStringWithLocaleProvider_Country() {
		final Locale locale = new Locale(LOCALE_LANGUAGE, LOCALE_COUNTRY);
		final ServiceRegistration<EMFFormsLocaleProvider> registerService = setupLocale(locale);

		final String string = LocalizationServiceHelper.getString(getClass(), TEST_KEY);
		assertEquals(TEST_VALUE_LANGUAGE_COUNTRY, string);
		registerService.unregister();
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.spi.localization.LocalizationServiceHelper#getString(java.lang.Class, java.lang.String)}
	 * .
	 */
	@Test
	public void testGetStringWithLocaleProvider_Variant() {
		final Locale locale = new Locale(LOCALE_LANGUAGE, LOCALE_COUNTRY, LOCALE_VARIANT);
		final ServiceRegistration<EMFFormsLocaleProvider> registerService = setupLocale(locale);

		final String string = LocalizationServiceHelper.getString(getClass(), TEST_KEY);
		assertEquals(TEST_VALUE_LANGUAGE_COUNTRY_VARIANT, string);
		registerService.unregister();
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.spi.localization.LocalizationServiceHelper#getString(java.lang.Class, java.lang.String)}
	 * .
	 */
	@Test
	public void testGetStringWithLocaleProvider_FallbackCountry() {
		final Locale locale = new Locale(LOCALE_BAR, LOCALE_BAR, LOCALE_BAR);
		final ServiceRegistration<EMFFormsLocaleProvider> registerService = setupLocale(locale);

		final String string = LocalizationServiceHelper.getString(getClass(), TEST_KEY);
		assertEquals(TEST_VALUE_BAR, string);
		registerService.unregister();
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.spi.localization.LocalizationServiceHelper#getString(java.lang.Class, java.lang.String)}
	 * .
	 */
	@Test
	public void testGetStringWithLocaleProvider_FallbackLanguage() {
		final Locale locale = new Locale(LOCALE_FOO, LOCALE_BAR);
		final ServiceRegistration<EMFFormsLocaleProvider> registerService = setupLocale(locale);

		final String string = LocalizationServiceHelper.getString(getClass(), TEST_KEY);
		assertEquals(TEST_VALUE_FOO, string);
		registerService.unregister();
	}

	private ServiceRegistration<EMFFormsLocaleProvider> setupLocale(Locale locale) {
		final BundleContext bundleContext = FrameworkUtil.getBundle(getClass()).getBundleContext();
		final EMFFormsLocaleProvider localeProvider = Mockito.mock(EMFFormsLocaleProvider.class);
		Mockito.when(localeProvider.getLocale()).thenReturn(locale);
		return bundleContext.registerService(EMFFormsLocaleProvider.class, localeProvider, null);
	}

}
