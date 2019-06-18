/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas Koehler- initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.domainexpander.defaultheuristic;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDMRExpander;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDMRSegmentExpander;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsExpandingFailedException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test cases for {@link EMFFormsDomainExpanderImpl}.
 *
 * @author Lucas Koehler
 *
 */
public class EMFFormsDomainExpanderImpl_Test {

	private EMFFormsDomainExpanderImpl domainExpander;

	/**
	 * Create a new {@link EMFFormsDomainExpanderImpl} for every test case.
	 */
	@Before
	public void setUp() {
		domainExpander = new EMFFormsDomainExpanderImpl();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test(expected = EMFFormsExpandingFailedException.class)
	public void testPrepareDomainObject_NoDMRExpander() throws EMFFormsExpandingFailedException {
		domainExpander.prepareDomainObject(VViewFactory.eINSTANCE.createFeaturePathDomainModelReference(),
			mock(EObject.class));
	}

	@Test(expected = EMFFormsExpandingFailedException.class)
	public void testPrepareDomainObjectNoSuitableDMRExpander() throws EMFFormsExpandingFailedException {
		final EMFFormsDMRExpander dmrExpander = mock(EMFFormsDMRExpander.class);
		when(dmrExpander.isApplicable(any(VDomainModelReference.class))).thenReturn(EMFFormsDMRExpander.NOT_APPLICABLE);
		domainExpander.addEMFFormsDMRExpander(dmrExpander);

		domainExpander.prepareDomainObject(VViewFactory.eINSTANCE.createFeaturePathDomainModelReference(),
			mock(EObject.class));
	}

	@Test
	public void testPrepareDomainObjectAllDMRExpandersConsidered() throws EMFFormsExpandingFailedException {
		final EMFFormsDMRExpander dmrExpander1 = mock(EMFFormsDMRExpander.class);
		when(dmrExpander1.isApplicable(any(VDomainModelReference.class))).thenReturn(1d);
		final EMFFormsDMRExpander dmrExpander2 = mock(EMFFormsDMRExpander.class);
		when(dmrExpander2.isApplicable(any(VDomainModelReference.class))).thenReturn(2d);
		final EMFFormsDMRExpander dmrExpander3 = mock(EMFFormsDMRExpander.class);
		when(dmrExpander3.isApplicable(any(VDomainModelReference.class))).thenReturn(3d);
		domainExpander.addEMFFormsDMRExpander(dmrExpander1);
		domainExpander.addEMFFormsDMRExpander(dmrExpander2);
		domainExpander.addEMFFormsDMRExpander(dmrExpander3);

		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		domainExpander.prepareDomainObject(dmr, mock(EObject.class));
		verify(dmrExpander1, atLeastOnce()).isApplicable(dmr);
		verify(dmrExpander2, atLeastOnce()).isApplicable(dmr);
		verify(dmrExpander3, atLeastOnce()).isApplicable(dmr);
	}

	@Test
	public void testPrepareDomainObjectUseCorrectDMRExpander() throws EMFFormsExpandingFailedException {
		final EMFFormsDMRExpander dmrExpander1 = mock(EMFFormsDMRExpander.class);
		when(dmrExpander1.isApplicable(any(VDomainModelReference.class))).thenReturn(1d);
		final EMFFormsDMRExpander dmrExpander2 = mock(EMFFormsDMRExpander.class);
		when(dmrExpander2.isApplicable(any(VDomainModelReference.class)))
			.thenReturn(EMFFormsDMRExpander.NOT_APPLICABLE);
		final EMFFormsDMRExpander dmrExpander3 = mock(EMFFormsDMRExpander.class);
		when(dmrExpander3.isApplicable(any(VDomainModelReference.class))).thenReturn(3d);
		domainExpander.addEMFFormsDMRExpander(dmrExpander1);
		domainExpander.addEMFFormsDMRExpander(dmrExpander2);
		domainExpander.addEMFFormsDMRExpander(dmrExpander3);

		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		final EObject domainObject = mock(EObject.class);
		domainExpander.prepareDomainObject(dmr, domainObject);
		verify(dmrExpander3).prepareDomainObject(dmr, domainObject);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPrepareDomainObjectReferenceNull() throws EMFFormsExpandingFailedException {
		domainExpander.prepareDomainObject(null, mock(EObject.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPrepareDomainObjectObjectNull() throws EMFFormsExpandingFailedException {
		domainExpander.prepareDomainObject(mock(VDomainModelReference.class), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPrepareDomainObjectBothNull() throws EMFFormsExpandingFailedException {
		domainExpander.prepareDomainObject(null, null);
	}

	/**
	 * Tests that are used over the whole dmr if segments are present.
	 *
	 * @throws EMFFormsExpandingFailedException
	 */
	@Test
	public void prepareDomainObject_SegmentsOverDmr() throws EMFFormsExpandingFailedException {
		final EMFFormsDMRExpander dmrExpander = mock(EMFFormsDMRExpander.class);
		final EMFFormsDMRSegmentExpander segmentExpander = mock(EMFFormsDMRSegmentExpander.class);
		when(segmentExpander.isApplicable(any(VDomainModelReferenceSegment.class))).thenReturn(1d);

		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		final VDomainModelReferenceSegment segment1 = VViewFactory.eINSTANCE.createFeatureDomainModelReferenceSegment();
		final VDomainModelReferenceSegment segment2 = VViewFactory.eINSTANCE.createFeatureDomainModelReferenceSegment();
		dmr.getSegments().add(segment1);
		dmr.getSegments().add(segment2);

		final EObject domainObject = mock(EObject.class);
		final EObject resultForSegment1 = mock(EObject.class);
		when(segmentExpander.prepareDomainObject(segment1, domainObject)).thenReturn(Optional.of(resultForSegment1));

		domainExpander.addEMFFormsDMRExpander(dmrExpander);
		domainExpander.addEMFFormsDMRSegmentExpander(segmentExpander);

		domainExpander.prepareDomainObject(dmr, domainObject);

		verify(dmrExpander, never()).isApplicable(any(VDomainModelReference.class));
		verify(dmrExpander, never()).prepareDomainObject(any(VDomainModelReference.class), any(EObject.class));
		verify(segmentExpander).isApplicable(segment1);
		verify(segmentExpander).prepareDomainObject(segment1, domainObject);
	}

	@Test
	public void testPrepareDomainObject() throws EMFFormsExpandingFailedException {

		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		final VDomainModelReferenceSegment segment1 = VViewFactory.eINSTANCE.createFeatureDomainModelReferenceSegment();
		final VDomainModelReferenceSegment segment2 = VViewFactory.eINSTANCE.createFeatureDomainModelReferenceSegment();
		final VDomainModelReferenceSegment segment3 = VViewFactory.eINSTANCE.createFeatureDomainModelReferenceSegment();
		dmr.getSegments().add(segment1);
		dmr.getSegments().add(segment2);
		dmr.getSegments().add(segment3);
		final EObject domainObject = mock(EObject.class);
		final EObject resultForSegment1 = mock(EObject.class);

		final EMFFormsDMRSegmentExpander segmentExpander = mock(EMFFormsDMRSegmentExpander.class);
		when(segmentExpander.isApplicable(any(VDomainModelReferenceSegment.class))).thenReturn(1d);
		when(segmentExpander.prepareDomainObject(segment1, domainObject)).thenReturn(Optional.of(resultForSegment1));
		when(segmentExpander.prepareDomainObject(segment2, resultForSegment1))
			.thenReturn(Optional.of(mock(EObject.class)));

		domainExpander.addEMFFormsDMRSegmentExpander(segmentExpander);

		domainExpander.prepareDomainObject(dmr, domainObject);

		verify(segmentExpander).prepareDomainObject(segment1, domainObject);
		verify(segmentExpander).prepareDomainObject(segment2, resultForSegment1);
		verify(segmentExpander, never()).prepareDomainObject(eq(segment3), any(EObject.class));
	}

	@Test
	public void testPrepareDomainObjectLastSegmentNeedsExpansion() throws EMFFormsExpandingFailedException {

		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		final VDomainModelReferenceSegment segment1 = VViewFactory.eINSTANCE.createFeatureDomainModelReferenceSegment();
		final VDomainModelReferenceSegment segment2 = VViewFactory.eINSTANCE.createFeatureDomainModelReferenceSegment();
		final VDomainModelReferenceSegment segment3 = VViewFactory.eINSTANCE.createFeatureDomainModelReferenceSegment();
		dmr.getSegments().add(segment1);
		dmr.getSegments().add(segment2);
		dmr.getSegments().add(segment3);
		final EObject domainObject = mock(EObject.class);
		final EObject resultForSegment1 = mock(EObject.class);
		final EObject resultForSegment2 = mock(EObject.class);

		final EMFFormsDMRSegmentExpander segmentExpander = mock(EMFFormsDMRSegmentExpander.class);
		when(segmentExpander.isApplicable(any(VDomainModelReferenceSegment.class))).thenReturn(1d);
		when(segmentExpander.needsToExpandLastSegment()).thenReturn(true);
		when(segmentExpander.prepareDomainObject(segment1, domainObject)).thenReturn(Optional.of(resultForSegment1));
		when(segmentExpander.prepareDomainObject(segment2, resultForSegment1))
			.thenReturn(Optional.of(resultForSegment2));

		domainExpander.addEMFFormsDMRSegmentExpander(segmentExpander);

		domainExpander.prepareDomainObject(dmr, domainObject);

		verify(segmentExpander).prepareDomainObject(segment1, domainObject);
		verify(segmentExpander).prepareDomainObject(segment2, resultForSegment1);
		verify(segmentExpander).prepareDomainObject(segment3, resultForSegment2);
	}

	@Test(expected = EMFFormsExpandingFailedException.class)
	public void testPrepareDomainObjectNoSuitableSegmentExpander() throws EMFFormsExpandingFailedException {
		final EMFFormsDMRSegmentExpander segmentExpander = mock(EMFFormsDMRSegmentExpander.class);
		when(segmentExpander.isApplicable(any(VDomainModelReferenceSegment.class)))
			.thenReturn(EMFFormsDMRSegmentExpander.NOT_APPLICABLE);
		domainExpander.addEMFFormsDMRSegmentExpander(segmentExpander);

		final VDomainModelReferenceSegment segment1 = VViewFactory.eINSTANCE.createFeatureDomainModelReferenceSegment();
		final VDomainModelReferenceSegment segment2 = VViewFactory.eINSTANCE.createFeatureDomainModelReferenceSegment();
		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		dmr.getSegments().add(segment1);
		dmr.getSegments().add(segment2);

		domainExpander.prepareDomainObject(dmr, mock(EObject.class));
	}

	@Test
	public void testPrepareDomainObjectAllDMRSegmentExpandersConsidered() throws EMFFormsExpandingFailedException {
		final EMFFormsDMRSegmentExpander segmentExpander1 = mock(EMFFormsDMRSegmentExpander.class);
		when(segmentExpander1.isApplicable(any(VDomainModelReferenceSegment.class))).thenReturn(1d);
		final EMFFormsDMRSegmentExpander segmentExpander2 = mock(EMFFormsDMRSegmentExpander.class);
		when(segmentExpander2.isApplicable(any(VDomainModelReferenceSegment.class))).thenReturn(2d);
		final EMFFormsDMRSegmentExpander segmentExpander3 = mock(EMFFormsDMRSegmentExpander.class);
		when(segmentExpander3.isApplicable(any(VDomainModelReferenceSegment.class))).thenReturn(3d);
		domainExpander.addEMFFormsDMRSegmentExpander(segmentExpander1);
		domainExpander.addEMFFormsDMRSegmentExpander(segmentExpander2);
		domainExpander.addEMFFormsDMRSegmentExpander(segmentExpander3);

		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		final VDomainModelReferenceSegment segment1 = VViewFactory.eINSTANCE.createFeatureDomainModelReferenceSegment();
		final VDomainModelReferenceSegment segment2 = VViewFactory.eINSTANCE.createFeatureDomainModelReferenceSegment();
		dmr.getSegments().add(segment1);
		dmr.getSegments().add(segment2);

		when(segmentExpander3.prepareDomainObject(eq(segment1), any(EObject.class)))
			.thenReturn(Optional.of(mock(EObject.class)));

		domainExpander.prepareDomainObject(dmr, mock(EObject.class));
		verify(segmentExpander1, atLeastOnce()).isApplicable(segment1);
		verify(segmentExpander2, atLeastOnce()).isApplicable(segment1);
		verify(segmentExpander3, atLeastOnce()).isApplicable(segment1);
	}

	@Test
	public void testPrepareDomainObjectUseCorrectSegmentExpander() throws EMFFormsExpandingFailedException {
		final EMFFormsDMRSegmentExpander segmentExpander1 = mock(EMFFormsDMRSegmentExpander.class);
		when(segmentExpander1.isApplicable(any(VDomainModelReferenceSegment.class))).thenReturn(1d);
		final EMFFormsDMRSegmentExpander segmentExpander2 = mock(EMFFormsDMRSegmentExpander.class);
		when(segmentExpander2.isApplicable(any(VDomainModelReferenceSegment.class)))
			.thenReturn(EMFFormsDMRSegmentExpander.NOT_APPLICABLE);
		final EMFFormsDMRSegmentExpander segmentExpander3 = mock(EMFFormsDMRSegmentExpander.class);
		when(segmentExpander3.isApplicable(any(VDomainModelReferenceSegment.class))).thenReturn(3d);
		domainExpander.addEMFFormsDMRSegmentExpander(segmentExpander1);
		domainExpander.addEMFFormsDMRSegmentExpander(segmentExpander2);
		domainExpander.addEMFFormsDMRSegmentExpander(segmentExpander3);

		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		final VDomainModelReferenceSegment segment1 = VViewFactory.eINSTANCE.createFeatureDomainModelReferenceSegment();
		final VDomainModelReferenceSegment segment2 = VViewFactory.eINSTANCE.createFeatureDomainModelReferenceSegment();
		dmr.getSegments().add(segment1);
		dmr.getSegments().add(segment2);
		final EObject domainObject = mock(EObject.class);

		when(segmentExpander3.prepareDomainObject(eq(segment1), any(EObject.class)))
			.thenReturn(Optional.of(mock(EObject.class)));

		domainExpander.prepareDomainObject(dmr, domainObject);
		verify(segmentExpander3).prepareDomainObject(segment1, domainObject);
	}
}
