/**
 * Copyright (c) 2017 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.rule.model.impl;

import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.view.spi.rule.model.False;
import org.eclipse.emf.ecp.view.spi.rule.model.RulePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>False</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class FalseImpl extends ConditionImpl implements False {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected FalseImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RulePackage.Literals.FALSE;
	}

	@Override
	public boolean evaluate(EObject domainModel) {
		return false;
	}

	@Override
	public boolean evaluateChangedValues(EObject domainModel, Map<Setting, Object> possibleNewValues) {
		return false;
	}

} // FalseImpl
