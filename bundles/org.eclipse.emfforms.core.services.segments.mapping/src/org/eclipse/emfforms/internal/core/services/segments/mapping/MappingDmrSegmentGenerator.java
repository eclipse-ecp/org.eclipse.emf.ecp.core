/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.segments.mapping;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.common.spi.asserts.Assert;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingDomainModelReference;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingdmrPackage;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.segments.DmrSegmentGenerator;
import org.eclipse.emfforms.spi.core.services.segments.EMFFormsSegmentGenerator;
import org.eclipse.emfforms.spi.view.mappingsegment.model.VMappingDomainModelReferenceSegment;
import org.eclipse.emfforms.spi.view.mappingsegment.model.VMappingsegmentFactory;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * {@link DmrSegmentGenerator} that generates {@link VDomainModelReferenceSegment segments} for a
 * {@link VMappingDomainModelReference}.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "MappingDmrSegmentGenerator")
public class MappingDmrSegmentGenerator implements DmrSegmentGenerator {

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
		if (reference.eClass() == VMappingdmrPackage.Literals.MAPPING_DOMAIN_MODEL_REFERENCE) {
			return 5d;
		}
		return NOT_APPLICABLE;
	}

	@Override
	public List<VDomainModelReferenceSegment> generateSegments(VDomainModelReference reference) {
		Assert.create(reference).notNull();
		if (reference.eClass() != VMappingdmrPackage.Literals.MAPPING_DOMAIN_MODEL_REFERENCE) {
			throw new IllegalArgumentException(
				String.format("The given DMR was no mapping domain model reference. The DMR was: %s", reference)); //$NON-NLS-1$
		}

		final VMappingDomainModelReference mappingDmr = (VMappingDomainModelReference) reference;
		final List<VDomainModelReferenceSegment> result = new LinkedList<>();

		// Create segments for reference path
		for (final EReference eReference : mappingDmr.getDomainModelEReferencePath()) {
			final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
				.createFeatureDomainModelReferenceSegment();
			segment.setDomainModelFeature(eReference.getName());
			result.add(segment);
		}
		// Create segment for the domain model e feature
		if (mappingDmr.getDomainModelEFeature() != null) {
			final VMappingDomainModelReferenceSegment mappingSegment = VMappingsegmentFactory.eINSTANCE
				.createMappingDomainModelReferenceSegment();
			mappingSegment.setDomainModelFeature(mappingDmr.getDomainModelEFeature().getName());
			mappingSegment.setMappedClass(mappingDmr.getMappedClass());
			result.add(mappingSegment);
		} else {
			reportFailure("its domain model e feature is null", mappingDmr); //$NON-NLS-1$
			return Collections.emptyList();
		}

		// Get the segments for the target dmr and add them to the result
		final List<VDomainModelReferenceSegment> targetSegments = getEMFFormsSegmentGenerator()
			.generateSegments(mappingDmr.getDomainModelReference());
		if (targetSegments.isEmpty()) {
			reportFailure("no target segments could be generated", mappingDmr); //$NON-NLS-1$
			return Collections.emptyList();
		}
		result.addAll(targetSegments);

		return result;
	}

	private void reportFailure(String reason, VDomainModelReference mappingDmr) {
		final StringBuilder sb = new StringBuilder();
		sb.append("Could not generate segments for the mapping dmr because "); //$NON-NLS-1$
		sb.append(reason);
		sb.append(". The mapping DMR was: %s"); //$NON-NLS-1$
		reportService.report(new AbstractReport(String.format(sb.toString(), mappingDmr), IStatus.WARNING));
	}

	// Manually get the EMFFormsSegmentGenerator to avoid a circular dependency
	private EMFFormsSegmentGenerator getEMFFormsSegmentGenerator() {
		if (emfFormsSegmentGenerator == null) {
			final BundleContext bundleContext = FrameworkUtil.getBundle(MappingDmrSegmentGenerator.class)
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
