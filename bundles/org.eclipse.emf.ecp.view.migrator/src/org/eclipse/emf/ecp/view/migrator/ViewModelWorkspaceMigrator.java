/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.migrator;

import java.util.ArrayList;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;

/**
 * Migrator that handles all the view models in the workspace.
 *
 * @since 1.8
 * @author Alexandra Buzila
 */
public interface ViewModelWorkspaceMigrator {

	/**
	 * Returns a list of viewmodel {@link URI}s that need to be migrated.
	 *
	 * @return the URIs
	 * @throws CoreException if a problem occurred while searching the workspace
	 **/
	ArrayList<URI> getURIsToMigrate() throws CoreException;

}
