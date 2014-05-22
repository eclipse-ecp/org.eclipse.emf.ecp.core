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
package org.eclipse.emf.ecp.view.internal.table.migration;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edapt.migration.CustomMigration;
import org.eclipse.emf.edapt.migration.Instance;
import org.eclipse.emf.edapt.migration.Metamodel;
import org.eclipse.emf.edapt.migration.MigrationException;
import org.eclipse.emf.edapt.migration.Model;

/**
 * @author jfaltermeier
 *
 */
public class MoveAttributeToDomainModelReferenceMigration extends CustomMigration {

	private EReference attributeReference;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.edapt.migration.CustomMigration#migrateBefore(org.eclipse.emf.edapt.migration.Model,
	 *      org.eclipse.emf.edapt.migration.Metamodel)
	 */
	@Override
	public void migrateBefore(Model model, Metamodel metamodel) throws MigrationException {
		attributeReference = metamodel.getEReference("table.ReadOnlyColumnConfiguration.attribute"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.edapt.migration.CustomMigration#migrateAfter(org.eclipse.emf.edapt.migration.Model,
	 *      org.eclipse.emf.edapt.migration.Metamodel)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void migrateAfter(Model model, Metamodel metamodel) throws MigrationException {
		final EList<Instance> allTableControlInstances = model.getAllInstances("table.TableControl"); //$NON-NLS-1$

		for (final Instance tableControl : allTableControlInstances) {
			final List<Instance> columnConfigurations = (List<Instance>) tableControl.get("columnConfigurations"); //$NON-NLS-1$
			final Instance domainModelReference = tableControl.get("domainModelReference"); //$NON-NLS-1$
			final List<Instance> columnDomainModelReferences = domainModelReference.get("columnDomainModelReferences"); //$NON-NLS-1$

			final List<Instance> readOnlyColumnConfigurations = new ArrayList<Instance>();
			final List<Instance> writableColumnConfigurations = new ArrayList<Instance>();

			for (final Instance columnConfiguration : columnConfigurations) {
				final Instance columnAttribute = columnConfiguration.get(attributeReference);
				columnConfiguration.unset(attributeReference);

				final Instance columnDomainModelReference = model.newInstance("model.FeaturePathDomainModelReference"); //$NON-NLS-1$
				columnDomainModelReference.set("domainModelEFeature", columnAttribute); //$NON-NLS-1$

				columnDomainModelReferences.add(columnDomainModelReference);
				columnConfiguration.add("columnDomainReference", columnDomainModelReference); //$NON-NLS-1$

				if (columnConfiguration.get("readOnly")) { //$NON-NLS-1$
					readOnlyColumnConfigurations.add(columnConfiguration);
				} else {
					writableColumnConfigurations.add(columnConfiguration);
				}
			}

			merge(columnConfigurations, readOnlyColumnConfigurations);
			merge(columnConfigurations, writableColumnConfigurations);

		}
	}

	private void merge(List<Instance> allColumnConfigurations, List<Instance> toMerge) {
		// if (toMerge.size() < 2) {
		// return;
		// }
		// final Instance master = toMerge.get(0);
		// for (int i = 1; i < toMerge.size(); i++) {
		// final Instance slave = toMerge.get(i);
		// @SuppressWarnings("unchecked")
		//			final Object reference = ((List<Object>) slave.get("columnDomainReference")).get(0); //$NON-NLS-1$
		//			master.add("columnDomainReference", reference); //$NON-NLS-1$
		// allColumnConfigurations.remove(slave);
		// }
	}
}
