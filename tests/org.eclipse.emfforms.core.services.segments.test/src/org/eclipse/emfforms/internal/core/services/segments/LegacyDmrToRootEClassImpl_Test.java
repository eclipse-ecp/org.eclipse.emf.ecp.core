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
package org.eclipse.emfforms.internal.core.services.segments;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.segments.DmrToRootEClassConverter;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link LegacyDmrToRootEClassImpl}.
 *
 * @author Lucas Koehler
 *
 */
public class LegacyDmrToRootEClassImpl_Test {

	private LegacyDmrToRootEClassImpl fixture;
	private ReportService reportService;
	private VDomainModelReference dmr;

	@Before
	public void setUp() {
		fixture = new LegacyDmrToRootEClassImpl();
		reportService = mock(ReportService.class);
		fixture.setReportService(reportService);
		dmr = mock(VDomainModelReference.class);
	}

	@Test
	public void getRootEClass_noConverters() {
		final Optional<EClass> result = fixture.getRootEClass(dmr);

		assertFalse(result.isPresent());
		verify(reportService, times(1)).report(notNull(AbstractReport.class));
	}

	@Test
	public void getRootEClass_noApplicableConverter() {
		final DmrToRootEClassConverter converter = mock(DmrToRootEClassConverter.class);
		when(converter.isApplicable(dmr)).thenReturn(DmrToRootEClassConverter.NOT_APPLICABLE);

		fixture.addDmrToRootEClassConverter(converter);
		final Optional<EClass> result = fixture.getRootEClass(dmr);

		assertFalse(result.isPresent());
		verify(reportService, times(1)).report(notNull(AbstractReport.class));
		verify(converter, never()).getRootEClass(any());
	}

	@Test
	public void getRootEClass_useHighestRankingConverter() {
		final DmrToRootEClassConverter converter1 = mock(DmrToRootEClassConverter.class);
		when(converter1.isApplicable(dmr)).thenReturn(1d);
		final DmrToRootEClassConverter converter2 = mock(DmrToRootEClassConverter.class);
		when(converter2.isApplicable(dmr)).thenReturn(5d);
		final EClass eClass = mock(EClass.class);
		when(converter2.getRootEClass(dmr)).thenReturn(eClass);
		final DmrToRootEClassConverter converter3 = mock(DmrToRootEClassConverter.class);
		when(converter3.isApplicable(dmr)).thenReturn(DmrToRootEClassConverter.NOT_APPLICABLE);

		fixture.addDmrToRootEClassConverter(converter1);
		fixture.addDmrToRootEClassConverter(converter2);
		fixture.addDmrToRootEClassConverter(converter3);
		final Optional<EClass> result = fixture.getRootEClass(dmr);

		assertTrue(result.isPresent());
		assertSame(eClass, result.get());
		verify(reportService, never()).report(any());
		verify(converter1).isApplicable(dmr);
		verify(converter2).isApplicable(dmr);
		verify(converter3).isApplicable(dmr);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getRootEClass_converterThrowsException() {
		final DmrToRootEClassConverter converter = mock(DmrToRootEClassConverter.class);
		when(converter.isApplicable(dmr)).thenReturn(1d);
		when(converter.getRootEClass(dmr)).thenThrow(IllegalArgumentException.class);

		fixture.addDmrToRootEClassConverter(converter);
		final Optional<EClass> result = fixture.getRootEClass(dmr);

		assertFalse(result.isPresent());
		verify(reportService, times(1)).report(notNull(AbstractReport.class));
	}
}
