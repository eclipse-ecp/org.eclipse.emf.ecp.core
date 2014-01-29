/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * jfaltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.edit.internal.swt.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecp.edit.internal.swt.Activator;
import org.eclipse.swt.widgets.Listener;
import org.osgi.framework.Bundle;

/**
 * Helper class for getting a verify listener for RAP client scripting. This class caches instances of a created
 * listeners since they should be reused for every applicable control.
 * 
 * @author jfaltermeier
 * 
 */
public final class ControlListenerHelper {

	/**
	 * The singleton instance.
	 */
	public static final ControlListenerHelper INSTANCE = new ControlListenerHelper();

	private final Map<Class<? extends SWTControl>, Listener> controlToListenerMap;

	private ControlListenerHelper() {
		controlToListenerMap = new LinkedHashMap<Class<? extends SWTControl>, Listener>();
		getElementsFromExtensionPoint();
	}

	private void getElementsFromExtensionPoint() {
		final IConfigurationElement[] controls = Platform.getExtensionRegistry().getConfigurationElementsFor(
			"org.eclipse.emf.ecp.edit.swt.controlListener"); //$NON-NLS-1$
		for (final IConfigurationElement e : controls) {
			try {
				final String className = e.getAttribute("control"); //$NON-NLS-1$
				final Class<? extends SWTControl> controlClass = loadClass(e.getContributor().getName(), className);
				final Listener verifyListener = (Listener) e.createExecutableExtension("listener"); //$NON-NLS-1$
				controlToListenerMap.put(controlClass, verifyListener);
			} catch (final ClassNotFoundException e1) {
				Activator.logException(e1);
			} catch (final CoreException e1) {
				Activator.logException(e1);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> Class<T> loadClass(String bundleName, String className) throws ClassNotFoundException {
		final Bundle bundle = Platform.getBundle(bundleName);
		if (bundle == null) {
			throw new ClassNotFoundException(className + UtilMessages.CellEditorFactory_CannotBeLoadedBecauseBundle
				+ bundleName
				+ UtilMessages.CellEditorFactory_CannotBeResolved);
		}
		return (Class<T>) bundle.loadClass(className);
	}

	/**
	 * Returns the registered listener for the class.
	 * 
	 * @param controlClass the class
	 * @return the registered listener or <code>null</code> if none available
	 */
	public Listener getVerifyListenerForControl(Class<? extends SWTControl> controlClass) {
		return controlToListenerMap.get(controlClass);
	}
}
