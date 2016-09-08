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
package org.eclipse.emfforms.internal.core.services.domainexpander.index;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.common.spi.asserts.Assert;
import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDMRSegmentExpander;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsExpandingFailedException;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * A {@link EMFFormsDMRSegmentExpander} for {@link VIndexDomainModelReferenceSegment
 * VIndexDomainModelReferenceSegments}.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "EMFFormsDMRSegmentExpanderIndex")
public class EMFFormsDMRSegmentExpanderIndex implements EMFFormsDMRSegmentExpander {
	private ReportService reportService;

	/**
	 * Called by the framework to set the {@link ReportService}.
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
	 * @see org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDMRSegmentExpander#prepareDomainObject(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public EObject prepareDomainObject(VDomainModelReferenceSegment segment, EObject domainObject)
		throws EMFFormsExpandingFailedException {
		Assert.create(segment).notNull();
		Assert.create(domainObject).notNull();
		Assert.create(segment).ofClass(VIndexDomainModelReferenceSegment.class);

		final VIndexDomainModelReferenceSegment indexSegment = (VIndexDomainModelReferenceSegment) segment;
		final EStructuralFeature structuralFeature = domainObject.eClass()
			.getEStructuralFeature(indexSegment.getDomainModelFeature());

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

		if (!reference.isMany()) {
			throw new EMFFormsExpandingFailedException(
				String.format("The EReference described by the given index segment must be a multi reference. " //$NON-NLS-1$
					+ "The segment was %1$s.", segment)); //$NON-NLS-1$
		}

		final int index = indexSegment.getIndex();
		final EList<EObject> list = (EList<EObject>) domainObject.eGet(reference);
		if (list.size() <= index) {
			checkReferenceInstantiable(segment, reference);

			// When index == list.size(), one element needs to be created as the index starts at 0
			final int iterations = index - list.size() + 1;
			for (int i = 0; i < iterations; i++) {
				final EObject newElement = EcoreUtil.create(reference.getEReferenceType());
				list.add(newElement);
			}
		} else if (list.get(index) == null) {
			// The list is big enough but the element at the index must not be null
			checkReferenceInstantiable(segment, reference);

			final EObject newElement = EcoreUtil.create(reference.getEReferenceType());
			list.set(index, newElement);
		}
		return list.get(indexSegment.getIndex());
	}

	/**
	 * Checks that the type of the given {@link EReference} is instantiable.
	 *
	 * @param segment The source segment (only for the exception message)
	 * @param reference The {@link EReference} to check
	 * @throws EMFFormsExpandingFailedException If the given {@link EReference} is not instantiable
	 */
	private void checkReferenceInstantiable(VDomainModelReferenceSegment segment, final EReference reference)
		throws EMFFormsExpandingFailedException {
		if (reference.getEReferenceType().isAbstract() || reference.getEReferenceType().isInterface()) {
			throw new EMFFormsExpandingFailedException(String.format(
				"The reference type of the segment's feature is either abstract or an interface. " //$NON-NLS-1$
					+ "Therefore, no instance can be created. The segment was %1$s.", //$NON-NLS-1$
				segment));
		}
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
		if (VIndexDomainModelReferenceSegment.class.isInstance(segment)) {
			return 5d;
		}
		return NOT_APPLICABLE;
	}

}
