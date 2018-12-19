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
package org.eclipse.emfforms.internal.core.services.segments.multi;

import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecp.common.spi.asserts.Assert;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emf.ecp.view.spi.table.model.VTablePackage;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.segments.DmrSegmentGenerator;
import org.eclipse.emfforms.spi.core.services.segments.EMFFormsSegmentGenerator;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultiDomainModelReferenceSegment;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultisegmentFactory;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * {@link DmrSegmentGenerator} for {@link VTableDomainModelReference VTableDomainModelReferences}.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "TableDmrSegmentGenerator")
public class TableDmrSegmentGenerator implements DmrSegmentGenerator {

	private EMFFormsSegmentGenerator emfFormsSegmentGenerator;
	private ReportService reportService;

	@Override
	public double isApplicable(VDomainModelReference reference) {
		Assert.create(reference).notNull();
		if (reference instanceof VTableDomainModelReference) {
			return 5d;
		}
		return NOT_APPLICABLE;
	}

	private EMFFormsSegmentGenerator getEMFFormsSegmentGenerator() {
		if (emfFormsSegmentGenerator == null) {
			final BundleContext bundleContext = FrameworkUtil.getBundle(TableDmrSegmentGenerator.class)
				.getBundleContext();
			final ServiceReference<EMFFormsSegmentGenerator> serviceReference = bundleContext
				.getServiceReference(EMFFormsSegmentGenerator.class);
			emfFormsSegmentGenerator = bundleContext.getService(serviceReference);
		}
		return emfFormsSegmentGenerator;
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

	/**
	 * Sets the {@link EMFFormsSegmentGenerator}. This method is package-visible because it is only intended for
	 * testing.
	 *
	 * @param generator The {@link EMFFormsSegmentGenerator} to use
	 */
	void setEMFFormsSegmentGenerator(EMFFormsSegmentGenerator generator) {
		emfFormsSegmentGenerator = generator;
	}

	@Override
	public List<VDomainModelReferenceSegment> generateSegments(VDomainModelReference reference) {
		Assert.create(reference).notNull();
		if (reference.eClass() != VTablePackage.Literals.TABLE_DOMAIN_MODEL_REFERENCE) {
			throw new IllegalArgumentException(
				String.format("The given DMR was no table domain model reference. The DMR was: %s", reference)); //$NON-NLS-1$
		}

		final List<VDomainModelReferenceSegment> result = new LinkedList<>();
		final VTableDomainModelReference tableDmr = (VTableDomainModelReference) reference;
		final List<VDomainModelReferenceSegment> subDmrSegments;

		// Generate segments for the table dmr's inner dmr resp. the feature dmr's feature path
		if (tableDmr.getDomainModelReference() != null) {
			subDmrSegments = getEMFFormsSegmentGenerator().generateSegments(tableDmr.getDomainModelReference());
		} else {
			final VFeaturePathDomainModelReference featureDmr = VViewFactory.eINSTANCE
				.createFeaturePathDomainModelReference();
			featureDmr.setDomainModelEFeature(tableDmr.getDomainModelEFeature());
			featureDmr.getDomainModelEReferencePath().addAll(tableDmr.getDomainModelEReferencePath());
			subDmrSegments = getEMFFormsSegmentGenerator().generateSegments(featureDmr);
		}

		if (subDmrSegments.isEmpty()) {
			return result;
		}

		final VDomainModelReferenceSegment lastSegment = subDmrSegments.get(subDmrSegments.size() - 1);
		if (!(lastSegment instanceof VFeatureDomainModelReferenceSegment)) {
			reportService.report(new AbstractReport(
				MessageFormat.format(
					"The last path segment of the Table DMR {0} was no feature segment. Consequently, it cannot be transformed to a multi segment and the segment generation for the DMR failed.", //$NON-NLS-1$
					tableDmr),
				IStatus.WARNING));
			return result;
		}

		// Convert the last segment to a multi segment and add all column dmrs as child dmrs.
		final VMultiDomainModelReferenceSegment multiSegment = VMultisegmentFactory.eINSTANCE
			.createMultiDomainModelReferenceSegment();
		multiSegment
			.setDomainModelFeature(VFeatureDomainModelReferenceSegment.class.cast(lastSegment).getDomainModelFeature());
		multiSegment.getChildDomainModelReferences().addAll(tableDmr.getColumnDomainModelReferences());

		// Build the result by using the generated segments for the path and the multi segment as the final segment.
		for (int i = 0; i < subDmrSegments.size() - 1; i++) {
			result.add(subDmrSegments.get(i));
		}
		result.add(multiSegment);

		return result;
	}

}
