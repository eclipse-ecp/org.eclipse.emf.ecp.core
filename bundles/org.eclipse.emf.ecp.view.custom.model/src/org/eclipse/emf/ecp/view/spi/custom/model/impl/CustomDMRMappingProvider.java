/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.custom.model.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.view.spi.custom.model.ECPHardcodedReferences;
import org.eclipse.emf.ecp.view.spi.custom.model.VCustomDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.mappingprovider.EMFFormsMappingProvider;
import org.eclipse.emfforms.spi.core.services.mappingprovider.EMFFormsMappingProviderManager;
import org.eclipse.emfforms.spi.localization.LocalizationServiceHelper;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * An EMFFormsMappingProvider for {@link VCustomDomainModelReference}.
 *
 * @author Eugen Neufeld
 * @since 1.9
 *
 */
@Component
public class CustomDMRMappingProvider implements EMFFormsMappingProvider {

	private BundleContext bundleContext;
	private ServiceReference<EMFFormsMappingProviderManager> eMFFormsMappingProviderManagerServiceReference;
	private EMFFormsMappingProviderManager mappingProviderManager;
	private ReportService reportService;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.mappingprovider.EMFFormsMappingProvider#getMappingFor(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public Set<UniqueSetting> getMappingFor(VDomainModelReference domainModelReference, EObject domainObject) {
		if (domainModelReference == null) {
			throw new IllegalArgumentException("The given VDomainModelReference must not be null."); //$NON-NLS-1$
		}
		if (!VCustomDomainModelReference.class.isInstance(domainModelReference)) {
			throw new IllegalArgumentException(
				"DomainModelReference must be an instance of VTableDomainModelReference."); //$NON-NLS-1$
		}

		final VCustomDomainModelReference customDomainModelReference = VCustomDomainModelReference.class
			.cast(domainModelReference);
		if (!customDomainModelReference.getDomainModelReferences().isEmpty()) {
			return getMappingFor(customDomainModelReference.getDomainModelReferences(), domainObject);
		}
		final ECPHardcodedReferences hardcodedReference;
		try {
			hardcodedReference = loadObject(customDomainModelReference.getBundleName(),
				customDomainModelReference.getClassName());
		} catch (final DatabindingFailedException ex) {
			reportService.report(new DatabindingFailedReport(ex));
			return Collections.<UniqueSetting> emptySet();
		}
		if (hardcodedReference == null) {
			reportService.report(new DatabindingFailedReport(new DatabindingFailedException(
				String
					.format(
						"The provided ECPHardcodedReferences from Bundle %1$s Class %2$s cannot be resolved.", //$NON-NLS-1$
						customDomainModelReference.getBundleName(), customDomainModelReference.getClassName()))));
			return Collections.<UniqueSetting> emptySet();
		}
		final Set<VDomainModelReference> neededDomainModelReferences = hardcodedReference
			.getNeededDomainModelReferences();
		if (neededDomainModelReferences.isEmpty()) {
			reportService.report(new DatabindingFailedReport(new DatabindingFailedException(
				String
					.format(
						"The provided ECPHardcodedReferences from Bundle %1$s Class %2$s doesn't define any DomainModelReferences.", //$NON-NLS-1$
						customDomainModelReference.getBundleName(), customDomainModelReference.getClassName()))));
			return Collections.<UniqueSetting> emptySet();
		}
		return getMappingFor(neededDomainModelReferences, domainObject);
	}

	private Set<UniqueSetting> getMappingFor(Collection<VDomainModelReference> domainModelReferences,
		EObject domainObject) {
		final Set<UniqueSetting> result = new LinkedHashSet<UniqueSetting>();
		for (final VDomainModelReference domainModelReference : domainModelReferences) {
			final Set<UniqueSetting> settingsFor = getEMFFormsMappingProviderManager()
				.getAllSettingsFor(domainModelReference, domainObject);
			if (settingsFor != null) {
				result.addAll(settingsFor);
			}
		}
		return result;
	}

	private static ECPHardcodedReferences loadObject(String bundleName, String clazz)
		throws DatabindingFailedException {
		final Bundle bundle = Platform.getBundle(bundleName);
		if (bundle == null) {
			new ClassNotFoundException(String.format(LocalizationServiceHelper.getString(
				VCustomDomainModelReferenceImpl.class, "BundleNotFound_ExceptionMessage"), clazz, bundleName)); //$NON-NLS-1$
			return null;
		}
		try {
			final Class<?> loadClass = bundle.loadClass(clazz);
			if (!ECPHardcodedReferences.class.isAssignableFrom(loadClass)) {
				return null;
			}
			return ECPHardcodedReferences.class.cast(loadClass.newInstance());
		} catch (final ClassNotFoundException ex) {
			throw new DatabindingFailedException(ex.getMessage());
		} catch (final InstantiationException ex) {
			throw new DatabindingFailedException(ex.getMessage());
		} catch (final IllegalAccessException ex) {
			throw new DatabindingFailedException(ex.getMessage());
		}

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.mappingprovider.EMFFormsMappingProvider#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public double isApplicable(VDomainModelReference domainModelReference, EObject domainObject) {
		if (VCustomDomainModelReference.class.isInstance(domainModelReference)) {
			return 10;
		}
		return NOT_APPLICABLE;
	}

	/**
	 * Called by the framework when the component gets activated.
	 *
	 * @param bundleContext The {@link BundleContext}
	 */
	@Activate
	protected void activate(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}

	/**
	 * Called by the framework when the component gets deactivated.
	 *
	 * @param bundleContext The {@link BundleContext}
	 */
	@Deactivate
	protected void deactivate(BundleContext bundleContext) {
		if (eMFFormsMappingProviderManagerServiceReference != null) {
			bundleContext.ungetService(eMFFormsMappingProviderManagerServiceReference);
			mappingProviderManager = null;
		}
	}

	private EMFFormsMappingProviderManager getEMFFormsMappingProviderManager() {
		if (mappingProviderManager == null) {
			eMFFormsMappingProviderManagerServiceReference = bundleContext
				.getServiceReference(EMFFormsMappingProviderManager.class);
			if (eMFFormsMappingProviderManagerServiceReference == null) {
				throw new IllegalStateException("No EMFFormsDomainExpander available!"); //$NON-NLS-1$
			}
			setEMFFormsMappingProviderManager(bundleContext.getService(eMFFormsMappingProviderManagerServiceReference));
		}
		return mappingProviderManager;
	}

	/**
	 * Helper method for tests. This is quite ugly!
	 *
	 * @param mappingProviderManager The EMFFormsMappingProviderManager to use
	 */
	void setEMFFormsMappingProviderManager(EMFFormsMappingProviderManager mappingProviderManager) {
		this.mappingProviderManager = mappingProviderManager;
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
}
