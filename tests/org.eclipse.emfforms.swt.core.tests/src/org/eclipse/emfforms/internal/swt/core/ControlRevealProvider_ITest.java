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
package org.eclipse.emfforms.internal.swt.core;

import static java.util.Collections.singleton;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.test.common.DefaultRealm;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.ModelReferenceHelper;
import org.eclipse.emf.ecp.view.spi.model.VContainedContainer;
import org.eclipse.emf.ecp.view.spi.model.VContainer;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.vertical.model.VVerticalFactory;
import org.eclipse.emf.ecp.view.test.common.spi.EMFFormsRevealServiceFixture;
import org.eclipse.emf.ecp.view.test.common.spi.EMFFormsViewContextFixture.DomainModel;
import org.eclipse.emf.ecp.view.test.common.spi.EMFFormsViewContextFixture.ViewModel;
import org.eclipse.emf.ecp.view.test.common.spi.EMFMockingRunner;
import org.eclipse.emf.ecp.view.test.common.spi.EMock;
import org.eclipse.emf.ecp.view.test.common.swt.spi.SWTTestUtil;
import org.eclipse.emf.ecp.view.test.common.swt.spi.SWTViewTestHelper;
import org.eclipse.emfforms.bazaar.Bid;
import org.eclipse.emfforms.bazaar.Create;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.controlmapper.EMFFormsSettingToControlMapper;
import org.eclipse.emfforms.spi.core.services.reveal.EMFFormsRevealProvider;
import org.eclipse.emfforms.spi.core.services.reveal.Reveal;
import org.eclipse.emfforms.spi.core.services.reveal.RevealHelper;
import org.eclipse.emfforms.spi.core.services.reveal.RevealStep;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererFactory;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererService;
import org.eclipse.emfforms.spi.swt.core.layout.GridDescriptionFactory;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.Mock;

/**
 * Tests covering the {@link ControlRevealProvider} class.
 */
@SuppressWarnings("nls")
@RunWith(EMFMockingRunner.class)
public class ControlRevealProvider_ITest {

	@ViewModel
	private final VView view = VViewFactory.eINSTANCE.createView();
	private final VControl control = VViewFactory.eINSTANCE.createControl();

	@EMock
	@DomainModel
	private EObject rootObject;

	@EMock
	private EObject obj1;

	@EMock
	private EAttribute feature;

	private AbstractSWTRenderer<VElement> controlRenderer;

	@Mock
	private EMFFormsRendererService<VElement> rendererService;

	@Mock
	private Runnable scrollToReveal;

	@Rule
	public final TestRule realm = DefaultRealm.rule();

	@Rule
	public final EMFFormsRevealServiceFixture<ViewModelContext> fixture = EMFFormsRevealServiceFixture.create(
		ViewModelContext.class, this);

	private Shell shell;

	/**
	 * Initializes me.
	 */
	public ControlRevealProvider_ITest() {
		super();
	}

	@Test
	public void revealWithFocus() {
		final VContainer container = (VContainer) view.getChildren().get(0);

		final Runnable reveal = mock(Runnable.class);

		final EMFFormsRevealProvider compositeRevealer = new EMFFormsRevealProvider() {
			@Bid
			public Double bid(VElement view, EObject model) {
				return view == container && model == obj1 ? Double.MAX_VALUE : null;
			}

			@Create
			public RevealStep create(VElement view, EObject model, RevealHelper helper) {
				return view == container && model == obj1
					? helper.drillDown(this)
					: RevealStep.fail();
			}

			@Reveal
			RevealStep drillDown(VContainer element, EObject model) {
				return RevealStep.reveal(element, model, reveal);
			}

		};
		fixture.addRevealProvider(compositeRevealer);

		render();

		SWTTestUtil.waitForUIThread();

		fixture.reveal(obj1, feature);

		SWTTestUtil.waitForUIThread();

		verify(reveal).run();

		SWTTestUtil.waitForUIThread();

		// Check that the specific setting renderer was asked to reveal
		verify(scrollToReveal, atLeastOnce()).run();
	}

	//
	// Test framework
	//

	@Before
	public void createDomainModel() {
		when(feature.getEType()).thenReturn(EcorePackage.Literals.ESTRING);
		when(feature.getEAttributeType()).thenReturn(EcorePackage.Literals.ESTRING);
		when(obj1.eGet(feature)).thenReturn("Hello, world");
		when(obj1.eGet(argThat(is(feature)), anyBoolean())).thenReturn("Hello, world");
	}

	@Before
	public void createViewModel() {
		final VContainedContainer container = VVerticalFactory.eINSTANCE.createVerticalLayout();
		view.getChildren().add(container);
		control.setDomainModelReference(ModelReferenceHelper.createDomainModelReference(feature));
		container.getChildren().add(control);
	}

	@Before
	public void mockServices() throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		// Ensure that the context-provider service gets our Eclipse context
		when(fixture.getViewContext().getContextValue(IEclipseContext.class.getName()))
			.then(invocation -> fixture.getService(IEclipseContext.class));

		// Spy on a renderer for verification of interactions
		when(rendererService.isApplicable(argThat(is(control)), any())).thenReturn(Double.MAX_VALUE);
		when(rendererService.getRendererInstance(argThat(is(control)), any())).then(invocation -> {
			final ViewModelContext viewContext = (ViewModelContext) invocation.getArguments()[1];
			controlRenderer = new DummyRenderer(control, viewContext, viewContext.getService(ReportService.class));
			return controlRenderer;
		});
		((EMFFormsRendererFactoryImpl) fixture.getService(EMFFormsRendererFactory.class))
			.addEMFFormsRendererService(rendererService);

		// And also important is the control mapper
		final EMFFormsSettingToControlMapper mapper = mock(EMFFormsSettingToControlMapper.class);
		when(mapper.hasMapping(any(), any(VControl.class))).thenReturn(true);
		when(mapper.getControlsFor(any(UniqueSetting.class))).thenReturn(singleton(control));
		fixture.putService(EMFFormsSettingToControlMapper.class, mapper);
	}

	@Before
	public void createShell() {
		shell = new Shell();
		shell.setLayout(new GridLayout());
	}

	@After
	public void destroyShell() {
		shell.dispose();
		shell = null;
	}

	// Don't mess with other tests
	@After
	public void removeMockServices() {
		((EMFFormsRendererFactoryImpl) fixture.getService(EMFFormsRendererFactory.class))
			.removeEMFFormsRendererService(rendererService);
	}

	void render() {
		SWTViewTestHelper.render(fixture.getViewContext(), shell);
		shell.layout();
		shell.setSize(500, 300);
		shell.open();
	}

	//
	// Nested types
	//

	/**
	 * The abstract renderer API has too many final methods that cannot be mocked, and
	 * they cause NPEs in the rendering process when using mocks.
	 */
	private final class DummyRenderer extends AbstractSWTRenderer<VElement> {

		DummyRenderer(VElement vElement, ViewModelContext viewContext, ReportService reportService) {
			super(vElement, viewContext, reportService);
		}

		@Override
		public SWTGridDescription getGridDescription(SWTGridDescription gridDescription) {
			return GridDescriptionFactory.INSTANCE.createSimpleGrid(1, 1, this);
		}

		@Override
		protected Control renderControl(SWTGridCell cell, Composite parent)
			throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {

			return new Text(parent, SWT.BORDER);
		}

		@Override
		public void scrollToReveal() {
			scrollToReveal.run();
		}
	}

}
