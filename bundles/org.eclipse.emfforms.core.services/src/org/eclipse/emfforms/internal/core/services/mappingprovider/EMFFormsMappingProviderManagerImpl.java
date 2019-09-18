/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
import org.eclipse.emfforms.common.RankingHelper;
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

	private static final RankingHelper<EMFFormsMappingProvider> RANKING_HELPER = //
		new RankingHelper<EMFFormsMappingProvider>(
			EMFFormsMappingProvider.class,
			EMFFormsMappingProvider.NOT_APPLICABLE,
			EMFFormsMappingProvider.NOT_APPLICABLE);

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
	@Reference(unbind = "-")
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
	public Set<UniqueSetting> getAllSettingsFor(
		final VDomainModelReference domainModelReference, final EObject domainObject) {

		final EMFFormsMappingProvider bestMappingProvider = RANKING_HELPER.getHighestRankingElement(
			mappingProviders,
			new RankingHelper.RankTester<EMFFormsMappingProvider>() {

				@Override
				public double getRank(final EMFFormsMappingProvider mappingProvider) {
					return mappingProvider.isApplicable(domainModelReference, domainObject);
				}

			});

		if (bestMappingProvider == null) {
			reportService.report(new AbstractReport("Warning: No applicable EMFFormsMappingProvider was found.")); //$NON-NLS-1$
			return Collections.emptySet();
		}
		return bestMappingProvider.getMappingFor(domainModelReference, domainObject);
	}
}
