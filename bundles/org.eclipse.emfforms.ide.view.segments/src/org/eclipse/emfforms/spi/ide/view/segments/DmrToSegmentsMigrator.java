/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.ide.view.segments;

import org.eclipse.emf.common.util.URI;

/**
 * Service which replaces all legacy domain model references of a view model with equivalent plain DMRs containing the
 * corresponding segments.
 *
 * @author Lucas Koehler
 *
 */
public interface DmrToSegmentsMigrator {

	/**
	 * Checks whether a view model still contains legacy domain model references that need to be migrated to segments.
	 *
	 * @param resourceUri The URI of the view model that should be checked.
	 * @return true, if the view model requires a migration, false otherwise.
	 */
	boolean needsMigration(URI resourceUri);

	/**
	 * Migrates a view model to use segment based DMRs. This removes the legacy from the view model and is not undoable!
	 *
	 * @param resourceUri The URI of the view model that should be migrated.
	 * @throws DmrToSegmentsMigrationException if the migration fails
	 */
	void performMigration(URI resourceUri) throws DmrToSegmentsMigrationException;
}
