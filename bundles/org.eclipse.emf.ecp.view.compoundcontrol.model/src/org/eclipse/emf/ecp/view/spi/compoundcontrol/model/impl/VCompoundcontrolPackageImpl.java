/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.compoundcontrol.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecp.view.spi.compoundcontrol.model.VCompoundControl;
import org.eclipse.emf.ecp.view.spi.compoundcontrol.model.VCompoundcontrolFactory;
import org.eclipse.emf.ecp.view.spi.compoundcontrol.model.VCompoundcontrolPackage;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class VCompoundcontrolPackageImpl extends EPackageImpl implements VCompoundcontrolPackage
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass compoundControlEClass = null;

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
	 * @see org.eclipse.emf.ecp.view.spi.compoundcontrol.model.VCompoundcontrolPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private VCompoundcontrolPackageImpl()
	{
		super(eNS_URI, VCompoundcontrolFactory.eINSTANCE);
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
	 * This method is used to initialize {@link VCompoundcontrolPackage#eINSTANCE} when that field is accessed. Clients
	 * should not invoke it directly. Instead, they should simply access that field to obtain the package. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static VCompoundcontrolPackage init()
	{
		if (isInited) {
			return (VCompoundcontrolPackage) EPackage.Registry.INSTANCE.getEPackage(VCompoundcontrolPackage.eNS_URI);
		}

		// Obtain or create and register package
		final VCompoundcontrolPackageImpl theCompoundcontrolPackage = (VCompoundcontrolPackageImpl) (EPackage.Registry.INSTANCE
			.get(eNS_URI) instanceof VCompoundcontrolPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
			: new VCompoundcontrolPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		VViewPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theCompoundcontrolPackage.createPackageContents();

		// Initialize created meta-data
		theCompoundcontrolPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theCompoundcontrolPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(VCompoundcontrolPackage.eNS_URI, theCompoundcontrolPackage);
		return theCompoundcontrolPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getCompoundControl()
	{
		return compoundControlEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getCompoundControl_Controls()
	{
		return (EReference) compoundControlEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VCompoundcontrolFactory getCompoundcontrolFactory()
	{
		return (VCompoundcontrolFactory) getEFactoryInstance();
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
		compoundControlEClass = createEClass(COMPOUND_CONTROL);
		createEReference(compoundControlEClass, COMPOUND_CONTROL__CONTROLS);
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
		final VViewPackage theViewPackage = (VViewPackage) EPackage.Registry.INSTANCE.getEPackage(VViewPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		compoundControlEClass.getESuperTypes().add(theViewPackage.getContainedElement());

		// Initialize classes and features; add operations and parameters
		initEClass(compoundControlEClass, VCompoundControl.class,
			"CompoundControl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
			getCompoundControl_Controls(),
			theViewPackage.getControl(),
			null,
			"controls", null, 1, -1, VCompoundControl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		// Create resource
		createResource(eNS_URI);
	}

} // VCompoundcontrolPackageImpl
