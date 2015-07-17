/**
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Style Property</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.emf.ecp.view.template.model.VTTemplatePackage#getStyleProperty()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface VTStyleProperty extends EObject
{

	/**
	 * Compare this {@link VTStyleProperty} with another.
	 *
	 * @param styleProperty the {@link VTStyleProperty} to compare to
	 * @return true if both properties are equal
	 */
	boolean equalStyles(VTStyleProperty styleProperty);
} // VTStyleProperty
