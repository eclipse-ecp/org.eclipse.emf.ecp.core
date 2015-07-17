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
 * Indicates that an {@link org.eclipse.emf.ecp.view.internal.swt.ECPRendererDescription ECPRendererDescription} could
 * not be initialized.
 *
 * @author emueller
 * @since 1.5
 *
 */
public class ECPRendererDescriptionInitFailedReport extends AbstractReport {

	/**
	 * Constructor.
	 *
	 * @param exception
	 *            the underlying exception
	 */
	public ECPRendererDescriptionInitFailedReport(Throwable exception) {
		super(exception,
			MessageFormat.format("ECPRendererDescription initialization failed due to: {0}", //$NON-NLS-1$
				exception.getMessage()));
	}

}
