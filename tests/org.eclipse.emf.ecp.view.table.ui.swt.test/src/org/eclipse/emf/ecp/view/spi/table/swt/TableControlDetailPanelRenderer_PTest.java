/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.swt;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.ecp.view.test.common.swt.spi.DatabindingClassRunner;
import org.eclipse.emf.ecp.view.test.common.swt.spi.SWTViewTestHelper;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Unit tests for the {@link TableControlDetailPanelRenderer}.
 *
 * @author Eugen Neufeld
 *
 */
@RunWith(DatabindingClassRunner.class)
public class TableControlDetailPanelRenderer_PTest {

	private Shell shell;

	@Before
	public void setUp() {
		shell = SWTViewTestHelper.createShell();
	}

	@After
	public void tearDown() {
		shell.dispose();
	}

	@Test
	public void testGetView() {
		final TableControlDetailPanelRenderer renderer = new TableControlDetailPanelRenderer(mock(VTableControl.class),
			mock(ViewModelContext.class), mock(ReportService.class), mock(EMFFormsDatabindingEMF.class),
			mock(EMFFormsLabelProvider.class), mock(VTViewTemplateProvider.class), mock(ImageRegistryService.class),
			mock(EMFFormsEditSupport.class));
		final EAttribute eAttribute = EcoreFactory.eINSTANCE.createEAttribute();
		final EReference eReference = EcoreFactory.eINSTANCE.createEReference();
		final VView viewAttribute1 = renderer.getView(eAttribute);
		final VView viewReference = renderer.getView(eReference);
		assertFalse(EcoreUtil.equals(viewAttribute1, viewReference));
		final VView viewAttribute2 = renderer.getView(eAttribute);
		assertTrue(EcoreUtil.equals(viewAttribute1, viewAttribute2));
	}

	/**
	 * If the detail view is not configured as readonly, the table sets it to read only when the table is disabled.
	 * Furthermore, the detail's readonly state is resetted when the table is enabled again.
	 */
	@Test
	public void testEnabledChangeAppliedToDetailView_OriginalReadonlyFalse() {
		final VTableControl control = mock(VTableControl.class);
		final VView detailView = VViewFactory.eINSTANCE.createView();
		detailView.setReadonly(false);
		when(control.getDetailView()).thenReturn(detailView);
		when(control.isEffectivelyEnabled()).thenReturn(true);
		when(control.isEffectivelyReadonly()).thenReturn(false);

		final EAttribute eAttribute = EcoreFactory.eINSTANCE.createEAttribute();
		final ViewModelContext context = mockViewModelContext(detailView, eAttribute);

		final TableControlDetailPanelRenderer renderer = new TableControlDetailPanelRenderer(control,
			context, mock(ReportService.class), mock(EMFFormsDatabindingEMF.class),
			mock(EMFFormsLabelProvider.class), mock(VTViewTemplateProvider.class), mock(ImageRegistryService.class),
			mock(EMFFormsEditSupport.class));

		renderer.renderSelectedObject(shell, eAttribute);

		assertFalse(detailView.isReadonly());
		when(control.isEffectivelyEnabled()).thenReturn(false);
		renderer.applyEnable();
		assertTrue(detailView.isReadonly());
		when(control.isEffectivelyEnabled()).thenReturn(true);
		renderer.applyEnable();
		assertFalse(detailView.isReadonly());
	}

	/** If the detail view is configured as readonly, it must always stay readonly. */
	@Test
	public void testEnabledChangeAppliedToDetailView_OriginalReadonlyTrue() {
		final VTableControl control = mock(VTableControl.class);
		final VView detailView = VViewFactory.eINSTANCE.createView();
		detailView.setReadonly(true);
		when(control.getDetailView()).thenReturn(detailView);
		when(control.isEffectivelyEnabled()).thenReturn(false);
		when(control.isEffectivelyReadonly()).thenReturn(false);

		final EAttribute eAttribute = EcoreFactory.eINSTANCE.createEAttribute();
		final ViewModelContext context = mockViewModelContext(detailView, eAttribute);

		final TableControlDetailPanelRenderer renderer = new TableControlDetailPanelRenderer(control,
			context, mock(ReportService.class), mock(EMFFormsDatabindingEMF.class),
			mock(EMFFormsLabelProvider.class), mock(VTViewTemplateProvider.class), mock(ImageRegistryService.class),
			mock(EMFFormsEditSupport.class));

		renderer.renderSelectedObject(shell, eAttribute);

		assertTrue(detailView.isReadonly());
		when(control.isEffectivelyEnabled()).thenReturn(true);
		renderer.applyEnable();
		assertTrue(detailView.isReadonly());
	}

	/**
	 * If the table is read only, enabling it must not set the detail's readonly flag to false.
	 */
	@Test
	public void testEnabledChangeAppliedToDetailView_DoesNotOverrideReadonlyTrue() {
		final VTableControl control = mock(VTableControl.class);
		final VView detailView = VViewFactory.eINSTANCE.createView();
		detailView.setReadonly(false);
		when(control.getDetailView()).thenReturn(detailView);
		when(control.isEffectivelyEnabled()).thenReturn(false);
		when(control.isEffectivelyReadonly()).thenReturn(true);

		final EAttribute eAttribute = EcoreFactory.eINSTANCE.createEAttribute();
		final ViewModelContext context = mockViewModelContext(detailView, eAttribute);

		final TableControlDetailPanelRenderer renderer = new TableControlDetailPanelRenderer(control,
			context, mock(ReportService.class), mock(EMFFormsDatabindingEMF.class),
			mock(EMFFormsLabelProvider.class), mock(VTViewTemplateProvider.class), mock(ImageRegistryService.class),
			mock(EMFFormsEditSupport.class));

		renderer.renderSelectedObject(shell, eAttribute);

		assertTrue(detailView.isReadonly());
		when(control.isEffectivelyEnabled()).thenReturn(true);
		renderer.applyEnable();
		assertTrue(detailView.isReadonly());
	}

	/**
	 * If the detail view is not configured as readonly, the table sets it to read only when the table is set to
	 * readonly.
	 */
	@Test
	public void testReadonlyChangeAppliedToDetailView_OriginalReadonlyFalse() {
		final VTableControl control = mock(VTableControl.class);
		final VView detailView = VViewFactory.eINSTANCE.createView();
		detailView.setReadonly(false);
		when(control.getDetailView()).thenReturn(detailView);
		when(control.isEffectivelyEnabled()).thenReturn(true);
		when(control.isEffectivelyReadonly()).thenReturn(false);

		final EAttribute eAttribute = EcoreFactory.eINSTANCE.createEAttribute();
		final ViewModelContext context = mockViewModelContext(detailView, eAttribute);

		final TableControlDetailPanelRenderer renderer = new TableControlDetailPanelRenderer(control,
			context, mock(ReportService.class), mock(EMFFormsDatabindingEMF.class),
			mock(EMFFormsLabelProvider.class), mock(VTViewTemplateProvider.class), mock(ImageRegistryService.class),
			mock(EMFFormsEditSupport.class));

		renderer.renderSelectedObject(shell, eAttribute);

		assertFalse(detailView.isReadonly());
		when(control.isEffectivelyReadonly()).thenReturn(true);
		renderer.applyReadOnly();
		assertTrue(detailView.isReadonly());
	}

	/** If the detail view is configured as readonly, it must always stay readonly. */
	@Test
	public void testReadonlyChangeAppliedToDetailView_OriginalReadonlyTrue() {
		final VTableControl control = mock(VTableControl.class);
		final VView detailView = VViewFactory.eINSTANCE.createView();
		detailView.setReadonly(true);
		when(control.getDetailView()).thenReturn(detailView);
		when(control.isEffectivelyEnabled()).thenReturn(true);
		when(control.isEffectivelyReadonly()).thenReturn(true);

		final EAttribute eAttribute = EcoreFactory.eINSTANCE.createEAttribute();
		final ViewModelContext context = mockViewModelContext(detailView, eAttribute);

		final TableControlDetailPanelRenderer renderer = new TableControlDetailPanelRenderer(control,
			context, mock(ReportService.class), mock(EMFFormsDatabindingEMF.class),
			mock(EMFFormsLabelProvider.class), mock(VTViewTemplateProvider.class), mock(ImageRegistryService.class),
			mock(EMFFormsEditSupport.class));

		renderer.renderSelectedObject(shell, eAttribute);

		assertTrue(detailView.isReadonly());
		when(control.isEffectivelyReadonly()).thenReturn(false);
		renderer.applyEnable();
		assertTrue(detailView.isReadonly());
	}

	/**
	 * If the table is disabled, setting its read only flag to false, must not set the detail's readonly flag to false.
	 */
	@Test
	public void testReadonlyChangeAppliedToDetailView_DoesNotOverrideEnabledFalse() {
		final VTableControl control = mock(VTableControl.class);
		final VView detailView = VViewFactory.eINSTANCE.createView();
		detailView.setReadonly(false);
		when(control.getDetailView()).thenReturn(detailView);
		when(control.isEffectivelyEnabled()).thenReturn(false);
		when(control.isEffectivelyReadonly()).thenReturn(true);

		final EAttribute eAttribute = EcoreFactory.eINSTANCE.createEAttribute();
		final ViewModelContext context = mockViewModelContext(detailView, eAttribute);

		final TableControlDetailPanelRenderer renderer = new TableControlDetailPanelRenderer(control,
			context, mock(ReportService.class), mock(EMFFormsDatabindingEMF.class),
			mock(EMFFormsLabelProvider.class), mock(VTViewTemplateProvider.class), mock(ImageRegistryService.class),
			mock(EMFFormsEditSupport.class));

		renderer.renderSelectedObject(shell, eAttribute);

		assertTrue(detailView.isReadonly());
		when(control.isEffectivelyReadonly()).thenReturn(false);
		renderer.applyEnable();
		assertTrue(detailView.isReadonly());
	}

	private ViewModelContext mockViewModelContext(final VView detailView, final EObject domainObject) {
		final ViewModelContext context = mock(ViewModelContext.class);
		final ViewModelContext childContext = mock(ViewModelContext.class);
		when(childContext.getDomainModel()).thenReturn(domainObject);
		when(childContext.getViewModel()).thenReturn(detailView);

		when(context.getChildContext(any(EObject.class), any(VElement.class), any(VView.class)))
			.thenReturn(childContext);
		return context;
	}
}
