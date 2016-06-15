/**
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 * Lucas Koehler - refactoring to segments
 */
package org.eclipse.emf.ecp.view.spi.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>VDomain Model Reference</b></em>'.
 *
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.model.VDomainModelReference#getSegments <em>Segments</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.ecp.view.spi.model.VViewPackage#getDomainModelReference()
 * @model
 * @generated
 */
public interface VDomainModelReference extends EObject {
	/**
	 * Returns the value of the '<em><b>Segments</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Segments</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 *
	 * @since 2.0
	 *        <!-- end-user-doc -->
	 * @return the value of the '<em>Segments</em>' reference list.
	 * @see org.eclipse.emf.ecp.view.spi.model.VViewPackage#getDomainModelReference_Segments()
	 * @model required="true"
	 * @generated
	 */
	EList<VDomainModelReferenceSegment> getSegments();
} // VDomainModelReference
