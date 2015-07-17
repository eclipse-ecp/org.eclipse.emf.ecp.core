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
package org.eclipse.emf.ecp.view.spi.rule.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecp.view.spi.rule.model.AndCondition;
import org.eclipse.emf.ecp.view.spi.rule.model.EnableRule;
import org.eclipse.emf.ecp.view.spi.rule.model.LeafCondition;
import org.eclipse.emf.ecp.view.spi.rule.model.OrCondition;
import org.eclipse.emf.ecp.view.spi.rule.model.RuleFactory;
import org.eclipse.emf.ecp.view.spi.rule.model.RulePackage;
import org.eclipse.emf.ecp.view.spi.rule.model.ShowRule;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 *
 * @since 1.2
 *        <!-- end-user-doc -->
 * @generated
 */
public class RuleFactoryImpl extends EFactoryImpl implements RuleFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static RuleFactory init() {
		try
		{
			final RuleFactory theRuleFactory = (RuleFactory) EPackage.Registry.INSTANCE
				.getEFactory(RulePackage.eNS_URI);
			if (theRuleFactory != null)
			{
				return theRuleFactory;
			}
		} catch (final Exception exception)
		{
			EcorePlugin.INSTANCE.log(exception);
		}
		return new RuleFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public RuleFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID())
		{
		case RulePackage.LEAF_CONDITION:
			return createLeafCondition();
		case RulePackage.OR_CONDITION:
			return createOrCondition();
		case RulePackage.AND_CONDITION:
			return createAndCondition();
		case RulePackage.SHOW_RULE:
			return createShowRule();
		case RulePackage.ENABLE_RULE:
			return createEnableRule();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public LeafCondition createLeafCondition() {
		final LeafConditionImpl leafCondition = new LeafConditionImpl();
		return leafCondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public OrCondition createOrCondition() {
		final OrConditionImpl orCondition = new OrConditionImpl();
		return orCondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public AndCondition createAndCondition() {
		final AndConditionImpl andCondition = new AndConditionImpl();
		return andCondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ShowRule createShowRule() {
		final ShowRuleImpl showRule = new ShowRuleImpl();
		return showRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EnableRule createEnableRule() {
		final EnableRuleImpl enableRule = new EnableRuleImpl();
		return enableRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public RulePackage getRulePackage() {
		return (RulePackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static RulePackage getPackage() {
		return RulePackage.eINSTANCE;
	}

} // RuleFactoryImpl
