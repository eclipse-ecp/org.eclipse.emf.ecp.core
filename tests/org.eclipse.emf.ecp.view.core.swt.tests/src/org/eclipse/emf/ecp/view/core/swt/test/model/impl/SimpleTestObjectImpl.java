/**
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 * Lucas Koehler - extension for EnumComboViewerRenderer_PTest
 */
package org.eclipse.emf.ecp.view.core.swt.test.model.impl;

import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecp.view.core.swt.test.model.InnerObject;
import org.eclipse.emf.ecp.view.core.swt.test.model.SimpleTestObject;
import org.eclipse.emf.ecp.view.core.swt.test.model.TestEnum;
import org.eclipse.emf.ecp.view.core.swt.test.model.TestPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Simple Test Object</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.core.swt.test.model.impl.SimpleTestObjectImpl#getDate <em>Date</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.core.swt.test.model.impl.SimpleTestObjectImpl#getXmlDate <em>Xml Date</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.core.swt.test.model.impl.SimpleTestObjectImpl#getMyEnum <em>My Enum</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.core.swt.test.model.impl.SimpleTestObjectImpl#getInner <em>Inner</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SimpleTestObjectImpl extends MinimalEObjectImpl.Container implements SimpleTestObject {
	/**
	 * The default value of the '{@link #getDate() <em>Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getDate()
	 * @generated
	 * @ordered
	 */
	protected static final Date DATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDate() <em>Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getDate()
	 * @generated
	 * @ordered
	 */
	protected Date date = DATE_EDEFAULT;

	/**
	 * The default value of the '{@link #getXmlDate() <em>Xml Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getXmlDate()
	 * @generated
	 * @ordered
	 */
	protected static final XMLGregorianCalendar XML_DATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getXmlDate() <em>Xml Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getXmlDate()
	 * @generated
	 * @ordered
	 */
	protected XMLGregorianCalendar xmlDate = XML_DATE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getMyEnum() <em>My Enum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getMyEnum()
	 * @generated
	 * @ordered
	 */
	protected TestEnum myEnum;

	/**
	 * The cached value of the '{@link #getInner() <em>Inner</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getInner()
	 * @generated
	 * @ordered
	 */
	protected InnerObject inner;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected SimpleTestObjectImpl() {
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
		return TestPackage.Literals.SIMPLE_TEST_OBJECT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Date getDate() {
		return date;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setDate(Date newDate) {
		final Date oldDate = date;
		date = newDate;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TestPackage.SIMPLE_TEST_OBJECT__DATE, oldDate, date));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public XMLGregorianCalendar getXmlDate() {
		return xmlDate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setXmlDate(XMLGregorianCalendar newXmlDate) {
		final XMLGregorianCalendar oldXmlDate = xmlDate;
		xmlDate = newXmlDate;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TestPackage.SIMPLE_TEST_OBJECT__XML_DATE, oldXmlDate,
				xmlDate));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public TestEnum getMyEnum() {
		return myEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setMyEnum(TestEnum newMyEnum) {
		final TestEnum oldMyEnum = myEnum;
		myEnum = newMyEnum == null ? null : newMyEnum;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TestPackage.SIMPLE_TEST_OBJECT__MY_ENUM, oldMyEnum,
				myEnum));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public InnerObject getInner() {
		return inner;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetInner(InnerObject newInner, NotificationChain msgs) {
		final InnerObject oldInner = inner;
		inner = newInner;
		if (eNotificationRequired()) {
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
				TestPackage.SIMPLE_TEST_OBJECT__INNER, oldInner, newInner);
			if (msgs == null) {
				msgs = notification;
			} else {
				msgs.add(notification);
			}
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setInner(InnerObject newInner) {
		if (newInner != inner) {
			NotificationChain msgs = null;
			if (inner != null) {
				msgs = ((InternalEObject) inner).eInverseRemove(this,
					EOPPOSITE_FEATURE_BASE - TestPackage.SIMPLE_TEST_OBJECT__INNER, null, msgs);
			}
			if (newInner != null) {
				msgs = ((InternalEObject) newInner).eInverseAdd(this,
					EOPPOSITE_FEATURE_BASE - TestPackage.SIMPLE_TEST_OBJECT__INNER, null, msgs);
			}
			msgs = basicSetInner(newInner, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TestPackage.SIMPLE_TEST_OBJECT__INNER, newInner,
				newInner));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case TestPackage.SIMPLE_TEST_OBJECT__INNER:
			return basicSetInner(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
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
		case TestPackage.SIMPLE_TEST_OBJECT__DATE:
			return getDate();
		case TestPackage.SIMPLE_TEST_OBJECT__XML_DATE:
			return getXmlDate();
		case TestPackage.SIMPLE_TEST_OBJECT__MY_ENUM:
			return getMyEnum();
		case TestPackage.SIMPLE_TEST_OBJECT__INNER:
			return getInner();
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
		case TestPackage.SIMPLE_TEST_OBJECT__DATE:
			setDate((Date) newValue);
			return;
		case TestPackage.SIMPLE_TEST_OBJECT__XML_DATE:
			setXmlDate((XMLGregorianCalendar) newValue);
			return;
		case TestPackage.SIMPLE_TEST_OBJECT__MY_ENUM:
			setMyEnum((TestEnum) newValue);
			return;
		case TestPackage.SIMPLE_TEST_OBJECT__INNER:
			setInner((InnerObject) newValue);
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
		case TestPackage.SIMPLE_TEST_OBJECT__DATE:
			setDate(DATE_EDEFAULT);
			return;
		case TestPackage.SIMPLE_TEST_OBJECT__XML_DATE:
			setXmlDate(XML_DATE_EDEFAULT);
			return;
		case TestPackage.SIMPLE_TEST_OBJECT__MY_ENUM:
			setMyEnum((TestEnum) null);
			return;
		case TestPackage.SIMPLE_TEST_OBJECT__INNER:
			setInner((InnerObject) null);
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
		case TestPackage.SIMPLE_TEST_OBJECT__DATE:
			return DATE_EDEFAULT == null ? date != null : !DATE_EDEFAULT.equals(date);
		case TestPackage.SIMPLE_TEST_OBJECT__XML_DATE:
			return XML_DATE_EDEFAULT == null ? xmlDate != null : !XML_DATE_EDEFAULT.equals(xmlDate);
		case TestPackage.SIMPLE_TEST_OBJECT__MY_ENUM:
			return myEnum != null;
		case TestPackage.SIMPLE_TEST_OBJECT__INNER:
			return inner != null;
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

		final StringBuilder result = new StringBuilder(super.toString());
		result.append(" (date: "); //$NON-NLS-1$
		result.append(date);
		result.append(", xmlDate: "); //$NON-NLS-1$
		result.append(xmlDate);
		result.append(", myEnum: "); //$NON-NLS-1$
		result.append(myEnum);
		result.append(')');
		return result.toString();
	}

} // SimpleTestObjectImpl
