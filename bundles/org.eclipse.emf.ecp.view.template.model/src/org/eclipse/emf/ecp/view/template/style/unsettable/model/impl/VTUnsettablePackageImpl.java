/**
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.style.unsettable.model.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecp.view.template.model.VTTemplatePackage;
import org.eclipse.emf.ecp.view.template.style.unsettable.model.ButtonAlignmentType;
import org.eclipse.emf.ecp.view.template.style.unsettable.model.ButtonPlacementType;
import org.eclipse.emf.ecp.view.template.style.unsettable.model.VTUnsettableFactory;
import org.eclipse.emf.ecp.view.template.style.unsettable.model.VTUnsettablePackage;
import org.eclipse.emf.ecp.view.template.style.unsettable.model.VTUnsettableStyleProperty;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class VTUnsettablePackageImpl extends EPackageImpl implements VTUnsettablePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass unsettableStylePropertyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum buttonAlignmentTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum buttonPlacementTypeEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.emf.ecp.view.template.style.unsettable.model.VTUnsettablePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private VTUnsettablePackageImpl() {
		super(eNS_URI, VTUnsettableFactory.eINSTANCE);
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
	 * This method is used to initialize {@link VTUnsettablePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static VTUnsettablePackage init() {
		if (isInited) {
			return (VTUnsettablePackage) EPackage.Registry.INSTANCE.getEPackage(VTUnsettablePackage.eNS_URI);
		}

		// Obtain or create and register package
		final VTUnsettablePackageImpl theUnsettablePackage = (VTUnsettablePackageImpl) (EPackage.Registry.INSTANCE
			.get(eNS_URI) instanceof VTUnsettablePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
				: new VTUnsettablePackageImpl());

		isInited = true;

		// Initialize simple dependencies
		VTTemplatePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theUnsettablePackage.createPackageContents();

		// Initialize created meta-data
		theUnsettablePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theUnsettablePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(VTUnsettablePackage.eNS_URI, theUnsettablePackage);
		return theUnsettablePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getUnsettableStyleProperty() {
		return unsettableStylePropertyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getUnsettableStyleProperty_ButtonAlignment() {
		return (EAttribute) unsettableStylePropertyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getUnsettableStyleProperty_ButtonPlacement() {
		return (EAttribute) unsettableStylePropertyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EEnum getButtonAlignmentType() {
		return buttonAlignmentTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EEnum getButtonPlacementType() {
		return buttonPlacementTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public VTUnsettableFactory getUnsettableFactory() {
		return (VTUnsettableFactory) getEFactoryInstance();
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
		unsettableStylePropertyEClass = createEClass(UNSETTABLE_STYLE_PROPERTY);
		createEAttribute(unsettableStylePropertyEClass, UNSETTABLE_STYLE_PROPERTY__BUTTON_ALIGNMENT);
		createEAttribute(unsettableStylePropertyEClass, UNSETTABLE_STYLE_PROPERTY__BUTTON_PLACEMENT);

		// Create enums
		buttonAlignmentTypeEEnum = createEEnum(BUTTON_ALIGNMENT_TYPE);
		buttonPlacementTypeEEnum = createEEnum(BUTTON_PLACEMENT_TYPE);
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
		unsettableStylePropertyEClass.getESuperTypes().add(theTemplatePackage.getStyleProperty());

		// Initialize classes, features, and operations; add parameters
		initEClass(unsettableStylePropertyEClass, VTUnsettableStyleProperty.class, "UnsettableStyleProperty", //$NON-NLS-1$
			!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getUnsettableStyleProperty_ButtonAlignment(), getButtonAlignmentType(), "buttonAlignment", //$NON-NLS-1$
			"RIGHT", 1, 1, VTUnsettableStyleProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, //$NON-NLS-1$
			!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUnsettableStyleProperty_ButtonPlacement(), getButtonPlacementType(), "buttonPlacement", //$NON-NLS-1$
			"RIGHT_OF_LABEL", 1, 1, VTUnsettableStyleProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, //$NON-NLS-1$
			!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(buttonAlignmentTypeEEnum, ButtonAlignmentType.class, "ButtonAlignmentType"); //$NON-NLS-1$
		addEEnumLiteral(buttonAlignmentTypeEEnum, ButtonAlignmentType.RIGHT);
		addEEnumLiteral(buttonAlignmentTypeEEnum, ButtonAlignmentType.LEFT);

		initEEnum(buttonPlacementTypeEEnum, ButtonPlacementType.class, "ButtonPlacementType"); //$NON-NLS-1$
		addEEnumLiteral(buttonPlacementTypeEEnum, ButtonPlacementType.RIGHT_OF_LABEL);
		addEEnumLiteral(buttonPlacementTypeEEnum, ButtonPlacementType.LEFT_OF_LABEL);

		// Create resource
		createResource(eNS_URI);
	}

} // VTUnsettablePackageImpl
