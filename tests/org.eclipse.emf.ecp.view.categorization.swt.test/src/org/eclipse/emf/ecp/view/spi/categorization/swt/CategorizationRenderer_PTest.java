/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen - initial API and implementation
 * Christian W. Damus - bug 552852
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.categorization.swt;

import static java.util.Collections.singleton;
import static org.eclipse.emf.ecp.view.test.common.spi.EMFMocking.eMock;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTView;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.emf.ecp.view.categorization.swt.test.CategoryRendererTestHelper;
import org.eclipse.emf.ecp.view.spi.categorization.model.VAbstractCategorization;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorization;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationElement;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationFactory;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategory;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextFactory;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.ecp.view.test.common.swt.spi.DatabindingClassRunner;
import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.Player;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.swt.core.EMFFormsNoRendererException;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererFactory;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eugen
 *
 */
@RunWith(DatabindingClassRunner.class)
public class CategorizationRenderer_PTest {

	@Test
	public void testCategorizationElementTreeRenderer() throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption {
		final ReportService reportService = mock(ReportService.class);
		final EMFFormsRendererFactory emfFormsRendererFactory = mock(EMFFormsRendererFactory.class);

		final SWTGridCell gridCell = mock(SWTGridCell.class);
		final Shell shell = new Shell();
		final VCategorizationElement categorizationElement = mock(VCategorizationElement.class);
		final EList<VAbstractCategorization> categorizations = new BasicEList<VAbstractCategorization>();
		when(categorizationElement.getCategorizations()).thenReturn(categorizations);
		@SuppressWarnings("unchecked")
		final TreeIterator<EObject> iterator = mock(TreeIterator.class);
		when(iterator.hasNext()).thenReturn(false);
		when(categorizationElement.eAllContents()).thenReturn(iterator);
		final ViewModelContext vmc = mock(ViewModelContext.class);
		final SWTCategorizationElementRenderer categorizatrionElementRenderer = new SWTCategorizationElementRenderer(
			categorizationElement, vmc, reportService, emfFormsRendererFactory);
		categorizatrionElementRenderer.init();
		final Control render = categorizatrionElementRenderer.render(gridCell, shell);
		assertTrue(Composite.class.isInstance(render));
		assertEquals(2, Composite.class.cast(render).getChildren().length);
		assertTrue(Tree.class.isInstance(Composite.class.cast(render).getChildren()[0]));
		assertTrue(ScrolledComposite.class.isInstance(Composite.class.cast(render).getChildren()[1]));
	}

	@Test
	public void testCategorizationElementTabRenderer() throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption, EMFFormsNoRendererException {

		final Set<Object> contextUsers = new HashSet<>();

		final ReportService reportService = mock(ReportService.class);
		final EMFFormsRendererFactory emfFormsRendererFactory = mockRendererFactory(reportService);

		final SWTGridCell gridCell = mock(SWTGridCell.class);
		final Shell shell = new Shell();
		final VCategorizationElement categorizationElement = mock(VCategorizationElement.class);
		final EList<VAbstractCategorization> categorizations = new BasicEList<VAbstractCategorization>();
		categorizations.add(eMock(VCategory.class));
		when(categorizationElement.getCategorizations()).thenReturn(categorizations);
		final ViewModelContext vmc = mockViewModelContext(contextUsers);
		final VTViewTemplateProvider viewTemplateProvider = mock(VTViewTemplateProvider.class);
		final CategorizationElementTabbedSWTRenderer categorizatrionElementRenderer = new CategorizationElementTabbedSWTRenderer(
			categorizationElement, vmc, reportService, emfFormsRendererFactory, viewTemplateProvider);
		categorizatrionElementRenderer.init();
		final Control render = categorizatrionElementRenderer.render(gridCell, shell);
		categorizatrionElementRenderer.finalizeRendering(shell);
		assertTrue(CTabFolder.class.isInstance(render));

		shell.dispose();
		assertThat("Some context user remains", contextUsers, not(hasItem(anything())));
	}

	@Test
	public void testCompositeCategoryTreeRenderer() throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption {
		final ReportService reportService = mock(ReportService.class);
		final EMFFormsRendererFactory emfFormsRendererFactory = mock(EMFFormsRendererFactory.class);

		final SWTGridCell gridCell = mock(SWTGridCell.class);
		final Shell shell = new Shell();
		final VCategorizationElement categorizationElement = mock(VCategorizationElement.class);
		final EList<VAbstractCategorization> categorizations = new BasicEList<VAbstractCategorization>();
		final VCategorization categorization = mock(VCategorization.class);
		categorizations.add(categorization);
		when(categorization.getCategorizations()).thenReturn(new BasicEList<VAbstractCategorization>());
		when(categorizationElement.getCategorizations()).thenReturn(categorizations);
		@SuppressWarnings("unchecked")
		final TreeIterator<EObject> iterator = mock(TreeIterator.class);
		when(iterator.hasNext()).thenReturn(false);
		when(categorization.eAllContents()).thenReturn(iterator);

		when(categorization.eContainer()).thenReturn(categorizationElement);

		final ViewModelContext vmc = mock(ViewModelContext.class);
		final CompositeCategoryJFaceTreeRenderer categorizatrionElementRenderer = new CompositeCategoryJFaceTreeRenderer(
			categorization, vmc, reportService, emfFormsRendererFactory);
		categorizatrionElementRenderer.init();
		final Control render = categorizatrionElementRenderer.render(gridCell, shell);
		assertTrue(Composite.class.isInstance(render));
		assertEquals(2, Composite.class.cast(render).getChildren().length);
		assertTrue(Tree.class.isInstance(Composite.class.cast(render).getChildren()[0]));
		assertTrue(ScrolledComposite.class.isInstance(Composite.class.cast(render).getChildren()[1]));
	}

	@Test
	public void testTreeRendererSingleSelection() throws ECPRendererException {
		final VView view = VViewFactory.eINSTANCE.createView();

		final VCategorizationElement categorizationElement = VCategorizationFactory.eINSTANCE
			.createCategorizationElement();

		final VCategory category1 = VCategorizationFactory.eINSTANCE.createCategory();
		final VCategory category2 = VCategorizationFactory.eINSTANCE.createCategory();

		final VCategorization categorization = VCategorizationFactory.eINSTANCE.createCategorization();
		categorization.getCategorizations().add(category1);

		categorizationElement.getCategorizations().add(categorization);
		final VCategorization compositeCategory = VCategorizationFactory.eINSTANCE.createCategorization();
		compositeCategory.getCategorizations().add(category2);

		categorizationElement.getCategorizations().add(compositeCategory);

		view.getChildren().add(categorizationElement);

		final Shell shell = new Shell();
		shell.setLayout(new FillLayout());
		final Player player = BowlingFactory.eINSTANCE.createPlayer();

		final ViewModelContext vmc = ViewModelContextFactory.INSTANCE.createViewModelContext(view, player);
		final ECPSWTView ecpSwtView = ECPSWTViewRenderer.INSTANCE.render(shell, vmc);
		final Tree tree = CategoryRendererTestHelper.getTree(ecpSwtView.getSWTControl());

		tree.setSelection(tree.getItems());
		assertEquals("Categorization Tree allows multi selection", 0, tree.getSelectionCount());
		final TreeItem[] items = tree.getItems();
		for (final TreeItem treeItem : items) {
			tree.setSelection(treeItem);
			assertEquals(1, tree.getSelectionCount());
		}

	}

	@Test
	public void testCompositeCategoryElementTabRenderer() throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption {

		final Set<Object> contextUsers = new HashSet<>();

		final ReportService reportService = mock(ReportService.class);
		final EMFFormsRendererFactory emfFormsRendererFactory = mockRendererFactory(reportService);

		final SWTGridCell gridCell = mock(SWTGridCell.class);
		final Shell shell = new Shell();
		final VCategorizationElement categorizationElement = mock(VCategorizationElement.class);
		final EList<VAbstractCategorization> categorizations = new BasicEList<VAbstractCategorization>();
		final VCategorization categorization = mock(VCategorization.class);
		categorizations.add(categorization);
		final EList<VAbstractCategorization> tabs = new BasicEList<VAbstractCategorization>(
			singleton(eMock(VCategory.class)));
		when(categorization.getCategorizations()).thenReturn(tabs);
		when(categorizationElement.getCategorizations()).thenReturn(categorizations);
		final ViewModelContext vmc = mockViewModelContext(contextUsers);
		final VTViewTemplateProvider viewTemplateProvider = mock(VTViewTemplateProvider.class);
		final CompositeCategorySWTTabRenderer categorizatrionElementRenderer = new CompositeCategorySWTTabRenderer(
			categorization, vmc, reportService, emfFormsRendererFactory, viewTemplateProvider);
		categorizatrionElementRenderer.init();
		final Control render = categorizatrionElementRenderer.render(gridCell, shell);
		categorizatrionElementRenderer.finalizeRendering(shell);
		assertTrue(CTabFolder.class.isInstance(render));

		shell.dispose();
		assertThat("Some context user remains", contextUsers, not(hasItem(anything())));
	}

	@Test
	public void testCategorizationElementTreeRendererReadOnlyBehavior_ViewIsReadOnly() throws ECPRendererException {
		final VView view = VViewFactory.eINSTANCE.createView();
		view.setReadonly(true);

		final VCategorizationElement categorizationElement = VCategorizationFactory.eINSTANCE
			.createCategorizationElement();

		final VCategory category1 = VCategorizationFactory.eINSTANCE.createCategory();

		final VCategorization categorization = VCategorizationFactory.eINSTANCE.createCategorization();
		categorization.getCategorizations().add(category1);

		categorizationElement.getCategorizations().add(categorization);
		final VCategorization compositeCategory = VCategorizationFactory.eINSTANCE.createCategorization();

		categorizationElement.getCategorizations().add(compositeCategory);

		view.getChildren().add(categorizationElement);

		final Shell shell = new Shell();
		shell.setLayout(new FillLayout());
		final Player player = BowlingFactory.eINSTANCE.createPlayer();

		final ViewModelContext vmc = ViewModelContextFactory.INSTANCE.createViewModelContext(view, player);
		final ECPSWTView ecpSwtView = ECPSWTViewRenderer.INSTANCE.render(shell, vmc);
		final Tree tree = CategoryRendererTestHelper.getTree(ecpSwtView.getSWTControl());
		final Composite detailComposite = CategoryRendererTestHelper.getDetailComposite(ecpSwtView.getSWTControl());

		assertTrue(tree.isEnabled());
		assertTrue(detailComposite.isEnabled());
	}

	@Test
	public void testCategorizationElementTreeRendererReadOnlyBehavior_CategorizationIsReadOnly()
		throws ECPRendererException {
		final VView view = VViewFactory.eINSTANCE.createView();

		final VCategorizationElement categorizationElement = VCategorizationFactory.eINSTANCE
			.createCategorizationElement();
		categorizationElement.setReadonly(true);

		final VCategory category1 = VCategorizationFactory.eINSTANCE.createCategory();

		final VCategorization categorization = VCategorizationFactory.eINSTANCE.createCategorization();
		categorization.getCategorizations().add(category1);

		categorizationElement.getCategorizations().add(categorization);
		final VCategorization compositeCategory = VCategorizationFactory.eINSTANCE.createCategorization();

		categorizationElement.getCategorizations().add(compositeCategory);

		view.getChildren().add(categorizationElement);

		final Shell shell = new Shell();
		shell.setLayout(new FillLayout());
		final Player player = BowlingFactory.eINSTANCE.createPlayer();

		final ViewModelContext vmc = ViewModelContextFactory.INSTANCE.createViewModelContext(view, player);
		final ECPSWTView ecpSwtView = ECPSWTViewRenderer.INSTANCE.render(shell, vmc);
		final Tree tree = CategoryRendererTestHelper.getTree(ecpSwtView.getSWTControl());
		final Composite detailComposite = CategoryRendererTestHelper.getDetailComposite(ecpSwtView.getSWTControl());

		assertTrue(tree.isEnabled());
		assertTrue(detailComposite.isEnabled());
	}

	//
	// Test framework
	//

	ViewModelContext mockViewModelContext(Set<Object> users) {
		final ViewModelContext result = mock(ViewModelContext.class);
		doAnswer(invocation -> users.add(invocation.getArguments()[0])).when(result).addContextUser(any());
		doAnswer(invocation -> users.remove(invocation.getArguments()[0])).when(result).removeContextUser(any());
		return result;
	}

	EMFFormsRendererFactory mockRendererFactory(ReportService reportService) {
		final EMFFormsRendererFactory result = mock(EMFFormsRendererFactory.class);
		try {
			when(result.getRendererInstance(isA(VCategory.class), any()))
				.thenAnswer(invocation -> {
					final SWTCategoryRenderer renderer = new SWTCategoryRenderer(
						(VCategory) invocation.getArguments()[0],
						(ViewModelContext) invocation.getArguments()[1], reportService, result);
					renderer.init();
					return renderer;
				});
		} catch (final EMFFormsNoRendererException e) {
			e.printStackTrace();
			fail("Exception in mock set-up: " + e.getMessage());
		}

		return result;
	}
}
