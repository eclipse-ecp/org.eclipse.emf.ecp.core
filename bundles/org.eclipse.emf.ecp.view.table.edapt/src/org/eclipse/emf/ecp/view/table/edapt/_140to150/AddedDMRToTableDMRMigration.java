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
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edapt.migration.CustomMigration;
import org.eclipse.emf.edapt.migration.MigrationException;
import org.eclipse.emf.edapt.spi.migration.Instance;
import org.eclipse.emf.edapt.spi.migration.Metamodel;
import org.eclipse.emf.edapt.spi.migration.Model;

/**
 * Before 1.5.0 a table domain model reference was simply a subclass of feature path domain model reference. This made
 * it impossible to use a different dmr to reach the multi reference. Starting with 1.5.0. it is possible to set a
 * different dmr leading to the multi reference on the table dmr. This migration creates a feature path dmr with the old
 * values on the table.
 *
 * @author jfaltermeier
 *
 */
public class AddedDMRToTableDMRMigration extends CustomMigration {

	@Override
	public void migrateAfter(Model model, Metamodel metamodel)
		throws MigrationException {
		final EPackage viewPkg = getEPackageWithNSPrefix(metamodel, "org.eclipse.emf.ecp.view.model"); //$NON-NLS-1$
		final EClass featurePathDMREClass = (EClass) viewPkg.getEClassifier("FeaturePathDomainModelReference"); //$NON-NLS-1$
		final EReference feature = (EReference) featurePathDMREClass.getEStructuralFeature("domainModelEFeature"); //$NON-NLS-1$
		final EReference path = (EReference) featurePathDMREClass.getEStructuralFeature("domainModelEReferencePath"); //$NON-NLS-1$
		final EList<Instance> allTableDMRs = model.getAllInstances("table.TableDomainModelReference"); //$NON-NLS-1$
		for (final Instance tableDMR : allTableDMRs) {
			if (null == tableDMR.get(feature)) {
				continue;
			}
			final Instance childDMR = model.newInstance(featurePathDMREClass);
			final Object featureInstance = tableDMR.unset(feature);
			final Object pathInstance = tableDMR.unset(path);
			childDMR.set(feature, featureInstance);
			childDMR.set(path, pathInstance);
			tableDMR.set("domainModelReference", childDMR); //$NON-NLS-1$
		}
	}

	private EPackage getEPackageWithNSPrefix(Metamodel metamodel, String nsPrefix) {
		for (final EPackage pkg : metamodel.getEPackages()) {
			if (nsPrefix.equals(pkg.getNsPrefix())) {
				return pkg;
			}
		}
		return null;
	}

}
