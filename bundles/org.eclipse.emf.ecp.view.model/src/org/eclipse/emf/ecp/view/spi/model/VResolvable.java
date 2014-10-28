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
package org.eclipse.emf.ecp.view.spi.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Resolvable</b></em>'.
 * 
 * @since 1.5
 *        <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.emf.ecp.view.spi.model.VViewPackage#getResolvable()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface VResolvable extends EObject
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @model kind="operation" ordered="false"
	 * @generated
	 */
	EList<VDomainModelReference> getDomainModelReferencesToResolve();

} // VResolvable
