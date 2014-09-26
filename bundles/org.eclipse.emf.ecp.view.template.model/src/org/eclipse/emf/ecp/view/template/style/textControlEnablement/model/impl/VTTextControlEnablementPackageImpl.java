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
package org.eclipse.emf.ecp.view.template.style.textControlEnablement.model.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecp.view.template.model.VTTemplatePackage;
import org.eclipse.emf.ecp.view.template.style.textControlEnablement.model.VTTextControlEnablementFactory;
import org.eclipse.emf.ecp.view.template.style.textControlEnablement.model.VTTextControlEnablementPackage;
import org.eclipse.emf.ecp.view.template.style.textControlEnablement.model.VTTextControlEnablementStyleProperty;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class VTTextControlEnablementPackageImpl extends EPackageImpl implements VTTextControlEnablementPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass textControlEnablementStylePropertyEClass = null;

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
	 * @see org.eclipse.emf.ecp.view.template.style.textControlEnablement.model.VTTextControlEnablementPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private VTTextControlEnablementPackageImpl() {
		super(eNS_URI, VTTextControlEnablementFactory.eINSTANCE);
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
	 * This method is used to initialize {@link VTTextControlEnablementPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static VTTextControlEnablementPackage init() {
		if (isInited) {
			return (VTTextControlEnablementPackage) EPackage.Registry.INSTANCE
				.getEPackage(VTTextControlEnablementPackage.eNS_URI);
		}

		// Obtain or create and register package
		final VTTextControlEnablementPackageImpl theTextControlEnablementPackage = (VTTextControlEnablementPackageImpl) (EPackage.Registry.INSTANCE
			.get(eNS_URI) instanceof VTTextControlEnablementPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
			: new VTTextControlEnablementPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		VTTemplatePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theTextControlEnablementPackage.createPackageContents();

		// Initialize created meta-data
		theTextControlEnablementPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theTextControlEnablementPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(VTTextControlEnablementPackage.eNS_URI, theTextControlEnablementPackage);
		return theTextControlEnablementPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getTextControlEnablementStyleProperty() {
		return textControlEnablementStylePropertyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getTextControlEnablementStyleProperty_RenderDisableAsEditable() {
		return (EAttribute) textControlEnablementStylePropertyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public VTTextControlEnablementFactory getTextControlEnablementFactory() {
		return (VTTextControlEnablementFactory) getEFactoryInstance();
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
		textControlEnablementStylePropertyEClass = createEClass(TEXT_CONTROL_ENABLEMENT_STYLE_PROPERTY);
		createEAttribute(textControlEnablementStylePropertyEClass,
			TEXT_CONTROL_ENABLEMENT_STYLE_PROPERTY__RENDER_DISABLE_AS_EDITABLE);
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
		textControlEnablementStylePropertyEClass.getESuperTypes().add(theTemplatePackage.getStyleProperty());

		// Initialize classes and features; add operations and parameters
		initEClass(textControlEnablementStylePropertyEClass, VTTextControlEnablementStyleProperty.class,
			"TextControlEnablementStyleProperty", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(
			getTextControlEnablementStyleProperty_RenderDisableAsEditable(),
			ecorePackage.getEBoolean(),
			"RenderDisableAsEditable", "false", 0, 1, VTTextControlEnablementStyleProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

		// Create resource
		createResource(eNS_URI);
	}

} // VTTextControlEnablementPackageImpl
