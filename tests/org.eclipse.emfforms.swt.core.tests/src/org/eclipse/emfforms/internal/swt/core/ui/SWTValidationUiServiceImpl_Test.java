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
package org.eclipse.emfforms.internal.swt.core.ui;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VDiagnostic;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emfforms.spi.swt.core.ui.SWTValidationHelper;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link SWTValidationUiServiceImpl}.
 *
 * @author Lucas Koehler
 *
 */
public class SWTValidationUiServiceImpl_Test {

	private SWTValidationHelper validationHelper;
	private SWTValidationUiServiceImpl fixture;
	private VElement vElement;
	private ViewModelContext viewContext;

	@Before
	public void setUp() {
		validationHelper = mock(SWTValidationHelper.class);
		fixture = new SWTValidationUiServiceImpl(validationHelper);
		vElement = mock(VElement.class);
		viewContext = mock(ViewModelContext.class);
	}

	/**
	 * Test that getValidationIcon(VElement, ViewModelContext) correctly delegates to getValidationIcon(Diagnostic,
	 * VElement, ViewModelContext) and that the latter correctly delegates to SWTValidationHelper.
	 */
	@Test
	public void getValidationIcon() {
		// Spy to be able to verify method calls
		final SWTValidationUiServiceImpl spiedFixture = spy(fixture);
		final VDiagnostic vDiagnostic = VViewFactory.eINSTANCE.createDiagnostic();
		final Diagnostic ok = mock(Diagnostic.class);
		when(ok.getSeverity()).thenReturn(Diagnostic.OK);
		final Diagnostic error = mock(Diagnostic.class);
		when(error.getSeverity()).thenReturn(Diagnostic.ERROR);
		vDiagnostic.getDiagnostics().add(ok);
		vDiagnostic.getDiagnostics().add(error);
		when(vElement.getDiagnostic()).thenReturn(vDiagnostic);
		final Image expected = new Image(Display.getDefault(), 1, 1);
		when(validationHelper.getValidationIcon(Diagnostic.ERROR, vElement, viewContext)).thenReturn(expected);

		final Image result = spiedFixture.getValidationIcon(vElement, viewContext);

		assertSame(expected, result);
		verify(spiedFixture).getValidationIcon(error, vElement, viewContext);
	}

	/** If the VElement doesn't have a VDiagnostic, it should be assumed that there is no validation error. */
	@Test
	public void getValidationIcon_nullVDiagnostic() {
		// Spy to be able to verify method calls
		final SWTValidationUiServiceImpl spiedFixture = spy(fixture);
		when(vElement.getDiagnostic()).thenReturn(null);
		final Image expected = new Image(Display.getDefault(), 1, 1);
		when(validationHelper.getValidationIcon(Diagnostic.OK, vElement, viewContext)).thenReturn(expected);

		final Image result = spiedFixture.getValidationIcon(vElement, viewContext);

		assertSame(expected, result);
		verify(spiedFixture).getValidationIcon(Diagnostic.OK_INSTANCE, vElement, viewContext);
	}

	/**
	 * If the VElement's VDiagnostic doesn't have any Diagnostics, it should be assumed that there is no validation
	 * error.
	 */
	@Test
	public void getValidationIcon_emptyVDiagnostic() {
		// Spy to be able to verify method calls
		final SWTValidationUiServiceImpl spiedFixture = spy(fixture);
		final VDiagnostic vDiagnostic = VViewFactory.eINSTANCE.createDiagnostic();
		when(vElement.getDiagnostic()).thenReturn(vDiagnostic);
		final Image expected = new Image(Display.getDefault(), 1, 1);
		when(validationHelper.getValidationIcon(Diagnostic.OK, vElement, viewContext)).thenReturn(expected);

		final Image result = spiedFixture.getValidationIcon(vElement, viewContext);

		assertSame(expected, result);
		verify(spiedFixture).getValidationIcon(Diagnostic.OK_INSTANCE, vElement, viewContext);
	}

	/**
	 * If the given Diagnostic is null, it should be assumed that there is no validation error.
	 */
	@Test
	public void getValidationIcon_nullDiagnostic() {
		// Spy to be able to verify method calls
		final SWTValidationUiServiceImpl spiedFixture = spy(fixture);
		final Image expected = new Image(Display.getDefault(), 1, 1);
		when(validationHelper.getValidationIcon(Diagnostic.OK, vElement, viewContext)).thenReturn(expected);

		final Image result = spiedFixture.getValidationIcon(null, vElement, viewContext);

		assertSame(expected, result);
	}

	/** The service should cache icons by severity. */
	@Test
	public void getValidationIcon_caching() {
		// Spy to be able to verify method calls
		final SWTValidationUiServiceImpl spiedFixture = spy(fixture);
		final Image expected = new Image(Display.getDefault(), 1, 1);
		when(validationHelper.getValidationIcon(Diagnostic.OK, vElement, viewContext)).thenReturn(expected);
		final Diagnostic ok1 = mock(Diagnostic.class);
		when(ok1.getSeverity()).thenReturn(Diagnostic.OK);
		final Diagnostic ok2 = mock(Diagnostic.class);
		when(ok2.getSeverity()).thenReturn(Diagnostic.OK);

		final Image result1 = spiedFixture.getValidationIcon(ok1, vElement, viewContext);
		final Image result2 = spiedFixture.getValidationIcon(ok2, vElement, viewContext);

		assertSame(expected, result1);
		assertSame(expected, result2);
		// if caching is used, the validation helper only needs to be called once
		verify(validationHelper, times(1)).getValidationIcon(Diagnostic.OK, vElement, viewContext);
	}

	/**
	 * Test that getValidationForegroundColor(VElement, ViewModelContext) correctly delegates to
	 * getValidationForegroundColor(Diagnostic, VElement, ViewModelContext) and that the latter correctly delegates to
	 * SWTValidationHelper.
	 */
	@Test
	public void getValidationForegroundColor() {
		// Spy to be able to verify method calls
		final SWTValidationUiServiceImpl spiedFixture = spy(fixture);
		final VDiagnostic vDiagnostic = VViewFactory.eINSTANCE.createDiagnostic();
		final Diagnostic ok = mock(Diagnostic.class);
		when(ok.getSeverity()).thenReturn(Diagnostic.OK);
		final Diagnostic error = mock(Diagnostic.class);
		when(error.getSeverity()).thenReturn(Diagnostic.ERROR);
		vDiagnostic.getDiagnostics().add(ok);
		vDiagnostic.getDiagnostics().add(error);
		when(vElement.getDiagnostic()).thenReturn(vDiagnostic);
		final Color expected = new Color(Display.getDefault(), 1, 2, 3);
		when(validationHelper.getValidationForegroundColor(Diagnostic.ERROR, vElement, viewContext))
			.thenReturn(expected);

		final Color result = spiedFixture.getValidationForegroundColor(vElement, viewContext);

		assertSame(expected, result);
		verify(spiedFixture).getValidationForegroundColor(error, vElement, viewContext);
	}

	/** If the VElement doesn't have a VDiagnostic, it should be assumed that there is no validation error. */
	@Test
	public void getValidationForegroundColor_nullVDiagnostic() {
		// Spy to be able to verify method calls
		final SWTValidationUiServiceImpl spiedFixture = spy(fixture);
		when(vElement.getDiagnostic()).thenReturn(null);
		final Color expected = new Color(Display.getDefault(), 1, 2, 3);
		when(validationHelper.getValidationForegroundColor(Diagnostic.OK, vElement, viewContext)).thenReturn(expected);

		final Color result = spiedFixture.getValidationForegroundColor(vElement, viewContext);

		assertSame(expected, result);
		verify(spiedFixture).getValidationForegroundColor(Diagnostic.OK_INSTANCE, vElement, viewContext);
	}

	/**
	 * If the VElement's VDiagnostic doesn't have any Diagnostics, it should be assumed that there is no validation
	 * error.
	 */
	@Test
	public void getValidationForegroundColor_emptyVDiagnostic() {
		// Spy to be able to verify method calls
		final SWTValidationUiServiceImpl spiedFixture = spy(fixture);
		final VDiagnostic vDiagnostic = VViewFactory.eINSTANCE.createDiagnostic();
		when(vElement.getDiagnostic()).thenReturn(vDiagnostic);
		final Color expected = new Color(Display.getDefault(), 1, 2, 3);
		when(validationHelper.getValidationForegroundColor(Diagnostic.OK, vElement, viewContext)).thenReturn(expected);

		final Color result = spiedFixture.getValidationForegroundColor(vElement, viewContext);

		assertSame(expected, result);
		verify(spiedFixture).getValidationForegroundColor(Diagnostic.OK_INSTANCE, vElement, viewContext);
	}

	/**
	 * If the given Diagnostic is null, it should be assumed that there is no validation error.
	 */
	@Test
	public void getValidationForegroundColor_nullDiagnostic() {
		// Spy to be able to verify method calls
		final SWTValidationUiServiceImpl spiedFixture = spy(fixture);
		final Color expected = new Color(Display.getDefault(), 1, 2, 3);
		when(validationHelper.getValidationForegroundColor(Diagnostic.OK, vElement, viewContext)).thenReturn(expected);

		final Color result = spiedFixture.getValidationForegroundColor(null, vElement, viewContext);

		assertSame(expected, result);
	}

	/** The service should cache icons by severity. */
	@Test
	public void getValidationForegroundColor_caching() {
		// Spy to be able to verify method calls
		final SWTValidationUiServiceImpl spiedFixture = spy(fixture);
		final Color expected = new Color(Display.getDefault(), 1, 2, 3);
		when(validationHelper.getValidationForegroundColor(Diagnostic.OK, vElement, viewContext)).thenReturn(expected);
		final Diagnostic ok1 = mock(Diagnostic.class);
		when(ok1.getSeverity()).thenReturn(Diagnostic.OK);
		final Diagnostic ok2 = mock(Diagnostic.class);
		when(ok2.getSeverity()).thenReturn(Diagnostic.OK);

		final Color result1 = spiedFixture.getValidationForegroundColor(ok1, vElement, viewContext);
		final Color result2 = spiedFixture.getValidationForegroundColor(ok2, vElement, viewContext);

		assertSame(expected, result1);
		assertSame(expected, result2);
		// if caching is used, the validation helper only needs to be called once
		verify(validationHelper, times(1)).getValidationForegroundColor(Diagnostic.OK, vElement, viewContext);
	}

	/**
	 * Test that getValidationBackgroundColor(VElement, ViewModelContext) correctly delegates to
	 * getValidationBackgroundColor(Diagnostic, VElement, ViewModelContext) and that the latter correctly delegates to
	 * SWTValidationHelper.
	 */
	@Test
	public void getValidationBackgroundColor() {
		// Spy to be able to verify method calls
		final SWTValidationUiServiceImpl spiedFixture = spy(fixture);
		final VDiagnostic vDiagnostic = VViewFactory.eINSTANCE.createDiagnostic();
		final Diagnostic ok = mock(Diagnostic.class);
		when(ok.getSeverity()).thenReturn(Diagnostic.OK);
		final Diagnostic error = mock(Diagnostic.class);
		when(error.getSeverity()).thenReturn(Diagnostic.ERROR);
		vDiagnostic.getDiagnostics().add(ok);
		vDiagnostic.getDiagnostics().add(error);
		when(vElement.getDiagnostic()).thenReturn(vDiagnostic);
		final Color expected = new Color(Display.getDefault(), 1, 2, 3);
		when(validationHelper.getValidationBackgroundColor(Diagnostic.ERROR, vElement, viewContext))
			.thenReturn(expected);

		final Color result = spiedFixture.getValidationBackgroundColor(vElement, viewContext);

		assertSame(expected, result);
		verify(spiedFixture).getValidationBackgroundColor(error, vElement, viewContext);
	}

	/** If the VElement doesn't have a VDiagnostic, it should be assumed that there is no validation error. */
	@Test
	public void getValidationBackgroundColor_nullVDiagnostic() {
		// Spy to be able to verify method calls
		final SWTValidationUiServiceImpl spiedFixture = spy(fixture);
		when(vElement.getDiagnostic()).thenReturn(null);
		final Color expected = new Color(Display.getDefault(), 1, 2, 3);
		when(validationHelper.getValidationBackgroundColor(Diagnostic.OK, vElement, viewContext)).thenReturn(expected);

		final Color result = spiedFixture.getValidationBackgroundColor(vElement, viewContext);

		assertSame(expected, result);
		verify(spiedFixture).getValidationBackgroundColor(Diagnostic.OK_INSTANCE, vElement, viewContext);
	}

	/**
	 * If the VElement's VDiagnostic doesn't have any Diagnostics, it should be assumed that there is no validation
	 * error.
	 */
	@Test
	public void getValidationBackgroundColor_emptyVDiagnostic() {
		// Spy to be able to verify method calls
		final SWTValidationUiServiceImpl spiedFixture = spy(fixture);
		final VDiagnostic vDiagnostic = VViewFactory.eINSTANCE.createDiagnostic();
		when(vElement.getDiagnostic()).thenReturn(vDiagnostic);
		final Color expected = new Color(Display.getDefault(), 1, 2, 3);
		when(validationHelper.getValidationBackgroundColor(Diagnostic.OK, vElement, viewContext)).thenReturn(expected);

		final Color result = spiedFixture.getValidationBackgroundColor(vElement, viewContext);

		assertSame(expected, result);
		verify(spiedFixture).getValidationBackgroundColor(Diagnostic.OK_INSTANCE, vElement, viewContext);
	}

	/**
	 * If the given Diagnostic is null, it should be assumed that there is no validation error.
	 */
	@Test
	public void getValidationBackgroundColor_nullDiagnostic() {
		// Spy to be able to verify method calls
		final SWTValidationUiServiceImpl spiedFixture = spy(fixture);
		final Color expected = new Color(Display.getDefault(), 1, 2, 3);
		when(validationHelper.getValidationBackgroundColor(Diagnostic.OK, vElement, viewContext)).thenReturn(expected);

		final Color result = spiedFixture.getValidationBackgroundColor(null, vElement, viewContext);

		assertSame(expected, result);
	}

	/** The service should cache icons by severity. */
	@Test
	public void getValidationBackgroundColor_caching() {
		// Spy to be able to verify method calls
		final SWTValidationUiServiceImpl spiedFixture = spy(fixture);
		final Color expected = new Color(Display.getDefault(), 1, 2, 3);
		when(validationHelper.getValidationBackgroundColor(Diagnostic.OK, vElement, viewContext)).thenReturn(expected);
		final Diagnostic ok1 = mock(Diagnostic.class);
		when(ok1.getSeverity()).thenReturn(Diagnostic.OK);
		final Diagnostic ok2 = mock(Diagnostic.class);
		when(ok2.getSeverity()).thenReturn(Diagnostic.OK);

		final Color result1 = spiedFixture.getValidationBackgroundColor(ok1, vElement, viewContext);
		final Color result2 = spiedFixture.getValidationBackgroundColor(ok2, vElement, viewContext);

		assertSame(expected, result1);
		assertSame(expected, result2);
		// if caching is used, the validation helper only needs to be called once
		verify(validationHelper, times(1)).getValidationBackgroundColor(Diagnostic.OK, vElement, viewContext);
	}
}
