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
 * Edgar Mueller - refactorings
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.provider;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;
import org.eclipse.emf.ecp.view.spi.provider.EMFFormsViewService;
import org.eclipse.emf.ecp.view.spi.provider.IViewProvider;
import org.eclipse.emf.ecp.view.spi.provider.reporting.NoViewProviderFoundReport;
import org.eclipse.emf.ecp.view.spi.provider.reporting.ViewModelIsNullReport;
import org.eclipse.emf.ecp.view.spi.provider.reporting.ViewProviderInitFailedReport;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.osgi.framework.Bundle;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * Implementation of the {@link EMFFormsViewService} which collects all known {@link IViewProvider} and finds the best
 * fitting view.
 *
 * @author Eugen Neufeld
 */
@Component(name = "EMFFormsViewService")
public class ViewProviderImpl implements EMFFormsViewService {

	private static final String CLASS_CANNOT_BE_RESOLVED = "%1$s cannot be loaded because bundle %2$s cannot be resolved."; //$NON-NLS-1$
	private static final String CLASS = "class"; //$NON-NLS-1$
	private static final String EXTENSION_POINT_ID = "org.eclipse.emf.ecp.ui.view.viewModelProviders"; //$NON-NLS-1$
	private final Set<IViewProvider> viewProviders = new CopyOnWriteArraySet<IViewProvider>();
	private ReportService reportService;

	/**
	 * Component activate method.
	 */
	@Activate
	protected void activate() {
		readViewProviders();
	}

	private void readViewProviders() {
		final IConfigurationElement[] controls = Platform.getExtensionRegistry()
			.getConfigurationElementsFor(
				EXTENSION_POINT_ID);

		for (final IConfigurationElement e : controls) {
			try {
				final String clazz = e.getAttribute(CLASS);
				final Class<? extends IViewProvider> resolvedClass = loadClass(e
					.getContributor().getName(), clazz);
				final Constructor<? extends IViewProvider> controlConstructor = resolvedClass
					.getConstructor();
				final IViewProvider viewProvider = controlConstructor.newInstance();
				viewProviders.add(viewProvider);
			} catch (final ClassNotFoundException ex) {
				report(new ViewProviderInitFailedReport(ex));
			} catch (final NoSuchMethodException ex) {
				report(new ViewProviderInitFailedReport(ex));
			} catch (final SecurityException ex) {
				report(new ViewProviderInitFailedReport(ex));
			} catch (final InstantiationException ex) {
				report(new ViewProviderInitFailedReport(ex));
			} catch (final IllegalAccessException ex) {
				report(new ViewProviderInitFailedReport(ex));
			} catch (final IllegalArgumentException ex) {
				report(new ViewProviderInitFailedReport(ex));
			} catch (final InvocationTargetException ex) {
				report(new ViewProviderInitFailedReport(ex));
			}
		}
	}

	private void report(AbstractReport reportEntity) {
		reportService.report(reportEntity);
	}

	@SuppressWarnings("unchecked")
	private static <T> Class<T> loadClass(String bundleName, String clazz)
		throws ClassNotFoundException {
		final Bundle bundle = Platform.getBundle(bundleName);
		if (bundle == null) {
			throw new ClassNotFoundException(String.format(
				CLASS_CANNOT_BE_RESOLVED, clazz, bundleName));
		}
		return (Class<T>) bundle.loadClass(clazz);

	}

	/**
	 * This allows to retrieve a {@link VView} based on an {@link EObject}. This method reads all {@link IViewProvider
	 * IViewProviders} and searches for the best fitting. If none can be found, then null is returned.
	 *
	 * @param eObject the {@link EObject} to find a {@link VView} for
	 * @param properties the {@link VViewModelProperties properties}
	 * @return a view model for the given {@link EObject} or null if no suited provider could be found
	 */
	@Override
	public VView getView(EObject eObject, VViewModelProperties properties) {
		double highestPrio = IViewProvider.NOT_APPLICABLE;
		IViewProvider selectedProvider = null;
		if (properties == null) {
			properties = VViewFactory.eINSTANCE.createViewModelLoadingProperties();
		}

		if (viewProviders.isEmpty()) {
			report(new NoViewProviderFoundReport());
		}

		for (final IViewProvider viewProvider : viewProviders) {
			final double prio = viewProvider.canProvideViewModel(eObject, properties);
			if (prio > highestPrio) {
				highestPrio = prio;
				selectedProvider = viewProvider;
			}
		}

		if (selectedProvider != null) {
			final VView view = selectedProvider.provideViewModel(eObject, properties);
			if (view == null) {
				report(new ViewModelIsNullReport());
			}
			return view;
		}

		return null;

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.provider.EMFFormsViewService#addProvider(org.eclipse.emf.ecp.view.spi.provider.IViewProvider)
	 */
	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	@Override
	public void addProvider(IViewProvider viewProvider) {
		viewProviders.add(viewProvider);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.provider.EMFFormsViewService#removeProvider(org.eclipse.emf.ecp.view.spi.provider.IViewProvider)
	 */
	@Override
	public void removeProvider(IViewProvider viewProvider) {
		viewProviders.remove(viewProvider);
	}

	/**
	 * Set the ReportService.
	 *
	 * @param reportService The {@link ReportService}
	 */
	@Reference(cardinality = ReferenceCardinality.MANDATORY, policy = ReferencePolicy.STATIC)
	protected void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}
}
