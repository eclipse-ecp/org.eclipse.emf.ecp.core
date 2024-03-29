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
 * Eugen Neufeld - initial API and implementation
 * Lucas Koehler - databinding tests
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.core.swt.renderer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.core.databinding.observable.Observables;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.Properties;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.test.common.DefaultRealm;
import org.eclipse.emf.ecp.view.core.swt.tests.ObservingWritableValue;
import org.eclipse.emf.ecp.view.spi.model.LabelAlignment;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.ecp.view.test.common.swt.spi.SWTTestUtil;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.core.services.label.NoLabelFoundException;
import org.eclipse.emfforms.spi.swt.core.EMFFormsControlProcessorService;
import org.eclipse.emfforms.spi.swt.core.SWTDataElementIdHelper;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.swt.common.test.AbstractControl_PTest;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class TextControlRenderer_PTest extends AbstractControl_PTest<VControl> {

	private DefaultRealm realm;

	@Before
	public void before() throws DatabindingFailedException {
		realm = new DefaultRealm();
		final ReportService reportService = mock(ReportService.class);
		setDatabindingService(mock(EMFFormsDatabinding.class));
		setLabelProvider(mock(EMFFormsLabelProvider.class));
		setTemplateProvider(mock(VTViewTemplateProvider.class));
		final EMFFormsEditSupport editSupport = mock(EMFFormsEditSupport.class);
		setup();
		setRenderer(new TextControlSWTRenderer(getvControl(), getContext(), reportService, getDatabindingService(),
			getLabelProvider(),
			getTemplateProvider(), editSupport));
		getRenderer().init();
	}

	@After
	public void testTearDown() {
		realm.dispose();
		dispose();
	}

	@Test
	public void renderControlLabelAlignmentNone()
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption, DatabindingFailedException,
		NoLabelFoundException {
		when(getLabelProvider().getDisplayName(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
			Observables.constantObservableValue("antiException", String.class)); //$NON-NLS-1$
		setMockLabelAlignment(LabelAlignment.NONE);
		final TestObservableValue mockedObservableValue = mock(TestObservableValue.class);
		when(mockedObservableValue.getRealm()).thenReturn(realm);
		final EObject mockedEObject = mock(EObject.class);
		when(mockedEObject.eIsSet(any(EStructuralFeature.class))).thenReturn(true);
		when(mockedObservableValue.getObserved()).thenReturn(mockedEObject);
		final EStructuralFeature mockedEStructuralFeature = EcorePackage.eINSTANCE.getENamedElement_Name();
		when(mockedObservableValue.getValueType()).thenReturn(mockedEStructuralFeature);
		when(getDatabindingService().getObservableValue(any(VDomainModelReference.class), any(EObject.class)))
			.thenReturn(
				mockedObservableValue);
		final Control render = renderControl(new SWTGridCell(0, 1, getRenderer()));
		assertControl(render);
	}

	@Test
	public void renderControlLabelAlignmentLeft()
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption, DatabindingFailedException,
		NoLabelFoundException {
		when(getLabelProvider().getDisplayName(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
			Observables.constantObservableValue("antiException", String.class)); //$NON-NLS-1$
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
		final Control textRender = Composite.class.cast(render).getChildren()[0];
		assertTrue(Text.class.isInstance(textRender));
		assertEquals(SWT.LEFT, Text.class.cast(textRender).getStyle()
			& SWT.LEFT);
		assertEquals("org_eclipse_emf_ecp_control_string", Text.class.cast(textRender).getData(CUSTOM_VARIANT)); //$NON-NLS-1$
		assertEquals(UUID + "#control", textRender.getData(SWTDataElementIdHelper.ELEMENT_ID_KEY)); //$NON-NLS-1$
		assertEquals(UUID + "#control", render.getData(SWTDataElementIdHelper.ELEMENT_ID_KEY)); //$NON-NLS-1$
	}

	@Override
	protected void mockControl() throws DatabindingFailedException {
		final EStructuralFeature eObject = EcoreFactory.eINSTANCE.createEAttribute();
		final EStructuralFeature eStructuralFeature = EcorePackage.eINSTANCE.getENamedElement_Name();
		super.mockControl(eObject, eStructuralFeature);
	}

	@Test
	public void testDatabindingServiceUsageInitialBinding() throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption, DatabindingFailedException, NoLabelFoundException {
		final String initialValue = "initial"; //$NON-NLS-1$
		final ObservingWritableValue mockedObservable = new ObservingWritableValue(realm, initialValue,
			EcorePackage.eINSTANCE.getENamedElement_Name());
		final Text text = setUpDatabindingTest(mockedObservable);

		assertEquals(initialValue, text.getText());

	}

	@Test
	@SuppressWarnings("unchecked")
	public void testDatabindingServiceUsageChangeObservable() throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption, DatabindingFailedException, NoLabelFoundException {
		final String initialValue = "initial"; //$NON-NLS-1$
		final String changedValue = "changed"; //$NON-NLS-1$
		final ObservingWritableValue mockedObservable = new ObservingWritableValue(realm, initialValue,
			EcorePackage.eINSTANCE.getENamedElement_Name());

		final Text text = setUpDatabindingTest(mockedObservable);
		mockedObservable.setValue(changedValue);

		assertEquals(changedValue, text.getText());

	}

	@Test
	public void testDatabindingServiceUsageChangeControl() throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption, DatabindingFailedException, NoLabelFoundException {
		final String initialValue = "initial"; //$NON-NLS-1$
		final String changedValue = "changed"; //$NON-NLS-1$
		final ObservingWritableValue mockedObservable = new ObservingWritableValue(realm, initialValue,
			EcorePackage.eINSTANCE.getENamedElement_Name());

		final Text text = setUpDatabindingTest(mockedObservable);
		SWTTestUtil.typeAndFocusOut(text, changedValue);

		assertEquals(changedValue, mockedObservable.getValue());

	}

	/**
	 * Universal set up stuff for the data binding test cases.
	 *
	 * @param mockedObservable
	 * @return
	 * @throws NoRendererFoundException
	 * @throws NoPropertyDescriptorFoundExeption
	 * @throws DatabindingFailedException if the databinding failed
	 * @throws NoLabelFoundException
	 */
	private Text setUpDatabindingTest(final ObservingWritableValue mockedObservable) throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption, DatabindingFailedException, NoLabelFoundException {
		when(getLabelProvider().getDisplayName(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
			Observables.constantObservableValue("antiException")); //$NON-NLS-1$
		Mockito.reset(getDatabindingService());
		mockDatabindingIsSettableAndChangeable();
		when(getDatabindingService().getObservableValue(any(VDomainModelReference.class), any(EObject.class)))
			.thenReturn(
				mockedObservable, new ObservingWritableValue(mockedObservable));
		when(getDatabindingService().getValueProperty(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
			Properties.selfValue(mockedObservable.getValueType()));
		final Control renderControl = renderControl(new SWTGridCell(0, 2, getRenderer()));
		final Text text = (Text) Composite.class.cast(renderControl).getChildren()[0];
		return text;
	}

	/**
	 * Tests whether the {@link EMFFormsLabelProvider} is used to get the message of the text field of the text
	 * control.
	 *
	 * @throws NoPropertyDescriptorFoundExeption
	 * @throws NoRendererFoundException
	 * @throws DatabindingFailedException
	 * @throws NoLabelFoundException
	 */
	@Test
	@SuppressWarnings({ "rawtypes" })
	public void testLabelServiceUsageTextField() throws NoRendererFoundException, NoPropertyDescriptorFoundExeption,
		DatabindingFailedException, NoLabelFoundException {
		final IObservableValue testDisplayName = Observables.constantObservableValue("test-displayname", String.class); //$NON-NLS-1$
		when(getLabelProvider().getDisplayName(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
			testDisplayName);

		setMockLabelAlignment(LabelAlignment.LEFT);

		final TestObservableValue mockedObservableValue = mock(TestObservableValue.class);
		when(mockedObservableValue.getRealm()).thenReturn(realm);
		final EObject mockedEObject = mock(EObject.class);
		when(mockedEObject.eIsSet(any(EStructuralFeature.class))).thenReturn(true);
		when(mockedObservableValue.getObserved()).thenReturn(mockedEObject);
		final EStructuralFeature mockedEStructuralFeature = EcorePackage.eINSTANCE.getENamedElement_Name();
		when(mockedObservableValue.getValueType()).thenReturn(mockedEStructuralFeature);
		when(getDatabindingService().getObservableValue(any(VDomainModelReference.class), any(EObject.class)))
			.thenReturn(
				mockedObservableValue);

		final Control renderControl = renderControl(new SWTGridCell(0, 2, getRenderer()));
		final Control textRender = Composite.class.cast(renderControl).getChildren()[0];
		assertTrue(Text.class.isInstance(textRender));

		final Text text = (Text) textRender;
		assertEquals(testDisplayName.getValue(), text.getMessage());
	}

	@Test
	public void testEffectivelyReadOnlyDeactivatesControl()
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption, DatabindingFailedException,
		NoLabelFoundException {
		final ObservingWritableValue mockedObservable = new ObservingWritableValue(realm, "", //$NON-NLS-1$
			EcorePackage.eINSTANCE.getENamedElement_Name());
		when(getLabelProvider().getDisplayName(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
			Observables.constantObservableValue("antiException")); //$NON-NLS-1$
		when(getDatabindingService().getObservableValue(any(VDomainModelReference.class), any(EObject.class)))
			.thenReturn(mockedObservable, new ObservingWritableValue(mockedObservable));
		when(getDatabindingService().getValueProperty(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
			Properties.selfValue(mockedObservable.getValueType()));

		when(getvControl().isEffectivelyReadonly()).thenReturn(true);
		final Control renderControl = renderControl(new SWTGridCell(0, 2, getRenderer()));
		getRenderer().finalizeRendering(getShell());
		assertFalse(renderControl.isEnabled());
	}

	@Test
	public void testProcessorCalled()
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption, NoLabelFoundException,
		DatabindingFailedException {
		final ObservingWritableValue mockedObservable = new ObservingWritableValue(realm, "", //$NON-NLS-1$
			EcorePackage.eINSTANCE.getENamedElement_Name());
		when(getLabelProvider().getDisplayName(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
			Observables.constantObservableValue("antiException")); //$NON-NLS-1$
		when(getDatabindingService().getObservableValue(any(VDomainModelReference.class), any(EObject.class)))
			.thenReturn(mockedObservable, new ObservingWritableValue(mockedObservable));
		when(getDatabindingService().getValueProperty(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
			Properties.selfValue(mockedObservable.getValueType()));

		final EMFFormsControlProcessorService dummyProcessor = mock(EMFFormsControlProcessorService.class);
		when(getContext().getService(EMFFormsControlProcessorService.class)).thenReturn(dummyProcessor);
		when(getContext().hasService(EMFFormsControlProcessorService.class)).thenReturn(Boolean.TRUE);
		final Control renderControl = renderControl(new SWTGridCell(0, 2, getRenderer()));
		getRenderer().finalizeRendering(getShell());
		Mockito.verify(dummyProcessor).process(renderControl, getvControl(), getContext());
	}
}
