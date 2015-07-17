/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.style.fontProperties.model;

import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Style Property</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesStyleProperty#isItalic <em>
 * Italic</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesStyleProperty#isBold <em>Bold
 * </em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesStyleProperty#getColorHEX
 * <em>Color HEX</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesStyleProperty#getHeight <em>
 * Height</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesStyleProperty#getFontName
 * <em>Font Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesPackage#getFontPropertiesStyleProperty()
 * @model
 * @generated
 */
public interface VTFontPropertiesStyleProperty extends VTStyleProperty {
	/**
	 * Returns the value of the '<em><b>Italic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Italic</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Italic</em>' attribute.
	 * @see #setItalic(boolean)
	 * @see org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesPackage#getFontPropertiesStyleProperty_Italic()
	 * @model
	 * @generated
	 */
	boolean isItalic();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesStyleProperty#isItalic
	 * <em>Italic</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Italic</em>' attribute.
	 * @see #isItalic()
	 * @generated
	 */
	void setItalic(boolean value);

	/**
	 * Returns the value of the '<em><b>Bold</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bold</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Bold</em>' attribute.
	 * @see #setBold(boolean)
	 * @see org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesPackage#getFontPropertiesStyleProperty_Bold()
	 * @model
	 * @generated
	 */
	boolean isBold();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesStyleProperty#isBold
	 * <em>Bold</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Bold</em>' attribute.
	 * @see #isBold()
	 * @generated
	 */
	void setBold(boolean value);

	/**
	 * Returns the value of the '<em><b>Color HEX</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Color HEX</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Color HEX</em>' attribute.
	 * @see #setColorHEX(String)
	 * @see org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesPackage#getFontPropertiesStyleProperty_ColorHEX()
	 * @model
	 * @generated
	 */
	String getColorHEX();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesStyleProperty#getColorHEX
	 * <em>Color HEX</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Color HEX</em>' attribute.
	 * @see #getColorHEX()
	 * @generated
	 */
	void setColorHEX(String value);

	/**
	 * Returns the value of the '<em><b>Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Height</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Height</em>' attribute.
	 * @see #setHeight(int)
	 * @see org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesPackage#getFontPropertiesStyleProperty_Height()
	 * @model
	 * @generated
	 */
	int getHeight();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesStyleProperty#getHeight
	 * <em>Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Height</em>' attribute.
	 * @see #getHeight()
	 * @generated
	 */
	void setHeight(int value);

	/**
	 * Returns the value of the '<em><b>Font Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Font Name</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Font Name</em>' attribute.
	 * @see #setFontName(String)
	 * @see org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesPackage#getFontPropertiesStyleProperty_FontName()
	 * @model
	 * @generated
	 */
	String getFontName();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesStyleProperty#getFontName
	 * <em>Font Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Font Name</em>' attribute.
	 * @see #getFontName()
	 * @generated
	 */
	void setFontName(String value);

} // VTFontPropertiesStyleProperty
