/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.core.swt.renderer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;

import org.eclipse.core.databinding.observable.Observables;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.test.common.DefaultRealm;
import org.eclipse.emf.ecp.view.internal.core.swt.renderer.AbstractControl_PTest;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.ecp.view.template.style.unsettable.model.ButtonAlignmentType;
import org.eclipse.emf.ecp.view.template.style.unsettable.model.ButtonPlacementType;
import org.eclipse.emf.ecp.view.template.style.unsettable.model.VTUnsettableFactory;
import org.eclipse.emf.ecp.view.template.style.unsettable.model.VTUnsettableStyleProperty;
import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.BowlingPackage;
import org.eclipse.emf.emfstore.bowling.Fan;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.core.services.label.NoLabelFoundException;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit tests that test whether the (un)set button is properly aligned for unsettable attributes when an
 * {@link VTUnsettableStyleProperty} is configured and defines the button's alignment.
 *
 * @author Lucas Koehler
 *
 */
public class TextControlRendererUnsettable_PTest extends AbstractControl_PTest {

	private DefaultRealm realm;
	private VTUnsettableStyleProperty unsettableStyleProperty;
	private Fan eObject;

	@SuppressWarnings("rawtypes")
	@Before
	public void before() throws DatabindingFailedException, NoLabelFoundException {
		realm = new DefaultRealm();
		final ReportService reportService = mock(ReportService.class);
		setDatabindingService(mock(EMFFormsDatabinding.class));
		setLabelProvider(mock(EMFFormsLabelProvider.class));
		final VTViewTemplateProvider viewTemplateProvider = mock(VTViewTemplateProvider.class);
		setTemplateProvider(viewTemplateProvider);
		final EMFFormsEditSupport editSupport = mock(EMFFormsEditSupport.class);
		setup();

		final HashSet<VTStyleProperty> styleProperties = new HashSet<VTStyleProperty>();
		unsettableStyleProperty = VTUnsettableFactory.eINSTANCE.createUnsettableStyleProperty();
		styleProperties.add(unsettableStyleProperty);
		when(viewTemplateProvider.getStyleProperties(getvControl(), getContext())).thenReturn(styleProperties);
		setRenderer(new TextControlSWTRenderer(getvControl(), getContext(), reportService, getDatabindingService(),
			getLabelProvider(),
			getTemplateProvider(), editSupport));

		final IObservableValue testDisplayName = Observables.constantObservableValue("test-displayname", String.class);
		when(getLabelProvider().getDisplayName(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
			testDisplayName);

		getRenderer().init();
	}

	@After
	public void testTearDown() {
		realm.dispose();
		dispose();
	}

	@Override
	protected void mockDatabindingIsUnsettable() throws DatabindingFailedException {
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.internal.core.swt.renderer.AbstractControl_PTest#mockControl()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected void mockControl() throws DatabindingFailedException {
		eObject = BowlingFactory.eINSTANCE.createFan();
		final EStructuralFeature eStructuralFeature = BowlingPackage.eINSTANCE.getFan_Name();
		final TestObservableValue mockedObservableValue = mock(TestObservableValue.class);
		when(mockedObservableValue.getRealm()).thenReturn(realm);
		when(mockedObservableValue.getObserved()).thenReturn(eObject);
		when(mockedObservableValue.getValueType()).thenReturn(eStructuralFeature);
		when(getDatabindingService().getObservableValue(any(VDomainModelReference.class), any(EObject.class)))
			.thenReturn(mockedObservableValue);

		final IValueProperty valueProperty = mock(IValueProperty.class);
		when(valueProperty.getValueType()).thenReturn(eStructuralFeature);
		when(getDatabindingService().getValueProperty(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
			valueProperty);
		super.mockControl(eObject, eStructuralFeature);
	}

	@Test
	public void unsetButtonAlignmentLeftUnset() throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		unsettableStyleProperty.setButtonAlignment(ButtonAlignmentType.LEFT);
		final Control render = renderControl(new SWTGridCell(0, 2, getRenderer()));
		final Composite renderedComposite = (Composite) render;
		final Composite controlComposite = (Composite) renderedComposite.getChildren()[0];
		final GridData gridData = (GridData) controlComposite.getLayoutData();
		assertFalse(gridData.grabExcessHorizontalSpace);
	}

	@Test
	public void unsetButtonAlignmentLeftUnsetToSetToUnset()
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		unsettableStyleProperty.setButtonAlignment(ButtonAlignmentType.LEFT);
		final Control render = renderControl(new SWTGridCell(0, 2, getRenderer()));
		final Composite renderedComposite = (Composite) render;
		final Composite controlComposite = (Composite) renderedComposite.getChildren()[0];
		final GridData gridData = (GridData) controlComposite.getLayoutData();
		assertFalse(gridData.grabExcessHorizontalSpace);
		final Button unsetButton = (Button) renderedComposite.getChildren()[1];
		// simulate button click to set attribute
		unsetButton.notifyListeners(SWT.Selection, new Event());
		final GridData gridData2 = (GridData) controlComposite.getLayoutData();
		assertTrue(gridData2.grabExcessHorizontalSpace);
		// simulate button click to unset attribute again
		unsetButton.notifyListeners(SWT.Selection, new Event());
		final GridData gridData3 = (GridData) controlComposite.getLayoutData();
		assertFalse(gridData3.grabExcessHorizontalSpace);
	}

	@Test
	public void unsetButtonAlignmentLeftSet() throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		unsettableStyleProperty.setButtonAlignment(ButtonAlignmentType.LEFT);
		eObject.setName("TEST");
		final Control render = renderControl(new SWTGridCell(0, 2, getRenderer()));
		final Composite renderedComposite = (Composite) render;
		final Composite controlComposite = (Composite) renderedComposite.getChildren()[0];
		final GridData gridData = (GridData) controlComposite.getLayoutData();
		assertTrue(gridData.grabExcessHorizontalSpace);
	}

	@Test
	public void unsetButtonAlignmentLeftSetToUnsetToSet()
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		unsettableStyleProperty.setButtonAlignment(ButtonAlignmentType.LEFT);
		eObject.setName("TEST");
		final Control render = renderControl(new SWTGridCell(0, 2, getRenderer()));
		final Composite renderedComposite = (Composite) render;
		final Composite controlComposite = (Composite) renderedComposite.getChildren()[0];
		final GridData gridData = (GridData) controlComposite.getLayoutData();
		assertTrue(gridData.grabExcessHorizontalSpace);
		final Button unsetButton = (Button) renderedComposite.getChildren()[1];
		// simulate button click to set attribute
		unsetButton.notifyListeners(SWT.Selection, new Event());
		final GridData gridData2 = (GridData) controlComposite.getLayoutData();
		assertFalse(gridData2.grabExcessHorizontalSpace);
		// simulate button click to unset attribute again
		unsetButton.notifyListeners(SWT.Selection, new Event());
		final GridData gridData3 = (GridData) controlComposite.getLayoutData();
		assertTrue(gridData3.grabExcessHorizontalSpace);
	}

	@Test
	public void unsetButtonAlignmentRightUnset() throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		unsettableStyleProperty.setButtonAlignment(ButtonAlignmentType.RIGHT);
		final Control render = renderControl(new SWTGridCell(0, 2, getRenderer()));
		final Composite renderedComposite = (Composite) render;
		final Composite controlComposite = (Composite) renderedComposite.getChildren()[0];
		final GridData gridData = (GridData) controlComposite.getLayoutData();
		assertTrue(gridData.grabExcessHorizontalSpace);
	}

	@Test
	public void unsetButtonAlignmentRightUnsetToSetToUnset()
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		unsettableStyleProperty.setButtonAlignment(ButtonAlignmentType.RIGHT);
		final Control render = renderControl(new SWTGridCell(0, 2, getRenderer()));
		final Composite renderedComposite = (Composite) render;
		final Composite controlComposite = (Composite) renderedComposite.getChildren()[0];
		final GridData gridData = (GridData) controlComposite.getLayoutData();
		assertTrue(gridData.grabExcessHorizontalSpace);
		final Button unsetButton = (Button) renderedComposite.getChildren()[1];
		// simulate button click to set attribute
		unsetButton.notifyListeners(SWT.Selection, new Event());
		final GridData gridData2 = (GridData) controlComposite.getLayoutData();
		assertTrue(gridData2.grabExcessHorizontalSpace);
		// simulate button click to unset attribute again
		unsetButton.notifyListeners(SWT.Selection, new Event());
		final GridData gridData3 = (GridData) controlComposite.getLayoutData();
		assertTrue(gridData3.grabExcessHorizontalSpace);
	}

	@Test
	public void unsetButtonAlignmentRightSet() throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		unsettableStyleProperty.setButtonAlignment(ButtonAlignmentType.RIGHT);
		eObject.setName("TEST");
		final Control render = renderControl(new SWTGridCell(0, 2, getRenderer()));
		final Composite renderedComposite = (Composite) render;
		final Composite controlComposite = (Composite) renderedComposite.getChildren()[0];
		final GridData gridData = (GridData) controlComposite.getLayoutData();
		assertTrue(gridData.grabExcessHorizontalSpace);
	}

	@Test
	public void unsetButtonAlignmentRightSetToUnsetToSet()
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		unsettableStyleProperty.setButtonAlignment(ButtonAlignmentType.RIGHT);
		eObject.setName("TEST");
		final Control render = renderControl(new SWTGridCell(0, 2, getRenderer()));
		final Composite renderedComposite = (Composite) render;
		final Composite controlComposite = (Composite) renderedComposite.getChildren()[0];
		final GridData gridData = (GridData) controlComposite.getLayoutData();
		assertTrue(gridData.grabExcessHorizontalSpace);
		final Button unsetButton = (Button) renderedComposite.getChildren()[1];
		// simulate button click to set attribute
		unsetButton.notifyListeners(SWT.Selection, new Event());
		final GridData gridData2 = (GridData) controlComposite.getLayoutData();
		assertTrue(gridData2.grabExcessHorizontalSpace);
		// simulate button click to unset attribute again
		unsetButton.notifyListeners(SWT.Selection, new Event());
		final GridData gridData3 = (GridData) controlComposite.getLayoutData();
		assertTrue(gridData3.grabExcessHorizontalSpace);
	}

	@Test
	public void unsetButtonPlacementLeftAndAlignmentRight()
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		unsettableStyleProperty.setButtonPlacement(ButtonPlacementType.LEFT_OF_LABEL);
		unsettableStyleProperty.setButtonAlignment(ButtonAlignmentType.RIGHT);
		final Control render = renderControl(new SWTGridCell(0, 2, getRenderer()));
		final Composite renderedComposite = (Composite) render;
		assertTrue(renderedComposite.getChildren()[0] instanceof Button);
		assertTrue(renderedComposite.getChildren()[1] instanceof Composite);
		final Composite controlComposite = (Composite) renderedComposite.getChildren()[1];
		final GridData gridData = (GridData) controlComposite.getLayoutData();
		assertTrue(gridData.grabExcessHorizontalSpace);

		// simulate button click to set attribute
		final Button unsetButton = (Button) renderedComposite.getChildren()[0];
		unsetButton.notifyListeners(SWT.Selection, new Event());
		assertTrue(renderedComposite.getChildren()[0] instanceof Button);
		// controlComposite should not change
		assertTrue(renderedComposite.getChildren()[1] == controlComposite);
		final GridData gridData2 = (GridData) controlComposite.getLayoutData();
		assertTrue(gridData2.grabExcessHorizontalSpace);
	}

	@Test
	public void unsetButtonPlacementLeftAndAlignmentLeft()
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		// The button alignment should make no difference when the button is placed on the left
		unsettableStyleProperty.setButtonPlacement(ButtonPlacementType.LEFT_OF_LABEL);
		unsettableStyleProperty.setButtonAlignment(ButtonAlignmentType.LEFT);
		final Control render = renderControl(new SWTGridCell(0, 2, getRenderer()));
		final Composite renderedComposite = (Composite) render;
		assertTrue(renderedComposite.getChildren()[0] instanceof Button);
		assertTrue(renderedComposite.getChildren()[1] instanceof Composite);
		final Composite controlComposite = (Composite) renderedComposite.getChildren()[1];
		final GridData gridData = (GridData) controlComposite.getLayoutData();
		assertTrue(gridData.grabExcessHorizontalSpace);

		// simulate button click to set attribute
		final Button unsetButton = (Button) renderedComposite.getChildren()[0];
		unsetButton.notifyListeners(SWT.Selection, new Event());
		assertTrue(renderedComposite.getChildren()[0] instanceof Button);
		// controlComposite should not change
		assertTrue(renderedComposite.getChildren()[1] == controlComposite);
		final GridData gridData2 = (GridData) controlComposite.getLayoutData();
		assertTrue(gridData2.grabExcessHorizontalSpace);
	}

	@Test
	public void unsetButtonPlacementRightAndAlignmentRight()
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		// The button alignment should make no difference
		unsettableStyleProperty.setButtonPlacement(ButtonPlacementType.RIGHT_OF_LABEL);
		unsettableStyleProperty.setButtonAlignment(ButtonAlignmentType.RIGHT);
		final Control render = renderControl(new SWTGridCell(0, 2, getRenderer()));
		final Composite renderedComposite = (Composite) render;
		assertTrue(renderedComposite.getChildren()[0] instanceof Composite);
		assertTrue(renderedComposite.getChildren()[1] instanceof Button);

		final Composite controlComposite = (Composite) renderedComposite.getChildren()[0];
		final GridData gridData = (GridData) controlComposite.getLayoutData();
		assertTrue(gridData.grabExcessHorizontalSpace);

		// simulate button click to set attribute
		final Button unsetButton = (Button) renderedComposite.getChildren()[1];
		unsetButton.notifyListeners(SWT.Selection, new Event());
		// controlComposite should not change
		assertTrue(renderedComposite.getChildren()[0] == controlComposite);
		assertTrue(renderedComposite.getChildren()[1] instanceof Button);
		final GridData gridData2 = (GridData) controlComposite.getLayoutData();
		assertTrue(gridData2.grabExcessHorizontalSpace);
	}

	@Test
	public void unsetButtonPlacementRightAndAlignmentLeft()
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		// The button alignment should make no difference
		unsettableStyleProperty.setButtonPlacement(ButtonPlacementType.RIGHT_OF_LABEL);
		unsettableStyleProperty.setButtonAlignment(ButtonAlignmentType.LEFT);
		final Control render = renderControl(new SWTGridCell(0, 2, getRenderer()));
		final Composite renderedComposite = (Composite) render;
		assertTrue(renderedComposite.getChildren()[0] instanceof Composite);
		assertTrue(renderedComposite.getChildren()[1] instanceof Button);

		final Composite controlComposite = (Composite) renderedComposite.getChildren()[0];
		final GridData gridData = (GridData) controlComposite.getLayoutData();
		assertFalse(gridData.grabExcessHorizontalSpace);

		// simulate button click to set attribute
		final Button unsetButton = (Button) renderedComposite.getChildren()[1];
		unsetButton.notifyListeners(SWT.Selection, new Event());
		// controlComposite should not change
		assertTrue(renderedComposite.getChildren()[0] == controlComposite);
		assertTrue(renderedComposite.getChildren()[1] instanceof Button);

		final GridData gridData2 = (GridData) controlComposite.getLayoutData();
		assertTrue(gridData2.grabExcessHorizontalSpace);
	}
}
