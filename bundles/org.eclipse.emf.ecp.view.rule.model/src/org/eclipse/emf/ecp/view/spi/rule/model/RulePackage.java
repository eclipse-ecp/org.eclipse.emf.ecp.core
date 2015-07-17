/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich GmbH - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.rule.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 *
 * @since 1.2
 * @noimplement This interface is not intended to be implemented by clients.
 *              <!-- end-user-doc -->
 * @see org.eclipse.emf.ecp.view.spi.rule.model.RuleFactory
 * @model kind="package"
 * @generated
 */
public interface RulePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "rule"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://org/eclipse/emf/ecp/view/rule/model"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.ecp.view.rule.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	RulePackage eINSTANCE = org.eclipse.emf.ecp.view.spi.rule.model.impl.RulePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.rule.model.impl.ConditionImpl <em>Condition</em>}
	 * ' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.ConditionImpl
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.RulePackageImpl#getCondition()
	 * @generated
	 */
	int CONDITION = 0;

	/**
	 * The number of structural features of the '<em>Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONDITION_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.rule.model.impl.LeafConditionImpl
	 * <em>Leaf Condition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.LeafConditionImpl
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.RulePackageImpl#getLeafCondition()
	 * @generated
	 */
	int LEAF_CONDITION = 1;

	/**
	 * The feature id for the '<em><b>Expected Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int LEAF_CONDITION__EXPECTED_VALUE = CONDITION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Domain Model Reference</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int LEAF_CONDITION__DOMAIN_MODEL_REFERENCE = CONDITION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Value Domain Model Reference</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.5
	 *        <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEAF_CONDITION__VALUE_DOMAIN_MODEL_REFERENCE = CONDITION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Leaf Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int LEAF_CONDITION_FEATURE_COUNT = CONDITION_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.rule.model.impl.OrConditionImpl
	 * <em>Or Condition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.OrConditionImpl
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.RulePackageImpl#getOrCondition()
	 * @generated
	 */
	int OR_CONDITION = 2;

	/**
	 * The feature id for the '<em><b>Conditions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int OR_CONDITION__CONDITIONS = CONDITION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Or Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int OR_CONDITION_FEATURE_COUNT = CONDITION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.rule.model.impl.AndConditionImpl
	 * <em>And Condition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.AndConditionImpl
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.RulePackageImpl#getAndCondition()
	 * @generated
	 */
	int AND_CONDITION = 3;

	/**
	 * The feature id for the '<em><b>Conditions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int AND_CONDITION__CONDITIONS = CONDITION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>And Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int AND_CONDITION_FEATURE_COUNT = CONDITION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.rule.model.impl.RuleImpl <em>Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.RuleImpl
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.RulePackageImpl#getRule()
	 * @generated
	 */
	int RULE = 4;

	/**
	 * The feature id for the '<em><b>Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RULE__CONDITION = VViewPackage.ATTACHMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RULE_FEATURE_COUNT = VViewPackage.ATTACHMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.rule.model.impl.ShowRuleImpl <em>Show Rule</em>}'
	 * class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.ShowRuleImpl
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.RulePackageImpl#getShowRule()
	 * @generated
	 */
	int SHOW_RULE = 5;

	/**
	 * The feature id for the '<em><b>Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SHOW_RULE__CONDITION = RULE__CONDITION;

	/**
	 * The feature id for the '<em><b>Hide</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SHOW_RULE__HIDE = RULE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Show Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SHOW_RULE_FEATURE_COUNT = RULE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.rule.model.impl.EnableRuleImpl
	 * <em>Enable Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.EnableRuleImpl
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.RulePackageImpl#getEnableRule()
	 * @generated
	 */
	int ENABLE_RULE = 6;

	/**
	 * The feature id for the '<em><b>Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ENABLE_RULE__CONDITION = RULE__CONDITION;

	/**
	 * The feature id for the '<em><b>Disable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ENABLE_RULE__DISABLE = RULE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Enable Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ENABLE_RULE_FEATURE_COUNT = RULE_FEATURE_COUNT + 1;

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.rule.model.Condition <em>Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Condition</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.Condition
	 * @generated
	 */
	EClass getCondition();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.rule.model.LeafCondition
	 * <em>Leaf Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Leaf Condition</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.LeafCondition
	 * @generated
	 */
	EClass getLeafCondition();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.spi.rule.model.LeafCondition#getExpectedValue <em>Expected Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Expected Value</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.LeafCondition#getExpectedValue()
	 * @see #getLeafCondition()
	 * @generated
	 */
	EAttribute getLeafCondition_ExpectedValue();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.ecp.view.spi.rule.model.LeafCondition#getDomainModelReference
	 * <em>Domain Model Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Domain Model Reference</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.LeafCondition#getDomainModelReference()
	 * @see #getLeafCondition()
	 * @generated
	 */
	EReference getLeafCondition_DomainModelReference();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.ecp.view.spi.rule.model.LeafCondition#getValueDomainModelReference
	 * <em>Value Domain Model Reference</em>}'.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.5
	 *        <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value Domain Model Reference</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.LeafCondition#getValueDomainModelReference()
	 * @see #getLeafCondition()
	 * @generated
	 */
	EReference getLeafCondition_ValueDomainModelReference();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.rule.model.OrCondition
	 * <em>Or Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Or Condition</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.OrCondition
	 * @generated
	 */
	EClass getOrCondition();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.ecp.view.spi.rule.model.OrCondition#getConditions <em>Conditions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Conditions</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.OrCondition#getConditions()
	 * @see #getOrCondition()
	 * @generated
	 */
	EReference getOrCondition_Conditions();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.rule.model.AndCondition
	 * <em>And Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>And Condition</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.AndCondition
	 * @generated
	 */
	EClass getAndCondition();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.ecp.view.spi.rule.model.AndCondition#getConditions <em>Conditions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Conditions</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.AndCondition#getConditions()
	 * @see #getAndCondition()
	 * @generated
	 */
	EReference getAndCondition_Conditions();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.rule.model.Rule <em>Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Rule</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.Rule
	 * @generated
	 */
	EClass getRule();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.ecp.view.spi.rule.model.Rule#getCondition <em>Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Condition</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.Rule#getCondition()
	 * @see #getRule()
	 * @generated
	 */
	EReference getRule_Condition();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.rule.model.ShowRule <em>Show Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Show Rule</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.ShowRule
	 * @generated
	 */
	EClass getShowRule();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.ecp.view.spi.rule.model.ShowRule#isHide
	 * <em>Hide</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Hide</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.ShowRule#isHide()
	 * @see #getShowRule()
	 * @generated
	 */
	EAttribute getShowRule_Hide();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.rule.model.EnableRule
	 * <em>Enable Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Enable Rule</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.EnableRule
	 * @generated
	 */
	EClass getEnableRule();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.ecp.view.spi.rule.model.EnableRule#isDisable
	 * <em>Disable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Disable</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.EnableRule#isDisable()
	 * @see #getEnableRule()
	 * @generated
	 */
	EAttribute getEnableRule_Disable();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	RuleFactory getRuleFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
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
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.rule.model.impl.ConditionImpl
		 * <em>Condition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.ConditionImpl
		 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.RulePackageImpl#getCondition()
		 * @generated
		 */
		EClass CONDITION = eINSTANCE.getCondition();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.rule.model.impl.LeafConditionImpl
		 * <em>Leaf Condition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.LeafConditionImpl
		 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.RulePackageImpl#getLeafCondition()
		 * @generated
		 */
		EClass LEAF_CONDITION = eINSTANCE.getLeafCondition();

		/**
		 * The meta object literal for the '<em><b>Expected Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute LEAF_CONDITION__EXPECTED_VALUE = eINSTANCE.getLeafCondition_ExpectedValue();

		/**
		 * The meta object literal for the '<em><b>Domain Model Reference</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference LEAF_CONDITION__DOMAIN_MODEL_REFERENCE = eINSTANCE.getLeafCondition_DomainModelReference();

		/**
		 * The meta object literal for the '<em><b>Value Domain Model Reference</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 *
		 * @since 1.5
		 *        <!-- end-user-doc -->
		 * @generated
		 */
		EReference LEAF_CONDITION__VALUE_DOMAIN_MODEL_REFERENCE = eINSTANCE
			.getLeafCondition_ValueDomainModelReference();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.rule.model.impl.OrConditionImpl
		 * <em>Or Condition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.OrConditionImpl
		 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.RulePackageImpl#getOrCondition()
		 * @generated
		 */
		EClass OR_CONDITION = eINSTANCE.getOrCondition();

		/**
		 * The meta object literal for the '<em><b>Conditions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference OR_CONDITION__CONDITIONS = eINSTANCE.getOrCondition_Conditions();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.rule.model.impl.AndConditionImpl
		 * <em>And Condition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.AndConditionImpl
		 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.RulePackageImpl#getAndCondition()
		 * @generated
		 */
		EClass AND_CONDITION = eINSTANCE.getAndCondition();

		/**
		 * The meta object literal for the '<em><b>Conditions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference AND_CONDITION__CONDITIONS = eINSTANCE.getAndCondition_Conditions();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.rule.model.impl.RuleImpl <em>Rule</em>}'
		 * class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.RuleImpl
		 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.RulePackageImpl#getRule()
		 * @generated
		 */
		EClass RULE = eINSTANCE.getRule();

		/**
		 * The meta object literal for the '<em><b>Condition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference RULE__CONDITION = eINSTANCE.getRule_Condition();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.rule.model.impl.ShowRuleImpl
		 * <em>Show Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.ShowRuleImpl
		 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.RulePackageImpl#getShowRule()
		 * @generated
		 */
		EClass SHOW_RULE = eINSTANCE.getShowRule();

		/**
		 * The meta object literal for the '<em><b>Hide</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute SHOW_RULE__HIDE = eINSTANCE.getShowRule_Hide();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.rule.model.impl.EnableRuleImpl
		 * <em>Enable Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.EnableRuleImpl
		 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.RulePackageImpl#getEnableRule()
		 * @generated
		 */
		EClass ENABLE_RULE = eINSTANCE.getEnableRule();

		/**
		 * The meta object literal for the '<em><b>Disable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute ENABLE_RULE__DISABLE = eINSTANCE.getEnableRule_Disable();

	}

} // RulePackage
