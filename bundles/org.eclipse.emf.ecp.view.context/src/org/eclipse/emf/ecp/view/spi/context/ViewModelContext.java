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
import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeListener;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext;

/**
 * The Interface ViewModelContext.
 *
 * @author Eugen Neufeld
 * @since 1.2
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ViewModelContext extends EMFFormsViewContext {

	/**
	 * Register domain change listener.
	 *
	 * @param modelChangeListener the model change listener
	 * @since 1.3
	 */
	@Override
	void registerDomainChangeListener(ModelChangeListener modelChangeListener);

	/**
	 * Unregister domain change listener.
	 *
	 * @param modelChangeListener the model change listener
	 * @since 1.3
	 */
	@Override
	void unregisterDomainChangeListener(ModelChangeListener modelChangeListener);

	/**
	 * Gets the view model.
	 *
	 * @return the view model
	 */
	@Override
	VElement getViewModel();

	/**
	 * Gets the domain model.
	 *
	 * @return the domain model
	 */
	@Override
	EObject getDomainModel();

	/**
	 * Register view change listener.
	 *
	 * @param modelChangeListener the model change listener
	 * @since 1.3
	 */
	@Override
	void registerViewChangeListener(ModelChangeListener modelChangeListener);

	/**
	 * Unregister view change listener.
	 *
	 * @param modelChangeListener the model change listener
	 * @since 1.3
	 */
	@Override
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
	@Override
	<T> T getService(Class<T> serviceType);

	/**
	 * Returns all controls which are associated with the provided {@link Setting}. The {@link Setting} is converted to
	 * a {@link UniqueSetting}.
	 *
	 * @param setting the {@link Setting} to search controls for
	 * @return the Set of all controls associated with the provided setting or null if no controls can be found
	 * @since 1.3
	 * @deprecated please use
	 *             {@link org.eclipse.emfforms.spi.core.services.controlmapper.EMFFormsSettingToControlMapper#getControlsFor(Setting)
	 *             EMFFormsSettingToControlMapper#getControlsFor(Setting)}
	 */
	@Deprecated
	Set<VControl> getControlsFor(Setting setting);

	/**
	 * Returns all controls which are associated with the provided {@link UniqueSetting}.
	 *
	 * @param setting the {@link UniqueSetting} to search controls for
	 * @return the Set of all controls associated with the provided setting or null if no controls can be found
	 * @since 1.5
	 * @deprecated please use
	 *             {@link org.eclipse.emfforms.spi.core.services.controlmapper.EMFFormsSettingToControlMapper#getControlsFor(UniqueSetting)
	 *             EMFFormsSettingToControlMapper#getControlsFor(UniqueSetting)}
	 */
	@Deprecated
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
	 * This returns the childContext for the provided EObject and the provided {@link VElement}. If a child context
	 * already exists it will be returned otherwise a new {@link ViewModelContext} will be created.
	 *
	 * @param eObject The {@link EObject} to get the child context for
	 * @param parent The {@link VElement} which requests the child context
	 * @param vView The {@link VView} of the {@link EObject}
	 * @param viewModelServices The list of {@link ViewModelService} which should be part of a child context
	 * @return a {@link ViewModelContext} witch is a child of the current context
	 * @since 1.5
	 */
	ViewModelContext getChildContext(EObject eObject, VElement parent, VView vView,
		ViewModelService... viewModelServices);

	/**
	 * This returns the parent context. This may be <code>null</code> for the topmost context.
	 *
	 * @return the parent
	 * @since 1.9
	 */
	ViewModelContext getParentContext();

	/**
	 * If this context has a {@link #getParentContext() parent context} this method will return the parent VElement
	 * which requested the creation of this context. Otherwise this method will return <code>null</code>.
	 *
	 * @return the parent
	 * @since 1.9
	 */
	VElement getParentVElement();

	/**
	 * Allows to register a dispose listener.
	 *
	 * @param listener The {@link ViewModelContextDisposeListener} to register
	 * @since 1.5
	 */
	void registerDisposeListener(ViewModelContextDisposeListener listener);

	/**
	 * Adds a user of the context.
	 *
	 * @param user The context user to add
	 * @since 1.5
	 */
	void addContextUser(Object user);

	/**
	 * Removes a context user.
	 *
	 * @param user The context user to remove
	 * @since 1.5
	 */
	void removeContextUser(Object user);
}
