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
package org.eclipse.emf.ecp.view.spi.table.swt;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emfforms.spi.common.BundleResolver;
import org.eclipse.emfforms.spi.common.BundleResolver.NoBundleFoundException;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.localization.EMFFormsLocalizationService;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.Bundle;

/**
 * Unit tests for {@link LocalizedEnumeratorComparator}. Run as plugin test because otherwise the hamcrest matchers do
 * not work due to a security exception.
 *
 * @author Lucas Koehler
 *
 */
public class LocalizedEnumeratorComparator_PTest {

	private static final String TEST_ENUM = "TestEnum";
	private static final String TEST_LITERAL = "test literal";
	private static final String TEST_NAME = "testName";
	private static final String KEY_TEMPLATE = "_UI_" + TEST_ENUM + "_%s_literal";
	private static final String TEST_NAME_2 = "testZName";
	private static final String TEST_LITERAL_2 = "test z literal";

	private BundleResolver bundleResolver;
	private ReportService reportService;
	private EMFFormsLocalizationService localization;
	private LocalizedEnumeratorComparator comparator;
	private EEnumLiteral testLiteral;
	private EAttribute feature;
	private EEnumLiteral testLiteral2;

	@Before
	public void setUp() {
		bundleResolver = mock(BundleResolver.class);
		reportService = mock(ReportService.class);
		localization = mock(EMFFormsLocalizationService.class);
		comparator = new LocalizedEnumeratorComparator(localization, bundleResolver, reportService);

		final EEnum testEnum = EcoreFactory.eINSTANCE.createEEnum();
		testEnum.setName(TEST_ENUM);
		testLiteral = EcoreFactory.eINSTANCE.createEEnumLiteral();
		testLiteral.setName(TEST_NAME);
		testLiteral.setLiteral(TEST_LITERAL);
		testEnum.getELiterals().add(testLiteral);

		testLiteral2 = EcoreFactory.eINSTANCE.createEEnumLiteral();
		testLiteral2.setName(TEST_NAME_2);
		testLiteral2.setLiteral(TEST_LITERAL_2);
		testEnum.getELiterals().add(testLiteral2);

		feature = EcoreFactory.eINSTANCE.createEAttribute();
		feature.setName("TestAttribute");
		feature.setEType(testEnum);

		final EClass containerClass = EcoreFactory.eINSTANCE.createEClass();
		containerClass.setName("ContainerClass");
		containerClass.getEStructuralFeatures().add(feature);
	}

	@Test
	public void compare_bothNull() {
		final int result = comparator.compare(feature, null, null);
		verify(reportService, never()).report(any());
		assertEquals(0, result);
	}

	@Test
	public void compare_rightNull() {
		final int result = comparator.compare(feature, testLiteral, null);
		verify(reportService, never()).report(any());
		assertThat(result, lessThanOrEqualTo(-1));
	}

	@Test
	public void compare_leftNull() {
		final int result = comparator.compare(feature, null, testLiteral);
		verify(reportService, never()).report(any());
		assertThat(result, greaterThanOrEqualTo(1));
	}

	@Test
	public void compare() throws NoBundleFoundException {
		final Bundle bundle = mock(Bundle.class);
		when(bundleResolver.getEditBundle(any())).thenReturn(bundle);
		when(localization.getString(bundle, String.format(KEY_TEMPLATE, TEST_NAME))).thenReturn("b");
		when(localization.getString(bundle, String.format(KEY_TEMPLATE, TEST_NAME_2))).thenReturn("a");

		final int result = comparator.compare(feature, testLiteral, testLiteral2);

		// With the non-localized literals, the result would be -1
		assertThat(result, greaterThanOrEqualTo(1));
		verify(reportService, never()).report(any());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void compare_noBundle() throws NoBundleFoundException {
		when(bundleResolver.getEditBundle(any())).thenThrow(NoBundleFoundException.class);

		final int result = comparator.compare(feature, testLiteral, testLiteral2);

		assertThat(result, lessThanOrEqualTo(-1));
		verify(localization, never()).getString((Bundle) any(), any());
		verify(reportService, times(1)).report(notNull(AbstractReport.class));
	}
}
