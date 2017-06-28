/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.validation.test.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecp.view.validation.test.model.Gender;
import org.eclipse.emf.ecp.view.validation.test.model.Person;
import org.eclipse.emf.ecp.view.validation.test.model.TestPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Person</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.validation.test.model.impl.PersonImpl#getFirstName <em>First Name</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.validation.test.model.impl.PersonImpl#getGender <em>Gender</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.validation.test.model.impl.PersonImpl#getLastName <em>Last Name</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.validation.test.model.impl.PersonImpl#getCustom <em>Custom</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.validation.test.model.impl.PersonImpl#getAge <em>Age</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PersonImpl extends EObjectImpl implements Person {
	/**
	 * The default value of the '{@link #getFirstName() <em>First Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getFirstName()
	 * @generated
	 * @ordered
	 */
	protected static final String FIRST_NAME_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getFirstName() <em>First Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getFirstName()
	 * @generated
	 * @ordered
	 */
	protected String firstName = FIRST_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getGender() <em>Gender</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getGender()
	 * @generated
	 * @ordered
	 */
	protected static final Gender GENDER_EDEFAULT = Gender.MALE;
	/**
	 * The cached value of the '{@link #getGender() <em>Gender</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getGender()
	 * @generated
	 * @ordered
	 */
	protected Gender gender = GENDER_EDEFAULT;

	/**
	 * The default value of the '{@link #getLastName() <em>Last Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getLastName()
	 * @generated
	 * @ordered
	 */
	protected static final String LAST_NAME_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getLastName() <em>Last Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getLastName()
	 * @generated
	 * @ordered
	 */
	protected String lastName = LAST_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getCustom() <em>Custom</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getCustom()
	 * @generated
	 * @ordered
	 */
	protected static final String CUSTOM_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getCustom() <em>Custom</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getCustom()
	 * @generated
	 * @ordered
	 */
	protected String custom = CUSTOM_EDEFAULT;
	/**
	 * The default value of the '{@link #getAge() <em>Age</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getAge()
	 * @generated
	 * @ordered
	 */
	protected static final Integer AGE_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getAge() <em>Age</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getAge()
	 * @generated
	 * @ordered
	 */
	protected Integer age = AGE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected PersonImpl() {
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
		return TestPackage.Literals.PERSON;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getFirstName() {
		return firstName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setFirstName(String newFirstName) {
		final String oldFirstName = firstName;
		firstName = newFirstName;
		if (eNotificationRequired()) {
			eNotify(
				new ENotificationImpl(this, Notification.SET, TestPackage.PERSON__FIRST_NAME, oldFirstName, firstName));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Gender getGender() {
		return gender;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setGender(Gender newGender) {
		final Gender oldGender = gender;
		gender = newGender == null ? GENDER_EDEFAULT : newGender;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TestPackage.PERSON__GENDER, oldGender, gender));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getLastName() {
		return lastName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setLastName(String newLastName) {
		final String oldLastName = lastName;
		lastName = newLastName;
		if (eNotificationRequired()) {
			eNotify(
				new ENotificationImpl(this, Notification.SET, TestPackage.PERSON__LAST_NAME, oldLastName, lastName));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getCustom() {
		return custom;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setCustom(String newCustom) {
		final String oldCustom = custom;
		custom = newCustom;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TestPackage.PERSON__CUSTOM, oldCustom, custom));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Integer getAge() {
		return age;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setAge(Integer newAge) {
		final Integer oldAge = age;
		age = newAge;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TestPackage.PERSON__AGE, oldAge, age));
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
		case TestPackage.PERSON__FIRST_NAME:
			return getFirstName();
		case TestPackage.PERSON__GENDER:
			return getGender();
		case TestPackage.PERSON__LAST_NAME:
			return getLastName();
		case TestPackage.PERSON__CUSTOM:
			return getCustom();
		case TestPackage.PERSON__AGE:
			return getAge();
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
		case TestPackage.PERSON__FIRST_NAME:
			setFirstName((String) newValue);
			return;
		case TestPackage.PERSON__GENDER:
			setGender((Gender) newValue);
			return;
		case TestPackage.PERSON__LAST_NAME:
			setLastName((String) newValue);
			return;
		case TestPackage.PERSON__CUSTOM:
			setCustom((String) newValue);
			return;
		case TestPackage.PERSON__AGE:
			setAge((Integer) newValue);
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
		case TestPackage.PERSON__FIRST_NAME:
			setFirstName(FIRST_NAME_EDEFAULT);
			return;
		case TestPackage.PERSON__GENDER:
			setGender(GENDER_EDEFAULT);
			return;
		case TestPackage.PERSON__LAST_NAME:
			setLastName(LAST_NAME_EDEFAULT);
			return;
		case TestPackage.PERSON__CUSTOM:
			setCustom(CUSTOM_EDEFAULT);
			return;
		case TestPackage.PERSON__AGE:
			setAge(AGE_EDEFAULT);
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
		case TestPackage.PERSON__FIRST_NAME:
			return FIRST_NAME_EDEFAULT == null ? firstName != null : !FIRST_NAME_EDEFAULT.equals(firstName);
		case TestPackage.PERSON__GENDER:
			return gender != GENDER_EDEFAULT;
		case TestPackage.PERSON__LAST_NAME:
			return LAST_NAME_EDEFAULT == null ? lastName != null : !LAST_NAME_EDEFAULT.equals(lastName);
		case TestPackage.PERSON__CUSTOM:
			return CUSTOM_EDEFAULT == null ? custom != null : !CUSTOM_EDEFAULT.equals(custom);
		case TestPackage.PERSON__AGE:
			return AGE_EDEFAULT == null ? age != null : !AGE_EDEFAULT.equals(age);
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
		result.append(" (firstName: ");
		result.append(firstName);
		result.append(", gender: ");
		result.append(gender);
		result.append(", lastName: ");
		result.append(lastName);
		result.append(", custom: ");
		result.append(custom);
		result.append(", age: ");
		result.append(age);
		result.append(')');
		return result.toString();
	}

} // PersonImpl
