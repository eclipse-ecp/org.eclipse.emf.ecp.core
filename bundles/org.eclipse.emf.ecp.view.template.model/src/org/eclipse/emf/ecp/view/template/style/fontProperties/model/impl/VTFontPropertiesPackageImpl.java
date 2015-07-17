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
package org.eclipse.emf.ecp.view.template.style.fontProperties.model.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecp.view.template.model.VTTemplatePackage;
import org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesFactory;
import org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesPackage;
import org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesStyleProperty;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class VTFontPropertiesPackageImpl extends EPackageImpl implements VTFontPropertiesPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass fontPropertiesStylePropertyEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with {@link org.eclipse.emf.ecore.EPackage.Registry
	 * EPackage.Registry} by the package
	 * package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory method {@link #init init()}, which also
	 * performs initialization of the package, or returns the registered package, if one already exists. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private VTFontPropertiesPackageImpl() {
		super(eNS_URI, VTFontPropertiesFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>
	 * This method is used to initialize {@link VTFontPropertiesPackage#eINSTANCE} when that field is accessed. Clients
	 * should not invoke it directly. Instead, they should simply access that field to obtain the package. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static VTFontPropertiesPackage init() {
		if (isInited) {
			return (VTFontPropertiesPackage) EPackage.Registry.INSTANCE.getEPackage(VTFontPropertiesPackage.eNS_URI);
		}

		// Obtain or create and register package
		final VTFontPropertiesPackageImpl theFontPropertiesPackage = (VTFontPropertiesPackageImpl) (EPackage.Registry.INSTANCE
			.get(eNS_URI) instanceof VTFontPropertiesPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
			: new VTFontPropertiesPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		VTTemplatePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theFontPropertiesPackage.createPackageContents();

		// Initialize created meta-data
		theFontPropertiesPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theFontPropertiesPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(VTFontPropertiesPackage.eNS_URI, theFontPropertiesPackage);
		return theFontPropertiesPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getFontPropertiesStyleProperty() {
		return fontPropertiesStylePropertyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getFontPropertiesStyleProperty_Italic() {
		return (EAttribute) fontPropertiesStylePropertyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getFontPropertiesStyleProperty_Bold() {
		return (EAttribute) fontPropertiesStylePropertyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getFontPropertiesStyleProperty_ColorHEX() {
		return (EAttribute) fontPropertiesStylePropertyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getFontPropertiesStyleProperty_Height() {
		return (EAttribute) fontPropertiesStylePropertyEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getFontPropertiesStyleProperty_FontName() {
		return (EAttribute) fontPropertiesStylePropertyEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VTFontPropertiesFactory getFontPropertiesFactory() {
		return (VTFontPropertiesFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package. This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) {
			return;
		}
		isCreated = true;

		// Create classes and their features
		fontPropertiesStylePropertyEClass = createEClass(FONT_PROPERTIES_STYLE_PROPERTY);
		createEAttribute(fontPropertiesStylePropertyEClass, FONT_PROPERTIES_STYLE_PROPERTY__ITALIC);
		createEAttribute(fontPropertiesStylePropertyEClass, FONT_PROPERTIES_STYLE_PROPERTY__BOLD);
		createEAttribute(fontPropertiesStylePropertyEClass, FONT_PROPERTIES_STYLE_PROPERTY__COLOR_HEX);
		createEAttribute(fontPropertiesStylePropertyEClass, FONT_PROPERTIES_STYLE_PROPERTY__HEIGHT);
		createEAttribute(fontPropertiesStylePropertyEClass, FONT_PROPERTIES_STYLE_PROPERTY__FONT_NAME);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model. This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) {
			return;
		}
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		final VTTemplatePackage theTemplatePackage = (VTTemplatePackage) EPackage.Registry.INSTANCE
			.getEPackage(VTTemplatePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		fontPropertiesStylePropertyEClass.getESuperTypes().add(theTemplatePackage.getStyleProperty());

		// Initialize classes, features, and operations; add parameters
		initEClass(fontPropertiesStylePropertyEClass, VTFontPropertiesStyleProperty.class,
			"FontPropertiesStyleProperty", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(
			getFontPropertiesStyleProperty_Italic(),
			ecorePackage.getEBoolean(),
			"italic", null, 0, 1, VTFontPropertiesStyleProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getFontPropertiesStyleProperty_Bold(),
			ecorePackage.getEBoolean(),
			"bold", null, 0, 1, VTFontPropertiesStyleProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getFontPropertiesStyleProperty_ColorHEX(),
			ecorePackage.getEString(),
			"colorHEX", null, 0, 1, VTFontPropertiesStyleProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getFontPropertiesStyleProperty_Height(),
			ecorePackage.getEInt(),
			"height", null, 0, 1, VTFontPropertiesStyleProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getFontPropertiesStyleProperty_FontName(),
			ecorePackage.getEString(),
			"fontName", null, 0, 1, VTFontPropertiesStyleProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		// Create resource
		createResource(eNS_URI);
	}

} // VTFontPropertiesPackageImpl
