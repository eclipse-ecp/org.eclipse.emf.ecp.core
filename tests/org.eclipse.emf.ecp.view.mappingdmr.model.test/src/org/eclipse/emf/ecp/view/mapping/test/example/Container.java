/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 */
package org.eclipse.emf.ecp.view.mapping.test.example;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.mapping.test.example.Container#getChildren <em>Children</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.ecp.view.mapping.test.example.ExamplePackage#getContainer()
 * @model
 * @generated
 */
public interface Container extends EObject {
	/**
	 * Returns the value of the '<em><b>Children</b></em>' map.
	 * The key is of type {@link org.eclipse.emf.ecore.EClass},
	 * and the value is of type {@link org.eclipse.emf.ecp.view.mapping.test.example.AbstractChild},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Children</em>' map isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Children</em>' map.
	 * @see org.eclipse.emf.ecp.view.mapping.test.example.ExamplePackage#getContainer_Children()
	 * @model mapType=
	 *        "org.eclipse.emf.ecp.view.mapping.test.example.EClassToAdditionMap<org.eclipse.emf.ecore.EClass, org.eclipse.emf.ecp.view.mapping.test.example.AbstractChild>"
	 * @generated
	 */
	EMap<EClass, AbstractChild> getChildren();

} // Container
