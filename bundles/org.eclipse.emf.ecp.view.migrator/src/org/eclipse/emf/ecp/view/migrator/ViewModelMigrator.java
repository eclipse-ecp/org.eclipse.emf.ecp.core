/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler- initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.migrator;

import org.eclipse.emf.common.util.URI;

/**
 * @author Lucas Koehler
 *
 */
public interface ViewModelMigrator {

	/**
	 * Checks whether a view model needs to be migrated.
	 *
	 * @param resourceURI The URI of the view model that should be checked.
	 * @return true, if the view model does not require a migration, false otherwise.
	 */
	boolean checkMigration(URI resourceURI);

	/**
	 * Migrates a view model to the latest version.
	 *
	 * @param resourceURI The URI of the view model that should be migrated.
	 * @throws ViewModelMigrationException in case of an error
	 */
	void performMigration(URI resourceURI) throws ViewModelMigrationException;
}
