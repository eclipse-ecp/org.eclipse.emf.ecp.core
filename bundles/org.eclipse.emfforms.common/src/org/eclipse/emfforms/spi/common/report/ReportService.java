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
package org.eclipse.emfforms.spi.common.report;

import java.util.List;

/**
 * Service for reporting and aggregating errors.
 *
 * @author emueller
 * @since 1.5
 */
public interface ReportService {

	/**
	 * Report an {@link AbstractReport} to the service.
	 *
	 * @param reportEntity
	 *            the report entity
	 */
	void report(AbstractReport reportEntity);

	/**
	 * Returns all ReportEntities.
	 *
	 * @return all ReportEntities
	 */
	List<AbstractReport> getReports();

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
