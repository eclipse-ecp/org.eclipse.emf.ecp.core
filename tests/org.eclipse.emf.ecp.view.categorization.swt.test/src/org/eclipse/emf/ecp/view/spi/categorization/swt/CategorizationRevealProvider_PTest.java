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
package org.eclipse.emf.ecp.view.spi.categorization.swt;

import static org.eclipse.emf.ecp.common.spi.UniqueSetting.createSetting;
import static org.eclipse.emf.ecp.view.test.common.spi.EMFMocking.eMock;
import static org.eclipse.emf.ecp.view.test.common.spi.EMFMocking.withESettings;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.test.common.DefaultRealm;
import org.eclipse.emf.ecp.view.internal.categorization.swt.CategorizationRevealProvider;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorization;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationElement;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationFactory;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategory;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VContainedContainer;
import org.eclipse.emf.ecp.view.spi.model.VContainer;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.vertical.model.VVerticalFactory;
import org.eclipse.emf.ecp.view.spi.vertical.model.VVerticalLayout;
import org.eclipse.emf.ecp.view.test.common.spi.EMFFormsRevealServiceFixture;
import org.eclipse.emf.ecp.view.test.common.spi.EMFFormsViewContextFixture.DomainModel;
import org.eclipse.emf.ecp.view.test.common.spi.EMFFormsViewContextFixture.ViewModel;
import org.eclipse.emf.ecp.view.test.common.spi.EMFMockingRunner;
import org.eclipse.emf.ecp.view.test.common.spi.EMock;
import org.eclipse.emf.ecp.view.test.common.swt.spi.SWTViewTestHelper;
import org.eclipse.emfforms.bazaar.Bid;
import org.eclipse.emfforms.bazaar.Create;
import org.eclipse.emfforms.spi.core.services.controlmapper.EMFFormsSettingToControlMapper;
import org.eclipse.emfforms.spi.core.services.reveal.EMFFormsRevealProvider;
import org.eclipse.emfforms.spi.core.services.reveal.RevealStep;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

/**
 * Tests covering the {@link CategorizationRevealProvider} class.
 */
@RunWith(EMFMockingRunner.class)
public class CategorizationRevealProvider_PTest {

	@ViewModel
	private final VCategorizationElement categorizations = VCategorizationFactory.eINSTANCE
		.createCategorizationElement();

	@DomainModel
	private final EObject rootObject = EcoreFactory.eINSTANCE.createEClass();

	@EMock
	private EObject obj1;

	@EMock
	private EObject obj2;

	private VCategorization mid1;
	private VCategory cat1;
	private VCategory cat2;
	private VCategory cat3;

	@Rule
	public final TestRule realm = DefaultRealm.rule();

	@Rule
	public final EMFFormsRevealServiceFixture<ViewModelContext> fixture = EMFFormsRevealServiceFixture.create(
		ViewModelContext.class, this);

	private Shell shell;

	/**
	 * Initializes me.
	 */
	public CategorizationRevealProvider_PTest() {
		super();
	}

	@Test
	public void revealInTree() {
		final VContainer container = (VContainer) cat3.getComposite();

		final Runnable reveal = mock(Runnable.class);

		final EMFFormsRevealProvider compositeRevealer = new EMFFormsRevealProvider() {
			@Bid
			public Double bid(VElement view, EObject model) {
				return view == container && model == obj1 ? Double.MAX_VALUE : null;
			}

			@Create
			public RevealStep create(VElement view, EObject model) {
				return view == container && model == obj1
					? RevealStep.reveal(view, model, reveal)
					: RevealStep.fail();
			}
		};
		fixture.addRevealProvider(compositeRevealer);

		render();

		fixture.reveal(obj1);

		assertThat("The category was not selected", categorizations.getCurrentSelection(), is(cat3));

		verify(reveal).run();
	}

	@Test
	public void revealInTreeNested() {
		// Introduce a couple of intermediary layers of categorization
		final VCategorization int1 = VCategorizationFactory.eINSTANCE.createCategorization();
		final VCategorization int2 = VCategorizationFactory.eINSTANCE.createCategorization();
		int1.getCategorizations().add(int2);
		int2.getCategorizations().addAll(new ArrayList<>(categorizations.getCategorizations()));
		categorizations.getCategorizations().add(int1);

		final VContainer container = (VContainer) cat3.getComposite();

		final Runnable reveal = mock(Runnable.class);

		final EMFFormsRevealProvider compositeRevealer = new EMFFormsRevealProvider() {
			@Bid
			public Double bid(VElement view, EObject model) {
				return view == container && model == obj1 ? Double.MAX_VALUE : null;
			}

			@Create
			public RevealStep create(VElement view, EObject model) {
				return view == container && model == obj1
					? RevealStep.reveal(view, model, reveal)
					: RevealStep.fail();
			}
		};
		fixture.addRevealProvider(compositeRevealer);

		render();

		fixture.reveal(obj1);

		assertThat("The category was not selected", categorizations.getCurrentSelection(), is(cat3));

		verify(reveal).run();
	}

	@Test
	public void revealInTabs() {
		// Render mid1 and cat3 in tabs
		categorizations.setMainCategoryDepth(1);

		final VContainer container = (VContainer) cat2.getComposite();

		final Runnable reveal = mock(Runnable.class);

		final EMFFormsRevealProvider compositeRevealer = new EMFFormsRevealProvider() {
			@Bid
			public Double bid(VElement view, EObject model) {
				return view == container && model == obj2 ? Double.MAX_VALUE : null;
			}

			@Create
			public RevealStep create(VElement view, EObject model) {
				return view == container && model == obj2
					? RevealStep.reveal(view, model, reveal)
					: RevealStep.fail();
			}
		};
		fixture.addRevealProvider(compositeRevealer);

		render();

		fixture.reveal(obj2);

		assertThat("The category was not selected", categorizations.getCurrentSelection(), is(cat2));

		verify(reveal).run();
	}

	/**
	 * Integration test for revealing a specific control (by feature) within a tree categorization,
	 * which is the scenario that didn't work as reported in <a href="http://eclip.se/551066">bug 551066</a>.
	 * The test relies on the fact that the non-default category containing the control to be revealed
	 * would not end up being selected if the control within did not provide a successful reveal step.
	 *
	 * @see <a href="http://eclip.se/551066">bug 551066</a>
	 */
	@Test
	public void revealSpecificControlInTree() {
		final VVerticalLayout vertical = VVerticalFactory.eINSTANCE.createVerticalLayout();
		cat3.setComposite(vertical);

		final VControl control = VViewFactory.eINSTANCE.createControl();
		final EStructuralFeature feature = EcorePackage.Literals.ENAMED_ELEMENT__NAME;
		control.setDomainModelReference(feature);
		vertical.getChildren().add(control);

		// Revealing a control requires the settings mapper
		final EMFFormsSettingToControlMapper mapper = mock(EMFFormsSettingToControlMapper.class);
		when(mapper.hasControlsFor(rootObject)).thenReturn(true);
		when(mapper.hasMapping(createSetting(rootObject, feature), control)).thenReturn(true);
		fixture.putService(EMFFormsSettingToControlMapper.class, mapper);

		render();

		fixture.reveal(rootObject, feature);

		// The category would only be selected if the control within it was revealed.
		// In the case of bug 551066, context injection of the ControlRevealProvider failed
		// to create the provider because the name control renderer was not yet available
		assertThat("The category was not selected", categorizations.getCurrentSelection(), is(cat3));
	}

	//
	// Test framework
	//

	@Before
	public void createViewModel() {
		mid1 = VCategorizationFactory.eINSTANCE.createCategorization();
		cat1 = VCategorizationFactory.eINSTANCE.createCategory();
		cat2 = VCategorizationFactory.eINSTANCE.createCategory();
		cat3 = VCategorizationFactory.eINSTANCE.createCategory();

		mid1.getCategorizations().add(cat1);
		mid1.getCategorizations().add(cat2);

		categorizations.getCategorizations().add(mid1);
		categorizations.getCategorizations().add(cat3);

		VContainedContainer container = eMock(VContainedContainer.class, withESettings().eContents().eContainer(cat1));
		cat1.setComposite(container);
		container = eMock(VContainedContainer.class, withESettings().eContents().eContainer(cat2));
		cat2.setComposite(container);
		container = eMock(VContainedContainer.class, withESettings().eContents().eContainer(cat3));
		cat3.setComposite(container);
	}

	@Before
	public void createShell() {
		shell = new Shell();
	}

	@After
	public void destroyShell() {
		shell.dispose();
		shell = null;
	}

	void render() {
		SWTViewTestHelper.render(fixture.getViewContext(), shell);
	}

}
