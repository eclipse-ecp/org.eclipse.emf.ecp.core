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

import org.eclipse.emf.common.util.EMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Model Properties</b></em>'.
 *
 * @since 1.7
 *        <!-- end-user-doc -->
 *
 *        <p>
 *        The following features are supported:
 *        </p>
 *        <ul>
 *        <li>{@link org.eclipse.emf.ecp.view.spi.model.VViewModelLoadingProperties#getInheritableProperties
 *        <em>Inheritable Properties</em>}</li>
 *        <li>{@link org.eclipse.emf.ecp.view.spi.model.VViewModelLoadingProperties#getNonInheritableProperties <em>Non
 *        Inheritable Properties</em>}</li>
 *        </ul>
 *
 * @see org.eclipse.emf.ecp.view.spi.model.VViewPackage#getViewModelLoadingProperties()
 * @model
 * @generated
 */
public interface VViewModelLoadingProperties extends VViewModelProperties {
	/**
	 * Returns the value of the '<em><b>Inheritable Properties</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.Object},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inheritable Properties</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Inheritable Properties</em>' map.
	 * @see org.eclipse.emf.ecp.view.spi.model.VViewPackage#getViewModelLoadingProperties_InheritableProperties()
	 * @model mapType="org.eclipse.emf.ecp.view.spi.model.StringToObjectMapEntry&lt;org.eclipse.emf.ecore.EString,
	 *        org.eclipse.emf.ecore.EJavaObject&gt;"
	 * @generated
	 */
	EMap<String, Object> getInheritableProperties();

	/**
	 * Returns the value of the '<em><b>Non Inheritable Properties</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.Object},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Non Inheritable Properties</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Non Inheritable Properties</em>' map.
	 * @see org.eclipse.emf.ecp.view.spi.model.VViewPackage#getViewModelLoadingProperties_NonInheritableProperties()
	 * @model mapType="org.eclipse.emf.ecp.view.spi.model.StringToObjectMapEntry&lt;org.eclipse.emf.ecore.EString,
	 *        org.eclipse.emf.ecore.EJavaObject&gt;"
	 * @generated
	 */
	EMap<String, Object> getNonInheritableProperties();

} // VViewModelProperties
