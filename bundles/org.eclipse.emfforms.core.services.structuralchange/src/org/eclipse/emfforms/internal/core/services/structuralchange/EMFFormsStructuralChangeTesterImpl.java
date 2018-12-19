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
package org.eclipse.emfforms.internal.core.services.structuralchange;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emfforms.common.RankingHelper;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsSegmentResolver;
import org.eclipse.emfforms.spi.core.services.structuralchange.EMFFormsStructuralChangeTester;
import org.eclipse.emfforms.spi.core.services.structuralchange.StructuralChangeSegmentTester;
import org.eclipse.emfforms.spi.core.services.structuralchange.StructuralChangeTesterInternal;
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
	private final Set<StructuralChangeTesterInternal> dmrChangeTesters = new LinkedHashSet<StructuralChangeTesterInternal>();
	private final Set<StructuralChangeSegmentTester> segmentChangeTesters = new LinkedHashSet<>();
	private EMFFormsSegmentResolver segmentResolver;

	private static final RankingHelper<StructuralChangeTesterInternal> DMR_RANKING_HELPER = //
		new RankingHelper<StructuralChangeTesterInternal>(
			StructuralChangeTesterInternal.class,
			StructuralChangeTesterInternal.NOT_APPLICABLE,
			StructuralChangeTesterInternal.NOT_APPLICABLE);

	private static final RankingHelper<StructuralChangeSegmentTester> SEGMENTS_RANKING_HELPER = //
		new RankingHelper<>(
			StructuralChangeSegmentTester.class, StructuralChangeSegmentTester.NOT_APPLICABLE,
			StructuralChangeSegmentTester.NOT_APPLICABLE);

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
	 * Called by the framework to add a {@link StructuralChangeTesterInternal}.
	 *
	 * @param structuralChangeTester The {@link StructuralChangeTesterInternal} to add
	 */
	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	protected void addStructuralChangeTesterInternal(StructuralChangeTesterInternal structuralChangeTester) {
		dmrChangeTesters.add(structuralChangeTester);
	}

	/**
	 * Called by the framework to remove a {@link StructuralChangeTesterInternal}.
	 *
	 * @param structuralChangeTester The {@link StructuralChangeTesterInternal} to remove
	 */
	protected void removeStructuralChangeTesterInternal(StructuralChangeTesterInternal structuralChangeTester) {
		dmrChangeTesters.remove(structuralChangeTester);
	}

	/**
	 * Called by the framework to add a {@link StructuralChangeSegmentTester}.
	 *
	 * @param segmentChangeTester The {@link StructuralChangeSegmentTester} to add
	 */
	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	protected void addStructuralChangeSegmentTester(StructuralChangeSegmentTester segmentChangeTester) {
		segmentChangeTesters.add(segmentChangeTester);
	}

	/**
	 * Called by the framework to remove a {@link StructuralChangeSegmentTester}.
	 *
	 * @param segmentChangeTester The {@link StructuralChangeSegmentTester} to remove
	 */
	protected void removeStructuralChangeSegmentTester(StructuralChangeSegmentTester segmentChangeTester) {
		segmentChangeTesters.remove(segmentChangeTester);
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

	@Override
	public boolean isStructureChanged(final VDomainModelReference reference, final EObject domainRootObject,
		ModelChangeNotification notification) {

		// Changed EAttributes or only touched EReferences do not constitute structural changes
		if (EAttribute.class.isInstance(notification.getStructuralFeature())) {
			return false;
		}
		if (notification.getRawNotification().isTouch()) {
			return false;
		}

		final EList<VDomainModelReferenceSegment> segments = reference.getSegments();
		if (segments.isEmpty()) {
			// fall back to DMR based structural change testing
			return testOnDmr(reference, domainRootObject, notification);
		}

		return testOnSegments(reference, domainRootObject, notification);
	}

	/**
	 * Test for structural changes based on the {@link VDomainModelReference VDomainModelReference's} segments.
	 *
	 * @param reference The {@link VDomainModelReference} whose segments are tested
	 * @param domainRootObject The root domain object to resolve and test the segments against
	 * @param notification The {@link ModelChangeNotification} triggering the structural change test
	 * @return <code>true</code> if the structure has changed, <code>false</code> otherwise
	 */
	private boolean testOnSegments(final VDomainModelReference reference, final EObject domainRootObject,
		ModelChangeNotification notification) {
		final EList<VDomainModelReferenceSegment> segments = reference.getSegments();
		boolean relevantChange = false;
		EObject currentDomainObject = domainRootObject;
		for (int i = 0; i < segments.size(); i++) {
			final VDomainModelReferenceSegment segment = segments.get(i);
			final StructuralChangeSegmentTester segmentTester = SEGMENTS_RANKING_HELPER
				.getHighestRankingElement(segmentChangeTesters, tester -> tester.isApplicable(segment));
			if (segmentTester == null) {
				reportService.report(new AbstractReport(String.format(
					"Structural changes of the DMR: %1$s could not be analyzed because no suitable StructuralChangeSegmentTester was available for segment %2$s.", //$NON-NLS-1$
					reference, segment), IStatus.WARNING));
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
	 * Test for structural changes based on the whole {@link VDomainModelReference}.
	 *
	 * @param reference The {@link VDomainModelReference} to test
	 * @param domainRootObject The root domain object to resolve and test the DMR against
	 * @param notification The {@link ModelChangeNotification} triggering the structural change test
	 * @return <code>true</code> if the structure has changed, <code>false</code> otherwise
	 */
	private boolean testOnDmr(final VDomainModelReference reference, final EObject domainRootObject,
		ModelChangeNotification notification) {
		final StructuralChangeTesterInternal bestTester = DMR_RANKING_HELPER.getHighestRankingElement(
			dmrChangeTesters, tester -> tester.isApplicable(reference));

		if (bestTester == null) {
			reportService.report(new AbstractReport("Structural changes of the DMR: " + reference //$NON-NLS-1$
				+ "could not be analyzed because no suitable StructuralChangeTesterInternal was available.", //$NON-NLS-1$
				IStatus.WARNING));
			return false;
		}
		return bestTester.isStructureChanged(reference, domainRootObject, notification);
	}

}
