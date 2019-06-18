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

import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emfforms.spi.core.services.segments.DmrSegmentGenerator;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link SegmentGeneratorService}.
 *
 * @author Lucas Koehler
 *
 */
public class SegmentGeneratorService_Test {

	private SegmentGeneratorService service;

	@Before
	public void setUp() {
		service = new SegmentGeneratorService();
	}

	@Test(expected = IllegalStateException.class)
	public void noApplicableGenerator() {
		final DmrSegmentGenerator generator = mock(DmrSegmentGenerator.class);
		when(generator.isApplicable(any(VDomainModelReference.class))).thenReturn(DmrSegmentGenerator.NOT_APPLICABLE);

		service.addDmrSegmentGenerator(generator);
		service.generateSegments(mock(VDomainModelReference.class));
	}

	@Test
	public void useBestGenerators() {
		final DmrSegmentGenerator generator1 = mock(DmrSegmentGenerator.class);
		when(generator1.isApplicable(any(VDomainModelReference.class))).thenReturn(1d);
		final DmrSegmentGenerator generator2 = mock(DmrSegmentGenerator.class);
		when(generator2.isApplicable(any(VDomainModelReference.class))).thenReturn(100d);
		final DmrSegmentGenerator generator3 = mock(DmrSegmentGenerator.class);
		when(generator3.isApplicable(any(VDomainModelReference.class))).thenReturn(5d);

		final List<VDomainModelReferenceSegment> list = new LinkedList<>();
		when(generator2.generateSegments(any(VDomainModelReference.class))).thenReturn(list);

		service.addDmrSegmentGenerator(generator1);
		service.addDmrSegmentGenerator(generator2);
		service.addDmrSegmentGenerator(generator3);

		final VDomainModelReference dmr = mock(VDomainModelReference.class);
		final List<VDomainModelReferenceSegment> result = service.generateSegments(dmr);

		// Verify that the correct generator was used and its result returned and verify that all generators were
		// considered
		assertSame(list, result);
		verify(generator1, times(1)).isApplicable(dmr);
		verify(generator2, times(1)).isApplicable(dmr);
		verify(generator3, times(1)).isApplicable(dmr);
		verify(generator1, never()).generateSegments(any(VDomainModelReference.class));
		verify(generator2, times(1)).generateSegments(dmr);
		verify(generator3, never()).generateSegments(any(VDomainModelReference.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void dmrNull() {
		service.generateSegments(null);
	}
}
