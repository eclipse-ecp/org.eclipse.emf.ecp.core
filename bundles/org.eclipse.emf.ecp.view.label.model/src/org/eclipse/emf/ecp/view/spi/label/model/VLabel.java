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
package org.eclipse.emf.ecp.view.spi.label.model;

import org.eclipse.emf.ecp.view.spi.model.VContainedElement;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VResolvable;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Label</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.label.model.VLabel#getStyle <em>Style</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.spi.label.model.VLabel#getDomainModelReference <em>Domain Model Reference</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.ecp.view.spi.label.model.VLabelPackage#getLabel()
 * @model
 * @generated
 */
public interface VLabel extends VContainedElement, VResolvable
{
	/**
	 * Returns the value of the '<em><b>Style</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.emf.ecp.view.spi.label.model.VLabelStyle}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Style</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Style</em>' attribute.
	 * @see org.eclipse.emf.ecp.view.spi.label.model.VLabelStyle
	 * @see #setStyle(VLabelStyle)
	 * @see org.eclipse.emf.ecp.view.spi.label.model.VLabelPackage#getLabel_Style()
	 * @model
	 * @generated
	 */
	VLabelStyle getStyle();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.label.model.VLabel#getStyle <em>Style</em>}'
	 * attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Style</em>' attribute.
	 * @see org.eclipse.emf.ecp.view.spi.label.model.VLabelStyle
	 * @see #getStyle()
	 * @generated
	 */
	void setStyle(VLabelStyle value);

	/**
	 * Returns the value of the '<em><b>Domain Model Reference</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Domain Model Reference</em>' containment reference isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 *
	 * @since 1.4
	 *        <!-- end-user-doc -->
	 * @return the value of the '<em>Domain Model Reference</em>' containment reference.
	 * @see #setDomainModelReference(VDomainModelReference)
	 * @see org.eclipse.emf.ecp.view.spi.label.model.VLabelPackage#getLabel_DomainModelReference()
	 * @model containment="true"
	 * @generated
	 */
	VDomainModelReference getDomainModelReference();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.label.model.VLabel#getDomainModelReference
	 * <em>Domain Model Reference</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.4
	 *        <!-- end-user-doc -->
	 * @param value the new value of the '<em>Domain Model Reference</em>' containment reference.
	 * @see #getDomainModelReference()
	 * @generated
	 */
	void setDomainModelReference(VDomainModelReference value);

} // VLabel
