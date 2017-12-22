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
package org.eclipse.emf.ecp.internal.view.template.selector.domainmodelreference.model.edapt;

import java.io.IOException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecp.ide.spi.util.EcoreHelper;
import org.eclipse.emf.ecp.view.edapt.MigrationPreProcessor;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplate;
import org.eclipse.emf.edapt.migration.MigrationException;

/**
 * Registers all referenced Ecores of a {@link VTViewTemplate} to the package registry to allow finding them
 * during the migration and the migration post processing.
 *
 * @author Lucas Koehler
 *
 */
public class DmrSelectorMigrationPreProcessor implements MigrationPreProcessor {

	/**
	 *
	 * {@inheritDoc}
	 * <p>
	 * Registers all referenced ecores of the {@link VTViewTemplate} to the package registry to allow finding them
	 * during the migration and the migration post processing.
	 *
	 * @see org.eclipse.emf.ecp.view.edapt.MigrationPreProcessor#process(org.eclipse.emf.ecore.resource.Resource)
	 */
	@Override
	public void process(Resource modelToMigrate) throws MigrationException {

		final EList<EObject> contents = modelToMigrate.getContents();
		if (contents.isEmpty()) {
			// nothing to process
			return;
		}

		final EObject eObject = contents.get(0);
		if (!VTViewTemplate.class.isInstance(eObject)) {
			return;
		}

		final VTViewTemplate viewTemplate = (VTViewTemplate) eObject;
		for (final String ecorePath : viewTemplate.getReferencedEcores()) {
			try {
				EcoreHelper.registerEcore(ecorePath);
			} catch (final IOException e) {
				new MigrationException(e);
			}
		}
	}

}
