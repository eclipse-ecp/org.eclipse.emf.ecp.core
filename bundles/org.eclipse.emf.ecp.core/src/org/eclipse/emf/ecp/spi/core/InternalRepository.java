/**
 * Copyright (c) 2011 Eike Stepper (Berlin, Germany) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.ecp.spi.core;

import java.util.Collection;

import org.eclipse.emf.ecp.core.ECPRepository;
import org.eclipse.emf.ecp.internal.core.util.PropertiesStore.StorableElement;
import org.eclipse.emf.ecp.spi.core.util.InternalRegistryElement;

/**
 * @author Eike Stepper
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 * @since 1.1
 */
public interface InternalRepository extends ECPRepository, StorableElement, InternalRegistryElement {
	/**
	 * This method returns the provider of this repository.
	 *
	 * @return the provider of the repository
	 */
	@Override
	InternalProvider getProvider();

	/**
	 * This method returns provider specific data of this repository.
	 *
	 * @return the provider specific data
	 */
	Object getProviderSpecificData();

	/**
	 * This method sets the provider specific data for this repository.
	 *
	 * @param data the provider specific data to set
	 */
	void setProviderSpecificData(Object data);

	/**
	 * This is a callback method used to notify the repository about changes.
	 *
	 * @param objects that have changed
	 */
	void notifyObjectsChanged(Collection<Object> objects);
}
