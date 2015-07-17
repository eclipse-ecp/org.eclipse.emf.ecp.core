/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.keyattributedmr.model;

import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;

/**
 * <!-- begin-user-doc --> A representation of the model object ' <em><b>Key Attribute Domain Model Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.keyattributedmr.model.VKeyAttributeDomainModelReference#getKeyDMR <em>Key DMR
 * </em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.spi.keyattributedmr.model.VKeyAttributeDomainModelReference#getKeyValue <em>Key
 * Value</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.spi.keyattributedmr.model.VKeyAttributeDomainModelReference#getValueDMR <em>Value
 * DMR</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.ecp.view.spi.keyattributedmr.model.VKeyattributedmrPackage#getKeyAttributeDomainModelReference()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='resolveable'"
 * @generated
 */
public interface VKeyAttributeDomainModelReference extends
	VFeaturePathDomainModelReference {

	/**
	 * Returns the value of the '<em><b>Key DMR</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Key DMR</em>' containment reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Key DMR</em>' containment reference.
	 * @see #setKeyDMR(VDomainModelReference)
	 * @see org.eclipse.emf.ecp.view.spi.keyattributedmr.model.VKeyattributedmrPackage#getKeyAttributeDomainModelReference_KeyDMR()
	 * @model containment="true" required="true"
	 * @generated
	 */
	VDomainModelReference getKeyDMR();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.ecp.view.spi.keyattributedmr.model.VKeyAttributeDomainModelReference#getKeyDMR
	 * <em>Key DMR</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Key DMR</em>' containment reference.
	 * @see #getKeyDMR()
	 * @generated
	 */
	void setKeyDMR(VDomainModelReference value);

	/**
	 * Returns the value of the '<em><b>Key Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Key Value</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Key Value</em>' attribute.
	 * @see #setKeyValue(Object)
	 * @see org.eclipse.emf.ecp.view.spi.keyattributedmr.model.VKeyattributedmrPackage#getKeyAttributeDomainModelReference_KeyValue()
	 * @model required="true"
	 * @generated
	 */
	Object getKeyValue();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.ecp.view.spi.keyattributedmr.model.VKeyAttributeDomainModelReference#getKeyValue
	 * <em>Key Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Key Value</em>' attribute.
	 * @see #getKeyValue()
	 * @generated
	 */
	void setKeyValue(Object value);

	/**
	 * Returns the value of the '<em><b>Value DMR</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value DMR</em>' containment reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Value DMR</em>' containment reference.
	 * @see #setValueDMR(VDomainModelReference)
	 * @see org.eclipse.emf.ecp.view.spi.keyattributedmr.model.VKeyattributedmrPackage#getKeyAttributeDomainModelReference_ValueDMR()
	 * @model containment="true" required="true"
	 * @generated
	 */
	VDomainModelReference getValueDMR();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.ecp.view.spi.keyattributedmr.model.VKeyAttributeDomainModelReference#getValueDMR
	 * <em>Value DMR</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Value DMR</em>' containment reference.
	 * @see #getValueDMR()
	 * @generated
	 */
	void setValueDMR(VDomainModelReference value);
} // VKeyAttributeDomainModelReference
