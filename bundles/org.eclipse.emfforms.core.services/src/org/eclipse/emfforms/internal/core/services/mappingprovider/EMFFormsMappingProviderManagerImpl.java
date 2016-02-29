/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.mappingprovider;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.mappingprovider.EMFFormsMappingProvider;
import org.eclipse.emfforms.spi.core.services.mappingprovider.EMFFormsMappingProviderManager;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Eugen
 *
 */
@Component
public class EMFFormsMappingProviderManagerImpl implements EMFFormsMappingProviderManager {

	private final Set<EMFFormsMappingProvider> mappingProviders = new LinkedHashSet<EMFFormsMappingProvider>();
	private ReportService reportService;

	/**
	 * Called by the framework to add an {@link EMFFormsMappingProvider}.
	 *
	 * @param emfFormsMappingProvider The {@link EMFFormsMappingProvider}
	 */

	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	protected void addEMFFormsMappingProvider(EMFFormsMappingProvider emfFormsMappingProvider) {
		mappingProviders.add(emfFormsMappingProvider);
	}

	/**
	 * Called by the framework to remove an {@link EMFFormsMappingProvider}.
	 *
	 * @param emfFormsMappingProvider The {@link EMFFormsMappingProvider}
	 */
	protected void removeEMFFormsMappingProvider(EMFFormsMappingProvider emfFormsMappingProvider) {
		mappingProviders.remove(emfFormsMappingProvider);
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
	 * @see org.eclipse.emfforms.spi.core.services.mappingprovider.EMFFormsMappingProviderManager#getAllSettingsFor(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public Set<UniqueSetting> getAllSettingsFor(VDomainModelReference domainModelReference, EObject domainObject) {
		EMFFormsMappingProvider bestMappingProvider = null;
		double bestScore = EMFFormsMappingProvider.NOT_APPLICABLE;

		for (final EMFFormsMappingProvider mappingProvider : mappingProviders) {
			final double score = mappingProvider.isApplicable(domainModelReference, domainObject);
			if (score > bestScore) {
				bestMappingProvider = mappingProvider;
				bestScore = score;
			}
		}
		if (bestMappingProvider == null) {
			reportService.report(new AbstractReport("Warning: No applicable EMFFormsMappingProvider was found.")); //$NON-NLS-1$
			return Collections.emptySet();
		}
		return bestMappingProvider.getMappingFor(domainModelReference, domainObject);
	}
}
