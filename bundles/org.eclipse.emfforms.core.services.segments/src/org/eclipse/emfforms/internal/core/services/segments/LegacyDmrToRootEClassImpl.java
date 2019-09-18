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

import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emfforms.common.RankingHelper;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.segments.DmrToRootEClassConverter;
import org.eclipse.emfforms.spi.core.services.segments.LegacyDmrToRootEClass;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

/**
 * Default implementation of {@link LegacyDmrToRootEClass}.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "LegacyDmrToRootEClassImpl")
public class LegacyDmrToRootEClassImpl implements LegacyDmrToRootEClass {

	private static final RankingHelper<DmrToRootEClassConverter> RANKING_HELPER = new RankingHelper<>(
		DmrToRootEClassConverter.class,
		DmrToRootEClassConverter.NOT_APPLICABLE,
		DmrToRootEClassConverter.NOT_APPLICABLE);

	private final List<DmrToRootEClassConverter> converters = new LinkedList<>();
	private ReportService reportService;

	/**
	 * Adds a {@link DmrToRootEClassConverter}.
	 *
	 * @param converter The {@link DmrToRootEClassConverter}
	 */
	@Reference(cardinality = ReferenceCardinality.MULTIPLE)
	void addDmrToRootEClassConverter(DmrToRootEClassConverter converter) {
		converters.add(converter);
	}

	/**
	 * Removes a {@link DmrToRootEClassConverter}.
	 *
	 * @param converter The {@link DmrToRootEClassConverter}
	 */
	void removeDmrToRootEClassConverter(DmrToRootEClassConverter converter) {
		converters.remove(converter);
	}

	/**
	 * Sets the {@link ReportService}.
	 *
	 * @param reportService The {@link ReportService} to report problems during root EClass resolvement
	 */
	@Reference(unbind = "-")
	void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	@Override
	public Optional<EClass> getRootEClass(VDomainModelReference dmr) {
		final DmrToRootEClassConverter bestConverter = RANKING_HELPER.getHighestRankingElement(converters,
			converter -> converter.isApplicable(dmr));
		if (bestConverter != null) {
			try {
				return Optional.of(bestConverter.getRootEClass(dmr));
			} catch (final IllegalArgumentException ex) {
				reportService.report(new AbstractReport(ex, "Could not determine root EClass due to an exception.")); //$NON-NLS-1$
			}
		} else {
			reportService.report(new AbstractReport(MessageFormat.format(
				"Could not get root EClass for DMR ''{0}'' because there was no applicable DmrToRootEClassConverter.", //$NON-NLS-1$
				dmr), IStatus.WARNING));
		}
		return Optional.empty();
	}

}
