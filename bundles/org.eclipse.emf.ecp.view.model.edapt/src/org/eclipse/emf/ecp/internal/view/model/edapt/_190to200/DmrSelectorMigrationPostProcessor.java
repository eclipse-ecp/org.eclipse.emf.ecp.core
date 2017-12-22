/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.internal.view.model.edapt._190to200;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecp.view.edapt.MigrationPostProcessor;

/**
 * This {@link MigrationPostProcessor} is used to set the dmrRootEClass feature added to the
 * VTDomainModelReferenceSelector after the Edapt migration finished.
 * This is done because the migration cannot set the EClass because no Instance of the EClass can be created in Edapt.
 *
 * @author Lucas Koehler
 *
 */
public interface DmrSelectorMigrationPostProcessor extends MigrationPostProcessor {
	/**
	 * Saves the given EClass to set it to the domain model reference selector specified by the given UUID.
	 *
	 * @param uuid The UUID identifying the DomainModelReferenceSelector
	 * @param dmrRootEClass The {@link EClass} to set as the selectors dmrRootEClass
	 */
	void addSelectorToRootEClassMapping(String uuid, EClass dmrRootEClass);
}
