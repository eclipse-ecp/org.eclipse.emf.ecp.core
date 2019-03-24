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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.DateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.core.databinding.property.Properties;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.test.common.DefaultRealm;
import org.eclipse.emf.ecp.view.core.swt.test.model.SimpleTestObject;
import org.eclipse.emf.ecp.view.core.swt.test.model.TestFactory;
import org.eclipse.emf.ecp.view.core.swt.test.model.TestPackage;
import org.eclipse.emf.ecp.view.core.swt.tests.ObservingWritableValue;
import org.eclipse.emf.ecp.view.spi.model.LabelAlignment;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.ecp.view.test.common.swt.spi.SWTTestUtil;
import org.eclipse.emfforms.spi.common.locale.EMFFormsLocaleProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.core.services.label.NoLabelFoundException;
import org.eclipse.emfforms.spi.localization.EMFFormsLocalizationService;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.swt.common.test.AbstractControl_PTest;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class XMLDateControlRenderer_PTest extends AbstractControl_PTest<VControl> {

	private DefaultRealm realm;

	@Before
	public void before() throws DatabindingFailedException {
		realm = new DefaultRealm();
		final ReportService reportService = mock(ReportService.class);
		setDatabindingService(mock(EMFFormsDatabinding.class));
		setLabelProvider(mock(EMFFormsLabelProvider.class));
		setTemplateProvider(mock(VTViewTemplateProvider.class));
		final EMFFormsEditSupport editSupport = mock(EMFFormsEditSupport.class);
		final EMFFormsLocalizationService localizationService = mock(EMFFormsLocalizationService.class);
		final EMFFormsLocaleProvider localeProvider = mock(EMFFormsLocaleProvider.class);
		when(localeProvider.getLocale()).thenReturn(Locale.getDefault());
		final ImageRegistryService imageRegistryService = mock(ImageRegistryService.class);

		setup();
		setRenderer(new XMLDateControlSWTRenderer(getvControl(), getContext(), reportService, getDatabindingService(),
			getLabelProvider(),
			getTemplateProvider(), editSupport, localizationService, localeProvider, imageRegistryService));
		getRenderer().init();
	}

	@After
	public void testTearDown() {
		realm.dispose();
		dispose();
	}

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
		when(mockedEStructuralFeature.isUnsettable()).thenReturn(false);
		when(mockedObservableValue.getValueType()).thenReturn(mockedEStructuralFeature);
		when(getDatabindingService().getObservableValue(any(VDomainModelReference.class), any(EObject.class)))
			.thenReturn(
				mockedObservableValue);
		final Control render = renderControl(new SWTGridCell(0, 1, getRenderer()));
		assertControl(render);
	}

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
		when(mockedEStructuralFeature.isUnsettable()).thenReturn(false);
		when(mockedObservableValue.getValueType()).thenReturn(mockedEStructuralFeature);
		when(getDatabindingService().getObservableValue(any(VDomainModelReference.class), any(EObject.class)))
			.thenReturn(
				mockedObservableValue);
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
		assertTrue(Composite.class.isInstance(render));
		final Composite top = Composite.class.cast(render);
		assertEquals(2, top.getChildren().length);
		final Control textRender = Composite.class.cast(top.getChildren()[0]).getChildren()[0];
		assertTrue(Text.class.isInstance(textRender));
		assertTrue(Button.class.isInstance(top.getChildren()[1]));
		final Text text = Text.class.cast(textRender);
		assertEquals(SWT.LEFT, text.getStyle() & SWT.LEFT);
		assertEquals("org_eclipse_emf_ecp_control_xmldate", text.getData(CUSTOM_VARIANT));
	}

	@Override
	protected void mockControl() throws DatabindingFailedException {
		final SimpleTestObject eObject = TestFactory.eINSTANCE.createSimpleTestObject();
		super.mockControl(eObject, TestPackage.eINSTANCE.getSimpleTestObject_XmlDate());
	}

	@Test
	public void testDatabindingServiceUsageInitialBinding() throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption, DatatypeConfigurationException, DatabindingFailedException {
		final XMLGregorianCalendar initialValue = getXMLGregorianCalendarFromDate(new Date());

		final ObservingWritableValue mockedObservable = new ObservingWritableValue(realm, initialValue,
			TestPackage.eINSTANCE.getSimpleTestObject_XmlDate());
		final Text text = setUpDatabindingTest(mockedObservable);

		final String expected = formatXMLGregorianCalendar(initialValue);
		assertEquals(expected, text.getText());

	}

	@Test
	@SuppressWarnings("unchecked")
	public void testDatabindingServiceUsageChangeObservable() throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption, DatatypeConfigurationException, DatabindingFailedException {
		final XMLGregorianCalendar initialValue = getXMLGregorianCalendarFromDate(new Date());
		final XMLGregorianCalendar changedValue = getXMLGregorianCalendarFromDate(new Date(
			System.currentTimeMillis() * 2));
		final ObservingWritableValue mockedObservable = new ObservingWritableValue(realm, initialValue,
			TestPackage.eINSTANCE.getSimpleTestObject_XmlDate());

		final Text text = setUpDatabindingTest(mockedObservable);
		mockedObservable.setValue(changedValue);

		final String expected = formatXMLGregorianCalendar(changedValue);
		assertEquals(expected, text.getText());

	}

	@Test
	public void testDatabindingServiceUsageChangeControl() throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption, DatatypeConfigurationException, DatabindingFailedException {
		// TODO
		final XMLGregorianCalendar initialValue = getXMLGregorianCalendarFromDate(new Date());
		final XMLGregorianCalendar changedValue = getXMLGregorianCalendarFromDate(new Date(
			System.currentTimeMillis() * 2));
		final ObservingWritableValue mockedObservable = new ObservingWritableValue(realm, initialValue,
			TestPackage.eINSTANCE.getSimpleTestObject_XmlDate());

		final Text text = setUpDatabindingTest(mockedObservable);
		SWTTestUtil.typeAndFocusOut(text, formatXMLGregorianCalendar(changedValue));

		final String actual = formatXMLGregorianCalendar((XMLGregorianCalendar) mockedObservable.getValue());
		assertEquals(text.getText(), actual);

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
		Mockito.reset(getDatabindingService());
		mockDatabindingIsSettableAndChangeable();
		when(getDatabindingService().getObservableValue(any(VDomainModelReference.class), any(EObject.class)))
			.thenReturn(
				mockedObservable, new ObservingWritableValue(mockedObservable));
		when(getDatabindingService().getValueProperty(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
			Properties.selfValue(mockedObservable.getValueType()));

		final Control renderControl = renderControl(new SWTGridCell(0, 2, getRenderer()));
		final Composite composite = (Composite) renderControl;
		final Text text = (Text) Composite.class.cast(composite.getChildren()[0]).getChildren()[0];
		return text;
	}

	private String formatXMLGregorianCalendar(XMLGregorianCalendar calendar) {
		final DateFormat format = setupFormat();
		if (calendar == null) {
			return null;
		}
		final Date date = calendar.toGregorianCalendar().getTime();
		return format.format(date);
	}

	/**
	 * Setups the {@link DateFormat}.
	 *
	 * @return the {@link DateFormat}
	 */
	private DateFormat setupFormat() {
		final DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
		df.setLenient(false);
		return df;
	}

	/**
	 * @return
	 * @throws DatatypeConfigurationException
	 */
	private XMLGregorianCalendar getXMLGregorianCalendarFromDate(Date date) throws DatatypeConfigurationException {
		final GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		final XMLGregorianCalendar initialValue = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
		return initialValue;
	}

	@Test
	public void testEffectivelyReadOnlyDeactivatesControl()
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption, DatabindingFailedException {

		final ObservingWritableValue mockedObservable = new ObservingWritableValue(realm, null,
			TestPackage.eINSTANCE.getSimpleTestObject_XmlDate());

		when(getDatabindingService().getObservableValue(any(VDomainModelReference.class), any(EObject.class)))
			.thenReturn(
				mockedObservable, new ObservingWritableValue(mockedObservable));
		when(getDatabindingService().getValueProperty(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
			Properties.selfValue(mockedObservable.getValueType()));
		when(getvControl().isEffectivelyReadonly()).thenReturn(true);
		final Control renderControl = renderControl(new SWTGridCell(0, 2, getRenderer()));
		getRenderer().finalizeRendering(getShell());
		assertFalse(renderControl.isEnabled());
	}
}
