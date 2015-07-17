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
package org.eclipse.emf.ecp.view.spi.categorization.model;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Categorization</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.categorization.model.VCategorization#getCategorizations <em>Categorizations
 * </em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationPackage#getCategorization()
 * @model
 * @generated
 */
public interface VCategorization extends VAbstractCategorization
{
	/**
	 * Returns the value of the '<em><b>Categorizations</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecp.view.spi.categorization.model.VAbstractCategorization}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Categorizations</em>' containment reference list isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Categorizations</em>' containment reference list.
	 * @see org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationPackage#getCategorization_Categorizations()
	 * @model containment="true"
	 * @generated
	 */
	EList<VAbstractCategorization> getCategorizations();

} // VCategorization
