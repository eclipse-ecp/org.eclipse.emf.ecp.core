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
 * EclipseSource Muenchen GmbH - initial API and implementation
 */
package org.eclipse.emfforms.datatemplate.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emfforms.datatemplate.DataTemplateFactory;
import org.eclipse.emfforms.datatemplate.DataTemplatePackage;
import org.eclipse.emfforms.datatemplate.Template;
import org.eclipse.emfforms.datatemplate.TemplateCollection;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class DataTemplatePackageImpl extends EPackageImpl implements DataTemplatePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass templateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass templateCollectionEClass = null;

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
	 * @see org.eclipse.emfforms.datatemplate.DataTemplatePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private DataTemplatePackageImpl() {
		super(eNS_URI, DataTemplateFactory.eINSTANCE);
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
	 * This method is used to initialize {@link DataTemplatePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static DataTemplatePackage init() {
		if (isInited) {
			return (DataTemplatePackage) EPackage.Registry.INSTANCE.getEPackage(DataTemplatePackage.eNS_URI);
		}

		// Obtain or create and register package
		final Object registeredDataTemplatePackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		final DataTemplatePackageImpl theDataTemplatePackage = registeredDataTemplatePackage instanceof DataTemplatePackageImpl
			? (DataTemplatePackageImpl) registeredDataTemplatePackage
			: new DataTemplatePackageImpl();

		isInited = true;

		// Create package meta-data objects
		theDataTemplatePackage.createPackageContents();

		// Initialize created meta-data
		theDataTemplatePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theDataTemplatePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(DataTemplatePackage.eNS_URI, theDataTemplatePackage);
		return theDataTemplatePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getTemplate() {
		return templateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getTemplate_Name() {
		return (EAttribute) templateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getTemplate_Instance() {
		return (EReference) templateEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getTemplateCollection() {
		return templateCollectionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getTemplateCollection_Templates() {
		return (EReference) templateCollectionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public DataTemplateFactory getDataTemplateFactory() {
		return (DataTemplateFactory) getEFactoryInstance();
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
		templateEClass = createEClass(TEMPLATE);
		createEAttribute(templateEClass, TEMPLATE__NAME);
		createEReference(templateEClass, TEMPLATE__INSTANCE);

		templateCollectionEClass = createEClass(TEMPLATE_COLLECTION);
		createEReference(templateCollectionEClass, TEMPLATE_COLLECTION__TEMPLATES);
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

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(templateEClass, Template.class, "Template", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
			IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTemplate_Name(), ecorePackage.getEString(), "name", null, 0, 1, Template.class, !IS_TRANSIENT, //$NON-NLS-1$
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTemplate_Instance(), ecorePackage.getEObject(), null, "instance", null, 1, 1, Template.class, //$NON-NLS-1$
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);

		initEClass(templateCollectionEClass, TemplateCollection.class, "TemplateCollection", !IS_ABSTRACT, //$NON-NLS-1$
			!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTemplateCollection_Templates(), getTemplate(), null, "templates", null, 0, -1, //$NON-NLS-1$
			TemplateCollection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} // DataTemplatePackageImpl
