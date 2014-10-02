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
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.context;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.common.UniqueSetting;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeListener;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;

/**
 * The Interface ViewModelContext.
 *
 * @author Eugen Neufeld
 * @since 1.2
 */
public interface ViewModelContext {

	/**
	 * Register domain change listener.
	 *
	 * @param modelChangeListener the model change listener
	 * @since 1.3
	 */
	void registerDomainChangeListener(ModelChangeListener modelChangeListener);

	/**
	 * Unregister domain change listener.
	 *
	 * @param modelChangeListener the model change listener
	 * @since 1.3
	 */
	void unregisterDomainChangeListener(ModelChangeListener modelChangeListener);

	/**
	 * Gets the view model.
	 *
	 * @return the view model
	 */
	VElement getViewModel();

	/**
	 * Gets the domain model.
	 *
	 * @return the domain model
	 */
	EObject getDomainModel();

	/**
	 * Register view change listener.
	 *
	 * @param modelChangeListener the model change listener
	 * @since 1.3
	 */
	void registerViewChangeListener(ModelChangeListener modelChangeListener);

	/**
	 * Unregister view change listener.
	 *
	 * @param modelChangeListener the model change listener
	 * @since 1.3
	 */
	void unregisterViewChangeListener(ModelChangeListener modelChangeListener);

	/**
	 * Disposes the context.
	 */
	void dispose();

	/**
	 * Whether the context has a service of the given type {@code serviceType}.
	 *
	 * @param <T>
	 *            the type of the desired service
	 *
	 * @param serviceType
	 *            the type of the service
	 * @return {@code true}, if the context has a service of the given type, {@code false} otherwise
	 */
	<T> boolean hasService(Class<T> serviceType);

	/**
	 * Retrieve an {@link ViewModelService} of type {@code serviceType}.
	 *
	 * @param <T>
	 *            the type of the desired service
	 *
	 * @param serviceType
	 *            the type of the service to be retrieved
	 * @return the service
	 */
	<T> T getService(Class<T> serviceType);

	/**
	 * Returns all controls which are associated with the provided {@link Setting}. The {@link Setting} is converted to
	 * a {@link UniqueSetting}.
	 *
	 * @param setting the {@link Setting} to search controls for
	 * @return the Set of all controls associated with the provided setting or null if no controls can be found
	 * @since 1.3
	 */
	Set<VControl> getControlsFor(Setting setting);

	/**
	 * Returns all controls which are associated with the provided {@link UniqueSetting}.
	 *
	 * @param setting the {@link UniqueSetting} to search controls for
	 * @return the Set of all controls associated with the provided setting or null if no controls can be found
	 * @since 1.3
	 */
	Set<VElement> getControlsFor(UniqueSetting setting);

	/**
	 * Returns the value of the context for the passed key.
	 *
	 * @param key the key of the value to get
	 * @return the Object for the provided key
	 * @since 1.4
	 */
	Object getContextValue(String key);

	/**
	 * Puts the value for the provided key into the context.
	 *
	 * @param key the key to set
	 * @param value the Object to set into the context
	 * @since 1.4
	 */
	void putContextValue(String key, Object value);

	/**
	 * @since 1.5
	 */
	void addChildContext(VElement vElement, EObject eObject, ViewModelContext childContext);

	/**
	 * @since 1.5
	 */
	ViewModelContext getChildContext(EObject eObject);

	/**
	 * @since 1.5
	 */
	void removeChildContext(EObject eObject);
}
