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
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.common.spi.asserts.Assert;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsSegmentResolver;
import org.eclipse.emfforms.spi.core.services.structuralchange.StructuralChangeSegmentTester;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * A {@link StructuralChangeSegmentTester} implementation for {@link VFeatureDomainModelReferenceSegment
 * VFeatureDomainModelReferenceSegments}.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "StructuralChangeSegmentTesterFeature")
public class StructuralChangeSegmentTesterFeature implements StructuralChangeSegmentTester {
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
		Assert.create(segment).ofClass(VFeatureDomainModelReferenceSegment.class);

		/*
		 * Check whether the notifying EObject and the EReference match the notification.
		 * If the EStructuralFeature of the resolved Setting is an EAttribute, its change is irrelevant.
		 */
		Setting setting;
		try {
			setting = segmentResolver.resolveSegment(segment, domainObject);
		} catch (final DatabindingFailedException ex) {
			reportService.report(new DatabindingFailedReport(ex));
			return false;
		}
		if (EReference.class.isInstance(setting.getEStructuralFeature())) {
			final EReference eReference = (EReference) setting.getEStructuralFeature();
			return eReference.equals(notification.getStructuralFeature())
				&& notification.getNotifier() == setting.getEObject();
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
		if (VFeatureDomainModelReferenceSegment.class.isInstance(segment)) {
			return 1d;
		}
		return NOT_APPLICABLE;
	}

}
