/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * remi - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.swt;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.databinding.EMFObservables;
import org.eclipse.emf.databinding.EMFProperties;
import org.eclipse.emf.databinding.EObjectObservableValue;
import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.view.model.common.AbstractGridCell.Alignment;
import org.eclipse.emf.ecp.view.model.common.AbstractRenderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.LabelAlignment;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.RenderMode;
import org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty;
import org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStylePropertyFactory;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.internal.core.services.databinding.DefaultRealm;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

/**
 * Basic tests around {@link TableControlSWTRenderer}.
 */
@SuppressWarnings("restriction")
public class TableControlSWTRenderer_ITest {

	private EPackage ePackage;
	private EClass eClass1;
	private DefaultRealm realm;

	private VTableControl vElement;
	private ViewModelContext viewContext;
	private ReportService reportService;
	private EMFFormsDatabindingEMF emfFormsDatabinding;
	private EMFFormsLabelProvider emfFormsLabelProvider;
	private VTViewTemplateProvider vtViewTemplateProvider;
	private ImageRegistryService imageRegistryService;
	private EMFFormsEditSupport emfFormsEditSupport;

	@Before
	public void before() {
		ePackage = EcoreFactory.eINSTANCE.createEPackage();
		ePackage.setName("pack1");
		eClass1 = EcoreFactory.eINSTANCE.createEClass();
		ePackage.getEClassifiers().add(eClass1);
		realm = new DefaultRealm();

		vElement = mock(VTableControl.class);
		viewContext = mock(ViewModelContext.class);
		reportService = mock(ReportService.class);
		emfFormsDatabinding = mock(EMFFormsDatabindingEMF.class);
		emfFormsLabelProvider = mock(EMFFormsLabelProvider.class);
		vtViewTemplateProvider = mock(VTViewTemplateProvider.class);
		imageRegistryService = mock(ImageRegistryService.class);
		emfFormsEditSupport = mock(EMFFormsEditSupport.class);
	}

	@After
	public void after() {
		realm.dispose();
	}

	private void setupCompact() {
		final Set<VTStyleProperty> properties = new HashSet<VTStyleProperty>();
		final VTTableStyleProperty tableStyleProperty = VTTableStylePropertyFactory.eINSTANCE
			.createTableStyleProperty();
		tableStyleProperty.setRenderMode(RenderMode.COMPACT_VERTICALLY);
		properties.add(tableStyleProperty);
		Mockito.when(vtViewTemplateProvider.getStyleProperties(Matchers.any(VElement.class),
			Matchers.any(ViewModelContext.class))).thenReturn(properties);
	}

	@Test
	public void getSettingFromObservable_simple() throws DatabindingFailedException {
		final EStructuralFeature feature = EcorePackage.Literals.ENAMED_ELEMENT__NAME;

		final Optional<Setting> result = doGetSettingFromObservable(feature, ePackage, ePackage);

		assertThat(result.isPresent(), is(true));
		assertThat(ePackage, equalTo(result.get().getEObject()));
		assertThat(feature, equalTo(result.get().getEStructuralFeature()));
	}

	protected Optional<Setting> doGetSettingFromObservable(EStructuralFeature feature, EObject mainObject,
		EObject observed) throws DatabindingFailedException {
		final TableControlSWTRenderer renderer = mock(TableControlSWTRenderer.class);
		final VFeaturePathDomainModelReference dmr = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		dmr.setDomainModelEFeature(feature);

		final IEMFValueProperty valueProperty = EMFProperties.value(feature);
		final EObjectObservableValue observableValue = (EObjectObservableValue) EMFObservables.observeValue(
			realm, observed,
			feature);

		final EMFFormsDatabindingEMF db = mock(EMFFormsDatabindingEMF.class);
		Mockito.when(renderer.getEMFFormsDatabinding()).thenReturn(db);
		when(db.getValueProperty(dmr, mainObject)).thenReturn(valueProperty);
		when(db.getObservableValue(dmr, mainObject)).thenReturn(observableValue);

		Mockito.when(renderer.getSettingFromObservable(dmr, mainObject)).thenCallRealMethod();
		return renderer.getSettingFromObservable(dmr, mainObject);
	}

	protected TableControlSWTRenderer createNonMockRenderer() {
		return new TableControlSWTRenderer(vElement, viewContext, reportService, emfFormsDatabinding,
			emfFormsLabelProvider, vtViewTemplateProvider, imageRegistryService, emfFormsEditSupport);
	}

	@Test
	public void getSettingFromObservable_noFeature() throws DatabindingFailedException {
		final EStructuralFeature feature = EcorePackage.Literals.ECLASS__ABSTRACT;

		final Optional<Setting> result = doGetSettingFromObservable(feature, ePackage, ePackage);
		assertThat(result.isPresent(), is(false));
	}

	@Test
	public void getSettingFromObservable_differentObserved() throws DatabindingFailedException {
		final EStructuralFeature feature = EcorePackage.Literals.ECLASS__EOPERATIONS;
		final Optional<Setting> result = doGetSettingFromObservable(feature, ePackage, eClass1);
		assertThat(result.isPresent(), is(true));
		assertThat(eClass1, equalTo(result.get().getEObject()));
		assertThat(feature, equalTo(result.get().getEStructuralFeature()));
	}

	@Test
	public void getSettingFromObservable_differentObservedNoFeature() throws DatabindingFailedException {
		final EStructuralFeature feature = EcorePackage.Literals.EPACKAGE__ESUBPACKAGES;

		final Optional<Setting> result = doGetSettingFromObservable(feature, ePackage, eClass1);
		assertThat(result.isPresent(), is(false));
	}

	@Test
	public void getSettingFromObservable_observedNull() throws DatabindingFailedException {
		final EStructuralFeature feature = EcorePackage.Literals.EPACKAGE__ESUBPACKAGES;
		final Optional<Setting> result = doGetSettingFromObservable(feature, ePackage, null);
		assertThat(result.isPresent(), is(false));
	}

	@Test
	public void getSettingFromObservable_databindingException()
		throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException,
		DatabindingFailedException {
		final EStructuralFeature feature = EcorePackage.Literals.EPACKAGE__ESUBPACKAGES;
		final TableControlSWTRenderer renderer = mock(TableControlSWTRenderer.class);
		final DatabindingFailedException dbfe = new DatabindingFailedException("testing");
		final ReportService reportService = mock(ReportService.class);

		final Field field = AbstractRenderer.class.getDeclaredField("reportService");
		field.setAccessible(true);
		field.set(renderer, reportService);

		final VFeaturePathDomainModelReference dmr = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		dmr.setDomainModelEFeature(feature);

		final EMFFormsDatabindingEMF db = mock(EMFFormsDatabindingEMF.class);
		Mockito.when(renderer.getEMFFormsDatabinding()).thenReturn(db);
		when(db.getObservableValue(dmr, ePackage)).thenThrow(dbfe);

		Mockito.when(renderer.getSettingFromObservable(dmr, ePackage)).thenCallRealMethod();
		final Optional<Setting> result = renderer.getSettingFromObservable(dmr, ePackage);
		assertThat(result.isPresent(), is(false));
	}

	@Test
	public void getGridDescription_WithLabel() {
		setupCompact();
		when(vElement.getLabelAlignment()).thenReturn(LabelAlignment.DEFAULT);
		final TableControlSWTRenderer renderer = createNonMockRenderer();

		final SWTGridDescription gridDescription = renderer.getGridDescription(null);
		assertThat(gridDescription.getColumns(), is(equalTo(3)));

		assertLabelCell(gridDescription.getGrid().get(0));
		assertValidationCell(gridDescription.getGrid().get(1));
		assertMainCell(gridDescription.getGrid().get(2));
	}

	@Test
	public void getGridDescription_WithoutLabel() {
		setupCompact();
		when(vElement.getLabelAlignment()).thenReturn(LabelAlignment.NONE);
		final TableControlSWTRenderer renderer = createNonMockRenderer();

		final SWTGridDescription gridDescription = renderer.getGridDescription(null);
		assertThat(gridDescription.getColumns(), is(equalTo(2)));

		assertValidationCell(gridDescription.getGrid().get(0));
		assertMainCell(gridDescription.getGrid().get(1));
	}

	private void assertLabelCell(SWTGridCell labelCell) {
		assertThat(labelCell.getPreferredSize(), nullValue());
		assertThat(labelCell.isHorizontalGrab(), is(false));
		assertThat(labelCell.getVerticalAlignment(), is(Alignment.BEGINNING));
	}

	private void assertValidationCell(SWTGridCell validationCell) {
		assertThat(validationCell.getPreferredSize(), notNullValue());
		assertThat(validationCell.isHorizontalGrab(), is(false));
		assertThat(validationCell.getVerticalAlignment(), is(Alignment.BEGINNING));
	}

	private void assertMainCell(SWTGridCell mainCell) {
		assertThat(mainCell.getPreferredSize(), nullValue());
		assertThat(mainCell.isHorizontalGrab(), is(true));
		assertThat(mainCell.isVerticalGrab(), is(true));
	}

}
