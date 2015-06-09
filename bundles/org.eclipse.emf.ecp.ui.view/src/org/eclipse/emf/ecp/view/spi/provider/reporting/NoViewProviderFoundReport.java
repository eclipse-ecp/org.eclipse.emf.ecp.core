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
package org.eclipse.emf.ecp.view.spi.provider.reporting;

import org.eclipse.emfforms.spi.common.report.AbstractReport;

/**
 * Indicates that no view provider could be obtained.
 *
 * @author emueller
 * @since 1.5
 *
 */
public class NoViewProviderFoundReport extends AbstractReport {

	/**
	 * Default constructor.
	 */
	public NoViewProviderFoundReport() {
		super("No view provider available."); //$NON-NLS-1$
	}

}
