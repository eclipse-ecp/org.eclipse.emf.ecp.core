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
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;

/**
 * Service which replaces all legacy domain model references of a view model with equivalent plain DMRs containing the
 * corresponding segments.
 *
 * @author Lucas Koehler
 * @since 1.22
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
	 * Migrates a view model to use segment based DMRs. This replaces all legacy DMRs from the view model and replaces
	 * them with segment based DMRs. This is not undoable!
	 *
	 * @param resourceUri The URI of the view model that should be migrated.
	 * @param preReplaceProcessors {@link PreReplaceProcessor} which are executed just before a legacy dmr is replaced
	 *            with the corresponding segment dmr. The processors are always executed in the given order
	 * @throws DmrToSegmentsMigrationException if the migration fails
	 */
	void performMigration(URI resourceUri, PreReplaceProcessor... preReplaceProcessors)
		throws DmrToSegmentsMigrationException;

	/**
	 * Processor that is executed after segments have been generated for the new segment based dmr but before the legacy
	 * dmr is replaced in the model. The processor may alter any of the two dmrs or any other part of the containing
	 * resource.
	 */
	@FunctionalInterface
	public interface PreReplaceProcessor {
		/**
		 * <p>
		 * Processes the legacy dmr, the segment dmr or any related EObject <strong>before</strong> the legacy dmr is
		 * replaced with the segment dmr in the model.
		 * </p>
		 * <p>
		 * This may also alter the model including the legacy dmr or the segment dmr.
		 *
		 * @param legacyDmr The legacy dmr which will be replaced in the model
		 * @param segmentDmr The segment based dmr which is already filled with segments based on the legacy dmr
		 */
		void process(VDomainModelReference legacyDmr, VDomainModelReference segmentDmr);
	}
}
