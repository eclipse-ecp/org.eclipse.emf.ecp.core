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
package org.eclipse.emfforms.spi.swt.core.di;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextFactory;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the {@code EMFFormsContextViewServiceFactory} class.
 */
@SuppressWarnings("nls")
public class EMFFormsContextViewServiceFactory_PTest {

	private final VElement viewModel = VViewFactory.eINSTANCE.createView();

	private final EObject domainModel = EcoreFactory.eINSTANCE.createEObject();

	private ViewModelContext viewModelContext;

	/**
	 * Initializes me.
	 */
	public EMFFormsContextViewServiceFactory_PTest() {
		super();
	}

	@Test
	public void test_default() {
		final IEclipseContext e4Context = viewModelContext.getService(IEclipseContext.class);
		assertThat("Should have got an Eclipse context", e4Context, notNullValue());
	}

	@Test
	public void test_success() {
		final IEclipseContext expected = EclipseContextFactory.create();

		// Bootstrap the Eclipse context
		viewModelContext.putContextValue(IEclipseContext.class.getName(), expected);

		final IEclipseContext e4Context = viewModelContext.getService(IEclipseContext.class);
		assertThat("Should not have got the exact Eclipse context", e4Context, not(sameInstance(expected)));
		assertThat("Should have got a child of the Eclipse context", e4Context.getParent(), sameInstance(expected));
	}

	//
	// Test framework
	//

	@Before
	public void createContext() {
		viewModelContext = ViewModelContextFactory.INSTANCE.createViewModelContext(viewModel, domainModel);
	}

	@After
	public void destroyContext() {
		viewModelContext.dispose();
		viewModelContext = null;
	}
}
