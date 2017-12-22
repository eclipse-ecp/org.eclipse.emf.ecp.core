/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * lucas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.edapt;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edapt.migration.MigrationException;

/**
 * A {@link MigrationPreProcessor} executes migration steps before any Edapt migration related steps are executed by the
 * {@link EdaptViewModelMigrator}.
 *
 * @author Lucas Koehler
 *
 */
public interface MigrationPreProcessor {

	/**
	 * Process the model contained in the given {@link Resource}.
	 * It is assumed that the resource is loaded and saved by the caller of the processor.
	 *
	 * @param modelToMigrate The Resource containing the model that will be migrated later on.
	 * @throws MigrationException if the pre procession encounters an error
	 */
	void process(Resource modelToMigrate) throws MigrationException;
}
