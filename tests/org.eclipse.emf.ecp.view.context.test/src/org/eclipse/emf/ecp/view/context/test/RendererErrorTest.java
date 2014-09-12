/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.context.test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.view.context.test.mockup.MockViewSWTRenderer;
import org.eclipse.emf.ecp.view.internal.context.Activator;
import org.eclipse.emf.ecp.view.internal.core.swt.renderer.ViewSWTRenderer;
import org.eclipse.emf.ecp.view.internal.provider.ViewProviderImpl;
import org.eclipse.emf.ecp.view.model.common.spi.reporting.ReportService;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextFactory;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.provider.IViewProvider;
import org.eclipse.emf.ecp.view.spi.provider.NoViewProviderError;
import org.eclipse.emf.ecp.view.spi.provider.ViewModelIsNullError;
import org.eclipse.emf.ecp.view.spi.provider.ViewProviderHelper;
import org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer;
import org.eclipse.emf.ecp.view.spi.swt.reporting.AmbiguousRendererPriorityError;
import org.eclipse.emf.ecp.view.spi.swt.reporting.InvalidGridDescriptionError;
import org.eclipse.emf.ecp.view.spi.swt.reporting.NoRendererFoundError;
import org.eclipse.emf.ecp.view.spi.swt.reporting.NoRenderingPossibleError;
import org.eclipse.emf.ecp.view.spi.swt.reporting.RendererInitFailedError;
import org.eclipse.emf.ecp.view.test.common.swt.DatabindingClassRunner;
import org.eclipse.emf.ecp.view.test.common.swt.SWTViewTestHelper;
import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.League;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Unit test for renderer error aggregation service.
 * 
 * @author emueller
 */
@RunWith(DatabindingClassRunner.class)
public class RendererErrorTest {

	private Shell shell;
	private League league;
	private VView view;
	private ViewModelContext viewContext;
	private ReportService reportService;
	private TestSWTRendererFactory swtViewTestHelper;

	@Before
	public void before() {
		shell = SWTViewTestHelper.createShell();
		swtViewTestHelper = new TestSWTRendererFactory();
		league = BowlingFactory.eINSTANCE.createLeague();
		view = ViewProviderHelper.getView(league, null);
		viewContext = ViewModelContextFactory.INSTANCE.createViewModelContext(view, league);
		reportService = Activator.getInstance().getReportService();
	}

	@After
	public void after() {
		reportService.clearReports();
	}

	@Ignore
	@Test
	public void noErrors() throws ECPRendererException {
		swtViewTestHelper.render(shell, viewContext);
		assertEquals(0, reportService.getReports().size());
	}

	@Test
	public void missingRenderer() throws ECPRendererException {
		swtViewTestHelper.clearRenderers();
		swtViewTestHelper.render(shell, viewContext);
		assertThat(reportService.getReports(), hasSize(1));
		assertThat(reportService.getReports().get(0), instanceOf(NoRendererFoundError.class));
	}

	@Test
	public void rendererInit() throws ECPRendererException {
		final ViewSWTRenderer failingInitRenderer = new ViewSWTRenderer() {
			@Override
			protected void postInit() {
				throw new RuntimeException();
			}
		};

		swtViewTestHelper.registerRenderer(3, cast(failingInitRenderer.getClass()), VView.class);

		swtViewTestHelper.render(shell, viewContext);
		assertThat(reportService.getReports(), hasSize(1));
		assertThat(reportService.getReports().get(0), instanceOf(RendererInitFailedError.class));
	}

	@Test
	public void samePriorityRenderers() throws ECPRendererException {
		// modifiableSWTViewTestHelper.clearRenderers();

		final ViewSWTRenderer viewRenderer = new ViewSWTRenderer();

		swtViewTestHelper.registerRenderer(1, cast(viewRenderer.getClass()), VView.class);

		swtViewTestHelper.render(shell, viewContext);
		assertThat(reportService.getReports(), hasSize(1));
		assertThat(reportService.getReports().get(0), instanceOf(AmbiguousRendererPriorityError.class));
	}

	@Test
	public void invalidGridDescription() throws ECPRendererException {
		swtViewTestHelper.replaceViewRenderer(1, cast(
			MockViewSWTRenderer.withInvalidGridDescription().getClass()), VView.class);
		swtViewTestHelper.render(shell, viewContext);
		assertThat(reportService.getReports(), hasSize(1));
		assertThat(reportService.getReports().get(0), instanceOf(InvalidGridDescriptionError.class));
	}

	@Test
	public void noRendererFound() throws ECPRendererException {
		swtViewTestHelper.replaceViewRenderer(1, cast(
			MockViewSWTRenderer.withoutPropertyDescriptor().getClass()), VView.class);

		swtViewTestHelper.render(shell, viewContext);
		assertThat(reportService.getReports(), hasSize(1));
		assertThat(reportService.getReports().get(0), instanceOf(NoRenderingPossibleError.class));
	}

	@Test
	public void noPropertyDescriptorFound() throws ECPRendererException {
		swtViewTestHelper.replaceViewRenderer(1, cast(
			MockViewSWTRenderer.withoutPropertyDescriptor().getClass()), VView.class);

		swtViewTestHelper.render(shell, viewContext);
		assertThat(reportService.getReports(), hasSize(1));
		assertThat(reportService.getReports().get(0), instanceOf(NoRenderingPossibleError.class));
	}

	@Test
	public void viewProviderReturnsNullView() {
		final ViewProviderImpl viewProvider = new ViewProviderImpl(false);
		viewProvider.clearProviders();
		viewProvider.addProvider(new IViewProvider() {
			@Override
			public VView generate(EObject eObject, Map<String, Object> context) {
				return null;
			}

			@Override
			public int canRender(EObject eObject, Map<String, Object> context) {
				return 0;
			}
		});
		viewProvider.getView(league, null);
		assertThat(reportService.getReports(), hasSize(1));
		assertThat(reportService.getReports().get(0), instanceOf(ViewModelIsNullError.class));
	}

	@Test
	public void noViewProvider() {
		final ViewProviderImpl viewProvider = new ViewProviderImpl(false);

		viewProvider.clearProviders();
		viewProvider.getView(league, null);
		assertThat(reportService.getReports(), hasSize(1));
		assertThat(reportService.getReports().get(0), instanceOf(NoViewProviderError.class));
	}

	@Test
	public void noViewProviderInitFailed() {
		final ViewProviderImpl viewProvider = new ViewProviderImpl(false);

		viewProvider.clearProviders();
		viewProvider.getView(league, null);
		assertThat(reportService.getReports(), hasSize(1));
		assertThat(reportService.getReports().get(0), instanceOf(NoViewProviderError.class));
	}

	// @Test
	// public void initCustomControlFailed() {
	//
	// }

	@SuppressWarnings({ "unchecked" })
	private Class<AbstractSWTRenderer<VElement>> cast(Class<?> clazz) {
		return (Class<AbstractSWTRenderer<VElement>>) clazz;
	}
}
