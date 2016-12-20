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

	private static final String INDEX = "index"; //$NON-NLS-1$
	private static final String DOMAIN_MODEL_FEATURE = "domainModelFeature"; //$NON-NLS-1$
	private static final String SEGMENTS = "segments"; //$NON-NLS-1$
	private static final String DOMAIN_MODEL_REFERENCE = "http://org/eclipse/emf/ecp/view/model/200.DomainModelReference"; //$NON-NLS-1$
	private static final String FEATURE_PATH_DOMAIN_MODEL_REFERENCE = "http://org/eclipse/emf/ecp/view/model/200.FeaturePathDomainModelReference"; //$NON-NLS-1$
	private static final String INDEX_DOMAIN_MODEL_REFERENCE = "http://www/eclipse/org/emf/ecp/view/indexdmr/model/200.IndexDomainModelReference"; //$NON-NLS-1$

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.edapt.migration.CustomMigration#migrateBefore(org.eclipse.emf.edapt.spi.migration.Model,
	 *      org.eclipse.emf.edapt.spi.migration.Metamodel)
	 */
	@Override
	public void migrateBefore(Model model, Metamodel metamodel) throws MigrationException {
		// Get all FPDMRs excluding sub classes.
		final EList<Instance> allFeaturePathDMRs = model
			.getInstances(FEATURE_PATH_DOMAIN_MODEL_REFERENCE);
		for (final Instance dmr : allFeaturePathDMRs) {
			migrateFeaturePathDmr(model, metamodel, dmr);
		}

		// -------------------------
		// Index DMR Migration
		final EClass indexDmrEClass = metamodel.getEClass(INDEX_DOMAIN_MODEL_REFERENCE);
		EList<Instance> indexDmrs = model.getInstances(indexDmrEClass);
		while (!indexDmrs.isEmpty()) {
			// TODO performance problem?
			final Instance indexDmr = indexDmrs.get(0);
			migrateIndexDmr(model, metamodel, indexDmr);
			indexDmrs = model.getInstances(indexDmrEClass);
		}
	}

	private void migrateDmr(Model model, Metamodel metamodel, Instance dmr) throws MigrationException {
		final EClass indexDmrEClass = metamodel.getEClass(INDEX_DOMAIN_MODEL_REFERENCE);
		final EClass featurePathDmrEClass = metamodel.getEClass(FEATURE_PATH_DOMAIN_MODEL_REFERENCE);
		if (featurePathDmrEClass.equals(dmr.getEClass())) {
			migrateFeaturePathDmr(model, metamodel, dmr);
		} else if (indexDmrEClass.equals(dmr.getEClass())) {
			migrateIndexDmr(model, metamodel, dmr);
		} else {
			// TODO remove
			System.out.println("No suitable migration method for " + dmr); //$NON-NLS-1$
		}
	}

	private void migrateIndexDmr(Model model, Metamodel metamodel, Instance dmr) throws MigrationException {
		final EClass dmrEClass = metamodel.getEClass(DOMAIN_MODEL_REFERENCE);
		final EClass indexDmrEClass = metamodel
			.getEClass(INDEX_DOMAIN_MODEL_REFERENCE);
		final EClass indexSegmentEClass = metamodel
			.getEClass("http://www/eclipse/org/emf/ecp/view/indexdmr/model/200.IndexDomainModelReferenceSegment"); //$NON-NLS-1$
		final EClass featureSegmentEClass = metamodel
			.getEClass("http://org/eclipse/emf/ecp/view/model/200.FeatureDomainModelReferenceSegment"); //$NON-NLS-1$
		final EReference targetDmrEReference = (EReference) indexDmrEClass.getEStructuralFeature("targetDMR"); //$NON-NLS-1$
		final EReference prefixDmrEReference = (EReference) indexDmrEClass.getEStructuralFeature("prefixDMR"); //$NON-NLS-1$
		final EStructuralFeature indexEAttribute = indexDmrEClass.getEStructuralFeature(INDEX);

		final Instance newDMR = model.newInstance(dmrEClass);
		final Instance prefixDmr = dmr.get(prefixDmrEReference);
		final Instance targetDmr = dmr.get(targetDmrEReference);
		final Integer index = dmr.get(indexEAttribute);

		if (prefixDmr == null) {
			// Index dmr uses reference path and domain model e feature

			// migrate reference path
			final EList<Instance> referencePath = dmr.get("domainModelEReferencePath"); //$NON-NLS-1$
			for (final Instance pathPart : referencePath) {
				final Instance featureSegment = createSegmentForStructuralFeatureInstance(model,
					featureSegmentEClass, pathPart);
				newDMR.add(SEGMENTS, featureSegment);
			}

			// migrate domainModelEFeature
			final Instance domainModelEFeature = dmr.get("domainModelEFeature"); //$NON-NLS-1$
			final Instance indexSegment = createSegmentForStructuralFeatureInstance(model,
				indexSegmentEClass, domainModelEFeature);
			indexSegment.set(INDEX, index);
			newDMR.add(SEGMENTS, indexSegment);
		} else {
			migrateDmr(model, metamodel, prefixDmr);
			// prefix dmr feature of index dmr now contains a migrated dmr with segments
			final Instance newPrefixDmr = dmr.get(prefixDmrEReference);
			final EList<Instance> prefixDmrSegments = newPrefixDmr.get(SEGMENTS);
			// All but the last segment are copied
			for (int i = 0; i < prefixDmrSegments.size() - 1; i++) {
				newDMR.add(SEGMENTS, prefixDmrSegments.get(i).copy());
			}
			// The last segment needs to be "transformed" to an index segment
			final Instance lastPrefixDmrSegment = prefixDmrSegments.get(prefixDmrSegments.size() - 1);
			final String domainModelFeature = lastPrefixDmrSegment.get(DOMAIN_MODEL_FEATURE);
			final Instance indexSegment = createSegmentForStructuralFeatureName(model, indexSegmentEClass,
				domainModelFeature);
			indexSegment.set(INDEX, index);
			newDMR.add(SEGMENTS, indexSegment);
		}

		// migrate target dmr
		migrateDmr(model, metamodel, targetDmr);
		final Instance newTargetDmr = dmr.get(targetDmrEReference);
		final EList<Instance> targetDmrSegments = newTargetDmr.get(SEGMENTS);
		for (final Instance targetDmrSegment : targetDmrSegments) {
			// copy is important because the original segment instance will be deleted from the model when the migrated
			// target dmr is deleted as part of the old dmr.
			newDMR.add(SEGMENTS, targetDmrSegment.copy());
		}

		// replace IndexDmr with new DMR
		final Instance container = dmr.getContainer();
		final EReference containerReference = dmr.getContainerReference();
		container.set(containerReference, newDMR);
		// Delete old Index DMR from the model
		model.delete(dmr);
	}

	/**
	 * @param model
	 * @param feature
	 * @param dmr The feature path dmr to be migrated
	 * @throws MigrationException
	 */
	private void migrateFeaturePathDmr(Model model, Metamodel metamodel, final Instance dmr)
		throws MigrationException {

		final EClass featurePathDmrEClass = metamodel
			.getEClass(FEATURE_PATH_DOMAIN_MODEL_REFERENCE);
		final EReference feature = (EReference) featurePathDmrEClass.getEStructuralFeature("domainModelEFeature"); //$NON-NLS-1$
		final EReference path = (EReference) featurePathDmrEClass.getEStructuralFeature("domainModelEReferencePath"); //$NON-NLS-1$
		final EClass dmrEClass = metamodel.getEClass(DOMAIN_MODEL_REFERENCE);
		final EReference segments = (EReference) dmrEClass.getEStructuralFeature(SEGMENTS);
		final EClass featureSegmentEClass = metamodel
			.getEClass("http://org/eclipse/emf/ecp/view/model/200.FeatureDomainModelReferenceSegment"); //$NON-NLS-1$

		final Instance newDMR = model.newInstance(dmrEClass);

		// migrate reference path
		final EList<Instance> referencePath = dmr.get(path);
		for (final Instance pathPart : referencePath) {
			final Instance featureSegment = createSegmentForStructuralFeatureInstance(model,
				featureSegmentEClass, pathPart);
			newDMR.add(segments, featureSegment);
		}

		// migrate domainModelEFeature
		final Instance domainModelEFeature = dmr.get(feature);
		final Instance featureSegment = createSegmentForStructuralFeatureInstance(model,
			featureSegmentEClass, domainModelEFeature);
		newDMR.add(segments, featureSegment);

		// replace FeaturePathDMR with new DMR
		final Instance container = dmr.getContainer();
		final EReference containerReference = dmr.getContainerReference();
		// container.unset(containerReference);
		container.set(containerReference, newDMR);
		// Delete old FeaturePathDMR from the model
		model.delete(dmr);
	}

	/**
	 * Creates a segment of the given EClass and sets its 'domainModelFeature' attribute according to the given
	 * {@link EStructuralFeature}.
	 *
	 * @param model
	 * @param segmentEClass The EClass that defines which type of segment is created
	 * @param structuralFeature The {@link Instance} of the {@link EStructuralFeature} for which a segment will be
	 *            created
	 * @return The {@link Instance} of the segment for the given structural feature instance
	 * @throws MigrationException
	 */
	private Instance createSegmentForStructuralFeatureInstance(Model model, final EClass segmentEClass,
		final Instance structuralFeature) throws MigrationException {
		return createSegmentForStructuralFeatureName(model, segmentEClass,
			getNameOfStructuralFeature(structuralFeature));
	}

	/**
	 * Creates a segment of the given EClass and sets its 'domainModelFeature' attribute according to the given
	 * structural feature name.
	 *
	 * @param model
	 * @param segmentEClass The EClass that defines which type of segment is created
	 * @param structuralFeatureName The name of the {@link EStructuralFeature} for which a segment will be
	 *            created
	 * @return The {@link Instance} of the segment for the given structural feature name
	 * @throws MigrationException
	 */
	private Instance createSegmentForStructuralFeatureName(Model model, EClass segmentEClass,
		String structuralFeatureName) {
		final Instance segment = model.newInstance(segmentEClass);
		segment.set(DOMAIN_MODEL_FEATURE, structuralFeatureName);
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
