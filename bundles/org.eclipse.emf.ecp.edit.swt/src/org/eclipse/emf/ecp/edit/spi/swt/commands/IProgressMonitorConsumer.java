/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.edit.spi.swt.commands;

/**
 * A Marker interface for classes which may consume an {@link IProgressMonitorProvider}.
 *
 * @author Johannes Faltermeier
 * @since 1.11
 *
 */
public interface IProgressMonitorConsumer {

	/**
	 * @param monitor the {@link IProgressMonitorProvider}
	 */
	void setIProgressMonitorAccessor(IProgressMonitorProvider monitor);

}
