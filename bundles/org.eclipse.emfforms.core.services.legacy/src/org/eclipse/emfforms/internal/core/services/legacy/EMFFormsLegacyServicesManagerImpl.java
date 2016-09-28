/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.legacy;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecp.view.spi.context.EMFFormsLegacyServicesManager;
import org.eclipse.emf.ecp.view.spi.context.GlobalViewModelService;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceFactory;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * This implements the {@link EMFFormsLegacyServicesManager} which allows to use {@link ViewModelService} registered
 * using an extension point with the new osgi based architecture.
 *
 * @author Eugen Neufeld
 *
 */
@SuppressWarnings("rawtypes")
@Component
public class EMFFormsLegacyServicesManagerImpl implements EMFFormsLegacyServicesManager {

	private ReportService reportService;
	private final Set<ServiceRegistration<EMFFormsViewServiceFactory>> registrations = new LinkedHashSet<ServiceRegistration<EMFFormsViewServiceFactory>>();
	private BundleContext bundleContext;
	private boolean instantiated;

	/**
	 * Called by OSGi to set the {@link ReportService}.
	 *
	 * @param reportService The {@link ReportService}
	 */
	@Reference(cardinality = ReferenceCardinality.MANDATORY, policy = ReferencePolicy.STATIC, unbind = "-")
	protected void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	
	/**
	 * Called by OSGi when the component is ready to be activated.
	 *
	 * @param bundleContext The {@link BundleContext}
	 */
	@Activate
	protected void activate(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}

	/**
	 * Called by OSGi when the component must be deactivated.
	 */
	@Deactivate
	protected void deactivate() {
		for (final ServiceRegistration<EMFFormsViewServiceFactory> registration : registrations) {
			registration.unregister();
		}
		registrations.clear();
	}

	@SuppressWarnings("unchecked")
	private Set<EMFFormsViewServiceFactory<? extends ViewModelService>> parseExtensions() {
		final Set<EMFFormsViewServiceFactory<? extends ViewModelService>> legacyServiceProviders = new LinkedHashSet<EMFFormsViewServiceFactory<? extends ViewModelService>>();
		final IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		if (extensionRegistry == null) {
			return legacyServiceProviders;
		}
		final IConfigurationElement[] controls = extensionRegistry
			.getConfigurationElementsFor("org.eclipse.emf.ecp.view.context.viewServices"); //$NON-NLS-1$
		for (final IConfigurationElement e : controls) {
			try {
				final ViewModelService viewService = (ViewModelService) e.createExecutableExtension("class"); //$NON-NLS-1$
				if (GlobalViewModelService.class.isInstance(viewService)) {
					legacyServiceProviders.add(new EMFFormsLegacyGlobalServiceFactory(viewService.getClass(),
						viewService.getPriority(), reportService));
				} else {
					legacyServiceProviders.add(new EMFFormsLegacyLocalServiceFactory(viewService.getClass(),
						viewService.getPriority(), reportService));
				}
			} catch (final CoreException e1) {
				reportService.report(new AbstractReport(e1));
			}
		}
		return legacyServiceProviders;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.EMFFormsLegacyServicesManager#instantiate()
	 */
	@Override
	public void instantiate() {
		if (instantiated) {
			return;
		}
		final Set<EMFFormsViewServiceFactory<? extends ViewModelService>> legacyServiceProviders = parseExtensions();
		for (final EMFFormsViewServiceFactory<? extends ViewModelService> provider : legacyServiceProviders) {
			final ServiceRegistration<EMFFormsViewServiceFactory> registerService = bundleContext
				.registerService(EMFFormsViewServiceFactory.class, provider, null);
			registrations.add(registerService);
		}
		instantiated = true;
	}
}
