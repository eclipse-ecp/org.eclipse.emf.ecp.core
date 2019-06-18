/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emfforms.internal.core.services.segments.index;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.common.spi.asserts.Assert;
import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexDomainModelReference;
import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexdmrPackage;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.segments.DmrSegmentGenerator;
import org.eclipse.emfforms.spi.core.services.segments.EMFFormsSegmentGenerator;
import org.eclipse.emfforms.spi.view.indexsegment.model.VIndexDomainModelReferenceSegment;
import org.eclipse.emfforms.spi.view.indexsegment.model.VIndexsegmentFactory;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * {@link DmrSegmentGenerator} that generates {@link VDomainModelReferenceSegment segments} for a
 * {@link VIndexDomainModelReference}.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "IndexDmrSegmentGenerator")
public class IndexDmrSegmentGenerator implements DmrSegmentGenerator {

	private EMFFormsSegmentGenerator emfFormsSegmentGenerator;
	private ReportService reportService;

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
	public double isApplicable(VDomainModelReference reference) {
		Assert.create(reference).notNull();
		if (reference.eClass() == VIndexdmrPackage.Literals.INDEX_DOMAIN_MODEL_REFERENCE) {
			return 5d;
		}
		return NOT_APPLICABLE;
	}

	@Override
	public List<VDomainModelReferenceSegment> generateSegments(VDomainModelReference reference) {
		Assert.create(reference).notNull();
		if (reference.eClass() != VIndexdmrPackage.Literals.INDEX_DOMAIN_MODEL_REFERENCE) {
			throw new IllegalArgumentException(
				String.format("The given DMR was no index domain model reference. The DMR was: %s", reference)); //$NON-NLS-1$
		}
		final VIndexDomainModelReference indexDmr = (VIndexDomainModelReference) reference;
		final List<VDomainModelReferenceSegment> result = new LinkedList<>();

		// If the index dmr has a prefix dmr, use it to resolve the path to the indexed list.
		// Otherwise, use the index dmr's reference path and domain model e feature
		if (indexDmr.getPrefixDMR() != null) {
			final List<VDomainModelReferenceSegment> prefixSegments = getEMFFormsSegmentGenerator()
				.generateSegments(indexDmr.getPrefixDMR());
			if (prefixSegments.isEmpty()) {
				reportFailure("no segments could be generated for the prefix dmr", indexDmr); //$NON-NLS-1$
				return Collections.emptyList();
			}

			// Transform the last segment of the prefix to an index segment and set its index
			final VFeatureDomainModelReferenceSegment lastPrefixSegment = (VFeatureDomainModelReferenceSegment) prefixSegments
				.get(prefixSegments.size() - 1);
			final VIndexDomainModelReferenceSegment indexSegment = createIndexSegment(
				lastPrefixSegment.getDomainModelFeature(), indexDmr.getIndex());

			// Add the path to the index segment (if present) and the index segment to the result
			prefixSegments.remove(lastPrefixSegment);
			result.addAll(prefixSegments);
			result.add(indexSegment);
		} else {
			// Create segments for reference path
			for (final EReference eReference : indexDmr.getDomainModelEReferencePath()) {
				final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
					.createFeatureDomainModelReferenceSegment();
				segment.setDomainModelFeature(eReference.getName());
				result.add(segment);
			}
			// Create segment for the domain model e feature
			if (indexDmr.getDomainModelEFeature() != null) {
				final VIndexDomainModelReferenceSegment indexSegment = createIndexSegment(
					indexDmr.getDomainModelEFeature().getName(), indexDmr.getIndex());
				result.add(indexSegment);
			} else {
				reportFailure("it has no prefix dmr and its domain model e feature is null", indexDmr); //$NON-NLS-1$
				return Collections.emptyList();
			}
		}

		// Get the segments for the target dmr and add them to the result
		final List<VDomainModelReferenceSegment> targetSegments = getEMFFormsSegmentGenerator()
			.generateSegments(indexDmr.getTargetDMR());
		if (targetSegments.isEmpty()) {
			reportFailure("no target segments could be generated", indexDmr); //$NON-NLS-1$
			return Collections.emptyList();
		}
		result.addAll(targetSegments);

		return result;
	}

	private VIndexDomainModelReferenceSegment createIndexSegment(String domainModelFeature, int index) {
		final VIndexDomainModelReferenceSegment indexSegment = VIndexsegmentFactory.eINSTANCE
			.createIndexDomainModelReferenceSegment();
		indexSegment.setDomainModelFeature(domainModelFeature);
		indexSegment.setIndex(index);
		return indexSegment;
	}

	private void reportFailure(String reason, VDomainModelReference indexDmr) {
		final StringBuilder sb = new StringBuilder();
		sb.append("Could not generate segments for the index dmr because "); //$NON-NLS-1$
		sb.append(reason);
		sb.append(". The index DMR was: %s"); //$NON-NLS-1$
		reportService.report(new AbstractReport(String.format(sb.toString(), indexDmr), IStatus.WARNING));
	}

	// Manually get the EMFFormsSegmentGenerator to avoid a circular dependency
	private EMFFormsSegmentGenerator getEMFFormsSegmentGenerator() {
		if (emfFormsSegmentGenerator == null) {
			final BundleContext bundleContext = FrameworkUtil.getBundle(IndexDmrSegmentGenerator.class)
				.getBundleContext();
			final ServiceReference<EMFFormsSegmentGenerator> serviceReference = bundleContext
				.getServiceReference(EMFFormsSegmentGenerator.class);
			emfFormsSegmentGenerator = bundleContext.getService(serviceReference);
		}
		return emfFormsSegmentGenerator;
	}

	/**
	 * Sets the {@link EMFFormsSegmentGenerator}.
	 * <p>
	 * Package protected because this method should only be used for testing
	 *
	 * @param generator The {@link EMFFormsSegmentGenerator}
	 */
	void setEMFFormsSegmentGenerator(EMFFormsSegmentGenerator generator) {
		emfFormsSegmentGenerator = generator;
	}
}
