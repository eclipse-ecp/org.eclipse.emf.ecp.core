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
 * Lucas Koehler - adaption to multi segment
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.mappingprovider.multi;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.common.spi.asserts.Assert;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.core.services.mappingprovider.EMFFormsMappingProvider;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultiDomainModelReferenceSegment;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * An {@link EMFFormsMappingProvider} implementation for {@link VDomainModelReference VDomainModelReferences} which
 * contain a {@link VMultiDomainModelReferenceSegment} as their last segment.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "EMFFormsMappingProviderMulti")
public class EMFFormsMappingProviderMulti implements EMFFormsMappingProvider {

	private EMFFormsDatabindingEMF emfFormsDatabinding;
	private ReportService reportService;

	/**
	 * Sets the {@link EMFFormsDatabindingEMF} service.
	 *
	 * @param emfFormsDatabinding The databinding service
	 */
	@Reference(unbind = "-")
	protected void setEMFFormsDatabinding(EMFFormsDatabindingEMF emfFormsDatabinding) {
		this.emfFormsDatabinding = emfFormsDatabinding;
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
	 * @see org.eclipse.emfforms.spi.core.services.mappingprovider.EMFFormsMappingProvider#getMappingFor(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Set<UniqueSetting> getMappingFor(VDomainModelReference domainModelReference, EObject domainObject) {
		Assert.create(domainModelReference).notNull();
		Assert.create(domainObject).notNull();
		final EList<VDomainModelReferenceSegment> segments = domainModelReference.getSegments();
		if (segments.isEmpty()) {
			throw new IllegalArgumentException("The DMR does not contain any segments."); //$NON-NLS-1$
		}
		if (!VMultiDomainModelReferenceSegment.class.isInstance(segments.get(segments.size() - 1))) {
			throw new IllegalArgumentException(
				"The last segment of the DMR must be a VMultiDomainModelReferenceSegment."); //$NON-NLS-1$
		}

		final VMultiDomainModelReferenceSegment multiSegment = (VMultiDomainModelReferenceSegment) segments
			.get(segments.size() - 1);

		Setting multiSetting;
		try {
			multiSetting = emfFormsDatabinding.getSetting(domainModelReference, domainObject);
		} catch (final DatabindingFailedException ex) {
			reportService.report(new DatabindingFailedReport(ex));
			return Collections.<UniqueSetting> emptySet();
		}

		final Set<UniqueSetting> settings = new LinkedHashSet<UniqueSetting>();
		settings.add(UniqueSetting.createSetting(multiSetting));

		for (final EObject eObject : (List<EObject>) multiSetting.get(true)) {

			for (final VDomainModelReference childDMR : multiSegment.getChildDomainModelReferences()) {
				try {
					final Setting childSetting = emfFormsDatabinding.getSetting(childDMR, eObject);
					settings.add(UniqueSetting.createSetting(childSetting));
				} catch (final DatabindingFailedException ex) {
					reportService.report(new DatabindingFailedReport(ex));
				}
			}
		}
		return settings;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.mappingprovider.EMFFormsMappingProvider#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public double isApplicable(VDomainModelReference domainModelReference, EObject domainObject) {
		if (domainModelReference == null) {
			reportService.report(new AbstractReport("Warning: The given VDomainModelReference was null.")); //$NON-NLS-1$
			return NOT_APPLICABLE;
		}
		if (domainObject == null) {
			reportService.report(new AbstractReport("Warning: The given domain object was null.")); //$NON-NLS-1$
			return NOT_APPLICABLE;
		}
		final EList<VDomainModelReferenceSegment> segments = domainModelReference.getSegments();
		if (segments.isEmpty()) {
			reportService
				.report(new AbstractReport("Warning: The given VDomainModelReference does not contain any segments.")); //$NON-NLS-1$
			return NOT_APPLICABLE;
		}
		final VDomainModelReferenceSegment lastSegment = segments.get(segments.size() - 1);
		if (VMultiDomainModelReferenceSegment.class.isInstance(lastSegment)) {
			return 5d;
		}

		return NOT_APPLICABLE;
	}
}
