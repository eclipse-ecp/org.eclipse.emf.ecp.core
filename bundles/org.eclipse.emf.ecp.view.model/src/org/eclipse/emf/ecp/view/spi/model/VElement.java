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
 * A representation of the model object '<em><b>Renderable</b></em>'.
 *
 * @since 1.2
 * @noimplement This interface is not intended to be implemented by clients.
 *              <!-- end-user-doc -->
 *
 *              <p>
 *              The following features are supported:
 *              </p>
 *              <ul>
 *              <li>{@link org.eclipse.emf.ecp.view.spi.model.VElement#getName <em>Name</em>}</li>
 *              <li>{@link org.eclipse.emf.ecp.view.spi.model.VElement#getLabel <em>Label</em>}</li>
 *              <li>{@link org.eclipse.emf.ecp.view.spi.model.VElement#isVisible <em>Visible</em>}</li>
 *              <li>{@link org.eclipse.emf.ecp.view.spi.model.VElement#isEnabled <em>Enabled</em>}</li>
 *              <li>{@link org.eclipse.emf.ecp.view.spi.model.VElement#isReadonly <em>Readonly</em>}</li>
 *              <li>{@link org.eclipse.emf.ecp.view.spi.model.VElement#getDiagnostic <em>Diagnostic</em>}</li>
 *              <li>{@link org.eclipse.emf.ecp.view.spi.model.VElement#getAttachments <em>Attachments</em>}</li>
 *              <li>{@link org.eclipse.emf.ecp.view.spi.model.VElement#getUuid <em>Uuid</em>}</li>
 *              </ul>
 *
 * @see org.eclipse.emf.ecp.view.spi.model.VViewPackage#getElement()
 * @model abstract="true"
 * @generated
 */
public interface VElement extends EObject {

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.emf.ecp.view.spi.model.VViewPackage#getElement_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.model.VElement#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Label</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 *
	 * @since 1.6
	 *        <!-- end-user-doc -->
	 * @return the value of the '<em>Label</em>' attribute.
	 * @see #setLabel(String)
	 * @see org.eclipse.emf.ecp.view.spi.model.VViewPackage#getElement_Label()
	 * @model transient="true"
	 * @generated
	 */
	String getLabel();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.model.VElement#getLabel <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.6
	 *        <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label</em>' attribute.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(String value);

	/**
	 * Returns the value of the '<em><b>Visible</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Visible</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Visible</em>' attribute.
	 * @see #setVisible(boolean)
	 * @see org.eclipse.emf.ecp.view.spi.model.VViewPackage#getElement_Visible()
	 * @model default="true" transient="true"
	 * @generated
	 */
	boolean isVisible();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.model.VElement#isVisible <em>Visible</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Visible</em>' attribute.
	 * @see #isVisible()
	 * @generated
	 */
	void setVisible(boolean value);

	/**
	 * Returns the value of the '<em><b>Enabled</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enabled</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Enabled</em>' attribute.
	 * @see #setEnabled(boolean)
	 * @see org.eclipse.emf.ecp.view.spi.model.VViewPackage#getElement_Enabled()
	 * @model default="true" transient="true"
	 * @generated
	 */
	boolean isEnabled();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.model.VElement#isEnabled <em>Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Enabled</em>' attribute.
	 * @see #isEnabled()
	 * @generated
	 */
	void setEnabled(boolean value);

	/**
	 * Returns the value of the '<em><b>Readonly</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Readonly</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Readonly</em>' attribute.
	 * @see #setReadonly(boolean)
	 * @see org.eclipse.emf.ecp.view.spi.model.VViewPackage#getElement_Readonly()
	 * @model default="false"
	 * @generated
	 */
	boolean isReadonly();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.model.VElement#isReadonly <em>Readonly</em>}'
	 * attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Readonly</em>' attribute.
	 * @see #isReadonly()
	 * @generated
	 */
	void setReadonly(boolean value);

	/**
	 * Returns the value of the '<em><b>Diagnostic</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Diagnostic</em>' containment reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Diagnostic</em>' containment reference.
	 * @see #setDiagnostic(VDiagnostic)
	 * @see org.eclipse.emf.ecp.view.spi.model.VViewPackage#getElement_Diagnostic()
	 * @model containment="true" transient="true"
	 * @generated
	 */
	VDiagnostic getDiagnostic();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.model.VElement#getDiagnostic <em>Diagnostic</em>}'
	 * containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Diagnostic</em>' containment reference.
	 * @see #getDiagnostic()
	 * @generated
	 */
	void setDiagnostic(VDiagnostic value);

	/**
	 * Returns the value of the '<em><b>Attachments</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecp.view.spi.model.VAttachment}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attachments</em>' containment reference list isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Attachments</em>' containment reference list.
	 * @see org.eclipse.emf.ecp.view.spi.model.VViewPackage#getElement_Attachments()
	 * @model containment="true"
	 * @generated
	 */
	EList<VAttachment> getAttachments();

	/**
	 * Returns the value of the '<em><b>Uuid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Uuid</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 *
	 * @since 1.9
	 *        <!-- end-user-doc -->
	 * @return the value of the '<em>Uuid</em>' attribute.
	 * @see #setUuid(String)
	 * @see org.eclipse.emf.ecp.view.spi.model.VViewPackage#getElement_Uuid()
	 * @model transient="true"
	 * @generated
	 */
	String getUuid();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.model.VElement#getUuid <em>Uuid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.9
	 *        <!-- end-user-doc -->
	 * @param value the new value of the '<em>Uuid</em>' attribute.
	 * @see #getUuid()
	 * @generated
	 */
	void setUuid(String value);
} // Renderable
