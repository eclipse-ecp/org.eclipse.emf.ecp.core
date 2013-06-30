/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eike Stepper - initial API and implementation
 * Eugen Neufeld - JavaDoc and API cleanup
 * Jonas Helming - API cleanup
 * 
 *******************************************************************************/

package org.eclipse.emf.ecp.core;

import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.core.util.observer.ECPObserver;
import org.eclipse.emf.ecp.core.util.observer.ECPObserverBus;
import org.eclipse.emf.ecp.core.util.observer.ECPProvidersChangedObserver;

import java.util.Collection;

/**
 * Registry class to manage the registered providers.
 * It publishes observable events on the {@link ECPObserverBus}.
 * Related Observer types: {@link ECPProvidersChangedObserver}. Use {@link ECPUtil#getECPObserverBus()} to
 * retrieve the ObserverBus and {@link ECPObserverBus#register(ECPObserver)} to register an Observer
 * 
 * @author Eike Stepper
 * @author Jonas
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface ECPProviderRegistry {

	/**
	 * This method returns the ECPProvider based on the name.
	 * 
	 * @param name the name of the {@link ECPProvider} to search for
	 * @return the {@link ECPProvider}
	 */
	ECPProvider getProvider(String name);

	/**
	 * This method returns all known providers.
	 * 
	 * @return array of {@link ECPProvider ECPProviders}
	 */
	Collection<ECPProvider> getProviders();

	/**
	 * Method to programmatically add an {@link ECPProvider} to list of available provider.
	 * 
	 * @param provider the {@link ECPProvider} to add
	 */
	void addProvider(ECPProvider provider);

	/**
	 * Delete a provider programmatically from the list of available providers by its name.
	 * 
	 * @param name the name of the provider to delete
	 */
	void removeProvider(String name);

	// /**
	// * Add an {@link ECPProviderRegistryObserver} to be notified.
	// *
	// * @param changeObserver the observer to add
	// */
	// void addObserver(ECPProviderRegistryObserver changeObserver);
	//
	// /**
	// * Remove an {@link ECPProviderRegistryObserver} from the list of the providers to be notified.
	// *
	// * @param changeObserver the observer to remove
	// */
	// void removeObserver(ECPProviderRegistryObserver changeObserver);
}
