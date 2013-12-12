/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * EclipseSource Muenchen - initial API and implementation
 * 
 *******************************************************************************/
package org.eclipse.emf.ecp.view.spi.provider;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.internal.ui.Activator;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.osgi.framework.Bundle;

/**
 * Util class for retrieving a {@link VView} based on an {@link EObject}.
 * 
 * @author Eugen Neufeld
 * 
 */
public final class ViewProviderHelper {

	private static final String CLASS_CANNOT_BE_RESOLVED = "%1$s cannot be loaded because bundle %2$s cannot be resolved."; //$NON-NLS-1$
	private static final String CLASS = "class"; //$NON-NLS-1$
	private static final String EXTENSION_POINT_ID = "org.eclipse.emf.ecp.ui.view.viewModelProviders"; //$NON-NLS-1$

	private ViewProviderHelper() {

	}

	private static Set<IViewProvider> viewProviders = new HashSet<IViewProvider>();

	private static Set<IViewProvider> getViewProviders() {
		if (viewProviders == null || viewProviders.isEmpty()) {
			readViewProviders();
		}
		return viewProviders;
	}

	private static void readViewProviders() {
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
				Activator.log(ex);
			} catch (final NoSuchMethodException ex) {
				Activator.log(ex);
			} catch (final SecurityException ex) {
				Activator.log(ex);
			} catch (final InstantiationException ex) {
				Activator.log(ex);
			} catch (final IllegalAccessException ex) {
				Activator.log(ex);
			} catch (final IllegalArgumentException ex) {
				Activator.log(ex);
			} catch (final InvocationTargetException ex) {
				Activator.log(ex);
			}

		}

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
	 * .
	 * 
	 * @param eObject the {@link EObject} to find a {@link VView} for
	 * @return a view model for the given {@link EObject} or null if no suited provider could be found
	 */
	public static VView getView(EObject eObject) {
		int highestPrio = IViewProvider.NOT_APPLICABLE;
		IViewProvider selectedProvider = null;
		for (final IViewProvider viewProvider : ViewProviderHelper.getViewProviders()) {
			final int prio = viewProvider.canRender(eObject);
			if (prio > highestPrio) {
				highestPrio = prio;
				selectedProvider = viewProvider;
			}
		}
		if (selectedProvider != null) {
			return selectedProvider.generate(eObject);
		}
		return null;

	}
}
