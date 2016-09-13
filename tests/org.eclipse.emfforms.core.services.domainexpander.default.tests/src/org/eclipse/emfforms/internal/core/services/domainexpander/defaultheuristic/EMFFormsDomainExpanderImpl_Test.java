/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler- initial API and implementation
 * Lucas Koehler - adaption to segments
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.domainexpander.defaultheuristic;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDMRSegmentExpander;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsExpandingFailedException;
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
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.domainexpander.defaultheuristic.EMFFormsDomainExpanderImpl#prepareDomainObject(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}
	 * .
	 *
	 * @throws EMFFormsExpandingFailedException
	 */
	@Test
	public void testPrepareDomainObject() throws EMFFormsExpandingFailedException {

		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createDomainModelReference();
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
		when(segmentExpander.prepareDomainObject(segment1, domainObject)).thenReturn(resultForSegment1);

		domainExpander.addEMFFormsDMRSegmentExpander(segmentExpander);

		domainExpander.prepareDomainObject(dmr, domainObject);

		verify(segmentExpander).prepareDomainObject(segment1, domainObject);
		verify(segmentExpander).prepareDomainObject(segment2, resultForSegment1);
		verify(segmentExpander, never()).prepareDomainObject(eq(segment3), any(EObject.class));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.domainexpander.defaultheuristic.EMFFormsDomainExpanderImpl#prepareDomainObject(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}
	 * .
	 *
	 * @throws EMFFormsExpandingFailedException
	 */
	@Test(expected = EMFFormsExpandingFailedException.class)
	public void testPrepareDomainObjectNoSuitableSegmentExpander() throws EMFFormsExpandingFailedException {
		final EMFFormsDMRSegmentExpander segmentExpander = mock(EMFFormsDMRSegmentExpander.class);
		when(segmentExpander.isApplicable(any(VDomainModelReferenceSegment.class)))
			.thenReturn(EMFFormsDMRSegmentExpander.NOT_APPLICABLE);
		domainExpander.addEMFFormsDMRSegmentExpander(segmentExpander);

		final VDomainModelReferenceSegment segment1 = VViewFactory.eINSTANCE.createFeatureDomainModelReferenceSegment();
		final VDomainModelReferenceSegment segment2 = VViewFactory.eINSTANCE.createFeatureDomainModelReferenceSegment();
		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createDomainModelReference();
		dmr.getSegments().add(segment1);
		dmr.getSegments().add(segment2);

		domainExpander.prepareDomainObject(dmr, mock(EObject.class));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.domainexpander.defaultheuristic.EMFFormsDomainExpanderImpl#prepareDomainObject(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}
	 * .
	 *
	 * @throws EMFFormsExpandingFailedException
	 */
	@Test
	public void testPrepareDomainObjectAllDMRExpandersConsidered() throws EMFFormsExpandingFailedException {
		final EMFFormsDMRSegmentExpander segmentExpander1 = mock(EMFFormsDMRSegmentExpander.class);
		when(segmentExpander1.isApplicable(any(VDomainModelReferenceSegment.class))).thenReturn(1d);
		final EMFFormsDMRSegmentExpander segmentExpander2 = mock(EMFFormsDMRSegmentExpander.class);
		when(segmentExpander2.isApplicable(any(VDomainModelReferenceSegment.class))).thenReturn(2d);
		final EMFFormsDMRSegmentExpander segmentExpander3 = mock(EMFFormsDMRSegmentExpander.class);
		when(segmentExpander3.isApplicable(any(VDomainModelReferenceSegment.class))).thenReturn(3d);
		domainExpander.addEMFFormsDMRSegmentExpander(segmentExpander1);
		domainExpander.addEMFFormsDMRSegmentExpander(segmentExpander2);
		domainExpander.addEMFFormsDMRSegmentExpander(segmentExpander3);

		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createDomainModelReference();
		final VDomainModelReferenceSegment segment1 = VViewFactory.eINSTANCE.createFeatureDomainModelReferenceSegment();
		final VDomainModelReferenceSegment segment2 = VViewFactory.eINSTANCE.createFeatureDomainModelReferenceSegment();
		dmr.getSegments().add(segment1);
		dmr.getSegments().add(segment2);

		domainExpander.prepareDomainObject(dmr, mock(EObject.class));
		verify(segmentExpander1, atLeastOnce()).isApplicable(segment1);
		verify(segmentExpander2, atLeastOnce()).isApplicable(segment1);
		verify(segmentExpander3, atLeastOnce()).isApplicable(segment1);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.domainexpander.defaultheuristic.EMFFormsDomainExpanderImpl#prepareDomainObject(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}
	 * .
	 *
	 * @throws EMFFormsExpandingFailedException
	 */
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

		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createDomainModelReference();
		final VDomainModelReferenceSegment segment1 = VViewFactory.eINSTANCE.createFeatureDomainModelReferenceSegment();
		final VDomainModelReferenceSegment segment2 = VViewFactory.eINSTANCE.createFeatureDomainModelReferenceSegment();
		dmr.getSegments().add(segment1);
		dmr.getSegments().add(segment2);
		final EObject domainObject = mock(EObject.class);

		domainExpander.prepareDomainObject(dmr, domainObject);
		verify(segmentExpander3).prepareDomainObject(segment1, domainObject);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.domainexpander.defaultheuristic.EMFFormsDomainExpanderImpl#prepareDomainObject(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}
	 * .
	 *
	 * @throws EMFFormsExpandingFailedException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testPrepareDomainObjectReferenceNull() throws EMFFormsExpandingFailedException {
		domainExpander.prepareDomainObject(null, mock(EObject.class));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.domainexpander.defaultheuristic.EMFFormsDomainExpanderImpl#prepareDomainObject(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}
	 * .
	 *
	 * @throws EMFFormsExpandingFailedException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testPrepareDomainObjectObjectNull() throws EMFFormsExpandingFailedException {
		domainExpander.prepareDomainObject(mock(VDomainModelReference.class), null);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.domainexpander.defaultheuristic.EMFFormsDomainExpanderImpl#prepareDomainObject(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject)}
	 * .
	 *
	 * @throws EMFFormsExpandingFailedException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testPrepareDomainObjectBothNull() throws EMFFormsExpandingFailedException {
		domainExpander.prepareDomainObject(null, null);
	}
}
