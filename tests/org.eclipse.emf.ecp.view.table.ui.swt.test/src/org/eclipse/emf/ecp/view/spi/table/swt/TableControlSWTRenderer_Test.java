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
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;

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
import org.eclipse.emf.ecp.view.model.common.AbstractRenderer;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.internal.core.services.databinding.DefaultRealm;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Basic tests around {@link TableControlSWTRenderer}.
 */
@SuppressWarnings("restriction")
public class TableControlSWTRenderer_Test {

	private EPackage ePackage;
	private EClass eClass1;
	private DefaultRealm realm;

	@Before
	public void before() {
		ePackage = EcoreFactory.eINSTANCE.createEPackage();
		ePackage.setName("pack1");
		eClass1 = EcoreFactory.eINSTANCE.createEClass();
		ePackage.getEClassifiers().add(eClass1);

		realm = new DefaultRealm();
	}

	@After
	public void after() {
		realm.dispose();
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

}
