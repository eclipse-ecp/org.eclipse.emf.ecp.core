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
 * Lucas Koehler - adjust to DMR segments
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.structuralchange;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.common.spi.asserts.Assert;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.structuralchange.EMFFormsStructuralChangeTester;
import org.eclipse.emfforms.spi.core.services.structuralchange.StructuralChangeSegmentResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * Implementation of {@link EMFFormsStructuralChangeTester}.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "EMFFormsStructuralChangeTesterImpl")
public class EMFFormsStructuralChangeTesterImpl implements EMFFormsStructuralChangeTester {

	private ReportService reportService;
	private final Set<StructuralChangeSegmentResolver> structuralChangeResolvers = new LinkedHashSet<StructuralChangeSegmentResolver>();

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
	 * Called by the framework to add a {@link StructuralChangeSegmentResolver}.
	 *
	 * @param structuralChangeResolver The {@link StructuralChangeSegmentResolver} to add
	 */
	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	protected void addStructuralChangeSegmentResolver(StructuralChangeSegmentResolver structuralChangeResolver) {
		structuralChangeResolvers.add(structuralChangeResolver);
	}

	/**
	 * Called by the framework to remove a {@link StructuralChangeSegmentResolver}.
	 *
	 * @param structuralChangeResolver The {@link StructuralChangeSegmentResolver} to remove
	 */
	protected void removeStructuralChangeSegmentResolver(StructuralChangeSegmentResolver structuralChangeResolver) {
		structuralChangeResolvers.remove(structuralChangeResolver);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.structuralchange.EMFFormsStructuralChangeTester#isStructureChanged(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference,
	 *      org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification)
	 */
	@Override
	public boolean isStructureChanged(VDomainModelReference reference, EObject domainRootObject,
		ModelChangeNotification notification) {
		Assert.create(reference).notNull();
		Assert.create(domainRootObject).notNull();
		Assert.create(notification).notNull();

		// Changed EAttributes or only touched EReferences do not constitute structural changes
		if (EAttribute.class.isInstance(notification.getStructuralFeature())) {
			return false;
		}
		if (notification.getRawNotification().isTouch()) {
			return false;
		}

		final EList<VDomainModelReferenceSegment> segments = reference.getSegments();

		boolean relevantChange = false;

		EObject currentDomainObject = domainRootObject;
		for (final VDomainModelReferenceSegment segment : segments) {
			final StructuralChangeSegmentResolver segmentResolver = getBestSegmentResolver(segment);
			if (segmentResolver == null) {
				reportService.report(new AbstractReport("Warning: Structural changes of the DMR: " + reference //$NON-NLS-1$
					+ "could not be analyzed because no suitable StructuralChangeSegmentResolver was available.")); //$NON-NLS-1$
				return false;
			}

			/*
			 * Check whether the notifying EObject and the EReference match the notification.
			 * If the EStructuralFeature of the resolved Setting is an EAttribute, we cannot resolve further and its
			 * change is irrelevant.
			 */
			final Setting setting = segmentResolver.resolveSegment(segment, currentDomainObject);
			if (setting == null) {
				break;
			}

			if (EReference.class.isInstance(setting.getEStructuralFeature())) {
				final EReference eReference = (EReference) setting.getEStructuralFeature();
				relevantChange |= eReference.equals(notification.getStructuralFeature())
					&& notification.getNotifier() == setting.getEObject();
			} else {
				break;
			}

			if (relevantChange) {
				return true;
			}

			// The value of the Setting is an EObject because its EStructuralFeature is an EReference.
			currentDomainObject = (EObject) setting.get(true);
		}

		return relevantChange;

	}

	/**
	 * @param segment
	 * @return The most suitable {@link StructuralChangeSegmentResolver} for the given
	 *         {@link VDomainModelReferenceSegment segment}, <code>null</code> if there is none
	 */
	private StructuralChangeSegmentResolver getBestSegmentResolver(VDomainModelReferenceSegment segment) {
		double bestPriority = StructuralChangeSegmentResolver.NOT_APPLICABLE;
		StructuralChangeSegmentResolver bestTester = null;

		for (final StructuralChangeSegmentResolver resolver : structuralChangeResolvers) {
			final double priority = resolver.isApplicable(segment);
			if (priority > bestPriority) {
				bestPriority = priority;
				bestTester = resolver;
			}
		}
		return bestTester;
	}

}
