/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich GmbH - initial API and implementation
 * Christian W. Damus - bugs 527753, 530900
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.rule.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.rule.model.impl.ConditionImpl
	 * <em>Condition</em>}' class.
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
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.rule.model.impl.LeafConditionImpl <em>Leaf
	 * Condition</em>}' class.
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
	 * The feature id for the '<em><b>Compare Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.11
	 *        <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEAF_CONDITION__COMPARE_TYPE = CONDITION_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Leaf Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int LEAF_CONDITION_FEATURE_COUNT = CONDITION_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.rule.model.impl.OrConditionImpl <em>Or
	 * Condition</em>}' class.
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
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.rule.model.impl.AndConditionImpl <em>And
	 * Condition</em>}' class.
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
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.rule.model.impl.EnableRuleImpl <em>Enable
	 * Rule</em>}' class.
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
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.rule.model.impl.IterateConditionImpl <em>Iterate
	 * Condition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.IterateConditionImpl
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.RulePackageImpl#getIterateCondition()
	 * @generated
	 */
	int ITERATE_CONDITION = 7;

	/**
	 * The feature id for the '<em><b>Quantifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ITERATE_CONDITION__QUANTIFIER = CONDITION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>If Empty</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ITERATE_CONDITION__IF_EMPTY = CONDITION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Item Reference</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ITERATE_CONDITION__ITEM_REFERENCE = CONDITION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Item Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ITERATE_CONDITION__ITEM_CONDITION = CONDITION_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Iterate Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ITERATE_CONDITION_FEATURE_COUNT = CONDITION_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.rule.model.impl.TrueImpl <em>True</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.TrueImpl
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.RulePackageImpl#getTrue()
	 * @generated
	 */
	int TRUE = 8;

	/**
	 * The number of structural features of the '<em>True</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRUE_FEATURE_COUNT = CONDITION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.rule.model.impl.FalseImpl <em>False</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.FalseImpl
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.RulePackageImpl#getFalse()
	 * @generated
	 */
	int FALSE = 9;

	/**
	 * The number of structural features of the '<em>False</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int FALSE_FEATURE_COUNT = CONDITION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.rule.model.impl.NotConditionImpl <em>Not
	 * Condition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.NotConditionImpl
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.RulePackageImpl#getNotCondition()
	 * @generated
	 */
	int NOT_CONDITION = 10;

	/**
	 * The feature id for the '<em><b>Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int NOT_CONDITION__CONDITION = CONDITION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Not Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int NOT_CONDITION_FEATURE_COUNT = CONDITION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.rule.model.impl.IsProxyConditionImpl <em>Is Proxy
	 * Condition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.IsProxyConditionImpl
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.RulePackageImpl#getIsProxyCondition()
	 * @generated
	 */
	int IS_PROXY_CONDITION = 11;

	/**
	 * The feature id for the '<em><b>Domain Model Reference</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int IS_PROXY_CONDITION__DOMAIN_MODEL_REFERENCE = CONDITION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Is Proxy Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int IS_PROXY_CONDITION_FEATURE_COUNT = CONDITION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.rule.model.CompareType <em>Compare Type</em>}'
	 * enum.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.11
	 *        <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.CompareType
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.RulePackageImpl#getCompareType()
	 * @generated
	 */
	int COMPARE_TYPE = 12;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.rule.model.Quantifier <em>Quantifier</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.Quantifier
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.RulePackageImpl#getQuantifier()
	 * @generated
	 */
	int QUANTIFIER = 13;

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
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.rule.model.LeafCondition <em>Leaf
	 * Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Leaf Condition</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.LeafCondition
	 * @generated
	 */
	EClass getLeafCondition();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.spi.rule.model.LeafCondition#getExpectedValue <em>Expected Value</em>}'.
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
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.emf.ecp.view.spi.rule.model.LeafCondition#getDomainModelReference <em>Domain Model
	 * Reference</em>}'.
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
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.emf.ecp.view.spi.rule.model.LeafCondition#getValueDomainModelReference <em>Value Domain Model
	 * Reference</em>}'.
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
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.spi.rule.model.LeafCondition#getCompareType <em>Compare Type</em>}'.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.11
	 *        <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Compare Type</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.LeafCondition#getCompareType()
	 * @see #getLeafCondition()
	 * @generated
	 */
	EAttribute getLeafCondition_CompareType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.rule.model.OrCondition <em>Or
	 * Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Or Condition</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.OrCondition
	 * @generated
	 */
	EClass getOrCondition();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.emf.ecp.view.spi.rule.model.OrCondition#getConditions <em>Conditions</em>}'.
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
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.rule.model.AndCondition <em>And
	 * Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>And Condition</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.AndCondition
	 * @generated
	 */
	EClass getAndCondition();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.emf.ecp.view.spi.rule.model.AndCondition#getConditions <em>Conditions</em>}'.
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
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.emf.ecp.view.spi.rule.model.Rule#getCondition <em>Condition</em>}'.
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
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.rule.model.EnableRule <em>Enable
	 * Rule</em>}'.
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
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.rule.model.IterateCondition <em>Iterate
	 * Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Iterate Condition</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.IterateCondition
	 * @generated
	 */
	EClass getIterateCondition();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.spi.rule.model.IterateCondition#getQuantifier <em>Quantifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Quantifier</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.IterateCondition#getQuantifier()
	 * @see #getIterateCondition()
	 * @generated
	 */
	EAttribute getIterateCondition_Quantifier();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.spi.rule.model.IterateCondition#isIfEmpty <em>If Empty</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>If Empty</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.IterateCondition#isIfEmpty()
	 * @see #getIterateCondition()
	 * @generated
	 */
	EAttribute getIterateCondition_IfEmpty();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.emf.ecp.view.spi.rule.model.IterateCondition#getItemReference <em>Item Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Item Reference</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.IterateCondition#getItemReference()
	 * @see #getIterateCondition()
	 * @generated
	 */
	EReference getIterateCondition_ItemReference();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.emf.ecp.view.spi.rule.model.IterateCondition#getItemCondition <em>Item Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Item Condition</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.IterateCondition#getItemCondition()
	 * @see #getIterateCondition()
	 * @generated
	 */
	EReference getIterateCondition_ItemCondition();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.rule.model.True <em>True</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>True</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.True
	 * @generated
	 */
	EClass getTrue();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.rule.model.False <em>False</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>False</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.False
	 * @generated
	 */
	EClass getFalse();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.rule.model.NotCondition <em>Not
	 * Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Not Condition</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.NotCondition
	 * @generated
	 */
	EClass getNotCondition();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.emf.ecp.view.spi.rule.model.NotCondition#getCondition <em>Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Condition</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.NotCondition#getCondition()
	 * @see #getNotCondition()
	 * @generated
	 */
	EReference getNotCondition_Condition();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.rule.model.IsProxyCondition <em>Is Proxy
	 * Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Is Proxy Condition</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.IsProxyCondition
	 * @generated
	 */
	EClass getIsProxyCondition();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.emf.ecp.view.spi.rule.model.IsProxyCondition#getDomainModelReference <em>Domain Model
	 * Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Domain Model Reference</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.IsProxyCondition#getDomainModelReference()
	 * @see #getIsProxyCondition()
	 * @generated
	 */
	EReference getIsProxyCondition_DomainModelReference();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.emf.ecp.view.spi.rule.model.CompareType <em>Compare
	 * Type</em>}'.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.11
	 *        <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Compare Type</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.CompareType
	 * @generated
	 */
	EEnum getCompareType();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.emf.ecp.view.spi.rule.model.Quantifier
	 * <em>Quantifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for enum '<em>Quantifier</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.Quantifier
	 * @generated
	 */
	EEnum getQuantifier();

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
		 * The meta object literal for the '<em><b>Compare Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 *
		 * @since 1.11
		 *        <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LEAF_CONDITION__COMPARE_TYPE = eINSTANCE.getLeafCondition_CompareType();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.rule.model.impl.OrConditionImpl <em>Or
		 * Condition</em>}' class.
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
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.rule.model.impl.AndConditionImpl <em>And
		 * Condition</em>}' class.
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
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.rule.model.impl.ShowRuleImpl <em>Show
		 * Rule</em>}' class.
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

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.rule.model.impl.IterateConditionImpl
		 * <em>Iterate Condition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.IterateConditionImpl
		 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.RulePackageImpl#getIterateCondition()
		 * @generated
		 */
		EClass ITERATE_CONDITION = eINSTANCE.getIterateCondition();

		/**
		 * The meta object literal for the '<em><b>Quantifier</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute ITERATE_CONDITION__QUANTIFIER = eINSTANCE.getIterateCondition_Quantifier();

		/**
		 * The meta object literal for the '<em><b>If Empty</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute ITERATE_CONDITION__IF_EMPTY = eINSTANCE.getIterateCondition_IfEmpty();

		/**
		 * The meta object literal for the '<em><b>Item Reference</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference ITERATE_CONDITION__ITEM_REFERENCE = eINSTANCE.getIterateCondition_ItemReference();

		/**
		 * The meta object literal for the '<em><b>Item Condition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference ITERATE_CONDITION__ITEM_CONDITION = eINSTANCE.getIterateCondition_ItemCondition();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.rule.model.impl.TrueImpl <em>True</em>}'
		 * class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.TrueImpl
		 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.RulePackageImpl#getTrue()
		 * @generated
		 */
		EClass TRUE = eINSTANCE.getTrue();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.rule.model.impl.FalseImpl
		 * <em>False</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.FalseImpl
		 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.RulePackageImpl#getFalse()
		 * @generated
		 */
		EClass FALSE = eINSTANCE.getFalse();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.rule.model.impl.NotConditionImpl <em>Not
		 * Condition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.NotConditionImpl
		 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.RulePackageImpl#getNotCondition()
		 * @generated
		 */
		EClass NOT_CONDITION = eINSTANCE.getNotCondition();

		/**
		 * The meta object literal for the '<em><b>Condition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference NOT_CONDITION__CONDITION = eINSTANCE.getNotCondition_Condition();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.rule.model.impl.IsProxyConditionImpl
		 * <em>Is Proxy Condition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.IsProxyConditionImpl
		 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.RulePackageImpl#getIsProxyCondition()
		 * @generated
		 */
		EClass IS_PROXY_CONDITION = eINSTANCE.getIsProxyCondition();

		/**
		 * The meta object literal for the '<em><b>Domain Model Reference</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference IS_PROXY_CONDITION__DOMAIN_MODEL_REFERENCE = eINSTANCE.getIsProxyCondition_DomainModelReference();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.rule.model.CompareType <em>Compare
		 * Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 *
		 * @since 1.11
		 *        <!-- end-user-doc -->
		 * @see org.eclipse.emf.ecp.view.spi.rule.model.CompareType
		 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.RulePackageImpl#getCompareType()
		 * @generated
		 */
		EEnum COMPARE_TYPE = eINSTANCE.getCompareType();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.rule.model.Quantifier
		 * <em>Quantifier</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.spi.rule.model.Quantifier
		 * @see org.eclipse.emf.ecp.view.spi.rule.model.impl.RulePackageImpl#getQuantifier()
		 * @generated
		 */
		EEnum QUANTIFIER = eINSTANCE.getQuantifier();

	}

} // RulePackage
