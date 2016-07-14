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

import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Index Domain Model Reference Segment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexDomainModelReferenceSegment#getIndex
 * <em>Index</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexdmrPackage#getIndexDomainModelReferenceSegment()
 * @model
 * @generated
 */
public interface VIndexDomainModelReferenceSegment extends VFeatureDomainModelReferenceSegment {
	/**
	 * Returns the value of the '<em><b>Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Index</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Index</em>' attribute.
	 * @see #setIndex(int)
	 * @see org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexdmrPackage#getIndexDomainModelReferenceSegment_Index()
	 * @model
	 * @generated
	 */
	int getIndex();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexDomainModelReferenceSegment#getIndex <em>Index</em>}'
	 * attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Index</em>' attribute.
	 * @see #getIndex()
	 * @generated
	 */
	void setIndex(int value);

} // VIndexDomainModelReferenceSegment
