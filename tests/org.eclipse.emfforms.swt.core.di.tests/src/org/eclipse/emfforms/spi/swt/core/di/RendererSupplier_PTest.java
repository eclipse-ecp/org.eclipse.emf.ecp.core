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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.function.Supplier;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.InjectionException;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.emf.common.notify.impl.BasicNotifierImpl.EAdapterList;
import org.eclipse.emf.ecp.view.model.common.di.annotations.Renderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emfforms.internal.swt.core.di.RendererSupplier;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * Test cases for the {@link RendererSupplier}.
 */
@SuppressWarnings({ "restriction", "nls" })
public class RendererSupplier_PTest {

	private IEclipseContext e4Context;

	/**
	 * Initializes me.
	 */
	public RendererSupplier_PTest() {
		super();
	}

	@Test
	public void injection() {
		final ViewModelContext viewContext = mock(ViewModelContext.class);
		final ReportService reportService = mock(ReportService.class);
		when(viewContext.getService(ReportService.class)).thenReturn(reportService);
		final VControl control = mock(VControl.class);
		when(control.eAdapters()).thenReturn(new EAdapterList<>(control));

		final AbstractSWTRenderer<VControl> renderer = new MyRenderer(control, viewContext);

		final InjectMe injectMe = new InjectMe();

		try {
			ContextInjectionFactory.invoke(injectMe, Execute.class, e4Context);
			fail("Should have failed to inject");
		} catch (final InjectionException e) {
			// Success
		}

		// Now put the prerequisites into the context
		e4Context.set(VElement.class, control);
		// Note that this one requires the context function that casts to ViewModelContext type
		e4Context.set(EMFFormsViewContext.class, viewContext);

		try {
			final String returnResult = (String) ContextInjectionFactory.invoke(injectMe, Execute.class, e4Context);
			assertThat(returnResult, is("success"));
			assertThat(injectMe.get(), is(renderer));
		} catch (final InjectionException e) {
			e.printStackTrace();
			fail("Failed to inject: " + e.getMessage());
		}
	}

	//
	// Test framework
	//

	@Before
	public void createContext() {
		final Bundle self = FrameworkUtil.getBundle(RendererSupplier_PTest.class);
		e4Context = EclipseContextFactory.createServiceContext(self.getBundleContext());
	}

	@After
	public void destroyContext() {
		e4Context.dispose();
	}

	//
	// Nested types
	//

	static class InjectMe implements Supplier<Object> {
		private Object injected;

		@Execute
		public String call(@Renderer MyRenderer renderer) {
			injected = renderer;
			return "success";
		}

		@Override
		public Object get() {
			return injected;
		}
	}

	static class MyRenderer extends AbstractSWTRenderer<VControl> {

		MyRenderer(VControl control, ViewModelContext viewContext) {
			super(control, viewContext, viewContext.getService(ReportService.class));
		}

		@Override
		protected Control renderControl(SWTGridCell cell, Composite parent)
			throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
			return null;
		}

		@Override
		public SWTGridDescription getGridDescription(SWTGridDescription gridDescription) {
			return null;
		}

	}

}
