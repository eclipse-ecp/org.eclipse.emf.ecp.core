/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.mappingprovider.table;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.common.spi.asserts.Assert;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.core.services.mappingprovider.EMFFormsMappingProvider;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * An {@link EMFFormsMappingProvider} implementation for {@link VTableDomainModelReference}.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "EMFFormsMappingProviderTable")
public class EMFFormsMappingProviderTable implements EMFFormsMappingProvider {

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

		final VTableDomainModelReference tableDMR = (VTableDomainModelReference) domainModelReference;
		Setting tableSetting;
		try {
			tableSetting = emfFormsDatabinding.getSetting(tableDMR, domainObject);
		} catch (final DatabindingFailedException ex) {
			reportService.report(new DatabindingFailedReport(ex));
			return Collections.<UniqueSetting> emptySet();
		}

		final Set<UniqueSetting> settingsMap = new LinkedHashSet<UniqueSetting>();
		settingsMap.add(UniqueSetting.createSetting(tableSetting));

		for (final EObject eObject : (List<EObject>) tableSetting.get(true)) {

			for (final VDomainModelReference columnDMR : tableDMR.getColumnDomainModelReferences()) {
				try {
					final Setting columnSetting = emfFormsDatabinding.getSetting(columnDMR, eObject);
					settingsMap.add(UniqueSetting.createSetting(columnSetting));
				} catch (final DatabindingFailedException ex) {
					reportService.report(new DatabindingFailedReport(ex));
				}
			}
		}
		return settingsMap;
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

		if (VTableDomainModelReference.class.isInstance(domainModelReference)) {
			return 5d;
		}

		return NOT_APPLICABLE;
	}
}
