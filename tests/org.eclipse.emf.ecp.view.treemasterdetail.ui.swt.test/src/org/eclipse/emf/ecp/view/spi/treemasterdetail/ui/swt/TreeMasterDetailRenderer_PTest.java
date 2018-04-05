/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.treemasterdetail.ui.swt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.eclipse.emf.ecp.test.common.DefaultRealm;
import org.eclipse.emf.ecp.view.internal.context.ViewModelContextImpl;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.treemasterdetail.model.VTreeMasterDetail;
import org.eclipse.emf.ecp.view.treemasterdetail.model.VTreeMasterDetailFactory;
import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.BowlingPackage;
import org.eclipse.emf.emfstore.bowling.League;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MenuListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.TypedListener;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("restriction")
public class TreeMasterDetailRenderer_PTest {

	private DefaultRealm realm;
	private TreeMasterDetailSWTRenderer renderer;
	private Shell shell;
	private ViewModelContext context;

	@Before
	public void before() throws DatabindingFailedException {
		realm = new DefaultRealm();
		final ReportService reportService = mock(ReportService.class);
		final VTreeMasterDetail tmd = VTreeMasterDetailFactory.eINSTANCE.createTreeMasterDetail();
		// define explicit inner View
		final VView detailView = VViewFactory.eINSTANCE.createView();
		detailView.setRootEClass(BowlingPackage.eINSTANCE.getLeague());
		final VControl vControl = VViewFactory.eINSTANCE.createControl();
		final VFeaturePathDomainModelReference featurePathDomainModelReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		featurePathDomainModelReference.setDomainModelEFeature(BowlingPackage.eINSTANCE.getLeague_Name());
		vControl.setDomainModelReference(featurePathDomainModelReference);
		detailView.getChildren().add(vControl);
		tmd.setDetailView(detailView);

		final League domainModel = BowlingFactory.eINSTANCE.createLeague();
		context = new ViewModelContextImpl(tmd, domainModel);
		renderer = new TreeMasterDetailSWTRenderer(tmd, context, reportService);
		renderer.init();

		shell = new Shell(Display.getDefault(), SWT.NONE);
	}

	@After
	public void testTearDown() {
		realm.dispose();
		shell.dispose();
	}

	@Test
	public void getGridDescription() {
		final SWTGridDescription gridDescription = renderer.getGridDescription(null);
		assertEquals(1, gridDescription.getColumns());
		assertEquals(1, gridDescription.getRows());
	}

	@Test
	public void hasContextMenu() {
		assertTrue(renderer.hasContextMenu());
	}

	@Test
	public void hasDnDSupport() {
		assertTrue(renderer.hasDnDSupport());
	}

	@Test
	public void initialRendering_NoContainer() throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		final Control renderResult = render();
		assertTrue(Composite.class.isInstance(renderResult));
		final Composite resultComposite = Composite.class.cast(renderResult);
		assertEquals(2, resultComposite.getChildren().length);

		// FIXME why do we have an intermediate composite?
		assertTrue(Composite.class.isInstance(resultComposite.getChildren()[0]));
		final Composite topComposite = Composite.class.cast(resultComposite.getChildren()[0]);
		assertEquals(1, topComposite.getChildren().length);

		assertTrue(Composite.class.isInstance(topComposite.getChildren()[0]));
		final Composite titleComposite = Composite.class.cast(topComposite.getChildren()[0]);
		assertEquals(3, titleComposite.getChildren().length);
		// FIXME check backgroundcolor?

		assertTrue(Label.class.isInstance(titleComposite.getChildren()[0]));
		final Label titleIcon = Label.class.cast(titleComposite.getChildren()[0]);
		assertNotNull(titleIcon.getImage());

		assertTrue(Label.class.isInstance(titleComposite.getChildren()[1]));
		final Label titleLabel = Label.class.cast(titleComposite.getChildren()[1]);
		assertEquals("View Editor", titleLabel.getText()); //$NON-NLS-1$

		assertTrue(ToolBar.class.isInstance(titleComposite.getChildren()[2]));
		final ToolBar titleToolbar = ToolBar.class.cast(titleComposite.getChildren()[2]);
		assertEquals(0, titleToolbar.getChildren().length);

		// Bottom composite
		// FIXME why do we have an intermediate composite?
		assertTrue(Composite.class.isInstance(resultComposite.getChildren()[1]));
		final Composite bottomComposite = Composite.class.cast(resultComposite.getChildren()[1]);
		assertEquals(1, bottomComposite.getChildren().length);

		assertTrue(SashForm.class.isInstance(bottomComposite.getChildren()[0]));
		final SashForm sash = SashForm.class.cast(bottomComposite.getChildren()[0]);
		assertEquals(2, sash.getChildren().length);

		assertTrue(Composite.class.isInstance(sash.getChildren()[0]));
		final Composite treeComposite = Composite.class.cast(sash.getChildren()[0]);
		assertEquals(1, treeComposite.getChildren().length);

		assertTrue(Tree.class.isInstance(treeComposite.getChildren()[0]));
		final Tree tree = Tree.class.cast(treeComposite.getChildren()[0]);
		assertEquals(1, tree.getItemCount());
		final TreeItem item = TreeItem.class.cast(tree.getItems()[0]);
		assertEquals("League", item.getText()); //$NON-NLS-1$
		assertNotNull(item.getImage());

		assertTrue(ScrolledComposite.class.isInstance(sash.getChildren()[1]));
		final ScrolledComposite detailScrolledComposite = ScrolledComposite.class.cast(sash.getChildren()[1]);
		assertEquals(1, detailScrolledComposite.getChildren().length);
		// FIXME why do we have an intermediate composite?
		assertTrue(Composite.class.isInstance(detailScrolledComposite.getChildren()[0]));
		final Composite detailComposite = Composite.class.cast(detailScrolledComposite.getChildren()[0]);
		assertEquals(2, detailComposite.getChildren().length);

		// Detail Title
		assertTrue(Composite.class.isInstance(detailComposite.getChildren()[0]));
		final Composite detailTitleComposite = Composite.class.cast(detailComposite.getChildren()[0]);
		assertEquals(1, detailTitleComposite.getChildren().length);

		assertTrue(Label.class.isInstance(detailTitleComposite.getChildren()[0]));
		final Label detailTitle = Label.class.cast(detailTitleComposite.getChildren()[0]);
		assertEquals("Details", detailTitle.getText()); //$NON-NLS-1$

		// Detail Content
		assertTrue(Composite.class.isInstance(detailComposite.getChildren()[1]));
		final Composite detailContentComposite = Composite.class.cast(detailComposite.getChildren()[1]);
		assertEquals(1, detailContentComposite.getChildren().length);

		// too many intermediate composites
		final Composite content = Composite.class.cast(
			Composite.class.cast(
				detailContentComposite.getChildren()[0])
				.getChildren()[0]);
		final StringBuilder sb = new StringBuilder();
		sb.append(String.format("Instead of 3 Elements of Control found %1$s controls.", content.getChildren().length)); //$NON-NLS-1$
		sb.append("The controls are:"); //$NON-NLS-1$
		for (final Control c : content.getChildren()) {
			sb.append(String.format("Control: %1$s.", c)); //$NON-NLS-1$
		}
		assertEquals(sb.toString(), 3,
			content.getChildren().length);

		assertTrue(Label.class.isInstance(content.getChildren()[0]));
		final Label label = Label.class.cast(content.getChildren()[0]);
		assertEquals("Name", label.getText()); //$NON-NLS-1$

		assertTrue(Label.class.isInstance(content.getChildren()[1]));
		final Label validation = Label.class.cast(content.getChildren()[1]);
		assertNull(validation.getImage());

		assertTrue(Composite.class.isInstance(content.getChildren()[2]));
		final Composite control = Composite.class.cast(content.getChildren()[2]);
		assertEquals(1, control.getChildren().length);

		assertTrue(Text.class.isInstance(control.getChildren()[0]));
		final Text textControl = Text.class.cast(control.getChildren()[0]);
		assertEquals("", textControl.getText()); //$NON-NLS-1$

		assertContextMenu(tree, 1);
	}

	@Test
	public void tmd_readOnly() throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		context.getViewModel().setReadonly(true);
		final Control renderResult = render();
		final Tree tree = getTree(renderResult);
		assertTrue(tree.isEnabled());
		final Composite detail = getDetail(renderResult);
		assertTrue(detail.isEnabled());
		final Control[] content = getDetailContent(detail);
		assertFalse(content[2].isEnabled());

		assertContextMenu(tree, 0);
	}

	@Test
	public void detailView_readOnly() throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		((VTreeMasterDetail) context.getViewModel()).getDetailView().setReadonly(true);
		final Control renderResult = render();
		final Tree tree = getTree(renderResult);
		assertTrue(tree.isEnabled());
		final Composite detail = getDetail(renderResult);
		assertTrue(detail.isEnabled());
		final Control[] content = getDetailContent(detail);
		assertFalse(content[2].isEnabled());

		assertContextMenu(tree, 1);
	}

	@Test
	public void tmd_initially_disabled() throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		context.getViewModel().setEnabled(false);
		final Control renderResult = render();
		final Tree tree = getTree(renderResult);
		assertTrue(tree.isEnabled());
		final Composite detail = getDetail(renderResult);
		assertTrue(detail.isEnabled());
		final Control[] content = getDetailContent(detail);
		assertFalse(content[2].isEnabled());

		assertContextMenu(tree, 0);
	}

	@Test
	public void tmd_dynamic_disabled() throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		final Control renderResult = render();
		context.getViewModel().setEnabled(false);
		final Tree tree = getTree(renderResult);
		assertTrue(tree.isEnabled());
		final Composite detail = getDetail(renderResult);
		assertTrue(detail.isEnabled());
		final Control[] content = getDetailContent(detail);
		assertFalse(content[2].isEnabled());

		assertContextMenu(tree, 0);
	}

	@Test
	public void detailView_initially_disabled() throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		((VTreeMasterDetail) context.getViewModel()).getDetailView().setEnabled(false);
		final Control renderResult = render();
		final Tree tree = getTree(renderResult);
		assertTrue(tree.isEnabled());
		final Composite detail = getDetail(renderResult);
		assertTrue(detail.isEnabled());
		final Control[] content = getDetailContent(detail);
		assertFalse(content[2].isEnabled());

		assertContextMenu(tree, 1);
	}

	private Control render() throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		final Control renderResult = renderer.render(new SWTGridCell(0, 0, renderer), shell);
		renderer.finalizeRendering(shell);
		return renderResult;
	}

	private Tree getTree(Control renderResult) {
		final Composite resultComposite = Composite.class.cast(renderResult);
		final Composite bottomComposite = Composite.class.cast(resultComposite.getChildren()[1]);
		final SashForm sash = SashForm.class.cast(bottomComposite.getChildren()[0]);
		final Composite treeComposite = Composite.class.cast(sash.getChildren()[0]);
		final Tree tree = Tree.class.cast(treeComposite.getChildren()[0]);
		return tree;
	}

	private Composite getDetail(Control renderResult) {
		final Composite resultComposite = Composite.class.cast(renderResult);
		final Composite bottomComposite = Composite.class.cast(resultComposite.getChildren()[1]);
		final SashForm sash = SashForm.class.cast(bottomComposite.getChildren()[0]);
		final ScrolledComposite detailScrolledComposite = ScrolledComposite.class.cast(sash.getChildren()[1]);
		final Composite detailComposite = Composite.class.cast(detailScrolledComposite.getChildren()[0]);
		final Composite detailContentComposite = Composite.class.cast(detailComposite.getChildren()[1]);
		return detailContentComposite;
	}

	private Control[] getDetailContent(Composite detailContentComposite) {
		final Composite content = Composite.class.cast(
			Composite.class.cast(
				detailContentComposite.getChildren()[0])
				.getChildren()[0]);
		final Label label = Label.class.cast(content.getChildren()[0]);
		final Label validation = Label.class.cast(content.getChildren()[1]);
		final Composite control = Composite.class.cast(content.getChildren()[2]);
		final Text textControl = Text.class.cast(control.getChildren()[0]);

		return new Control[] { label, validation, textControl };
	}

	private void assertContextMenu(Tree tree, int numberItems) {
		final Menu menu = tree.getMenu();
		assertEquals(0, menu.getItemCount());
		final MenuListener menuListener = (MenuListener) ((TypedListener) menu.getListeners(SWT.Show)[0])
			.getEventListener();
		menuListener.menuShown(null);
		assertEquals(numberItems, menu.getItemCount());
	}
}
