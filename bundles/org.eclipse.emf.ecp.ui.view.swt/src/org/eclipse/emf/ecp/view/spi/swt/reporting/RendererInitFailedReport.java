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
package org.eclipse.emf.ecp.view.spi.swt.reporting;

import java.text.MessageFormat;

import org.eclipse.emfforms.spi.common.report.AbstractReport;

/**
 * An error report that indicates that an renderer failed during its initialization.
 *
 * @author emueller
 * @since 1.5
 *
 */
public class RendererInitFailedReport extends AbstractReport {

	/**
	 * Constructor.
	 *
	 * @param exception
	 *            the underlying exception
	 */
	public RendererInitFailedReport(Throwable exception) {
		super(exception,
			MessageFormat.format("Initialization of renderer failed due to: {0}", //$NON-NLS-1$
				exception.getMessage()));
	}
}
