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
package org.eclipse.emfforms.internal.core.services.structuralchange.mapping;

import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.common.spi.asserts.Assert;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsSegmentResolver;
import org.eclipse.emfforms.spi.core.services.structuralchange.StructuralChangeSegmentTester;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * A {@link StructuralChangeSegmentTester} for {@link VMappingDomainModelReferenceSegment
 * VMappingDomainModelReferenceSegments}.
 *
 * Changes to a map entry itself (such as setting a new value for an existing key) are not registered: see
 * https://www.eclipse.org/forums/index.php/t/129124/
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "StructuralChangeSegmentTesterMapping")
public class StructuralChangeSegmentTesterMapping implements StructuralChangeSegmentTester {
	private EMFFormsSegmentResolver segmentResolver;
	private ReportService reportService;

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
	 * @see org.eclipse.emfforms.spi.core.services.structuralchange.StructuralChangeSegmentTester#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment)
	 */
	@Override
	public double isApplicable(VDomainModelReferenceSegment segment) {
		if (segment == null) {
			reportService.report(new AbstractReport("Warning: The given segment was null.")); //$NON-NLS-1$
			return NOT_APPLICABLE;
		}
		if (VMappingDomainModelReferenceSegment.class.isInstance(segment)) {
			return 5d;
		}
		return NOT_APPLICABLE;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.structuralchange.StructuralChangeSegmentTester#isStructureChanged(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment,
	 *      org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean isStructureChanged(VDomainModelReferenceSegment segment, EObject domainObject,
		ModelChangeNotification notification) {
		Assert.create(segment).notNull().ofClass(VMappingDomainModelReferenceSegment.class);
		Assert.create(domainObject).notNull();

		if (notification.getRawNotification().isTouch()) {
			return false;
		}

		final VMappingDomainModelReferenceSegment mappingSegment = (VMappingDomainModelReferenceSegment) segment;

		Setting setting;
		try {
			setting = segmentResolver.resolveSegment(mappingSegment, domainObject);
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

				final Notification rawNotification = notification.getRawNotification();
				final int event = rawNotification.getEventType();
				/*
				 * For ADD_MANY or REMOVE_MANY events we cannot find out whether the entry for our relevant key has
				 * changed. Therefore, we are overly careful and indicate a change.
				 */
				if (event == Notification.ADD_MANY || event == Notification.REMOVE_MANY) {
					return true;
				}
				// It doesn't matter if an entry is moved in the list representation of the map.
				if (event == Notification.MOVE) {
					return false;
				}

				// If the newly added or deleted map entry has the relevant EClass as its key, a structural change
				// occurred
				Map.Entry<EClass, Object> entry = null;
				if (rawNotification.getOldValue() != null) {
					entry = (Entry<EClass, Object>) rawNotification.getOldValue();
				} else if (notification.getRawNotification().getNewValue() != null) {
					entry = (Entry<EClass, Object>) rawNotification.getNewValue();
				}
				if (entry != null && mappingSegment.getMappedClass().equals(entry.getKey())) {
					return true;
				}
			}
		}

		return false;
	}

}
