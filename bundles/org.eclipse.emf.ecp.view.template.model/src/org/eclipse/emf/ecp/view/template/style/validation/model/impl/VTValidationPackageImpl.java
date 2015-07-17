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
package org.eclipse.emf.ecp.view.template.style.validation.model.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecp.view.template.model.VTTemplatePackage;
import org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationFactory;
import org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationPackage;
import org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class VTValidationPackageImpl extends EPackageImpl implements VTValidationPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass validationStylePropertyEClass = null;

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
	 * @see org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private VTValidationPackageImpl() {
		super(eNS_URI, VTValidationFactory.eINSTANCE);
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
	 * This method is used to initialize {@link VTValidationPackage#eINSTANCE} when that field is accessed. Clients
	 * should not invoke it directly. Instead, they should simply access that field to obtain the package. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static VTValidationPackage init() {
		if (isInited) {
			return (VTValidationPackage) EPackage.Registry.INSTANCE.getEPackage(VTValidationPackage.eNS_URI);
		}

		// Obtain or create and register package
		final VTValidationPackageImpl theValidationPackage = (VTValidationPackageImpl) (EPackage.Registry.INSTANCE
			.get(eNS_URI) instanceof VTValidationPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
			: new VTValidationPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		VTTemplatePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theValidationPackage.createPackageContents();

		// Initialize created meta-data
		theValidationPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theValidationPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(VTValidationPackage.eNS_URI, theValidationPackage);
		return theValidationPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getValidationStyleProperty() {
		return validationStylePropertyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getValidationStyleProperty_OkColorHEX() {
		return (EAttribute) validationStylePropertyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getValidationStyleProperty_OkImageURL() {
		return (EAttribute) validationStylePropertyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getValidationStyleProperty_OkOverlayURL() {
		return (EAttribute) validationStylePropertyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getValidationStyleProperty_InfoColorHEX() {
		return (EAttribute) validationStylePropertyEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getValidationStyleProperty_InfoImageURL() {
		return (EAttribute) validationStylePropertyEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getValidationStyleProperty_InfoOverlayURL() {
		return (EAttribute) validationStylePropertyEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getValidationStyleProperty_WarningColorHEX() {
		return (EAttribute) validationStylePropertyEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getValidationStyleProperty_WarningImageURL() {
		return (EAttribute) validationStylePropertyEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getValidationStyleProperty_WarningOverlayURL() {
		return (EAttribute) validationStylePropertyEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getValidationStyleProperty_ErrorColorHEX() {
		return (EAttribute) validationStylePropertyEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getValidationStyleProperty_ErrorImageURL() {
		return (EAttribute) validationStylePropertyEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getValidationStyleProperty_ErrorOverlayURL() {
		return (EAttribute) validationStylePropertyEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getValidationStyleProperty_CancelColorHEX() {
		return (EAttribute) validationStylePropertyEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getValidationStyleProperty_CancelImageURL() {
		return (EAttribute) validationStylePropertyEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getValidationStyleProperty_CancelOverlayURL() {
		return (EAttribute) validationStylePropertyEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VTValidationFactory getValidationFactory() {
		return (VTValidationFactory) getEFactoryInstance();
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
		validationStylePropertyEClass = createEClass(VALIDATION_STYLE_PROPERTY);
		createEAttribute(validationStylePropertyEClass, VALIDATION_STYLE_PROPERTY__OK_COLOR_HEX);
		createEAttribute(validationStylePropertyEClass, VALIDATION_STYLE_PROPERTY__OK_IMAGE_URL);
		createEAttribute(validationStylePropertyEClass, VALIDATION_STYLE_PROPERTY__OK_OVERLAY_URL);
		createEAttribute(validationStylePropertyEClass, VALIDATION_STYLE_PROPERTY__INFO_COLOR_HEX);
		createEAttribute(validationStylePropertyEClass, VALIDATION_STYLE_PROPERTY__INFO_IMAGE_URL);
		createEAttribute(validationStylePropertyEClass, VALIDATION_STYLE_PROPERTY__INFO_OVERLAY_URL);
		createEAttribute(validationStylePropertyEClass, VALIDATION_STYLE_PROPERTY__WARNING_COLOR_HEX);
		createEAttribute(validationStylePropertyEClass, VALIDATION_STYLE_PROPERTY__WARNING_IMAGE_URL);
		createEAttribute(validationStylePropertyEClass, VALIDATION_STYLE_PROPERTY__WARNING_OVERLAY_URL);
		createEAttribute(validationStylePropertyEClass, VALIDATION_STYLE_PROPERTY__ERROR_COLOR_HEX);
		createEAttribute(validationStylePropertyEClass, VALIDATION_STYLE_PROPERTY__ERROR_IMAGE_URL);
		createEAttribute(validationStylePropertyEClass, VALIDATION_STYLE_PROPERTY__ERROR_OVERLAY_URL);
		createEAttribute(validationStylePropertyEClass, VALIDATION_STYLE_PROPERTY__CANCEL_COLOR_HEX);
		createEAttribute(validationStylePropertyEClass, VALIDATION_STYLE_PROPERTY__CANCEL_IMAGE_URL);
		createEAttribute(validationStylePropertyEClass, VALIDATION_STYLE_PROPERTY__CANCEL_OVERLAY_URL);
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
		validationStylePropertyEClass.getESuperTypes().add(theTemplatePackage.getStyleProperty());

		// Initialize classes, features, and operations; add parameters
		initEClass(validationStylePropertyEClass, VTValidationStyleProperty.class,
			"ValidationStyleProperty", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(
			getValidationStyleProperty_OkColorHEX(),
			ecorePackage.getEString(),
			"okColorHEX", null, 0, 1, VTValidationStyleProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getValidationStyleProperty_OkImageURL(),
			ecorePackage.getEString(),
			"okImageURL", null, 0, 1, VTValidationStyleProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getValidationStyleProperty_OkOverlayURL(),
			ecorePackage.getEString(),
			"okOverlayURL", null, 0, 1, VTValidationStyleProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getValidationStyleProperty_InfoColorHEX(),
			ecorePackage.getEString(),
			"infoColorHEX", null, 0, 1, VTValidationStyleProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getValidationStyleProperty_InfoImageURL(),
			ecorePackage.getEString(),
			"infoImageURL", null, 0, 1, VTValidationStyleProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getValidationStyleProperty_InfoOverlayURL(),
			ecorePackage.getEString(),
			"infoOverlayURL", null, 0, 1, VTValidationStyleProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getValidationStyleProperty_WarningColorHEX(),
			ecorePackage.getEString(),
			"warningColorHEX", null, 0, 1, VTValidationStyleProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getValidationStyleProperty_WarningImageURL(),
			ecorePackage.getEString(),
			"warningImageURL", null, 0, 1, VTValidationStyleProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getValidationStyleProperty_WarningOverlayURL(),
			ecorePackage.getEString(),
			"warningOverlayURL", null, 0, 1, VTValidationStyleProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getValidationStyleProperty_ErrorColorHEX(),
			ecorePackage.getEString(),
			"errorColorHEX", null, 0, 1, VTValidationStyleProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getValidationStyleProperty_ErrorImageURL(),
			ecorePackage.getEString(),
			"errorImageURL", null, 0, 1, VTValidationStyleProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getValidationStyleProperty_ErrorOverlayURL(),
			ecorePackage.getEString(),
			"errorOverlayURL", null, 0, 1, VTValidationStyleProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getValidationStyleProperty_CancelColorHEX(),
			ecorePackage.getEString(),
			"cancelColorHEX", null, 0, 1, VTValidationStyleProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getValidationStyleProperty_CancelImageURL(),
			ecorePackage.getEString(),
			"cancelImageURL", null, 0, 1, VTValidationStyleProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getValidationStyleProperty_CancelOverlayURL(),
			ecorePackage.getEString(),
			"cancelOverlayURL", null, 0, 1, VTValidationStyleProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		// Create resource
		createResource(eNS_URI);
	}

} // VTValidationPackageImpl
