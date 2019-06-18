/**
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 */
package org.eclipse.emfforms.spi.view.indexsegment.model;

import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Index
 * Domain Model Reference Segment</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emfforms.spi.view.indexsegment.model.VIndexDomainModelReferenceSegment#getIndex
 * <em>Index</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfforms.spi.view.indexsegment.model.VIndexsegmentPackage#getIndexDomainModelReferenceSegment()
 * @model
 * @generated
 */
public interface VIndexDomainModelReferenceSegment extends VFeatureDomainModelReferenceSegment {
	/**
	 * Returns the value of the '<em><b>Index</b></em>' attribute. The default value
	 * is <code>"0"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Index</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Index</em>' attribute.
	 * @see #setIndex(int)
	 * @see org.eclipse.emfforms.spi.view.indexsegment.model.VIndexsegmentPackage#getIndexDomainModelReferenceSegment_Index()
	 * @model default="0" required="true"
	 * @generated
	 */
	int getIndex();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.emfforms.spi.view.indexsegment.model.VIndexDomainModelReferenceSegment#getIndex
	 * <em>Index</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Index</em>' attribute.
	 * @see #getIndex()
	 * @generated
	 */
	void setIndex(int value);

} // VIndexDomainModelReferenceSegment
