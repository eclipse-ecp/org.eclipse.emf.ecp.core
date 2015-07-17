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
package org.eclipse.emf.ecp.view.template.style.mandatory.model.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecp.view.template.model.VTTemplatePackage;
import org.eclipse.emf.ecp.view.template.style.mandatory.model.VTMandatoryFactory;
import org.eclipse.emf.ecp.view.template.style.mandatory.model.VTMandatoryPackage;
import org.eclipse.emf.ecp.view.template.style.mandatory.model.VTMandatoryStyleProperty;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class VTMandatoryPackageImpl extends EPackageImpl implements VTMandatoryPackage
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass mandatoryStylePropertyEClass = null;

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
	 * @see org.eclipse.emf.ecp.view.template.style.mandatory.model.VTMandatoryPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private VTMandatoryPackageImpl()
	{
		super(eNS_URI, VTMandatoryFactory.eINSTANCE);
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
	 * This method is used to initialize {@link VTMandatoryPackage#eINSTANCE} when that field is accessed. Clients
	 * should not invoke it directly. Instead, they should simply access that field to obtain the package. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static VTMandatoryPackage init()
	{
		if (isInited) {
			return (VTMandatoryPackage) EPackage.Registry.INSTANCE.getEPackage(VTMandatoryPackage.eNS_URI);
		}

		// Obtain or create and register package
		final VTMandatoryPackageImpl theMandatoryPackage = (VTMandatoryPackageImpl) (EPackage.Registry.INSTANCE
			.get(eNS_URI) instanceof VTMandatoryPackageImpl ? EPackage.Registry.INSTANCE
			.get(eNS_URI) : new VTMandatoryPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		VTTemplatePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theMandatoryPackage.createPackageContents();

		// Initialize created meta-data
		theMandatoryPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theMandatoryPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(VTMandatoryPackage.eNS_URI, theMandatoryPackage);
		return theMandatoryPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getMandatoryStyleProperty()
	{
		return mandatoryStylePropertyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getMandatoryStyleProperty_HighliteMandatoryFields()
	{
		return (EAttribute) mandatoryStylePropertyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getMandatoryStyleProperty_MandatoryMarker()
	{
		return (EAttribute) mandatoryStylePropertyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VTMandatoryFactory getMandatoryFactory()
	{
		return (VTMandatoryFactory) getEFactoryInstance();
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
		if (isCreated) {
			return;
		}
		isCreated = true;

		// Create classes and their features
		mandatoryStylePropertyEClass = createEClass(MANDATORY_STYLE_PROPERTY);
		createEAttribute(mandatoryStylePropertyEClass, MANDATORY_STYLE_PROPERTY__HIGHLITE_MANDATORY_FIELDS);
		createEAttribute(mandatoryStylePropertyEClass, MANDATORY_STYLE_PROPERTY__MANDATORY_MARKER);
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
		mandatoryStylePropertyEClass.getESuperTypes().add(theTemplatePackage.getStyleProperty());

		// Initialize classes, features, and operations; add parameters
		initEClass(mandatoryStylePropertyEClass, VTMandatoryStyleProperty.class,
			"MandatoryStyleProperty", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(
			getMandatoryStyleProperty_HighliteMandatoryFields(),
			ecorePackage.getEBoolean(),
			"highliteMandatoryFields", "true", 0, 1, VTMandatoryStyleProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(
			getMandatoryStyleProperty_MandatoryMarker(),
			ecorePackage.getEString(),
			"mandatoryMarker", "*", 0, 1, VTMandatoryStyleProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

		// Create resource
		createResource(eNS_URI);
	}

} // VTMandatoryPackageImpl
