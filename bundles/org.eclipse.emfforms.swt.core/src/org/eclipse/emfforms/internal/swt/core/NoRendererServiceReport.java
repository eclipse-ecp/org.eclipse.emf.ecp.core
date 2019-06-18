/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.swt.core;

import org.eclipse.emfforms.spi.common.report.AbstractReport;

/**
 * A report to describe that there is no fitting renderer service.
 *
 * @author Eugen Neufeld
 *
 */
public class NoRendererServiceReport extends AbstractReport {

	/**
	 * The default constructor.
	 *
	 * @param exception The {@link Throwable} to report
	 */
	public NoRendererServiceReport(Throwable exception) {
		super(exception);
	}

}
