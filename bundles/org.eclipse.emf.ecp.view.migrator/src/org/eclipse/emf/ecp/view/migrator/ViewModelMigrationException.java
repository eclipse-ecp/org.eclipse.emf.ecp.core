/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
