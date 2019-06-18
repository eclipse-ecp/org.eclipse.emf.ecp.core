/*******************************************************************************
 * Copyright (c) 2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.internal.reporting;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecp.view.spi.model.impl.Activator;
import org.eclipse.emf.ecp.view.spi.model.util.ViewModelUtil;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportServiceConsumer;

/**
 * A {@link ReportServiceConsumer} that logs all all received {@code ReportEntities}.
 *
 * @author emueller
 */
public class LogConsumer implements ReportServiceConsumer {

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.common.report.ReportServiceConsumer#reported(org.eclipse.emfforms.spi.common.report.AbstractReport)
	 */
	@Override
	public void reported(AbstractReport reportEntity) {
		if (reportEntity.getSeverity() > IStatus.INFO || ViewModelUtil.isDebugMode()) {
			Activator.log(reportEntity);
		}
	}

}
