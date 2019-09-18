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
package org.eclipse.emf.ecp.view.internal.context;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * Integration tests for the context function that casts the
 * {@link EMFFormsViewContext} value as {@link ViewModelContext} when
 * it is such.
 */
public class ViewModelContextContextFunction_ITest {

	private IEclipseContext e4Context;

	/**
	 * Initializes me.
	 */
	public ViewModelContextContextFunction_ITest() {
		super();
	}

	@Test
	public void successCase() {
		final EMFFormsViewContext viewContext = mock(ViewModelContext.class);
		e4Context.set(EMFFormsViewContext.class, viewContext);

		assertThat(e4Context.get(ViewModelContext.class), sameInstance(viewContext));
	}

	@Test
	public void failureCase() {
		final EMFFormsViewContext viewContext = mock(EMFFormsViewContext.class);
		e4Context.set(EMFFormsViewContext.class, viewContext);

		assertThat(e4Context.get(EMFFormsViewContext.class), sameInstance(viewContext));
		assertThat(e4Context.get(ViewModelContext.class), nullValue());
	}

	//
	// Test framework
	//

	@Before
	public void createContext() {
		final Bundle self = FrameworkUtil.getBundle(ViewModelContextContextFunction_ITest.class);
		e4Context = EclipseContextFactory.createServiceContext(self.getBundleContext());
	}

	@After
	public void destroyContext() {
		e4Context.dispose();
	}

}
