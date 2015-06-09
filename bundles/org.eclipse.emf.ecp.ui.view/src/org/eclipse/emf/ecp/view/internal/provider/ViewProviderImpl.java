/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
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
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.internal.ui.Activator;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.provider.IViewProvider;
import org.eclipse.emf.ecp.view.spi.provider.reporting.NoViewProviderFoundReport;
import org.eclipse.emf.ecp.view.spi.provider.reporting.ViewModelIsNullReport;
import org.eclipse.emf.ecp.view.spi.provider.reporting.ViewProviderInitFailedReport;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.osgi.framework.Bundle;

/**
 * @author Eugen Neufeld
 */
public class ViewProviderImpl {

	private static final String CLASS_CANNOT_BE_RESOLVED = "%1$s cannot be loaded because bundle %2$s cannot be resolved."; //$NON-NLS-1$
	private static final String CLASS = "class"; //$NON-NLS-1$
	private static final String EXTENSION_POINT_ID = "org.eclipse.emf.ecp.ui.view.viewModelProviders"; //$NON-NLS-1$
	private final Set<IViewProvider> viewProviders = new LinkedHashSet<IViewProvider>();
	private final boolean shouldReadExtensionPointPerRequest;

	/**
	 * Default Constructor.
	 */
	public ViewProviderImpl() {
		this(true);
	}

	/**
	 * Constructor.
	 *
	 * @param shouldReadExtensionPointPerRequest
	 *            whether the view providers extension should be read on each request
	 */
	public ViewProviderImpl(boolean shouldReadExtensionPointPerRequest) {
		this.shouldReadExtensionPointPerRequest = shouldReadExtensionPointPerRequest;
	}

	/**
	 * Clears all {@link IViewProvider}s.
	 */
	public void clearProviders() {
		viewProviders.clear();
	}

	/**
	 * Adds a {@link IViewProvider}.
	 *
	 * @param provider
	 *            the {@link IViewProvider} to be added
	 */
	public void addProvider(IViewProvider provider) {
		viewProviders.add(provider);
	}

	private Set<IViewProvider> getViewProviders() {
		if (viewProviders == null || viewProviders.isEmpty()) {
			viewProviders.addAll(readViewProviders());
		}
		return viewProviders;
	}

	private static Set<IViewProvider> readViewProviders() {
		final IConfigurationElement[] controls = Platform.getExtensionRegistry()
			.getConfigurationElementsFor(
				EXTENSION_POINT_ID);

		final Set<IViewProvider> providers = new LinkedHashSet<IViewProvider>();

		for (final IConfigurationElement e : controls) {
			try {
				final String clazz = e.getAttribute(CLASS);
				final Class<? extends IViewProvider> resolvedClass = loadClass(e
					.getContributor().getName(), clazz);
				final Constructor<? extends IViewProvider> controlConstructor = resolvedClass
					.getConstructor();
				final IViewProvider viewProvider = controlConstructor.newInstance();
				providers.add(viewProvider);
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

		return providers;
	}

	private static void report(AbstractReport reportEntity) {
		Activator.getDefault().getReportService().report(reportEntity);
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
	 * @param context a key-value-map from String to Object
	 * @return a view model for the given {@link EObject} or null if no suited provider could be found
	 */
	public VView getView(EObject eObject, Map<String, Object> context) {
		int highestPrio = IViewProvider.NOT_APPLICABLE;
		IViewProvider selectedProvider = null;
		if (context == null) {
			context = new LinkedHashMap<String, Object>();
		}

		Set<IViewProvider> providers;
		if (shouldReadExtensionPointPerRequest) {
			providers = getViewProviders();
		} else {
			providers = viewProviders;
		}

		if (providers.isEmpty()) {
			report(new NoViewProviderFoundReport());
		}

		for (final IViewProvider viewProvider : providers) {
			final int prio = viewProvider.canRender(eObject, context);
			if (prio > highestPrio) {
				highestPrio = prio;
				selectedProvider = viewProvider;
			}
		}

		if (selectedProvider != null) {
			final VView view = selectedProvider.generate(eObject, context);
			if (view == null) {
				report(new ViewModelIsNullReport());
			}
			return view;
		}

		return null;

	}
}
