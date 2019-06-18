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
package org.eclipse.emf.ecp.view.spi.provider.reporting;

import org.eclipse.emfforms.spi.common.report.AbstractReport;

/**
 * Indicates that a {@link org.eclipse.emf.ecp.view.spi.provider.IViewProvider IViewProvider} has returned {@code null}
 * as the {@link org.eclipse.emf.ecp.view.spi.model.VView VView}.
 *
 * @author emueller
 * @since 1.5
 *
 */
public class ViewModelIsNullReport extends AbstractReport {

	/**
	 * Default constructor.
	 */
	public ViewModelIsNullReport() {
		super("ViewProvider returned null as view."); //$NON-NLS-1$
	}

}
