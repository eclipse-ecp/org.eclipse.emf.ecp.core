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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecp.view.template.model.VTTemplatePackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each operation of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesFactory
 * @model kind="package"
 * @generated
 */
public interface VTFontPropertiesPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "fontProperties"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/emf/ecp/view/template/style/fontProperties/model"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.ecp.view.template.style.fontProperties.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	VTFontPropertiesPackage eINSTANCE = org.eclipse.emf.ecp.view.template.style.fontProperties.model.impl.VTFontPropertiesPackageImpl
		.init();

	/**
	 * The meta object id for the '
	 * {@link org.eclipse.emf.ecp.view.template.style.fontProperties.model.impl.VTFontPropertiesStylePropertyImpl
	 * <em>Style Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.template.style.fontProperties.model.impl.VTFontPropertiesStylePropertyImpl
	 * @see org.eclipse.emf.ecp.view.template.style.fontProperties.model.impl.VTFontPropertiesPackageImpl#getFontPropertiesStyleProperty()
	 * @generated
	 */
	int FONT_PROPERTIES_STYLE_PROPERTY = 0;

	/**
	 * The feature id for the '<em><b>Italic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int FONT_PROPERTIES_STYLE_PROPERTY__ITALIC = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Bold</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int FONT_PROPERTIES_STYLE_PROPERTY__BOLD = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Color HEX</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int FONT_PROPERTIES_STYLE_PROPERTY__COLOR_HEX = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int FONT_PROPERTIES_STYLE_PROPERTY__HEIGHT = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Font Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int FONT_PROPERTIES_STYLE_PROPERTY__FONT_NAME = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Style Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int FONT_PROPERTIES_STYLE_PROPERTY_FEATURE_COUNT = VTTemplatePackage.STYLE_PROPERTY_FEATURE_COUNT + 5;

	/**
	 * The number of operations of the '<em>Style Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int FONT_PROPERTIES_STYLE_PROPERTY_OPERATION_COUNT = VTTemplatePackage.STYLE_PROPERTY_OPERATION_COUNT + 0;

	/**
	 * Returns the meta object for class '
	 * {@link org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesStyleProperty
	 * <em>Style Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Style Property</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesStyleProperty
	 * @generated
	 */
	EClass getFontPropertiesStyleProperty();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesStyleProperty#isItalic
	 * <em>Italic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Italic</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesStyleProperty#isItalic()
	 * @see #getFontPropertiesStyleProperty()
	 * @generated
	 */
	EAttribute getFontPropertiesStyleProperty_Italic();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesStyleProperty#isBold
	 * <em>Bold</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Bold</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesStyleProperty#isBold()
	 * @see #getFontPropertiesStyleProperty()
	 * @generated
	 */
	EAttribute getFontPropertiesStyleProperty_Bold();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesStyleProperty#getColorHEX
	 * <em>Color HEX</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Color HEX</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesStyleProperty#getColorHEX()
	 * @see #getFontPropertiesStyleProperty()
	 * @generated
	 */
	EAttribute getFontPropertiesStyleProperty_ColorHEX();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesStyleProperty#getHeight
	 * <em>Height</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Height</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesStyleProperty#getHeight()
	 * @see #getFontPropertiesStyleProperty()
	 * @generated
	 */
	EAttribute getFontPropertiesStyleProperty_Height();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesStyleProperty#getFontName
	 * <em>Font Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Font Name</em>'.
	 * @see org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesStyleProperty#getFontName()
	 * @see #getFontPropertiesStyleProperty()
	 * @generated
	 */
	EAttribute getFontPropertiesStyleProperty_FontName();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VTFontPropertiesFactory getFontPropertiesFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each operation of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.emf.ecp.view.template.style.fontProperties.model.impl.VTFontPropertiesStylePropertyImpl
		 * <em>Style Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.template.style.fontProperties.model.impl.VTFontPropertiesStylePropertyImpl
		 * @see org.eclipse.emf.ecp.view.template.style.fontProperties.model.impl.VTFontPropertiesPackageImpl#getFontPropertiesStyleProperty()
		 * @generated
		 */
		EClass FONT_PROPERTIES_STYLE_PROPERTY = eINSTANCE.getFontPropertiesStyleProperty();

		/**
		 * The meta object literal for the '<em><b>Italic</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute FONT_PROPERTIES_STYLE_PROPERTY__ITALIC = eINSTANCE.getFontPropertiesStyleProperty_Italic();

		/**
		 * The meta object literal for the '<em><b>Bold</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute FONT_PROPERTIES_STYLE_PROPERTY__BOLD = eINSTANCE.getFontPropertiesStyleProperty_Bold();

		/**
		 * The meta object literal for the '<em><b>Color HEX</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute FONT_PROPERTIES_STYLE_PROPERTY__COLOR_HEX = eINSTANCE.getFontPropertiesStyleProperty_ColorHEX();

		/**
		 * The meta object literal for the '<em><b>Height</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute FONT_PROPERTIES_STYLE_PROPERTY__HEIGHT = eINSTANCE.getFontPropertiesStyleProperty_Height();

		/**
		 * The meta object literal for the '<em><b>Font Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute FONT_PROPERTIES_STYLE_PROPERTY__FONT_NAME = eINSTANCE.getFontPropertiesStyleProperty_FontName();

	}

} // VTFontPropertiesPackage
