/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edapt.migration.CustomMigration;
import org.eclipse.emf.edapt.migration.MigrationException;
import org.eclipse.emf.edapt.spi.migration.Instance;
import org.eclipse.emf.edapt.spi.migration.Metamodel;
import org.eclipse.emf.edapt.spi.migration.Model;

/**
 * {@link CustomMigration} that migrates feature path DMRs to "normal" DMRs with segments.
 *
 * @author Lucas Koehler
 *
 */
public class FeaturePathDMRRemovalMigration extends CustomMigration {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.edapt.migration.CustomMigration#migrateBefore(org.eclipse.emf.edapt.spi.migration.Model,
	 *      org.eclipse.emf.edapt.spi.migration.Metamodel)
	 */
	@Override
	public void migrateBefore(Model model, Metamodel metamodel) throws MigrationException {
		final EClass featurePathDMREClass = metamodel
			.getEClass("http://org/eclipse/emf/ecp/view/model/200.FeaturePathDomainModelReference"); //$NON-NLS-1$
		final EReference feature = (EReference) featurePathDMREClass.getEStructuralFeature("domainModelEFeature"); //$NON-NLS-1$
		final EReference path = (EReference) featurePathDMREClass.getEStructuralFeature("domainModelEReferencePath"); //$NON-NLS-1$

		final EClass dmrEClass = metamodel.getEClass("http://org/eclipse/emf/ecp/view/model/200.DomainModelReference"); //$NON-NLS-1$
		final EReference segments = (EReference) dmrEClass.getEStructuralFeature("segments"); //$NON-NLS-1$

		final EClass featureSegmentEClass = metamodel
			.getEClass("http://org/eclipse/emf/ecp/view/model/200.FeatureDomainModelReferenceSegment"); //$NON-NLS-1$

		// Get all FPDMRs including sub classes. This might have to change in the future
		final EList<Instance> allFeaturePathDMRs = model
			.getAllInstances("http://org/eclipse/emf/ecp/view/model/200.FeaturePathDomainModelReference"); //$NON-NLS-1$
		for (final Instance dmr : allFeaturePathDMRs) {
			final Instance newDMR = model.newInstance(dmrEClass);

			// migrate reference path
			final EList<Instance> referencePath = dmr.get(path);
			for (final Instance pathPart : referencePath) {
				final Instance featureSegment = createFeatureSegmentForEStructuralFeatureInstance(model,
					featureSegmentEClass, pathPart);
				newDMR.add(segments, featureSegment);
			}

			// migrate domainModelEFeature
			final Instance domainModelEFeature = dmr.get(feature);
			final Instance featureSegment = createFeatureSegmentForEStructuralFeatureInstance(model,
				featureSegmentEClass, domainModelEFeature);
			newDMR.add(segments, featureSegment);

			// replace FeaturePathDMR with new DMR
			final Instance container = dmr.getContainer();
			final EReference containerReference = dmr.getContainerReference();
			container.unset(containerReference);
			container.set(containerReference, newDMR);

			// Delete old FeaturePathDMR from the model
			model.delete(dmr);
		}
	}

	/**
	 * @param model
	 * @param featureSegmentEClass
	 * @param structuralFeature The {@link Instance} of the {@link EStructuralFeature} for which a segment will be
	 *            created
	 * @return The {@link Instance} of the segment for the given structural feature instance
	 * @throws MigrationException
	 */
	private Instance createFeatureSegmentForEStructuralFeatureInstance(Model model, final EClass segmentEClass,
		final Instance structuralFeature) throws MigrationException {
		final Instance segment = model.newInstance(segmentEClass);
		final String strucutralFeatureName = getNameOfStructuralFeature(structuralFeature);
		segment.set("domainModelFeature", strucutralFeatureName); //$NON-NLS-1$
		return segment;
	}

	/**
	 * @param structuralFeatureInstance
	 * @throws MigrationException
	 */
	private String getNameOfStructuralFeature(final Instance structuralFeatureInstance) throws MigrationException {
		// TODO get feature name in a clean way, this is a hack
		final String[] uriParts = structuralFeatureInstance.getUri().toString().split("/"); //$NON-NLS-1$
		if (uriParts.length > 0) {
			return uriParts[uriParts.length - 1];
		}
		throw new MigrationException(
			new IllegalStateException("The URI of the current domain model e feature was empty.")); //$NON-NLS-1$
	}
}
