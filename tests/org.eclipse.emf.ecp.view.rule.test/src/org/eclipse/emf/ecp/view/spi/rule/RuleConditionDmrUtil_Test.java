/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emf.ecp.view.spi.rule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.rule.model.Condition;
import org.eclipse.emf.ecp.view.spi.rule.model.EnableRule;
import org.eclipse.emf.ecp.view.spi.rule.model.IterateCondition;
import org.eclipse.emf.ecp.view.spi.rule.model.RuleFactory;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link RuleConditionDmrUtil}.
 *
 * @author Lucas Koehler
 *
 */
public class RuleConditionDmrUtil_Test {

	private EMFFormsDatabindingEMF databinding;
	private ReportService reportService;
	private Condition condition;

	@Before
	public void setUp() {
		databinding = mock(EMFFormsDatabindingEMF.class);
		reportService = mock(ReportService.class);
		condition = RuleFactory.eINSTANCE.createLeafCondition();
	}

	@Test
	public void getDmrRootEClass_noView() {
		final IterateCondition iterateCondition = RuleFactory.eINSTANCE.createIterateCondition();
		iterateCondition.setItemCondition(condition);

		final Optional<EClass> result = RuleConditionDmrUtil.getDmrRootEClass(databinding, reportService, condition);
		assertFalse(result.isPresent());
		verify(reportService, never()).report(any());
	}

	@Test
	public void getDmrRootEClass_noIterateConditions() {
		final EnableRule rule = RuleFactory.eINSTANCE.createEnableRule();
		rule.setCondition(condition);
		final VView view = VViewFactory.eINSTANCE.createView();
		view.getAttachments().add(rule);
		view.setRootEClass(EcorePackage.Literals.EATTRIBUTE);

		final Optional<EClass> result = RuleConditionDmrUtil.getDmrRootEClass(databinding, reportService, condition);
		assumeTrue(result.isPresent());
		assertSame(EcorePackage.Literals.EATTRIBUTE, result.get());
		verify(reportService, never()).report(any());
	}

	@Test
	public void getDmrRootEClass_oneIterateCondition() throws DatabindingFailedException {
		final IterateCondition iterate = RuleFactory.eINSTANCE.createIterateCondition();
		final VDomainModelReference iterateDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		iterate.setItemReference(iterateDmr);
		iterate.setItemCondition(condition);
		final EnableRule rule = RuleFactory.eINSTANCE.createEnableRule();
		rule.setCondition(iterate);
		final VView view = VViewFactory.eINSTANCE.createView();
		view.getAttachments().add(rule);
		view.setRootEClass(EcorePackage.Literals.EREFERENCE);

		final IEMFValueProperty valueProperty = mock(IEMFValueProperty.class);
		when(valueProperty.getValueType()).thenReturn(EcorePackage.Literals.ESTRUCTURAL_FEATURE__ECONTAINING_CLASS);
		when(databinding.getValueProperty(iterateDmr, EcorePackage.Literals.EREFERENCE)).thenReturn(valueProperty);

		final Optional<EClass> result = RuleConditionDmrUtil.getDmrRootEClass(databinding, reportService, condition);
		assertTrue(result.isPresent());
		assertSame(EcorePackage.Literals.ECLASS, result.get());
		verify(reportService, never()).report(any());
	}

	@Test
	public void getDmrRootEClass_nestedIterateConditions() throws DatabindingFailedException {
		// Hierarchy: View -> Rule -> iterate -> iterateNested -> condition

		// deeper nested
		final IterateCondition iterateNested = RuleFactory.eINSTANCE.createIterateCondition();
		final VDomainModelReference iterateNestedDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		iterateNested.setItemReference(iterateNestedDmr);
		iterateNested.setItemCondition(condition);

		final IterateCondition iterate = RuleFactory.eINSTANCE.createIterateCondition();
		final VDomainModelReference iterateDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		iterate.setItemReference(iterateDmr);
		iterate.setItemCondition(iterateNested);

		final EnableRule rule = RuleFactory.eINSTANCE.createEnableRule();
		rule.setCondition(iterate);
		final VView view = VViewFactory.eINSTANCE.createView();
		view.getAttachments().add(rule);
		view.setRootEClass(EcorePackage.Literals.EREFERENCE);

		final IEMFValueProperty valueProperty = mock(IEMFValueProperty.class);
		when(valueProperty.getValueType()).thenReturn(EcorePackage.Literals.ESTRUCTURAL_FEATURE__ECONTAINING_CLASS);
		when(databinding.getValueProperty(iterateDmr, EcorePackage.Literals.EREFERENCE)).thenReturn(valueProperty);

		final IEMFValueProperty valuePropertyNested = mock(IEMFValueProperty.class);
		when(valuePropertyNested.getValueType()).thenReturn(EcorePackage.Literals.EMODEL_ELEMENT__EANNOTATIONS);
		when(databinding.getValueProperty(iterateNestedDmr, EcorePackage.Literals.ECLASS))
			.thenReturn(valuePropertyNested);

		final Optional<EClass> result = RuleConditionDmrUtil.getDmrRootEClass(databinding, reportService, condition);
		assertTrue(result.isPresent());
		assertSame(EcorePackage.Literals.EANNOTATION, result.get());
		verify(reportService, never()).report(any());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getDmrRootEClass_iterateCondition_databindingFailed() throws DatabindingFailedException {
		final IterateCondition iterate = RuleFactory.eINSTANCE.createIterateCondition();
		final VDomainModelReference iterateDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		iterate.setItemReference(iterateDmr);
		iterate.setItemCondition(condition);
		final EnableRule rule = RuleFactory.eINSTANCE.createEnableRule();
		rule.setCondition(iterate);
		final VView view = VViewFactory.eINSTANCE.createView();
		view.getAttachments().add(rule);
		view.setRootEClass(EcorePackage.Literals.EREFERENCE);

		when(databinding.getValueProperty(iterateDmr, EcorePackage.Literals.EREFERENCE))
			.thenThrow(DatabindingFailedException.class);

		final Optional<EClass> result = RuleConditionDmrUtil.getDmrRootEClass(databinding, reportService, condition);
		assertFalse(result.isPresent());
		verify(reportService, times(1)).report(any());
	}

	@Test
	public void getDmrRootEClass_iterateCondition_noEReference() throws DatabindingFailedException {
		final IterateCondition iterate = RuleFactory.eINSTANCE.createIterateCondition();
		final VDomainModelReference iterateDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		iterate.setItemReference(iterateDmr);
		iterate.setItemCondition(condition);
		final EnableRule rule = RuleFactory.eINSTANCE.createEnableRule();
		rule.setCondition(iterate);
		final VView view = VViewFactory.eINSTANCE.createView();
		view.getAttachments().add(rule);
		view.setRootEClass(EcorePackage.Literals.EREFERENCE);

		final IEMFValueProperty valueProperty = mock(IEMFValueProperty.class);
		when(valueProperty.getValueType()).thenReturn(EcorePackage.Literals.ESTRUCTURAL_FEATURE__VOLATILE);
		when(databinding.getValueProperty(iterateDmr, EcorePackage.Literals.EREFERENCE)).thenReturn(valueProperty);

		final Optional<EClass> result = RuleConditionDmrUtil.getDmrRootEClass(databinding, reportService, condition);
		assertFalse(result.isPresent());
		verify(reportService, never()).report(any());
	}

	/**
	 * Tests correct resolvement when the condition that will contain the new/edited dmr is an iterate condition. It
	 * must be ensured that it is not tried to resolve this direct parent iterate condition.
	 */
	@Test
	public void getDmrRootEClass_ownerIsIterateCondition() {
		final IterateCondition owner = RuleFactory.eINSTANCE.createIterateCondition();
		final EnableRule rule = RuleFactory.eINSTANCE.createEnableRule();
		rule.setCondition(owner);
		final VView view = VViewFactory.eINSTANCE.createView();
		view.getAttachments().add(rule);
		view.setRootEClass(EcorePackage.Literals.EREFERENCE);

		final Optional<EClass> result = RuleConditionDmrUtil.getDmrRootEClass(databinding, reportService, owner);
		assertTrue(result.isPresent());
		assertSame(EcorePackage.Literals.EREFERENCE, result.get());
		verify(reportService, never()).report(any());
	}

	/** Trivial non-nested case. */
	@Test
	public void getDmrRootObject_simple() {
		final EnableRule rule = RuleFactory.eINSTANCE.createEnableRule();
		rule.setCondition(condition);
		final VView view = VViewFactory.eINSTANCE.createView();
		view.getAttachments().add(rule);
		view.setRootEClass(EcorePackage.Literals.EATTRIBUTE);

		final EAttribute domain = EcoreFactory.eINSTANCE.createEAttribute();

		final List<EObject> result = RuleConditionDmrUtil.getDmrRootObject(databinding, reportService, domain,
			condition);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertSame(domain, result.get(0));
	}

	/** Trivial non-nested case but without a VView. This should not affect the method. */
	@Test
	public void getDmrRootObject_simple_noView() {
		final EnableRule rule = RuleFactory.eINSTANCE.createEnableRule();
		rule.setCondition(condition);

		final EAttribute domain = EcoreFactory.eINSTANCE.createEAttribute();

		final List<EObject> result = RuleConditionDmrUtil.getDmrRootObject(databinding, reportService, domain,
			condition);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertSame(domain, result.get(0));
	}

	@Test
	public void getDmrRootObject_oneIterateCondition_singleRef() throws DatabindingFailedException {
		final IterateCondition iterate = RuleFactory.eINSTANCE.createIterateCondition();
		final VDomainModelReference iterateDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		iterate.setItemReference(iterateDmr);
		iterate.setItemCondition(condition);
		final EnableRule rule = RuleFactory.eINSTANCE.createEnableRule();
		rule.setCondition(iterate);
		final VView view = VViewFactory.eINSTANCE.createView();
		view.getAttachments().add(rule);
		view.setRootEClass(EcorePackage.Literals.EREFERENCE);

		final EReference domain = EcoreFactory.eINSTANCE.createEReference();
		domain.setEType(EcorePackage.Literals.EANNOTATION);

		// iterate dmr points to single ref
		final Setting setting = InternalEObject.class.cast(domain)
			.eSetting(EcorePackage.Literals.EREFERENCE__EREFERENCE_TYPE);
		when(databinding.getSetting(iterateDmr, domain)).thenReturn(setting);

		final List<EObject> result = RuleConditionDmrUtil.getDmrRootObject(databinding, reportService, domain,
			condition);

		assertNotNull(result);
		assertEquals(1, result.size());
		assertSame(EcorePackage.Literals.EANNOTATION, result.get(0));
	}

	@Test
	public void getDmrRootObject_oneIterateCondition_multiRef() throws DatabindingFailedException {
		final IterateCondition iterate = RuleFactory.eINSTANCE.createIterateCondition();
		final VDomainModelReference iterateDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		iterate.setItemReference(iterateDmr);
		iterate.setItemCondition(condition);
		final EnableRule rule = RuleFactory.eINSTANCE.createEnableRule();
		rule.setCondition(iterate);
		final VView view = VViewFactory.eINSTANCE.createView();
		view.getAttachments().add(rule);
		view.setRootEClass(EcorePackage.Literals.EREFERENCE);

		final EReference domain = EcoreFactory.eINSTANCE.createEReference();
		// set multi ref objects
		domain.getEKeys().add(EcorePackage.Literals.ECLASS__ABSTRACT);
		domain.getEKeys().add(EcorePackage.Literals.ENAMED_ELEMENT__NAME);

		// iterate dmr points to multiref
		final Setting setting = InternalEObject.class.cast(domain)
			.eSetting(EcorePackage.Literals.EREFERENCE__EKEYS);
		when(databinding.getSetting(iterateDmr, domain)).thenReturn(setting);

		final List<EObject> result = RuleConditionDmrUtil.getDmrRootObject(databinding, reportService, domain,
			condition);

		assertNotNull(result);
		assertEquals(2, result.size());
		assertTrue(result.contains(EcorePackage.Literals.ECLASS__ABSTRACT));
		assertTrue(result.contains(EcorePackage.Literals.ENAMED_ELEMENT__NAME));
	}

	@Test
	public void getDmrRootObject_oneIterateCondition_attribute() throws DatabindingFailedException {
		final IterateCondition iterate = RuleFactory.eINSTANCE.createIterateCondition();
		final VDomainModelReference iterateDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		iterate.setItemReference(iterateDmr);
		iterate.setItemCondition(condition);
		final EnableRule rule = RuleFactory.eINSTANCE.createEnableRule();
		rule.setCondition(iterate);
		final VView view = VViewFactory.eINSTANCE.createView();
		view.getAttachments().add(rule);
		view.setRootEClass(EcorePackage.Literals.EREFERENCE);

		final EReference domain = EcoreFactory.eINSTANCE.createEReference();
		domain.setEType(EcorePackage.Literals.EANNOTATION);

		// iterate dmr points to attribute
		final Setting setting = InternalEObject.class.cast(domain)
			.eSetting(EcorePackage.Literals.EREFERENCE__CONTAINMENT);
		when(databinding.getSetting(iterateDmr, domain)).thenReturn(setting);

		final List<EObject> result = RuleConditionDmrUtil.getDmrRootObject(databinding, reportService, domain,
			condition);

		assertNotNull(result);
		assertEquals(0, result.size());
		verify(reportService, times(1)).report(notNull(AbstractReport.class));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getDmrRootObject_oneIterateCondition_databindingFailed() throws DatabindingFailedException {
		final IterateCondition iterate = RuleFactory.eINSTANCE.createIterateCondition();
		final VDomainModelReference iterateDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		iterate.setItemReference(iterateDmr);
		iterate.setItemCondition(condition);
		final EnableRule rule = RuleFactory.eINSTANCE.createEnableRule();
		rule.setCondition(iterate);
		final VView view = VViewFactory.eINSTANCE.createView();
		view.getAttachments().add(rule);
		view.setRootEClass(EcorePackage.Literals.EREFERENCE);

		final EReference domain = EcoreFactory.eINSTANCE.createEReference();

		when(databinding.getSetting(iterateDmr, domain)).thenThrow(DatabindingFailedException.class);

		final List<EObject> result = RuleConditionDmrUtil.getDmrRootObject(databinding, reportService, domain,
			condition);

		assertNotNull(result);
		assertEquals(0, result.size());
		verify(reportService, times(1)).report(notNull(AbstractReport.class));
	}

	@Test
	public void getDmrRootObject_nestedIterateConditions() throws DatabindingFailedException {
		// Hierarchy: View -> Rule -> iterate -> iterateNested -> condition

		// deeper nested
		final IterateCondition iterateNested = RuleFactory.eINSTANCE.createIterateCondition();
		final VDomainModelReference iterateNestedDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		iterateNested.setItemReference(iterateNestedDmr);
		iterateNested.setItemCondition(condition);

		final IterateCondition iterate = RuleFactory.eINSTANCE.createIterateCondition();
		final VDomainModelReference iterateDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		iterate.setItemReference(iterateDmr);
		iterate.setItemCondition(iterateNested);

		final EnableRule rule = RuleFactory.eINSTANCE.createEnableRule();
		rule.setCondition(iterate);
		final VView view = VViewFactory.eINSTANCE.createView();
		view.getAttachments().add(rule);
		view.setRootEClass(EcorePackage.Literals.ECLASS);

		// nested objects
		final EReference ref1 = EcoreFactory.eINSTANCE.createEReference();
		ref1.getEKeys().add(EcorePackage.Literals.ECLASS__ABSTRACT);
		ref1.getEKeys().add(EcorePackage.Literals.ECLASS__INTERFACE);
		final EReference ref2 = EcoreFactory.eINSTANCE.createEReference();
		ref2.getEKeys().add(EcorePackage.Literals.EANNOTATION__SOURCE);

		// domain
		final EClass domain = EcoreFactory.eINSTANCE.createEClass();
		domain.getEStructuralFeatures().add(ref1);
		domain.getEStructuralFeatures().add(ref2);

		// iterate dmr points to multiref
		final Setting setting = InternalEObject.class.cast(domain)
			.eSetting(EcorePackage.Literals.ECLASS__EREFERENCES);
		when(databinding.getSetting(iterateDmr, domain)).thenReturn(setting);

		final Setting nestedSetting1 = InternalEObject.class.cast(ref1)
			.eSetting(EcorePackage.Literals.EREFERENCE__EKEYS);
		when(databinding.getSetting(iterateNestedDmr, ref1)).thenReturn(nestedSetting1);

		final Setting nestedSetting2 = InternalEObject.class.cast(ref2)
			.eSetting(EcorePackage.Literals.EREFERENCE__EKEYS);
		when(databinding.getSetting(iterateNestedDmr, ref2)).thenReturn(nestedSetting2);

		final List<EObject> result = RuleConditionDmrUtil.getDmrRootObject(databinding, reportService, domain,
			condition);

		assertNotNull(result);
		assertEquals(3, result.size());
		assertTrue(result.contains(EcorePackage.Literals.ECLASS__ABSTRACT));
		assertTrue(result.contains(EcorePackage.Literals.ECLASS__INTERFACE));
		assertTrue(result.contains(EcorePackage.Literals.EANNOTATION__SOURCE));
	}
}
