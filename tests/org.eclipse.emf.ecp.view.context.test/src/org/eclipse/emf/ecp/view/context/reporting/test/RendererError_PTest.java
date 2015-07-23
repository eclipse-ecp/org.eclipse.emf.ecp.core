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
package org.eclipse.emf.ecp.view.context.reporting.test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.view.context.reporting.test.TestSWTRendererFactory.TestEMFFormsRendererService;
import org.eclipse.emf.ecp.view.context.test.mockup.MockViewSWTRenderer;
import org.eclipse.emf.ecp.view.internal.core.swt.renderer.ViewSWTRenderer;
import org.eclipse.emf.ecp.view.internal.provider.ViewProviderImpl;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextFactory;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;
import org.eclipse.emf.ecp.view.spi.model.impl.Activator;
import org.eclipse.emf.ecp.view.spi.provider.IViewProvider;
import org.eclipse.emf.ecp.view.spi.provider.ViewProviderHelper;
import org.eclipse.emf.ecp.view.spi.provider.reporting.NoViewProviderFoundReport;
import org.eclipse.emf.ecp.view.spi.provider.reporting.ViewModelIsNullReport;
import org.eclipse.emf.ecp.view.spi.swt.reporting.AmbiguousRendererPriorityReport;
import org.eclipse.emf.ecp.view.spi.swt.reporting.InvalidGridDescriptionReport;
import org.eclipse.emf.ecp.view.spi.swt.reporting.NoRendererFoundReport;
import org.eclipse.emf.ecp.view.spi.swt.reporting.RendererInitFailedReport;
import org.eclipse.emf.ecp.view.spi.swt.reporting.RenderingFailedReport;
import org.eclipse.emf.ecp.view.test.common.swt.spi.DatabindingClassRunner;
import org.eclipse.emf.ecp.view.test.common.swt.spi.SWTViewTestHelper;
import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.League;
import org.eclipse.emfforms.spi.common.report.ReportService;
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
public class RendererError_PTest {

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
		reportService = Activator.getDefault().getReportService();
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
	@Ignore
	// this test should not be here but on the emfformsrendererFactory
	public void missingRenderer() throws ECPRendererException {
		swtViewTestHelper.render(shell, viewContext);
		assertThat(reportService.getReports(), hasSize(1));
		assertThat(reportService.getReports().get(0), instanceOf(NoRendererFoundReport.class));
	}

	@Test
	@Ignore
	public void rendererInit() throws ECPRendererException {

		final TestEMFFormsRendererService registerRenderer = swtViewTestHelper.registerRenderer(3, new FailingRenderer(
			view, viewContext, reportService), VView.class);

		swtViewTestHelper.render(shell, viewContext);
		swtViewTestHelper.deleteRegisteredRenderer(registerRenderer);
		assertThat(reportService.getReports(), hasSize(1));
		assertThat(reportService.getReports().get(0), instanceOf(RendererInitFailedReport.class));
	}

	@Test
	@Ignore
	public void samePriorityRenderers() throws ECPRendererException {
		// modifiableSWTViewTestHelper.clearRenderers();

		final ViewSWTRenderer viewRenderer = MockViewSWTRenderer.newRenderer();

		final TestEMFFormsRendererService registerRenderer = swtViewTestHelper.registerRenderer(1, viewRenderer,
			VView.class);

		swtViewTestHelper.render(shell, viewContext);
		swtViewTestHelper.deleteRegisteredRenderer(registerRenderer);
		assertThat(reportService.getReports(), hasSize(1));
		assertThat(reportService.getReports().get(0), instanceOf(AmbiguousRendererPriorityReport.class));
	}

	@Test
	public void invalidGridDescription() throws ECPRendererException {
		final TestEMFFormsRendererService registerRenderer = swtViewTestHelper.registerRenderer(5,
			MockViewSWTRenderer.withInvalidGridDescription(), VView.class);
		swtViewTestHelper.render(shell, viewContext);
		swtViewTestHelper.deleteRegisteredRenderer(registerRenderer);
		assertThat(reportService.getReports(), hasSize(1));
		assertThat(reportService.getReports().get(0), instanceOf(InvalidGridDescriptionReport.class));
	}

	@Test
	public void noRendererFound() throws ECPRendererException {
		final TestEMFFormsRendererService registerRenderer = swtViewTestHelper.registerRenderer(5,
			MockViewSWTRenderer.withoutPropertyDescriptor(), VView.class);

		swtViewTestHelper.render(shell, viewContext);
		swtViewTestHelper.deleteRegisteredRenderer(registerRenderer);
		assertThat(reportService.getReports(), hasSize(1));
		assertThat(reportService.getReports().get(0), instanceOf(RenderingFailedReport.class));
	}

	@Test
	public void noPropertyDescriptorFound() throws ECPRendererException {
		final TestEMFFormsRendererService registerRenderer = swtViewTestHelper.registerRenderer(5,
			MockViewSWTRenderer.withoutPropertyDescriptor(), VView.class);

		swtViewTestHelper.render(shell, viewContext);
		swtViewTestHelper.deleteRegisteredRenderer(registerRenderer);
		assertThat(reportService.getReports(), hasSize(1));
		assertThat(reportService.getReports().get(0), instanceOf(RenderingFailedReport.class));
	}

	@Test
	public void viewProviderReturnsNullView() {
		final ViewProviderImpl viewProvider = new ViewProviderImpl(false);
		viewProvider.clearProviders();
		viewProvider.addProvider(new IViewProvider() {

			@Override
			public VView provideViewModel(EObject eObject, VViewModelProperties properties) {
				return null;
			}

			@Override
			public double canProvideViewModel(EObject eObject, VViewModelProperties properties) {
				return 0d;
			}
		});
		viewProvider.getView(league, null);
		assertThat(reportService.getReports(), hasSize(1));
		assertThat(reportService.getReports().get(0), instanceOf(ViewModelIsNullReport.class));
	}

	@Test
	public void noViewProvider() {
		final ViewProviderImpl viewProvider = new ViewProviderImpl(false);

		viewProvider.clearProviders();
		viewProvider.getView(league, null);
		assertThat(reportService.getReports(), hasSize(1));
		assertThat(reportService.getReports().get(0), instanceOf(NoViewProviderFoundReport.class));
	}

	@Test
	public void noViewProviderInitFailed() {
		final ViewProviderImpl viewProvider = new ViewProviderImpl(false);

		viewProvider.clearProviders();
		viewProvider.getView(league, null);
		assertThat(reportService.getReports(), hasSize(1));
		assertThat(reportService.getReports().get(0), instanceOf(NoViewProviderFoundReport.class));
	}
}
