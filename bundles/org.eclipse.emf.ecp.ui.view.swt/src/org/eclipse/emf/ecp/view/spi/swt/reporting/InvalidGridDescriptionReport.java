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

import org.eclipse.emfforms.spi.common.report.AbstractReport;

/**
 * Indicates that a renderer returned an invalid grid description.
 *
 * @author emueller
 * @since 1.5
 *
 */
public class InvalidGridDescriptionReport extends AbstractReport {

	/**
	 * Constructor.
	 *
	 * @param msg
	 *            an error message
	 */
	public InvalidGridDescriptionReport(String msg) {
		super(msg);
	}

}
