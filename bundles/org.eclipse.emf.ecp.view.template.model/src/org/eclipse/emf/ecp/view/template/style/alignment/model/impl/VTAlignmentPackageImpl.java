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
package org.eclipse.emf.ecp.view.template.style.alignment.model.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecp.view.template.model.VTTemplatePackage;
import org.eclipse.emf.ecp.view.template.style.alignment.model.AlignmentType;
import org.eclipse.emf.ecp.view.template.style.alignment.model.VTAlignmentFactory;
import org.eclipse.emf.ecp.view.template.style.alignment.model.VTAlignmentPackage;
import org.eclipse.emf.ecp.view.template.style.alignment.model.VTAlignmentStyleProperty;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class VTAlignmentPackageImpl extends EPackageImpl implements VTAlignmentPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass alignmentStylePropertyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EEnum alignmentTypeEEnum = null;

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
	 * @see org.eclipse.emf.ecp.view.template.style.alignment.model.VTAlignmentPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private VTAlignmentPackageImpl() {
		super(eNS_URI, VTAlignmentFactory.eINSTANCE);
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
	 * This method is used to initialize {@link VTAlignmentPackage#eINSTANCE} when that field is accessed. Clients
	 * should not invoke it directly. Instead, they should simply access that field to obtain the package. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static VTAlignmentPackage init() {
		if (isInited) {
			return (VTAlignmentPackage) EPackage.Registry.INSTANCE.getEPackage(VTAlignmentPackage.eNS_URI);
		}

		// Obtain or create and register package
		final VTAlignmentPackageImpl theAlignmentPackage = (VTAlignmentPackageImpl) (EPackage.Registry.INSTANCE
			.get(eNS_URI) instanceof VTAlignmentPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
			: new VTAlignmentPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		VTTemplatePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theAlignmentPackage.createPackageContents();

		// Initialize created meta-data
		theAlignmentPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theAlignmentPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(VTAlignmentPackage.eNS_URI, theAlignmentPackage);
		return theAlignmentPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getAlignmentStyleProperty() {
		return alignmentStylePropertyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getAlignmentStyleProperty_Type() {
		return (EAttribute) alignmentStylePropertyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EEnum getAlignmentType() {
		return alignmentTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VTAlignmentFactory getAlignmentFactory() {
		return (VTAlignmentFactory) getEFactoryInstance();
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
		alignmentStylePropertyEClass = createEClass(ALIGNMENT_STYLE_PROPERTY);
		createEAttribute(alignmentStylePropertyEClass, ALIGNMENT_STYLE_PROPERTY__TYPE);

		// Create enums
		alignmentTypeEEnum = createEEnum(ALIGNMENT_TYPE);
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
		alignmentStylePropertyEClass.getESuperTypes().add(theTemplatePackage.getStyleProperty());

		// Initialize classes, features, and operations; add parameters
		initEClass(alignmentStylePropertyEClass, VTAlignmentStyleProperty.class,
			"AlignmentStyleProperty", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(
			getAlignmentStyleProperty_Type(),
			getAlignmentType(),
			"type", "LEFT", 1, 1, VTAlignmentStyleProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

		// Initialize enums and add enum literals
		initEEnum(alignmentTypeEEnum, AlignmentType.class, "AlignmentType"); //$NON-NLS-1$
		addEEnumLiteral(alignmentTypeEEnum, AlignmentType.LEFT);
		addEEnumLiteral(alignmentTypeEEnum, AlignmentType.RIGHT);

		// Create resource
		createResource(eNS_URI);
	}

} // VTAlignmentPackageImpl
