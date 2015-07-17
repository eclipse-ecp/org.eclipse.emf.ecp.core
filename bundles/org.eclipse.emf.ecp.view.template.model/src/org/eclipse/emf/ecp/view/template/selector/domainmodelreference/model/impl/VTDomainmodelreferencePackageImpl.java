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
package org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.template.model.VTTemplatePackage;
import org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.VTDomainModelReferenceSelector;
import org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.VTDomainmodelreferenceFactory;
import org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.VTDomainmodelreferencePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class VTDomainmodelreferencePackageImpl extends EPackageImpl implements VTDomainmodelreferencePackage
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass domainModelReferenceSelectorEClass = null;

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
	 * @see org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.VDomainmodelreferencePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private VTDomainmodelreferencePackageImpl()
	{
		super(eNS_URI, VTDomainmodelreferenceFactory.eINSTANCE);
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
	 * This method is used to initialize {@link VTDomainmodelreferencePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static VTDomainmodelreferencePackage init()
	{
		if (isInited) {
			return (VTDomainmodelreferencePackage) EPackage.Registry.INSTANCE
				.getEPackage(VTDomainmodelreferencePackage.eNS_URI);
		}

		// Obtain or create and register package
		final VTDomainmodelreferencePackageImpl theDomainmodelreferencePackage = (VTDomainmodelreferencePackageImpl) (EPackage.Registry.INSTANCE
			.get(eNS_URI) instanceof VTDomainmodelreferencePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
			: new VTDomainmodelreferencePackageImpl());

		isInited = true;

		// Initialize simple dependencies
		VTTemplatePackage.eINSTANCE.eClass();
		VViewPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theDomainmodelreferencePackage.createPackageContents();

		// Initialize created meta-data
		theDomainmodelreferencePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theDomainmodelreferencePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(VTDomainmodelreferencePackage.eNS_URI, theDomainmodelreferencePackage);
		return theDomainmodelreferencePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getDomainModelReferenceSelector()
	{
		return domainModelReferenceSelectorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getDomainModelReferenceSelector_DomainModelReference()
	{
		return (EReference) domainModelReferenceSelectorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VTDomainmodelreferenceFactory getDomainmodelreferenceFactory()
	{
		return (VTDomainmodelreferenceFactory) getEFactoryInstance();
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
		domainModelReferenceSelectorEClass = createEClass(DOMAIN_MODEL_REFERENCE_SELECTOR);
		createEReference(domainModelReferenceSelectorEClass, DOMAIN_MODEL_REFERENCE_SELECTOR__DOMAIN_MODEL_REFERENCE);
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
		final VViewPackage theViewPackage = (VViewPackage) EPackage.Registry.INSTANCE.getEPackage(VViewPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		domainModelReferenceSelectorEClass.getESuperTypes().add(theTemplatePackage.getStyleSelector());

		// Initialize classes, features, and operations; add parameters
		initEClass(domainModelReferenceSelectorEClass, VTDomainModelReferenceSelector.class,
			"DomainModelReferenceSelector", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
			getDomainModelReferenceSelector_DomainModelReference(),
			theViewPackage.getDomainModelReference(),
			null,
			"domainModelReference", null, 1, 1, VTDomainModelReferenceSelector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		// Create resource
		createResource(eNS_URI);
	}

} // VTDomainmodelreferencePackageImpl
