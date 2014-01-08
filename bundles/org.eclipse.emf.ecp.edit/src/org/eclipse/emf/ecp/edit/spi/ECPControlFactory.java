/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 * 
 *******************************************************************************/
package org.eclipse.emf.ecp.edit.spi;

import java.util.Set;

import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;

/**
 * The ControlFactory provides a
 * method ({@link ECPControlFactory#createControl(Class, VDomainModelReference)}) for creating a
 * suitable
 * control with the known widgets.
 * 
 * @author Eugen Neufeld
 * 
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ECPControlFactory {

	/**
	 * Creates an {@link ECPAbstractControl} based on a {@link Class} and the {@link VDomainModelReference}.
	 * 
	 * @param controlTypeClass the type of the control to return
	 * @param domainModelReference the {@link VDomainModelReference} to use to identify the control
	 * @param <T> the type of the control to return
	 * @return the found control or null
	 * 
	 * @since 1.1
	 */
	<T> T createControl(Class<T> controlTypeClass, VDomainModelReference domainModelReference);

	/**
	 * Creates an {@link ECPAbstractControl} based on an id.
	 * 
	 * @param controlId the if of the control to return
	 * @param <T> the type of the control to return
	 * @return the found control or null
	 * 
	 * @since 1.1
	 */
	<T> T createControl(String controlId);

	/**
	 * A copy of all known {@link ECPControlDescription}.
	 * 
	 * @return a copy of the set of all known controlDescriptions
	 */
	Set<ECPControlDescription> getControlDescriptors();
}
