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
package org.eclipse.emf.ecp.view.internal.table.swt;

import static org.eclipse.emf.ecp.view.test.common.spi.EMFMocking.eSetContainer;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecp.test.common.DefaultRealm;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.table.model.DetailEditing;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.table.test.common.TableControlHandle;
import org.eclipse.emf.ecp.view.table.test.common.TableTestUtil;
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
import org.eclipse.emfforms.spi.core.services.reveal.Reveal;
import org.eclipse.emfforms.spi.core.services.reveal.RevealHelper;
import org.eclipse.emfforms.spi.core.services.reveal.RevealStep;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

/**
 * Tests covering the {@link TableDetailRevealProvider} class.
 */
@RunWith(EMFMockingRunner.class)
public class TableDetailRevealProvider_PTest {

	@ViewModel
	private final VView viewModel = VViewFactory.eINSTANCE.createView();

	@EMock
	@DomainModel
	private EPackage rootObject;

	@EMock
	private EClass class1;

	@EMock
	private EClass class2;

	@EMock
	private EReference obj1;

	@EMock
	private EAttribute obj2;

	private VTableControl masterTable;

	private final VView detailView = VViewFactory.eINSTANCE.createView();

	private VTableControl detailTable;

	@Rule
	public final TestRule realm = DefaultRealm.rule();

	@Rule
	public final EMFFormsRevealServiceFixture<ViewModelContext> fixture = EMFFormsRevealServiceFixture.create(
		ViewModelContext.class, this);

	private Shell shell;

	/**
	 * Initializes me.
	 */
	public TableDetailRevealProvider_PTest() {
		super();
	}

	@Test
	public void revealInTableInTableDetail() {
		final Runnable masterReveal = mock(Runnable.class);
		final Runnable detailReveal = mock(Runnable.class);

		fixture.addRevealProvider(new ViewRevealer(viewModel, masterReveal));
		fixture.addRevealProvider(new ViewRevealer(detailView, detailReveal));

		render();

		fixture.reveal(obj2);

		SWTTestUtil.waitForUIThread();

		// We revealed the root view and the detail view
		verify(masterReveal).run();
		verify(detailReveal).run();

		// The second table is the detail
		final Table table = SWTTestUtil.findControl(shell, 1, Table.class);
		assertThat("Table selection incorrect", table.getSelectionIndex(), is(1));
	}

	//
	// Test framework
	//

	@Before
	public void createViewModel() {
		final TableControlHandle masterHandle = TableTestUtil
			.createInitializedTableWithoutTableColumns(EcorePackage.Literals.EPACKAGE__ECLASSIFIERS);

		masterHandle.addFirstTableColumn(TableTestUtil.createTableColumn(EcorePackage.Literals.ENAMED_ELEMENT__NAME));
		masterHandle.addSecondTableColumn(
			TableTestUtil.createTableColumn(EcorePackage.Literals.ECLASSIFIER__INSTANCE_CLASS_NAME));

		masterTable = masterHandle.getTableControl();
		masterTable.setDetailEditing(DetailEditing.WITH_PANEL);
		viewModel.getChildren().add(masterTable);

		final TableControlHandle detailHandle = TableTestUtil
			.createInitializedTableWithoutTableColumns(EcorePackage.Literals.ECLASS__ESTRUCTURAL_FEATURES);

		detailHandle.addFirstTableColumn(TableTestUtil.createTableColumn(EcorePackage.Literals.ENAMED_ELEMENT__NAME));
		detailHandle.addSecondTableColumn(TableTestUtil.createTableColumn(EcorePackage.Literals.ETYPED_ELEMENT__MANY));

		detailTable = detailHandle.getTableControl();
		detailView.getChildren().add(detailTable);

		masterTable.setDetailView(detailView);
	}

	@Before
	public void createDomainModel() {
		final EList<EClassifier> classifiers = ECollections.asEList(class1, class2);
		when(rootObject.getEClassifiers()).thenReturn(classifiers);
		when(rootObject.eGet(EcorePackage.Literals.EPACKAGE__ECLASSIFIERS)).thenReturn(classifiers);

		when(class1.getName()).thenReturn("Class1");
		eSetContainer(class1, rootObject);
		when(class2.getName()).thenReturn("Class2");
		eSetContainer(class2, rootObject);
		final EList<EStructuralFeature> features = ECollections.asEList(obj1, obj2);
		when(class2.getEStructuralFeatures()).thenReturn(features);
		when(class2.eGet(EcorePackage.Literals.ECLASS__ESTRUCTURAL_FEATURES)).thenReturn(features);
		when(obj1.getName()).thenReturn("ref1");
		eSetContainer(obj1, class2);
		when(obj2.getName()).thenReturn("att1");
		eSetContainer(obj2, class2);

		// These are needed by the data binding service
		EStructuralFeature.Setting setting = mock(EStructuralFeature.Setting.class);
		when(setting.getEObject()).thenReturn(rootObject);
		when(setting.getEStructuralFeature()).thenReturn(EcorePackage.Literals.EPACKAGE__ECLASSIFIERS);
		when(setting.get(anyBoolean())).thenReturn(classifiers);
		when(((InternalEObject) rootObject).eSetting(EcorePackage.Literals.EPACKAGE__ECLASSIFIERS))
			.thenReturn(setting);
		setting = mock(EStructuralFeature.Setting.class);
		when(setting.getEObject()).thenReturn(class1);
		when(setting.getEStructuralFeature()).thenReturn(EcorePackage.Literals.ECLASS__ESTRUCTURAL_FEATURES);
		when(setting.get(anyBoolean())).thenReturn(ECollections.emptyEList());
		when(((InternalEObject) class1).eSetting(EcorePackage.Literals.ECLASS__ESTRUCTURAL_FEATURES))
			.thenReturn(setting);
		setting = mock(EStructuralFeature.Setting.class);
		when(setting.getEObject()).thenReturn(class2);
		when(setting.getEStructuralFeature()).thenReturn(EcorePackage.Literals.ECLASS__ESTRUCTURAL_FEATURES);
		when(setting.get(anyBoolean())).thenReturn(features);
		when(((InternalEObject) class2).eSetting(EcorePackage.Literals.ECLASS__ESTRUCTURAL_FEATURES))
			.thenReturn(setting);
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

	//
	// Nested types
	//

	/**
	 * A high-bidding reveal provider to make sure that we drill into the view
	 * to find the table, regardless of other possible contributions in the
	 * current configuration.
	 */
	private final class ViewRevealer implements EMFFormsRevealProvider {

		private final VView view;
		private final Runnable reveal;

		ViewRevealer(VView view, Runnable reveal) {
			super();

			this.view = view;
			this.reveal = reveal;
		}

		@Bid
		public Double bid(VView view, EObject model) {
			return view == this.view && model == obj2 ? Double.MAX_VALUE : null;
		}

		@Create
		public RevealStep create(VView view, EObject model, RevealHelper helper) {
			return view == this.view && model == obj2
				? helper.drillDown(this)
				: RevealStep.fail();
		}

		@Reveal
		private RevealStep drillDown(VView view, EObject model) {
			return RevealStep.reveal(view, model, reveal);
		}
	}

}
