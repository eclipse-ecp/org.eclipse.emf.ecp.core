/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.swt.layout;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.internal.swt.Activator;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.spi.swt.core.layout.LayoutProvider;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;
import org.osgi.framework.Bundle;

/**
 * The helper class allowing an easy access to {@link LayoutProvider LayoutProviders}.
 *
 * @author Eugen Neufeld
 * @since 1.3
 *
 */
public final class LayoutProviderHelper {
	private static final String CLASS_CANNOT_BE_RESOLVED = "%1$s cannot be loaded because bundle %2$s cannot be resolved."; //$NON-NLS-1$
	private static final String CLASS = "class"; //$NON-NLS-1$
	private static final String EXTENSION_POINT_ID = "org.eclipse.emf.ecp.ui.view.swt.layoutProvider"; //$NON-NLS-1$

	private LayoutProviderHelper() {

	}

	private static List<LayoutProvider> layoutProviders = new ArrayList<LayoutProvider>();

	private static List<LayoutProvider> getLayoutProvider() {
		if (layoutProviders == null || layoutProviders.isEmpty()) {
			readLayoutProviders();
		}
		return layoutProviders;
	}

	private static void readLayoutProviders() {
		final IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		if (extensionRegistry == null) {
			return;
		}
		final IConfigurationElement[] controls = extensionRegistry.getConfigurationElementsFor(EXTENSION_POINT_ID);
		for (final IConfigurationElement e : controls) {
			try {
				final String clazz = e.getAttribute(CLASS);
				final Class<? extends LayoutProvider> resolvedClass = loadClass(e
					.getContributor().getName(), clazz);
				final Constructor<? extends LayoutProvider> controlConstructor = resolvedClass
					.getConstructor();
				final LayoutProvider layoutProvider = controlConstructor.newInstance();
				layoutProviders.add(layoutProvider);
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
	 * Eases the access to the layout provider extension point. For the method description.
	 *
	 * @see LayoutProvider#getColumnLayout(int, boolean)
	 *
	 * @param numColumns the number of columns to create
	 * @param equalWidth whether the columns should be equal width
	 * @return the layout to use
	 */
	public static Layout getColumnLayout(int numColumns, boolean equalWidth) {
		checkProviderLength();
		return getLayoutProvider().get(0).getColumnLayout(numColumns, equalWidth);
	}

	/**
	 * Eases the access to the layout provider extension point. For the method description.
	 *
	 * @see LayoutProvider#getLayoutData(GridCell, GridDescription, GridDescription, GridDescription, VElement, Control)
	 *
	 * @param gridCell the current {@link GridCell}
	 * @param controlGridDescription the {@link GridDescription} of the rendered {@link VElement}
	 * @param currentRowGridDescription the {@link GridDescription} of the current row
	 * @param fullGridDescription the {@link GridDescription} of the whole container
	 * @param vElement the {@link VElement} which is currently rendered
	 * @param domainModel The domain model object whose feature is currently rendered
	 * @param control the rendered {@link Control}
	 * @return the Object being the layout data to set
	 * @since 1.6
	 */
	public static Object getLayoutData(SWTGridCell gridCell, SWTGridDescription controlGridDescription,
		SWTGridDescription currentRowGridDescription, SWTGridDescription fullGridDescription, VElement vElement,
		EObject domainModel, Control control) {
		checkProviderLength();
		return getLayoutProvider().get(0).getLayoutData(gridCell, controlGridDescription, currentRowGridDescription,
			fullGridDescription, vElement, domainModel, control);
	}

	private static void checkProviderLength() {
		if (getLayoutProvider().size() != 1) {
			final StringBuilder sb = new StringBuilder("There must be exactly one LayoutProvider!"); //$NON-NLS-1$
			sb.append("\n"); //$NON-NLS-1$
			sb.append(String.format("Found %1$d providers.", getLayoutProvider().size())); //$NON-NLS-1$
			for (final LayoutProvider layoutProvider : getLayoutProvider()) {
				sb.append("\n"); //$NON-NLS-1$
				sb.append(layoutProvider.toString());
			}
			throw new IllegalStateException(sb.toString());
		}
	}

	/**
	 * The layout data for a spanning layout.
	 *
	 * @param spanX the horizontal span
	 * @param spanY the vertical span
	 * @return a simple spanning layout
	 */
	public static Object getSpanningLayoutData(int spanX, int spanY) {
		checkProviderLength();
		return getLayoutProvider().get(0).getSpanningLayoutData(spanX, spanY);
	}

	/**
	 * Allows to add a {@link LayoutProvider} directly.
	 *
	 * @param layoutProvider The {@link LayoutProvider}
	 * @since 1.6
	 */
	public static void addLayoutProvider(LayoutProvider layoutProvider) {
		layoutProviders.add(layoutProvider);
	}
}
