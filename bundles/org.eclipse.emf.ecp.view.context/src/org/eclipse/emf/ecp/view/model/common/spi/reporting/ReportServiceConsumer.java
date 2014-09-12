/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.common.spi.reporting;

/**
 * Common base types for any {@link ReportEntity} consumer that may
 * be added to the {@link ReportService} in order to be notified.
 * 
 * @author emueller
 * 
 */
public interface ReportServiceConsumer {

	/**
	 * Called when a {@link ReportEntity} has been received
	 * by the {@link ReportService}.
	 * 
	 * @param reportEntity
	 *            the received {@link ReportEntity}
	 */
	void reported(ReportEntity reportEntity);

}
