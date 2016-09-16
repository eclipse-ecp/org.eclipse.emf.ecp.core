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
package org.eclipse.emfforms.internal.core.services.structuralchange.index;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.common.spi.asserts.Assert;
import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsSegmentResolver;
import org.eclipse.emfforms.spi.core.services.structuralchange.StructuralChangeSegmentTester;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * A {@link StructuralChangeSegmentTester} implementation to check {@link VIndexDomainModelReferenceSegment
 * VIndexDomainModelReferenceSegments} with corresponding domain objects for structural changes.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "StructuralChangeSegmentTesterIndex")
public class StructuralChangeSegmentTesterIndex implements StructuralChangeSegmentTester {
	private EMFFormsSegmentResolver segmentResolver;
	private ReportService reportService;

	/**
	 * Sets the {@link EMFFormsSegmentResolver}.
	 *
	 * @param segmentResolver The {@link EMFFormsSegmentResolver}
	 */
	@Reference(unbind = "-")
	private void setEMFFormsSegmentResolver(EMFFormsSegmentResolver segmentResolver) {
		this.segmentResolver = segmentResolver;
	}

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
	 * @see org.eclipse.emfforms.spi.core.services.structuralchange.StructuralChangeSegmentTester#isStructureChanged(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment,
	 *      org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification)
	 */
	@Override
	public boolean isStructureChanged(VDomainModelReferenceSegment segment, EObject domainObject,
		ModelChangeNotification notification) {
		Assert.create(segment).notNull();
		Assert.create(domainObject).notNull();
		Assert.create(segment).ofClass(VIndexDomainModelReferenceSegment.class);

		final VIndexDomainModelReferenceSegment indexSegment = (VIndexDomainModelReferenceSegment) segment;

		Setting setting;
		try {
			setting = segmentResolver.resolveSegment(indexSegment, domainObject);
		} catch (final DatabindingFailedException ex) {
			reportService.report(new DatabindingFailedReport(ex));
			return false;
		}

		/*
		 * Check whether the notifying EObject and the EReference match the notification. If this is the case, check if
		 * the relevant element was changed.
		 * If the EStructuralFeature of the resolved Setting is an EAttribute, its change is irrelevant.
		 */
		if (EReference.class.isInstance(setting.getEStructuralFeature())) {
			final EReference eReference = (EReference) setting.getEStructuralFeature();
			if (eReference.equals(notification.getStructuralFeature())
				&& notification.getNotifier() == setting.getEObject()) {

				final int position = notification.getRawNotification().getPosition();

				if (position == indexSegment.getIndex()) {
					return true;
				}

				/*
				 * For ADD_MANY, REMOVE_MANY or MOVE events the position of the notification will not be the segment's
				 * index even if the element at the index changed. Therefore, we have to be overly careful and always
				 * indicate a change when one of the events was triggered.
				 */
				final int event = notification.getRawNotification().getEventType();
				if (event == Notification.ADD_MANY || event == Notification.REMOVE_MANY || event == Notification.MOVE) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.structuralchange.StructuralChangeSegmentTester#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment)
	 */
	@Override
	public double isApplicable(VDomainModelReferenceSegment segment) {
		Assert.create(segment).notNull();
		if (VIndexDomainModelReferenceSegment.class.isInstance(segment)) {
			return 5d;
		}
		return NOT_APPLICABLE;
	}

}
