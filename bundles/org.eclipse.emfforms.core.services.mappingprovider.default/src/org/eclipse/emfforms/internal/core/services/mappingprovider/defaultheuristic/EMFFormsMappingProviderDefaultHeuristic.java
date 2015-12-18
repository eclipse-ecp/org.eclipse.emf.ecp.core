/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler- initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.mappingprovider.defaultheuristic;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.common.spi.asserts.Assert;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.mappingprovider.EMFFormsMappingProvider;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Default implementation of an {@link EMFFormsMappingProvider}.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "EMFFormsMappingProviderDefaultHeuristic")
public class EMFFormsMappingProviderDefaultHeuristic implements EMFFormsMappingProvider {

	private EMFFormsDatabinding emfFormsDatabinding;
	private ReportService reportService;

	/**
	 * Sets the {@link EMFFormsDatabinding} service.
	 *
	 * @param emfFormsDatabinding The databinding service
	 */
	@Reference
	protected void setEMFFormsDatabinding(EMFFormsDatabinding emfFormsDatabinding) {
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
	@Override
	public Map<UniqueSetting, Set<VControl>> getMappingFor(VControl vControl, EObject domainObject) {
		Assert.create(vControl).notNull();
		Assert.create(domainObject).notNull();

		IObservableValue observableValue;
		try {
			observableValue = emfFormsDatabinding.getObservableValue(vControl.getDomainModelReference(), domainObject);
		} catch (final DatabindingFailedException ex) {
			reportService.report(new DatabindingFailedReport(ex));
			return Collections.<UniqueSetting, Set<VControl>> emptyMap();
		}

		final IObserving observing = (IObserving) observableValue;
		final EObject eObject = (EObject) observing.getObserved();
		final EStructuralFeature structuralFeature = (EStructuralFeature) observableValue.getValueType();
		observableValue.dispose();
		final UniqueSetting uniqueSetting = UniqueSetting.createSetting(eObject, structuralFeature);

		return Collections.singletonMap(uniqueSetting, Collections.singleton(vControl));
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

		return 1d;
	}

}
