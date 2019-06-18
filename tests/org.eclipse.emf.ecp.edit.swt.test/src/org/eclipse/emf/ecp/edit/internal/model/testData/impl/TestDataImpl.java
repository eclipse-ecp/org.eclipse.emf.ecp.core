/**
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * EclipseSource Muenchen GmbH - initial API and implementation
 */
package org.eclipse.emf.ecp.edit.internal.model.testData.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecp.edit.internal.model.testData.TestData;
import org.eclipse.emf.ecp.edit.internal.model.testData.TestDataPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Test Data</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.edit.internal.model.testData.impl.TestDataImpl#getString <em>String</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.edit.internal.model.testData.impl.TestDataImpl#isBoolean <em>Boolean</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.edit.internal.model.testData.impl.TestDataImpl#getInteger <em>Integer</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.edit.internal.model.testData.impl.TestDataImpl#getLong <em>Long</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.edit.internal.model.testData.impl.TestDataImpl#getFloat <em>Float</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.edit.internal.model.testData.impl.TestDataImpl#getDouble <em>Double</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.edit.internal.model.testData.impl.TestDataImpl#getStringMax8 <em>String
 * Max8</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TestDataImpl extends MinimalEObjectImpl.Container implements TestData {
	/**
	 * The default value of the '{@link #getString() <em>String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getString()
	 * @generated
	 * @ordered
	 */
	protected static final String STRING_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getString() <em>String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getString()
	 * @generated
	 * @ordered
	 */
	protected String string = STRING_EDEFAULT;

	/**
	 * The default value of the '{@link #isBoolean() <em>Boolean</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #isBoolean()
	 * @generated
	 * @ordered
	 */
	protected static final boolean BOOLEAN_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isBoolean() <em>Boolean</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #isBoolean()
	 * @generated
	 * @ordered
	 */
	protected boolean boolean_ = BOOLEAN_EDEFAULT;

	/**
	 * The default value of the '{@link #getInteger() <em>Integer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getInteger()
	 * @generated
	 * @ordered
	 */
	protected static final int INTEGER_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getInteger() <em>Integer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getInteger()
	 * @generated
	 * @ordered
	 */
	protected int integer = INTEGER_EDEFAULT;

	/**
	 * The default value of the '{@link #getLong() <em>Long</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getLong()
	 * @generated
	 * @ordered
	 */
	protected static final long LONG_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getLong() <em>Long</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getLong()
	 * @generated
	 * @ordered
	 */
	protected long long_ = LONG_EDEFAULT;

	/**
	 * The default value of the '{@link #getFloat() <em>Float</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getFloat()
	 * @generated
	 * @ordered
	 */
	protected static final float FLOAT_EDEFAULT = 0.0F;

	/**
	 * The cached value of the '{@link #getFloat() <em>Float</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getFloat()
	 * @generated
	 * @ordered
	 */
	protected float float_ = FLOAT_EDEFAULT;

	/**
	 * The default value of the '{@link #getDouble() <em>Double</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getDouble()
	 * @generated
	 * @ordered
	 */
	protected static final double DOUBLE_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getDouble() <em>Double</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getDouble()
	 * @generated
	 * @ordered
	 */
	protected double double_ = DOUBLE_EDEFAULT;

	/**
	 * The default value of the '{@link #getStringMax8() <em>String Max8</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getStringMax8()
	 * @generated
	 * @ordered
	 */
	protected static final String STRING_MAX8_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getStringMax8() <em>String Max8</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getStringMax8()
	 * @generated
	 * @ordered
	 */
	protected String stringMax8 = STRING_MAX8_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected TestDataImpl() {
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
		return TestDataPackage.Literals.TEST_DATA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getString() {
		return string;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setString(String newString) {
		final String oldString = string;
		string = newString;
		if (eNotificationRequired()) {
			eNotify(
				new ENotificationImpl(this, Notification.SET, TestDataPackage.TEST_DATA__STRING, oldString, string));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean isBoolean() {
		return boolean_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setBoolean(boolean newBoolean) {
		final boolean oldBoolean = boolean_;
		boolean_ = newBoolean;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TestDataPackage.TEST_DATA__BOOLEAN, oldBoolean,
				boolean_));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public int getInteger() {
		return integer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setInteger(int newInteger) {
		final int oldInteger = integer;
		integer = newInteger;
		if (eNotificationRequired()) {
			eNotify(
				new ENotificationImpl(this, Notification.SET, TestDataPackage.TEST_DATA__INTEGER, oldInteger, integer));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public long getLong() {
		return long_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setLong(long newLong) {
		final long oldLong = long_;
		long_ = newLong;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TestDataPackage.TEST_DATA__LONG, oldLong, long_));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public float getFloat() {
		return float_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setFloat(float newFloat) {
		final float oldFloat = float_;
		float_ = newFloat;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TestDataPackage.TEST_DATA__FLOAT, oldFloat, float_));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public double getDouble() {
		return double_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setDouble(double newDouble) {
		final double oldDouble = double_;
		double_ = newDouble;
		if (eNotificationRequired()) {
			eNotify(
				new ENotificationImpl(this, Notification.SET, TestDataPackage.TEST_DATA__DOUBLE, oldDouble, double_));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getStringMax8() {
		return stringMax8;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setStringMax8(String newStringMax8) {
		final String oldStringMax8 = stringMax8;
		stringMax8 = newStringMax8;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TestDataPackage.TEST_DATA__STRING_MAX8, oldStringMax8,
				stringMax8));
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
		case TestDataPackage.TEST_DATA__STRING:
			return getString();
		case TestDataPackage.TEST_DATA__BOOLEAN:
			return isBoolean();
		case TestDataPackage.TEST_DATA__INTEGER:
			return getInteger();
		case TestDataPackage.TEST_DATA__LONG:
			return getLong();
		case TestDataPackage.TEST_DATA__FLOAT:
			return getFloat();
		case TestDataPackage.TEST_DATA__DOUBLE:
			return getDouble();
		case TestDataPackage.TEST_DATA__STRING_MAX8:
			return getStringMax8();
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
		case TestDataPackage.TEST_DATA__STRING:
			setString((String) newValue);
			return;
		case TestDataPackage.TEST_DATA__BOOLEAN:
			setBoolean((Boolean) newValue);
			return;
		case TestDataPackage.TEST_DATA__INTEGER:
			setInteger((Integer) newValue);
			return;
		case TestDataPackage.TEST_DATA__LONG:
			setLong((Long) newValue);
			return;
		case TestDataPackage.TEST_DATA__FLOAT:
			setFloat((Float) newValue);
			return;
		case TestDataPackage.TEST_DATA__DOUBLE:
			setDouble((Double) newValue);
			return;
		case TestDataPackage.TEST_DATA__STRING_MAX8:
			setStringMax8((String) newValue);
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
		case TestDataPackage.TEST_DATA__STRING:
			setString(STRING_EDEFAULT);
			return;
		case TestDataPackage.TEST_DATA__BOOLEAN:
			setBoolean(BOOLEAN_EDEFAULT);
			return;
		case TestDataPackage.TEST_DATA__INTEGER:
			setInteger(INTEGER_EDEFAULT);
			return;
		case TestDataPackage.TEST_DATA__LONG:
			setLong(LONG_EDEFAULT);
			return;
		case TestDataPackage.TEST_DATA__FLOAT:
			setFloat(FLOAT_EDEFAULT);
			return;
		case TestDataPackage.TEST_DATA__DOUBLE:
			setDouble(DOUBLE_EDEFAULT);
			return;
		case TestDataPackage.TEST_DATA__STRING_MAX8:
			setStringMax8(STRING_MAX8_EDEFAULT);
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
		case TestDataPackage.TEST_DATA__STRING:
			return STRING_EDEFAULT == null ? string != null : !STRING_EDEFAULT.equals(string);
		case TestDataPackage.TEST_DATA__BOOLEAN:
			return boolean_ != BOOLEAN_EDEFAULT;
		case TestDataPackage.TEST_DATA__INTEGER:
			return integer != INTEGER_EDEFAULT;
		case TestDataPackage.TEST_DATA__LONG:
			return long_ != LONG_EDEFAULT;
		case TestDataPackage.TEST_DATA__FLOAT:
			return float_ != FLOAT_EDEFAULT;
		case TestDataPackage.TEST_DATA__DOUBLE:
			return double_ != DOUBLE_EDEFAULT;
		case TestDataPackage.TEST_DATA__STRING_MAX8:
			return STRING_MAX8_EDEFAULT == null ? stringMax8 != null : !STRING_MAX8_EDEFAULT.equals(stringMax8);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		final StringBuffer result = new StringBuffer(super.toString());
		result.append(" (string: "); //$NON-NLS-1$
		result.append(string);
		result.append(", boolean: "); //$NON-NLS-1$
		result.append(boolean_);
		result.append(", integer: "); //$NON-NLS-1$
		result.append(integer);
		result.append(", long: "); //$NON-NLS-1$
		result.append(long_);
		result.append(", float: "); //$NON-NLS-1$
		result.append(float_);
		result.append(", double: "); //$NON-NLS-1$
		result.append(double_);
		result.append(", stringMax8: "); //$NON-NLS-1$
		result.append(stringMax8);
		result.append(')');
		return result.toString();
	}

} // TestDataImpl
