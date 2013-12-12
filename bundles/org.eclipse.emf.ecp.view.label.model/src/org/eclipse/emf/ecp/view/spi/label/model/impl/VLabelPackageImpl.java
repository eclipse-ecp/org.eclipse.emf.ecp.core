/**
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.label.model.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecp.view.spi.label.model.VLabel;
import org.eclipse.emf.ecp.view.spi.label.model.VLabelFactory;
import org.eclipse.emf.ecp.view.spi.label.model.VLabelPackage;
import org.eclipse.emf.ecp.view.spi.label.model.VLabelStyle;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class VLabelPackageImpl extends EPackageImpl implements VLabelPackage
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass labelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum vLabelStyleEEnum = null;

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
	 * @see org.eclipse.emf.ecp.view.spi.label.model.VLabelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private VLabelPackageImpl()
	{
		super(eNS_URI, VLabelFactory.eINSTANCE);
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
	 * This method is used to initialize {@link VLabelPackage#eINSTANCE} when that field is accessed. Clients should not
	 * invoke it directly. Instead, they should simply access that field to obtain the package. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static VLabelPackage init()
	{
		if (isInited)
			return (VLabelPackage) EPackage.Registry.INSTANCE.getEPackage(VLabelPackage.eNS_URI);

		// Obtain or create and register package
		VLabelPackageImpl theLabelPackage = (VLabelPackageImpl) (EPackage.Registry.INSTANCE.get(eNS_URI) instanceof VLabelPackageImpl ? EPackage.Registry.INSTANCE
			.get(eNS_URI) : new VLabelPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		VViewPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theLabelPackage.createPackageContents();

		// Initialize created meta-data
		theLabelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theLabelPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(VLabelPackage.eNS_URI, theLabelPackage);
		return theLabelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getLabel()
	{
		return labelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getLabel_Style()
	{
		return (EAttribute) labelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EEnum getVLabelStyle()
	{
		return vLabelStyleEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public VLabelFactory getLabelFactory()
	{
		return (VLabelFactory) getEFactoryInstance();
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
	public void createPackageContents()
	{
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		labelEClass = createEClass(LABEL);
		createEAttribute(labelEClass, LABEL__STYLE);

		// Create enums
		vLabelStyleEEnum = createEEnum(VLABEL_STYLE);
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
	public void initializePackageContents()
	{
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		VViewPackage theViewPackage = (VViewPackage) EPackage.Registry.INSTANCE.getEPackage(VViewPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		labelEClass.getESuperTypes().add(theViewPackage.getContainedElement());

		// Initialize classes and features; add operations and parameters
		initEClass(labelEClass, VLabel.class, "Label", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(
			getLabel_Style(),
			this.getVLabelStyle(),
			"style", null, 0, 1, VLabel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		// Initialize enums and add enum literals
		initEEnum(vLabelStyleEEnum, VLabelStyle.class, "VLabelStyle"); //$NON-NLS-1$
		addEEnumLiteral(vLabelStyleEEnum, VLabelStyle.H0);
		addEEnumLiteral(vLabelStyleEEnum, VLabelStyle.H1);
		addEEnumLiteral(vLabelStyleEEnum, VLabelStyle.H2);
		addEEnumLiteral(vLabelStyleEEnum, VLabelStyle.H3);
		addEEnumLiteral(vLabelStyleEEnum, VLabelStyle.H4);
		addEEnumLiteral(vLabelStyleEEnum, VLabelStyle.H5);
		addEEnumLiteral(vLabelStyleEEnum, VLabelStyle.H6);
		addEEnumLiteral(vLabelStyleEEnum, VLabelStyle.H7);
		addEEnumLiteral(vLabelStyleEEnum, VLabelStyle.H8);
		addEEnumLiteral(vLabelStyleEEnum, VLabelStyle.H9);
		addEEnumLiteral(vLabelStyleEEnum, VLabelStyle.SEPARATOR);

		// Create resource
		createResource(eNS_URI);
	}

} // VLabelPackageImpl
