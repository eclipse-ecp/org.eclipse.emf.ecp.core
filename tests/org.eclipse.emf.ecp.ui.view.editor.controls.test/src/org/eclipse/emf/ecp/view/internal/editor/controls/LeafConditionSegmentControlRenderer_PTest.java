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
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.controls;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecp.test.common.TestUtil;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.rule.model.EnableRule;
import org.eclipse.emf.ecp.view.spi.rule.model.LeafCondition;
import org.eclipse.emf.ecp.view.spi.rule.model.RuleFactory;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link LeafConditionSegmentControlRenderer}.
 *
 * @author Lucas Koehler
 *
 */
public class LeafConditionSegmentControlRenderer_PTest {

	private LeafCondition leafCondition;
	private Object selectedObject;
	private TestLeafConditionSegmentControlRenderer renderer;
	private EMFFormsDatabindingEMF databinding;
	private Shell shell;
	private Label label;
	private ReportService reportService;

	@Before
	public void setUp() {
		selectedObject = new Object();
		leafCondition = RuleFactory.eINSTANCE.createLeafCondition();
		final EnableRule rule = RuleFactory.eINSTANCE.createEnableRule();
		rule.setCondition(leafCondition);
		final VControl control = VViewFactory.eINSTANCE.createControl();
		control.getAttachments().add(rule);
		final VView view = VViewFactory.eINSTANCE.createView();
		view.setRootEClass(EcorePackage.Literals.ECLASS);
		view.getChildren().add(control);
		// Add leaf condition to resource with editing domain
		final Resource resource = TestUtil.createResourceWithEditingDomain();
		resource.getContents().add(view);

		reportService = mock(ReportService.class);
		final VControl vControl = VViewFactory.eINSTANCE.createControl();
		final ViewModelContext viewContext = mock(ViewModelContext.class);
		databinding = mock(EMFFormsDatabindingEMF.class);
		final EMFFormsLabelProvider labelProvider = mock(EMFFormsLabelProvider.class);
		final VTViewTemplateProvider templateProvider = mock(VTViewTemplateProvider.class);

		renderer = spy(new TestLeafConditionSegmentControlRenderer(vControl, viewContext, reportService, databinding,
			labelProvider, templateProvider));
		shell = new Shell();
		label = new Label(shell, SWT.NONE);
	}

	@After
	public void tearDown() {
		shell.dispose();
	}

	@Test
	public void onSelectButton_noDmr() {
		renderer.onSelectButton(label);

		verify(renderer).showError(shell, "No Domain Model Reference found",
			"A Domain Model Reference needs to be added to the condition first.");
		assertNull(leafCondition.getExpectedValue());
	}

	@Test
	public void onSelectButton_dmrDatabindingFailed() throws DatabindingFailedException {
		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createDomainModelReference();
		leafCondition.setDomainModelReference(dmr);
		when(databinding.getValueProperty(same(dmr), any(EClass.class)))
			.thenThrow(mock(DatabindingFailedException.class));

		renderer.onSelectButton(label);

		verify(reportService).report(any());
		assertNull(leafCondition.getExpectedValue());
	}

	@Test
	public void onSelectButton_dmrNotPointingToEAttribute() throws DatabindingFailedException {
		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createDomainModelReference();
		leafCondition.setDomainModelReference(dmr);
		final IEMFValueProperty valueProperty = mock(IEMFValueProperty.class);
		when(valueProperty.getStructuralFeature()).thenReturn(mock(EReference.class));
		when(databinding.getValueProperty(same(dmr), any(EClass.class))).thenReturn(valueProperty);

		renderer.onSelectButton(label);

		verify(renderer).showError(shell, "No EAttribute selected", //$NON-NLS-1$
			"The condition's domain model reference must point to an EAttribute."); //$NON-NLS-1$
		assertNull(leafCondition.getExpectedValue());
	}

	@Test
	public void onSelectButton() throws DatabindingFailedException {
		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createDomainModelReference();
		leafCondition.setDomainModelReference(dmr);
		final VDomainModelReference valueDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		leafCondition.setValueDomainModelReference(valueDmr);
		final IEMFValueProperty valueProperty = mock(IEMFValueProperty.class);
		final EAttribute eAttribute = mock(EAttribute.class);
		when(valueProperty.getStructuralFeature()).thenReturn(eAttribute);
		when(databinding.getValueProperty(same(dmr), any(EClass.class))).thenReturn(valueProperty);

		renderer.onSelectButton(label);

		verify(renderer).showInfo(same(shell), eq("Legacy Value DMR will be removed"), any());
		assertNull(leafCondition.getValueDomainModelReference());
		assertNotNull(leafCondition.getExpectedValue());
		final EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(leafCondition);
		final CommandStack commandStack = domain.getCommandStack();
		assertTrue(commandStack.getMostRecentCommand() instanceof SetCommand);
		final SetCommand setCommand = (SetCommand) commandStack.getMostRecentCommand();
		assertSame("SetCommand should have selected object as value.", selectedObject, setCommand.getValue());
		assertEquals(selectedObject.toString(), label.getText());
	}

	// Must be public to allow spying/mocking with Mockito
	public class TestLeafConditionSegmentControlRenderer extends LeafConditionSegmentControlRenderer {

		/**
		 * @param vElement
		 * @param viewContext
		 * @param reportService
		 * @param databindingService
		 * @param labelProvider
		 * @param viewTemplateProvider
		 */
		public TestLeafConditionSegmentControlRenderer(VControl vElement, ViewModelContext viewContext,
			ReportService reportService, EMFFormsDatabindingEMF databindingService, EMFFormsLabelProvider labelProvider,
			VTViewTemplateProvider viewTemplateProvider) {
			super(vElement, viewContext, reportService, databindingService, labelProvider, viewTemplateProvider);
		}

		@Override
		protected void showError(Shell shell, String title, String description) {
			// do nothing. Mockito is used to verify whether this is called
		}

		@Override
		protected void showInfo(Shell parent, String title, String message) {
			// do nothing. Mockito is used to verify whether this is called
		}

		@Override
		protected EObject getObservedEObject() throws DatabindingFailedException {
			return leafCondition;
		}

		@Override
		protected Object getSelectedObject(EAttribute attribute) {
			return selectedObject;
		}
	}
}
