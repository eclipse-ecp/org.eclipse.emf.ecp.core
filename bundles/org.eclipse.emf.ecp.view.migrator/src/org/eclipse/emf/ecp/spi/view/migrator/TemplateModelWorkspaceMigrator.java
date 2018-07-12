/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.spi.view.migrator;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;

/**
 * A service that provides methods to get all uris of template models in the workspace that need to be migrated and to
 * migrate these models.
 * 
 * @author Lucas Koehler
 * @since 1.17
 *
 */
public interface TemplateModelWorkspaceMigrator {

	/**
	 * Returns a list of template model {@link URI}s that need to be migrated.
	 *
	 * @return the template model URIs
	 * @throws CoreException if a problem occurred while searching the workspace
	 **/
	List<URI> getURIsToMigrate() throws CoreException;

	/**
	 * Migrates a template model to the latest version.
	 *
	 * @param resourceURI The URI of the template model that should be migrated.
	 * @throws TemplateModelMigrationException If the migration fails.
	 */
	void performMigration(URI resourceURI) throws TemplateModelMigrationException;
}
