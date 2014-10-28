/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.table.edapt._140to150;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edapt.migration.CustomMigration;
import org.eclipse.emf.edapt.migration.MigrationException;
import org.eclipse.emf.edapt.spi.migration.Instance;
import org.eclipse.emf.edapt.spi.migration.Metamodel;
import org.eclipse.emf.edapt.spi.migration.Model;

/**
 * @author jfaltermeier
 *
 */
public class AddedDMRToTableDMRMigration extends CustomMigration {

	@Override
	public void migrateAfter(Model model, Metamodel metamodel)
		throws MigrationException {
		final EReference feature = metamodel.getEReference("model.FeaturePathDomainModelReference.domainModelEFeature"); //$NON-NLS-1$
		final EReference path = metamodel
			.getEReference("model.FeaturePathDomainModelReference.domainModelEReferencePath"); //$NON-NLS-1$
		final EList<Instance> allTableDMRs = model.getAllInstances("table.TableDomainModelReference"); //$NON-NLS-1$
		for (final Instance tableDMR : allTableDMRs) {
			final Instance childDMR = model.newInstance("model.FeaturePathDomainModelReference"); //$NON-NLS-1$
			final Object featureInstance = tableDMR.unset(feature);
			final Object pathInstance = tableDMR.unset(path);
			childDMR.set(feature, featureInstance);
			childDMR.set(path, pathInstance);
			tableDMR.set("domainModelReference", childDMR); //$NON-NLS-1$
		}
	}

}
