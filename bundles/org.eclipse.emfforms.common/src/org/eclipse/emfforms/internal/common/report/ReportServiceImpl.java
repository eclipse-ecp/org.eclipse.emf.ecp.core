/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.common.report;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.common.report.ReportServiceConsumer;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * Implementation of a {@link ReportService}.
 *
 * @author emueller
 */
@Component
public class ReportServiceImpl implements ReportService {

	private final List<AbstractReport> reports;
	private final Set<ReportServiceConsumer> consumers;

	/**
	 * Constructor.
	 */
	public ReportServiceImpl() {
		reports = new ArrayList<AbstractReport>();
		consumers = new LinkedHashSet<ReportServiceConsumer>();
	}

	/**
	 * Report an {@link AbstractReport} to the service.
	 *
	 * @param reportEntity
	 *            the report entity
	 */
	@Override
	public void report(AbstractReport reportEntity) {
		reports.add(reportEntity);
		for (final ReportServiceConsumer consumer : consumers) {
			consumer.reported(reportEntity);
		}
	}

	/**
	 * Returns all ReportEntities.
	 *
	 * @return all ReportEntities
	 */
	@Override
	public List<AbstractReport> getReports() {
		return reports;
	}

	/**
	 * Discards all ReportEntities.
	 */
	@Override
	public void clearReports() {
		reports.clear();
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.common.report.ReportService#addConsumer(org.eclipse.emfforms.spi.common.report.ReportServiceConsumer)
	 */
	@Override
	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	public void addConsumer(ReportServiceConsumer consumer) {
		consumers.add(consumer);
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.common.report.ReportService#removeConsumer(org.eclipse.emfforms.spi.common.report.ReportServiceConsumer)
	 */
	@Override
	public void removeConsumer(ReportServiceConsumer consumer) {
		consumers.remove(consumer);
	}
}