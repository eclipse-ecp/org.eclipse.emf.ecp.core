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
package org.eclipse.emfforms.internal.core.services.domainexpander.multi;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.common.spi.asserts.Assert;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDMRSegmentExpander;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDomainExpander;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsExpandingFailedException;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultiDomainModelReferenceSegment;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * {@link EMFFormsDMRSegmentExpander} implementation for {@link VMultiDomainModelReferenceSegment
 * VMultiDomainModelReferenceSegments}.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "EMFFormsDMRSegmentExpanderMulti")
public class EMFFormsDMRSegmentExpanderMulti implements EMFFormsDMRSegmentExpander {

	private ReportService reportService;
	private EMFFormsDomainExpander domainExpander;
	private ServiceReference<EMFFormsDomainExpander> emfFormsDomainExpanderServiceReference;
	private BundleContext bundleContext;

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
	 * Called by the framework when the component gets activated.
	 *
	 * @param bundleContext The {@link BundleContext}
	 */
	@Activate
	protected void activate(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}

	/**
	 * Called by the framework when the component gets deactivated.
	 *
	 * @param bundleContext The {@link BundleContext}
	 */
	@Deactivate
	protected void deactivate(BundleContext bundleContext) {
		if (emfFormsDomainExpanderServiceReference != null) {
			bundleContext.ungetService(emfFormsDomainExpanderServiceReference);
			domainExpander = null;
		}
	}

	/**
	 *
	 * @return The {@link EMFFormsDomainExpander}
	 */
	private EMFFormsDomainExpander getEMFFormsDomainExpander() {
		if (domainExpander == null) {
			emfFormsDomainExpanderServiceReference = bundleContext.getServiceReference(EMFFormsDomainExpander.class);
			if (emfFormsDomainExpanderServiceReference == null) {
				throw new IllegalStateException("No EMFFormsDomainExpander available!"); //$NON-NLS-1$
			}
			domainExpander = bundleContext.getService(emfFormsDomainExpanderServiceReference);
		}
		return domainExpander;
	}

	/**
	 * Helper method for tests.
	 *
	 * @param domainExpander The EMFFormsDomainExpander to use
	 */
	void setEMFFormsDomainExpander(EMFFormsDomainExpander domainExpander) {
		this.domainExpander = domainExpander;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDMRSegmentExpander#prepareDomainObject(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public EObject prepareDomainObject(VDomainModelReferenceSegment segment, EObject domainObject)
		throws EMFFormsExpandingFailedException {
		Assert.create(segment).notNull().ofClass(VMultiDomainModelReferenceSegment.class);
		Assert.create(domainObject).notNull();

		final VMultiDomainModelReferenceSegment multiSegment = (VMultiDomainModelReferenceSegment) segment;
		final EStructuralFeature structuralFeature = domainObject.eClass()
			.getEStructuralFeature(multiSegment.getDomainModelFeature());

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
				String.format("The EReference described by the given multi segment must be a multi reference. " //$NON-NLS-1$
					+ "The segment was %1$s.", segment)); //$NON-NLS-1$
		}

		final EList<EObject> list = (EList<EObject>) domainObject.eGet(reference);
		// Expand every list object for every child dmr of the multi segment
		for (final EObject childRoot : list) {
			for (final VDomainModelReference childDMR : multiSegment.getChildDomainModelReferences()) {
				getEMFFormsDomainExpander().prepareDomainObject(childDMR, childRoot);
			}
		}

		// return null because there is no specific object to return and the multi segment has to be the last segment of
		// a DMR
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDMRSegmentExpander#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment)
	 */
	@Override
	public double isApplicable(VDomainModelReferenceSegment segment) {
		if (segment == null) {
			reportService.report(new AbstractReport("Warning: The given segment was null.")); //$NON-NLS-1$
			return NOT_APPLICABLE;
		}
		if (VMultiDomainModelReferenceSegment.class.isInstance(segment)) {
			return 5d;
		}
		return NOT_APPLICABLE;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDMRSegmentExpander#needsToExpandLastSegment()
	 */
	@Override
	public boolean needsToExpandLastSegment() {
		return true;
	}

}
