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
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.migrator;

/**
 * Exception that may occur during view model migration.
 *
 * @author jfaltermeier
 *
 */
public class ViewModelMigrationException extends Exception {

	/**
	 * Default constructor.
	 *
	 * @param ex the throwable to wrap
	 */
	public ViewModelMigrationException(Throwable ex) {
		super(ex);
	}

	private static final long serialVersionUID = 968804478300257360L;

}
