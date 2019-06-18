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
package org.eclipse.emf.ecp.view.spi.swt.reporting;

import org.eclipse.emfforms.spi.common.report.AbstractReport;

/**
 * Indicates that the initialization of a custom control failed.
 *
 * @author emueller
 * @since 1.5
 *
 */
public class CustomControlInitFailedReport extends AbstractReport {

	/**
	 * Constructor.
	 *
	 * @param bundleName
	 *            the name of the bundle containing the custom control
	 * @param customControlClassName
	 *            the name of the class for the custom control
	 */
	public CustomControlInitFailedReport(String bundleName, String customControlClassName) {
		super(String.format("The  %1$s/%2$s cannot be loaded!", //$NON-NLS-1$
			bundleName, customControlClassName));
	}
}
