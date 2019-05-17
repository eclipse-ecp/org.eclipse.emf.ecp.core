/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emf.ecp.view.internal.core.swt.renderer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.Properties;
import org.eclipse.emf.databinding.EMFProperties;
import org.eclipse.emf.databinding.edit.EMFEditProperties;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.test.common.DefaultRealm;
import org.eclipse.emf.ecp.view.core.swt.test.model.SimpleTestObject;
import org.eclipse.emf.ecp.view.core.swt.test.model.TestEnum;
import org.eclipse.emf.ecp.view.core.swt.test.model.TestFactory;
import org.eclipse.emf.ecp.view.core.swt.test.model.TestPackage;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.ecp.view.test.common.swt.spi.SWTTestUtil;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
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
@SuppressWarnings({ "rawtypes", "unchecked" })
public class EnumComboViewerRenderer_PTest extends AbstractControl_PTest<VControl> {

	private EMFFormsEditSupport editSupport;
	private DefaultRealm realm;
	private SimpleTestObject domainObject;
	private IObservableValue observableValue;

	@Before
	public void before() throws DatabindingFailedException {
		realm = new DefaultRealm();
		final ReportService reportService = mock(ReportService.class);
		setDatabindingService(mock(EMFFormsDatabinding.class));
		setLabelProvider(mock(EMFFormsLabelProvider.class));
		setTemplateProvider(mock(VTViewTemplateProvider.class));
		editSupport = mock(EMFFormsEditSupport.class);

		domainObject = TestFactory.eINSTANCE.createSimpleTestObject();

		setup();

		final EditingDomain editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(domainObject);
		observableValue = EMFEditProperties
			.value(editingDomain, TestPackage.Literals.SIMPLE_TEST_OBJECT__MY_ENUM).observe(domainObject);

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

	@Override
	protected void mockControl() throws DatabindingFailedException {
		super.mockControl(domainObject, TestPackage.eINSTANCE.getSimpleTestObject_MyEnum());
	}

	@Test
	public void testDatabindingServiceUsageInitialBinding() throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption, DatabindingFailedException {
		final TestEnum initialValue = TestEnum.C;
		observableValue.setValue(initialValue);

		when(
			editSupport.getText(any(VDomainModelReference.class), any(EObject.class),
				Matchers.eq(observableValue.getValue())))
					.thenReturn(observableValue.getValue().toString());

		final Combo combo = setUpDatabindingTest(observableValue);
		assertEquals(initialValue.getName(), combo.getText());

	}

	@Test
	public void testDatabindingServiceUsageChangeObservable() throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption, DatabindingFailedException {
		final TestEnum initialValue = TestEnum.B;
		final TestEnum changedValue = TestEnum.C;
		observableValue.setValue(initialValue);

		when(
			editSupport.getText(any(VDomainModelReference.class), any(EObject.class),
				Matchers.same(initialValue)))
					.thenReturn(initialValue.toString());

		when(
			editSupport.getText(any(VDomainModelReference.class), any(EObject.class),
				Matchers.same(changedValue)))
					.thenReturn(changedValue.toString());

		final Combo combo = setUpDatabindingTest(observableValue);
		observableValue.setValue(changedValue);

		assertEquals(changedValue.getName(), combo.getText());

	}

	@Test
	public void testDatabindingServiceUsageChangeControl() throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption, DatabindingFailedException {
		final TestEnum changedValue = TestEnum.C;

		domainObject.setMyEnum(TestEnum.B);
		final Combo combo = setUpDatabindingTest(observableValue);
		combo.select(1); // TestEnum.C
		combo.notifyListeners(SWT.Selection, new Event());
		SWTTestUtil.waitForUIThread();

		assertEquals(changedValue.getName(), ((TestEnum) observableValue.getValue()).getName());

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
	private Combo setUpDatabindingTest(final IObservableValue<?> mockedObservable) throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption, DatabindingFailedException {
		mockDatabindingIsSettableAndChangeable();
		when(getDatabindingService().getObservableValue(any(VDomainModelReference.class), any(EObject.class)))
			.thenReturn(
				mockedObservable);
		when(getDatabindingService().getValueProperty(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
			EMFProperties.value((EStructuralFeature) mockedObservable.getValueType()));

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
		observableValue.setValue(TestEnum.B);
		when(getDatabindingService().getObservableValue(any(VDomainModelReference.class), any(EObject.class)))
			.thenReturn(observableValue);
		when(getDatabindingService().getValueProperty(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
			Properties.selfValue(observableValue.getValueType()));

		when(getvControl().isEffectivelyReadonly()).thenReturn(true);
		final Control renderControl = renderControl(new SWTGridCell(0, 2, getRenderer()));
		getRenderer().finalizeRendering(getShell());
		assertFalse(renderControl.isEnabled());
	}

	/**
	 * Tests that the renderer only shows enum literals which fulfill both conditions:
	 * <ul>
	 * <li>they are returned by the item descriptor</li>
	 * <li>they are not filtered out by the custom isInputtable annotation</li>
	 * </ul>
	 *
	 * @throws DatabindingFailedException
	 */
	@Test
	public void availableEnumValues() throws DatabindingFailedException {
		when(getDatabindingService().getObservableValue(any(VDomainModelReference.class), any(EObject.class)))
			.thenReturn(observableValue);
		final EnumComboViewerSWTRenderer enumRenderer = (EnumComboViewerSWTRenderer) getRenderer();
		final Collection<?> choices = enumRenderer.getAvailableChoicesValue().getValue();

		// TestEnum.A is filtered by annotation and TestEnum.D is filtered by the property descriptor
		assertEquals(2, choices.size());
		final Iterator<?> iterator = choices.iterator();
		assertSame(TestEnum.B, iterator.next());
		assertSame(TestEnum.C, iterator.next());
	}
}
