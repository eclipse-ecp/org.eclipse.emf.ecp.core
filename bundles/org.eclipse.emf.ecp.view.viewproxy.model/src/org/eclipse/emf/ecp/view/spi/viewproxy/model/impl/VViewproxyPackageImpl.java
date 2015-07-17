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
package org.eclipse.emf.ecp.view.spi.viewproxy.model.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.viewproxy.model.VViewProxy;
import org.eclipse.emf.ecp.view.spi.viewproxy.model.VViewproxyFactory;
import org.eclipse.emf.ecp.view.spi.viewproxy.model.VViewproxyPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class VViewproxyPackageImpl extends EPackageImpl implements VViewproxyPackage
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass viewProxyEClass = null;

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
	 * @see org.eclipse.emf.ecp.view.spi.viewproxy.model.VViewproxyPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private VViewproxyPackageImpl()
	{
		super(eNS_URI, VViewproxyFactory.eINSTANCE);
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
	 * This method is used to initialize {@link VViewproxyPackage#eINSTANCE} when that field is accessed. Clients should
	 * not invoke it directly. Instead, they should simply access that field to obtain the package. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static VViewproxyPackage init()
	{
		if (isInited) {
			return (VViewproxyPackage) EPackage.Registry.INSTANCE.getEPackage(VViewproxyPackage.eNS_URI);
		}

		// Obtain or create and register package
		final VViewproxyPackageImpl theViewproxyPackage = (VViewproxyPackageImpl) (EPackage.Registry.INSTANCE
			.get(eNS_URI) instanceof VViewproxyPackageImpl ? EPackage.Registry.INSTANCE
			.get(eNS_URI)
			: new VViewproxyPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		VViewPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theViewproxyPackage.createPackageContents();

		// Initialize created meta-data
		theViewproxyPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theViewproxyPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(VViewproxyPackage.eNS_URI, theViewproxyPackage);
		return theViewproxyPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getViewProxy()
	{
		return viewProxyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getViewProxy_Id()
	{
		return (EAttribute) viewProxyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VViewproxyFactory getViewproxyFactory()
	{
		return (VViewproxyFactory) getEFactoryInstance();
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
		viewProxyEClass = createEClass(VIEW_PROXY);
		createEAttribute(viewProxyEClass, VIEW_PROXY__ID);
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
		final EcorePackage theEcorePackage = (EcorePackage) EPackage.Registry.INSTANCE
			.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		viewProxyEClass.getESuperTypes().add(theViewPackage.getContainedElement());

		// Initialize classes and features; add operations and parameters
		initEClass(viewProxyEClass, VViewProxy.class,
			"ViewProxy", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(
			getViewProxy_Id(),
			theEcorePackage.getEString(),
			"id", null, 1, 1, VViewProxy.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		// Create resource
		createResource(eNS_URI);
	}

} // VViewproxyPackageImpl
