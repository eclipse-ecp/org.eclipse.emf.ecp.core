/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.core;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextFactory;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.test.common.swt.spi.SWTViewTestHelper;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link AbstractSWTRenderer}.
 *
 * @author Lucas Koehler
 *
 */
public class AbstractSWTRenderer_PTest {

	private AbstractSWTRenderer<VControl> renderer;
	private VView view;
	private VControl control;
	private Shell shell;

	@Before
	public void setUp() throws Exception {
		view = VViewFactory.eINSTANCE.createView();
		control = VViewFactory.eINSTANCE.createControl();
		view.getChildren().add(control);
		final EAttribute domainObject = EcoreFactory.eINSTANCE.createEAttribute();
		final ViewModelContext context = ViewModelContextFactory.INSTANCE.createViewModelContext(view,
			domainObject);

		renderer = spy(new TestAbstractSWTRenderer(control, context, mock(ReportService.class)));
		shell = SWTViewTestHelper.createShell();
	}

	@After
	public void tearDown() {
		if (shell != null) {
			shell.dispose();
		}
	}

	@Test
	public void viewChangeListener_applyReadOnlyCalled_sameElement() {
		renderer.init();
		renderer.finalizeRendering(shell);

		control.setReadonly(true);

		// Once during finalizeRendering and once by the view change listener
		verify(renderer, times(2)).applyReadOnly();

		control.setReadonly(false);

		verify(renderer, times(3)).applyReadOnly();
	}

	@Test
	public void viewChangeListener_applyReadOnlyCalled_parentElement() {
		renderer.init();
		renderer.finalizeRendering(shell);

		view.setReadonly(true);

		// Once during finalizeRendering and once by the view change listener
		verify(renderer, times(2)).applyReadOnly();

		view.setReadonly(false);

		verify(renderer, times(3)).applyReadOnly();
	}

	@Test
	public void viewChangeListener_applyEnableCalled_sameElement() {
		renderer.init();
		renderer.finalizeRendering(shell);

		control.setEnabled(false);

		// Once during finalizeRendering and once by the view change listener
		verify(renderer, times(2)).applyEnable();

		control.setEnabled(true);

		verify(renderer, times(3)).applyEnable();
	}

	@Test
	public void viewChangeListener_applyEnableCalled_parentElement() {
		renderer.init();
		renderer.finalizeRendering(shell);

		view.setEnabled(false);

		// Once during finalizeRendering and once by the view change listener
		verify(renderer, times(2)).applyEnable();

		view.setEnabled(true);

		verify(renderer, times(3)).applyEnable();
	}

	// Unfortunately, this cannot be replaced by a mock because AbstractRenderer.getViewModelContext and .getVElement
	// are final and cannot be mocked to return needed values. Also this needs to be public for Mockito to be able to
	// spy it in a plugin test
	public static class TestAbstractSWTRenderer extends AbstractSWTRenderer<VControl> {

		TestAbstractSWTRenderer(VControl vElement, ViewModelContext viewContext, ReportService reportService) {
			super(vElement, viewContext, reportService);
		}

		@Override
		public SWTGridDescription getGridDescription(SWTGridDescription gridDescription) {
			return null;
		}

		@Override
		protected Control renderControl(SWTGridCell cell, Composite parent)
			throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
			return null;
		}

	}
}
