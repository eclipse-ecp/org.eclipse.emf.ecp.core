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
package org.eclipse.emf.ecp.view.treemasterdetail.ui.swt.internal;

import static org.eclipse.emf.ecp.view.test.common.spi.EMFMocking.eMock;
import static org.eclipse.emf.ecp.view.test.common.spi.EMFMocking.withESettings;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.test.common.DefaultRealm;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.swt.services.DefaultSelectionProviderService;
import org.eclipse.emf.ecp.view.spi.swt.services.ECPSelectionProviderService;
import org.eclipse.emf.ecp.view.test.common.spi.EMFFormsRevealServiceFixture;
import org.eclipse.emf.ecp.view.test.common.spi.EMFFormsViewContextFixture.DomainModel;
import org.eclipse.emf.ecp.view.test.common.spi.EMFFormsViewContextFixture.ViewModel;
import org.eclipse.emf.ecp.view.test.common.swt.spi.SWTTestUtil;
import org.eclipse.emf.ecp.view.test.common.swt.spi.SWTViewTestHelper;
import org.eclipse.emf.ecp.view.treemasterdetail.model.VTreeMasterDetail;
import org.eclipse.emf.ecp.view.treemasterdetail.model.VTreeMasterDetailFactory;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emfforms.bazaar.Bid;
import org.eclipse.emfforms.bazaar.Create;
import org.eclipse.emfforms.spi.core.services.reveal.EMFFormsRevealProvider;
import org.eclipse.emfforms.spi.core.services.reveal.Reveal;
import org.eclipse.emfforms.spi.core.services.reveal.RevealHelper;
import org.eclipse.emfforms.spi.core.services.reveal.RevealStep;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * Tests covering the {@link TreeRevealProvider} class.
 */
@SuppressWarnings("nls")
public class TreeRevealProvider_PTest {

	@ViewModel
	private final VView viewModel = VViewFactory.eINSTANCE.createView();

	@DomainModel
	private EObject rootObject;

	private EObject class1;
	private EObject class2;

	private EObject att1;
	private EObject att2;

	private EObject detailObj;

	private VTreeMasterDetail treeMD;

	@Rule
	public final TestRule realm = DefaultRealm.rule();

	@Rule
	public final EMFFormsRevealServiceFixture<ViewModelContext> fixture = EMFFormsRevealServiceFixture.create(
		ViewModelContext.class, this);

	private Shell shell;

	/**
	 * Initializes me.
	 */
	public TreeRevealProvider_PTest() {
		super();

		createDomainModel();
	}

	/**
	 * Test revealing an object that is presented and selectable in the tree.
	 */
	@Test
	public void revealInTree() {
		final Runnable reveal = mock(Runnable.class);

		fixture.addRevealProvider(new ViewRevealer(reveal));

		render();

		fixture.reveal(att1);

		SWTTestUtil.waitForUIThread();

		verify(reveal).run();

		final Tree tree = SWTTestUtil.findControl(shell, 0, Tree.class);
		final TreeItem[] selection = tree.getSelection();
		assertThat("No tree selection", selection.length, is(1));
		assertThat("Tree selection incorrect", selection[0].getData(), is(att1));
	}

	/**
	 * Test revealing an object that is presented only in the detail view of an object
	 * that is selectable in the tree.
	 */
	@Test
	public void revealInDetail() {
		final Runnable reveal = mock(Runnable.class);

		fixture.addRevealProvider(new ViewRevealer(mock(Runnable.class)));
		fixture.addRevealProvider(new DetailRevealer(reveal));

		render();

		fixture.reveal(detailObj);

		SWTTestUtil.waitForUIThread();

		verify(reveal).run();

		final Tree tree = SWTTestUtil.findControl(shell, 0, Tree.class);
		final TreeItem[] selection = tree.getSelection();
		assertThat("No tree selection", selection.length, is(1));
		assertThat("Tree selection incorrect", selection[0].getData(), is(att2));
	}

	//
	// Test framework
	//

	@Before
	public void createViewModel() {
		treeMD = VTreeMasterDetailFactory.eINSTANCE.createTreeMasterDetail();
		viewModel.getChildren().add(treeMD);

		// The renderer needs this
		fixture.putService(ECPSelectionProviderService.class, new DefaultSelectionProviderService());
	}

	private void createDomainModel() {
		// Tiny dynamic schema to make sure that we get a generated view model
		// for the details (for predictability)
		final EPackage pkg = EcoreFactory.eINSTANCE.createEPackage();
		pkg.setName("pkg");
		pkg.setNsPrefix("pkg");
		pkg.setNsURI("fake:pkg");
		final EClass namedElement = EcoreFactory.eINSTANCE.createEClass();
		namedElement.setName("NamedElement");
		pkg.getEClassifiers().add(namedElement);
		final EAttribute name = EcoreFactory.eINSTANCE.createEAttribute();
		name.setName("name");
		name.setEType(EcorePackage.Literals.ESTRING);
		namedElement.getEStructuralFeatures().add(name);
		final EReference children = EcoreFactory.eINSTANCE.createEReference();
		children.setName("children");
		children.setEType(namedElement);
		children.setContainment(true);
		children.setLowerBound(0);
		children.setUpperBound(ETypedElement.UNBOUNDED_MULTIPLICITY);
		namedElement.getEStructuralFeatures().add(children);

		// Create the model that will show in the tree
		rootObject = EcoreUtil.create(namedElement);
		rootObject.eSet(name, "pkg");
		class1 = EcoreUtil.create(namedElement);
		class1.eSet(name, "Class1");
		class2 = EcoreUtil.create(namedElement);
		class2.eSet(name, "Class2");
		rootObject.eSet(children, Arrays.asList(class1, class2));

		att1 = EcoreUtil.create(namedElement);
		att1.eSet(name, "att1");
		att2 = EcoreUtil.create(namedElement);
		att2.eSet(name, "att2");
		class2.eSet(children, Arrays.asList(att1, att2));

		// An object that isn't presented in the tree but as a detail
		// of an object that is in the tree
		detailObj = eMock(EObject.class, withESettings().eContainer(att2));

		// The detail rendeering for featurees wants to find types in the resource set
		final Resource res = new ResourceImpl(URI.createURI("fake:resource"));
		res.getContents().add(rootObject);
		new AdapterFactoryEditingDomain(new ReflectiveItemProviderAdapterFactory(),
			new BasicCommandStack()).getResourceSet().getResources().add(res);
	}

	@Before
	public void createShell() {
		shell = new Shell();
	}

	@Before
	public void mockChildContexts() {
		when(fixture.getViewContext().getChildContext(any(), any(), any(), (ViewModelService[]) anyVararg()))
			.then(invocation -> {
				final ViewModelContext result = fixture.createChildContext((VElement) invocation.getArguments()[1],
					"child",
					(VView) invocation.getArguments()[2],
					(EObject) invocation.getArguments()[0]);
				when(result.getParentContext()).thenReturn((ViewModelContext) invocation.getMock());
				return result;
			});
	}

	@After
	public void destroyShell() {
		shell.dispose();
		shell = null;
	}

	void render() {
		SWTViewTestHelper.render(fixture.getViewContext(), shell);
	}

	//
	// Nested types
	//

	/**
	 * A high-bidding reveal provider to make sure that we drill into the view
	 * to find the tree, regardless of other possible contributions in the
	 * current configuration.
	 */
	private final class ViewRevealer implements EMFFormsRevealProvider {

		private final Runnable reveal;

		ViewRevealer(Runnable reveal) {
			super();

			this.reveal = reveal;
		}

		@Bid
		public Double bid(VView view) {
			return view == viewModel ? Double.MAX_VALUE : null;
		}

		@Create
		public RevealStep create(VView view, EObject model, RevealHelper helper) {
			return view == viewModel
				? helper.drillDown(this)
				: RevealStep.fail();
		}

		@Reveal
		private RevealStep drillDown(VView view, EObject model) {
			return RevealStep.reveal(view, model, reveal);
		}
	}

	/**
	 * A high-bidding reveal provider to mock revealing the detail object
	 * that is not presented in the tree.
	 */
	private final class DetailRevealer implements EMFFormsRevealProvider {

		private final Runnable reveal;

		DetailRevealer(Runnable reveal) {
			super();

			this.reveal = reveal;
		}

		@Bid
		public Double bid(VView view, EObject object) {
			// The detail is a generated view
			return view != viewModel && object == detailObj ? Double.MAX_VALUE : null;
		}

		@Create
		public RevealStep create(VView view, EObject model) {
			return RevealStep.reveal(view, model, reveal);
		}

	}

}
