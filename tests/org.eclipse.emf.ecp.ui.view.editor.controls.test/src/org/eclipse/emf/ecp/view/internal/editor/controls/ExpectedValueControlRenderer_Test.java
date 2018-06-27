/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.controls;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.test.common.DefaultRealm;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link ExpectedValueControlRenderer}.
 *
 * @author Lucas Koehler
 *
 */
public class ExpectedValueControlRenderer_Test {
	private static final String TEST_STRING = "TestString";

	private TestExpectedValueControlRenderer renderer;
	private VControl vControl;
	private ReportService reportService;
	private ViewModelContext viewContext;
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
		renderer = spy(new TestExpectedValueControlRenderer(vControl, viewContext, reportService, databinding,
			labelProvider, templateProvider));
	}

	@After
	public void tearDown() {
		realm.dispose();
	}

	@Test
	public void getSelectedObject_nullAttribute() {
		final Object selectedObject = renderer.getSelectedObject(null);

		assertNull(selectedObject);
		verify(renderer).showError(any(Shell.class), eq("Missing Attribute"),
			eq("Please select an attribute before attempting to select its value."));
	}

	@Test
	public void getSelectedObject_primitiveAttribute() {
		final Object object = renderer.getSelectedObject(EcorePackage.Literals.ENAMED_ELEMENT__NAME);

		assertTrue(object instanceof String);
		assertEquals(TEST_STRING, object);
	}

	public class TestExpectedValueControlRenderer extends ExpectedValueControlRenderer {

		TestExpectedValueControlRenderer(VControl vElement, ViewModelContext viewContext,
			ReportService reportService, EMFFormsDatabinding databindingService, EMFFormsLabelProvider labelProvider,
			VTViewTemplateProvider viewTemplateProvider) {
			super(vElement, viewContext, reportService, databindingService, labelProvider, viewTemplateProvider);
		}

		@Override
		protected void onSelectButton(Label text) {
			// Do nothing
		}

		@Override
		protected void showError(Shell shell, String title, String description) {
			// Do nothing
		}

		@Override
		Object promptForValue(Shell shell, Object object, Class<?> attributeClazz) {
			return TEST_STRING;
		}
	}
}
