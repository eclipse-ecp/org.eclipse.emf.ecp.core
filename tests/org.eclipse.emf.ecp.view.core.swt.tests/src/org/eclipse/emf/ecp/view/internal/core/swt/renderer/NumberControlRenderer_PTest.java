/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 * Lucas Koehler - databinding tests
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.core.swt.renderer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.DecimalFormat;
import java.util.Locale;

import org.eclipse.core.databinding.property.Properties;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.edit.internal.swt.controls.NumericalHelper;
import org.eclipse.emf.ecp.test.common.DefaultRealm;
import org.eclipse.emf.ecp.view.core.swt.tests.ObservingWritableValue;
import org.eclipse.emf.ecp.view.internal.core.swt.MessageKeys;
import org.eclipse.emf.ecp.view.spi.model.LabelAlignment;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.ecp.view.test.common.swt.spi.SWTTestUtil;
import org.eclipse.emfforms.spi.common.locale.EMFFormsLocaleProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.common.validation.PreSetValidationService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.core.services.label.NoLabelFoundException;
import org.eclipse.emfforms.spi.localization.EMFFormsLocalizationService;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.swt.common.test.AbstractControl_PTest;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@SuppressWarnings("rawtypes")
public class NumberControlRenderer_PTest extends AbstractControl_PTest {

	private DefaultRealm realm;

	@Before
	public void before() throws DatabindingFailedException {
		realm = new DefaultRealm();
		final ReportService reportService = mock(ReportService.class);
		setDatabindingService(mock(EMFFormsDatabinding.class));
		setLabelProvider(mock(EMFFormsLabelProvider.class));
		setTemplateProvider(mock(VTViewTemplateProvider.class));
		final EMFFormsLocalizationService localizationService = mock(EMFFormsLocalizationService.class);
		when(
			localizationService.getString(NumberControlSWTRenderer.class, MessageKeys.NumericalControl_FormatNumerical))
				.thenReturn("#");
		when(
			localizationService.getString(NumberControlSWTRenderer.class,
				MessageKeys.NumericalControl_FormatNumericalDecimal)).thenReturn("#.#");
		final EMFFormsLocaleProvider localeProvider = mock(EMFFormsLocaleProvider.class);
		when(localeProvider.getLocale()).thenReturn(Locale.getDefault());
		final EMFFormsEditSupport editSupport = mock(EMFFormsEditSupport.class);
		setup();
		setRenderer(new NumberControlSWTRenderer(getvControl(), getContext(), reportService, getDatabindingService(),
			getLabelProvider(),
			getTemplateProvider(), editSupport, localizationService, localeProvider));
		getRenderer().init();
	}

	@After
	public void testTearDown() {
		realm.dispose();
		dispose();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void renderControlLabelAlignmentNone()
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption, DatabindingFailedException {
		setMockLabelAlignment(LabelAlignment.NONE);
		final TestObservableValue mockedObservableValue = mock(TestObservableValue.class);
		when(mockedObservableValue.getRealm()).thenReturn(realm);
		final EObject mockedEObject = mock(EObject.class);
		when(mockedEObject.eIsSet(any(EStructuralFeature.class))).thenReturn(true);
		when(mockedObservableValue.getObserved()).thenReturn(mockedEObject);
		final EStructuralFeature mockedEStructuralFeature = mock(EStructuralFeature.class);
		final EClassifier mockedEClassifier = mock(EClassifier.class);
		final Class clazz = Double.class;
		when(mockedEClassifier.getInstanceClass()).thenReturn(clazz);
		when(mockedEStructuralFeature.getEType()).thenReturn(mockedEClassifier);
		when(mockedObservableValue.getValueType()).thenReturn(mockedEStructuralFeature);
		when(getDatabindingService().getObservableValue(any(VDomainModelReference.class), any(EObject.class)))
			.thenReturn(
				mockedObservableValue);
		when(getDatabindingService().getValueProperty(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
			Properties.selfValue(mockedEStructuralFeature));
		final Control render = renderControl(new SWTGridCell(0, 1, getRenderer()));
		assertControl(render);
	}

	@SuppressWarnings({ "unchecked" })
	@Test
	public void renderControlLabelAlignmentLeft()
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption, DatabindingFailedException {
		setMockLabelAlignment(LabelAlignment.LEFT);
		final TestObservableValue mockedObservableValue = mock(TestObservableValue.class);
		when(mockedObservableValue.getRealm()).thenReturn(realm);
		final EObject mockedEObject = mock(EObject.class);
		when(mockedEObject.eIsSet(any(EStructuralFeature.class))).thenReturn(true);
		when(mockedObservableValue.getObserved()).thenReturn(mockedEObject);
		final EStructuralFeature mockedEStructuralFeature = mock(EStructuralFeature.class);
		final EClassifier mockedEClassifier = mock(EClassifier.class);
		final Class clazz = Double.class;
		when(mockedEClassifier.getInstanceClass()).thenReturn(clazz);
		when(mockedEStructuralFeature.getEType()).thenReturn(mockedEClassifier);
		when(mockedObservableValue.getValueType()).thenReturn(mockedEStructuralFeature);
		when(getDatabindingService().getObservableValue(any(VDomainModelReference.class), any(EObject.class)))
			.thenReturn(
				mockedObservableValue);
		when(getDatabindingService().getValueProperty(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
			Properties.selfValue(mockedEStructuralFeature));

		final Control render = renderControl(new SWTGridCell(0, 2, getRenderer()));

		assertControl(render);
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

	private void assertControl(Control render) {
		final Control textRender = Composite.class.cast(render).getChildren()[0];
		assertTrue(Text.class.isInstance(textRender));
		assertEquals(SWT.RIGHT, Text.class.cast(textRender).getStyle()
			& SWT.RIGHT);
		assertEquals(SWT.RIGHT, Text.class.cast(textRender).getStyle()
			& SWT.RIGHT);

		assertEquals("org_eclipse_emf_ecp_control_numerical", Text.class.cast(textRender).getData(CUSTOM_VARIANT));
	}

	@Override
	protected void mockControl() throws DatabindingFailedException {
		final EStructuralFeature eObject = EcoreFactory.eINSTANCE.createEAttribute();
		final EStructuralFeature eStructuralFeature = EcorePackage.eINSTANCE.getETypedElement_LowerBound();
		super.mockControl(eObject, eStructuralFeature);
	}

	@Test
	public void testDatabindingServiceUsageInitialBinding() throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption, DatabindingFailedException {
		final int initialValue = 13;
		final ObservingWritableValue mockedObservable = new ObservingWritableValue(realm, initialValue,
			EcorePackage.eINSTANCE.getETypedElement_LowerBound());
		final Text text = setUpDatabindingTest(mockedObservable);

		final DecimalFormat format = getDecimalFormat(Integer.class);

		assertEquals(format.format(initialValue), text.getText());

	}

	@Test
	public void testDatabindingServiceUsageChangeObservable() throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption, DatabindingFailedException {
		final int initialValue = 13;
		final int changedValue = 42;
		final ObservingWritableValue mockedObservable = new ObservingWritableValue(realm, initialValue,
			EcorePackage.eINSTANCE.getETypedElement_LowerBound());

		final Text text = setUpDatabindingTest(mockedObservable);
		mockedObservable.setValue(changedValue);

		final DecimalFormat format = getDecimalFormat(Integer.class);

		assertEquals(format.format(changedValue), text.getText());

	}

	@Test
	public void testDatabindingServiceUsageChangeControl() throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption, DatabindingFailedException {
		final int initialValue = 13;
		final int changedValue = 42;
		final ObservingWritableValue mockedObservable = new ObservingWritableValue(realm, initialValue,
			EcorePackage.eINSTANCE.getETypedElement_LowerBound());

		final Text text = setUpDatabindingTest(mockedObservable);

		final DecimalFormat format = getDecimalFormat(Integer.class);
		SWTTestUtil.typeAndFocusOut(text, format.format(changedValue));

		assertEquals(changedValue, mockedObservable.getValue());

	}

	@Test
	public void testDatabindingServiceUsageChangeControlToDoubleValue() throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption, DatabindingFailedException {
		final double initialValue = 13;
		final double changedValue = 42.5;
		final EAttribute eAttribute = EcoreFactory.eINSTANCE.createEAttribute();
		eAttribute.setEType(EcorePackage.eINSTANCE.getEDouble());
		eAttribute.setName("doubleAttr");
		final ObservingWritableValue mockedObservable = new ObservingWritableValue(realm, initialValue,
			eAttribute);

		final Text text = setUpDatabindingTest(mockedObservable);

		final DecimalFormat format = getDecimalFormat(Double.class);
		SWTTestUtil.typeAndFocusOut(text, format.format(changedValue));

		assertEquals(changedValue, mockedObservable.getValue());

	}

	@Test
	public void testControlTextUpdateWithoutPrevalidation_InputTooBig()
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption, DatabindingFailedException {
		when(getContext().hasService(PreSetValidationService.class)).thenReturn(false);
		when(getContext().getService(PreSetValidationService.class)).thenReturn(null);

		final int initialValue = 0;
		final String tooBig = "11111111111111111111111111111111";
		final ObservingWritableValue mockedObservable = new ObservingWritableValue(realm, initialValue,
			EcorePackage.eINSTANCE.getETypedElement_LowerBound());

		final Text text = setUpDatabindingTest(mockedObservable);

		SWTTestUtil.typeAndFocusOut(text, tooBig);

		assertEquals(Integer.MAX_VALUE, mockedObservable.getValue());
		final DecimalFormat format = getDecimalFormat(Integer.class);
		assertEquals(format.format(Integer.MAX_VALUE), text.getText());
	}

	@Test
	public void testControlTextUpdateWithoutPrevalidation_InputTooSmall()
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption, DatabindingFailedException {
		when(getContext().hasService(PreSetValidationService.class)).thenReturn(false);
		when(getContext().getService(PreSetValidationService.class)).thenReturn(null);

		final int initialValue = 0;
		final String tooSmall = "-11111111111111111111111111111111";
		final ObservingWritableValue mockedObservable = new ObservingWritableValue(realm, initialValue,
			EcorePackage.eINSTANCE.getETypedElement_LowerBound());

		final Text text = setUpDatabindingTest(mockedObservable);

		SWTTestUtil.typeAndFocusOut(text, tooSmall);

		assertEquals(Integer.MIN_VALUE, mockedObservable.getValue());
		final DecimalFormat format = getDecimalFormat(Integer.class);
		assertEquals(format.format(Integer.MIN_VALUE), text.getText());
	}

	@Ignore
	@Test
	public void testControlTextUpdateWithPrevalidation() {
		// TODO implement when PreSetValidationListeners does not use a static instance of PreSetValidationService
		// anymore
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
	private Text setUpDatabindingTest(final ObservingWritableValue mockedObservable) throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption, DatabindingFailedException {
		mockDatabindingIsUnsettable();
		when(getDatabindingService().getObservableValue(any(VDomainModelReference.class), any(EObject.class)))
			.thenReturn(
				mockedObservable, new ObservingWritableValue(mockedObservable));
		when(getDatabindingService().getValueProperty(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
			Properties.selfValue(mockedObservable.getValueType()));

		final Control renderControl = renderControl(new SWTGridCell(0, 2, getRenderer()));
		final Text text = (Text) Composite.class.cast(renderControl).getChildren()[0];
		return text;
	}

	private DecimalFormat getDecimalFormat(Class<?> instanceClass) {
		return NumericalHelper.setupFormat(Locale.getDefault(), instanceClass);
	}

}
