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
package org.eclipse.emf.ecp.view.spi.group.model;

import org.eclipse.emf.ecp.view.spi.model.VContainedContainer;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Group</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.group.model.VGroup#getGroupType <em>Group Type</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.spi.group.model.VGroup#getLabelAlignment <em>Label Alignment</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.spi.group.model.VGroup#isCollapsed <em>Collapsed</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.ecp.view.spi.group.model.VGroupPackage#getGroup()
 * @model
 * @generated
 */
public interface VGroup extends VContainedContainer
{

	/**
	 * Returns the value of the '<em><b>Group Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.emf.ecp.view.spi.group.model.GroupType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Group Type</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 *
	 * @since 1.4
	 *        <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Group Type</em>' attribute.
	 * @see org.eclipse.emf.ecp.view.spi.group.model.GroupType
	 * @see #setGroupType(GroupType)
	 * @see org.eclipse.emf.ecp.view.spi.group.model.VGroupPackage#getGroup_GroupType()
	 * @model required="true"
	 * @generated
	 */
	GroupType getGroupType();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.group.model.VGroup#getGroupType <em>Group Type</em>}'
	 * attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.4
	 *        <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Group Type</em>' attribute.
	 * @see org.eclipse.emf.ecp.view.spi.group.model.GroupType
	 * @see #getGroupType()
	 * @generated
	 */
	void setGroupType(GroupType value);

	/**
	 * Returns the value of the '<em><b>Label Alignment</b></em>' attribute.
	 * The default value is <code>"LabelAligned"</code>.
	 * The literals are from the enumeration {@link org.eclipse.emf.ecp.view.spi.group.model.GroupLabelAlignment}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Label Alignment</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Label Alignment</em>' attribute.
	 * @see org.eclipse.emf.ecp.view.spi.group.model.GroupLabelAlignment
	 * @see #setLabelAlignment(GroupLabelAlignment)
	 * @see org.eclipse.emf.ecp.view.spi.group.model.VGroupPackage#getGroup_LabelAlignment()
	 * @model default="LabelAligned"
	 * @generated
	 */
	GroupLabelAlignment getLabelAlignment();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.group.model.VGroup#getLabelAlignment
	 * <em>Label Alignment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Label Alignment</em>' attribute.
	 * @see org.eclipse.emf.ecp.view.spi.group.model.GroupLabelAlignment
	 * @see #getLabelAlignment()
	 * @generated
	 */
	void setLabelAlignment(GroupLabelAlignment value);

	/**
	 * Returns the value of the '<em><b>Collapsed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Collapsed</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 *
	 * @since 1.4
	 *        <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Collapsed</em>' attribute.
	 * @see #setCollapsed(boolean)
	 * @see org.eclipse.emf.ecp.view.spi.group.model.VGroupPackage#getGroup_Collapsed()
	 * @model
	 * @generated
	 */
	boolean isCollapsed();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.group.model.VGroup#isCollapsed <em>Collapsed</em>}'
	 * attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.4
	 *        <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Collapsed</em>' attribute.
	 * @see #isCollapsed()
	 * @generated
	 */
	void setCollapsed(boolean value);
} // VGroup
