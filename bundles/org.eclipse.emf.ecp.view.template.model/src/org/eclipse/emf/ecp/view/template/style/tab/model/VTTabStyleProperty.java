/**
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.style.tab.model;

import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Style Property</b></em>'.
 *
 * @since 1.8
 *        <!-- end-user-doc -->
 *
 *        <p>
 *        The following features are supported:
 *        </p>
 *        <ul>
 *        <li>{@link org.eclipse.emf.ecp.view.template.style.tab.model.VTTabStyleProperty#getType <em>Type</em>}</li>
 *        <li>{@link org.eclipse.emf.ecp.view.template.style.tab.model.VTTabStyleProperty#getOkImageURL <em>Ok Image
 *        URL</em>}</li>
 *        <li>{@link org.eclipse.emf.ecp.view.template.style.tab.model.VTTabStyleProperty#getInfoImageURL <em>Info Image
 *        URL</em>}</li>
 *        <li>{@link org.eclipse.emf.ecp.view.template.style.tab.model.VTTabStyleProperty#getWarningImageURL <em>Warning
 *        Image URL</em>}</li>
 *        <li>{@link org.eclipse.emf.ecp.view.template.style.tab.model.VTTabStyleProperty#getErrorImageURL <em>Error
 *        Image URL</em>}</li>
 *        <li>{@link org.eclipse.emf.ecp.view.template.style.tab.model.VTTabStyleProperty#getCancelImageURL <em>Cancel
 *        Image URL</em>}</li>
 *        </ul>
 *
 * @see org.eclipse.emf.ecp.view.template.style.tab.model.VTTabPackage#getTabStyleProperty()
 * @model
 * @generated
 */
public interface VTTabStyleProperty extends VTStyleProperty {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The default value is <code>"BOTTOM"</code>.
	 * The literals are from the enumeration {@link org.eclipse.emf.ecp.view.template.style.tab.model.TabType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see org.eclipse.emf.ecp.view.template.style.tab.model.TabType
	 * @see #setType(TabType)
	 * @see org.eclipse.emf.ecp.view.template.style.tab.model.VTTabPackage#getTabStyleProperty_Type()
	 * @model default="BOTTOM" required="true"
	 * @generated
	 */
	TabType getType();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.template.style.tab.model.VTTabStyleProperty#getType
	 * <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see org.eclipse.emf.ecp.view.template.style.tab.model.TabType
	 * @see #getType()
	 * @generated
	 */
	void setType(TabType value);

	/**
	 * Returns the value of the '<em><b>Ok Image URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ok Image URL</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 *
	 * @since 1.10
	 *        <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Ok Image URL</em>' attribute.
	 * @see #setOkImageURL(String)
	 * @see org.eclipse.emf.ecp.view.template.style.tab.model.VTTabPackage#getTabStyleProperty_OkImageURL()
	 * @model
	 * @generated
	 */
	String getOkImageURL();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.template.style.tab.model.VTTabStyleProperty#getOkImageURL
	 * <em>Ok Image URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.10
	 *        <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Ok Image URL</em>' attribute.
	 * @see #getOkImageURL()
	 * @generated
	 */
	void setOkImageURL(String value);

	/**
	 * Returns the value of the '<em><b>Info Image URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Info Image URL</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 *
	 * @since 1.10
	 *        <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Info Image URL</em>' attribute.
	 * @see #setInfoImageURL(String)
	 * @see org.eclipse.emf.ecp.view.template.style.tab.model.VTTabPackage#getTabStyleProperty_InfoImageURL()
	 * @model
	 * @generated
	 */
	String getInfoImageURL();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.emf.ecp.view.template.style.tab.model.VTTabStyleProperty#getInfoImageURL <em>Info Image
	 * URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.10
	 *        <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Info Image URL</em>' attribute.
	 * @see #getInfoImageURL()
	 * @generated
	 */
	void setInfoImageURL(String value);

	/**
	 * Returns the value of the '<em><b>Warning Image URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Warning Image URL</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 *
	 * @since 1.10
	 *        <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Warning Image URL</em>' attribute.
	 * @see #setWarningImageURL(String)
	 * @see org.eclipse.emf.ecp.view.template.style.tab.model.VTTabPackage#getTabStyleProperty_WarningImageURL()
	 * @model
	 * @generated
	 */
	String getWarningImageURL();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.emf.ecp.view.template.style.tab.model.VTTabStyleProperty#getWarningImageURL <em>Warning Image
	 * URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * 
	 * @since 1.10
	 *        <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Warning Image URL</em>' attribute.
	 * @see #getWarningImageURL()
	 * @generated
	 */
	void setWarningImageURL(String value);

	/**
	 * Returns the value of the '<em><b>Error Image URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Error Image URL</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 *
	 * @since 1.10
	 *        <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Error Image URL</em>' attribute.
	 * @see #setErrorImageURL(String)
	 * @see org.eclipse.emf.ecp.view.template.style.tab.model.VTTabPackage#getTabStyleProperty_ErrorImageURL()
	 * @model
	 * @generated
	 */
	String getErrorImageURL();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.emf.ecp.view.template.style.tab.model.VTTabStyleProperty#getErrorImageURL <em>Error Image
	 * URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.10
	 *        <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Error Image URL</em>' attribute.
	 * @see #getErrorImageURL()
	 * @generated
	 */
	void setErrorImageURL(String value);

	/**
	 * Returns the value of the '<em><b>Cancel Image URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cancel Image URL</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 *
	 * @since 1.10
	 *        <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Cancel Image URL</em>' attribute.
	 * @see #setCancelImageURL(String)
	 * @see org.eclipse.emf.ecp.view.template.style.tab.model.VTTabPackage#getTabStyleProperty_CancelImageURL()
	 * @model
	 * @generated
	 */
	String getCancelImageURL();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.emf.ecp.view.template.style.tab.model.VTTabStyleProperty#getCancelImageURL <em>Cancel Image
	 * URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.10
	 *        <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Cancel Image URL</em>' attribute.
	 * @see #getCancelImageURL()
	 * @generated
	 */
	void setCancelImageURL(String value);

} // VTTabStyleProperty
