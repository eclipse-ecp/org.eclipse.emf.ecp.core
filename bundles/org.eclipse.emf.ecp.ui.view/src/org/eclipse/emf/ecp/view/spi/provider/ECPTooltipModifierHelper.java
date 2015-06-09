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
package org.eclipse.emf.ecp.view.spi.provider;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.view.internal.ui.Activator;
import org.osgi.framework.Bundle;

/**
 * A helper to ease the access to the string modifiers.
 *
 * @author Eugen Neufeld
 * @since 1.3
 */
public final class ECPTooltipModifierHelper {
	private static final String CLASS_CANNOT_BE_RESOLVED = "%1$s cannot be loaded because bundle %2$s cannot be resolved."; //$NON-NLS-1$
	private static final String CLASS = "class"; //$NON-NLS-1$
	private static final String EXTENSION_POINT_ID = "org.eclipse.emf.ecp.ui.view.tooltipModifier"; //$NON-NLS-1$

	private ECPTooltipModifierHelper() {

	}

	private static Set<ECPStringModifier> stringModifiers = new LinkedHashSet<ECPStringModifier>();

	private static Set<ECPStringModifier> getStringModifiers() {
		if (stringModifiers == null || stringModifiers.isEmpty()) {
			readStringModifiers();
		}
		return stringModifiers;
	}

	private static void readStringModifiers() {
		final IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		if (extensionRegistry == null) {
			return;
		}
		final IConfigurationElement[] controls = extensionRegistry
			.getConfigurationElementsFor(
			EXTENSION_POINT_ID);
		for (final IConfigurationElement e : controls) {
			try {
				final String clazz = e.getAttribute(CLASS);
				final Class<? extends ECPStringModifier> resolvedClass = loadClass(e
					.getContributor().getName(), clazz);
				final Constructor<? extends ECPStringModifier> controlConstructor = resolvedClass
					.getConstructor();
				final ECPStringModifier stringModifier = controlConstructor.newInstance();
				stringModifiers.add(stringModifier);
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
	 * Modifies a string by iterating over all known {@link ECPStringModifier}.
	 *
	 * @param text the text to modify
	 * @param setting the {@link Setting} used to create the text
	 * @return the modified text
	 */
	public static String modifyString(String text, Setting setting) {
		String returnValue = text;
		for (final ECPStringModifier tooltipModifier : getStringModifiers()) {
			returnValue = tooltipModifier.modifyString(returnValue, setting);
		}
		return returnValue;
	}

}
