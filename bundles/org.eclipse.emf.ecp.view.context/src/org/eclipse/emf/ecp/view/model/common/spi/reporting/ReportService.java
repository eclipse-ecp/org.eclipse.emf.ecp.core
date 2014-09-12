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
package org.eclipse.emf.ecp.view.model.common.spi.reporting;

import java.util.List;

/**
 * Service for reporting and aggregating errors.
 * 
 * @author emueller
 */
public interface ReportService {

	/**
	 * Report an {@link ReportEntity} to the service.
	 * 
	 * @param reportEntity
	 *            the report entity
	 */
	void report(ReportEntity reportEntity);

	/**
	 * Returns all ReportEntities.
	 * 
	 * @return all ReportEntities
	 */
	List<ReportEntity> getReports();

	/**
	 * Discards all ReportEntities.
	 */
	void clearReports();

	/**
	 * Adds a {@link ReportServiceConsumer} that consumes {@code ReportEntities}.
	 * 
	 * @param consumer
	 *            a {@link ReportServiceConsumer}
	 */
	void addConsumer(ReportServiceConsumer consumer);

	/**
	 * Removes a {@link ReportServiceConsumer}.
	 * 
	 * @param consumer
	 *            the consumer to be removed
	 */
	void removeConsumer(ReportServiceConsumer consumer);

}
