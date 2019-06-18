/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
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

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.test.common.DefaultRealm;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.rule.model.LeafCondition;
import org.eclipse.emf.ecp.view.spi.rule.model.RuleFactory;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link LeafConditionControlRenderer}.
 *
 * @author Lucas Koehler
 *
 */
public class LeafConditionControlRenderer_Test {
	private TestLeafConditionControlRenderer renderer;
	private VControl vControl;
	private ReportService reportService;
	private ViewModelContext viewContext;
	private LeafCondition leafCondition;
	private DefaultRealm realm;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		realm = new DefaultRealm();
		reportService = mock(ReportService.class);
		vControl = VViewFactory.eINSTANCE.createControl();
		viewContext = mock(ViewModelContext.class);
		final EMFFormsDatabinding databinding = mock(EMFFormsDatabinding.class);
		final EMFFormsLabelProvider labelProvider = mock(EMFFormsLabelProvider.class);
		final VTViewTemplateProvider templateProvider = mock(VTViewTemplateProvider.class);
		renderer = spy(new TestLeafConditionControlRenderer(vControl, viewContext, reportService, databinding,
			labelProvider, templateProvider));
		leafCondition = RuleFactory.eINSTANCE.createLeafCondition();
	}

	@After
	public void tearDown() {
		realm.dispose();
	}

	@Test
	public void onSelectButton_valueDmrNullDomainModelFeature() {
		final Label label = mock(Label.class);
		final VFeaturePathDomainModelReference dmr = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		leafCondition.setDomainModelReference(dmr);
		dmr.setDomainModelEFeature(VViewPackage.Literals.ELEMENT__LABEL);
		final VFeaturePathDomainModelReference valueDmr = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		// do not set a domain model e feature in the value dmr
		leafCondition.setValueDomainModelReference(valueDmr);

		renderer.onSelectButton(label);

		verify(renderer).showError(any(Shell.class), eq("No value selected"), //$NON-NLS-1$
			eq("Please set a value to the Domain Model Reference first.")); //$NON-NLS-1$
	}

	@Test
	public void onSelectButton_conditionDmrNull() {
		final Label label = mock(Label.class);

		renderer.onSelectButton(label);

		verify(renderer).showError(any(Shell.class), eq("No Feature Path Domain Model Reference found"), //$NON-NLS-1$
			eq("A Feature Path Domain Model Reference needs to be added to the condition first.")); //$NON-NLS-1$
	}

	public class TestLeafConditionControlRenderer extends LeafConditionControlRenderer {

		/**
		 * @param vElement
		 * @param viewContext
		 * @param reportService
		 */
		TestLeafConditionControlRenderer(VControl vElement, ViewModelContext viewContext, ReportService reportService,
			EMFFormsDatabinding databindingService, EMFFormsLabelProvider labelProvider,
			VTViewTemplateProvider viewTemplateProvider) {
			super(vElement, viewContext, reportService, databindingService, labelProvider, viewTemplateProvider);
		}

		@Override
		protected void showError(Shell shell, String title, String description) {
			// Do nothing
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.emf.ecp.view.internal.editor.controls.ExpectedValueControlRenderer#getObservedEObject()
		 */
		@Override
		protected EObject getObservedEObject() throws DatabindingFailedException {
			return leafCondition;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.emf.ecp.view.internal.editor.controls.ExpectedValueControlRenderer#promptForValue(org.eclipse.swt.widgets.Shell,
		 *      java.lang.Object, java.lang.Class)
		 */
		@Override
		Object promptForValue(Shell shell, Object object, Class<?> attributeClazz) {
			return object;
		}

	}
}
