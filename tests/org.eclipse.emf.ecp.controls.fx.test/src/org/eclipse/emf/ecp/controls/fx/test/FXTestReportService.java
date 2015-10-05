/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * jfaltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.controls.fx.test;

import java.util.List;

import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.common.report.ReportServiceConsumer;

/**
 * @author jfaltermeier
 *
 */
public class FXTestReportService implements ReportService {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emfforms.spi.common.report.ReportService#report(org.eclipse.emfforms.spi.common.report.AbstractReport)
	 */
	@Override
	public void report(AbstractReport reportEntity) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emfforms.spi.common.report.ReportService#getReports()
	 */
	@Override
	public List<AbstractReport> getReports() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emfforms.spi.common.report.ReportService#clearReports()
	 */
	@Override
	public void clearReports() {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emfforms.spi.common.report.ReportService#addConsumer(org.eclipse.emfforms.spi.common.report.ReportServiceConsumer)
	 */
	@Override
	public void addConsumer(ReportServiceConsumer consumer) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emfforms.spi.common.report.ReportService#removeConsumer(org.eclipse.emfforms.spi.common.report.ReportServiceConsumer)
	 */
	@Override
	public void removeConsumer(ReportServiceConsumer consumer) {
		// TODO Auto-generated method stub

	}

}
