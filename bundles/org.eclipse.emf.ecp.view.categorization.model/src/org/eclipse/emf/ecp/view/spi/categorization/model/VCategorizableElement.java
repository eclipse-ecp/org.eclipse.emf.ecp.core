/**
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.categorization.model;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Categorizable Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizableElement#getLabelObject <em>Label Object
 * </em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationPackage#getCategorizableElement()
 * @model abstract="true"
 * @generated
 */
public interface VCategorizableElement extends VElement
{

	/**
	 * Returns the value of the '<em><b>Label Object</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Label Object</em>' reference isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Label Object</em>' reference.
	 * @see org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationPackage#getCategorizableElement_LabelObject()
	 * @model resolveProxies="false" transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	EObject getLabelObject();

	List<ECPAction> getECPActions();

	void setECPActions(List<ECPAction> actions);
} // VCategorizableElement
