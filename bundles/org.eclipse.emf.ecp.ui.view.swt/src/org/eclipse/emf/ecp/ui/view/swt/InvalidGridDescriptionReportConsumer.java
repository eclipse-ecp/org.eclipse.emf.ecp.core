/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.ui.view.swt;

import org.eclipse.emf.ecp.view.spi.swt.reporting.InvalidGridDescriptionReport;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportServiceConsumer;

/**
 *
 *
 * @author emueller
 * @since 1.5
 *
 */
public class InvalidGridDescriptionReportConsumer implements ReportServiceConsumer {

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.common.report.ReportServiceConsumer#reported(org.eclipse.emfforms.spi.common.report.AbstractReport)
	 * @since 1.6
	 */
	@Override
	public void reported(AbstractReport reportEntity) {
		if (InvalidGridDescriptionReport.class.isInstance(reportEntity)) {
			// TODO: mimics existing behavior; should we rather show a dialog?
			throw new IllegalStateException("Invalid number of cells, expected exactly one cell!"); //$NON-NLS-1$
		}
	}
}
