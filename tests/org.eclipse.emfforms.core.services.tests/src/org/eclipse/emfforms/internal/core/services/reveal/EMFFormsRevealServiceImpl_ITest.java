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
package org.eclipse.emfforms.internal.core.services.reveal;

import static org.eclipse.emf.ecp.view.test.common.spi.EMFFormsRevealServiceFixture.isA;
import static org.eclipse.emf.ecp.view.test.common.spi.EMFFormsRevealServiceFixture.isListOf;
import static org.eclipse.emf.ecp.view.test.common.spi.EMFFormsRevealServiceFixture.pass;
import static org.eclipse.emf.ecp.view.test.common.spi.EMFMocking.eMock;
import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.test.common.spi.EMFFormsRevealServiceFixture;
import org.eclipse.emf.ecp.view.test.common.spi.EMFFormsRevealServiceFixture.TestProvider;
import org.eclipse.emf.ecp.view.test.common.spi.EMFFormsViewContextFixture.DomainModel;
import org.eclipse.emf.ecp.view.test.common.spi.EMFFormsViewContextFixture.ViewModel;
import org.eclipse.emf.ecp.view.test.common.spi.EMFMockingRunner;
import org.eclipse.emf.ecp.view.test.common.spi.EMock;
import org.eclipse.emfforms.bazaar.Bid;
import org.eclipse.emfforms.bazaar.Create;
import org.eclipse.emfforms.spi.core.services.reveal.DrillDown;
import org.eclipse.emfforms.spi.core.services.reveal.DrillUp;
import org.eclipse.emfforms.spi.core.services.reveal.EMFFormsRevealProvider;
import org.eclipse.emfforms.spi.core.services.reveal.Reveal;
import org.eclipse.emfforms.spi.core.services.reveal.RevealHelper;
import org.eclipse.emfforms.spi.core.services.reveal.RevealStep;
import org.eclipse.emfforms.spi.core.services.reveal.RevealStepKind;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;

/**
 * Integration tests covering the {@link EMFFormsRevealServiceImpl} class.
 */
@SuppressWarnings("nls")
@RunWith(EMFMockingRunner.class)
public class EMFFormsRevealServiceImpl_ITest {

	@EMock
	@ViewModel
	private VControl control;

	@EMock
	@DomainModel
	private EObject domainModelRoot;

	@EMock
	private EObject object;

	@Rule
	public final EMFFormsRevealServiceFixture<EMFFormsViewContext> fixture = EMFFormsRevealServiceFixture.create(this);

	/**
	 * Initializes me.
	 */
	public EMFFormsRevealServiceImpl_ITest() {
		super();
	}

	@Test
	public void reveal_noProviders() {
		assertThat("Should have failed to reveal", fixture.reveal(object), is(false));
		assertThat("Shoudl be a failure step", fixture.reveal(object, control), isA(RevealStepKind.FAILED));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void provider_injection() {
		final List<Object> injectedIntoBid = new ArrayList<>(2);
		final List<Object> injectedIntoCreate = new ArrayList<>(3);

		final EMFFormsRevealProvider provider = new EMFFormsRevealProvider() {
			@Bid
			double bid(VElement viewModel, EObject domainModel) {
				injectedIntoBid.add(viewModel);
				injectedIntoBid.add(domainModel);
				return 1.0;
			}

			@Create
			RevealStep create(VElement viewModel, EObject domainModel, RevealHelper helper) {
				injectedIntoCreate.add(viewModel);
				injectedIntoCreate.add(domainModel);
				injectedIntoCreate.add(helper);
				return RevealStep.reveal(viewModel, domainModel, pass());
			}
		};

		fixture.addRevealProvider(provider);
		fixture.reveal(object);

		assertThat(injectedIntoBid, isListOf(control, object));
		assertThat(injectedIntoCreate,
			hasItems(sameInstance(control), sameInstance(object), instanceOf(RevealHelperImpl.class)));
	}

	@Test
	public void terminalStep() {
		final Runnable reveal = mock(Runnable.class, "reveal");

		final EMFFormsRevealProvider provider = new TestProvider() {
			@Create
			RevealStep create(VElement viewModel, EObject domainModel) {
				return RevealStep.reveal(viewModel, domainModel, reveal);
			}
		};

		fixture.addRevealProvider(provider);

		final boolean revealed = fixture.reveal(object);

		assertThat("Not revealed", revealed, is(true));
		verify(reveal).run();
	}

	@Test
	public void helper_defer() {
		final Runnable reveal = mock(Runnable.class, "reveal");

		final Object revealer = new Object() {
			@Reveal
			public RevealStep reveal(VElement viewModel, EObject domainModel) {
				return RevealStep.reveal(viewModel, domainModel, reveal);
			}
		};

		final EMFFormsRevealProvider provider = new TestProvider() {
			@Create
			RevealStep create(VElement viewModel, EObject domainModel, RevealHelper helper) {
				return helper.defer(revealer);
			}
		};

		fixture.addRevealProvider(provider);

		final RevealStep deferredStep = fixture.reveal(object, control);

		// It's deferred; that's the point
		verify(reveal, never()).run();

		assertThat(deferredStep, isA(RevealStepKind.TERMINAL));
		assertThat(deferredStep.getViewModel(), is(control));
		assertThat(deferredStep.getDomainModel(), is(object));

		deferredStep.reveal();

		verify(reveal).run();
	}

	@Test
	public void helper_drillDown_internal() {
		final Runnable outerReveal = mock(Runnable.class, "outerReveal");
		final Runnable innerReveal = mock(Runnable.class, "innerReveal");

		configureDrillDown(outerReveal, innerReveal);
		final RevealStep deferredStep = fixture.reveal(object, control);

		// They're deferred; that's the point
		verify(outerReveal, never()).run();
		verify(innerReveal, never()).run();

		assertThat(deferredStep, isA(RevealStepKind.INTERMEDIATE));
		assertThat(deferredStep.getViewModel(), is(control));
		assertThat(deferredStep.getDomainModel(), is(domainModelRoot));
		final RevealStep next = deferredStep.drillDown();

		assertThat(next, isA(RevealStepKind.TERMINAL));
		assertThat("Should have revealed in the child control", next.getViewModel(),
			both(not((VElement) control)).and(notNullValue()));
		next.reveal();

		final InOrder inOrder = inOrder(outerReveal, innerReveal);
		inOrder.verify(outerReveal).run();
		inOrder.verify(innerReveal).run();

		assertThat("Another computation performed", deferredStep.drillDown(), sameInstance(next));
	}

	private void configureDrillDown(Runnable outerReveal, Runnable innerReveal) {
		final List<VControl> children = Collections.singletonList(eMock(VControl.class, "childControl"));

		final Object computation = new Object() {
			@DrillDown
			public List<VControl> drillDown(EObject parent) {
				return children;
			}

			@Reveal
			public RevealStep reveal(VElement viewModel, EObject domainModel, RevealStep nextStep) {
				return RevealStep.reveal(nextStep.getViewModel(), domainModel, outerReveal);
			}
		};

		final EMFFormsRevealProvider provider = new TestProvider() {
			@Create
			RevealStep create(VElement viewModel, EObject domainModel, RevealHelper helper) {
				return viewModel == control
					? helper.drillDown(computation, computation)
					: RevealStep.reveal(viewModel, domainModel, innerReveal);
			}
		};

		fixture.addRevealProvider(provider);
	}

	@Test
	public void helper_drillDown_external() {
		final Runnable outerReveal = mock(Runnable.class, "outerReveal");
		final Runnable innerReveal = mock(Runnable.class, "innerReveal");

		configureDrillDown(outerReveal, innerReveal);
		fixture.reveal(object);

		final InOrder inOrder = inOrder(outerReveal, innerReveal);
		inOrder.verify(outerReveal).run();
		inOrder.verify(innerReveal).run();
	}

	@Test
	public void helper_masterDetail_internal() {
		final Runnable masterReveal = mock(Runnable.class, "masterReveal");
		final Runnable detailReveal = mock(Runnable.class, "detailReveal");
		final EObject detail = eMock(EObject.class, "detail");

		configureMasterDetail(detail, masterReveal, detailReveal);
		final RevealStep deferredStep = fixture.reveal(detail, control);

		// They're deferred; that's the point
		verify(masterReveal, never()).run();
		verify(detailReveal, never()).run();

		assertThat(deferredStep, isA(RevealStepKind.INTERMEDIATE));
		assertThat(deferredStep.getViewModel(), is(control));
		// assertThat(deferredStep.getDomainModel(), is(object));
		final RevealStep next = deferredStep.drillDown();

		assertThat(next, isA(RevealStepKind.TERMINAL));
		assertThat("Should have revealed in the detail control", next.getViewModel(),
			both(not((VElement) control)).and(notNullValue()));
		assertThat(deferredStep.getDomainModel(), is(detail));
		next.reveal();

		final InOrder inOrder = inOrder(masterReveal, detailReveal);
		inOrder.verify(masterReveal).run();
		inOrder.verify(detailReveal).run();

		assertThat("Another computation performed", deferredStep.drillDown(), sameInstance(next));
	}

	private void configureMasterDetail(EObject detail, Runnable masterReveal, Runnable detailReveal) {
		final VControl detailView = eMock(VControl.class, "detailView");

		final Object computation = new Object() {
			@DrillUp
			public EObject getMaster(EObject eObject) {
				return eObject == detail ? object : eObject;
			}

			@Reveal
			public RevealStep reveal(VElement viewModel, EObject domainModel) {
				return RevealStep.reveal(viewModel, domainModel, masterReveal);
			}
		};

		final EMFFormsRevealProvider provider = new TestProvider() {
			@Create
			RevealStep create(VElement viewModel, EObject domainModel, RevealHelper helper) {
				return viewModel == control
					? helper.masterDetail(computation, computation)
					: RevealStep.reveal(viewModel, domainModel, detailReveal);
			}
		};

		// Supply the child context for the detail view of the master selection,
		// which is the 'object', the bespoke parent of the 'detail' object that
		// we are revealing
		fixture.createChildContext(control, "childContext", detailView, object);

		fixture.addRevealProvider(provider);
	}

	@Test
	public void helper_masterDetail_external() {
		final Runnable masterReveal = mock(Runnable.class, "masterReveal");
		final Runnable detailReveal = mock(Runnable.class, "detailReveal");
		final EObject detail = eMock(EObject.class, "detail");

		configureMasterDetail(detail, masterReveal, detailReveal);
		fixture.reveal(detail);

		final InOrder inOrder = inOrder(masterReveal, detailReveal);
		inOrder.verify(masterReveal).run();
		inOrder.verify(detailReveal).run();
	}

}
