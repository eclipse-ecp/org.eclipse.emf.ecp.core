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
package org.eclipse.emf.ecp.edit.internal.swt.util;

import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDiagnostic;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.test.common.swt.spi.SWTTestUtil;
import org.eclipse.emfforms.spi.common.validation.PreSetValidationService;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsContextListener;
import org.eclipse.emfforms.spi.core.services.view.RootDomainModelChangeListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests for the {@link PreSetValidationListeners} class.
 */
@SuppressWarnings("nls")
@RunWith(MockitoJUnitRunner.class)
public class PreSetValidationListeners_PTest {

	@Mock
	private PreSetValidationService service;

	private Shell shell;
	private SWTBot bot;

	/**
	 * Initializes me.
	 */
	public PreSetValidationListeners_PTest() {
		super();
	}

	/**
	 * Verify management of instances of the class: a single instance for any view-model context.
	 */
	@Test
	public void singleInstantiation() {
		final ViewModelContext ctx = mockContext();
		final PreSetValidationListeners l1 = PreSetValidationListeners.create(ctx);

		final PreSetValidationListeners l2 = PreSetValidationListeners.create(ctx);
		assertThat(l2, sameInstance(l1));

		final PreSetValidationListeners l3 = PreSetValidationListeners.create(ctx);
		assertThat(l3, sameInstance(l1));
	}

	/**
	 * Verify management of instances of the class: a different instance for different view-model contexts.
	 */
	@Test
	public void multipleInstantiation() {
		final ViewModelContext ctx1 = mockContext();
		final PreSetValidationListeners l1 = PreSetValidationListeners.create(ctx1);

		final ViewModelContext ctx2 = mockContext();
		final PreSetValidationListeners l2 = PreSetValidationListeners.create(ctx2);
		assertThat(l2, not(sameInstance(l1)));

		final ViewModelContext ctx3 = mockContext();
		final PreSetValidationListeners l3 = PreSetValidationListeners.create(ctx3);
		assertThat(l3, both(not(sameInstance(l1))).and(not(sameInstance(l2))));
	}

	/**
	 * Verify the support for verify listener on a text field.
	 */
	@Test
	public void textVerification() {
		final Text text = new Text(shell, SWT.BORDER);

		final VControl control = VViewFactory.eINSTANCE.createControl();
		final EStructuralFeature feature = EcorePackage.Literals.ENAMED_ELEMENT__NAME;
		PreSetValidationListeners.create(mockContext()).verify(text, feature, control);
		SWTTestUtil.waitForUIThread();

		bot.text().setText("foo");

		verify(service).validate(feature, "foo");
		assertError(control);
	}

	/**
	 * Verify the support for verify listener on a combo field.
	 */
	@Test
	public void comboVerification() {
		final Combo combo = new Combo(shell, SWT.DROP_DOWN);

		final VControl control = VViewFactory.eINSTANCE.createControl();
		final EStructuralFeature feature = EcorePackage.Literals.ENAMED_ELEMENT__NAME;
		PreSetValidationListeners.create(mockContext()).verify(combo, feature, control);
		SWTTestUtil.waitForUIThread();

		bot.comboBox().setText("foo");

		verify(service).validate(feature, "foo");
		assertError(control);
	}

	/**
	 * Verify the support for verify listener on a text field that is not a rendering of a view
	 * model element.
	 */
	@Test
	public void textVerification_noVElement() {
		final Text text = new Text(shell, SWT.BORDER);

		final EStructuralFeature feature = EcorePackage.Literals.ENAMED_ELEMENT__NAME;
		PreSetValidationListeners.create(mockContext()).verify(text, feature);
		SWTTestUtil.waitForUIThread();

		bot.text().setText("foo");

		verify(service).validateLoose(feature, "foo");
	}

	/**
	 * Verify the support for verify listener on a combo field that is not a rendering of a view
	 * model element.
	 */
	@Test
	public void comboVerification_noVElement() {
		final Combo combo = new Combo(shell, SWT.DROP_DOWN);

		final EStructuralFeature feature = EcorePackage.Literals.ENAMED_ELEMENT__NAME;
		PreSetValidationListeners.create(mockContext()).verify(combo, feature);
		SWTTestUtil.waitForUIThread();

		bot.comboBox().setText("foo");

		verify(service).validateLoose(feature, "foo");
	}

	/**
	 * Verify that the pre-set validation does not happen during root domain model change
	 * in the view model context.
	 */
	@Test
	public void domainModelChange() {
		final Text text = new Text(shell, SWT.BORDER);

		final VControl control = VViewFactory.eINSTANCE.createControl();
		final EStructuralFeature feature = EcorePackage.Literals.ENAMED_ELEMENT__NAME;
		final ViewModelContext ctx = mockContext();
		PreSetValidationListeners.create(ctx).verify(text, feature, control);
		SWTTestUtil.waitForUIThread();

		// We should pause listening while the domain model is changed

		ctx.changeDomainModel(EcoreFactory.eINSTANCE.createEObject());
		bot.text().setText("loaded from model");
		SWTTestUtil.waitForUIThread();

		verify(service, never()).validate(any(), any());

		// After domain model change is processed, we resume listening

		bot.text().setText("foo");
		SWTTestUtil.waitForUIThread();

		verify(service).validate(feature, "foo");
		assertError(control);
	}

	//
	// Test framework
	//

	@Before
	public void createShell() {
		shell = new Shell();
		shell.open();
		bot = new SWTBot(shell);
	}

	@Before
	public void mockValidation() {
		when(service.validateLoose(any(), any())).thenReturn(Diagnostic.OK_INSTANCE);
		when(service.validate(any(), any()))
			.then(invocation -> new BasicDiagnostic(Diagnostic.ERROR, "test", 0, "Test error", null));
	}

	@After
	public void destroyShell() {
		shell.dispose();
		shell = null;
	}

	ViewModelContext mockContext() {
		final ViewModelContext result = mock(ViewModelContext.class);

		// Mock management of context values
		final Map<String, Object> values = new HashMap<>();
		when(result.getContextValue(anyString())).then(invocation -> values.get(invocation.getArguments()[0]));
		doAnswer(invocation -> values.put((String) invocation.getArguments()[0], invocation.getArguments()[1]))
			.when(result).putContextValue(anyString(), any());

		// Mock access to certain critical services
		when(result.getService(PreSetValidationService.class)).thenReturn(service);
		when(result.hasService(any())).thenAnswer(invocation -> ((ViewModelContext) invocation.getMock())
			.getService((Class<?>) invocation.getArguments()[0]) != null);

		// Mock support for root domain model change
		final List<EMFFormsContextListener> listeners = new ArrayList<>();
		final List<RootDomainModelChangeListener> domainModelListeners = new ArrayList<>();
		doAnswer(invocation -> {
			listeners.forEach(EMFFormsContextListener::contextDispose);
			domainModelListeners.forEach(RootDomainModelChangeListener::notifyChange);
			listeners.forEach(EMFFormsContextListener::contextInitialised);
			return null;
		}).when(result).changeDomainModel(any());
		doAnswer(invocation -> {
			listeners.add((EMFFormsContextListener) invocation.getArguments()[0]);
			return null;
		}).when(result).registerEMFFormsContextListener(any());
		doAnswer(invocation -> {
			listeners.remove(invocation.getArguments()[0]);
			return null;
		}).when(result).unregisterEMFFormsContextListener(any());
		doAnswer(invocation -> {
			domainModelListeners.add((RootDomainModelChangeListener) invocation.getArguments()[0]);
			return null;
		}).when(result).registerRootDomainModelChangeListener(any());
		doAnswer(invocation -> {
			domainModelListeners.remove(invocation.getArguments()[0]);
			return null;
		}).when(result).unregisterRootDomainModelChangeListener(any());

		return result;
	}

	void assertError(VElement viewModel) {
		final VDiagnostic vDiagnostic = viewModel.getDiagnostic();
		assertThat("No diagnostic attached to view model", vDiagnostic, notNullValue());
		assertThat("No diagnostics", vDiagnostic.getDiagnostics().isEmpty(), is(false));

		final Object first = vDiagnostic.getDiagnostics().get(0);
		assertThat("Not a diagnostic", first, instanceOf(Diagnostic.class));
		assertThat("Not an error", ((Diagnostic) vDiagnostic.getDiagnostics().get(0)).getSeverity(),
			is(Diagnostic.ERROR));
	}

}
