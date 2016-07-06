/**
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.model;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Feature Domain Model Reference Segment</b></em>'.
 *
 * @since 2.0
 *        <!-- end-user-doc -->
 *
 *        <p>
 *        The following features are supported:
 *        </p>
 *        <ul>
 *        <li>{@link org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment#getDomainModelFeature
 *        <em>Domain Model Feature</em>}</li>
 *        </ul>
 *
 * @see org.eclipse.emf.ecp.view.spi.model.VViewPackage#getFeatureDomainModelReferenceSegment()
 * @model
 * @generated
 */
public interface VFeatureDomainModelReferenceSegment extends VDomainModelReferenceSegment {
	/**
	 * Returns the value of the '<em><b>Domain Model Feature</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Domain Model Feature</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Domain Model Feature</em>' attribute.
	 * @see #setDomainModelFeature(String)
	 * @see org.eclipse.emf.ecp.view.spi.model.VViewPackage#getFeatureDomainModelReferenceSegment_DomainModelFeature()
	 * @model required="true"
	 * @generated
	 */
	String getDomainModelFeature();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment#getDomainModelFeature <em>Domain
	 * Model Feature</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Domain Model Feature</em>' attribute.
	 * @see #getDomainModelFeature()
	 * @generated
	 */
	void setDomainModelFeature(String value);

} // VFeatureDomainModelReferenceSegment
