/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Johannes Faltermeier
 * 
 *******************************************************************************/
package org.eclipse.emf.ecp.view.table.ui.swt.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.model.util.ViewModelUtil;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.renderer.RenderingResultRow;
import org.eclipse.emf.ecp.view.spi.swt.SWTRendererFactory;
import org.eclipse.emf.ecp.view.spi.table.model.VTableColumn;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableFactory;
import org.eclipse.emf.ecp.view.test.common.swt.DatabindingClassRunner;
import org.eclipse.emf.ecp.view.test.common.swt.SWTViewTestHelper;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(DatabindingClassRunner.class)
public class SWTTableTest {

	private Shell shell;
	private EObject domainElement;

	@Before
	public void init() {
		shell = SWTViewTestHelper.createShell();

		final EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		eClass.getESuperTypes().add(EcorePackage.eINSTANCE.getEClass());
		domainElement = eClass;
	}

	@Test
	public void testUninitializedTableWithoutColumns() throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption {
		// setup model
		final TableControlHandle handle = createUninitializedTableWithoutColumns();
		final VDomainModelReference domainModelReference = handle.getTableControl().getDomainModelReference();
		//
		final Control render = SWTViewTestHelper.render(handle.getTableControl(), domainElement, shell);
		assertNull(render);

	}

	@Test
	public void testInitializedTableWithoutColumnsAndEmptyReference() throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption {
		// setup model
		final EClass createEClass = EcoreFactory.eINSTANCE.createEClass();
		createEClass.eUnset(EcorePackage.eINSTANCE.getEClass_ESuperTypes());
		domainElement = createEClass;
		final TableControlHandle handle = createInitializedTableWithoutTableColumns();

		try {
			SWTViewTestHelper.render(handle.getTableControl(), domainElement, shell);
		} catch (final NullPointerException e) {
			fail("Fails without a reference in domain object");
		}

	}

	@Ignore
	@Test
	public void testInitializedTableWithoutColumnsSingleReference() throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption {
		// setup model
		final VView view = VViewFactory.eINSTANCE.createView();
		view.setRootEClass(VViewPackage.eINSTANCE.getView());
		domainElement = view;
		final TableControlHandle handle = createInitializedTableWithoutTableColumns();
		final VFeaturePathDomainModelReference domainModelReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		domainModelReference.setDomainModelEFeature(VViewPackage.eINSTANCE.getView_RootEClass());
		handle.getTableControl().setDomainModelReference(domainModelReference);

		try {
			SWTViewTestHelper.render(handle.getTableControl(), domainElement, shell);
		} catch (final ClassCastException e) {
			e.printStackTrace();
			fail("Fails with single reference in domain object");
		}

	}

	@Ignore
	@Test
	public void testInitializedTableWithoutColumnsEmptySingleReference() throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption {
		// setup model
		final VView view = VViewFactory.eINSTANCE.createView();
		domainElement = view;
		final TableControlHandle handle = createInitializedTableWithoutTableColumns();
		final VFeaturePathDomainModelReference domainModelReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		domainModelReference.setDomainModelEFeature(VViewPackage.eINSTANCE.getView_RootEClass());
		handle.getTableControl().setDomainModelReference(domainModelReference);

		try {
			SWTViewTestHelper.render(handle.getTableControl(), domainElement, shell);
		} catch (final NullPointerException e) {
			fail("Fails with empty single reference in domain object");
		}

	}

	@Test
	public void testTableWithoutColumns() throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		// setup model
		final TableControlHandle handle = createInitializedTableWithoutTableColumns();

		final Control render = SWTViewTestHelper.render(handle.getTableControl(), domainElement, shell);
		assertTrue(render instanceof Composite);

		assertEquals(domainElement.eClass().getEAttributes().size(), handle.getTableControl().getColumns().size());

		final Control control = getTable(render);
		assertTrue(control instanceof Table);
		final Table table = (Table) control;
		assertEquals(3, table.getColumnCount());
	}

	@Test
	public void testTableWithoutColumnsWithoutViewServices() throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption {
		final TableControlHandle handle = createInitializedTableWithoutTableColumns();
		final List<RenderingResultRow<Control>> resultRows = SWTRendererFactory.INSTANCE.render(shell,
			handle.getTableControl(),
			new ViewModelContextWithoutServices(handle.getTableControl()));
		if (resultRows == null) {
			fail();
		}
		final Control render = resultRows.get(0).getMainControl();
		assertTrue(render instanceof Composite);

		assertEquals(0, handle.getTableControl().getColumns().size());

		final Control control = getTable(render);
		assertTrue(control instanceof Table);
		final Table table = (Table) control;
		assertEquals(1, table.getColumnCount());
	}

	@Test
	public void testTableWithTwoColumns() throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		// setup model
		final TableControlHandle handle = createTableWithTwoTableColumns();
		final Control render = SWTViewTestHelper.render(handle.getTableControl(), domainElement, shell);
		assertTrue(render instanceof Composite);

		final Control control = getTable(render);
		assertTrue(control instanceof Table);
		final Table table = (Table) control;
		assertEquals(2, table.getColumnCount());

	}

	@Test
	public void testTableWithTwoColumnsWithoutViewServices() throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption {
		// setup model
		final TableControlHandle handle = createTableWithTwoTableColumns();
		final List<RenderingResultRow<Control>> resultRows = SWTRendererFactory.INSTANCE.render(shell,
			handle.getTableControl(),
			new ViewModelContextWithoutServices(handle.getTableControl()));
		if (resultRows == null) {
			fail();
		}
		final Control render = resultRows.get(0).getMainControl();
		assertTrue(render instanceof Composite);

		final Control control = getTable(render);
		assertTrue(control instanceof Table);
		final Table table = (Table) control;
		assertEquals(2, table.getColumnCount());
	}

	private Control getTable(Control render) {
		Composite composite = (Composite) render;
		composite = (Composite) composite.getChildren()[1];
		composite = (Composite) composite.getChildren()[0];
		composite = (Composite) composite.getChildren()[0];
		composite = (Composite) composite.getChildren()[0];
		return composite.getChildren()[0];
	}

	private static TableControlHandle createTableWithTwoTableColumns() {
		final TableControlHandle tableControlHandle = createInitializedTableWithoutTableColumns();
		final VTableColumn tableColumn1 = createTableColumn();
		tableColumn1.setAttribute(EcorePackage.eINSTANCE.getEClass_Abstract());
		tableControlHandle.addFirstTableColumn(tableColumn1);
		final VTableColumn tableColumn2 = createTableColumn();
		tableColumn2.setAttribute(EcorePackage.eINSTANCE.getEClass_Abstract());
		tableControlHandle.addSecondTableColumn(tableColumn2);
		return tableControlHandle;
	}

	/**
	 * @return
	 */
	private static VTableColumn createTableColumn() {
		return VTableFactory.eINSTANCE.createTableColumn();
	}

	public static TableControlHandle createInitializedTableWithoutTableColumns() {
		final TableControlHandle tableControlHandle = createUninitializedTableWithoutColumns();
		final VFeaturePathDomainModelReference domainModelReference = VTableFactory.eINSTANCE
			.createTableDomainModelReference();
		domainModelReference.setDomainModelEFeature(EcorePackage.eINSTANCE.getEClass_ESuperTypes());
		tableControlHandle.getTableControl().setDomainModelReference(domainModelReference);

		return tableControlHandle;
	}

	public static TableControlHandle createUninitializedTableWithoutColumns() {
		final VTableControl tableControl = createTableControl();
		return new TableControlHandle(tableControl);
	}

	/**
	 * @return
	 */
	private static VTableControl createTableControl() {
		final VTableControl tc = VTableFactory.eINSTANCE.createTableControl();
		tc.setDomainModelReference(VViewFactory.eINSTANCE.createFeaturePathDomainModelReference());
		return tc;
	}

	/**
	 * Stub implementation without getting services from ex. point.
	 * 
	 * @author jfaltermeier
	 * 
	 */
	private class ViewModelContextWithoutServices implements ViewModelContext {

		private final VElement view;

		public ViewModelContextWithoutServices(VElement view) {
			this.view = view;
			ViewModelUtil.resolveDomainReferences(getViewModel(), getDomainModel());
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelContext#getViewModel()
		 */
		public VElement getViewModel() {
			return view;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelContext#getDomainModel()
		 */
		public EObject getDomainModel() {
			return domainElement;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelContext#registerViewChangeListener(org.eclipse.emf.ecp.view.spi.context.ViewModelContext.ModelChangeListener)
		 */
		public void registerViewChangeListener(ModelChangeListener modelChangeListener) {
			// not needed
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelContext#unregisterViewChangeListener(org.eclipse.emf.ecp.view.spi.context.ViewModelContext.ModelChangeListener)
		 */
		public void unregisterViewChangeListener(ModelChangeListener modelChangeListener) {
			// not needed
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelContext#registerDomainChangeListener(org.eclipse.emf.ecp.view.spi.context.ViewModelContext.ModelChangeListener)
		 */
		public void registerDomainChangeListener(ModelChangeListener modelChangeListener) {
			// not needed
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelContext#unregisterDomainChangeListener(org.eclipse.emf.ecp.view.spi.context.ViewModelContext.ModelChangeListener)
		 */
		public void unregisterDomainChangeListener(ModelChangeListener modelChangeListener) {
			// not needed
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelContext#dispose()
		 */
		public void dispose() {
			// not needed
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelContext#hasService(java.lang.Class)
		 */
		public <T> boolean hasService(Class<T> serviceType) {
			return false;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelContext#getService(java.lang.Class)
		 */
		public <T> T getService(Class<T> serviceType) {
			return null;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelContext#getControlsFor(org.eclipse.emf.ecore.EStructuralFeature.Setting)
		 */
		public Set<VControl> getControlsFor(Setting setting) {
			// TODO Auto-generated method stub
			return null;
		}

	}

}
