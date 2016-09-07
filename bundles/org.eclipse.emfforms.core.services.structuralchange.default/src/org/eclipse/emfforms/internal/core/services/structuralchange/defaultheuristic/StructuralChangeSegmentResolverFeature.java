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
package org.eclipse.emfforms.internal.core.services.structuralchange.defaultheuristic;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecp.common.spi.asserts.Assert;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.structuralchange.StructuralChangeSegmentResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * A {@link StructuralChangeSegmentResolver} implementation for {@link VFeatureDomainModelReferenceSegment
 * VFeatureDomainModelReferenceSegments}.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "StructuralChangeSegmentResolverFeature")
public class StructuralChangeSegmentResolverFeature implements StructuralChangeSegmentResolver {
	private ReportService reportService;

	/**
	 * Sets the {@link ReportService}.
	 *
	 * @param reportService The {@link ReportService}
	 */
	@Reference(unbind = "-")
	protected void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.structuralchange.StructuralChangeSegmentResolver#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment)
	 */
	@Override
	public double isApplicable(VDomainModelReferenceSegment segment) {
		Assert.create(segment).notNull();
		if (VFeatureDomainModelReferenceSegment.class.isInstance(segment)) {
			return 1d;
		}
		return NOT_APPLICABLE;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.structuralchange.StructuralChangeSegmentResolver#resolveSegment(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public Setting resolveSegment(VDomainModelReferenceSegment segment, EObject domainObject) {
		Assert.create(segment).notNull();
		Assert.create(domainObject).notNull();
		Assert.create(segment).ofClass(VFeatureDomainModelReferenceSegment.class);

		final VFeatureDomainModelReferenceSegment featureSegment = (VFeatureDomainModelReferenceSegment) segment;
		final EStructuralFeature structuralFeature = domainObject.eClass()
			.getEStructuralFeature(featureSegment.getDomainModelFeature());

		if (structuralFeature == null) {
			reportService.report(new AbstractReport(String.format(
				"Warning: The given segment could not be resolved for the given domain object. " //$NON-NLS-1$
					+ "The segment was %1$s. The domain object was %2$s.", //$NON-NLS-1$
				segment, domainObject)));
			return null;
		}

		return ((InternalEObject) domainObject).eSetting(structuralFeature);
	}

}
