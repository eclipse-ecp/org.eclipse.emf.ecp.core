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
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsSegmentResolver;
import org.eclipse.emfforms.spi.core.services.structuralchange.EMFFormsStructuralChangeTester;
import org.eclipse.emfforms.spi.core.services.structuralchange.StructuralChangeSegmentTester;
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
	private final Set<StructuralChangeSegmentTester> structuralChangeTesters = new LinkedHashSet<StructuralChangeSegmentTester>();
	private EMFFormsSegmentResolver segmentResolver;

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
	 * Called by the framework to add a {@link StructuralChangeSegmentTester}.
	 *
	 * @param structuralChangeTester The {@link StructuralChangeSegmentTester} to add
	 */
	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	protected void addStructuralChangeSegmentTester(StructuralChangeSegmentTester structuralChangeTester) {
		structuralChangeTesters.add(structuralChangeTester);
	}

	/**
	 * Sets the {@link EMFFormsSegmentResolver}.
	 *
	 * @param segmentResolver The {@link EMFFormsSegmentResolver}
	 */
	@Reference(unbind = "-")
	protected void setEMFFormsSegmentResolver(EMFFormsSegmentResolver segmentResolver) {
		this.segmentResolver = segmentResolver;
	}

	/**
	 * Called by the framework to remove a {@link StructuralChangeSegmentTester}.
	 *
	 * @param structuralChangeTester The {@link StructuralChangeSegmentTester} to remove
	 */
	protected void removeStructuralChangeSegmentTester(StructuralChangeSegmentTester structuralChangeTester) {
		structuralChangeTesters.remove(structuralChangeTester);
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
		for (int i = 0; i < segments.size(); i++) {
			final VDomainModelReferenceSegment segment = segments.get(i);
			final StructuralChangeSegmentTester segmentTester = getBestSegmentTester(segment);
			if (segmentTester == null) {
				reportService.report(new AbstractReport(String.format(
					"Warning: Structural changes of the DMR: %1$s could not be analyzed because no suitable StructuralChangeSegmentTester was available for segment %2$s.", //$NON-NLS-1$
					reference, segment)));
				return false;
			}

			final Setting setting;
			try {
				setting = segmentResolver.resolveSegment(segment, currentDomainObject);
			} catch (final DatabindingFailedException ex) {
				reportService.report(new AbstractReport(ex,
					"Could not finish structural change calculation.")); //$NON-NLS-1$
				break;
			}

			relevantChange |= segmentTester.isStructureChanged(segment, currentDomainObject, notification);

			if (relevantChange || !EReference.class.isInstance(setting.getEStructuralFeature())) {
				return relevantChange;
			}

			// Do not resolve the last setting and convert its value because the last value is not needed and might be
			// an EList because the last segment may represent a multi reference
			if (i == segments.size() - 1) {
				break;
			}
			// The value of the Setting is an EObject because its EStructuralFeature is an EReference.
			currentDomainObject = (EObject) setting.get(true);
		}

		return relevantChange;

	}

	/**
	 * @param segment The {@link VDomainModelReferenceSegment segment} for which a {@link StructuralChangeSegmentTester
	 *            tester} is needed
	 * @return The most suitable {@link StructuralChangeSegmentTester} for the given
	 *         {@link VDomainModelReferenceSegment segment}, <code>null</code> if there is none
	 */
	private StructuralChangeSegmentTester getBestSegmentTester(VDomainModelReferenceSegment segment) {
		double bestPriority = StructuralChangeSegmentTester.NOT_APPLICABLE;
		StructuralChangeSegmentTester bestTester = null;

		for (final StructuralChangeSegmentTester tester : structuralChangeTesters) {
			final double priority = tester.isApplicable(segment);
			if (priority > bestPriority) {
				bestPriority = priority;
				bestTester = tester;
			}
		}
		return bestTester;
	}

}
