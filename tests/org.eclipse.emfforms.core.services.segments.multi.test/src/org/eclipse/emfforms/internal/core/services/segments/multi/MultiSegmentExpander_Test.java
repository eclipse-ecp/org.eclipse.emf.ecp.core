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
package org.eclipse.emfforms.internal.core.services.segments.multi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.B;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.C;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.D;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestFactory;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestPackage;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDMRSegmentExpander;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDomainExpander;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsExpandingFailedException;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultiDomainModelReferenceSegment;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultisegmentFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit tests for {@link MultiSegmentExpander}.
 *
 * @author Lucas Koehler
 *
 */
public class MultiSegmentExpander_Test {

	private MultiSegmentExpander multiExpander;
	private ReportService reportService;
	private EMFFormsDomainExpander domainExpander;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		multiExpander = new MultiSegmentExpander();
		reportService = mock(ReportService.class);
		multiExpander.setReportService(reportService);
		domainExpander = mock(EMFFormsDomainExpander.class);
		multiExpander.setEMFFormsDomainExpander(domainExpander);
	}

	@Test
	public void testPrepareDomainObjectExpandAllChildObjectsAndChildDMRs() throws EMFFormsExpandingFailedException {
		final B domain = TestFactory.eINSTANCE.createB();
		final C child1 = TestFactory.eINSTANCE.createC();
		final C child2 = TestFactory.eINSTANCE.createC();
		domain.getCList().add(child1);
		domain.getCList().add(child2);
		final EReference feature = TestPackage.eINSTANCE.getB_CList();
		final VDomainModelReference childDMR1 = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		final VDomainModelReference childDMR2 = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();

		final VMultiDomainModelReferenceSegment multiSegment = VMultisegmentFactory.eINSTANCE
			.createMultiDomainModelReferenceSegment();
		multiSegment.setDomainModelFeature(feature.getName());
		multiSegment.getChildDomainModelReferences().add(childDMR1);
		multiSegment.getChildDomainModelReferences().add(childDMR2);

		final Optional<EObject> result = multiExpander.prepareDomainObject(multiSegment, domain);

		assertFalse(result.isPresent());
		verify(domainExpander).prepareDomainObject(childDMR1, child1);
		verify(domainExpander).prepareDomainObject(childDMR2, child1);
		verify(domainExpander).prepareDomainObject(childDMR1, child2);
		verify(domainExpander).prepareDomainObject(childDMR2, child2);
	}

	@Test(expected = EMFFormsExpandingFailedException.class)
	public void testPrepareDomainObjectSingleReference() throws EMFFormsExpandingFailedException {
		final B domain = TestFactory.eINSTANCE.createB();
		final C child1 = TestFactory.eINSTANCE.createC();
		domain.getCList().add(child1);
		final EReference feature = TestPackage.eINSTANCE.getB_C();
		final VDomainModelReference childDMR1 = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();

		final VMultiDomainModelReferenceSegment multiSegment = VMultisegmentFactory.eINSTANCE
			.createMultiDomainModelReferenceSegment();
		multiSegment.setDomainModelFeature(feature.getName());
		multiSegment.getChildDomainModelReferences().add(childDMR1);

		multiExpander.prepareDomainObject(multiSegment, domain);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPrepareDomainObjectNullSegment() throws EMFFormsExpandingFailedException {
		multiExpander.prepareDomainObject(null, mock(EObject.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPrepareDomainObjectNullDomainObject() throws EMFFormsExpandingFailedException {
		multiExpander.prepareDomainObject(mock(VMultiDomainModelReferenceSegment.class), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPrepareDomainObjectWrongSegmentType() throws EMFFormsExpandingFailedException {
		multiExpander.prepareDomainObject(mock(VFeatureDomainModelReferenceSegment.class), mock(EObject.class));
	}

	@Test(expected = EMFFormsExpandingFailedException.class)
	public void testPrepareDomainObjectSingleAttribute() throws EMFFormsExpandingFailedException {
		final D domain = TestFactory.eINSTANCE.createD();
		final EAttribute feature = TestPackage.eINSTANCE.getD_X();

		final VMultiDomainModelReferenceSegment multiSegment = VMultisegmentFactory.eINSTANCE
			.createMultiDomainModelReferenceSegment();
		multiSegment.setDomainModelFeature(feature.getName());

		multiExpander.prepareDomainObject(multiSegment, domain);
	}

	@Test(expected = EMFFormsExpandingFailedException.class)
	public void testPrepareDomainObjectMultiAttribute() throws EMFFormsExpandingFailedException {
		final D domain = TestFactory.eINSTANCE.createD();
		final EAttribute feature = TestPackage.eINSTANCE.getD_YList();

		final VMultiDomainModelReferenceSegment multiSegment = VMultisegmentFactory.eINSTANCE
			.createMultiDomainModelReferenceSegment();
		multiSegment.setDomainModelFeature(feature.getName());

		multiExpander.prepareDomainObject(multiSegment, domain);
	}

	@Test
	public void testIsApplicable() {
		final double applicable = multiExpander.isApplicable(mock(VMultiDomainModelReferenceSegment.class));
		assertEquals(5d, applicable, 0d);
	}

	@Test
	public void testIsApplicableNull() {
		final double applicable = multiExpander.isApplicable(null);
		assertEquals(EMFFormsDMRSegmentExpander.NOT_APPLICABLE, applicable, 0d);
		verify(reportService).report(any(AbstractReport.class));
	}

	@Test
	public void testIsApplicableWrongSegmentType() {
		final double applicable = multiExpander.isApplicable(mock(VFeatureDomainModelReferenceSegment.class));
		assertEquals(EMFFormsDMRSegmentExpander.NOT_APPLICABLE, applicable, 0d);
	}

	@Test
	public void testNeedsToExpandLastSegment() {
		assertTrue(multiExpander.needsToExpandLastSegment());
	}

}
