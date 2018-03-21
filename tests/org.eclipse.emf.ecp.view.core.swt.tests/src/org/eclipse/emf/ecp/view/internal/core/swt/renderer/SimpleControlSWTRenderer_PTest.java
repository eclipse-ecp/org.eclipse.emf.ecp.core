/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.core.swt.renderer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

import java.util.Collections;
import java.util.Set;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.test.common.DefaultRealm;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.LabelAlignment;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.ecp.view.template.style.labelwidth.model.VTLabelWidthStyleProperty;
import org.eclipse.emf.ecp.view.template.style.labelwidth.model.VTLabelwidthFactory;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.swt.common.test.AbstractControl_PTest;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class SimpleControlSWTRenderer_PTest extends AbstractControl_PTest<VControl> {

	private DefaultRealm realm;

	@Override
	protected void mockControl() throws DatabindingFailedException {
		final EStructuralFeature eObject = EcoreFactory.eINSTANCE.createEAttribute();
		final EStructuralFeature eStructuralFeature = EcorePackage.eINSTANCE.getENamedElement_Name();
		super.mockControl(eObject, eStructuralFeature);
	}

	@Before
	public void before() throws DatabindingFailedException {
		realm = new DefaultRealm();

		final ReportService reportService = mock(ReportService.class);
		setDatabindingService(mock(EMFFormsDatabinding.class));
		setLabelProvider(mock(EMFFormsLabelProvider.class));
		setTemplateProvider(mock(VTViewTemplateProvider.class));

		setup();

		setRenderer(new TestSimpleControlSWTRenderer(
			getvControl(),
			getContext(),
			reportService,
			getDatabindingService(),
			getLabelProvider(),
			getTemplateProvider()));

		getRenderer().init();
	}

	@After
	public void testTearDown() {
		realm.dispose();
		dispose();
	}

	@Test
	public void labelGridCellDefault() {
		/* setup */
		Mockito.when(getvControl().getLabelAlignment()).thenReturn(LabelAlignment.DEFAULT);

		/* act */
		final SWTGridCell labelCell = getRenderer().getGridDescription(null).getGrid().get(0);

		/* assert */
		assertNull(labelCell.getPreferredSize());
	}

	@Test
	public void labelGridCellTemplateWithWidth() {
		/* setup */
		Mockito.when(getvControl().getLabelAlignment()).thenReturn(LabelAlignment.DEFAULT);

		final VTLabelWidthStyleProperty property = VTLabelwidthFactory.eINSTANCE.createLabelWidthStyleProperty();
		property.setWidth(11);
		final Set<VTStyleProperty> properties = Collections.<VTStyleProperty> singleton(property);
		Mockito.when(getTemplateProvider().getStyleProperties(getvControl(), getContext())).thenReturn(properties);

		/* act */
		final SWTGridCell labelCell = getRenderer().getGridDescription(null).getGrid().get(0);

		/* assert */
		assertEquals(11, labelCell.getPreferredSize().x);
		assertEquals(SWT.DEFAULT, labelCell.getPreferredSize().y);
	}

	@Test
	public void labelGridCellTemplateWithoutWidth() {
		/* setup */
		Mockito.when(getvControl().getLabelAlignment()).thenReturn(LabelAlignment.DEFAULT);

		final VTLabelWidthStyleProperty property = VTLabelwidthFactory.eINSTANCE.createLabelWidthStyleProperty();
		property.setWidth(11);
		property.unsetWidth();
		final Set<VTStyleProperty> properties = Collections.<VTStyleProperty> singleton(property);
		Mockito.when(getTemplateProvider().getStyleProperties(getvControl(), getContext())).thenReturn(properties);

		/* act */
		final SWTGridCell labelCell = getRenderer().getGridDescription(null).getGrid().get(0);

		/* assert */
		assertNull(labelCell.getPreferredSize());
	}

	private class TestSimpleControlSWTRenderer extends SimpleControlSWTRenderer {

		TestSimpleControlSWTRenderer(
			VControl vElement,
			ViewModelContext viewContext,
			ReportService reportService,
			EMFFormsDatabinding emfFormsDatabinding,
			EMFFormsLabelProvider emfFormsLabelProvider,
			VTViewTemplateProvider vtViewTemplateProvider) {
			super(
				vElement,
				viewContext,
				reportService,
				emfFormsDatabinding,
				emfFormsLabelProvider,
				vtViewTemplateProvider);
		}

		@Override
		protected String getUnsetText() {
			return "I am unset text";
		}

		@Override
		protected Control createControl(Composite parent) throws DatabindingFailedException {
			return new Label(parent, SWT.NONE);
		}

	}

}
