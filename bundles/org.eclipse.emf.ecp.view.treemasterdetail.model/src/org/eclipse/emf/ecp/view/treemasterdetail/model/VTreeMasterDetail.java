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
package org.eclipse.emf.ecp.view.treemasterdetail.model;

import org.eclipse.emf.ecp.view.spi.model.VContainedElement;
import org.eclipse.emf.ecp.view.spi.model.VView;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Tree Master Detail</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.treemasterdetail.model.VTreeMasterDetail#getDetailView <em>Detail View</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.ecp.view.treemasterdetail.model.VTreeMasterDetailPackage#getTreeMasterDetail()
 * @model
 * @generated
 */
public interface VTreeMasterDetail extends VContainedElement
{
	/**
	 * Returns the value of the '<em><b>Detail View</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Detail View</em>' reference isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Detail View</em>' containment reference.
	 * @see #setDetailView(VView)
	 * @see org.eclipse.emf.ecp.view.treemasterdetail.model.VTreeMasterDetailPackage#getTreeMasterDetail_DetailView()
	 * @model containment="true"
	 * @generated
	 */
	VView getDetailView();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.treemasterdetail.model.VTreeMasterDetail#getDetailView
	 * <em>Detail View</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Detail View</em>' containment reference.
	 * @see #getDetailView()
	 * @generated
	 */
	void setDetailView(VView value);

} // VTreeMasterDetail
