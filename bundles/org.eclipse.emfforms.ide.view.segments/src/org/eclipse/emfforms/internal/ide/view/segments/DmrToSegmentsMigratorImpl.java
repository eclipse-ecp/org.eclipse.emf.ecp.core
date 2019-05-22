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
package org.eclipse.emfforms.internal.ide.view.segments;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.model.util.VViewResourceFactoryImpl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.segments.EMFFormsSegmentGenerator;
import org.eclipse.emfforms.spi.ide.view.segments.DmrToSegmentsMigrationException;
import org.eclipse.emfforms.spi.ide.view.segments.DmrToSegmentsMigrator;
import org.eclipse.emfforms.view.spi.multisegment.model.MultiSegmentUtil;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultiDomainModelReferenceSegment;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Implementation of {@link DmrToSegmentsMigrator}.
 *
 * @author Lucas Koehler
 *
 */
@Component
public class DmrToSegmentsMigratorImpl implements DmrToSegmentsMigrator {

	private EMFFormsSegmentGenerator segmentGenerator;
	private ReportService reportService;

	/**
	 * Sets the {@link EMFFormsSegmentGenerator}.
	 *
	 * @param segmentGenerator The {@link EMFFormsSegmentGenerator}
	 */
	@Reference(unbind = "-")
	void setEMFFormsSegmentGenerator(EMFFormsSegmentGenerator segmentGenerator) {
		this.segmentGenerator = segmentGenerator;
	}

	/**
	 * Sets the {@link ReportService}.
	 *
	 * @param reportService The {@link ReportService}
	 */
	@Reference(unbind = "-")
	void setReportService(ReportService reportService) {
		this.reportService = reportService;

	}

	@Override
	public boolean needsMigration(URI resourceUri) {
		Resource resource;
		try {
			resource = loadResource(resourceUri);
		} catch (final IOException ex) {
			reportService.report(
				new AbstractReport(ex,
					"Could not check whether resource ''{0}'' still uses legacy DMRs because it could not be loaded.", //$NON-NLS-1$
					resourceUri));
			return false;
		}

		return needsMigration(resource);
	}

	/**
	 * Checks whether a view model still contains legacy domain model references that need to be migrated to segments.
	 * The resource must be {@link Resource#load(java.util.Map) loaded} before handing it into this method.
	 *
	 * @param resource The loaded resource
	 * @return true, if the view model requires a migration, false otherwise.
	 */
	protected boolean needsMigration(Resource resource) {
		final TreeIterator<EObject> allContents = resource.getAllContents();
		while (allContents.hasNext()) {
			final EObject next = allContents.next();
			if (isLegacyDmr(next)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void performMigration(URI resourceUri) throws DmrToSegmentsMigrationException {
		final Resource resource;
		try {
			resource = loadResource(resourceUri);
		} catch (final IOException ex) {
			throw new DmrToSegmentsMigrationException(ex, "The resource {0} could not be loaded", resourceUri); //$NON-NLS-1$
		}

		performMigration(resource);

		try {
			resource.save(Collections.singletonMap(XMLResource.OPTION_ENCODING, "UTF-8")); //$NON-NLS-1$
		} catch (final IOException ex) {
			throw new DmrToSegmentsMigrationException(ex, "Migrated resource could not be saved to {0}.", resourceUri); //$NON-NLS-1$
		}
	}

	/**
	 * Migrates all legacy domain model references in the given resource. The resource must be
	 * {@link Resource#load(java.util.Map) loaded} before handing it into this method.
	 *
	 * @param resource The loaded resource
	 * @throws DmrToSegmentsMigrationException if the migration fails
	 */
	protected void performMigration(final Resource resource) throws DmrToSegmentsMigrationException {
		// Collect all legacy DMRs
		final List<VDomainModelReference> legacyDmrs = new LinkedList<>();
		final TreeIterator<EObject> allContents = resource.getAllContents();
		allContents.forEachRemaining(eObject -> {
			if (isLegacyDmr(eObject)) {
				legacyDmrs.add((VDomainModelReference) eObject);
				// Skip contents of legacy DMRs because we do not need to explicitly convert DMRs contained in other
				// DMRs. The contained DMRs will be considered when generating the segments for their parents.
				allContents.prune();
			}
		});

		// Generate new DMRs, replace the legacy ones in place, and adjust cross references
		for (final VDomainModelReference legacyDmr : legacyDmrs) {
			final List<VDomainModelReferenceSegment> segments = segmentGenerator.generateSegments(legacyDmr);
			final VDomainModelReference newDmr = VViewFactory.eINSTANCE.createDomainModelReference();
			newDmr.getSegments().addAll(segments);

			final Map<VDomainModelReference, VDomainModelReference> crossRefReplacements = new HashMap<>();
			crossRefReplacements.put(legacyDmr, newDmr);

			// If the legacy dmr is a table dmr, we need to make sure references to columns (e.g. in read only configs)
			// are migrated. This is not automatically done because we need to skip DMRs contained in other DMRs in the
			// initial legacy DMR collection.
			if (legacyDmr instanceof VTableDomainModelReference) {
				final EList<VDomainModelReference> columnDmrs = VTableDomainModelReference.class.cast(legacyDmr)
					.getColumnDomainModelReferences();
				final EList<VDomainModelReference> childDmrs = MultiSegmentUtil.getMultiSegment(newDmr)
					.map(VMultiDomainModelReferenceSegment::getChildDomainModelReferences)
					.orElseGet(BasicEList::new);
				if (columnDmrs.size() != childDmrs.size()) {
					throw new DmrToSegmentsMigrationException(
						"There was a different number of legacy column DMRs and generated child DMRs for table DMR {0}.", //$NON-NLS-1$
						legacyDmr);
				}
				for (int i = 0; i < columnDmrs.size(); i++) {
					crossRefReplacements.put(columnDmrs.get(i), childDmrs.get(i));
				}
			}

			// Replace legacy dmr with new dmr and then update all cross references to the legacy dmr or its child dmrs
			// (in case of a table dmr)
			EcoreUtil.replace(legacyDmr, newDmr);
			final Map<EObject, Collection<Setting>> usageMap = EcoreUtil.UsageCrossReferencer
				.findAll(crossRefReplacements.keySet(), resource);
			usageMap.forEach((old, usages) -> {
				usages.forEach(setting -> {
					final VDomainModelReference replacement = crossRefReplacements.get(old);
					EcoreUtil.replace(setting, old, replacement);
				});
			});

		}
	}

	/**
	 * Checks whether the given EObject is a legacy domain model reference. Legacy DMRs are all EObjects that are of a
	 * subtype of DomainModelReference but are not of type DomainModelReference itself
	 *
	 * @param eObject The {@link EObject} to check
	 * @return true if the given EObject is a legacy DMR
	 */
	private boolean isLegacyDmr(final EObject eObject) {
		return VViewPackage.Literals.DOMAIN_MODEL_REFERENCE.isSuperTypeOf(eObject.eClass())
			&& !(VViewPackage.Literals.DOMAIN_MODEL_REFERENCE == eObject.eClass());
	}

	private Resource loadResource(URI resourceUri) throws IOException {
		final ResourceSetImpl resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
			Resource.Factory.Registry.DEFAULT_EXTENSION, new VViewResourceFactoryImpl());
		final Resource resource = resourceSet.createResource(resourceUri);
		resource.load(null);
		return resource;
	}
}
