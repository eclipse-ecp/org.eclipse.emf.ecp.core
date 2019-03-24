/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.core.swt.renderer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.core.databinding.property.Properties;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.test.common.DefaultRealm;
import org.eclipse.emf.ecp.view.core.swt.test.model.SimpleTestObject;
import org.eclipse.emf.ecp.view.core.swt.test.model.TestEnum;
import org.eclipse.emf.ecp.view.core.swt.test.model.TestFactory;
import org.eclipse.emf.ecp.view.core.swt.test.model.TestPackage;
import org.eclipse.emf.ecp.view.core.swt.tests.ObservingWritableValue;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.core.services.label.NoLabelFoundException;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.swt.common.test.AbstractControl_PTest;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

/**
 * Plugin test for {@link EnumComboViewerSWTRenderer}.
 *
 * @author Lucas Koehler
 *
 */
public class EnumComboViewerRenderer_PTest extends AbstractControl_PTest<VControl> {

	private EMFFormsEditSupport editSupport;
	private DefaultRealm realm;

	@Before
	public void before() throws DatabindingFailedException {
		realm = new DefaultRealm();
		final ReportService reportService = mock(ReportService.class);
		setDatabindingService(mock(EMFFormsDatabinding.class));
		setLabelProvider(mock(EMFFormsLabelProvider.class));
		setTemplateProvider(mock(VTViewTemplateProvider.class));
		editSupport = mock(EMFFormsEditSupport.class);

		setup();
		setRenderer(new EnumComboViewerSWTRenderer(getvControl(), getContext(), reportService, getDatabindingService(),
			getLabelProvider(),
			getTemplateProvider(), editSupport));
		getRenderer().init();
	}

	@After
	public void testTearDown() {
		realm.dispose();
		dispose();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws DatabindingFailedException
	 *
	 * @see org.eclipse.emfforms.swt.common.test.AbstractControl_PTest.eclipse.emf.ecp.view.internal.core.swt.renderer.AbstractControl_PTest#mockControl()
	 */
	@Override
	protected void mockControl() throws DatabindingFailedException {
		final SimpleTestObject eObject = TestFactory.eINSTANCE.createSimpleTestObject();
		super.mockControl(eObject, TestPackage.eINSTANCE.getSimpleTestObject_MyEnum());
	}

	@Test
	public void testDatabindingServiceUsageInitialBinding() throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption, DatabindingFailedException {
		final TestEnum initialValue = TestEnum.B;

		final ObservingWritableValue mockedObservable = new ObservingWritableValue(realm, initialValue,
			TestPackage.eINSTANCE.getSimpleTestObject_MyEnum());

		when(
			editSupport.getText(any(VDomainModelReference.class), any(EObject.class),
				Matchers.eq(mockedObservable.getValue())))
					.thenReturn(mockedObservable.getValue().toString());

		final Combo combo = setUpDatabindingTest(mockedObservable);
		assertEquals(initialValue.getName(), combo.getText());

	}

	@Test
	@SuppressWarnings("unchecked")
	public void testDatabindingServiceUsageChangeObservable() throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption, DatabindingFailedException {
		final TestEnum initialValue = TestEnum.B;
		final TestEnum changedValue = TestEnum.C;
		final ObservingWritableValue mockedObservable = new ObservingWritableValue(realm, initialValue,
			TestPackage.eINSTANCE.getSimpleTestObject_MyEnum());
		when(
			editSupport.getText(any(VDomainModelReference.class), any(EObject.class),
				Matchers.same(initialValue)))
					.thenReturn(initialValue.toString());

		when(
			editSupport.getText(any(VDomainModelReference.class), any(EObject.class),
				Matchers.same(changedValue)))
					.thenReturn(changedValue.toString());

		final Combo combo = setUpDatabindingTest(mockedObservable);
		mockedObservable.setValue(changedValue);

		assertEquals(changedValue.getName(), combo.getText());

	}

	@Test
	public void testDatabindingServiceUsageChangeControl() throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption, DatabindingFailedException {
		final TestEnum initialValue = TestEnum.B;
		final TestEnum changedValue = TestEnum.C;
		final ObservingWritableValue mockedObservable = new ObservingWritableValue(realm, initialValue,
			TestPackage.eINSTANCE.getSimpleTestObject_MyEnum());

		final Combo combo = setUpDatabindingTest(mockedObservable);
		combo.select(2);
		combo.notifyListeners(SWT.Selection, new Event());

		assertEquals(changedValue.getName(), ((TestEnum) mockedObservable.getValue()).getName());

	}

	/**
	 * Universal set up stuff for the data binding test cases.
	 *
	 * @param mockedObservable
	 * @return
	 * @throws NoRendererFoundException
	 * @throws NoPropertyDescriptorFoundExeption
	 * @throws DatabindingFailedException
	 */
	private Combo setUpDatabindingTest(final ObservingWritableValue mockedObservable) throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption, DatabindingFailedException {
		mockDatabindingIsSettableAndChangeable();
		when(getDatabindingService().getObservableValue(any(VDomainModelReference.class), any(EObject.class)))
			.thenReturn(
				mockedObservable);
		when(getDatabindingService().getValueProperty(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
			Properties.selfValue(mockedObservable.getValueType()));

		final Control renderControl = renderControl(new SWTGridCell(0, 2, getRenderer()));

		final Combo combo = (Combo) renderControl;
		return combo;
	}

	/**
	 * Tests whether the {@link EMFFormsLabelProvider} is used to get the labels of the control.
	 *
	 * @throws NoRendererFoundException
	 * @throws NoPropertyDescriptorFoundExeption
	 * @throws DatabindingFailedException
	 * @throws NoLabelFoundException
	 */
	@Test
	public void testLabelServiceUsage() throws NoRendererFoundException, NoPropertyDescriptorFoundExeption,
		DatabindingFailedException, NoLabelFoundException {
		labelServiceUsage();
	}

	@Test
	public void testEffectivelyReadOnlyDeactivatesControl()
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption, DatabindingFailedException {
		final ObservingWritableValue mockedObservable = new ObservingWritableValue(realm, TestEnum.B,
			TestPackage.eINSTANCE.getSimpleTestObject_MyEnum());
		when(getDatabindingService().getObservableValue(any(VDomainModelReference.class), any(EObject.class)))
			.thenReturn(mockedObservable);
		when(getDatabindingService().getValueProperty(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
			Properties.selfValue(mockedObservable.getValueType()));

		when(getvControl().isEffectivelyReadonly()).thenReturn(true);
		final Control renderControl = renderControl(new SWTGridCell(0, 2, getRenderer()));
		getRenderer().finalizeRendering(getShell());
		assertFalse(renderControl.isEnabled());
	}
}
