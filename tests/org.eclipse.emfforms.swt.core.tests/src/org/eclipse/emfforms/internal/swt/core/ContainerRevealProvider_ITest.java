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

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.test.common.DefaultRealm;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.label.model.VLabelFactory;
import org.eclipse.emf.ecp.view.spi.model.VContainedContainer;
import org.eclipse.emf.ecp.view.spi.model.VContainedElement;
import org.eclipse.emf.ecp.view.spi.model.VContainer;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
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
import org.eclipse.emfforms.spi.core.services.reveal.EMFFormsRevealProvider;
import org.eclipse.emfforms.spi.core.services.reveal.RevealStep;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.hamcrest.CustomTypeSafeMatcher;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

/**
 * Tests covering the {@link ContainerRevealProvider} class.
 */
@SuppressWarnings("nls")
@RunWith(EMFMockingRunner.class)
public class ContainerRevealProvider_ITest {

	@ViewModel
	private final VView view = VViewFactory.eINSTANCE.createView();

	@EMock
	@DomainModel
	private EObject rootObject;

	@EMock
	private EObject obj1;

	@Rule
	public final TestRule realm = DefaultRealm.rule();

	@Rule
	public final EMFFormsRevealServiceFixture<ViewModelContext> fixture = EMFFormsRevealServiceFixture.create(
		ViewModelContext.class, this);

	private Shell shell;
	private ScrolledComposite scrollPane;

	/**
	 * Initializes me.
	 */
	public ContainerRevealProvider_ITest() {
		super();
	}

	@Test
	public void revealWithScroll() {
		final VContainer top = (VContainer) view.getChildren().get(0);
		final VContainer lastContainer = (VContainer) top.getChildren().get(top.getChildren().size() - 1);
		final VContainedElement label = lastContainer.getChildren().get(0);

		final Runnable reveal = mock(Runnable.class);

		final EMFFormsRevealProvider compositeRevealer = new EMFFormsRevealProvider() {
			@Bid
			public Double bid(VElement view, EObject model) {
				return view == label && model == obj1 ? Double.MAX_VALUE : null;
			}

			@Create
			public RevealStep create(VElement view, EObject model) {
				return view == label && model == obj1
					? RevealStep.reveal(view, model, reveal)
					: RevealStep.fail();
			}
		};
		fixture.addRevealProvider(compositeRevealer);

		render();

		SWTTestUtil.waitForUIThread();

		fixture.reveal(obj1);

		SWTTestUtil.waitForUIThread();

		verify(reveal).run();

		SWTTestUtil.waitForUIThread();

		// Check that the scroll pane was scrolled to reveal the label
		assertThat("Composite not scrolled", scrollPane.getVerticalBar().getSelection(), greaterThan(5));
	}

	//
	// Test framework
	//

	@Before
	public void createViewModel() {
		final VContainedContainer container = VVerticalFactory.eINSTANCE.createVerticalLayout();
		view.getChildren().add(container);

		// Create enough groups with labels in them that a scroll will certainly
		// be required to reveal the last of them
		for (int i = 0; i < 50; i++) {
			final VContainedContainer vertical = VVerticalFactory.eINSTANCE.createVerticalLayout();
			container.getChildren().add(vertical);
			final VContainedElement label = VLabelFactory.eINSTANCE.createLabel();
			vertical.getChildren().add(label);
			label.setName("Label " + (i + 1));
			label.setLabel(label.getName());
		}
	}

	@Before
	public void createShell() {
		shell = new Shell();
		shell.setLayout(new GridLayout());
		scrollPane = new ScrolledComposite(shell, SWT.V_SCROLL);
		scrollPane.setAlwaysShowScrollBars(true); // Useful for testing feedback
		scrollPane.setExpandHorizontal(true);
		scrollPane.setExpandVertical(true);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(scrollPane);
		final Composite content = new Composite(scrollPane, SWT.NONE);
		content.setLayout(new GridLayout());
		scrollPane.setContent(content);
	}

	@After
	public void destroyShell() {
		shell.dispose();
		shell = null;
	}

	void render() {
		SWTViewTestHelper.render(fixture.getViewContext(), (Composite) scrollPane.getContent());

		// Ensure that we can scroll the client area
		scrollPane.setMinSize(scrollPane.getContent().computeSize(SWT.DEFAULT, SWT.DEFAULT));
		shell.layout();
		shell.setSize(500, 300);
		shell.open();
	}

	static <N extends Number & Comparable<N>> Matcher<N> greaterThan(N lowerBound) {
		return new CustomTypeSafeMatcher<N>("> " + lowerBound) {
			@Override
			protected boolean matchesSafely(N item) {
				return item.compareTo(lowerBound) > 0;
			}
		};
	}

}
