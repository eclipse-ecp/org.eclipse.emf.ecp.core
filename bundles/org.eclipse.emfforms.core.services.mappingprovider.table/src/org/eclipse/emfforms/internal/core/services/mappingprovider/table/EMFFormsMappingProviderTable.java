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
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
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
 * An {@link EMFFormsMappingProvider} implementation for {@link VTableControl VTableControls}.
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
	@Reference
	protected void setEMFFormsDatabinding(EMFFormsDatabindingEMF emfFormsDatabinding) {
		this.emfFormsDatabinding = emfFormsDatabinding;
	}

	/**
	 * Sets the {@link ReportService}.
	 *
	 * @param reportService The {@link ReportService}
	 */
	@Reference
	protected void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.mappingprovider.EMFFormsMappingProvider#getMappingFor(org.eclipse.emf.ecp.view.spi.model.VControl,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Set<UniqueSetting> getMappingFor(VControl vControl, EObject domainObject) {
		Assert.create(vControl).notNull();
		Assert.create(domainObject).notNull();

		final VTableControl tableControl = (VTableControl) vControl;
		final VTableDomainModelReference tableDMR = (VTableDomainModelReference) tableControl.getDomainModelReference();
		Setting tableSetting;
		try {
			tableSetting = emfFormsDatabinding.getSetting(tableControl.getDomainModelReference(), domainObject);
		} catch (final DatabindingFailedException ex) {
			reportService.report(new DatabindingFailedReport(ex));
			return Collections.<UniqueSetting> emptySet();
		}

		final Set<UniqueSetting> settingsMap = new LinkedHashSet<UniqueSetting>();
		settingsMap.add(UniqueSetting.createSetting(tableSetting));

		for (final EObject eObject : (List<EObject>) tableSetting.get(true)) {

			for (final VDomainModelReference domainModelReference : tableDMR.getColumnDomainModelReferences()) {
				try {
					final Setting columnSetting = emfFormsDatabinding.getSetting(domainModelReference, eObject);
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
	 * @see org.eclipse.emfforms.spi.core.services.mappingprovider.EMFFormsMappingProvider#isApplicable(org.eclipse.emf.ecp.view.spi.model.VControl,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public double isApplicable(VControl vControl, EObject domainObject) {
		if (vControl == null) {
			reportService.report(new AbstractReport("Warning: The given VControl was null.")); //$NON-NLS-1$
			return NOT_APPLICABLE;
		}
		if (domainObject == null) {
			reportService.report(new AbstractReport("Warning: The given domain object was null.")); //$NON-NLS-1$
			return NOT_APPLICABLE;
		}

		if (VTableControl.class.isInstance(vControl)
			&& VTableDomainModelReference.class.isInstance(vControl.getDomainModelReference())) {
			return 5d;
		}

		return NOT_APPLICABLE;
	}
}
