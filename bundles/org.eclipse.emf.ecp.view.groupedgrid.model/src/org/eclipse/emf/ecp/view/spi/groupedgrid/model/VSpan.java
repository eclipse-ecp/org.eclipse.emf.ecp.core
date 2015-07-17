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
package org.eclipse.emf.ecp.view.spi.groupedgrid.model;

import org.eclipse.emf.ecp.view.spi.model.VAttachment;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Span</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.groupedgrid.model.VSpan#getHorizontalSpan <em>Horizontal Span</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.ecp.view.spi.groupedgrid.model.VGroupedGridPackage#getSpan()
 * @model
 * @generated
 * @since 1.2
 */
public interface VSpan extends VAttachment
{
	/**
	 * Returns the value of the '<em><b>Horizontal Span</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Horizontal Span</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Horizontal Span</em>' attribute.
	 * @see #setHorizontalSpan(int)
	 * @see org.eclipse.emf.ecp.view.spi.groupedgrid.model.VGroupedGridPackage#getSpan_HorizontalSpan()
	 * @model required="true"
	 * @generated
	 */
	int getHorizontalSpan();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.groupedgrid.model.VSpan#getHorizontalSpan
	 * <em>Horizontal Span</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Horizontal Span</em>' attribute.
	 * @see #getHorizontalSpan()
	 * @generated
	 */
	void setHorizontalSpan(int value);

} // VSpan
