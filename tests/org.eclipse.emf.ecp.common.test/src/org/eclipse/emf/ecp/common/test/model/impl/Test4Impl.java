/**
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 */
package org.eclipse.emf.ecp.common.test.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecp.common.spi.DerivedAttributeAdapter;
import org.eclipse.emf.ecp.common.test.model.Base;
import org.eclipse.emf.ecp.common.test.model.Test4;
import org.eclipse.emf.ecp.common.test.model.TestFactory;
import org.eclipse.emf.ecp.common.test.model.TestPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Test4</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.common.test.model.impl.Test4Impl#getDerived <em>Derived</em>}</li>
 * </ul>
 *
 * @generated
 */
public class Test4Impl extends BaseImpl implements Test4 {
	/**
	 * The default value of the '{@link #getDerived() <em>Derived</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getDerived()
	 * @generated
	 * @ordered
	 */
	protected static final String DERIVED_EDEFAULT = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	protected Test4Impl() {
		super();
		final DerivedAttributeAdapter daa = new DerivedAttributeAdapter(this, TestPackage.eINSTANCE.getTest4_Derived());
		daa.addNavigatedDependency(TestPackage.eINSTANCE.getBase_SingleAttribute(),
			TestPackage.eINSTANCE.getBase_Children());
		daa.addNavigatedDependency(TestPackage.eINSTANCE.getBase_SingleAttributeUnsettable(),
			TestPackage.eINSTANCE.getBase_Children());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TestPackage.Literals.TEST4;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public String getDerived() {
		for (final Base base : getChildren()) {
			if (KEY.equals(base.getSingleAttributeUnsettable())) {
				return base.getSingleAttribute();
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void setDerived(String newDerived) {
		Base baseToChange = null;
		for (final Base base : getChildren()) {
			if (KEY.equals(base.getSingleAttributeUnsettable())) {
				baseToChange = base;
				break;
			}
		}
		if (baseToChange == null) {
			final Base newBase = TestFactory.eINSTANCE.createBase();
			newBase.setSingleAttributeUnsettable(KEY);
			newBase.setSingleAttribute(newDerived);
			getChildren().add(newBase);
		} else {
			baseToChange.setSingleAttribute(newDerived);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case TestPackage.TEST4__DERIVED:
			return getDerived();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case TestPackage.TEST4__DERIVED:
			setDerived((String) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case TestPackage.TEST4__DERIVED:
			setDerived(DERIVED_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case TestPackage.TEST4__DERIVED:
			return DERIVED_EDEFAULT == null ? getDerived() != null : !DERIVED_EDEFAULT.equals(getDerived());
		}
		return super.eIsSet(featureID);
	}

} // Test4Impl
