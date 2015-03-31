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

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.core.swt.test.model.SimpleTestObject;
import org.eclipse.emf.ecp.view.core.swt.test.model.TestFactory;
import org.eclipse.emf.ecp.view.core.swt.test.model.TestPackage;
import org.eclipse.emf.ecp.view.spi.model.LabelAlignment;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.reporting.ReportService;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridCell;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.ecp.view.test.common.swt.spi.DatabindingClassRunner;
import org.eclipse.emf.emfforms.spi.core.services.labelprovider.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

@RunWith(DatabindingClassRunner.class)
public class DateTimeControlRenderer_PTest extends AbstractControl_PTest {

	@Before
	public void before() throws DatabindingFailedException {
		final ReportService reportService = mock(ReportService.class);
		databindingService = mock(EMFFormsDatabinding.class);
		labelProvider = mock(EMFFormsLabelProvider.class);
		templateProvider = mock(VTViewTemplateProvider.class);
		setup();
		renderer = new DateTimeControlSWTRenderer(vControl, context, reportService, databindingService, labelProvider,
			templateProvider);
	}

	@After
	public void testTearDown() {
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
		when(databindingService.getObservableValue(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
			mockedObservableValue);

		renderer.init();
		final Control render = renderControl(new SWTGridCell(0, 1, renderer));
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
		when(databindingService.getObservableValue(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
			mockedObservableValue);

		renderer.init();
		final Control render = renderControl(new SWTGridCell(0, 2, renderer));

		assertControl(render);
	}

	private void assertControl(Control render) {
		assertTrue(Composite.class.isInstance(render));
		final Composite top = Composite.class.cast(render);
		assertEquals(2, top.getChildren().length);
		assertTrue(Composite.class.isInstance(top.getChildren()[0]));
		assertTrue(Button.class.isInstance(top.getChildren()[1]));
		final Composite stack = Composite.class.cast(top.getChildren()[0]);
		assertTrue(StackLayout.class.isInstance(stack.getLayout()));
		assertEquals(2, stack.getChildren().length);
		assertTrue(Composite.class.isInstance(stack.getChildren()[0]));
		assertTrue(Label.class.isInstance(stack.getChildren()[1]));
		final Composite dateTime = Composite.class.cast(stack.getChildren()[0]);
		assertEquals(3, dateTime.getChildren().length);
		assertTrue(DateTime.class.isInstance(dateTime.getChildren()[0]));
		assertTrue(DateTime.class.isInstance(dateTime.getChildren()[1]));
		assertTrue(Button.class.isInstance(dateTime.getChildren()[2]));
	}

	@Override
	protected void mockControl() throws DatabindingFailedException {
		final SimpleTestObject eObject = TestFactory.eINSTANCE.createSimpleTestObject();
		super.mockControl(eObject, TestPackage.eINSTANCE.getSimpleTestObject_Date());
	}

	@Test
	public void testDatabindingServiceUsageInitialBinding() throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption, DatabindingFailedException {
		final Date initialValue = new Date();
		final EStructuralFeature mockedEStructuralFeature = mock(EStructuralFeature.class);
		final EObject mockedEObject = mock(EObject.class);
		when(mockedEObject.eIsSet(mockedEStructuralFeature)).thenReturn(true);

		final TestObservableValue mockedObservable = mock(TestObservableValue.class);
		when(mockedObservable.getRealm()).thenReturn(realm);
		when(mockedObservable.getValueType()).thenReturn(mockedEStructuralFeature);
		when(mockedObservable.getObserved()).thenReturn(mockedEObject);
		when(mockedObservable.getValue()).thenReturn(initialValue);

		final DateTime[] widgets = setUpDatabindingTest(mockedObservable);
		final DateTime dateWidget = widgets[0];
		final DateTime timeWidget = widgets[1];

		assertTrue(isDateEqualToDateTimes(initialValue, dateWidget, timeWidget));

	}

	@Test
	public void testDatabindingServiceUsageChangeObservable() throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption, DatabindingFailedException {
		final Date initialValue = new Date();
		final Date changedValue = new Date(System.currentTimeMillis() * 2);
		final Set<IValueChangeListener> listeners = new LinkedHashSet<IValueChangeListener>();
		final EStructuralFeature mockedEStructuralFeature = mock(EStructuralFeature.class);
		final EObject mockedEObject = mock(EObject.class);
		when(mockedEObject.eIsSet(mockedEStructuralFeature)).thenReturn(true);

		final TestObservableValue mockedObservable = mock(TestObservableValue.class);
		when(mockedObservable.getRealm()).thenReturn(realm);
		when(mockedObservable.getValueType()).thenReturn(mockedEStructuralFeature);
		when(mockedObservable.getObserved()).thenReturn(mockedEObject);
		when(mockedObservable.getValue()).thenReturn(initialValue);
		Mockito.doAnswer(new Answer<Void>() {

			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				listeners.add((IValueChangeListener) invocation.getArguments()[0]);
				return null;
			}
		}).when(mockedObservable).addValueChangeListener(any(IValueChangeListener.class));

		final DateTime[] widgets = setUpDatabindingTest(mockedObservable);
		final DateTime dateWidget = widgets[0];
		final DateTime timeWidget = widgets[1];

		when(mockedObservable.getValue()).thenReturn(changedValue);
		for (final IValueChangeListener valueChangeListener : listeners) {
			valueChangeListener.handleValueChange(new ValueChangeEvent(mockedObservable, Diffs.createValueDiff(
				initialValue, changedValue)));
		}

		assertTrue(isDateEqualToDateTimes(changedValue, dateWidget, timeWidget));

	}

	@Test
	public void testDatabindingServiceUsageChangeControl() throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption, DatabindingFailedException {
		final Date initialValue = new Date();
		final Date changedValue = new Date(System.currentTimeMillis() * 2);
		final Date[] setValue = new Date[1];
		final EStructuralFeature mockedEStructuralFeature = mock(EStructuralFeature.class);
		final EObject mockedEObject = mock(EObject.class);
		when(mockedEObject.eIsSet(mockedEStructuralFeature)).thenReturn(true);

		final TestObservableValue mockedObservable = mock(TestObservableValue.class);
		when(mockedObservable.getRealm()).thenReturn(realm);
		when(mockedObservable.getValueType()).thenReturn(mockedEStructuralFeature);
		when(mockedObservable.getObserved()).thenReturn(mockedEObject);
		when(mockedObservable.getValue()).thenReturn(initialValue);
		Mockito.doAnswer(new Answer<Void>() {

			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				setValue[0] = (Date) invocation.getArguments()[0];
				return null;
			}
		}).when(mockedObservable).setValue(any(Object.class));
		final DateTime[] widgets = setUpDatabindingTest(mockedObservable);
		final DateTime dateWidget = widgets[0];
		final DateTime timeWidget = widgets[1];

		final Calendar cal = Calendar.getInstance();
		cal.setTime(changedValue);
		dateWidget.setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
		// dateWidget.notifyListeners(SWT.Selection, new Event());
		timeWidget.setTime(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
		timeWidget.notifyListeners(SWT.Selection, new Event());

		// mockedObservable.getValue() cannot be compared directly to changedValue because the widgets can't save
		// milliseconds. From this it follows that mockedObservable.getValue() and changedValue might not be equal
		// even though the binding is working.

		final Calendar calChangedValue = Calendar.getInstance();
		calChangedValue.setTime(changedValue);
		calChangedValue.set(Calendar.MILLISECOND, 0);

		final Calendar calSetValue = Calendar.getInstance();
		calSetValue.setTime(setValue[0]);
		calSetValue.set(Calendar.MILLISECOND, 0);

		assertTrue(calChangedValue.equals(calSetValue));
	}

	/**
	 * Universal set up stuff for the data binding test cases.
	 *
	 * @param mockedObservable
	 * @return A DateTime array. DateTime[0] contains the date widget and DateTime[1] the time widget.
	 * @throws NoRendererFoundException
	 * @throws NoPropertyDescriptorFoundExeption
	 * @throws DatabindingFailedException
	 */
	private DateTime[] setUpDatabindingTest(final IObservableValue mockedObservable) throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption, DatabindingFailedException {
		mockDatabindingIsUnsettable();
		when(databindingService.getObservableValue(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
			mockedObservable);

		renderer.init();
		final Control renderControl = renderControl(new SWTGridCell(0, 2, renderer));

		final Composite composite = (Composite) renderControl;
		final Composite stackComposite = (Composite) composite.getChildren()[0];
		final Composite dateTimeComposite = (Composite) stackComposite.getChildren()[0];
		final DateTime dateWidget = (DateTime) dateTimeComposite.getChildren()[0];
		final DateTime timeWidget = (DateTime) dateTimeComposite.getChildren()[1];
		final DateTime[] widgets = new DateTime[] { dateWidget, timeWidget };

		return widgets;
	}

	private boolean isDateEqualToDateTimes(Date date, DateTime dateWidget, DateTime timeWidget) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		final int dateYear = cal.get(Calendar.YEAR);
		final int dateMonth = cal.get(Calendar.MONTH);
		final int dateDay = cal.get(Calendar.DAY_OF_MONTH);
		final int dateHour = cal.get(Calendar.HOUR_OF_DAY);
		final int dateMinute = cal.get(Calendar.MINUTE);
		final int dateSeconds = cal.get(Calendar.SECOND);

		final int widgetYear = dateWidget.getYear();
		final int widgetMonth = dateWidget.getMonth();
		final int widgetDay = dateWidget.getDay();
		final int widgetHour = timeWidget.getHours();
		final int widgetMinute = timeWidget.getMinutes();
		final int widgetSeconds = timeWidget.getSeconds();

		if (dateYear == widgetYear && dateMonth == widgetMonth && dateDay == widgetDay
			&& dateHour == widgetHour && dateMinute == widgetMinute && dateSeconds == widgetSeconds) {
			return true;
		}
		return false;
	}

	/**
	 * Tests whether the {@link EMFFormsLabelProvider} is used to get the labels of the control.
	 *
	 * @throws NoRendererFoundException
	 * @throws NoPropertyDescriptorFoundExeption
	 * @throws DatabindingFailedException
	 */
	@Test
	public void testLabelServiceUsage() throws NoRendererFoundException, NoPropertyDescriptorFoundExeption,
		DatabindingFailedException {
		labelServiceUsage();
	}
}
