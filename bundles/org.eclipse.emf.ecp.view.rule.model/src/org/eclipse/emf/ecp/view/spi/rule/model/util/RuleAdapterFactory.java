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
package org.eclipse.emf.ecp.view.spi.rule.model.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VAttachment;
import org.eclipse.emf.ecp.view.spi.rule.model.AndCondition;
import org.eclipse.emf.ecp.view.spi.rule.model.Condition;
import org.eclipse.emf.ecp.view.spi.rule.model.EnableRule;
import org.eclipse.emf.ecp.view.spi.rule.model.LeafCondition;
import org.eclipse.emf.ecp.view.spi.rule.model.OrCondition;
import org.eclipse.emf.ecp.view.spi.rule.model.Rule;
import org.eclipse.emf.ecp.view.spi.rule.model.RulePackage;
import org.eclipse.emf.ecp.view.spi.rule.model.ShowRule;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 *
 * @since 1.2
 *        <!-- end-user-doc -->
 * @see org.eclipse.emf.ecp.view.spi.rule.model.RulePackage
 * @generated
 */
public class RuleAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected static RulePackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public RuleAdapterFactory() {
		if (modelPackage == null)
		{
			modelPackage = RulePackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance
	 * object of the model.
	 * <!-- end-user-doc -->
	 *
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage)
		{
			return true;
		}
		if (object instanceof EObject)
		{
			return ((EObject) object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected RuleSwitch<Adapter> modelSwitch = new RuleSwitch<Adapter>()
	{
		@Override
		public Adapter caseCondition(Condition object)
		{
			return createConditionAdapter();
		}

		@Override
		public Adapter caseLeafCondition(LeafCondition object)
		{
			return createLeafConditionAdapter();
		}

		@Override
		public Adapter caseOrCondition(OrCondition object)
		{
			return createOrConditionAdapter();
		}

		@Override
		public Adapter caseAndCondition(AndCondition object)
		{
			return createAndConditionAdapter();
		}

		@Override
		public Adapter caseRule(Rule object)
		{
			return createRuleAdapter();
		}

		@Override
		public Adapter caseShowRule(ShowRule object)
		{
			return createShowRuleAdapter();
		}

		@Override
		public Adapter caseEnableRule(EnableRule object)
		{
			return createEnableRuleAdapter();
		}

		@Override
		public Adapter caseAttachment(VAttachment object)
		{
			return createAttachmentAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object)
		{
			return createEObjectAdapter();
		}
	};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject) target);
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emf.ecp.view.spi.rule.model.Condition
	 * <em>Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.Condition
	 * @generated
	 */
	public Adapter createConditionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emf.ecp.view.spi.rule.model.LeafCondition
	 * <em>Leaf Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.LeafCondition
	 * @generated
	 */
	public Adapter createLeafConditionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emf.ecp.view.spi.rule.model.OrCondition
	 * <em>Or Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.OrCondition
	 * @generated
	 */
	public Adapter createOrConditionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emf.ecp.view.spi.rule.model.AndCondition
	 * <em>And Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.AndCondition
	 * @generated
	 */
	public Adapter createAndConditionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emf.ecp.view.spi.rule.model.Rule <em>Rule</em>}
	 * '.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.Rule
	 * @generated
	 */
	public Adapter createRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emf.ecp.view.spi.rule.model.ShowRule
	 * <em>Show Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.ShowRule
	 * @generated
	 */
	public Adapter createShowRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emf.ecp.view.spi.rule.model.EnableRule
	 * <em>Enable Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.EnableRule
	 * @generated
	 */
	public Adapter createEnableRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emf.ecp.view.spi.model.VAttachment
	 * <em>Attachment</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.emf.ecp.view.spi.model.VAttachment
	 * @generated
	 */
	public Adapter createAttachmentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} // RuleAdapterFactory
