/**
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Model Properties</b></em>'.
 *
 * @since 1.7
 *        <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.emf.ecp.view.spi.model.VViewPackage#getViewModelProperties()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface VViewModelProperties extends EObject {

	/**
	 * Creates new {@link VViewModelProperties properties} which inherit the
	 * {@link #addInheritableProperty(String, Object)
	 * inheritable properties} of this object.
	 *
	 * @return the new properties
	 */
	VViewModelProperties inherit();

	/**
	 * Returns <code>true</code> if there is either an inherited property or a non-inherited property for the given key.
	 * Returns <code>false</code> otherwise.
	 *
	 * @param key the key to check
	 * @return whether there is a property for the given key
	 */
	boolean containsKey(String key);

	/**
	 * Returns the property value for the given key or <code>null</code> if there is no property for the given key,
	 *
	 * @param key the key to get the value for
	 * @return the value
	 */
	Object get(String key);

	/**
	 * Adds a new inheritable property. Returns the value which has been associated with the given key before the new
	 * property was added or <code>null</code> if there was no value for the key.
	 *
	 * @param key the key to add
	 * @param value the value to add
	 * @return the previous value
	 */
	Object addInheritableProperty(String key, Object value);

	/**
	 * Adds a new non-inheritable property. Returns the value which has been associated with the given key before the
	 * new
	 * property was added or <code>null</code> if there was no value for the key.
	 *
	 * @param key the key to add
	 * @param value the value to add
	 * @return the previous value
	 */
	Object addNonInheritableProperty(String key, Object value);

} // VViewModelProperties
