/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.segments.mapping;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.A;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.B;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.C;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.E;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestFactory;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestPackage;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDMRSegmentExpander;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsExpandingFailedException;
import org.eclipse.emfforms.spi.view.mappingsegment.model.VMappingDomainModelReferenceSegment;
import org.eclipse.emfforms.spi.view.mappingsegment.model.VMappingsegmentFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit tests for {@link MappingSegmentExpander}.
 *
 * @author Lucas Koehler
 *
 */
public class MappingSegmentExpander_Test {

	private MappingSegmentExpander expander;
	private ReportService reportService;

	/**
	 * Instantiates a new mapping expander for every test case.
	 */
	@Before
	public void setUp() {
		expander = new MappingSegmentExpander();
		reportService = mock(ReportService.class);
		expander.setReportService(reportService);
	}

	@Test
	public void testIsApplicable() {
		final double result = expander.isApplicable(mock(VMappingDomainModelReferenceSegment.class));
		assertEquals(5d, result, 0d);
	}

	@Test
	public void testIsApplicableNull() {
		final double result = expander.isApplicable(null);
		assertEquals(EMFFormsDMRSegmentExpander.NOT_APPLICABLE, result, 0d);
		verify(reportService).report(any(AbstractReport.class));
	}

	@Test
	public void testIsApplicableWrongSegmentType() {
		final double result = expander.isApplicable(mock(VDomainModelReferenceSegment.class));
		assertEquals(EMFFormsDMRSegmentExpander.NOT_APPLICABLE, result, 0d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPrepareDomainObjectSegmentNull() throws EMFFormsExpandingFailedException {
		expander.prepareDomainObject(null, mock(EObject.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPrepareDomainObjectEObjectNull() throws EMFFormsExpandingFailedException {
		expander.prepareDomainObject(mock(VMappingDomainModelReferenceSegment.class), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPrepareDomainObjectBothNull() throws EMFFormsExpandingFailedException {
		expander.prepareDomainObject(null, null);
	}

	@Test(expected = EMFFormsExpandingFailedException.class)
	public void testPrepareDomainObjectNoMap() throws EMFFormsExpandingFailedException {
		final B domain = TestFactory.eINSTANCE.createB();
		final EClass key = EcoreFactory.eINSTANCE.createEClass();
		final EReference structuralFeature = TestPackage.eINSTANCE.getB_CList();
		final VMappingDomainModelReferenceSegment mappingSegment = VMappingsegmentFactory.eINSTANCE
			.createMappingDomainModelReferenceSegment();
		mappingSegment.setDomainModelFeature(structuralFeature.getName());
		mappingSegment.setMappedClass(key);

		expander.prepareDomainObject(mappingSegment, domain);
	}

	@Test(expected = EMFFormsExpandingFailedException.class)
	public void testPrepareDomainObjectMapValueAttribute() throws EMFFormsExpandingFailedException {
		final C domain = TestFactory.eINSTANCE.createC();
		final EClass key = EcoreFactory.eINSTANCE.createEClass();
		final EReference structuralFeature = TestPackage.eINSTANCE.getC_EClassToString();
		final VMappingDomainModelReferenceSegment mappingSegment = VMappingsegmentFactory.eINSTANCE
			.createMappingDomainModelReferenceSegment();
		mappingSegment.setDomainModelFeature(structuralFeature.getName());
		mappingSegment.setMappedClass(key);

		expander.prepareDomainObject(mappingSegment, domain);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testPrepareDomainObjectKeyAndValuePresent() throws EMFFormsExpandingFailedException {
		final EReference structuralFeature = TestPackage.eINSTANCE.getC_EClassToA();
		final C domain = TestFactory.eINSTANCE.createC();
		final EClass key = EcoreFactory.eINSTANCE.createEClass();
		final A value = TestFactory.eINSTANCE.createA();
		final EMap<EClass, A> map = (EMap<EClass, A>) domain.eGet(structuralFeature);
		map.put(key, value);

		final VMappingDomainModelReferenceSegment mappingSegment = VMappingsegmentFactory.eINSTANCE
			.createMappingDomainModelReferenceSegment();
		mappingSegment.setDomainModelFeature(structuralFeature.getName());
		mappingSegment.setMappedClass(key);

		final Optional<EObject> result = expander.prepareDomainObject(mappingSegment, domain);

		assertEquals(value, result.get());
		assertEquals(value, map.get(key));
		assertEquals(1, map.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testPrepareDomainObjectKeyPresentValueNull() throws EMFFormsExpandingFailedException {
		final EReference structuralFeature = TestPackage.eINSTANCE.getC_EClassToA();
		final C domain = TestFactory.eINSTANCE.createC();
		final EClass key = EcoreFactory.eINSTANCE.createEClass();
		final EMap<EClass, A> map = (EMap<EClass, A>) domain.eGet(structuralFeature);
		map.put(key, null);

		final VMappingDomainModelReferenceSegment mappingSegment = VMappingsegmentFactory.eINSTANCE
			.createMappingDomainModelReferenceSegment();
		mappingSegment.setDomainModelFeature(structuralFeature.getName());
		mappingSegment.setMappedClass(key);

		final Optional<EObject> result = expander.prepareDomainObject(mappingSegment, domain);

		assertTrue(result.isPresent());
		assertTrue(result.get() instanceof A);
		assertEquals(result.get(), map.get(key));
		assertEquals(1, map.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testPrepareDomainObjectKeyNotPresent() throws EMFFormsExpandingFailedException {
		final EReference structuralFeature = TestPackage.eINSTANCE.getC_EClassToA();
		final C domain = TestFactory.eINSTANCE.createC();
		final EClass key = EcoreFactory.eINSTANCE.createEClass();
		final EMap<EClass, A> map = (EMap<EClass, A>) domain.eGet(structuralFeature);

		final VMappingDomainModelReferenceSegment mappingSegment = VMappingsegmentFactory.eINSTANCE
			.createMappingDomainModelReferenceSegment();
		mappingSegment.setDomainModelFeature(structuralFeature.getName());
		mappingSegment.setMappedClass(key);

		final Optional<EObject> result = expander.prepareDomainObject(mappingSegment, domain);

		assertTrue(result.isPresent());
		assertTrue(result.get() instanceof A);
		assertEquals(result.get(), map.get(key));
		assertEquals(1, map.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testPrepareDomainObjectValueRefAbstractKeyAndValuePresent() throws EMFFormsExpandingFailedException {
		final EReference structuralFeature = TestPackage.eINSTANCE.getC_EClassToE();
		final C domain = TestFactory.eINSTANCE.createC();
		final EClass key = EcoreFactory.eINSTANCE.createEClass();
		final A value = TestFactory.eINSTANCE.createA();
		final EMap<EClass, E> map = (EMap<EClass, E>) domain.eGet(structuralFeature);
		map.put(key, value);

		final VMappingDomainModelReferenceSegment mappingSegment = VMappingsegmentFactory.eINSTANCE
			.createMappingDomainModelReferenceSegment();
		mappingSegment.setDomainModelFeature(structuralFeature.getName());
		mappingSegment.setMappedClass(key);

		final Optional<EObject> result = expander.prepareDomainObject(mappingSegment, domain);

		assertEquals(value, result.get());
		assertEquals(value, map.get(key));
		assertEquals(1, map.size());
	}

	@SuppressWarnings("unchecked")
	@Test(expected = EMFFormsExpandingFailedException.class)
	public void testPrepareDomainObjectValueRefAbstractValueNotPresent() throws EMFFormsExpandingFailedException {
		final EReference structuralFeature = TestPackage.eINSTANCE.getC_EClassToE();
		final C domain = TestFactory.eINSTANCE.createC();
		final EClass key = EcoreFactory.eINSTANCE.createEClass();
		final EMap<EClass, E> map = (EMap<EClass, E>) domain.eGet(structuralFeature);
		map.put(key, null);

		final VMappingDomainModelReferenceSegment mappingSegment = VMappingsegmentFactory.eINSTANCE
			.createMappingDomainModelReferenceSegment();
		mappingSegment.setDomainModelFeature(structuralFeature.getName());
		mappingSegment.setMappedClass(key);

		expander.prepareDomainObject(mappingSegment, domain);
	}

	@Test(expected = EMFFormsExpandingFailedException.class)
	public void testPrepareDomainObjectValueRefAbstractKeyAndValueNotPresent() throws EMFFormsExpandingFailedException {
		final EReference structuralFeature = TestPackage.eINSTANCE.getC_EClassToE();
		final C domain = TestFactory.eINSTANCE.createC();
		final EClass key = EcoreFactory.eINSTANCE.createEClass();

		final VMappingDomainModelReferenceSegment mappingSegment = VMappingsegmentFactory.eINSTANCE
			.createMappingDomainModelReferenceSegment();
		mappingSegment.setDomainModelFeature(structuralFeature.getName());
		mappingSegment.setMappedClass(key);

		expander.prepareDomainObject(mappingSegment, domain);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testPrepareDomainObjectValueRefAbstractWithConcreteMappedClass()
		throws EMFFormsExpandingFailedException {
		final EReference structuralFeature = TestPackage.eINSTANCE.getC_EClassToE();
		final C domain = TestFactory.eINSTANCE.createC();
		final EClass key = TestPackage.eINSTANCE.getA();
		final EMap<EClass, E> map = (EMap<EClass, E>) domain.eGet(structuralFeature);
		map.put(key, null);

		final VMappingDomainModelReferenceSegment mappingSegment = VMappingsegmentFactory.eINSTANCE
			.createMappingDomainModelReferenceSegment();
		mappingSegment.setDomainModelFeature(structuralFeature.getName());
		mappingSegment.setMappedClass(key);

		expander.prepareDomainObject(mappingSegment, domain);

		assertEquals(TestPackage.eINSTANCE.getA(), domain.getEClassToE().get(key).eClass());
	}
}
