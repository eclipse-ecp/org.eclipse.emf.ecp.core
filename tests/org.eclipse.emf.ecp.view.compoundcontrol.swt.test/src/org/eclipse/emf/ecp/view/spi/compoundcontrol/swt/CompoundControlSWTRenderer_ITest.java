/**
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.compoundcontrol.swt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Set;

import org.eclipse.core.databinding.observable.Observables;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.test.common.DefaultRealm;
import org.eclipse.emf.ecp.view.spi.compoundcontrol.model.VCompoundControl;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.LabelAlignment;
import org.eclipse.emf.ecp.view.spi.model.VContainedElement;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.ecp.view.template.style.alignment.model.AlignmentType;
import org.eclipse.emf.ecp.view.template.style.alignment.model.VTAlignmentFactory;
import org.eclipse.emf.ecp.view.template.style.alignment.model.VTControlLabelAlignmentStyleProperty;
import org.eclipse.emf.ecp.view.template.style.labelwidth.model.VTLabelWidthStyleProperty;
import org.eclipse.emf.ecp.view.template.style.labelwidth.model.VTLabelwidthFactory;
import org.eclipse.emf.ecp.view.template.style.wrap.model.VTLabelWrapStyleProperty;
import org.eclipse.emf.ecp.view.template.style.wrap.model.VTWrapFactory;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.core.services.label.NoLabelFoundException;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.EMFFormsNoRendererException;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererFactory;
import org.eclipse.emfforms.spi.swt.core.layout.GridDescriptionFactory;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class CompoundControlSWTRenderer_ITest {

	private static final String LABEL1 = "label1"; //$NON-NLS-1$
	private static final String LABEL2 = "label2"; //$NON-NLS-1$

	private static final String TOOLTIP1 = "tooltip1"; //$NON-NLS-1$
	private static final String TOOLTIP2 = "tooltip2"; //$NON-NLS-1$

	private static final String C_CONTROL = "CControl"; //$NON-NLS-1$
	private static final String C_VALIDATION = "CValidation"; //$NON-NLS-1$

	private VCompoundControl compoundControl;
	private ViewModelContext viewModelContext;
	private ReportService reportService;
	private EMFFormsLabelProvider emfFormsLabelProvider;
	private EMFFormsRendererFactory emfFormsRendererFactory;
	private VTViewTemplateProvider viewTemplateProvider;
	private Shell shell;
	private DefaultRealm realm;
	private EMFFormsDatabindingEMF emfFormsDatabindingEMF;

	@Before
	public void before() {
		realm = new DefaultRealm();
		shell = new Shell(Display.getDefault());
		compoundControl = mock(VCompoundControl.class);
		viewModelContext = mock(ViewModelContext.class);
		reportService = mock(ReportService.class);
		emfFormsLabelProvider = mock(EMFFormsLabelProvider.class);
		emfFormsRendererFactory = mock(EMFFormsRendererFactory.class);
		viewTemplateProvider = Mockito.mock(VTViewTemplateProvider.class);
		emfFormsDatabindingEMF = Mockito.mock(EMFFormsDatabindingEMF.class);
	}

	@After
	public void after() {
		shell.dispose();
		realm.dispose();
	}

	private CompoundControlSWTRenderer createRenderer() {
		return new CompoundControlSWTRenderer(compoundControl, viewModelContext, reportService, emfFormsLabelProvider,
			emfFormsRendererFactory, viewTemplateProvider, emfFormsDatabindingEMF);
	}

	@Test
	public void testGetLabelProvider() {
		final CompoundControlSWTRenderer renderer = createRenderer();
		assertSame(emfFormsLabelProvider, renderer.getLabelProvider());
	}

	@Test
	public void testGetRendererFactory() {
		final CompoundControlSWTRenderer renderer = createRenderer();
		assertSame(emfFormsRendererFactory, renderer.getRendererFactory());
	}

	@Test
	public void testGetGridDescription() {
		when(compoundControl.getLabelAlignment()).thenReturn(LabelAlignment.DEFAULT);

		final CompoundControlSWTRenderer renderer = createRenderer();
		final SWTGridDescription gridDescription = renderer.getGridDescription(mock(SWTGridDescription.class));
		assertEquals(3, gridDescription.getColumns());
		assertEquals(1, gridDescription.getRows());
		assertEquals(3, gridDescription.getGrid().size());

		final SWTGridCell label = gridDescription.getGrid().get(0);
		assertEquals(0, label.getColumn());
		assertEquals(1, label.getHorizontalSpan());
		assertEquals(0, label.getRow());
		assertSame(renderer, label.getRenderer());
		assertFalse(label.isHorizontalFill());
		assertFalse(label.isHorizontalGrab());
		assertFalse(label.isVerticalFill());
		assertFalse(label.isVerticalGrab());

		final SWTGridCell validation = gridDescription.getGrid().get(1);
		assertEquals(1, validation.getColumn());
		assertEquals(1, validation.getHorizontalSpan());
		assertEquals(0, validation.getRow());
		assertSame(renderer, validation.getRenderer());
		assertFalse(validation.isHorizontalFill());
		assertFalse(validation.isHorizontalGrab());
		assertFalse(validation.isVerticalFill());
		assertFalse(validation.isVerticalGrab());

		final SWTGridCell controls = gridDescription.getGrid().get(2);
		assertEquals(2, controls.getColumn());
		assertEquals(1, controls.getHorizontalSpan());
		assertEquals(0, controls.getRow());
		assertSame(renderer, controls.getRenderer());
		assertTrue(controls.isHorizontalFill());
		assertTrue(controls.isHorizontalGrab());
		assertTrue(controls.isVerticalFill());
		assertFalse(controls.isVerticalGrab());
	}

	@Test
	public void testGetGridDescriptionWithWidth() {
		when(compoundControl.getLabelAlignment()).thenReturn(LabelAlignment.DEFAULT);

		final VTLabelWidthStyleProperty property = VTLabelwidthFactory.eINSTANCE.createLabelWidthStyleProperty();
		property.setWidth(11);
		final Set<VTStyleProperty> properties = Collections.<VTStyleProperty> singleton(property);
		Mockito.when(viewTemplateProvider.getStyleProperties(compoundControl, viewModelContext)).thenReturn(properties);

		final CompoundControlSWTRenderer renderer = createRenderer();
		final SWTGridDescription gridDescription = renderer.getGridDescription(mock(SWTGridDescription.class));
		assertEquals(3, gridDescription.getColumns());
		assertEquals(1, gridDescription.getRows());
		assertEquals(3, gridDescription.getGrid().size());

		final SWTGridCell label = gridDescription.getGrid().get(0);
		assertEquals(0, label.getColumn());
		assertEquals(1, label.getHorizontalSpan());
		assertEquals(0, label.getRow());
		assertSame(renderer, label.getRenderer());
		assertFalse(label.isHorizontalFill());
		assertFalse(label.isHorizontalGrab());
		assertFalse(label.isVerticalFill());
		assertFalse(label.isVerticalGrab());
		assertEquals(11, label.getPreferredSize().x);
		assertEquals(SWT.DEFAULT, label.getPreferredSize().y);

		final SWTGridCell validation = gridDescription.getGrid().get(1);
		assertEquals(1, validation.getColumn());
		assertEquals(1, validation.getHorizontalSpan());
		assertEquals(0, validation.getRow());
		assertSame(renderer, validation.getRenderer());
		assertFalse(validation.isHorizontalFill());
		assertFalse(validation.isHorizontalGrab());
		assertFalse(validation.isVerticalFill());
		assertFalse(validation.isVerticalGrab());

		final SWTGridCell controls = gridDescription.getGrid().get(2);
		assertEquals(2, controls.getColumn());
		assertEquals(1, controls.getHorizontalSpan());
		assertEquals(0, controls.getRow());
		assertSame(renderer, controls.getRenderer());
		assertTrue(controls.isHorizontalFill());
		assertTrue(controls.isHorizontalGrab());
		assertTrue(controls.isVerticalFill());
		assertFalse(controls.isVerticalGrab());
	}

	@Test
	public void testGetGridDescriptionControlLabelAlignmentLeft() {
		when(compoundControl.getLabelAlignment()).thenReturn(LabelAlignment.LEFT);

		final CompoundControlSWTRenderer renderer = createRenderer();
		final SWTGridDescription gridDescription = renderer.getGridDescription(mock(SWTGridDescription.class));
		assertEquals(3, gridDescription.getColumns());
		assertEquals(1, gridDescription.getRows());
		assertEquals(3, gridDescription.getGrid().size());

		final SWTGridCell label = gridDescription.getGrid().get(0);
		assertEquals(0, label.getColumn());
		assertEquals(1, label.getHorizontalSpan());
		assertEquals(0, label.getRow());
		assertSame(renderer, label.getRenderer());
		assertFalse(label.isHorizontalFill());
		assertFalse(label.isHorizontalGrab());
		assertFalse(label.isVerticalFill());
		assertFalse(label.isVerticalGrab());

		final SWTGridCell validation = gridDescription.getGrid().get(1);
		assertEquals(1, validation.getColumn());
		assertEquals(1, validation.getHorizontalSpan());
		assertEquals(0, validation.getRow());
		assertSame(renderer, validation.getRenderer());
		assertFalse(validation.isHorizontalFill());
		assertFalse(validation.isHorizontalGrab());
		assertFalse(validation.isVerticalFill());
		assertFalse(validation.isVerticalGrab());

		final SWTGridCell controls = gridDescription.getGrid().get(2);
		assertEquals(2, controls.getColumn());
		assertEquals(1, controls.getHorizontalSpan());
		assertEquals(0, controls.getRow());
		assertSame(renderer, controls.getRenderer());
		assertTrue(controls.isHorizontalFill());
		assertTrue(controls.isHorizontalGrab());
		assertTrue(controls.isVerticalFill());
		assertFalse(controls.isVerticalGrab());
	}

	@Test
	public void testGetGridDescriptionControlLabelAlignmentTop() {
		when(compoundControl.getLabelAlignment()).thenReturn(LabelAlignment.TOP);

		final CompoundControlSWTRenderer renderer = createRenderer();
		final SWTGridDescription gridDescription = renderer.getGridDescription(mock(SWTGridDescription.class));
		assertEquals(3, gridDescription.getColumns());
		assertEquals(1, gridDescription.getRows());
		assertEquals(3, gridDescription.getGrid().size());

		final SWTGridCell label = gridDescription.getGrid().get(0);
		assertEquals(0, label.getColumn());
		assertEquals(1, label.getHorizontalSpan());
		assertEquals(0, label.getRow());
		assertSame(renderer, label.getRenderer());
		assertFalse(label.isHorizontalFill());
		assertFalse(label.isHorizontalGrab());
		assertFalse(label.isVerticalFill());
		assertFalse(label.isVerticalGrab());

		final SWTGridCell validation = gridDescription.getGrid().get(1);
		assertEquals(1, validation.getColumn());
		assertEquals(1, validation.getHorizontalSpan());
		assertEquals(0, validation.getRow());
		assertSame(renderer, validation.getRenderer());
		assertFalse(validation.isHorizontalFill());
		assertFalse(validation.isHorizontalGrab());
		assertFalse(validation.isVerticalFill());
		assertFalse(validation.isVerticalGrab());

		final SWTGridCell controls = gridDescription.getGrid().get(2);
		assertEquals(2, controls.getColumn());
		assertEquals(1, controls.getHorizontalSpan());
		assertEquals(0, controls.getRow());
		assertSame(renderer, controls.getRenderer());
		assertTrue(controls.isHorizontalFill());
		assertTrue(controls.isHorizontalGrab());
		assertTrue(controls.isVerticalFill());
		assertFalse(controls.isVerticalGrab());

		/* assert that top was set to default because not supported */
		verify(compoundControl, times(1)).setLabelAlignment(LabelAlignment.DEFAULT);
	}

	@Test
	public void testGetGridDescriptionControlLabelAlignmentNone() {
		when(compoundControl.getLabelAlignment()).thenReturn(LabelAlignment.NONE);

		final CompoundControlSWTRenderer renderer = createRenderer();
		final SWTGridDescription gridDescription = renderer.getGridDescription(mock(SWTGridDescription.class));
		assertEquals(2, gridDescription.getColumns());
		assertEquals(1, gridDescription.getRows());
		assertEquals(2, gridDescription.getGrid().size());

		final SWTGridCell validation = gridDescription.getGrid().get(0);
		assertEquals(0, validation.getColumn());
		assertEquals(1, validation.getHorizontalSpan());
		assertEquals(0, validation.getRow());
		assertSame(renderer, validation.getRenderer());
		assertFalse(validation.isHorizontalFill());
		assertFalse(validation.isHorizontalGrab());
		assertFalse(validation.isVerticalFill());
		assertFalse(validation.isVerticalGrab());

		final SWTGridCell controls = gridDescription.getGrid().get(1);
		assertEquals(1, controls.getColumn());
		assertEquals(1, controls.getHorizontalSpan());
		assertEquals(0, controls.getRow());
		assertSame(renderer, controls.getRenderer());
		assertTrue(controls.isHorizontalFill());
		assertTrue(controls.isHorizontalGrab());
		assertTrue(controls.isVerticalFill());
		assertFalse(controls.isVerticalGrab());
	}

	@Test
	public void testCreateLabel() throws NoLabelFoundException, DatabindingFailedException {
		/* setup */
		final VControl control1 = mock(VControl.class);
		final VFeaturePathDomainModelReference dmr1 = mock(VFeaturePathDomainModelReference.class);
		when(control1.getDomainModelReference()).thenReturn(dmr1);

		final VControl control2 = mock(VControl.class);
		final VFeaturePathDomainModelReference dmr2 = mock(VFeaturePathDomainModelReference.class);
		when(control2.getDomainModelReference()).thenReturn(dmr2);

		when(compoundControl.getControls()).thenReturn(ECollections.asEList(control1, control2));

		final EObject domainModel = mock(EObject.class);
		when(viewModelContext.getDomainModel()).thenReturn(domainModel);

		when(emfFormsLabelProvider.getDisplayName(dmr1, domainModel))
			.thenReturn(Observables.constantObservableValue(LABEL1, String.class));

		when(emfFormsLabelProvider.getDisplayName(dmr2, domainModel))
			.thenReturn(Observables.constantObservableValue(LABEL2, String.class));

		when(emfFormsLabelProvider.getDescription(dmr1, domainModel))
			.thenReturn(Observables.constantObservableValue(TOOLTIP1, String.class));

		when(emfFormsLabelProvider.getDescription(dmr2, domainModel))
			.thenReturn(Observables.constantObservableValue(TOOLTIP2, String.class));

		final IEMFValueProperty valueProperty1 = mock(IEMFValueProperty.class);
		when(emfFormsDatabindingEMF.getValueProperty(dmr1, domainModel)).thenReturn(valueProperty1);
		final EStructuralFeature structuralFeature1 = mock(EStructuralFeature.class);
		when(valueProperty1.getStructuralFeature()).thenReturn(structuralFeature1);

		final IEMFValueProperty valueProperty2 = mock(IEMFValueProperty.class);
		when(emfFormsDatabindingEMF.getValueProperty(dmr2, domainModel)).thenReturn(valueProperty2);
		final EStructuralFeature structuralFeature2 = mock(EStructuralFeature.class);
		when(valueProperty2.getStructuralFeature()).thenReturn(structuralFeature2);

		final CompoundControlSWTRenderer renderer = createRenderer();

		/* act */
		final Control label = renderer.createLabel(shell);

		/* assert */
		assertTrue(Label.class.isInstance(label));
		final Label labelComposite = Label.class.cast(label);
		assertEquals(LABEL1 + " / " + LABEL2, labelComposite.getText()); //$NON-NLS-1$
		assertEquals(TOOLTIP1 + "\n" + TOOLTIP2, labelComposite.getToolTipText()); //$NON-NLS-1$
	}

	@Test
	public void testCreateLabelAlignmentDefault() throws NoLabelFoundException, DatabindingFailedException {
		/* setup */
		final VControl control1 = mock(VControl.class);
		final VFeaturePathDomainModelReference dmr1 = mock(VFeaturePathDomainModelReference.class);
		when(control1.getDomainModelReference()).thenReturn(dmr1);

		final VControl control2 = mock(VControl.class);
		final VFeaturePathDomainModelReference dmr2 = mock(VFeaturePathDomainModelReference.class);
		when(control2.getDomainModelReference()).thenReturn(dmr2);

		when(compoundControl.getControls()).thenReturn(ECollections.asEList(control1, control2));

		final EObject domainModel = mock(EObject.class);
		when(viewModelContext.getDomainModel()).thenReturn(domainModel);

		when(emfFormsLabelProvider.getDisplayName(dmr1, domainModel))
			.thenReturn(Observables.constantObservableValue(LABEL1, String.class));

		when(emfFormsLabelProvider.getDisplayName(dmr2, domainModel))
			.thenReturn(Observables.constantObservableValue(LABEL2, String.class));

		when(emfFormsLabelProvider.getDescription(dmr1, domainModel))
			.thenReturn(Observables.constantObservableValue(TOOLTIP1, String.class));

		when(emfFormsLabelProvider.getDescription(dmr2, domainModel))
			.thenReturn(Observables.constantObservableValue(TOOLTIP2, String.class));

		final IEMFValueProperty valueProperty1 = mock(IEMFValueProperty.class);
		when(emfFormsDatabindingEMF.getValueProperty(dmr1, domainModel)).thenReturn(valueProperty1);
		final EStructuralFeature structuralFeature1 = mock(EStructuralFeature.class);
		when(valueProperty1.getStructuralFeature()).thenReturn(structuralFeature1);

		final IEMFValueProperty valueProperty2 = mock(IEMFValueProperty.class);
		when(emfFormsDatabindingEMF.getValueProperty(dmr2, domainModel)).thenReturn(valueProperty2);
		final EStructuralFeature structuralFeature2 = mock(EStructuralFeature.class);
		when(valueProperty2.getStructuralFeature()).thenReturn(structuralFeature2);

		final CompoundControlSWTRenderer renderer = createRenderer();

		/* act */
		final Control label = renderer.createLabel(shell);

		/* assert */
		final Label swtLabel = Label.class.cast(label);
		assertEquals(SWT.LEFT, swtLabel.getAlignment());
	}

	@Test
	public void testCreateLabelAlignmentLeft() throws NoLabelFoundException, DatabindingFailedException {
		/* setup */
		final VControl control1 = mock(VControl.class);
		final VFeaturePathDomainModelReference dmr1 = mock(VFeaturePathDomainModelReference.class);
		when(control1.getDomainModelReference()).thenReturn(dmr1);

		final VControl control2 = mock(VControl.class);
		final VFeaturePathDomainModelReference dmr2 = mock(VFeaturePathDomainModelReference.class);
		when(control2.getDomainModelReference()).thenReturn(dmr2);

		when(compoundControl.getControls()).thenReturn(ECollections.asEList(control1, control2));

		final EObject domainModel = mock(EObject.class);
		when(viewModelContext.getDomainModel()).thenReturn(domainModel);

		when(emfFormsLabelProvider.getDisplayName(dmr1, domainModel))
			.thenReturn(Observables.constantObservableValue(LABEL1, String.class));

		when(emfFormsLabelProvider.getDisplayName(dmr2, domainModel))
			.thenReturn(Observables.constantObservableValue(LABEL2, String.class));

		when(emfFormsLabelProvider.getDescription(dmr1, domainModel))
			.thenReturn(Observables.constantObservableValue(TOOLTIP1, String.class));

		when(emfFormsLabelProvider.getDescription(dmr2, domainModel))
			.thenReturn(Observables.constantObservableValue(TOOLTIP2, String.class));

		final IEMFValueProperty valueProperty1 = mock(IEMFValueProperty.class);
		when(emfFormsDatabindingEMF.getValueProperty(dmr1, domainModel)).thenReturn(valueProperty1);
		final EStructuralFeature structuralFeature1 = mock(EStructuralFeature.class);
		when(valueProperty1.getStructuralFeature()).thenReturn(structuralFeature1);

		final IEMFValueProperty valueProperty2 = mock(IEMFValueProperty.class);
		when(emfFormsDatabindingEMF.getValueProperty(dmr2, domainModel)).thenReturn(valueProperty2);
		final EStructuralFeature structuralFeature2 = mock(EStructuralFeature.class);

		final CompoundControlSWTRenderer renderer = createRenderer();

		final VTControlLabelAlignmentStyleProperty property = VTAlignmentFactory.eINSTANCE
			.createControlLabelAlignmentStyleProperty();
		property.setType(AlignmentType.LEFT);
		final Set<VTStyleProperty> properties = Collections.<VTStyleProperty> singleton(property);
		Mockito.when(viewTemplateProvider.getStyleProperties(compoundControl, viewModelContext)).thenReturn(properties);
		when(valueProperty2.getStructuralFeature()).thenReturn(structuralFeature2);

		/* act */
		final Control label = renderer.createLabel(shell);

		/* assert */
		final Label swtLabel = Label.class.cast(label);
		assertEquals(SWT.LEFT, swtLabel.getAlignment());
	}

	@Test
	public void testCreateLabelAlignmentRight() throws NoLabelFoundException, DatabindingFailedException {
		/* setup */
		final VControl control1 = mock(VControl.class);
		final VFeaturePathDomainModelReference dmr1 = mock(VFeaturePathDomainModelReference.class);
		when(control1.getDomainModelReference()).thenReturn(dmr1);

		final VControl control2 = mock(VControl.class);
		final VFeaturePathDomainModelReference dmr2 = mock(VFeaturePathDomainModelReference.class);
		when(control2.getDomainModelReference()).thenReturn(dmr2);

		when(compoundControl.getControls()).thenReturn(ECollections.asEList(control1, control2));

		final EObject domainModel = mock(EObject.class);
		when(viewModelContext.getDomainModel()).thenReturn(domainModel);

		when(emfFormsLabelProvider.getDisplayName(dmr1, domainModel))
			.thenReturn(Observables.constantObservableValue(LABEL1, String.class));

		when(emfFormsLabelProvider.getDisplayName(dmr2, domainModel))
			.thenReturn(Observables.constantObservableValue(LABEL2, String.class));

		when(emfFormsLabelProvider.getDescription(dmr1, domainModel))
			.thenReturn(Observables.constantObservableValue(TOOLTIP1, String.class));

		when(emfFormsLabelProvider.getDescription(dmr2, domainModel))
			.thenReturn(Observables.constantObservableValue(TOOLTIP2, String.class));

		final IEMFValueProperty valueProperty1 = mock(IEMFValueProperty.class);
		when(emfFormsDatabindingEMF.getValueProperty(dmr1, domainModel)).thenReturn(valueProperty1);
		final EStructuralFeature structuralFeature1 = mock(EStructuralFeature.class);
		when(valueProperty1.getStructuralFeature()).thenReturn(structuralFeature1);

		final IEMFValueProperty valueProperty2 = mock(IEMFValueProperty.class);
		when(emfFormsDatabindingEMF.getValueProperty(dmr2, domainModel)).thenReturn(valueProperty2);
		final EStructuralFeature structuralFeature2 = mock(EStructuralFeature.class);
		when(valueProperty2.getStructuralFeature()).thenReturn(structuralFeature2);

		final CompoundControlSWTRenderer renderer = createRenderer();

		final VTControlLabelAlignmentStyleProperty property = VTAlignmentFactory.eINSTANCE
			.createControlLabelAlignmentStyleProperty();
		property.setType(AlignmentType.RIGHT);
		final Set<VTStyleProperty> properties = Collections.<VTStyleProperty> singleton(property);
		Mockito.when(viewTemplateProvider.getStyleProperties(compoundControl, viewModelContext)).thenReturn(properties);

		/* act */
		final Control label = renderer.createLabel(shell);

		/* assert */
		final Label swtLabel = Label.class.cast(label);
		assertEquals(SWT.RIGHT, swtLabel.getAlignment());
	}

	// TODO
	@Test
	public void testCreateLabelWrapDefault() throws NoLabelFoundException, DatabindingFailedException {
		/* setup */
		final VControl control1 = mock(VControl.class);
		final VFeaturePathDomainModelReference dmr1 = mock(VFeaturePathDomainModelReference.class);
		when(control1.getDomainModelReference()).thenReturn(dmr1);

		final VControl control2 = mock(VControl.class);
		final VFeaturePathDomainModelReference dmr2 = mock(VFeaturePathDomainModelReference.class);
		when(control2.getDomainModelReference()).thenReturn(dmr2);

		when(compoundControl.getControls()).thenReturn(ECollections.asEList(control1, control2));

		final EObject domainModel = mock(EObject.class);
		when(viewModelContext.getDomainModel()).thenReturn(domainModel);

		when(emfFormsLabelProvider.getDisplayName(dmr1, domainModel))
			.thenReturn(Observables.constantObservableValue(LABEL1, String.class));

		when(emfFormsLabelProvider.getDisplayName(dmr2, domainModel))
			.thenReturn(Observables.constantObservableValue(LABEL2, String.class));

		when(emfFormsLabelProvider.getDescription(dmr1, domainModel))
			.thenReturn(Observables.constantObservableValue(TOOLTIP1, String.class));

		when(emfFormsLabelProvider.getDescription(dmr2, domainModel))
			.thenReturn(Observables.constantObservableValue(TOOLTIP2, String.class));

		final IEMFValueProperty valueProperty1 = mock(IEMFValueProperty.class);
		when(emfFormsDatabindingEMF.getValueProperty(dmr1, domainModel)).thenReturn(valueProperty1);
		final EStructuralFeature structuralFeature1 = mock(EStructuralFeature.class);
		when(valueProperty1.getStructuralFeature()).thenReturn(structuralFeature1);

		final IEMFValueProperty valueProperty2 = mock(IEMFValueProperty.class);
		when(emfFormsDatabindingEMF.getValueProperty(dmr2, domainModel)).thenReturn(valueProperty2);
		final EStructuralFeature structuralFeature2 = mock(EStructuralFeature.class);
		when(valueProperty2.getStructuralFeature()).thenReturn(structuralFeature2);

		final CompoundControlSWTRenderer renderer = createRenderer();

		/* act */
		final Control label = renderer.createLabel(shell);

		/* assert */
		final Label swtLabel = Label.class.cast(label);
		assertEquals(0, swtLabel.getStyle() & SWT.WRAP);
	}

	@Test
	public void testCreateLabelWrapDisabled() throws NoLabelFoundException, DatabindingFailedException {
		/* setup */
		final VControl control1 = mock(VControl.class);
		final VFeaturePathDomainModelReference dmr1 = mock(VFeaturePathDomainModelReference.class);
		when(control1.getDomainModelReference()).thenReturn(dmr1);

		final VControl control2 = mock(VControl.class);
		final VFeaturePathDomainModelReference dmr2 = mock(VFeaturePathDomainModelReference.class);
		when(control2.getDomainModelReference()).thenReturn(dmr2);

		when(compoundControl.getControls()).thenReturn(ECollections.asEList(control1, control2));

		final EObject domainModel = mock(EObject.class);
		when(viewModelContext.getDomainModel()).thenReturn(domainModel);

		when(emfFormsLabelProvider.getDisplayName(dmr1, domainModel))
			.thenReturn(Observables.constantObservableValue(LABEL1, String.class));

		when(emfFormsLabelProvider.getDisplayName(dmr2, domainModel))
			.thenReturn(Observables.constantObservableValue(LABEL2, String.class));

		when(emfFormsLabelProvider.getDescription(dmr1, domainModel))
			.thenReturn(Observables.constantObservableValue(TOOLTIP1, String.class));

		when(emfFormsLabelProvider.getDescription(dmr2, domainModel))
			.thenReturn(Observables.constantObservableValue(TOOLTIP2, String.class));

		final IEMFValueProperty valueProperty1 = mock(IEMFValueProperty.class);
		when(emfFormsDatabindingEMF.getValueProperty(dmr1, domainModel)).thenReturn(valueProperty1);
		final EStructuralFeature structuralFeature1 = mock(EStructuralFeature.class);
		when(valueProperty1.getStructuralFeature()).thenReturn(structuralFeature1);

		final IEMFValueProperty valueProperty2 = mock(IEMFValueProperty.class);
		when(emfFormsDatabindingEMF.getValueProperty(dmr2, domainModel)).thenReturn(valueProperty2);
		final EStructuralFeature structuralFeature2 = mock(EStructuralFeature.class);
		when(valueProperty2.getStructuralFeature()).thenReturn(structuralFeature2);

		final CompoundControlSWTRenderer renderer = createRenderer();

		final VTLabelWrapStyleProperty property = VTWrapFactory.eINSTANCE.createLabelWrapStyleProperty();
		property.setWrapLabel(false);
		final Set<VTStyleProperty> properties = Collections.<VTStyleProperty> singleton(property);
		Mockito.when(viewTemplateProvider.getStyleProperties(compoundControl, viewModelContext)).thenReturn(properties);
		when(valueProperty2.getStructuralFeature()).thenReturn(structuralFeature2);

		/* act */
		final Control label = renderer.createLabel(shell);

		/* assert */
		final Label swtLabel = Label.class.cast(label);
		assertEquals(0, swtLabel.getStyle() & SWT.WRAP);
	}

	@Test
	public void testCreateLabelWrapEnabled() throws NoLabelFoundException, DatabindingFailedException {
		/* setup */
		final VControl control1 = mock(VControl.class);
		final VFeaturePathDomainModelReference dmr1 = mock(VFeaturePathDomainModelReference.class);
		when(control1.getDomainModelReference()).thenReturn(dmr1);

		final VControl control2 = mock(VControl.class);
		final VFeaturePathDomainModelReference dmr2 = mock(VFeaturePathDomainModelReference.class);
		when(control2.getDomainModelReference()).thenReturn(dmr2);

		when(compoundControl.getControls()).thenReturn(ECollections.asEList(control1, control2));

		final EObject domainModel = mock(EObject.class);
		when(viewModelContext.getDomainModel()).thenReturn(domainModel);

		when(emfFormsLabelProvider.getDisplayName(dmr1, domainModel))
			.thenReturn(Observables.constantObservableValue(LABEL1, String.class));

		when(emfFormsLabelProvider.getDisplayName(dmr2, domainModel))
			.thenReturn(Observables.constantObservableValue(LABEL2, String.class));

		when(emfFormsLabelProvider.getDescription(dmr1, domainModel))
			.thenReturn(Observables.constantObservableValue(TOOLTIP1, String.class));

		when(emfFormsLabelProvider.getDescription(dmr2, domainModel))
			.thenReturn(Observables.constantObservableValue(TOOLTIP2, String.class));

		final IEMFValueProperty valueProperty1 = mock(IEMFValueProperty.class);
		when(emfFormsDatabindingEMF.getValueProperty(dmr1, domainModel)).thenReturn(valueProperty1);
		final EStructuralFeature structuralFeature1 = mock(EStructuralFeature.class);
		when(valueProperty1.getStructuralFeature()).thenReturn(structuralFeature1);

		final IEMFValueProperty valueProperty2 = mock(IEMFValueProperty.class);
		when(emfFormsDatabindingEMF.getValueProperty(dmr2, domainModel)).thenReturn(valueProperty2);
		final EStructuralFeature structuralFeature2 = mock(EStructuralFeature.class);
		when(valueProperty2.getStructuralFeature()).thenReturn(structuralFeature2);

		final CompoundControlSWTRenderer renderer = createRenderer();

		final VTLabelWrapStyleProperty property = VTWrapFactory.eINSTANCE.createLabelWrapStyleProperty();
		property.setWrapLabel(true);
		final Set<VTStyleProperty> properties = Collections.<VTStyleProperty> singleton(property);
		Mockito.when(viewTemplateProvider.getStyleProperties(compoundControl, viewModelContext)).thenReturn(properties);
		when(valueProperty2.getStructuralFeature()).thenReturn(structuralFeature2);

		/* act */
		final Control label = renderer.createLabel(shell);

		/* assert */
		final Label swtLabel = Label.class.cast(label);
		assertNotEquals(0, swtLabel.getStyle() & SWT.WRAP);
	}

	@Test
	public void testCreateValidationIconNoChildren()
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		/* setup */
		when(compoundControl.getControls()).thenReturn(ECollections.<VControl> emptyEList());

		final EObject domainModel = mock(EObject.class);
		when(viewModelContext.getDomainModel()).thenReturn(domainModel);

		final CompoundControlSWTRenderer renderer = spy(createRenderer());

		/* act */
		final Control label = renderer.createValidationIcon(shell);

		/* assert */
		verify(renderer, times(1)).createDummyValidationIcon(shell);
		assertTrue(Label.class.isInstance(label));
	}

	@Test
	public void testCreateValidationIconFirstCellHasNoRenderer()
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		/* setup */
		final VControl control1 = mock(VControl.class);
		final VFeaturePathDomainModelReference dmr1 = mock(VFeaturePathDomainModelReference.class);
		when(control1.getDomainModelReference()).thenReturn(dmr1);

		final VControl control2 = mock(VControl.class);
		final VFeaturePathDomainModelReference dmr2 = mock(VFeaturePathDomainModelReference.class);
		when(control2.getDomainModelReference()).thenReturn(dmr2);

		when(compoundControl.getControls()).thenReturn(ECollections.asEList(control1, control2));

		final EObject domainModel = mock(EObject.class);
		when(viewModelContext.getDomainModel()).thenReturn(domainModel);

		final CompoundControlSWTRenderer renderer = spy(createRenderer());

		/* act */
		final Control label = renderer.createValidationIcon(shell);

		/* assert */
		verify(renderer, times(1)).createDummyValidationIcon(shell);
		assertTrue(Label.class.isInstance(label));
	}

	@Test
	public void testCreateValidationIconFirstCellRenderer1Column()
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption, EMFFormsNoRendererException {
		/* setup */
		final VControl control1 = mock(VControl.class);
		final VFeaturePathDomainModelReference dmr1 = mock(VFeaturePathDomainModelReference.class);
		when(control1.getDomainModelReference()).thenReturn(dmr1);

		final VControl control2 = mock(VControl.class);
		final VFeaturePathDomainModelReference dmr2 = mock(VFeaturePathDomainModelReference.class);
		when(control2.getDomainModelReference()).thenReturn(dmr2);

		when(compoundControl.getControls()).thenReturn(ECollections.asEList(control1, control2));

		final EObject domainModel = mock(EObject.class);
		when(viewModelContext.getDomainModel()).thenReturn(domainModel);

		final DummyRenderer dummyRenderer1 = createDummyRendererNoValidation(control1);
		when(emfFormsRendererFactory.getRendererInstance(control1, viewModelContext)).thenReturn(dummyRenderer1);

		final CompoundControlSWTRenderer renderer = spy(createRenderer());

		/* act */
		final Control label = renderer.createValidationIcon(shell);

		/* assert */
		verify(renderer, times(1)).createDummyValidationIcon(shell);
		assertTrue(Label.class.isInstance(label));
	}

	@Test
	public void testCreateValidationIconFirstCellRenderer2Columns()
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption, EMFFormsNoRendererException {
		/* setup */
		final VControl control1 = mock(VControl.class);
		final VFeaturePathDomainModelReference dmr1 = mock(VFeaturePathDomainModelReference.class);
		when(control1.getDomainModelReference()).thenReturn(dmr1);

		final VControl control2 = mock(VControl.class);
		final VFeaturePathDomainModelReference dmr2 = mock(VFeaturePathDomainModelReference.class);
		when(control2.getDomainModelReference()).thenReturn(dmr2);

		when(compoundControl.getControls()).thenReturn(ECollections.asEList(control1, control2));

		final EObject domainModel = mock(EObject.class);
		when(viewModelContext.getDomainModel()).thenReturn(domainModel);

		final DummyRenderer dummyRenderer1 = spy(createDummyRenderer(control1));
		when(emfFormsRendererFactory.getRendererInstance(control1, viewModelContext)).thenReturn(dummyRenderer1);

		final CompoundControlSWTRenderer renderer = spy(createRenderer());

		/* act */
		final Control label = renderer.createValidationIcon(shell);

		/* assert */
		verify(renderer, times(0)).createDummyValidationIcon(shell);
		verify(dummyRenderer1, times(1)).createLabel(C_VALIDATION, shell);
		assertTrue(Label.class.isInstance(label));
	}

	@Test
	public void testCreateControls() throws EMFFormsNoRendererException {
		/* setup */
		final VControl control1 = mock(VControl.class);
		final VFeaturePathDomainModelReference dmr1 = mock(VFeaturePathDomainModelReference.class);
		when(control1.getDomainModelReference()).thenReturn(dmr1);

		final VControl control2 = mock(VControl.class);
		final VFeaturePathDomainModelReference dmr2 = mock(VFeaturePathDomainModelReference.class);
		when(control2.getDomainModelReference()).thenReturn(dmr2);

		when(compoundControl.getControls()).thenReturn(ECollections.asEList(control1, control2));

		final EObject domainModel = mock(EObject.class);
		when(viewModelContext.getDomainModel()).thenReturn(domainModel);

		final DummyRenderer dummyRenderer1 = createDummyRenderer(control1);
		final DummyRenderer dummyRenderer2 = createDummyRenderer(control2);

		when(emfFormsRendererFactory.getRendererInstance(control1, viewModelContext))
			.thenReturn(dummyRenderer1);

		when(emfFormsRendererFactory.getRendererInstance(control2, viewModelContext))
			.thenReturn(dummyRenderer2);

		final CompoundControlSWTRenderer renderer = spy(createRenderer());

		doReturn(GridLayoutFactory.fillDefaults().create()).when(renderer).getColumnLayout(any(Integer.class),
			any(Boolean.class));

		doReturn(GridDataFactory.fillDefaults().create()).when(renderer).getLayoutData(any(SWTGridCell.class),
			any(SWTGridDescription.class), any(SWTGridDescription.class),
			any(SWTGridDescription.class), any(VElement.class), any(EObject.class), any(Control.class));

		doReturn(GridDataFactory.fillDefaults().create()).when(renderer).getSpanningLayoutData(
			any(VContainedElement.class), any(Integer.class), any(Integer.class));

		/* act */
		final Control controls = renderer.createControls(shell);

		/* assert */
		assertTrue(Composite.class.isInstance(controls));
		final Composite controlsComposite = Composite.class.cast(controls);
		assertEquals(2, controlsComposite.getChildren().length);
		for (final Control control : controlsComposite.getChildren()) {
			assertTrue(Composite.class.isInstance(control));
		}

		final Composite control1Composite = Composite.class.cast(controlsComposite.getChildren()[0]);
		assertEquals(2, control1Composite.getChildren().length);
		for (final Control control : control1Composite.getChildren()) {
			assertTrue(Label.class.isInstance(control));
		}
		assertEquals(C_VALIDATION, Label.class.cast(control1Composite.getChildren()[0]).getText());
		assertEquals(C_CONTROL, Label.class.cast(control1Composite.getChildren()[1]).getText());

		final Composite control2Composite = Composite.class.cast(controlsComposite.getChildren()[1]);
		assertEquals(2, control2Composite.getChildren().length);
		for (final Control control : control2Composite.getChildren()) {
			assertTrue(Label.class.isInstance(control));
		}
		assertEquals(C_VALIDATION, Label.class.cast(control2Composite.getChildren()[0]).getText());
		assertEquals(C_CONTROL, Label.class.cast(control2Composite.getChildren()[1]).getText());

		verify(control1, times(1)).setLabelAlignment(LabelAlignment.NONE);
		verify(control2, times(1)).setLabelAlignment(LabelAlignment.NONE);
	}

	@Test
	public void testCreateControlsSkipOne() throws EMFFormsNoRendererException {
		/* setup */
		final VControl control1 = mock(VControl.class);
		final VFeaturePathDomainModelReference dmr1 = mock(VFeaturePathDomainModelReference.class);
		when(control1.getDomainModelReference()).thenReturn(dmr1);

		final VControl control2 = mock(VControl.class);
		final VFeaturePathDomainModelReference dmr2 = mock(VFeaturePathDomainModelReference.class);
		when(control2.getDomainModelReference()).thenReturn(dmr2);

		when(compoundControl.getControls()).thenReturn(ECollections.asEList(control1, control2));

		final EObject domainModel = mock(EObject.class);
		when(viewModelContext.getDomainModel()).thenReturn(domainModel);

		final DummyRenderer dummyRenderer1 = createDummyRenderer(control1);
		final DummyRenderer dummyRenderer2 = createDummyRenderer(control2);

		when(emfFormsRendererFactory.getRendererInstance(control1, viewModelContext))
			.thenReturn(dummyRenderer1);

		when(emfFormsRendererFactory.getRendererInstance(control2, viewModelContext))
			.thenReturn(dummyRenderer2);

		final CompoundControlSWTRenderer renderer = spy(createRenderer());

		doReturn(GridLayoutFactory.fillDefaults().create()).when(renderer).getColumnLayout(any(Integer.class),
			any(Boolean.class));

		doReturn(GridDataFactory.fillDefaults().create()).when(renderer).getLayoutData(any(SWTGridCell.class),
			any(SWTGridDescription.class), any(SWTGridDescription.class),
			any(SWTGridDescription.class), any(VElement.class), any(EObject.class), any(Control.class));

		doReturn(GridDataFactory.fillDefaults().create()).when(renderer).getSpanningLayoutData(
			any(VContainedElement.class), any(Integer.class), any(Integer.class));

		/* act */
		final Control controls = renderer.createControls(shell, 1);

		/* assert */
		assertTrue(Composite.class.isInstance(controls));
		final Composite controlsComposite = Composite.class.cast(controls);
		assertEquals(2, controlsComposite.getChildren().length);
		for (final Control control : controlsComposite.getChildren()) {
			assertTrue(Composite.class.isInstance(control));
		}

		final Composite control1Composite = Composite.class.cast(controlsComposite.getChildren()[0]);
		assertEquals(1, control1Composite.getChildren().length);
		for (final Control control : control1Composite.getChildren()) {
			assertTrue(Label.class.isInstance(control));
		}
		assertEquals(C_CONTROL, Label.class.cast(control1Composite.getChildren()[0]).getText());

		final Composite control2Composite = Composite.class.cast(controlsComposite.getChildren()[1]);
		assertEquals(2, control2Composite.getChildren().length);
		for (final Control control : control2Composite.getChildren()) {
			assertTrue(Label.class.isInstance(control));
		}
		assertEquals(C_VALIDATION, Label.class.cast(control2Composite.getChildren()[0]).getText());
		assertEquals(C_CONTROL, Label.class.cast(control2Composite.getChildren()[1]).getText());

		verify(control1, times(1)).setLabelAlignment(LabelAlignment.NONE);
		verify(control2, times(1)).setLabelAlignment(LabelAlignment.NONE);
	}

	@Test
	public void testCreateControlsSkipTwo() throws EMFFormsNoRendererException {
		/* setup */
		final VControl control1 = mock(VControl.class);
		final VFeaturePathDomainModelReference dmr1 = mock(VFeaturePathDomainModelReference.class);
		when(control1.getDomainModelReference()).thenReturn(dmr1);

		final VControl control2 = mock(VControl.class);
		final VFeaturePathDomainModelReference dmr2 = mock(VFeaturePathDomainModelReference.class);
		when(control2.getDomainModelReference()).thenReturn(dmr2);

		when(compoundControl.getControls()).thenReturn(ECollections.asEList(control1, control2));

		final EObject domainModel = mock(EObject.class);
		when(viewModelContext.getDomainModel()).thenReturn(domainModel);

		final DummyRenderer dummyRenderer1 = createDummyRenderer(control1);
		final DummyRenderer dummyRenderer2 = createDummyRenderer(control2);

		when(emfFormsRendererFactory.getRendererInstance(control1, viewModelContext))
			.thenReturn(dummyRenderer1);

		when(emfFormsRendererFactory.getRendererInstance(control2, viewModelContext))
			.thenReturn(dummyRenderer2);

		final CompoundControlSWTRenderer renderer = spy(createRenderer());

		doReturn(GridLayoutFactory.fillDefaults().create()).when(renderer).getColumnLayout(any(Integer.class),
			any(Boolean.class));

		doReturn(GridDataFactory.fillDefaults().create()).when(renderer).getLayoutData(any(SWTGridCell.class),
			any(SWTGridDescription.class), any(SWTGridDescription.class),
			any(SWTGridDescription.class), any(VElement.class), any(EObject.class), any(Control.class));

		doReturn(GridDataFactory.fillDefaults().create()).when(renderer).getSpanningLayoutData(
			any(VContainedElement.class), any(Integer.class), any(Integer.class));

		/* act */
		final Control controls = renderer.createControls(shell, 2);

		/* assert */
		assertTrue(Composite.class.isInstance(controls));
		final Composite controlsComposite = Composite.class.cast(controls);
		assertEquals(2, controlsComposite.getChildren().length);
		for (final Control control : controlsComposite.getChildren()) {
			assertTrue(Composite.class.isInstance(control));
		}

		final Composite control1Composite = Composite.class.cast(controlsComposite.getChildren()[0]);
		assertEquals(0, control1Composite.getChildren().length);

		final Composite control2Composite = Composite.class.cast(controlsComposite.getChildren()[1]);
		assertEquals(2, control2Composite.getChildren().length);
		for (final Control control : control2Composite.getChildren()) {
			assertTrue(Label.class.isInstance(control));
		}
		assertEquals(C_VALIDATION, Label.class.cast(control2Composite.getChildren()[0]).getText());
		assertEquals(C_CONTROL, Label.class.cast(control2Composite.getChildren()[1]).getText());

		verify(control1, times(1)).setLabelAlignment(LabelAlignment.NONE);
		verify(control2, times(1)).setLabelAlignment(LabelAlignment.NONE);
	}

	@Test
	public void testRenderControlColumn0() throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		final SWTGridCell swtGridCell = mock(SWTGridCell.class);
		when(swtGridCell.getColumn()).thenReturn(0);
		final CompoundControlSWTRenderer renderer = spy(createRenderer());
		doReturn(new Label(shell, SWT.NONE)).when(renderer).createLabel(shell);
		renderer.renderControl(swtGridCell, shell);
		verify(renderer, times(1)).createLabel(shell);
	}

	@Test
	public void testRenderControlColumn1() throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		when(compoundControl.getControls()).thenReturn(ECollections.<VControl> emptyEList());
		final SWTGridCell swtGridCell = mock(SWTGridCell.class);
		when(swtGridCell.getColumn()).thenReturn(1);
		final CompoundControlSWTRenderer renderer = spy(createRenderer());
		doReturn(new Label(shell, SWT.NONE)).when(renderer).createLabel(shell);
		renderer.renderControl(swtGridCell, shell);
		verify(renderer, times(1)).createValidationIcon(shell);
	}

	@Test
	public void testRenderControlColumn2() throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		when(compoundControl.getControls()).thenReturn(ECollections.<VControl> emptyEList());
		final SWTGridCell swtGridCell = mock(SWTGridCell.class);
		when(swtGridCell.getColumn()).thenReturn(2);
		final CompoundControlSWTRenderer renderer = spy(createRenderer());
		doReturn(new Label(shell, SWT.NONE)).when(renderer).createControls(shell);
		renderer.renderControl(swtGridCell, shell);
		verify(renderer, times(1)).createControls(shell);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRenderControlColumnOther() throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		when(compoundControl.getControls()).thenReturn(ECollections.<VControl> emptyEList());
		final SWTGridCell swtGridCell = mock(SWTGridCell.class);
		when(swtGridCell.getColumn()).thenReturn(3);
		final CompoundControlSWTRenderer renderer = spy(createRenderer());
		renderer.renderControl(swtGridCell, shell);
	}

	@Test
	public void testRenderControlColumn0ControlLabelAlignmentNone()
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		when(compoundControl.getLabelAlignment()).thenReturn(LabelAlignment.NONE);
		final SWTGridCell swtGridCell = mock(SWTGridCell.class);
		when(swtGridCell.getColumn()).thenReturn(0);
		final CompoundControlSWTRenderer renderer = spy(createRenderer());
		doReturn(new Label(shell, SWT.NONE)).when(renderer).createValidationIcon(shell);
		renderer.renderControl(swtGridCell, shell);
		verify(renderer, times(1)).createValidationIcon(shell);
	}

	@Test
	public void testRenderControlColumn1ControlLabelAlignmentNone()
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		when(compoundControl.getLabelAlignment()).thenReturn(LabelAlignment.NONE);
		when(compoundControl.getControls()).thenReturn(ECollections.<VControl> emptyEList());
		final SWTGridCell swtGridCell = mock(SWTGridCell.class);
		when(swtGridCell.getColumn()).thenReturn(1);
		final CompoundControlSWTRenderer renderer = spy(createRenderer());
		doReturn(new Label(shell, SWT.NONE)).when(renderer).createControls(shell);
		renderer.renderControl(swtGridCell, shell);
		verify(renderer, times(1)).createControls(shell);
	}

	@Test
	public void testEffectivelyReadOnlyDoesNotDisableComposite()
		throws EMFFormsNoRendererException, NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		/* setup */
		when(compoundControl.isEffectivelyReadonly()).thenReturn(true);
		when(compoundControl.isEffectivelyEnabled()).thenReturn(true);
		when(compoundControl.isEnabled()).thenReturn(true);

		final VControl control1 = mock(VControl.class);
		final VFeaturePathDomainModelReference dmr1 = mock(VFeaturePathDomainModelReference.class);
		when(control1.getDomainModelReference()).thenReturn(dmr1);

		final VControl control2 = mock(VControl.class);
		final VFeaturePathDomainModelReference dmr2 = mock(VFeaturePathDomainModelReference.class);
		when(control2.getDomainModelReference()).thenReturn(dmr2);

		when(compoundControl.getControls()).thenReturn(ECollections.asEList(control1, control2));

		final EObject domainModel = mock(EObject.class);
		when(viewModelContext.getDomainModel()).thenReturn(domainModel);

		final DummyRenderer dummyRenderer1 = createDummyRenderer(control1);
		final DummyRenderer dummyRenderer2 = createDummyRenderer(control2);

		when(emfFormsRendererFactory.getRendererInstance(control1, viewModelContext))
			.thenReturn(dummyRenderer1);

		when(emfFormsRendererFactory.getRendererInstance(control2, viewModelContext))
			.thenReturn(dummyRenderer2);

		final CompoundControlSWTRenderer renderer = spy(createRenderer());

		doReturn(GridLayoutFactory.fillDefaults().create()).when(renderer).getColumnLayout(any(Integer.class),
			any(Boolean.class));

		doReturn(GridDataFactory.fillDefaults().create()).when(renderer).getLayoutData(any(SWTGridCell.class),
			any(SWTGridDescription.class), any(SWTGridDescription.class),
			any(SWTGridDescription.class), any(VElement.class), any(EObject.class), any(Control.class));

		doReturn(GridDataFactory.fillDefaults().create()).when(renderer).getSpanningLayoutData(
			any(VContainedElement.class), any(Integer.class), any(Integer.class));

		/* act */
		renderer.init();
		final Control controls = renderer.render(new SWTGridCell(0, 2, renderer), shell);
		renderer.finalizeRendering(shell);

		/* assert */
		assertTrue(Composite.class.isInstance(controls));
		final Composite controlsComposite = Composite.class.cast(controls);
		assertTrue(controlsComposite.isEnabled());

		assertEquals(2, controlsComposite.getChildren().length);
		for (final Control control : controlsComposite.getChildren()) {
			assertTrue(Composite.class.isInstance(control));
		}

		final Composite control1Composite = Composite.class.cast(controlsComposite.getChildren()[0]);
		assertTrue(control1Composite.isEnabled());

		final Composite control2Composite = Composite.class.cast(controlsComposite.getChildren()[1]);
		assertTrue(control2Composite.isEnabled());

	}

	private DummyRenderer createDummyRenderer(final VControl control) {
		final DummyRenderer dummyRenderer = new DummyRenderer(control, viewModelContext, reportService, true);
		dummyRenderer.init();
		return dummyRenderer;
	}

	private DummyRenderer createDummyRendererNoValidation(final VControl control) {
		final DummyRenderer dummyRenderer = new DummyRenderer(control, viewModelContext, reportService, false);
		dummyRenderer.init();
		return dummyRenderer;
	}

	public static class DummyRenderer extends AbstractSWTRenderer<VElement> {

		private SWTGridDescription rendererGridDescription;
		private final boolean showValidation;

		DummyRenderer(VControl vElement, ViewModelContext viewContext, ReportService reportService,
			boolean showValidation) {
			super(vElement, viewContext, reportService);
			this.showValidation = showValidation;
		}

		@Override
		public SWTGridDescription getGridDescription(SWTGridDescription gridDescription) {
			if (rendererGridDescription == null) {
				rendererGridDescription = GridDescriptionFactory.INSTANCE.createSimpleGrid(1, showValidation ? 2 : 1,
					this);
				for (int i = 0; i < rendererGridDescription.getGrid().size() - 1; i++) {
					final SWTGridCell swtGridCell = rendererGridDescription.getGrid().get(i);
					swtGridCell.setHorizontalGrab(false);
				}
			}
			return rendererGridDescription;
		}

		@Override
		protected Control renderControl(SWTGridCell gridCell, Composite parent)
			throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
			int column = gridCell.getColumn();
			if (!showValidation) {
				column++;
			}
			switch (column) {
			case 0:
				return createLabel(C_VALIDATION, parent);
			case 1:
				return createLabel(C_CONTROL, parent);
			default:
				throw new IllegalArgumentException();
			}
		}

		protected Control createLabel(String string, Composite parent) {
			final Label label = new Label(parent, SWT.NONE);
			label.setText(string);
			return label;
		}

	}

}
