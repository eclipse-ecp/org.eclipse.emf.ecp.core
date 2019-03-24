/**
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.style.reference.model.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecp.view.template.model.VTTemplatePackage;
import org.eclipse.emf.ecp.view.template.style.reference.model.VTReferenceFactory;
import org.eclipse.emf.ecp.view.template.style.reference.model.VTReferencePackage;
import org.eclipse.emf.ecp.view.template.style.reference.model.VTReferenceStyleProperty;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class VTReferencePackageImpl extends EPackageImpl implements VTReferencePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass referenceStylePropertyEClass = null;

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
	 * @see org.eclipse.emf.ecp.view.template.style.reference.model.VTReferencePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private VTReferencePackageImpl() {
		super(eNS_URI, VTReferenceFactory.eINSTANCE);
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
	 * This method is used to initialize {@link VTReferencePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static VTReferencePackage init() {
		if (isInited) {
			return (VTReferencePackage) EPackage.Registry.INSTANCE.getEPackage(VTReferencePackage.eNS_URI);
		}

		// Obtain or create and register package
		final Object registeredReferencePackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		final VTReferencePackageImpl theReferencePackage = registeredReferencePackage instanceof VTReferencePackageImpl
			? (VTReferencePackageImpl) registeredReferencePackage
			: new VTReferencePackageImpl();

		isInited = true;

		// Initialize simple dependencies
		VTTemplatePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theReferencePackage.createPackageContents();

		// Initialize created meta-data
		theReferencePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theReferencePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(VTReferencePackage.eNS_URI, theReferencePackage);
		return theReferencePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getReferenceStyleProperty() {
		return referenceStylePropertyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getReferenceStyleProperty_ShowCreateAndLinkButtonForCrossReferences() {
		return (EAttribute) referenceStylePropertyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getReferenceStyleProperty_ShowLinkButtonForContainmentReferences() {
		return (EAttribute) referenceStylePropertyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VTReferenceFactory getReferenceFactory() {
		return (VTReferenceFactory) getEFactoryInstance();
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
		referenceStylePropertyEClass = createEClass(REFERENCE_STYLE_PROPERTY);
		createEAttribute(referenceStylePropertyEClass,
			REFERENCE_STYLE_PROPERTY__SHOW_CREATE_AND_LINK_BUTTON_FOR_CROSS_REFERENCES);
		createEAttribute(referenceStylePropertyEClass,
			REFERENCE_STYLE_PROPERTY__SHOW_LINK_BUTTON_FOR_CONTAINMENT_REFERENCES);
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
		referenceStylePropertyEClass.getESuperTypes().add(theTemplatePackage.getStyleProperty());

		// Initialize classes, features, and operations; add parameters
		initEClass(referenceStylePropertyEClass, VTReferenceStyleProperty.class, "ReferenceStyleProperty", !IS_ABSTRACT, //$NON-NLS-1$
			!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getReferenceStyleProperty_ShowCreateAndLinkButtonForCrossReferences(),
			ecorePackage.getEBoolean(), "showCreateAndLinkButtonForCrossReferences", "true", 0, 1, //$NON-NLS-1$ //$NON-NLS-2$
			VTReferenceStyleProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
			IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getReferenceStyleProperty_ShowLinkButtonForContainmentReferences(), ecorePackage.getEBoolean(),
			"showLinkButtonForContainmentReferences", "true", 0, 1, VTReferenceStyleProperty.class, !IS_TRANSIENT, //$NON-NLS-1$ //$NON-NLS-2$
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} // VTReferencePackageImpl
