/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.treemasterdetail.validation.test;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextFactory;
import org.eclipse.emf.ecp.view.spi.model.VDiagnostic;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.treemasterdetail.model.VTreeMasterDetail;
import org.eclipse.emf.ecp.view.treemasterdetail.model.VTreeMasterDetailFactory;
import org.eclipse.emf.ecp.view.treemasterdetail.validation.test.TestTMD.ChildLevel1;
import org.eclipse.emf.ecp.view.treemasterdetail.validation.test.TestTMD.ChildLevel2;
import org.eclipse.emf.ecp.view.treemasterdetail.validation.test.TestTMD.Root;
import org.eclipse.emf.ecp.view.treemasterdetail.validation.test.TestTMD.TestTMDFactory;
import org.junit.Test;

/**
 * Validation tests for TreeMasterDetail.
 *
 * @author Eugen Neufeld
 *
 */
public class TreeMasterDetailValidation_ITest {

	private static final String VALID_NAME = "test"; //$NON-NLS-1$

	@Test
	public void test_InvalidRoot() {
		final VView view = VViewFactory.eINSTANCE.createView();
		final Root root = TestTMDFactory.eINSTANCE.createRoot();
		view.setRootEClass(root.eClass());

		final VTreeMasterDetail control = VTreeMasterDetailFactory.eINSTANCE
			.createTreeMasterDetail();

		view.getChildren().add(control);

		ViewModelContextFactory.INSTANCE.createViewModelContext(view, root);

		final VDiagnostic diagnostic = control.getDiagnostic();
		assertEquals(Diagnostic.ERROR, diagnostic.getHighestSeverity(), 0);
		assertEquals(1, diagnostic.getDiagnostics(root).size());
	}

	@Test
	public void test_ValidRoot() {
		final VView view = VViewFactory.eINSTANCE.createView();
		final Root root = TestTMDFactory.eINSTANCE.createRoot();
		root.setName(VALID_NAME);
		view.setRootEClass(root.eClass());

		final VTreeMasterDetail control = VTreeMasterDetailFactory.eINSTANCE
			.createTreeMasterDetail();

		view.getChildren().add(control);

		ViewModelContextFactory.INSTANCE.createViewModelContext(view, root);

		final VDiagnostic diagnostic = control.getDiagnostic();
		assertEquals(Diagnostic.OK, diagnostic.getHighestSeverity(), 0);
		assertEquals(0, diagnostic.getDiagnostics(root).size());
	}

	@Test
	public void test_InvalidRootInvalidChild() {
		final VView view = VViewFactory.eINSTANCE.createView();
		final Root root = TestTMDFactory.eINSTANCE.createRoot();
		final ChildLevel1 childLevel1 = TestTMDFactory.eINSTANCE.createChildLevel1();
		root.getChildren().add(childLevel1);

		view.setRootEClass(root.eClass());

		final VTreeMasterDetail control = VTreeMasterDetailFactory.eINSTANCE
			.createTreeMasterDetail();

		view.getChildren().add(control);

		ViewModelContextFactory.INSTANCE.createViewModelContext(view, root);

		final VDiagnostic diagnostic = control.getDiagnostic();
		assertEquals(Diagnostic.ERROR, diagnostic.getHighestSeverity(), 0);
		assertEquals(2, diagnostic.getDiagnostics(root).size());
		assertEquals(childLevel1, diagnostic.getDiagnostics(root).get(0)
			.getData().get(0));
		assertEquals(root, diagnostic.getDiagnostics(root).get(1).getData()
			.get(0));
		assertEquals(1, diagnostic.getDiagnostics(childLevel1).size());
	}

	@Test
	public void test_ValidRootInvalidChild() {
		final VView view = VViewFactory.eINSTANCE.createView();
		final Root root = TestTMDFactory.eINSTANCE.createRoot();
		root.setName(VALID_NAME);
		final ChildLevel1 childLevel1 = TestTMDFactory.eINSTANCE.createChildLevel1();
		root.getChildren().add(childLevel1);
		view.setRootEClass(root.eClass());

		final VTreeMasterDetail control = VTreeMasterDetailFactory.eINSTANCE
			.createTreeMasterDetail();

		view.getChildren().add(control);

		ViewModelContextFactory.INSTANCE.createViewModelContext(view, root);

		final VDiagnostic diagnostic = control.getDiagnostic();
		assertEquals(Diagnostic.ERROR, diagnostic.getHighestSeverity(), 0);
		assertEquals(1, diagnostic.getDiagnostics(root).size());
		assertEquals(childLevel1, diagnostic.getDiagnostics(root).get(0)
			.getData().get(0));
		assertEquals(1, diagnostic.getDiagnostics(childLevel1).size());
	}

	@Test
	public void test_InvalidRootValidChild() {
		final VView view = VViewFactory.eINSTANCE.createView();
		final Root root = TestTMDFactory.eINSTANCE.createRoot();
		final ChildLevel1 childLevel1 = TestTMDFactory.eINSTANCE.createChildLevel1();
		childLevel1.setName(VALID_NAME);
		root.getChildren().add(childLevel1);

		view.setRootEClass(root.eClass());

		final VTreeMasterDetail control = VTreeMasterDetailFactory.eINSTANCE
			.createTreeMasterDetail();

		view.getChildren().add(control);

		ViewModelContextFactory.INSTANCE.createViewModelContext(view, root);

		final VDiagnostic diagnostic = control.getDiagnostic();
		assertEquals(Diagnostic.ERROR, diagnostic.getHighestSeverity(), 0);
		assertEquals(1, diagnostic.getDiagnostics(root).size());
		assertEquals(root, diagnostic.getDiagnostics(root).get(0).getData()
			.get(0));
		assertEquals(0, diagnostic.getDiagnostics(childLevel1).size());
	}

	@Test
	public void test_ValidRootValidChild() {
		final VView view = VViewFactory.eINSTANCE.createView();
		final Root root = TestTMDFactory.eINSTANCE.createRoot();
		root.setName(VALID_NAME);
		final ChildLevel1 childLevel1 = TestTMDFactory.eINSTANCE.createChildLevel1();
		childLevel1.setName(VALID_NAME);
		root.getChildren().add(childLevel1);
		view.setRootEClass(root.eClass());

		final VTreeMasterDetail control = VTreeMasterDetailFactory.eINSTANCE
			.createTreeMasterDetail();

		view.getChildren().add(control);

		ViewModelContextFactory.INSTANCE.createViewModelContext(view, root);

		final VDiagnostic diagnostic = control.getDiagnostic();
		assertEquals(Diagnostic.OK, diagnostic.getHighestSeverity(), 0);
		assertEquals(0, diagnostic.getDiagnostics(root).size());
		assertEquals(0, diagnostic.getDiagnostics(childLevel1).size());
	}

	@Test
	public void test_ValidRootInvalidChildren() {
		final VView view = VViewFactory.eINSTANCE.createView();
		final Root root = TestTMDFactory.eINSTANCE.createRoot();
		root.setName(VALID_NAME);
		final ChildLevel1 childLevel1 = TestTMDFactory.eINSTANCE.createChildLevel1();
		childLevel1.setName(VALID_NAME);
		root.getChildren().add(childLevel1);
		final ChildLevel1 childLevel1_2 = TestTMDFactory.eINSTANCE.createChildLevel1();
		root.getChildren().add(childLevel1_2);
		view.setRootEClass(root.eClass());

		final VTreeMasterDetail control = VTreeMasterDetailFactory.eINSTANCE
			.createTreeMasterDetail();

		view.getChildren().add(control);

		ViewModelContextFactory.INSTANCE.createViewModelContext(view, root);

		final VDiagnostic diagnostic = control.getDiagnostic();
		assertEquals(Diagnostic.ERROR, diagnostic.getHighestSeverity(), 0);
		assertEquals(1, diagnostic.getDiagnostics(root).size());
		assertEquals(childLevel1_2, diagnostic.getDiagnostics(root).get(0)
			.getData().get(0));
		assertEquals(0, diagnostic.getDiagnostics(childLevel1).size());
		assertEquals(1, diagnostic.getDiagnostics(childLevel1_2).size());
	}

	@Test
	public void test_InvalidChildInvalidSubChild() {
		final VView view = VViewFactory.eINSTANCE.createView();
		final Root root = TestTMDFactory.eINSTANCE.createRoot();
		root.setName(VALID_NAME);
		final ChildLevel1 childLevel1 = TestTMDFactory.eINSTANCE.createChildLevel1();
		root.getChildren().add(childLevel1);
		final ChildLevel2 childLevel2 = TestTMDFactory.eINSTANCE.createChildLevel2();
		childLevel1.getChildren().add(childLevel2);

		view.setRootEClass(root.eClass());

		final VTreeMasterDetail control = VTreeMasterDetailFactory.eINSTANCE
			.createTreeMasterDetail();

		view.getChildren().add(control);

		ViewModelContextFactory.INSTANCE.createViewModelContext(view, root);

		final VDiagnostic diagnostic = control.getDiagnostic();
		assertEquals(Diagnostic.ERROR, diagnostic.getHighestSeverity(), 0);
		assertEquals(2, diagnostic.getDiagnostics(root).size());
		assertEquals(childLevel1, diagnostic.getDiagnostics(root).get(0)
			.getData().get(0));
		assertEquals(childLevel2, diagnostic.getDiagnostics(root).get(1)
			.getData().get(0));
		assertEquals(2, diagnostic.getDiagnostics(childLevel1).size());
		assertEquals(childLevel1, diagnostic.getDiagnostics(childLevel1).get(0)
			.getData().get(0));
		assertEquals(childLevel2, diagnostic.getDiagnostics(childLevel1).get(1)
			.getData().get(0));
		assertEquals(1, diagnostic.getDiagnostics(childLevel2).size());
	}

	@Test
	public void test_ValidChildInvalidSubChild() {
		final VView view = VViewFactory.eINSTANCE.createView();
		final Root root = TestTMDFactory.eINSTANCE.createRoot();
		root.setName(VALID_NAME);
		final ChildLevel1 childLevel1 = TestTMDFactory.eINSTANCE.createChildLevel1();
		childLevel1.setName(VALID_NAME);
		root.getChildren().add(childLevel1);
		final ChildLevel2 childLevel2 = TestTMDFactory.eINSTANCE.createChildLevel2();
		childLevel1.getChildren().add(childLevel2);

		view.setRootEClass(root.eClass());

		final VTreeMasterDetail control = VTreeMasterDetailFactory.eINSTANCE
			.createTreeMasterDetail();

		view.getChildren().add(control);

		ViewModelContextFactory.INSTANCE.createViewModelContext(view, root);

		final VDiagnostic diagnostic = control.getDiagnostic();
		assertEquals(Diagnostic.ERROR, diagnostic.getHighestSeverity(), 0);
		assertEquals(1, diagnostic.getDiagnostics(root).size());
		assertEquals(childLevel2, diagnostic.getDiagnostics(root).get(0)
			.getData().get(0));
		assertEquals(1, diagnostic.getDiagnostics(childLevel1).size());
		assertEquals(childLevel2, diagnostic.getDiagnostics(childLevel1).get(0)
			.getData().get(0));
		assertEquals(1, diagnostic.getDiagnostics(childLevel2).size());
	}

	@Test
	public void test_InvalidChildValidSubChild() {
		final VView view = VViewFactory.eINSTANCE.createView();
		final Root root = TestTMDFactory.eINSTANCE.createRoot();
		root.setName(VALID_NAME);
		final ChildLevel1 childLevel1 = TestTMDFactory.eINSTANCE.createChildLevel1();
		final ChildLevel2 childLevel2 = TestTMDFactory.eINSTANCE.createChildLevel2();
		childLevel2.setName(VALID_NAME);
		childLevel1.getChildren().add(childLevel2);

		root.getChildren().add(childLevel1);

		view.setRootEClass(root.eClass());

		final VTreeMasterDetail control = VTreeMasterDetailFactory.eINSTANCE
			.createTreeMasterDetail();

		view.getChildren().add(control);

		ViewModelContextFactory.INSTANCE.createViewModelContext(view, root);

		final VDiagnostic diagnostic = control.getDiagnostic();
		assertEquals(Diagnostic.ERROR, diagnostic.getHighestSeverity(), 0);
		assertEquals(1, diagnostic.getDiagnostics(root).size());
		assertEquals(childLevel1, diagnostic.getDiagnostics(root).get(0)
			.getData().get(0));
		assertEquals(1, diagnostic.getDiagnostics(childLevel1).size());
		assertEquals(0, diagnostic.getDiagnostics(childLevel2).size());
	}

	@Test
	public void test_ValidChildValidSubChild() {
		final VView view = VViewFactory.eINSTANCE.createView();
		final Root root = TestTMDFactory.eINSTANCE.createRoot();
		root.setName(VALID_NAME);
		final ChildLevel1 childLevel1 = TestTMDFactory.eINSTANCE.createChildLevel1();
		childLevel1.setName(VALID_NAME);
		root.getChildren().add(childLevel1);
		final ChildLevel2 childLevel2 = TestTMDFactory.eINSTANCE.createChildLevel2();
		childLevel2.setName(VALID_NAME);
		childLevel1.getChildren().add(childLevel2);

		view.setRootEClass(root.eClass());

		final VTreeMasterDetail control = VTreeMasterDetailFactory.eINSTANCE
			.createTreeMasterDetail();

		view.getChildren().add(control);

		ViewModelContextFactory.INSTANCE.createViewModelContext(view, root);

		final VDiagnostic diagnostic = control.getDiagnostic();
		assertEquals(Diagnostic.OK, diagnostic.getHighestSeverity(), 0);
		assertEquals(0, diagnostic.getDiagnostics(root).size());
		assertEquals(0, diagnostic.getDiagnostics(childLevel1).size());
		assertEquals(0, diagnostic.getDiagnostics(childLevel2).size());
	}

	@Test
	public void test_InvalidRootToValid() {
		final VView view = VViewFactory.eINSTANCE.createView();
		final Root root = TestTMDFactory.eINSTANCE.createRoot();
		view.setRootEClass(root.eClass());

		final VTreeMasterDetail control = VTreeMasterDetailFactory.eINSTANCE
			.createTreeMasterDetail();

		view.getChildren().add(control);

		ViewModelContextFactory.INSTANCE.createViewModelContext(view, root);

		root.setName(VALID_NAME);

		final VDiagnostic diagnostic = control.getDiagnostic();
		assertEquals(Diagnostic.OK, diagnostic.getHighestSeverity(), 0);
		assertEquals(0, diagnostic.getDiagnostics(root).size());
	}

	@Test
	public void test_ValidRootToInvalid() {
		final VView view = VViewFactory.eINSTANCE.createView();
		final Root root = TestTMDFactory.eINSTANCE.createRoot();
		root.setName(VALID_NAME);
		view.setRootEClass(root.eClass());

		final VTreeMasterDetail control = VTreeMasterDetailFactory.eINSTANCE
			.createTreeMasterDetail();

		view.getChildren().add(control);

		ViewModelContextFactory.INSTANCE.createViewModelContext(view, root);

		root.setName(null);

		final VDiagnostic diagnostic = control.getDiagnostic();
		assertEquals(Diagnostic.ERROR, diagnostic.getHighestSeverity(), 0);
		assertEquals(1, diagnostic.getDiagnostics(root).size());
	}

	@Test
	public void test_InvalidChildToValid() {
		final VView view = VViewFactory.eINSTANCE.createView();
		final Root root = TestTMDFactory.eINSTANCE.createRoot();
		root.setName(VALID_NAME);
		final ChildLevel1 childLevel1 = TestTMDFactory.eINSTANCE.createChildLevel1();
		root.getChildren().add(childLevel1);

		view.setRootEClass(root.eClass());

		final VTreeMasterDetail control = VTreeMasterDetailFactory.eINSTANCE
			.createTreeMasterDetail();

		view.getChildren().add(control);

		ViewModelContextFactory.INSTANCE.createViewModelContext(view, root);

		childLevel1.setName(VALID_NAME);

		final VDiagnostic diagnostic = control.getDiagnostic();
		assertEquals(Diagnostic.OK, diagnostic.getHighestSeverity(), 0);
		assertEquals(0, diagnostic.getDiagnostics(root).size());
		assertEquals(0, diagnostic.getDiagnostics(childLevel1).size());
	}

	@Test
	public void test_ValidChildToInvalid() {
		final VView view = VViewFactory.eINSTANCE.createView();
		final Root root = TestTMDFactory.eINSTANCE.createRoot();
		root.setName(VALID_NAME);
		final ChildLevel1 childLevel1 = TestTMDFactory.eINSTANCE.createChildLevel1();
		childLevel1.setName(VALID_NAME);
		root.getChildren().add(childLevel1);
		view.setRootEClass(root.eClass());

		final VTreeMasterDetail control = VTreeMasterDetailFactory.eINSTANCE
			.createTreeMasterDetail();

		view.getChildren().add(control);

		ViewModelContextFactory.INSTANCE.createViewModelContext(view, root);

		childLevel1.setName(null);

		final VDiagnostic diagnostic = control.getDiagnostic();
		assertEquals(Diagnostic.ERROR, diagnostic.getHighestSeverity(), 0);
		assertEquals(1, diagnostic.getDiagnostics(root).size());
		assertEquals(childLevel1, diagnostic.getDiagnostics(root).get(0)
			.getData().get(0));
		assertEquals(1, diagnostic.getDiagnostics(childLevel1).size());
	}

	@Test
	public void test_ValidRootAddValidChild() {
		final VView view = VViewFactory.eINSTANCE.createView();
		final Root root = TestTMDFactory.eINSTANCE.createRoot();
		root.setName(VALID_NAME);
		view.setRootEClass(root.eClass());

		final VTreeMasterDetail control = VTreeMasterDetailFactory.eINSTANCE
			.createTreeMasterDetail();

		view.getChildren().add(control);

		ViewModelContextFactory.INSTANCE.createViewModelContext(view, root);

		final ChildLevel1 childLevel1 = TestTMDFactory.eINSTANCE.createChildLevel1();
		childLevel1.setName(VALID_NAME);
		root.getChildren().add(childLevel1);

		final VDiagnostic diagnostic = control.getDiagnostic();
		assertEquals(Diagnostic.OK, diagnostic.getHighestSeverity(), 0);
		assertEquals(0, diagnostic.getDiagnostics(root).size());
		assertEquals(0, diagnostic.getDiagnostics(childLevel1).size());
	}

	@Test
	public void test_ValidRootAddInvalidChild() {
		final VView view = VViewFactory.eINSTANCE.createView();
		final Root root = TestTMDFactory.eINSTANCE.createRoot();
		root.setName(VALID_NAME);
		view.setRootEClass(root.eClass());

		final VTreeMasterDetail control = VTreeMasterDetailFactory.eINSTANCE
			.createTreeMasterDetail();

		view.getChildren().add(control);

		ViewModelContextFactory.INSTANCE.createViewModelContext(view, root);

		final ChildLevel1 childLevel1 = TestTMDFactory.eINSTANCE.createChildLevel1();
		root.getChildren().add(childLevel1);

		final VDiagnostic diagnostic = control.getDiagnostic();
		assertEquals(Diagnostic.ERROR, diagnostic.getHighestSeverity(), 0);
		assertEquals(1, diagnostic.getDiagnostics(root).size());
		assertEquals(childLevel1, diagnostic.getDiagnostics(root).get(0)
			.getData().get(0));
		assertEquals(1, diagnostic.getDiagnostics(childLevel1).size());
	}

	@Test
	public void test_InvalidRootAddValidChild() {
		final VView view = VViewFactory.eINSTANCE.createView();
		final Root root = TestTMDFactory.eINSTANCE.createRoot();
		view.setRootEClass(root.eClass());

		final VTreeMasterDetail control = VTreeMasterDetailFactory.eINSTANCE
			.createTreeMasterDetail();

		view.getChildren().add(control);

		ViewModelContextFactory.INSTANCE.createViewModelContext(view, root);

		final ChildLevel1 childLevel1 = TestTMDFactory.eINSTANCE.createChildLevel1();
		childLevel1.setName(VALID_NAME);
		root.getChildren().add(childLevel1);

		final VDiagnostic diagnostic = control.getDiagnostic();
		assertEquals(Diagnostic.ERROR, diagnostic.getHighestSeverity(), 0);
		assertEquals(1, diagnostic.getDiagnostics(root).size());
		assertEquals(0, diagnostic.getDiagnostics(childLevel1).size());
	}

	@Test
	public void test_InvalidRootAddInvalidChild() {
		final VView view = VViewFactory.eINSTANCE.createView();
		final Root root = TestTMDFactory.eINSTANCE.createRoot();
		view.setRootEClass(root.eClass());

		final VTreeMasterDetail control = VTreeMasterDetailFactory.eINSTANCE
			.createTreeMasterDetail();

		view.getChildren().add(control);

		ViewModelContextFactory.INSTANCE.createViewModelContext(view, root);

		final ChildLevel1 childLevel1 = TestTMDFactory.eINSTANCE.createChildLevel1();
		root.getChildren().add(childLevel1);

		final VDiagnostic diagnostic = control.getDiagnostic();
		assertEquals(Diagnostic.ERROR, diagnostic.getHighestSeverity(), 0);
		assertEquals(2, diagnostic.getDiagnostics(root).size());
		assertEquals(childLevel1, diagnostic.getDiagnostics(root).get(0)
			.getData().get(0));
		assertEquals(root, diagnostic.getDiagnostics(root).get(1).getData()
			.get(0));
		assertEquals(1, diagnostic.getDiagnostics(childLevel1).size());
	}

	@Test
	public void test_ValidRootAddValidSubTree() {
		final VView view = VViewFactory.eINSTANCE.createView();
		final Root root = TestTMDFactory.eINSTANCE.createRoot();
		root.setName(VALID_NAME);
		view.setRootEClass(root.eClass());

		final VTreeMasterDetail control = VTreeMasterDetailFactory.eINSTANCE
			.createTreeMasterDetail();

		view.getChildren().add(control);

		ViewModelContextFactory.INSTANCE.createViewModelContext(view, root);

		final ChildLevel1 childLevel1 = TestTMDFactory.eINSTANCE.createChildLevel1();
		childLevel1.setName(VALID_NAME);
		final ChildLevel2 childLevel2 = TestTMDFactory.eINSTANCE.createChildLevel2();
		childLevel2.setName(VALID_NAME);
		childLevel1.getChildren().add(childLevel2);

		root.getChildren().add(childLevel1);

		final VDiagnostic diagnostic = control.getDiagnostic();
		assertEquals(Diagnostic.OK, diagnostic.getHighestSeverity(), 0);
		assertEquals(0, diagnostic.getDiagnostics(root).size());
		assertEquals(0, diagnostic.getDiagnostics(childLevel1).size());
		assertEquals(0, diagnostic.getDiagnostics(childLevel2).size());
	}

	@Test
	public void test_ValidRootAddInvalidSubTree() {
		final VView view = VViewFactory.eINSTANCE.createView();
		final Root root = TestTMDFactory.eINSTANCE.createRoot();
		root.setName(VALID_NAME);
		view.setRootEClass(root.eClass());

		final VTreeMasterDetail control = VTreeMasterDetailFactory.eINSTANCE
			.createTreeMasterDetail();

		view.getChildren().add(control);

		ViewModelContextFactory.INSTANCE.createViewModelContext(view, root);

		final ChildLevel1 childLevel1 = TestTMDFactory.eINSTANCE.createChildLevel1();
		final ChildLevel2 childLevel2 = TestTMDFactory.eINSTANCE.createChildLevel2();
		childLevel2.setName(VALID_NAME);
		childLevel1.getChildren().add(childLevel2);

		root.getChildren().add(childLevel1);

		final VDiagnostic diagnostic = control.getDiagnostic();
		assertEquals(Diagnostic.ERROR, diagnostic.getHighestSeverity(), 0);
		assertEquals(1, diagnostic.getDiagnostics(root).size());
		assertEquals(childLevel1, diagnostic.getDiagnostics(root).get(0)
			.getData().get(0));
		assertEquals(1, diagnostic.getDiagnostics(childLevel1).size());
		assertEquals(0, diagnostic.getDiagnostics(childLevel2).size());
	}

	@Test
	public void test_ValidRootAddInvalidSubTree2() {
		final VView view = VViewFactory.eINSTANCE.createView();
		final Root root = TestTMDFactory.eINSTANCE.createRoot();
		root.setName(VALID_NAME);
		view.setRootEClass(root.eClass());

		final VTreeMasterDetail control = VTreeMasterDetailFactory.eINSTANCE
			.createTreeMasterDetail();

		view.getChildren().add(control);

		ViewModelContextFactory.INSTANCE.createViewModelContext(view, root);

		final ChildLevel1 childLevel1 = TestTMDFactory.eINSTANCE.createChildLevel1();
		childLevel1.setName(VALID_NAME);
		final ChildLevel2 childLevel2 = TestTMDFactory.eINSTANCE.createChildLevel2();
		childLevel1.getChildren().add(childLevel2);

		root.getChildren().add(childLevel1);

		final VDiagnostic diagnostic = control.getDiagnostic();
		assertEquals(Diagnostic.ERROR, diagnostic.getHighestSeverity(), 0);
		assertEquals(1, diagnostic.getDiagnostics(root).size());
		assertEquals(childLevel2, diagnostic.getDiagnostics(root).get(0)
			.getData().get(0));
		assertEquals(1, diagnostic.getDiagnostics(childLevel1).size());
		assertEquals(childLevel2, diagnostic.getDiagnostics(childLevel1).get(0)
			.getData().get(0));
		assertEquals(1, diagnostic.getDiagnostics(childLevel2).size());
	}

	@Test
	public void test_InvalidRootAddValidSubTree() {
		final VView view = VViewFactory.eINSTANCE.createView();
		final Root root = TestTMDFactory.eINSTANCE.createRoot();
		view.setRootEClass(root.eClass());

		final VTreeMasterDetail control = VTreeMasterDetailFactory.eINSTANCE
			.createTreeMasterDetail();

		view.getChildren().add(control);

		ViewModelContextFactory.INSTANCE.createViewModelContext(view, root);

		final ChildLevel1 childLevel1 = TestTMDFactory.eINSTANCE.createChildLevel1();
		childLevel1.setName(VALID_NAME);
		final ChildLevel2 childLevel2 = TestTMDFactory.eINSTANCE.createChildLevel2();
		childLevel2.setName(VALID_NAME);
		childLevel1.getChildren().add(childLevel2);

		root.getChildren().add(childLevel1);

		final VDiagnostic diagnostic = control.getDiagnostic();
		assertEquals(Diagnostic.ERROR, diagnostic.getHighestSeverity(), 0);
		assertEquals(1, diagnostic.getDiagnostics(root).size());
		assertEquals(0, diagnostic.getDiagnostics(childLevel1).size());
		assertEquals(0, diagnostic.getDiagnostics(childLevel2).size());
	}

	@Test
	public void test_InvalidRootAddInvalidSubTree1() {
		final VView view = VViewFactory.eINSTANCE.createView();
		final Root root = TestTMDFactory.eINSTANCE.createRoot();
		view.setRootEClass(root.eClass());

		final VTreeMasterDetail control = VTreeMasterDetailFactory.eINSTANCE
			.createTreeMasterDetail();

		view.getChildren().add(control);

		ViewModelContextFactory.INSTANCE.createViewModelContext(view, root);

		final ChildLevel1 childLevel1 = TestTMDFactory.eINSTANCE.createChildLevel1();
		final ChildLevel2 childLevel2 = TestTMDFactory.eINSTANCE.createChildLevel2();
		childLevel2.setName(VALID_NAME);
		childLevel1.getChildren().add(childLevel2);

		root.getChildren().add(childLevel1);

		final VDiagnostic diagnostic = control.getDiagnostic();
		assertEquals(Diagnostic.ERROR, diagnostic.getHighestSeverity(), 0);
		assertEquals(2, diagnostic.getDiagnostics(root).size());
		assertEquals(childLevel1, diagnostic.getDiagnostics(root).get(0)
			.getData().get(0));
		assertEquals(root, diagnostic.getDiagnostics(root).get(1).getData()
			.get(0));
		assertEquals(1, diagnostic.getDiagnostics(childLevel1).size());
		assertEquals(0, diagnostic.getDiagnostics(childLevel2).size());
	}

	@Test
	public void test_InvalidRootAddInvalidSubTree2() {
		final VView view = VViewFactory.eINSTANCE.createView();
		final Root root = TestTMDFactory.eINSTANCE.createRoot();
		view.setRootEClass(root.eClass());

		final VTreeMasterDetail control = VTreeMasterDetailFactory.eINSTANCE
			.createTreeMasterDetail();

		view.getChildren().add(control);

		ViewModelContextFactory.INSTANCE.createViewModelContext(view, root);

		final ChildLevel1 childLevel1 = TestTMDFactory.eINSTANCE.createChildLevel1();
		childLevel1.setName(VALID_NAME);
		final ChildLevel2 childLevel2 = TestTMDFactory.eINSTANCE.createChildLevel2();
		childLevel1.getChildren().add(childLevel2);

		root.getChildren().add(childLevel1);

		final VDiagnostic diagnostic = control.getDiagnostic();
		assertEquals(Diagnostic.ERROR, diagnostic.getHighestSeverity(), 0);
		assertEquals(2, diagnostic.getDiagnostics(root).size());
		assertEquals(childLevel2, diagnostic.getDiagnostics(root).get(0)
			.getData().get(0));
		assertEquals(root, diagnostic.getDiagnostics(root).get(1).getData()
			.get(0));
		assertEquals(1, diagnostic.getDiagnostics(childLevel1).size());
		assertEquals(childLevel2, diagnostic.getDiagnostics(childLevel1).get(0)
			.getData().get(0));
		assertEquals(1, diagnostic.getDiagnostics(childLevel2).size());
	}

	@Test
	public void test_ValidRootWithChildAddInvalidSibling() {
		final VView view = VViewFactory.eINSTANCE.createView();
		final Root root = TestTMDFactory.eINSTANCE.createRoot();
		root.setName(VALID_NAME);
		final ChildLevel1 childLevel1 = TestTMDFactory.eINSTANCE.createChildLevel1();
		childLevel1.setName(VALID_NAME);
		root.getChildren().add(childLevel1);

		view.setRootEClass(root.eClass());

		final VTreeMasterDetail control = VTreeMasterDetailFactory.eINSTANCE
			.createTreeMasterDetail();

		view.getChildren().add(control);

		ViewModelContextFactory.INSTANCE.createViewModelContext(view, root);

		final ChildLevel1 childLevel1_2 = TestTMDFactory.eINSTANCE.createChildLevel1();
		root.getChildren().add(childLevel1_2);

		final VDiagnostic diagnostic = control.getDiagnostic();
		assertEquals(Diagnostic.ERROR, diagnostic.getHighestSeverity(), 0);
		assertEquals(1, diagnostic.getDiagnostics(root).size());
		assertEquals(childLevel1_2, diagnostic.getDiagnostics(root).get(0)
			.getData().get(0));
		assertEquals(0, diagnostic.getDiagnostics(childLevel1).size());
		assertEquals(1, diagnostic.getDiagnostics(childLevel1_2).size());
	}

	@Test
	public void test_ValidChildAddValidSubChild() {
		final VView view = VViewFactory.eINSTANCE.createView();
		final Root root = TestTMDFactory.eINSTANCE.createRoot();
		root.setName(VALID_NAME);
		final ChildLevel1 childLevel1 = TestTMDFactory.eINSTANCE.createChildLevel1();
		childLevel1.setName(VALID_NAME);
		root.getChildren().add(childLevel1);

		view.setRootEClass(root.eClass());

		final VTreeMasterDetail control = VTreeMasterDetailFactory.eINSTANCE
			.createTreeMasterDetail();

		view.getChildren().add(control);

		ViewModelContextFactory.INSTANCE.createViewModelContext(view, root);

		final ChildLevel2 childLevel2 = TestTMDFactory.eINSTANCE.createChildLevel2();
		childLevel2.setName(VALID_NAME);
		childLevel1.getChildren().add(childLevel2);

		final VDiagnostic diagnostic = control.getDiagnostic();
		assertEquals(Diagnostic.OK, diagnostic.getHighestSeverity(), 0);
		assertEquals(0, diagnostic.getDiagnostics(root).size());
		assertEquals(0, diagnostic.getDiagnostics(childLevel1).size());
		assertEquals(0, diagnostic.getDiagnostics(childLevel2).size());
	}

	@Test
	public void test_ValidChildAddInvalidSubChild() {
		final VView view = VViewFactory.eINSTANCE.createView();
		final Root root = TestTMDFactory.eINSTANCE.createRoot();
		root.setName(VALID_NAME);
		final ChildLevel1 childLevel1 = TestTMDFactory.eINSTANCE.createChildLevel1();
		childLevel1.setName(VALID_NAME);
		root.getChildren().add(childLevel1);
		view.setRootEClass(root.eClass());

		final VTreeMasterDetail control = VTreeMasterDetailFactory.eINSTANCE
			.createTreeMasterDetail();

		view.getChildren().add(control);

		ViewModelContextFactory.INSTANCE.createViewModelContext(view, root);

		final ChildLevel2 childLevel2 = TestTMDFactory.eINSTANCE.createChildLevel2();
		childLevel1.getChildren().add(childLevel2);

		final VDiagnostic diagnostic = control.getDiagnostic();
		assertEquals(Diagnostic.ERROR, diagnostic.getHighestSeverity(), 0);
		assertEquals(1, diagnostic.getDiagnostics(root).size());
		assertEquals(childLevel2, diagnostic.getDiagnostics(root).get(0)
			.getData().get(0));
		assertEquals(1, diagnostic.getDiagnostics(childLevel1).size());
		assertEquals(childLevel2, diagnostic.getDiagnostics(childLevel1).get(0)
			.getData().get(0));
		assertEquals(1, diagnostic.getDiagnostics(childLevel2).size());
	}

	@Test
	public void test_InvalidChildAddValidSubChild() {
		final VView view = VViewFactory.eINSTANCE.createView();
		final Root root = TestTMDFactory.eINSTANCE.createRoot();
		root.setName(VALID_NAME);
		final ChildLevel1 childLevel1 = TestTMDFactory.eINSTANCE.createChildLevel1();
		root.getChildren().add(childLevel1);

		view.setRootEClass(root.eClass());

		final VTreeMasterDetail control = VTreeMasterDetailFactory.eINSTANCE
			.createTreeMasterDetail();

		view.getChildren().add(control);

		ViewModelContextFactory.INSTANCE.createViewModelContext(view, root);

		final ChildLevel2 childLevel2 = TestTMDFactory.eINSTANCE.createChildLevel2();
		childLevel2.setName(VALID_NAME);
		root.getChildren().add(childLevel1);

		final VDiagnostic diagnostic = control.getDiagnostic();
		assertEquals(Diagnostic.ERROR, diagnostic.getHighestSeverity(), 0);
		assertEquals(1, diagnostic.getDiagnostics(root).size());
		assertEquals(childLevel1, diagnostic.getDiagnostics(root).get(0)
			.getData().get(0));
		assertEquals(1, diagnostic.getDiagnostics(childLevel1).size());
		assertEquals(0, diagnostic.getDiagnostics(childLevel2).size());
	}

	@Test
	public void test_InvalidChildAddInvalidSubChild() {
		final VView view = VViewFactory.eINSTANCE.createView();
		final Root root = TestTMDFactory.eINSTANCE.createRoot();
		root.setName(VALID_NAME);
		final ChildLevel1 childLevel1 = TestTMDFactory.eINSTANCE.createChildLevel1();
		root.getChildren().add(childLevel1);

		view.setRootEClass(root.eClass());

		final VTreeMasterDetail control = VTreeMasterDetailFactory.eINSTANCE
			.createTreeMasterDetail();

		view.getChildren().add(control);

		ViewModelContextFactory.INSTANCE.createViewModelContext(view, root);

		final ChildLevel2 childLevel2 = TestTMDFactory.eINSTANCE.createChildLevel2();
		childLevel1.getChildren().add(childLevel2);

		final VDiagnostic diagnostic = control.getDiagnostic();
		assertEquals(Diagnostic.ERROR, diagnostic.getHighestSeverity(), 0);
		assertEquals(2, diagnostic.getDiagnostics(root).size());
		assertEquals(childLevel1, diagnostic.getDiagnostics(root).get(0)
			.getData().get(0));
		assertEquals(childLevel2, diagnostic.getDiagnostics(root).get(1)
			.getData().get(0));
		assertEquals(2, diagnostic.getDiagnostics(childLevel1).size());
		assertEquals(childLevel1, diagnostic.getDiagnostics(childLevel1).get(0)
			.getData().get(0));
		assertEquals(childLevel2, diagnostic.getDiagnostics(childLevel1).get(1)
			.getData().get(0));
		assertEquals(1, diagnostic.getDiagnostics(childLevel2).size());
	}

	@Test
	public void test_ValidChildWithSubChildAddInvalidSibling() {
		final VView view = VViewFactory.eINSTANCE.createView();
		final Root root = TestTMDFactory.eINSTANCE.createRoot();
		root.setName(VALID_NAME);
		final ChildLevel1 childLevel1 = TestTMDFactory.eINSTANCE.createChildLevel1();
		childLevel1.setName(VALID_NAME);
		final ChildLevel2 childLevel2 = TestTMDFactory.eINSTANCE.createChildLevel2();
		childLevel2.setName(VALID_NAME);
		childLevel1.getChildren().add(childLevel2);

		root.getChildren().add(childLevel1);
		view.setRootEClass(root.eClass());

		final VTreeMasterDetail control = VTreeMasterDetailFactory.eINSTANCE
			.createTreeMasterDetail();

		view.getChildren().add(control);

		ViewModelContextFactory.INSTANCE.createViewModelContext(view, root);

		final ChildLevel2 childLevel2_2 = TestTMDFactory.eINSTANCE.createChildLevel2();
		childLevel1.getChildren().add(childLevel2_2);

		final VDiagnostic diagnostic = control.getDiagnostic();
		assertEquals(Diagnostic.ERROR, diagnostic.getHighestSeverity(), 0);
		assertEquals(1, diagnostic.getDiagnostics(root).size());
		assertEquals(childLevel2_2, diagnostic.getDiagnostics(root).get(0)
			.getData().get(0));
		assertEquals(1, diagnostic.getDiagnostics(childLevel1).size());
		assertEquals(childLevel2_2, diagnostic.getDiagnostics(childLevel1).get(0)
			.getData().get(0));
		assertEquals(0, diagnostic.getDiagnostics(childLevel2).size());
		assertEquals(1, diagnostic.getDiagnostics(childLevel2_2).size());
	}

	@Test
	public void test_ValidRootAddValidChildToInvalid() {
		final VView view = VViewFactory.eINSTANCE.createView();
		final Root root = TestTMDFactory.eINSTANCE.createRoot();
		root.setName(VALID_NAME);
		view.setRootEClass(root.eClass());

		final VTreeMasterDetail control = VTreeMasterDetailFactory.eINSTANCE
			.createTreeMasterDetail();

		view.getChildren().add(control);

		ViewModelContextFactory.INSTANCE.createViewModelContext(view, root);

		final ChildLevel1 childLevel1 = TestTMDFactory.eINSTANCE.createChildLevel1();
		childLevel1.setName(VALID_NAME);
		root.getChildren().add(childLevel1);

		childLevel1.setName(null);

		final VDiagnostic diagnostic = control.getDiagnostic();
		assertEquals(Diagnostic.ERROR, diagnostic.getHighestSeverity(), 0);
		assertEquals(1, diagnostic.getDiagnostics(root).size());
		assertEquals(childLevel1, diagnostic.getDiagnostics(root).get(0)
			.getData().get(0));
		assertEquals(1, diagnostic.getDiagnostics(childLevel1).size());
	}
}
