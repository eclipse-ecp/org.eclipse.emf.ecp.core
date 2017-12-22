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
 * A {@link MigrationPostProcessor} executes migration steps after the Edapt migration itself was finished.
 *
 * @author Lucas Koehler
 *
 */
public interface MigrationPostProcessor {

	/**
	 * Process the model contained in the given {@link Resource}.
	 * It is assumed that the resource is loaded and saved by the caller of the processor.
	 *
	 * @param migratedModel The Resource containing the model to process.
	 * @throws MigrationException if the post procession encounters an error
	 */
	void process(Resource migratedModel) throws MigrationException;

}
