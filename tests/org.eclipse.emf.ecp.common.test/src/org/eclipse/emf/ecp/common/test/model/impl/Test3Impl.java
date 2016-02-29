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
import org.eclipse.emf.ecp.common.test.model.Test3;
import org.eclipse.emf.ecp.common.test.model.TestPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Test3</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.common.test.model.impl.Test3Impl#getDerived <em>Derived</em>}</li>
 * </ul>
 *
 * @generated
 */
public class Test3Impl extends BaseImpl implements Test3 {
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
	protected Test3Impl() {
		super();
		new DerivedAttributeAdapter(this, TestPackage.eINSTANCE.getTest3_Derived()).addNavigatedDependency(
			TestPackage.eINSTANCE.getBase_SingleAttributeUnsettable(),
			TestPackage.eINSTANCE.getBase_Child(),
			TestPackage.eINSTANCE.getBase_Child());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TestPackage.Literals.TEST3;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public String getDerived() {
		final Base child1 = getChild();
		if (child1 == null) {
			return DERIVED_EDEFAULT;
		}
		final Base child2 = child1.getChild();
		if (child2 == null) {
			return DERIVED_EDEFAULT;
		}
		return child2.getSingleAttributeUnsettable();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void setDerived(String newDerived) {
		final Base child1 = getChild();
		if (child1 == null) {
			return;
		}
		final Base child2 = child1.getChild();
		if (child2 == null) {
			return;
		}
		child2.setSingleAttributeUnsettable(newDerived);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void unsetDerived() {
		final Base child1 = getChild();
		if (child1 == null) {
			return;
		}
		final Base child2 = child1.getChild();
		if (child2 == null) {
			return;
		}
		child2.unsetSingleAttributeUnsettable();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public boolean isSetDerived() {
		final Base child1 = getChild();
		if (child1 == null) {
			return false;
		}
		final Base child2 = child1.getChild();
		if (child2 == null) {
			return false;
		}
		return child2.isSetSingleAttributeUnsettable();
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
		case TestPackage.TEST3__DERIVED:
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
		case TestPackage.TEST3__DERIVED:
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
		case TestPackage.TEST3__DERIVED:
			unsetDerived();
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
		case TestPackage.TEST3__DERIVED:
			return isSetDerived();
		}
		return super.eIsSet(featureID);
	}

} // Test3Impl
