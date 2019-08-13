/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.core.swt.renderer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.test.common.DefaultRealm;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.LabelAlignment;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.ecp.view.template.style.labelwidth.model.VTLabelWidthStyleProperty;
import org.eclipse.emf.ecp.view.template.style.labelwidth.model.VTLabelwidthFactory;
import org.eclipse.emf.ecp.view.test.common.swt.spi.SWTTestUtil;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.swt.common.test.AbstractControl_PTest;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
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

	@Test
	public void unsettable_readOnly()
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption, DatabindingFailedException {
		TestSimpleControlSWTRenderer.class.cast(getRenderer()).setUnsettable(true);
		final TestObservableValue mockedObservableValue = mock(TestObservableValue.class);
		when(mockedObservableValue.getRealm()).thenReturn(realm);
		final EObject mockedEObject = mock(EObject.class);
		when(mockedObservableValue.getObserved()).thenReturn(mockedEObject);
		when(getDatabindingService().getObservableValue(any(VDomainModelReference.class), any(EObject.class)))
			.thenReturn(
				mockedObservableValue);

		Mockito.when(getvControl().isEffectivelyReadonly()).thenReturn(true);

		final Control renderControl = renderControl(new SWTGridCell(0, 2, getRenderer()));
		getRenderer().finalizeRendering(getShell());

		final Button unset = SWTTestUtil.findControl(renderControl, 0, Button.class);
		assertFalse(unset.isVisible());
		assertTrue(GridData.class.cast(unset.getLayoutData()).exclude);
	}

	private class TestSimpleControlSWTRenderer extends SimpleControlSWTRenderer {
		private Boolean unsettable;

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
			return "I am unset text"; //$NON-NLS-1$
		}

		@Override
		protected Control createControl(Composite parent) throws DatabindingFailedException {
			return new Label(parent, SWT.NONE);
		}

		@Override
		protected boolean isUnsettable() throws DatabindingFailedException {
			if (unsettable == null) {
				return super.isUnsettable();
			}
			return unsettable;
		}

		void setUnsettable(boolean unsettable) {
			this.unsettable = unsettable;
		}
	}

}
