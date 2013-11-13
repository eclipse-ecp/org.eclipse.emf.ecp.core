/**
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>VFeature Path Domain Model Reference</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.model.VFeaturePathDomainModelReference#getDomainModelEFeature <em>Domain Model
 * EFeature</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.model.VFeaturePathDomainModelReference#getDomainModelEReferencePath <em>Domain
 * Model EReference Path</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.emf.ecp.view.model.VViewPackage#getFeaturePathDomainModelReference()
 * @model
 * @generated
 */
public interface VFeaturePathDomainModelReference extends VDomainModelReference
{

	/**
	 * Returns the value of the '<em><b>Domain Model EFeature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Domain Model EFeature</em>' reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Domain Model EFeature</em>' reference.
	 * @see #setDomainModelEFeature(EStructuralFeature)
	 * @see org.eclipse.emf.ecp.view.model.VViewPackage#getFeaturePathDomainModelReference_DomainModelEFeature()
	 * @model required="true"
	 * @generated
	 */
	EStructuralFeature getDomainModelEFeature();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.ecp.view.model.VFeaturePathDomainModelReference#getDomainModelEFeature
	 * <em>Domain Model EFeature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Domain Model EFeature</em>' reference.
	 * @see #getDomainModelEFeature()
	 * @generated
	 */
	void setDomainModelEFeature(EStructuralFeature value);

	/**
	 * Returns the value of the '<em><b>Domain Model EReference Path</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EReference}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Domain Model EReference Path</em>' reference list isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Domain Model EReference Path</em>' reference list.
	 * @see org.eclipse.emf.ecp.view.model.VViewPackage#getFeaturePathDomainModelReference_DomainModelEReferencePath()
	 * @model
	 * @generated
	 */
	EList<EReference> getDomainModelEReferencePath();

} // VFeaturePathDomainModelReference
