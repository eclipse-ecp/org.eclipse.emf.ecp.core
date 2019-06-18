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
package org.eclipse.emfforms.spi.common.report;

/**
 * Common base types for any {@link AbstractReport} consumer that may
 * be added to the {@link ReportService} in order to be notified.
 *
 * @author emueller
 * @since 1.5
 */
public interface ReportServiceConsumer {

	/**
	 * Called when a {@link AbstractReport} has been received
	 * by the {@link ReportService}.
	 *
	 * @param reportEntity
	 *            the received {@link AbstractReport}
	 */
	void reported(AbstractReport reportEntity);

}
