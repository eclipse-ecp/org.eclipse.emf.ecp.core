/**
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 */
package org.eclipse.emfforms.spi.view.mappingsegment.model;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecp.view.spi.model.VDMRSegment;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>DMR Mapping Segment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emfforms.spi.view.mappingsegment.model.VDMRMappingSegment#getMappedClass <em>Mapped Class
 * </em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emfforms.spi.view.mappingsegment.model.VMappingsegmentPackage#getDMRMappingSegment()
 * @model
 * @generated
 */
public interface VDMRMappingSegment extends VDMRSegment
{
	/**
	 * Returns the value of the '<em><b>Mapped Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mapped Class</em>' reference isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Mapped Class</em>' reference.
	 * @see #setMappedClass(EClass)
	 * @see org.eclipse.emfforms.spi.view.mappingsegment.model.VMappingsegmentPackage#getDMRMappingSegment_MappedClass()
	 * @model
	 * @generated
	 */
	EClass getMappedClass();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emfforms.spi.view.mappingsegment.model.VDMRMappingSegment#getMappedClass
	 * <em>Mapped Class</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Mapped Class</em>' reference.
	 * @see #getMappedClass()
	 * @generated
	 */
	void setMappedClass(EClass value);

} // VDMRMappingSegment
