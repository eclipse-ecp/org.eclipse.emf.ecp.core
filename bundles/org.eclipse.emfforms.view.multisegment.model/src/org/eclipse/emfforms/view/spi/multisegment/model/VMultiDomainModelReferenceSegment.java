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
package org.eclipse.emfforms.view.spi.multisegment.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Multi Domain Model Reference Segment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emfforms.view.spi.multisegment.model.VMultiDomainModelReferenceSegment#getChildDomainModelReferences
 * <em>Child Domain Model References</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfforms.view.spi.multisegment.model.VMultisegmentPackage#getMultiDomainModelReferenceSegment()
 * @model
 * @generated
 */
public interface VMultiDomainModelReferenceSegment extends VFeatureDomainModelReferenceSegment {
	/**
	 * Returns the value of the '<em><b>Child Domain Model References</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecp.view.spi.model.VDomainModelReference}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Child Domain Model References</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Child Domain Model References</em>' containment reference list.
	 * @see org.eclipse.emfforms.view.spi.multisegment.model.VMultisegmentPackage#getMultiDomainModelReferenceSegment_ChildDomainModelReferences()
	 * @model containment="true"
	 * @generated
	 */
	EList<VDomainModelReference> getChildDomainModelReferences();

} // VMultiDomainModelReferenceSegment
