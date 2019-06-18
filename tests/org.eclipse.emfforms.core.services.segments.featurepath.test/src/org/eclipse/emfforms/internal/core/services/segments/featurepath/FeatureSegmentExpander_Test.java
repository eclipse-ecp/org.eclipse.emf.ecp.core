/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emfforms.internal.core.services.segments.featurepath;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.A;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.B;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.C;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestFactory;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestPackage;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDMRSegmentExpander;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsExpandingFailedException;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit tests for {@link FeatureSegmentExpander}.
 *
 * @author Lucas Koehler
 *
 */
public class FeatureSegmentExpander_Test {

	private FeatureSegmentExpander expander;
	private ReportService reportService;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		expander = new FeatureSegmentExpander();
		reportService = mock(ReportService.class);
		expander.setReportService(reportService);
	}

	@Test
	public void testPrepareDomainObjectChildPresent() throws EMFFormsExpandingFailedException {
		final B domainObject = TestFactory.eINSTANCE.createB();
		final C child = TestFactory.eINSTANCE.createC();
		domainObject.setC(child);

		final VFeatureDomainModelReferenceSegment featureSegment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		final EReference reference = TestPackage.eINSTANCE.getB_C();
		featureSegment.setDomainModelFeature(reference.getName());

		final Optional<EObject> result = expander.prepareDomainObject(featureSegment, domainObject);

		assertEquals(child, domainObject.getC());
		assertTrue(result.isPresent());
		assertEquals(child, result.get());
	}

	@Test
	public void testPrepareDomainObjectChildNotPresent() throws EMFFormsExpandingFailedException {
		final B domainObject = TestFactory.eINSTANCE.createB();

		final VFeatureDomainModelReferenceSegment featureSegment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		final EReference reference = TestPackage.eINSTANCE.getB_C();
		featureSegment.setDomainModelFeature(reference.getName());

		assertNull(domainObject.getC());
		final Optional<EObject> result = expander.prepareDomainObject(featureSegment, domainObject);

		assertNotNull(domainObject.getC());
		assertTrue(result.isPresent());
		assertTrue(C.class.isInstance(result.get()));
	}

	@Test
	public void testPrepareDomainObjectChildPresentAbstractReference() throws EMFFormsExpandingFailedException {
		final B domainObject = TestFactory.eINSTANCE.createB();
		final A child = TestFactory.eINSTANCE.createA();
		domainObject.setE(child);

		final VFeatureDomainModelReferenceSegment featureSegment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		final EReference reference = TestPackage.eINSTANCE.getB_E();
		featureSegment.setDomainModelFeature(reference.getName());

		expander.prepareDomainObject(featureSegment, domainObject);

		assertEquals(child, domainObject.getE());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPrepareDomainObjectDomainObjectNull() throws EMFFormsExpandingFailedException {
		final VFeatureDomainModelReferenceSegment featureSegment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		expander.prepareDomainObject(featureSegment, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPrepareDomainObjectSegmentNull() throws EMFFormsExpandingFailedException {
		final EObject domainObject = EcoreFactory.eINSTANCE.createEObject();
		expander.prepareDomainObject(null, domainObject);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPrepareDomainObjectBothNull() throws EMFFormsExpandingFailedException {
		expander.prepareDomainObject(null, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPrepareDomainObjectWrongSegmentType() throws EMFFormsExpandingFailedException {
		final VDomainModelReferenceSegment segment = mock(VDomainModelReferenceSegment.class);
		final EObject domainObject = EcoreFactory.eINSTANCE.createEObject();
		expander.prepareDomainObject(segment, domainObject);
	}

	@Test(expected = EMFFormsExpandingFailedException.class)
	public void testPrepareDomainObjectChildNotPresentAbstractReference() throws EMFFormsExpandingFailedException {
		final B domainObject = TestFactory.eINSTANCE.createB();

		final VFeatureDomainModelReferenceSegment featureSegment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		final EReference reference = TestPackage.eINSTANCE.getB_E();
		featureSegment.setDomainModelFeature(reference.getName());

		expander.prepareDomainObject(featureSegment, domainObject);
	}

	@Test(expected = EMFFormsExpandingFailedException.class)
	public void testPrepareDomainObjectEAttribute() throws EMFFormsExpandingFailedException {
		final EObject domainObject = TestFactory.eINSTANCE.createD();

		final VFeatureDomainModelReferenceSegment featureSegment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		final EAttribute attribute = TestPackage.eINSTANCE.getD_X();
		featureSegment.setDomainModelFeature(attribute.getName());

		expander.prepareDomainObject(featureSegment, domainObject);
	}

	@Test(expected = EMFFormsExpandingFailedException.class)
	public void testPrepareDomainObjectNullReference() throws EMFFormsExpandingFailedException {
		final B domainObject = TestFactory.eINSTANCE.createB();

		final VFeatureDomainModelReferenceSegment featureSegment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();

		expander.prepareDomainObject(featureSegment, domainObject);
	}

	@Test
	public void testIsApplicable() {
		final VFeatureDomainModelReferenceSegment featureSegment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		assertEquals(1d, expander.isApplicable(featureSegment), 0d);
	}

	@Test
	public void testIsApplicableWrongSegmentType() {
		final VDomainModelReferenceSegment segment = mock(VDomainModelReferenceSegment.class);
		assertEquals(EMFFormsDMRSegmentExpander.NOT_APPLICABLE, expander.isApplicable(segment), 0d);
	}

	@Test
	public void testIsApplicableNull() {
		assertEquals(EMFFormsDMRSegmentExpander.NOT_APPLICABLE, expander.isApplicable(null), 0d);
		verify(reportService).report(any(AbstractReport.class));
	}
}
