/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.spi.view.migrator.string;

import org.eclipse.emf.ecp.view.migrator.ViewModelMigrationException;

/**
 * A String View Model Migrator may migrate view models which are available in memory as a string representation.
 *
 * @author Johannes Faltermeier
 * @since 1.8
 *
 */
public interface StringViewModelMigrator {

	/**
	 * Checks whether a view model needs to be migrated.
	 *
	 * @param serializedViewModel the view model that should be checked
	 * @return true, if the view model does not require a migration, false otherwise.
	 */
	boolean checkMigration(String serializedViewModel);

	/**
	 * Migrates a view model to the latest version.
	 *
	 * @param serializedViewModel the view model that should be checked
	 * @return the migrated string
	 * @throws ViewModelMigrationException in case of an error
	 */
	String performMigration(String serializedViewModel) throws ViewModelMigrationException;
}
