/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.domainexpander.index;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexdmrFactory;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.A;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.B;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.C;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.D;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestFactory;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestPackage;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDMRSegmentExpander;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsExpandingFailedException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit tests for {@link EMFFormsDMRSegmentExpanderIndex}.
 *
 * @author Lucas Koehler
 *
 */
public class EMFFormsDMRSegmentExpanderIndex_Test {

	private EMFFormsDMRSegmentExpanderIndex expander;
	private ReportService reportService;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		expander = new EMFFormsDMRSegmentExpanderIndex();
		reportService = mock(ReportService.class);
		expander.setReportService(reportService);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.domainexpander.index.EMFFormsDMRSegmentExpanderIndex#prepareDomainObject(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EObject)}.
	 *
	 * @throws EMFFormsExpandingFailedException
	 */
	@Test
	public void testPrepareDomainObjectIndexNotPresentEmptyList() throws EMFFormsExpandingFailedException {
		final int index = 2;

		final B b = TestFactory.eINSTANCE.createB();
		final EReference eReference = TestPackage.eINSTANCE.getB_CList();
		final VIndexDomainModelReferenceSegment indexSegment = VIndexdmrFactory.eINSTANCE
			.createIndexDomainModelReferenceSegment();
		indexSegment.setDomainModelFeature(eReference.getName());
		indexSegment.setIndex(index);

		assertEquals(0, b.getCList().size());
		expander.prepareDomainObject(indexSegment, b);
		assertEquals(index + 1, b.getCList().size());
		for (int i = 0; i <= index; i++) {
			assertNotNull(b.getCList().get(i));
		}
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.domainexpander.index.EMFFormsDMRSegmentExpanderIndex#prepareDomainObject(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EObject)}.
	 *
	 * @throws EMFFormsExpandingFailedException
	 */
	@Test
	public void testPrepareDomainObjectIndexNotPresentListTooSmall() throws EMFFormsExpandingFailedException {
		final int index = 2;

		final B b = TestFactory.eINSTANCE.createB();
		final C c0 = TestFactory.eINSTANCE.createC();
		b.getCList().add(c0);

		final EReference eReference = TestPackage.eINSTANCE.getB_CList();
		final VIndexDomainModelReferenceSegment indexSegment = VIndexdmrFactory.eINSTANCE
			.createIndexDomainModelReferenceSegment();
		indexSegment.setDomainModelFeature(eReference.getName());
		indexSegment.setIndex(index);

		assertEquals(1, b.getCList().size());
		expander.prepareDomainObject(indexSegment, b);

		assertEquals(index + 1, b.getCList().size());
		assertEquals(c0, b.getCList().get(0));
		assertNotNull(b.getCList().get(1));
		assertNotNull(b.getCList().get(2));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.domainexpander.index.EMFFormsDMRSegmentExpanderIndex#prepareDomainObject(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EObject)}.
	 *
	 * @throws EMFFormsExpandingFailedException
	 */
	@Test
	public void testPrepareDomainObjectIndexPresent() throws EMFFormsExpandingFailedException {
		// expander should not change the list if the index is present
		final int index = 2;

		final B b = TestFactory.eINSTANCE.createB();
		final C c0 = TestFactory.eINSTANCE.createC();
		final C c1 = TestFactory.eINSTANCE.createC();
		final C c2 = TestFactory.eINSTANCE.createC();
		final C c3 = TestFactory.eINSTANCE.createC();
		b.getCList().add(c0);
		b.getCList().add(c1);
		b.getCList().add(c2);
		b.getCList().add(c3);

		final EReference eReference = TestPackage.eINSTANCE.getB_CList();
		final VIndexDomainModelReferenceSegment indexSegment = VIndexdmrFactory.eINSTANCE
			.createIndexDomainModelReferenceSegment();
		indexSegment.setDomainModelFeature(eReference.getName());
		indexSegment.setIndex(index);

		assertEquals(4, b.getCList().size());
		expander.prepareDomainObject(indexSegment, b);

		assertEquals(4, b.getCList().size());
		assertEquals(c0, b.getCList().get(0));
		assertEquals(c1, b.getCList().get(1));
		assertEquals(c2, b.getCList().get(2));
		assertEquals(c3, b.getCList().get(3));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.domainexpander.index.EMFFormsDMRSegmentExpanderIndex#prepareDomainObject(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EObject)}.
	 *
	 * @throws EMFFormsExpandingFailedException
	 */
	@Test
	public void testPrepareDomainObjectIndexPresentAsLastListElement() throws EMFFormsExpandingFailedException {
		// expander should not change the list if the index is present
		final int index = 2;

		final B b = TestFactory.eINSTANCE.createB();
		final C c0 = TestFactory.eINSTANCE.createC();
		final C c1 = TestFactory.eINSTANCE.createC();
		final C c2 = TestFactory.eINSTANCE.createC();
		b.getCList().add(c0);
		b.getCList().add(c1);
		b.getCList().add(c2);

		final EReference eReference = TestPackage.eINSTANCE.getB_CList();
		final VIndexDomainModelReferenceSegment indexSegment = VIndexdmrFactory.eINSTANCE
			.createIndexDomainModelReferenceSegment();
		indexSegment.setDomainModelFeature(eReference.getName());
		indexSegment.setIndex(index);

		assertEquals(3, b.getCList().size());
		expander.prepareDomainObject(indexSegment, b);

		assertEquals(3, b.getCList().size());
		assertEquals(c0, b.getCList().get(0));
		assertEquals(c1, b.getCList().get(1));
		assertEquals(c2, b.getCList().get(2));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.domainexpander.index.EMFFormsDMRSegmentExpanderIndex#prepareDomainObject(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EObject)}.
	 *
	 * @throws EMFFormsExpandingFailedException
	 */
	@Test(expected = EMFFormsExpandingFailedException.class)
	public void testPrepareDomainObjectIndexNotPresentAbstractReference() throws EMFFormsExpandingFailedException {
		final int index = 1;

		final B b = TestFactory.eINSTANCE.createB();
		final A a0 = TestFactory.eINSTANCE.createA();
		b.getEList().add(a0);

		final EReference eReference = TestPackage.eINSTANCE.getB_EList();
		final VIndexDomainModelReferenceSegment indexSegment = VIndexdmrFactory.eINSTANCE
			.createIndexDomainModelReferenceSegment();
		indexSegment.setDomainModelFeature(eReference.getName());
		indexSegment.setIndex(index);

		assertEquals(1, b.getEList().size());
		expander.prepareDomainObject(indexSegment, b);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.domainexpander.index.EMFFormsDMRSegmentExpanderIndex#prepareDomainObject(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EObject)}.
	 *
	 * @throws EMFFormsExpandingFailedException
	 */
	@Test
	public void testPrepareDomainObjectIndexPresentAbstractReference() throws EMFFormsExpandingFailedException {
		final int index = 1;

		final B b = TestFactory.eINSTANCE.createB();
		final A a0 = TestFactory.eINSTANCE.createA();
		final A a1 = TestFactory.eINSTANCE.createA();
		b.getEList().add(a0);
		b.getEList().add(a1);

		final EReference eReference = TestPackage.eINSTANCE.getB_EList();
		final VIndexDomainModelReferenceSegment indexSegment = VIndexdmrFactory.eINSTANCE
			.createIndexDomainModelReferenceSegment();
		indexSegment.setDomainModelFeature(eReference.getName());
		indexSegment.setIndex(index);

		assertEquals(2, b.getEList().size());
		expander.prepareDomainObject(indexSegment, b);

		assertEquals(2, b.getEList().size());
		assertEquals(a0, b.getEList().get(0));
		assertEquals(a1, b.getEList().get(1));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.domainexpander.index.EMFFormsDMRSegmentExpanderIndex#prepareDomainObject(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EObject)}.
	 *
	 * @throws EMFFormsExpandingFailedException
	 */
	@Test(expected = EMFFormsExpandingFailedException.class)
	public void testPrepareDomainObjectSingleReference() throws EMFFormsExpandingFailedException {
		final int index = 0;

		final B b = TestFactory.eINSTANCE.createB();
		final C c = TestFactory.eINSTANCE.createC();
		b.setC(c);

		final EReference eReference = TestPackage.eINSTANCE.getB_C();
		final VIndexDomainModelReferenceSegment indexSegment = VIndexdmrFactory.eINSTANCE
			.createIndexDomainModelReferenceSegment();
		indexSegment.setDomainModelFeature(eReference.getName());
		indexSegment.setIndex(index);

		expander.prepareDomainObject(indexSegment, b);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.domainexpander.index.EMFFormsDMRSegmentExpanderIndex#prepareDomainObject(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EObject)}.
	 *
	 * @throws EMFFormsExpandingFailedException
	 */
	@Test(expected = EMFFormsExpandingFailedException.class)
	public void testPrepareDomainObjectEAttribute() throws EMFFormsExpandingFailedException {
		final int index = 1;

		final D d = TestFactory.eINSTANCE.createD();

		final EAttribute eAttribute = TestPackage.eINSTANCE.getD_YList();
		final VIndexDomainModelReferenceSegment indexSegment = VIndexdmrFactory.eINSTANCE
			.createIndexDomainModelReferenceSegment();
		indexSegment.setDomainModelFeature(eAttribute.getName());
		indexSegment.setIndex(index);

		expander.prepareDomainObject(indexSegment, d);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.domainexpander.index.EMFFormsDMRSegmentExpanderIndex#prepareDomainObject(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EObject)}.
	 *
	 * @throws EMFFormsExpandingFailedException
	 */
	@Test(expected = EMFFormsExpandingFailedException.class)
	public void testPrepareDomainObjectNullReference() throws EMFFormsExpandingFailedException {
		final int index = 1;

		final D d = TestFactory.eINSTANCE.createD();

		final VIndexDomainModelReferenceSegment indexSegment = VIndexdmrFactory.eINSTANCE
			.createIndexDomainModelReferenceSegment();
		indexSegment.setIndex(index);

		expander.prepareDomainObject(indexSegment, d);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.domainexpander.index.EMFFormsDMRSegmentExpanderIndex#prepareDomainObject(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EObject)}.
	 *
	 * @throws EMFFormsExpandingFailedException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testPrepareDomainObjectSegmentNull() throws EMFFormsExpandingFailedException {
		final EObject eObject = mock(EObject.class);
		expander.prepareDomainObject(null, eObject);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.domainexpander.index.EMFFormsDMRSegmentExpanderIndex#prepareDomainObject(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EObject)}.
	 *
	 * @throws EMFFormsExpandingFailedException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testPrepareDomainObjectDomainObjectNull() throws EMFFormsExpandingFailedException {
		final VIndexDomainModelReferenceSegment indexSegment = mock(VIndexDomainModelReferenceSegment.class);
		expander.prepareDomainObject(indexSegment, null);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.domainexpander.index.EMFFormsDMRSegmentExpanderIndex#prepareDomainObject(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EObject)}.
	 *
	 * @throws EMFFormsExpandingFailedException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testPrepareDomainObjectBothNull() throws EMFFormsExpandingFailedException {
		expander.prepareDomainObject(null, null);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.domainexpander.index.EMFFormsDMRSegmentExpanderIndex#prepareDomainObject(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EObject)}.
	 *
	 * @throws EMFFormsExpandingFailedException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testPrepareDomainObjectWrongSegmentType() throws EMFFormsExpandingFailedException {
		final VDomainModelReferenceSegment wrongSegment = mock(VDomainModelReferenceSegment.class);
		final EObject eObject = mock(EObject.class);

		expander.prepareDomainObject(wrongSegment, eObject);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.domainexpander.index.EMFFormsDMRSegmentExpanderIndex#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment)}.
	 */
	@Test
	public void testIsApplicable() {
		final VIndexDomainModelReferenceSegment indexSegment = mock(VIndexDomainModelReferenceSegment.class);
		assertEquals(5d, expander.isApplicable(indexSegment), 0d);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.domainexpander.index.EMFFormsDMRSegmentExpanderIndex#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment)}.
	 */
	@Test
	public void testIsApplicableNull() {
		assertEquals(EMFFormsDMRSegmentExpander.NOT_APPLICABLE, expander.isApplicable(null), 0d);
		verify(reportService).report(any(AbstractReport.class));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.domainexpander.index.EMFFormsDMRSegmentExpanderIndex#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment)}.
	 */
	@Test
	public void testIsApplicableWrongSegmentType() {
		final VDomainModelReferenceSegment segment = mock(VDomainModelReferenceSegment.class);
		assertEquals(EMFFormsDMRSegmentExpander.NOT_APPLICABLE, expander.isApplicable(segment), 0d);
	}
}
