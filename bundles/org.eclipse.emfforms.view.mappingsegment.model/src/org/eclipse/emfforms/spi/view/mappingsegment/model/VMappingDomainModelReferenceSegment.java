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
package org.eclipse.emfforms.spi.view.mappingsegment.model;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Mapping Domain Model Reference Segment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emfforms.spi.view.mappingsegment.model.VMappingDomainModelReferenceSegment#getMappedClass
 * <em>Mapped Class</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfforms.spi.view.mappingsegment.model.VMappingsegmentPackage#getMappingDomainModelReferenceSegment()
 * @model
 * @generated
 */
public interface VMappingDomainModelReferenceSegment extends VFeatureDomainModelReferenceSegment {
	/**
	 * Returns the value of the '<em><b>Mapped Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mapped Class</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Mapped Class</em>' reference.
	 * @see #setMappedClass(EClass)
	 * @see org.eclipse.emfforms.spi.view.mappingsegment.model.VMappingsegmentPackage#getMappingDomainModelReferenceSegment_MappedClass()
	 * @model required="true"
	 * @generated
	 */
	EClass getMappedClass();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.emfforms.spi.view.mappingsegment.model.VMappingDomainModelReferenceSegment#getMappedClass
	 * <em>Mapped Class</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Mapped Class</em>' reference.
	 * @see #getMappedClass()
	 * @generated
	 */
	void setMappedClass(EClass value);

} // VMappingDomainModelReferenceSegment
