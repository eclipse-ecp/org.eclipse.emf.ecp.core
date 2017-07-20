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

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
 * {@link CustomMigration} that migrates feature path DMRs and subclasses to "normal" DMRs with segments.
 *
 * @author Lucas Koehler
 *
 */
public class FeaturePathDMRRemovalMigration extends CustomMigration {

	private static final String MAPPING_DOMAIN_MODEL_REFERENCE = "http://www/eclipse/org/emf/ecp/view/mappingdmr/model/200.MappingDomainModelReference"; //$NON-NLS-1$
	private static final String INDEX = "index"; //$NON-NLS-1$
	private static final String DOMAIN_MODEL_FEATURE = "domainModelFeature"; //$NON-NLS-1$
	private static final String SEGMENTS = "segments"; //$NON-NLS-1$
	private static final String DOMAIN_MODEL_REFERENCE = "http://org/eclipse/emf/ecp/view/model/200.DomainModelReference"; //$NON-NLS-1$
	private static final String FEATURE_PATH_DOMAIN_MODEL_REFERENCE = "http://org/eclipse/emf/ecp/view/model/200.FeaturePathDomainModelReference"; //$NON-NLS-1$
	private static final String INDEX_DOMAIN_MODEL_REFERENCE = "http://www/eclipse/org/emf/ecp/view/indexdmr/model/200.IndexDomainModelReference"; //$NON-NLS-1$
	private static final String TABLE_DOMAIN_MODEL_REFERENCE = "http://org/eclipse/emf/ecp/view/table/model/190.TableDomainModelReference"; //$NON-NLS-1$
	private static final String MULTI_SEGMENT = "http://org/eclipse/emfforms/view/multisegment/model/200.MultiDomainModelReferenceSegment"; //$NON-NLS-1$

	private static final String TABLE_WIDTH_CONFIGURATION = "http://org/eclipse/emf/ecp/view/table/model/190.WidthConfiguration"; //$NON-NLS-1$
	private static final String TABLE_READ_ONLY_CONFIGURATION = "http://org/eclipse/emf/ecp/view/table/model/190.ReadOnlyColumnConfiguration"; //$NON-NLS-1$ ;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.edapt.migration.CustomMigration#migrateBefore(org.eclipse.emf.edapt.spi.migration.Model,
	 *      org.eclipse.emf.edapt.spi.migration.Metamodel)
	 */
	@Override
	public void migrateBefore(Model model, Metamodel metamodel) throws MigrationException {
		fixMappingMetaModel(metamodel);

		// Prepare table width configuration migrations
		final Map<Instance, Integer> configIndices = new LinkedHashMap<Instance, Integer>();
		final EClass widthConfigEClass = metamodel.getEClass(TABLE_WIDTH_CONFIGURATION);
		final EList<Instance> allTableWidthConfigs = model.getInstances(widthConfigEClass);
		for (final Instance tableWidthConfig : allTableWidthConfigs) {
			prepareWidthConfigMigration(model, metamodel, tableWidthConfig, configIndices);
		}
		// Prepare table read only configuration migrations
		final Map<Instance, List<Integer>> readOnlyConfigIndices = new LinkedHashMap<Instance, List<Integer>>();
		final EClass readOnlyConfigEClass = metamodel.getEClass(TABLE_READ_ONLY_CONFIGURATION);
		final EList<Instance> readOnlyConfigs = model.getInstances(readOnlyConfigEClass);
		for (final Instance readOnlyConfig : readOnlyConfigs) {
			prepareReadOnlyConfigMigration(model, metamodel, readOnlyConfig, readOnlyConfigIndices);
		}

		migrateDmrs(model, metamodel);

		// finish width configuration migration after all dmrs have been migrated
		for (final Instance tableConfig : configIndices.keySet()) {
			migrateWidthConfig(model, metamodel, tableConfig, configIndices.get(tableConfig));
		}

		// finish read only configuration migration after all dmrs have been migrated
		for (final Instance readOnlyConfig : readOnlyConfigIndices.keySet()) {
			migrateReadOnlyConfig(model, metamodel, readOnlyConfig, readOnlyConfigIndices.get(readOnlyConfig));
		}
	}

	/**
	 * @param model
	 * @param metamodel
	 * @throws MigrationException
	 */
	private void migrateDmrs(Model model, Metamodel metamodel) throws MigrationException {
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
			final Instance indexDmr = indexDmrs.get(0);
			migrateIndexDmr(model, metamodel, indexDmr);
			indexDmrs = model.getInstances(indexDmrEClass);
		}

		// -------------------------
		// Mapping final DMR Migration
		final EClass mappingDmrEClass = metamodel.getEClass(MAPPING_DOMAIN_MODEL_REFERENCE);
		EList<Instance> mappingDmrs = model.getInstances(mappingDmrEClass);
		while (!mappingDmrs.isEmpty()) {
			final Instance mappingDmr = mappingDmrs.get(0);
			migrateMappingDmr(model, metamodel, mappingDmr);
			mappingDmrs = model.getInstances(mappingDmrEClass);
		}

		// -------------------------
		// Table DMR Migration
		final EClass tableDmrEClass = metamodel.getEClass(TABLE_DOMAIN_MODEL_REFERENCE);
		EList<Instance> tableDmrs = model.getInstances(tableDmrEClass);
		while (!tableDmrs.isEmpty()) {
			final Instance tableDmr = tableDmrs.get(0);
			migrateTableDmr(model, metamodel, tableDmr);
			tableDmrs = model.getInstances(tableDmrEClass);
		}
	}

	private void migrateWidthConfig(Model model, Metamodel metamodel, Instance tableConfig, int index) {
		final Instance tableControlDmr = tableConfig.getContainer().get("domainModelReference"); //$NON-NLS-1$
		final EList<Instance> segments = tableControlDmr.get("segments"); //$NON-NLS-1$
		final Instance multiSegment = segments.get(segments.size() - 1);
		final EList<Instance> childDmrs = multiSegment.get("childDomainModelReferences"); //$NON-NLS-1$
		tableConfig.set("columnDomainReference", childDmrs.get(index)); //$NON-NLS-1$
	}

	/**
	 * Prepares the migration of VWidthConfiguration.
	 * Therefore, the index of the config's referenced column dmr in the TableControl containing it is stored.
	 * Later, this index is used to reference the correct dmr after its migration.
	 *
	 * @param model
	 * @param metamodel
	 * @param readOnlyConfig
	 * @param configIndices
	 */
	private void prepareWidthConfigMigration(Model model, Metamodel metamodel, Instance tableConfig,
		Map<Instance, Integer> configIndices) {
		final Instance columnDmr = tableConfig.get("columnDomainReference"); //$NON-NLS-1$
		if (columnDmr == null) {
			return;
		}
		// get column dmrs of table control
		final EList<Instance> columnDmrs = columnDmr.getContainer().get(columnDmr.getContainerReference());
		final int index = columnDmrs.indexOf(columnDmr);
		configIndices.put(tableConfig, index);
	}

	private void migrateReadOnlyConfig(Model model, Metamodel metamodel, Instance tableConfig,
		List<Integer> indices) {
		final Instance tableControlDmr = tableConfig.getContainer().get("domainModelReference"); //$NON-NLS-1$
		final EList<Instance> segments = tableControlDmr.get("segments"); //$NON-NLS-1$
		final Instance multiSegment = segments.get(segments.size() - 1);
		final EList<Instance> childDmrs = multiSegment.get("childDomainModelReferences"); //$NON-NLS-1$
		final EList<Instance> configColumnDmrs = tableConfig.get("columnDomainReferences"); //$NON-NLS-1$
		for (final int index : indices) {
			configColumnDmrs.add(childDmrs.get(index));
		}
	}

	/**
	 * Prepares the migration of a VReadOnlyColumConfiguration.
	 * Therefore, the indices of the config's referenced column Dmrs in the TableControl containing them are stored.
	 * Later, these indices are used to reference the correct dmrs after their migrations.
	 *
	 * @param model
	 * @param metamodel
	 * @param readOnlyConfig
	 * @param configIndices
	 */
	private void prepareReadOnlyConfigMigration(Model model, Metamodel metamodel, Instance readOnlyConfig,
		Map<Instance, List<Integer>> configIndices) {
		final EList<Instance> readOnlyConfigDmrs = readOnlyConfig.get("columnDomainReferences"); //$NON-NLS-1$
		if (readOnlyConfigDmrs.isEmpty()) {
			return;
		}
		// get column dmrs of table control
		final EList<Instance> tableControlColumnDmrs = readOnlyConfigDmrs.get(0).getContainer()
			.get(readOnlyConfigDmrs.get(0).getContainerReference());
		for (final Instance configDmr : readOnlyConfigDmrs) {
			final int index = tableControlColumnDmrs.indexOf(configDmr);
			configIndices.putIfAbsent(readOnlyConfig, new LinkedList<Integer>());
			configIndices.get(readOnlyConfig).add(index);
		}
	}

	/**
	 * @param metamodel
	 */
	private void fixMappingMetaModel(Metamodel metamodel) {
		// This method is a hack to fix the bug described in commit 1d8e1d9548c417e104ea8513a81973fb04c4ce62
		final EClass viewEClass = metamodel
			.getEClass("http://org/eclipse/emf/ecp/view/model/200.View"); //$NON-NLS-1$
		final EReference rootClassRef = (EReference) viewEClass.getEStructuralFeature("rootEClass"); //$NON-NLS-1$
		final EClass rootClassRefType = rootClassRef.getEReferenceType();

		final EClass mappingSegmentEClass = metamodel
			.getEClass("http://www/eclipse/org/emf/ecp/view/mappingdmr/model/200.MappingDomainModelReferenceSegment"); //$NON-NLS-1$
		if (mappingSegmentEClass == null) {
			return;
		}
		final EReference mappedClassRef = (EReference) mappingSegmentEClass.getEStructuralFeature("mappedClass"); //$NON-NLS-1$
		mappedClassRef.setEType(rootClassRefType);
	}

	private void migrateDmr(Model model, Metamodel metamodel, Instance dmr) throws MigrationException {
		final EClass dmrEClass = metamodel.getEClass(DOMAIN_MODEL_REFERENCE);
		final EClass featurePathDmrEClass = metamodel.getEClass(FEATURE_PATH_DOMAIN_MODEL_REFERENCE);
		final EClass indexDmrEClass = metamodel.getEClass(INDEX_DOMAIN_MODEL_REFERENCE);
		final EClass mappingDmrEClass = metamodel.getEClass(MAPPING_DOMAIN_MODEL_REFERENCE);
		final EClass tableDmrEClass = metamodel.getEClass(TABLE_DOMAIN_MODEL_REFERENCE);

		if (dmrEClass.equals(dmr.getEClass())) {
			// No migration needed
			return;
		} else if (featurePathDmrEClass.equals(dmr.getEClass())) {
			migrateFeaturePathDmr(model, metamodel, dmr);
		}
		// additional null checks necessary because an EClass is null if it is not used in the model
		else if (indexDmrEClass != null && indexDmrEClass.equals(dmr.getEClass())) {
			migrateIndexDmr(model, metamodel, dmr);
		}
		// TODO uncomment when mapping migration works
		else if (mappingDmrEClass != null && mappingDmrEClass.equals(dmr.getEClass())) {
			migrateMappingDmr(model, metamodel, dmr);
		} else if (tableDmrEClass != null && tableDmrEClass.equals(dmr.getEClass())) {
			migrateTableDmr(model, metamodel, dmr);
		}
	}

	/**
	 * @param model
	 * @param metamodel
	 * @param dmr
	 * @throws MigrationException
	 */
	@SuppressWarnings("unchecked")
	private void migrateTableDmr(Model model, Metamodel metamodel, Instance dmr) throws MigrationException {
		final EClass multiSegmentEClass = metamodel.getEClass(MULTI_SEGMENT);
		if (multiSegmentEClass == null) {
			throw new MigrationException(new IllegalStateException(
				"A Table DMR needs to be migrated but the MultiDomainModelReferenceSegment's EClass cannot be found.")); //$NON-NLS-1$
		}

		final EClass dmrEClass = metamodel.getEClass(DOMAIN_MODEL_REFERENCE);
		final EClass featureSegmentEClass = metamodel
			.getEClass("http://org/eclipse/emf/ecp/view/model/200.FeatureDomainModelReferenceSegment"); //$NON-NLS-1$
		final EClass tableDmrEClass = metamodel.getEClass(TABLE_DOMAIN_MODEL_REFERENCE);
		final EReference domainModelReferenceERef = (EReference) tableDmrEClass
			.getEStructuralFeature("domainModelReference"); //$NON-NLS-1$
		final EReference columnDmrsERef = (EReference) tableDmrEClass
			.getEStructuralFeature("columnDomainModelReferences"); //$NON-NLS-1$

		final Instance newDmr = model.newInstance(dmrEClass);
		final Instance domainModelReference = dmr.get(domainModelReferenceERef);

		final Instance multiSegment;
		if (domainModelReference == null) {
			// Table dmr uses reference path and domain model e feature

			// Migrate domain model reference path
			final EList<Instance> referencePath = dmr.get("domainModelEReferencePath"); //$NON-NLS-1$
			for (final Instance pathPart : referencePath) {
				final Instance featureSegment = createSegmentForStructuralFeatureInstance(model,
					featureSegmentEClass, pathPart);
				newDmr.add(SEGMENTS, featureSegment);
			}

			// migrate domainModelEFeature
			final Instance domainModelEFeature = dmr.get("domainModelEFeature"); //$NON-NLS-1$
			multiSegment = createSegmentForStructuralFeatureInstance(model,
				multiSegmentEClass, domainModelEFeature);
		} else {
			migrateDmr(model, metamodel, domainModelReference);
			// domain model reference feature of table dmr now contains a migrated dmr with segments
			final Instance newDomainModelReference = dmr.get(domainModelReferenceERef);
			final EList<Instance> domainModelReferenceSegments = newDomainModelReference.get(SEGMENTS);
			// All but the last segment are copied
			for (int i = 0; i < domainModelReferenceSegments.size() - 1; i++) {
				newDmr.add(SEGMENTS, domainModelReferenceSegments.get(i).copy());
			}
			// The last segment needs to be "transformed" to a multi segment
			final Instance lastDomainModelReferenceSegment = domainModelReferenceSegments
				.get(domainModelReferenceSegments.size() - 1);
			final String domainModelFeature = lastDomainModelReferenceSegment.get(DOMAIN_MODEL_FEATURE);
			multiSegment = createSegmentForStructuralFeatureName(model, multiSegmentEClass,
				domainModelFeature);
		}
		newDmr.add(SEGMENTS, multiSegment);

		// Migrate column dmrs and add to multi segment
		final List<Instance> columnDmrs = new LinkedList<Instance>((EList<Instance>) dmr.get(columnDmrsERef));
		for (final Instance columDmr : columnDmrs) {
			migrateDmr(model, metamodel, columDmr);
		}
		for (final Instance migratedColumnDmr : (EList<Instance>) dmr.get(columnDmrsERef)) {
			multiSegment.add("childDomainModelReferences", migratedColumnDmr.copy()); //$NON-NLS-1$
		}

		replaceDmrInContainer(dmr, newDmr);
		model.delete(dmr);
	}

	/**
	 * @param model
	 * @param metamodel
	 * @param dmr
	 * @throws MigrationException
	 */
	private void migrateMappingDmr(Model model, Metamodel metamodel, Instance dmr) throws MigrationException {
		final EClass dmrEClass = metamodel.getEClass(DOMAIN_MODEL_REFERENCE);
		final EClass featureSegmentEClass = metamodel
			.getEClass("http://org/eclipse/emf/ecp/view/model/200.FeatureDomainModelReferenceSegment"); //$NON-NLS-1$
		final EClass mappingSegmentEClass = metamodel
			.getEClass("http://www/eclipse/org/emf/ecp/view/mappingdmr/model/200.MappingDomainModelReferenceSegment"); //$NON-NLS-1$

		final Instance mappedClass = dmr.get("mappedClass"); //$NON-NLS-1$
		final Instance newDMR = model.newInstance(dmrEClass);
		final Instance domainModelReference = dmr.get("domainModelReference"); //$NON-NLS-1$

		// migrate domainModelEReferencePath
		final EList<Instance> referencePath = dmr.get("domainModelEReferencePath"); //$NON-NLS-1$
		for (final Instance pathPart : referencePath) {
			final Instance featureSegment = createSegmentForStructuralFeatureInstance(model,
				featureSegmentEClass, pathPart);
			newDMR.add(SEGMENTS, featureSegment);
		}

		// migrate domainModelEFeature
		final Instance domainModelEFeature = dmr.get("domainModelEFeature"); //$NON-NLS-1$
		final Instance mappingSegment = createSegmentForStructuralFeatureInstance(model,
			mappingSegmentEClass, domainModelEFeature);
		mappingSegment.set("mappedClass", mappedClass); //$NON-NLS-1$
		newDMR.add(SEGMENTS, mappingSegment);

		// migrate domain model reference
		migrateDmr(model, metamodel, domainModelReference);
		final Instance newDomainModelReference = dmr.get("domainModelReference"); //$NON-NLS-1$
		final EList<Instance> domainModelReferenceSegments = newDomainModelReference.get(SEGMENTS);
		for (final Instance segment : domainModelReferenceSegments) {
			newDMR.add(SEGMENTS, segment.copy());
		}

		// replace Mapping Dmr with new DMR
		replaceDmrInContainer(dmr, newDMR);
		model.delete(dmr);
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
		replaceDmrInContainer(dmr, newDMR);
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
		replaceDmrInContainer(dmr, newDMR);
		// Delete old FeaturePathDMR from the model
		model.delete(dmr);
	}

	/**
	 * @param oldDmr The DMR that is replaced
	 * @param newDmr The new DMR
	 */
	private void replaceDmrInContainer(Instance oldDmr, Instance newDmr) {
		final Instance container = oldDmr.getContainer();
		if (container == null) {
			return;
		}
		final EReference containerReference = oldDmr.getContainerReference();
		if (containerReference.isMany()) {
			final EList<Instance> list = container.get(containerReference);
			final int index = list.indexOf(oldDmr);
			list.remove(index);
			list.add(index, newDmr);
		} else {
			container.set(containerReference, newDmr);
		}
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
