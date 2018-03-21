/**
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.style.labelwidth.model.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecp.view.template.model.VTTemplatePackage;
import org.eclipse.emf.ecp.view.template.style.labelwidth.model.VTLabelWidthStyleProperty;
import org.eclipse.emf.ecp.view.template.style.labelwidth.model.VTLabelwidthFactory;
import org.eclipse.emf.ecp.view.template.style.labelwidth.model.VTLabelwidthPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class VTLabelwidthPackageImpl extends EPackageImpl implements VTLabelwidthPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass labelWidthStylePropertyEClass = null;

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
	 * @see org.eclipse.emf.ecp.view.template.style.labelwidth.model.VTLabelwidthPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private VTLabelwidthPackageImpl() {
		super(eNS_URI, VTLabelwidthFactory.eINSTANCE);
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
	 * This method is used to initialize {@link VTLabelwidthPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static VTLabelwidthPackage init() {
		if (isInited) {
			return (VTLabelwidthPackage) EPackage.Registry.INSTANCE.getEPackage(VTLabelwidthPackage.eNS_URI);
		}

		// Obtain or create and register package
		final VTLabelwidthPackageImpl theLabelwidthPackage = (VTLabelwidthPackageImpl) (EPackage.Registry.INSTANCE
			.get(eNS_URI) instanceof VTLabelwidthPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
				: new VTLabelwidthPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		VTTemplatePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theLabelwidthPackage.createPackageContents();

		// Initialize created meta-data
		theLabelwidthPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theLabelwidthPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(VTLabelwidthPackage.eNS_URI, theLabelwidthPackage);
		return theLabelwidthPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getLabelWidthStyleProperty() {
		return labelWidthStylePropertyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getLabelWidthStyleProperty_Width() {
		return (EAttribute) labelWidthStylePropertyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VTLabelwidthFactory getLabelwidthFactory() {
		return (VTLabelwidthFactory) getEFactoryInstance();
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
		labelWidthStylePropertyEClass = createEClass(LABEL_WIDTH_STYLE_PROPERTY);
		createEAttribute(labelWidthStylePropertyEClass, LABEL_WIDTH_STYLE_PROPERTY__WIDTH);
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
		labelWidthStylePropertyEClass.getESuperTypes().add(theTemplatePackage.getStyleProperty());

		// Initialize classes, features, and operations; add parameters
		initEClass(labelWidthStylePropertyEClass, VTLabelWidthStyleProperty.class, "LabelWidthStyleProperty", //$NON-NLS-1$
			!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLabelWidthStyleProperty_Width(), ecorePackage.getEInt(), "width", null, 1, 1, //$NON-NLS-1$
			VTLabelWidthStyleProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID,
			IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} // VTLabelwidthPackageImpl
