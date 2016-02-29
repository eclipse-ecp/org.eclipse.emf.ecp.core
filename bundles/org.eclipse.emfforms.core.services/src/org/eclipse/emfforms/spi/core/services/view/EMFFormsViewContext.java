/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.core.services.view;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeListener;
import org.eclipse.emf.ecp.view.spi.model.VElement;

/**
 * The EMFFormsViewContext provides easy access to commonly used methods during the rendering. It allows to retrieve the
 * view model as well as the domain model. Furthermore it is possible to register as a listener on the view model as
 * well as on the domain model.
 *
 * @author Eugen Neufeld
 * @since 1.8
 */
public interface EMFFormsViewContext {

	/**
	 * Gets the view model.
	 *
	 * @return the view model
	 */
	VElement getViewModel();

	/**
	 * Register view change listener.
	 *
	 * @param modelChangeListener the model change listener
	 */
	void registerViewChangeListener(ModelChangeListener modelChangeListener);

	/**
	 * Unregister view change listener.
	 *
	 * @param modelChangeListener the model change listener
	 */
	void unregisterViewChangeListener(ModelChangeListener modelChangeListener);

	/**
	 * Gets the domain model.
	 *
	 * @return the domain model
	 */
	EObject getDomainModel();

	/**
	 * Register domain change listener.
	 *
	 * @param modelChangeListener the model change listener
	 */
	void registerDomainChangeListener(ModelChangeListener modelChangeListener);

	/**
	 * Unregister domain change listener.
	 *
	 * @param modelChangeListener the model change listener
	 */
	void unregisterDomainChangeListener(ModelChangeListener modelChangeListener);

	/**
	 * Register an {@link EMFFormsContextListener}.
	 *
	 * @param contextListener the {@link EMFFormsContextListener} to register
	 */
	void registerEMFFormsContextListener(EMFFormsContextListener contextListener);

	/**
	 * Unregister an {@link EMFFormsContextListener}.
	 *
	 * @param contextListener the {@link EMFFormsContextListener} to unregister
	 */
	void unregisterEMFFormsContextListener(EMFFormsContextListener contextListener);

	/**
	 * Retrieve a service of type {@code serviceType}.
	 *
	 * @param <T> the type of the desired service
	 *
	 * @param serviceType the type of the service to be retrieved
	 * @return the service
	 */
	<T> T getService(Class<T> serviceType);
}
