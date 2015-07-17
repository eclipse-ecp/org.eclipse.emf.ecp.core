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
package org.eclipse.emf.ecp.view.template.style.tableValidation.model.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecp.view.template.model.VTTemplatePackage;
import org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationFactory;
import org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationPackage;
import org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationStyleProperty;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class VTTableValidationPackageImpl extends EPackageImpl implements VTTableValidationPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass tableValidationStylePropertyEClass = null;

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
	 * @see org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private VTTableValidationPackageImpl() {
		super(eNS_URI, VTTableValidationFactory.eINSTANCE);
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
	 * This method is used to initialize {@link VTTableValidationPackage#eINSTANCE} when that field is accessed. Clients
	 * should not invoke it directly. Instead, they should simply access that field to obtain the package. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static VTTableValidationPackage init() {
		if (isInited) {
			return (VTTableValidationPackage) EPackage.Registry.INSTANCE.getEPackage(VTTableValidationPackage.eNS_URI);
		}

		// Obtain or create and register package
		final VTTableValidationPackageImpl theTableValidationPackage = (VTTableValidationPackageImpl) (EPackage.Registry.INSTANCE
			.get(eNS_URI) instanceof VTTableValidationPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
			: new VTTableValidationPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		VTTemplatePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theTableValidationPackage.createPackageContents();

		// Initialize created meta-data
		theTableValidationPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theTableValidationPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(VTTableValidationPackage.eNS_URI, theTableValidationPackage);
		return theTableValidationPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getTableValidationStyleProperty() {
		return tableValidationStylePropertyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getTableValidationStyleProperty_ColumnWidth() {
		return (EAttribute) tableValidationStylePropertyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getTableValidationStyleProperty_ColumnName() {
		return (EAttribute) tableValidationStylePropertyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getTableValidationStyleProperty_ImagePath() {
		return (EAttribute) tableValidationStylePropertyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VTTableValidationFactory getTableValidationFactory() {
		return (VTTableValidationFactory) getEFactoryInstance();
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
		tableValidationStylePropertyEClass = createEClass(TABLE_VALIDATION_STYLE_PROPERTY);
		createEAttribute(tableValidationStylePropertyEClass, TABLE_VALIDATION_STYLE_PROPERTY__COLUMN_WIDTH);
		createEAttribute(tableValidationStylePropertyEClass, TABLE_VALIDATION_STYLE_PROPERTY__COLUMN_NAME);
		createEAttribute(tableValidationStylePropertyEClass, TABLE_VALIDATION_STYLE_PROPERTY__IMAGE_PATH);
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
		tableValidationStylePropertyEClass.getESuperTypes().add(theTemplatePackage.getStyleProperty());

		// Initialize classes, features, and operations; add parameters
		initEClass(tableValidationStylePropertyEClass, VTTableValidationStyleProperty.class,
			"TableValidationStyleProperty", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(
			getTableValidationStyleProperty_ColumnWidth(),
			ecorePackage.getEInt(),
			"columnWidth", null, 0, 1, VTTableValidationStyleProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getTableValidationStyleProperty_ColumnName(),
			ecorePackage.getEString(),
			"columnName", null, 0, 1, VTTableValidationStyleProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getTableValidationStyleProperty_ImagePath(),
			ecorePackage.getEString(),
			"imagePath", null, 0, 1, VTTableValidationStyleProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		// Create resource
		createResource(eNS_URI);
	}

} // VTTableValidationPackageImpl
