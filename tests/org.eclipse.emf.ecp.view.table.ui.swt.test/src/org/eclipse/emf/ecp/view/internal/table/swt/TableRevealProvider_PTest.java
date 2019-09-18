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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecp.test.common.DefaultRealm;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
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
 * Tests covering the {@link TableRevealProvider} class.
 */
@RunWith(EMFMockingRunner.class)
public class TableRevealProvider_PTest {

	@ViewModel
	private final VView viewModel = VViewFactory.eINSTANCE.createView();

	@EMock
	@DomainModel
	private EClass rootObject;

	@EMock
	private EReference obj1;

	@EMock
	private EAttribute obj2;

	private VTableControl table;

	@Rule
	public final TestRule realm = DefaultRealm.rule();

	@Rule
	public final EMFFormsRevealServiceFixture<ViewModelContext> fixture = EMFFormsRevealServiceFixture.create(
		ViewModelContext.class, this);

	private Shell shell;

	/**
	 * Initializes me.
	 */
	public TableRevealProvider_PTest() {
		super();
	}

	@Test
	public void revealInTable() {
		final Runnable reveal = mock(Runnable.class);

		fixture.addRevealProvider(new ViewRevealer(reveal));

		render();

		fixture.reveal(obj2);

		SWTTestUtil.waitForUIThread();

		verify(reveal).run();

		final Table table = SWTTestUtil.findControl(shell, 0, Table.class);
		assertThat("Table selection incorrect", table.getSelectionIndex(), is(1));
	}

	//
	// Test framework
	//

	@Before
	public void createViewModel() {
		final TableControlHandle handle = TableTestUtil
			.createInitializedTableWithoutTableColumns(EcorePackage.Literals.ECLASS__ESTRUCTURAL_FEATURES);

		handle.addFirstTableColumn(TableTestUtil.createTableColumn(EcorePackage.Literals.ENAMED_ELEMENT__NAME));
		handle.addSecondTableColumn(TableTestUtil.createTableColumn(EcorePackage.Literals.ETYPED_ELEMENT__ETYPE));

		table = handle.getTableControl();
		viewModel.getChildren().add(table);
	}

	@Before
	public void createDomainModel() {
		final EList<EStructuralFeature> features = ECollections.asEList(obj1, obj2);
		when(rootObject.getEStructuralFeatures()).thenReturn(features);
		when(rootObject.eGet(EcorePackage.Literals.ECLASS__ESTRUCTURAL_FEATURES)).thenReturn(features);
		when(obj1.getName()).thenReturn("ref1");
		when(obj2.getName()).thenReturn("att1");

		// This is needed by the data binding service
		final EStructuralFeature.Setting setting = mock(EStructuralFeature.Setting.class);
		when(setting.getEObject()).thenReturn(rootObject);
		when(setting.getEStructuralFeature()).thenReturn(EcorePackage.Literals.ECLASS__ESTRUCTURAL_FEATURES);
		when(setting.get(anyBoolean())).thenReturn(features);
		when(((InternalEObject) rootObject).eSetting(EcorePackage.Literals.ECLASS__ESTRUCTURAL_FEATURES))
			.thenReturn(setting);
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

		private final Runnable reveal;

		ViewRevealer(Runnable reveal) {
			super();

			this.reveal = reveal;
		}

		@Bid
		public Double bid(VView view, EObject model) {
			return view == viewModel && model == obj2 ? Double.MAX_VALUE : null;
		}

		@Create
		public RevealStep create(VView view, EObject model, RevealHelper helper) {
			return view == viewModel && model == obj2
				? helper.drillDown(this)
				: RevealStep.fail();
		}

		@Reveal
		private RevealStep drillDown(VView view, EObject model) {
			return RevealStep.reveal(view, model, reveal);
		}
	}

}
