/********************************************************************************
 * Copyright (c) 2011 Eike Stepper (Berlin, Germany) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eike Stepper - initial API and implementation
 ********************************************************************************/
package org.eclipse.emf.ecp.spi.ui;

import java.util.Collection;

/**
 * @author Eike Stepper
 * @since 1.1
 */
public interface UIProviderRegistry {
	/**
	 * This is the Instance to use for the {@link UIProviderRegistry}.
	 */
	UIProviderRegistry INSTANCE = org.eclipse.emf.ecp.internal.ui.UIProviderRegistryImpl.INSTANCE;

	/**
	 * It the adaptable is ECPProviderAware then the {@link UIProvider} that corresponds to the
	 * {@link org.eclipse.emf.ecp.core.ECPProvider} is
	 * returned. Otherwise the AdapterUtil tries to resolve this.
	 *
	 * @param adaptable the Object to adapt
	 * @return the {@link UIProvider} or null if none was found
	 */
	UIProvider getUIProvider(Object adaptable);

	/**
	 * Returns the {@link UIProvider} by its name.
	 *
	 * @param name the name of the ui provider
	 * @return the {@link UIProvider} or null if none was found
	 */
	UIProvider getUIProvider(String name);

	/**
	 * Returns all known {@link UIProvider}.
	 *
	 * @return the array containing all known {@link UIProvider}
	 */
	Collection<UIProvider> getUIProviders();

	/**
	 * Whether any {@link UIProvider} are registered.
	 *
	 * @return true if at least one {@link UIProvider} is registered, false otherwise
	 */
	boolean hasUIProviders();
}
