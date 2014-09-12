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
package org.eclipse.emf.ecp.view.model.common.internal.reporting;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecp.view.model.common.spi.reporting.ReportEntity;
import org.eclipse.emf.ecp.view.model.common.spi.reporting.ReportService;
import org.eclipse.emf.ecp.view.model.common.spi.reporting.ReportServiceConsumer;

/**
 * Implementation of a {@link ReportService}.
 * 
 * @author emueller
 */
public class ReportServiceImpl implements ReportService {

	private final List<ReportEntity> reports;
	private final Set<ReportServiceConsumer> consumers;

	/**
	 * Constructor.
	 */
	public ReportServiceImpl() {
		reports = new ArrayList<ReportEntity>();
		consumers = new LinkedHashSet<ReportServiceConsumer>();
	}

	/**
	 * Report an {@link ReportEntity} to the service.
	 * 
	 * @param reportEntity
	 *            the report entity
	 */
	@Override
	public void report(ReportEntity reportEntity) {
		reports.add(reportEntity);
	}

	/**
	 * Returns all ReportEntities.
	 * 
	 * @return all ReportEntities
	 */
	@Override
	public List<ReportEntity> getReports() {
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
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.model.common.spi.reporting.ReportService#addConsumer(org.eclipse.emf.ecp.view.model.common.spi.reporting.ReportServiceConsumer)
	 */
	@Override
	public void addConsumer(ReportServiceConsumer consumer) {
		consumers.add(consumer);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.model.common.spi.reporting.ReportService#removeConsumer(org.eclipse.emf.ecp.view.model.common.spi.reporting.ReportServiceConsumer)
	 */
	@Override
	public void removeConsumer(ReportServiceConsumer consumer) {
		consumers.remove(consumer);
	}
}