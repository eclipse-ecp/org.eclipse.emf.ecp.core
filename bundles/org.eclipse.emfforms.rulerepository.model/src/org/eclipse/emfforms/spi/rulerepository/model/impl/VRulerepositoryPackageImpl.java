/**
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emfforms.spi.rulerepository.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.rule.model.RulePackage;
import org.eclipse.emfforms.spi.rulerepository.model.VRuleEntry;
import org.eclipse.emfforms.spi.rulerepository.model.VRuleRepository;
import org.eclipse.emfforms.spi.rulerepository.model.VRulerepositoryFactory;
import org.eclipse.emfforms.spi.rulerepository.model.VRulerepositoryPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!--
 * end-user-doc -->
 *
 * @generated
 */
public class VRulerepositoryPackageImpl extends EPackageImpl implements VRulerepositoryPackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass ruleRepositoryEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass ruleEntryEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the
	 * package package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory
	 * method {@link #init init()}, which also performs initialization of the
	 * package, or returns the registered package, if one already exists. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.emfforms.spi.rulerepository.model.VRulerepositoryPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private VRulerepositoryPackageImpl() {
		super(eNS_URI, VRulerepositoryFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>
	 * This method is used to initialize {@link VRulerepositoryPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static VRulerepositoryPackage init() {
		if (isInited) {
			return (VRulerepositoryPackage) EPackage.Registry.INSTANCE.getEPackage(VRulerepositoryPackage.eNS_URI);
		}

		// Obtain or create and register package
		final VRulerepositoryPackageImpl theRulerepositoryPackage = (VRulerepositoryPackageImpl) (EPackage.Registry.INSTANCE
			.get(eNS_URI) instanceof VRulerepositoryPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
				: new VRulerepositoryPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		RulePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theRulerepositoryPackage.createPackageContents();

		// Initialize created meta-data
		theRulerepositoryPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theRulerepositoryPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(VRulerepositoryPackage.eNS_URI, theRulerepositoryPackage);
		return theRulerepositoryPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getRuleRepository() {
		return ruleRepositoryEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getRuleRepository_RuleEntries() {
		return (EReference) ruleRepositoryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getRuleEntry() {
		return ruleEntryEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getRuleEntry_Rule() {
		return (EReference) ruleEntryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getRuleEntry_Elements() {
		return (EReference) ruleEntryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VRulerepositoryFactory getRulerepositoryFactory() {
		return (VRulerepositoryFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
		ruleRepositoryEClass = createEClass(RULE_REPOSITORY);
		createEReference(ruleRepositoryEClass, RULE_REPOSITORY__RULE_ENTRIES);

		ruleEntryEClass = createEClass(RULE_ENTRY);
		createEReference(ruleEntryEClass, RULE_ENTRY__RULE);
		createEReference(ruleEntryEClass, RULE_ENTRY__ELEMENTS);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model. This
	 * method is guarded to have no affect on any invocation but its first. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
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
		final RulePackage theRulePackage = (RulePackage) EPackage.Registry.INSTANCE.getEPackage(RulePackage.eNS_URI);
		final VViewPackage theViewPackage = (VViewPackage) EPackage.Registry.INSTANCE.getEPackage(VViewPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(ruleRepositoryEClass, VRuleRepository.class, "RuleRepository", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
			IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRuleRepository_RuleEntries(), getRuleEntry(), null, "ruleEntries", null, 0, -1, //$NON-NLS-1$
			VRuleRepository.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ruleEntryEClass, VRuleEntry.class, "RuleEntry", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
			IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRuleEntry_Rule(), theRulePackage.getRule(), null, "rule", null, 1, 1, VRuleEntry.class, //$NON-NLS-1$
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);
		initEReference(getRuleEntry_Elements(), theViewPackage.getElement(), null, "elements", null, 0, -1, //$NON-NLS-1$
			VRuleEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} // VRulerepositoryPackageImpl
