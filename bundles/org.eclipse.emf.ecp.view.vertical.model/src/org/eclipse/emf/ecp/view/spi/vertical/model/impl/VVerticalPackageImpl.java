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
package org.eclipse.emf.ecp.view.spi.vertical.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.vertical.model.VVerticalFactory;
import org.eclipse.emf.ecp.view.spi.vertical.model.VVerticalLayout;
import org.eclipse.emf.ecp.view.spi.vertical.model.VVerticalPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 *
 * @since 1.2
 *        <!-- end-user-doc -->
 *
 * @generated
 */
public class VVerticalPackageImpl extends EPackageImpl implements VVerticalPackage
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass verticalLayoutEClass = null;

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
	 * @see org.eclipse.emf.ecp.view.spi.vertical.model.VVerticalPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private VVerticalPackageImpl()
	{
		super(eNS_URI, VVerticalFactory.eINSTANCE);
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
	 * This method is used to initialize {@link VVerticalPackage#eINSTANCE} when that field is accessed. Clients should
	 * not invoke it directly. Instead, they should simply access that field to obtain the package. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static VVerticalPackage init()
	{
		if (isInited) {
			return (VVerticalPackage) EPackage.Registry.INSTANCE.getEPackage(VVerticalPackage.eNS_URI);
		}

		// Obtain or create and register package
		final VVerticalPackageImpl theVerticalPackage = (VVerticalPackageImpl) (EPackage.Registry.INSTANCE.get(eNS_URI) instanceof VVerticalPackageImpl ? EPackage.Registry.INSTANCE
			.get(eNS_URI)
			: new VVerticalPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		VViewPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theVerticalPackage.createPackageContents();

		// Initialize created meta-data
		theVerticalPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theVerticalPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(VVerticalPackage.eNS_URI, theVerticalPackage);
		return theVerticalPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getVerticalLayout()
	{
		return verticalLayoutEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VVerticalFactory getVerticalFactory()
	{
		return (VVerticalFactory) getEFactoryInstance();
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
		verticalLayoutEClass = createEClass(VERTICAL_LAYOUT);
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
		verticalLayoutEClass.getESuperTypes().add(theViewPackage.getContainedContainer());

		// Initialize classes and features; add operations and parameters
		initEClass(verticalLayoutEClass, VVerticalLayout.class,
			"VerticalLayout", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		// Create resource
		createResource(eNS_URI);
	}

} // VVerticalPackageImpl
