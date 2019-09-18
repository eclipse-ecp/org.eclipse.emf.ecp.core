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

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emfforms.spi.common.BundleResolver;
import org.eclipse.emfforms.spi.common.BundleResolver.NoBundleFoundException;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.localization.EMFFormsLocalizationService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.Bundle;

/**
 * Unit tests for {@link ItemProviderEnumCellEditor}. Run as plugin test to be able to create a new Shell.
 *
 * @author Lucas Koehler
 *
 */
public class ItemProviderEnumCellEditor_PTest {

	private static final String TEST_ENUM = "TestEnum";
	private static final String TEST_LITERAL = "test literal";
	private static final String TEST_NAME = "testName";
	private static final String KEY_TEMPLATE = "_UI_" + TEST_ENUM + "_%s_literal";

	private ItemProviderEnumCellEditor cellEditor;
	private Shell shell;
	private BundleResolver bundleResolver;
	private EMFFormsLocalizationService localization;
	private EStructuralFeature feature;
	private ViewModelContext viewContext;
	private ReportService reportService;

	private EEnumLiteral testLiteral;

	@Before
	public void setUp() {
		shell = new Shell(Display.getDefault());
		bundleResolver = mock(BundleResolver.class);
		localization = mock(EMFFormsLocalizationService.class);
		viewContext = mock(ViewModelContext.class);
		reportService = mock(ReportService.class);
		when(viewContext.getService(ReportService.class)).thenReturn(reportService);
		cellEditor = new ItemProviderEnumCellEditor(shell, SWT.NONE, bundleResolver, localization);

		final EEnum testEnum = EcoreFactory.eINSTANCE.createEEnum();
		testEnum.setName(TEST_ENUM);
		testLiteral = EcoreFactory.eINSTANCE.createEEnumLiteral();
		testLiteral.setName(TEST_NAME);
		testLiteral.setLiteral(TEST_LITERAL);
		testEnum.getELiterals().add(testLiteral);

		feature = EcoreFactory.eINSTANCE.createEAttribute();
		feature.setName("TestAttribute");
		feature.setEType(testEnum);

		final EClass containerClass = EcoreFactory.eINSTANCE.createEClass();
		containerClass.setName("ContainerClass");
		containerClass.getEStructuralFeatures().add(feature);
	}

	@After
	public void disposeShell() {
		if (shell != null && !shell.isDisposed()) {
			shell.dispose();
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getFormatedString_noBundle() throws NoBundleFoundException {
		when(bundleResolver.getEditBundle(any())).thenThrow(NoBundleFoundException.class);
		cellEditor.instantiate(feature, viewContext);
		final String result = cellEditor.getFormatedString(testLiteral);

		verify(localization, never()).getString((Bundle) any(), any());
		verify(reportService, times(1)).report(any());
		// original literal text
		assertEquals(TEST_LITERAL, result);
	}

	@Test
	public void getFormatedString_localized() throws NoBundleFoundException {
		final String key = String.format(KEY_TEMPLATE, TEST_NAME);
		final String localized = "some localization";

		final Bundle bundle = mock(Bundle.class);
		when(bundleResolver.getEditBundle(feature.getEType())).thenReturn(bundle);
		when(localization.getString(bundle, key)).thenReturn(localized);

		cellEditor.instantiate(feature, viewContext);
		final String result = cellEditor.getFormatedString(testLiteral);

		verify(reportService, never()).report(any());
		assertEquals(localized, result);
	}

	@Test
	public void getFormatedString_noLocalizedString() throws NoBundleFoundException {
		final String key = String.format(KEY_TEMPLATE, TEST_NAME);

		final Bundle bundle = mock(Bundle.class);
		when(bundleResolver.getEditBundle(feature.getEType())).thenReturn(bundle);

		cellEditor.instantiate(feature, viewContext);
		final String result = cellEditor.getFormatedString(testLiteral);

		// once during init, once during getFormatedString
		verify(localization, times(2)).getString(bundle, key);
		verify(reportService, never()).report(any());
		assertEquals(TEST_LITERAL, result);
	}
}
