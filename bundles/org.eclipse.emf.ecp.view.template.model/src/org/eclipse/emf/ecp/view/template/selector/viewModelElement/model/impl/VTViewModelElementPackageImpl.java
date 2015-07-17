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
package org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecp.view.template.model.VTTemplatePackage;
import org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.VTViewModelElementFactory;
import org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.VTViewModelElementPackage;
import org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.VTViewModelElementSelector;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class VTViewModelElementPackageImpl extends EPackageImpl implements VTViewModelElementPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass viewModelElementSelectorEClass = null;

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
	 * @see org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.VTViewModelElementPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private VTViewModelElementPackageImpl() {
		super(eNS_URI, VTViewModelElementFactory.eINSTANCE);
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
	 * This method is used to initialize {@link VTViewModelElementPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static VTViewModelElementPackage init() {
		if (isInited) {
			return (VTViewModelElementPackage) EPackage.Registry.INSTANCE
				.getEPackage(VTViewModelElementPackage.eNS_URI);
		}

		// Obtain or create and register package
		final VTViewModelElementPackageImpl theViewModelElementPackage = (VTViewModelElementPackageImpl) (EPackage.Registry.INSTANCE
			.get(eNS_URI) instanceof VTViewModelElementPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
			: new VTViewModelElementPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();
		VTTemplatePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theViewModelElementPackage.createPackageContents();

		// Initialize created meta-data
		theViewModelElementPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theViewModelElementPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(VTViewModelElementPackage.eNS_URI, theViewModelElementPackage);
		return theViewModelElementPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getViewModelElementSelector() {
		return viewModelElementSelectorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getViewModelElementSelector_ClassType() {
		return (EReference) viewModelElementSelectorEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getViewModelElementSelector_Attribute()
	{
		return (EReference) viewModelElementSelectorEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getViewModelElementSelector_AttributeValue()
	{
		return (EAttribute) viewModelElementSelectorEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getViewModelElementSelector_SelectSubclasses() {
		return (EAttribute) viewModelElementSelectorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VTViewModelElementFactory getViewModelElementFactory() {
		return (VTViewModelElementFactory) getEFactoryInstance();
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
		viewModelElementSelectorEClass = createEClass(VIEW_MODEL_ELEMENT_SELECTOR);
		createEAttribute(viewModelElementSelectorEClass, VIEW_MODEL_ELEMENT_SELECTOR__SELECT_SUBCLASSES);
		createEReference(viewModelElementSelectorEClass, VIEW_MODEL_ELEMENT_SELECTOR__CLASS_TYPE);
		createEReference(viewModelElementSelectorEClass, VIEW_MODEL_ELEMENT_SELECTOR__ATTRIBUTE);
		createEAttribute(viewModelElementSelectorEClass, VIEW_MODEL_ELEMENT_SELECTOR__ATTRIBUTE_VALUE);
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
		final EcorePackage theEcorePackage = (EcorePackage) EPackage.Registry.INSTANCE
			.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		viewModelElementSelectorEClass.getESuperTypes().add(theTemplatePackage.getStyleSelector());

		// Initialize classes, features, and operations; add parameters
		initEClass(viewModelElementSelectorEClass, VTViewModelElementSelector.class,
			"ViewModelElementSelector", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(
			getViewModelElementSelector_SelectSubclasses(),
			ecorePackage.getEBoolean(),
			"selectSubclasses", null, 0, 1, VTViewModelElementSelector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
			getViewModelElementSelector_ClassType(),
			theEcorePackage.getEClass(),
			null,
			"classType", null, 0, 1, VTViewModelElementSelector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
			getViewModelElementSelector_Attribute(),
			theEcorePackage.getEAttribute(),
			null,
			"attribute", null, 0, 1, VTViewModelElementSelector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getViewModelElementSelector_AttributeValue(),
			ecorePackage.getEJavaObject(),
			"attributeValue", null, 0, 1, VTViewModelElementSelector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		// Create resource
		createResource(eNS_URI);
	}

} // VTViewModelElementPackageImpl
