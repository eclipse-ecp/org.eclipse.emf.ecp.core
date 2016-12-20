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
package org.eclipse.emfforms.spi.rulerepository.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains
 * accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.emfforms.spi.rulerepository.model.VRulerepositoryFactory
 * @model kind="package"
 * @generated
 */
public interface VRulerepositoryPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "rulerepository"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://org/eclipse/emfforms/rulerepository/model"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emfforms.rulerepository.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	VRulerepositoryPackage eINSTANCE = org.eclipse.emfforms.spi.rulerepository.model.impl.VRulerepositoryPackageImpl
		.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emfforms.spi.rulerepository.model.impl.VRuleRepositoryImpl
	 * <em>Rule Repository</em>}' class.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @see org.eclipse.emfforms.spi.rulerepository.model.impl.VRuleRepositoryImpl
	 * @see org.eclipse.emfforms.spi.rulerepository.model.impl.VRulerepositoryPackageImpl#getRuleRepository()
	 * @generated
	 */
	int RULE_REPOSITORY = 0;

	/**
	 * The feature id for the '<em><b>Rule Entries</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RULE_REPOSITORY__RULE_ENTRIES = 0;

	/**
	 * The number of structural features of the '<em>Rule Repository</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RULE_REPOSITORY_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '
	 * {@link org.eclipse.emfforms.spi.rulerepository.model.impl.VRuleEntryImpl
	 * <em>Rule Entry</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @see org.eclipse.emfforms.spi.rulerepository.model.impl.VRuleEntryImpl
	 * @see org.eclipse.emfforms.spi.rulerepository.model.impl.VRulerepositoryPackageImpl#getRuleEntry()
	 * @generated
	 */
	int RULE_ENTRY = 1;

	/**
	 * The feature id for the '<em><b>Rule</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RULE_ENTRY__RULE = 0;

	/**
	 * The feature id for the '<em><b>Elements</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RULE_ENTRY__ELEMENTS = 1;

	/**
	 * The feature id for the '<em><b>Merge Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RULE_ENTRY__MERGE_TYPE = 2;

	/**
	 * The number of structural features of the '<em>Rule Entry</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RULE_ENTRY_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.emfforms.spi.rulerepository.model.MergeType <em>Merge Type</em>}'
	 * enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emfforms.spi.rulerepository.model.MergeType
	 * @see org.eclipse.emfforms.spi.rulerepository.model.impl.VRulerepositoryPackageImpl#getMergeType()
	 * @generated
	 */
	int MERGE_TYPE = 2;

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfforms.spi.rulerepository.model.VRuleRepository <em>Rule
	 * Repository</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Rule Repository</em>'.
	 * @see org.eclipse.emfforms.spi.rulerepository.model.VRuleRepository
	 * @generated
	 */
	EClass getRuleRepository();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.emfforms.spi.rulerepository.model.VRuleRepository#getRuleEntries <em>Rule Entries</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Rule Entries</em>'.
	 * @see org.eclipse.emfforms.spi.rulerepository.model.VRuleRepository#getRuleEntries()
	 * @see #getRuleRepository()
	 * @generated
	 */
	EReference getRuleRepository_RuleEntries();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfforms.spi.rulerepository.model.VRuleEntry <em>Rule
	 * Entry</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Rule Entry</em>'.
	 * @see org.eclipse.emfforms.spi.rulerepository.model.VRuleEntry
	 * @generated
	 */
	EClass getRuleEntry();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.emfforms.spi.rulerepository.model.VRuleEntry#getRule <em>Rule</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Rule</em>'.
	 * @see org.eclipse.emfforms.spi.rulerepository.model.VRuleEntry#getRule()
	 * @see #getRuleEntry()
	 * @generated
	 */
	EReference getRuleEntry_Rule();

	/**
	 * Returns the meta object for the reference list
	 * '{@link org.eclipse.emfforms.spi.rulerepository.model.VRuleEntry#getElements <em>Elements</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Elements</em>'.
	 * @see org.eclipse.emfforms.spi.rulerepository.model.VRuleEntry#getElements()
	 * @see #getRuleEntry()
	 * @generated
	 */
	EReference getRuleEntry_Elements();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emfforms.spi.rulerepository.model.VRuleEntry#getMergeType <em>Merge Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Merge Type</em>'.
	 * @see org.eclipse.emfforms.spi.rulerepository.model.VRuleEntry#getMergeType()
	 * @see #getRuleEntry()
	 * @generated
	 */
	EAttribute getRuleEntry_MergeType();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.emfforms.spi.rulerepository.model.MergeType <em>Merge
	 * Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for enum '<em>Merge Type</em>'.
	 * @see org.eclipse.emfforms.spi.rulerepository.model.MergeType
	 * @generated
	 */
	EEnum getMergeType();

	/**
	 * Returns the factory that creates the instances of the model. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VRulerepositoryFactory getRulerepositoryFactory();

	/**
	 * <!-- begin-user-doc --> Defines literals for the meta objects that
	 * represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.emfforms.spi.rulerepository.model.impl.VRuleRepositoryImpl <em>Rule Repository</em>}'
		 * class.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 *
		 * @see org.eclipse.emfforms.spi.rulerepository.model.impl.VRuleRepositoryImpl
		 * @see org.eclipse.emfforms.spi.rulerepository.model.impl.VRulerepositoryPackageImpl#getRuleRepository()
		 * @generated
		 */
		EClass RULE_REPOSITORY = eINSTANCE.getRuleRepository();

		/**
		 * The meta object literal for the '<em><b>Rule Entries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 *
		 * @generated
		 */
		EReference RULE_REPOSITORY__RULE_ENTRIES = eINSTANCE.getRuleRepository_RuleEntries();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfforms.spi.rulerepository.model.impl.VRuleEntryImpl
		 * <em>Rule Entry</em>}' class.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 *
		 * @see org.eclipse.emfforms.spi.rulerepository.model.impl.VRuleEntryImpl
		 * @see org.eclipse.emfforms.spi.rulerepository.model.impl.VRulerepositoryPackageImpl#getRuleEntry()
		 * @generated
		 */
		EClass RULE_ENTRY = eINSTANCE.getRuleEntry();

		/**
		 * The meta object literal for the '<em><b>Rule</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference RULE_ENTRY__RULE = eINSTANCE.getRuleEntry_Rule();

		/**
		 * The meta object literal for the '<em><b>Elements</b></em>' reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference RULE_ENTRY__ELEMENTS = eINSTANCE.getRuleEntry_Elements();

		/**
		 * The meta object literal for the '<em><b>Merge Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute RULE_ENTRY__MERGE_TYPE = eINSTANCE.getRuleEntry_MergeType();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfforms.spi.rulerepository.model.MergeType <em>Merge
		 * Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emfforms.spi.rulerepository.model.MergeType
		 * @see org.eclipse.emfforms.spi.rulerepository.model.impl.VRulerepositoryPackageImpl#getMergeType()
		 * @generated
		 */
		EEnum MERGE_TYPE = eINSTANCE.getMergeType();

	}

} // VRulerepositoryPackage
