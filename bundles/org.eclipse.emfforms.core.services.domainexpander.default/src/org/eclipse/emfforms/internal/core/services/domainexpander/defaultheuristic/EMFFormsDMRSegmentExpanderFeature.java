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
package org.eclipse.emfforms.internal.core.services.domainexpander.defaultheuristic;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.common.spi.asserts.Assert;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDMRSegmentExpander;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsExpandingFailedException;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * A {@link EMFFormsDMRSegmentExpander} for {@link VFeatureDomainModelReferenceSegment
 * VFeatureDomainModelReferenceSegments}.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "EMFFormsDMRSegmentExpanderFeature")
public class EMFFormsDMRSegmentExpanderFeature implements EMFFormsDMRSegmentExpander {

	private ReportService reportService;

	/**
	 * Called by the framework to set the {@link ReportService}.
	 *
	 * @param reportService The {@link ReportService}
	 */
	@Reference
	protected void setReportService(ReportService reportService) {
		this.reportService = reportService;

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDMRSegmentExpander#prepareDomainObject(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public EObject prepareDomainObject(VDomainModelReferenceSegment segment, EObject domainObject)
		throws EMFFormsExpandingFailedException {
		Assert.create(segment).notNull();
		Assert.create(domainObject).notNull();
		Assert.create(segment).ofClass(VFeatureDomainModelReferenceSegment.class);

		final VFeatureDomainModelReferenceSegment featureSegment = (VFeatureDomainModelReferenceSegment) segment;
		final EStructuralFeature structuralFeature = domainObject.eClass()
			.getEStructuralFeature(featureSegment.getDomainModelFeature());

		if (structuralFeature == null) {
			throw new EMFFormsExpandingFailedException(
				String.format("The given domain object does not contain the segment's feature. " //$NON-NLS-1$
					+ "The segment was %1$s. The domain object was %2$s.", segment, domainObject)); //$NON-NLS-1$
		}
		if (!EReference.class.isInstance(structuralFeature)) {
			throw new EMFFormsExpandingFailedException(
				String.format("The feature described by the given segment must be an EReference. " //$NON-NLS-1$
					+ "The segment was %1$s.", segment)); //$NON-NLS-1$
		}

		final EReference reference = (EReference) structuralFeature;

		/*
		 * If the reference's target already exists, we do not need to do anything.
		 * Otherwise we have to create a new instance.
		 */
		EObject child = (EObject) domainObject.eGet(reference);
		if (child == null) {
			if (!reference.getEReferenceType().isAbstract() && !reference.getEReferenceType().isInterface()) {
				child = EcoreUtil.create(reference.getEReferenceType());
				domainObject.eSet(reference, child);
			} else {
				throw new EMFFormsExpandingFailedException(String.format(
					"The reference type of the segment's feature is either abstract or an interface. " //$NON-NLS-1$
						+ "Therefore, no instance can be created. The segment was %1$s.", //$NON-NLS-1$
					segment));
			}
		}

		return child;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDMRSegmentExpander#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment)
	 */
	@Override
	public double isApplicable(VDomainModelReferenceSegment segment) {
		if (segment == null) {
			reportService.report(new AbstractReport("Warning: The given domain model reference segment was null.")); //$NON-NLS-1$
			return NOT_APPLICABLE;
		}
		if (VFeatureDomainModelReferenceSegment.class.isInstance(segment)) {
			return 1d;
		}
		return NOT_APPLICABLE;
	}

}
