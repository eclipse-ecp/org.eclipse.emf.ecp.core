/**
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.selector.hierarchy.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecp.view.template.model.VTTemplatePackage;
import org.eclipse.emf.ecp.view.template.selector.hierarchy.model.VTHierarchyFactory;
import org.eclipse.emf.ecp.view.template.selector.hierarchy.model.VTHierarchyPackage;
import org.eclipse.emf.ecp.view.template.selector.hierarchy.model.VTHierarchySelector;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class VTHierarchyPackageImpl extends EPackageImpl implements VTHierarchyPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass hierarchySelectorEClass = null;

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
	 * @see org.eclipse.emf.ecp.view.template.selector.hierarchy.model.VTHierarchyPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private VTHierarchyPackageImpl() {
		super(eNS_URI, VTHierarchyFactory.eINSTANCE);
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
	 * This method is used to initialize {@link VTHierarchyPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static VTHierarchyPackage init() {
		if (isInited) {
			return (VTHierarchyPackage) EPackage.Registry.INSTANCE.getEPackage(VTHierarchyPackage.eNS_URI);
		}

		// Obtain or create and register package
		final VTHierarchyPackageImpl theHierarchyPackage = (VTHierarchyPackageImpl) (EPackage.Registry.INSTANCE
			.get(eNS_URI) instanceof VTHierarchyPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
				: new VTHierarchyPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		VTTemplatePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theHierarchyPackage.createPackageContents();

		// Initialize created meta-data
		theHierarchyPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theHierarchyPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(VTHierarchyPackage.eNS_URI, theHierarchyPackage);
		return theHierarchyPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getHierarchySelector() {
		return hierarchySelectorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VTHierarchyFactory getHierarchyFactory() {
		return (VTHierarchyFactory) getEFactoryInstance();
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
		hierarchySelectorEClass = createEClass(HIERARCHY_SELECTOR);
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
		hierarchySelectorEClass.getESuperTypes().add(theTemplatePackage.getStyleSelectorContainer());
		hierarchySelectorEClass.getESuperTypes().add(theTemplatePackage.getStyleSelector());

		// Initialize classes, features, and operations; add parameters
		initEClass(hierarchySelectorEClass, VTHierarchySelector.class, "HierarchySelector", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
			IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} // VTHierarchyPackageImpl
