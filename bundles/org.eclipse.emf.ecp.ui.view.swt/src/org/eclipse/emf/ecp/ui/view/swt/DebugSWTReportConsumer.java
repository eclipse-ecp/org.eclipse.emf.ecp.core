/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emf.ecp.ui.view.swt;

import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportServiceConsumer;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

/**
 * A {@link ReportServiceConsumer} that display a dialog
 * for each report.
 *
 * @author emueller
 * @since 1.5
 *
 */
public class DebugSWTReportConsumer implements ReportServiceConsumer {

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.common.report.ReportServiceConsumer#reported(org.eclipse.emfforms.spi.common.report.AbstractReport)
	 * @since 1.6
	 */
	@Override
	public void reported(AbstractReport reportEntity) {
		MessageDialog.openWarning(
			Display.getDefault().getActiveShell(), "Debug", //$NON-NLS-1$
			reportEntity.getMessage());
	}

}
