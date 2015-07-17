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
package org.eclipse.emf.ecp.view.spi.indexdmr.model;

import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Index Domain Model Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexDomainModelReference#getPrefixDMR <em>Prefix DMR</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexDomainModelReference#getIndex <em>Index</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexDomainModelReference#getTargetDMR <em>Target DMR</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexdmrPackage#getIndexDomainModelReference()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='resolveable'"
 * @generated
 */
public interface VIndexDomainModelReference extends VFeaturePathDomainModelReference
{
	/**
	 * Returns the value of the '<em><b>Prefix DMR</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Prefix DMR</em>' containment reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * 
	 * @since 1.6
	 *        <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Prefix DMR</em>' containment reference.
	 * @see #setPrefixDMR(VDomainModelReference)
	 * @see org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexdmrPackage#getIndexDomainModelReference_PrefixDMR()
	 * @model containment="true" required="true"
	 * @generated
	 */
	VDomainModelReference getPrefixDMR();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexDomainModelReference#getPrefixDMR <em>Prefix DMR</em>}'
	 * containment reference.
	 * <!-- begin-user-doc -->
	 * 
	 * @since 1.6
	 *        <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Prefix DMR</em>' containment reference.
	 * @see #getPrefixDMR()
	 * @generated
	 */
	void setPrefixDMR(VDomainModelReference value);

	/**
	 * Returns the value of the '<em><b>Target DMR</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target DMR</em>' containment reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Target DMR</em>' containment reference.
	 * @see #setTargetDMR(VDomainModelReference)
	 * @see org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexdmrPackage#getIndexDomainModelReference_TargetDMR()
	 * @model containment="true" required="true"
	 * @generated
	 */
	VDomainModelReference getTargetDMR();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexDomainModelReference#getTargetDMR <em>Target DMR</em>}'
	 * containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Target DMR</em>' containment reference.
	 * @see #getTargetDMR()
	 * @generated
	 */
	void setTargetDMR(VDomainModelReference value);

	/**
	 * Returns the value of the '<em><b>Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Index</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Index</em>' attribute.
	 * @see #setIndex(int)
	 * @see org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexdmrPackage#getIndexDomainModelReference_Index()
	 * @model
	 * @generated
	 */
	int getIndex();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexDomainModelReference#getIndex
	 * <em>Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Index</em>' attribute.
	 * @see #getIndex()
	 * @generated
	 */
	void setIndex(int value);

} // VIndexDomainModelReference
