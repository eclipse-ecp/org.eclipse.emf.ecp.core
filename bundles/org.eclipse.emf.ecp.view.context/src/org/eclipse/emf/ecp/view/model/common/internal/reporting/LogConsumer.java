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
package org.eclipse.emf.ecp.view.model.common.internal.reporting;

import org.eclipse.emf.ecp.view.internal.context.Activator;
import org.eclipse.emf.ecp.view.model.common.spi.reporting.ReportEntity;
import org.eclipse.emf.ecp.view.model.common.spi.reporting.ReportServiceConsumer;

/**
 * A {@link ReportServiceConsumer} that logs all all received {@code ReportEntities}.
 * 
 * @author emueller
 */
public class LogConsumer implements ReportServiceConsumer {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.model.common.spi.reporting.ReportServiceConsumer#reported(org.eclipse.emf.ecp.view.model.common.spi.reporting.ReportEntity)
	 */
	@Override
	public void reported(ReportEntity reportEntity) {
		Activator.log(reportEntity);
	}

}
