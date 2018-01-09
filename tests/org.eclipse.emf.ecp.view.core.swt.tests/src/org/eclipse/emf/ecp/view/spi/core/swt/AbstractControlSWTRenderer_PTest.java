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
package org.eclipse.emf.ecp.view.spi.core.swt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.core.databinding.observable.Observables;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.edit.spi.swt.util.SWTValidationHelper;
import org.eclipse.emf.ecp.test.common.DefaultRealm;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.LabelAlignment;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.ecp.view.template.style.alignment.model.AlignmentType;
import org.eclipse.emf.ecp.view.template.style.alignment.model.VTAlignmentFactory;
import org.eclipse.emf.ecp.view.template.style.alignment.model.VTControlLabelAlignmentStyleProperty;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.core.services.label.NoLabelFoundException;
import org.eclipse.emfforms.spi.swt.core.layout.GridDescriptionFactory;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.eclipse.emfforms.swt.common.test.AbstractControl_PTest.TestObservableValue;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class AbstractControlSWTRenderer_PTest {

	private DefaultRealm realm;
	private Shell shell;

	private VControl vControl;
	private ViewModelContext viewModelContext;
	private ReportService reportService;
	private EMFFormsDatabinding emfFormsDatabinding;
	private EMFFormsLabelProvider emfFormsLabelProvider;
	private VTViewTemplateProvider viewTemplateProvider;

	private TestAbstractControlSWTRenderer renderer;

	private SWTGridCell swtGridCell;

	@Before
	public void before() throws DatabindingFailedException, NoLabelFoundException {
		realm = new DefaultRealm();
		shell = new Shell();

		vControl = Mockito.mock(VControl.class);
		Mockito.when(vControl.getLabelAlignment()).thenReturn(LabelAlignment.LEFT);

		viewModelContext = Mockito.mock(ViewModelContext.class);
		reportService = Mockito.mock(ReportService.class);

		emfFormsDatabinding = Mockito.mock(EMFFormsDatabinding.class);
		final TestObservableValue mockedObservableValue = mock(TestObservableValue.class);
		Mockito.when(mockedObservableValue.getRealm()).thenReturn(realm);
		final EObject mockedEObject = mock(EObject.class);
		Mockito.when(mockedEObject.eIsSet(any(EStructuralFeature.class))).thenReturn(true);
		Mockito.when(mockedObservableValue.getObserved()).thenReturn(mockedEObject);
		final EStructuralFeature mockedEStructuralFeature = EcorePackage.eINSTANCE.getENamedElement_Name();
		Mockito.when(mockedObservableValue.getValueType()).thenReturn(mockedEStructuralFeature);
		Mockito.when(emfFormsDatabinding.getObservableValue(
			any(VDomainModelReference.class),
			any(EObject.class)))
			.thenReturn(mockedObservableValue);
		final IValueProperty valueProperty = Mockito.mock(IValueProperty.class);
		Mockito.when(valueProperty.getValueType()).thenReturn(mockedEStructuralFeature);
		Mockito.when(emfFormsDatabinding.getValueProperty(
			any(VDomainModelReference.class),
			any(EObject.class)))
			.thenReturn(valueProperty);

		emfFormsLabelProvider = Mockito.mock(EMFFormsLabelProvider.class);
		Mockito.when(emfFormsLabelProvider.getDisplayName(
			any(VDomainModelReference.class),
			any(EObject.class)))
			.thenReturn(Observables.constantObservableValue("I am display name", String.class));
		Mockito.when(emfFormsLabelProvider.getDescription(
			any(VDomainModelReference.class),
			any(EObject.class)))
			.thenReturn(Observables.constantObservableValue("I am description", String.class));

		viewTemplateProvider = Mockito.mock(VTViewTemplateProvider.class);

		renderer = new TestAbstractControlSWTRenderer(
			vControl,
			viewModelContext,
			reportService,
			emfFormsDatabinding,
			emfFormsLabelProvider,
			viewTemplateProvider);

		renderer.init();
	}

	@After
	public void tearDown() {
		shell.dispose();
		realm.dispose();
	}

	@Test
	public void testDefaultLabelStyleBits() throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		/* act */
		final Control render = renderer.renderControl(swtGridCell, shell);
		/* assert */
		assertTrue(Label.class.isInstance(render));
		assertEquals(SWT.LEFT, Label.class.cast(render).getAlignment());
	}

	@Test
	public void testDefaultLabelStyleBitsLeft() throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		/* setup */
		final VTControlLabelAlignmentStyleProperty property = VTAlignmentFactory.eINSTANCE
			.createControlLabelAlignmentStyleProperty();
		property.setType(AlignmentType.LEFT);
		final Set<VTStyleProperty> properties = Collections.<VTStyleProperty> singleton(property);
		Mockito.when(viewTemplateProvider.getStyleProperties(vControl, viewModelContext)).thenReturn(properties);

		/* act */
		final Control render = renderer.renderControl(swtGridCell, shell);
		/* assert */
		assertTrue(Label.class.isInstance(render));
		assertEquals(SWT.LEFT, Label.class.cast(render).getAlignment());
	}

	@Test
	public void testgetValidationBackgroundColor() throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		/* setup */
		final SWTValidationHelper validationHelper = Mockito.mock(SWTValidationHelper.class);

		renderer = new TestAbstractControlSWTRenderer(
			vControl,
			viewModelContext,
			reportService,
			emfFormsDatabinding,
			emfFormsLabelProvider,
			viewTemplateProvider, validationHelper);

		renderer.init();

		final List<Integer> severityList = createListOfSeverities();
		// Create a list of sample colors
		final List<Color> colorList0 = createColorList(0, severityList);

		mockBackGroundColorSet(validationHelper, severityList, colorList0);

		// Check that all colors are retrieved correctly on the first run
		int i = 0;
		for (final Integer severity : severityList) {
			final Color validationBackgroundColor = renderer.getValidationBackgroundColor(severity);
			assertEquals(colorList0.get(i), validationBackgroundColor);
			i++;
		}

		// change the colors in the validation helper mock
		final List<Color> colorList1 = createColorList(1, severityList);
		mockBackGroundColorSet(validationHelper, severityList, colorList1);

		// Check that still color set 0 is returned
		i = 0;
		for (final Integer severity : severityList) {
			final Color validationBackgroundColor = renderer.getValidationBackgroundColor(severity);
			assertEquals(colorList0.get(i), validationBackgroundColor);
			i++;
		}

	}

	@Test
	public void testgetValidationForeGroundColor() throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		/* setup */
		final SWTValidationHelper validationHelper = Mockito.mock(SWTValidationHelper.class);

		renderer = new TestAbstractControlSWTRenderer(
			vControl,
			viewModelContext,
			reportService,
			emfFormsDatabinding,
			emfFormsLabelProvider,
			viewTemplateProvider, validationHelper);

		renderer.init();
		final List<Integer> severityList = createListOfSeverities();
		// Create a list of sample colors
		final List<Color> colorList0 = createColorList(0, severityList);

		mockForeGroundColorSet(validationHelper, severityList, colorList0);

		// Check that all colors are retrieved correctly on the first run
		int i = 0;
		for (final Integer severity : severityList) {
			final Color validationBackgroundColor = renderer.getValidationForegroundColor(severity);
			assertEquals(colorList0.get(i), validationBackgroundColor);
			i++;
		}

		// change the colors in the validation helper mock
		final List<Color> colorList1 = createColorList(1, severityList);
		mockForeGroundColorSet(validationHelper, severityList, colorList1);

		// Check that still color set 0 is returned
		i = 0;
		for (final Integer severity : severityList) {
			final Color validationBackgroundColor = renderer.getValidationForegroundColor(severity);
			assertEquals(colorList0.get(i), validationBackgroundColor);
			i++;
		}

	}

	@Test
	public void testgetValidationIcon() throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		/* setup */
		final SWTValidationHelper validationHelper = Mockito.mock(SWTValidationHelper.class);

		renderer = new TestAbstractControlSWTRenderer(
			vControl,
			viewModelContext,
			reportService,
			emfFormsDatabinding,
			emfFormsLabelProvider,
			viewTemplateProvider, validationHelper);

		renderer.init();
		final List<Integer> severityList = createListOfSeverities();
		// Create a list of sample Icons
		final List<Image> iconList0 = createIconList(0, severityList);

		mockIconList(validationHelper, severityList, iconList0);

		// Check that all icons are retrieved correctly on the first run
		int i = 0;
		for (final Integer severity : severityList) {
			final Image validationIcon = renderer.getValidationIcon(severity);
			assertEquals(iconList0.get(i), validationIcon);
			i++;
		}

		// change the icons in the validation helper mock
		final List<Image> iconList1 = createIconList(1, severityList);

		mockIconList(validationHelper, severityList, iconList1);

		// Check that still icon set 0 is returned
		i = 0;
		for (final Integer severity : severityList) {
			final Image validationIcon = renderer.getValidationIcon(severity);
			assertEquals(iconList0.get(i), validationIcon);
			i++;
		}

	}

	/**
	 * @param i
	 * @param severityList
	 * @return
	 */
	private List<Image> createIconList(int index, List<Integer> severityList) {
		final List<Image> imageList = new ArrayList<Image>();
		for (final Integer severity : severityList) {
			// Shifting index by 1, as 0 size images are not allowed
			imageList.add(new Image(Display.getCurrent(), index + 1, severity + 1));
		}
		return imageList;
	}

	/**
	 * @param validationHelper
	 * @param severityList
	 * @param colorList1
	 */
	private void mockForeGroundColorSet(SWTValidationHelper validationHelper, List<Integer> severityList,
		List<Color> colorList1) {
		int i = 0;

		for (final Integer severity : severityList) {
			Mockito.when(validationHelper.getValidationForegroundColor(severity, vControl, viewModelContext))
				.thenReturn(colorList1.get(i));
			i++;

		}

	}

	/**
	 * @param validationHelper
	 * @param severityList
	 * @param colorList1
	 */
	private void mockIconList(SWTValidationHelper validationHelper, List<Integer> severityList,
		List<Image> imageList) {
		int i = 0;

		for (final Integer severity : severityList) {
			Mockito.when(validationHelper.getValidationIcon(severity, vControl, viewModelContext))
				.thenReturn(imageList.get(i));
			i++;

		}

	}

	/**
	 * @param validationHelper
	 * @param severityList
	 * @param colorList1
	 */
	private void mockBackGroundColorSet(SWTValidationHelper validationHelper, List<Integer> severityList,
		List<Color> colorList1) {
		int i = 0;

		for (final Integer severity : severityList) {
			Mockito.when(validationHelper.getValidationBackgroundColor(severity, vControl, viewModelContext))
				.thenReturn(colorList1.get(i));
			i++;

		}

	}

	/**
	 * @param severityList
	 * @return
	 */
	private List<Color> createColorList(int index, List<Integer> severityList) {
		final List<Color> colorList = new ArrayList<Color>();
		for (final Integer severity : severityList) {
			colorList.add(new Color(Display.getCurrent(), 0, index, severity));
		}
		return colorList;
	}

	/**
	 * @return
	 */
	private List<Integer> createListOfSeverities() {
		final List<Integer> listOfSeverties = new ArrayList<Integer>();
		listOfSeverties.add(Diagnostic.OK);
		listOfSeverties.add(Diagnostic.WARNING);
		listOfSeverties.add(Diagnostic.ERROR);
		listOfSeverties.add(Diagnostic.INFO);
		listOfSeverties.add(Diagnostic.CANCEL);
		return listOfSeverties;
	}

	@Test
	public void testDefaultLabelStyleBitsRight() throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		/* setup */
		final VTControlLabelAlignmentStyleProperty property = VTAlignmentFactory.eINSTANCE
			.createControlLabelAlignmentStyleProperty();
		property.setType(AlignmentType.RIGHT);
		final Set<VTStyleProperty> properties = Collections.<VTStyleProperty> singleton(property);
		Mockito.when(viewTemplateProvider.getStyleProperties(vControl, viewModelContext)).thenReturn(properties);

		/* act */
		final Control render = renderer.renderControl(swtGridCell, shell);
		/* assert */
		assertTrue(Label.class.isInstance(render));
		assertEquals(SWT.RIGHT, Label.class.cast(render).getAlignment());
	}

	private class TestAbstractControlSWTRenderer extends AbstractControlSWTRenderer<VControl> {

		TestAbstractControlSWTRenderer(
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

		TestAbstractControlSWTRenderer(
			VControl vElement,
			ViewModelContext viewContext,
			ReportService reportService,
			EMFFormsDatabinding emfFormsDatabinding,
			EMFFormsLabelProvider emfFormsLabelProvider,
			VTViewTemplateProvider vtViewTemplateProvider, SWTValidationHelper swtValidationHelper) {
			super(
				vElement,
				viewContext,
				reportService,
				emfFormsDatabinding,
				emfFormsLabelProvider,
				vtViewTemplateProvider, swtValidationHelper);
		}

		@Override
		public SWTGridDescription getGridDescription(SWTGridDescription gridDescription) {
			final SWTGridDescription simpleGrid = GridDescriptionFactory.INSTANCE.createSimpleGrid(1, 1, this);
			swtGridCell = simpleGrid.getGrid().get(0);
			return simpleGrid;
		}

		@Override
		protected Control renderControl(SWTGridCell cell, Composite parent)
			throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
			return createLabel(parent);
		}

	}
}