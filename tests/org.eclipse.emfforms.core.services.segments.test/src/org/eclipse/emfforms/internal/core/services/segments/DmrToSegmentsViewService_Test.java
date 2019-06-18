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
package org.eclipse.emfforms.internal.core.services.segments;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.vertical.model.VVerticalFactory;
import org.eclipse.emf.ecp.view.spi.vertical.model.VVerticalLayout;
import org.eclipse.emfforms.spi.core.services.segments.EMFFormsSegmentGenerator;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link DmrToSegmentsViewService}.
 *
 * @author Lucas Koehler
 *
 */
public class DmrToSegmentsViewService_Test {

	private EMFFormsSegmentGenerator segmentGenerator;
	private VView view;
	private EMFFormsViewContext viewContext;

	@Before
	public void setUp() {
		segmentGenerator = mock(EMFFormsSegmentGenerator.class);
		view = VViewFactory.eINSTANCE.createView();

		viewContext = mock(EMFFormsViewContext.class);
		when(viewContext.getService(EMFFormsSegmentGenerator.class)).thenReturn(segmentGenerator);
		when(viewContext.getViewModel()).thenReturn(view);
	}

	@Test
	public void initialSegmentCreation() {
		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		final VDomainModelReference dmrWithSegments = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();

		// Add controls with dmrs, one control is cascaded in a vertical layout
		final VControl control1 = VViewFactory.eINSTANCE.createControl();
		final VControl control2 = VViewFactory.eINSTANCE.createControl();
		final VVerticalLayout verticalLayout = VVerticalFactory.eINSTANCE.createVerticalLayout();
		control1.setDomainModelReference(dmr);
		control2.setDomainModelReference(dmrWithSegments);
		verticalLayout.getChildren().add(control1);
		view.getChildren().add(verticalLayout);
		view.getChildren().add(control2);

		// Configure segments and the segment generator
		final VFeatureDomainModelReferenceSegment existingSegment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		dmrWithSegments.getSegments().add(existingSegment);
		final VDomainModelReferenceSegment generatedSegment1 = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		final VDomainModelReferenceSegment generatedSegment2 = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		final List<VDomainModelReferenceSegment> list = new LinkedList<>();
		list.add(generatedSegment1);
		list.add(generatedSegment2);
		when(segmentGenerator.generateSegments(any(VDomainModelReference.class))).thenReturn(list);

		// Create service and thereby trigger the initial segment generation
		new DmrToSegmentsViewService(viewContext);

		// The generated segments were added to the dmr without segments
		assertEquals(2, dmr.getSegments().size());
		assertSame(generatedSegment1, dmr.getSegments().get(0));
		assertSame(generatedSegment2, dmr.getSegments().get(1));

		// Segments of the dmr with existing segments were not changed
		assertEquals(1, dmrWithSegments.getSegments().size());
		assertSame(existingSegment, dmrWithSegments.getSegments().get(0));

		// Only once for the dmr without segments
		verify(segmentGenerator, times(1)).generateSegments(any(VDomainModelReference.class));
		verify(segmentGenerator, times(1)).generateSegments(dmr);
	}

	/**
	 * Tests that Segments are also generated for DMRs that are added to the view model after the service has been
	 * created.
	 */
	@Test
	public void segmentCreationForAddedDmrs() {
		final DmrToSegmentsViewService service = new DmrToSegmentsViewService(viewContext);
		// The service registers itself as a view change listener
		verify(viewContext).registerViewChangeListener(service);

		final VDomainModelReferenceSegment generatedSegment1 = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		final VDomainModelReferenceSegment generatedSegment2 = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		final List<VDomainModelReferenceSegment> list = new LinkedList<>();
		list.add(generatedSegment1);
		list.add(generatedSegment2);
		when(segmentGenerator.generateSegments(any(VDomainModelReference.class))).thenReturn(list);

		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();

		service.notifyAdd(dmr);

		// The generated segments were added to the dmr without segments
		assertEquals(2, dmr.getSegments().size());
		assertSame(generatedSegment1, dmr.getSegments().get(0));
		assertSame(generatedSegment2, dmr.getSegments().get(1));

		// A control is added
		service.notifyAdd(mock(VControl.class));

		// Add a DMR with existing segments
		final VDomainModelReference dmrWithSegments = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		final VFeatureDomainModelReferenceSegment existingSegment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		dmrWithSegments.getSegments().add(existingSegment);
		service.notifyAdd(dmrWithSegments);

		// Segments of the dmr with existing segments were not changed
		assertEquals(1, dmrWithSegments.getSegments().size());
		assertSame(existingSegment, dmrWithSegments.getSegments().get(0));

		// The segment generator was queried once for the dmr without segments
		verify(segmentGenerator, times(1)).generateSegments(any(VDomainModelReference.class));
		verify(segmentGenerator, times(1)).generateSegments(dmr);
	}
}
