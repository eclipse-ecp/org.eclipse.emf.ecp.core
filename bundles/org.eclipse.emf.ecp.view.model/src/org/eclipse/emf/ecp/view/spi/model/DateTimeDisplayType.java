/**
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Date Time Display Type</b></em>',
 * and utility methods for working with them.
 * 
 * @since 1.8
 *        <!-- end-user-doc -->
 * @see org.eclipse.emf.ecp.view.spi.model.VViewPackage#getDateTimeDisplayType()
 * @model
 * @generated
 */
public enum DateTimeDisplayType implements Enumerator {
	/**
	 * The '<em><b>Time And Date</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #TIME_AND_DATE_VALUE
	 * @generated
	 * @ordered
	 */
	TIME_AND_DATE(0, "TimeAndDate", "TimeAndDate"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Time Only</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #TIME_ONLY_VALUE
	 * @generated
	 * @ordered
	 */
	TIME_ONLY(1, "TimeOnly", "TimeOnly"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Date Only</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #DATE_ONLY_VALUE
	 * @generated
	 * @ordered
	 */
	DATE_ONLY(2, "DateOnly", "DateOnly"); //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Time And Date</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Time And Date</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #TIME_AND_DATE
	 * @model name="TimeAndDate"
	 * @generated
	 * @ordered
	 */
	public static final int TIME_AND_DATE_VALUE = 0;

	/**
	 * The '<em><b>Time Only</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Time Only</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #TIME_ONLY
	 * @model name="TimeOnly"
	 * @generated
	 * @ordered
	 */
	public static final int TIME_ONLY_VALUE = 1;

	/**
	 * The '<em><b>Date Only</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Date Only</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #DATE_ONLY
	 * @model name="DateOnly"
	 * @generated
	 * @ordered
	 */
	public static final int DATE_ONLY_VALUE = 2;

	/**
	 * An array of all the '<em><b>Date Time Display Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static final DateTimeDisplayType[] VALUES_ARRAY = new DateTimeDisplayType[] {
		TIME_AND_DATE,
		TIME_ONLY,
		DATE_ONLY,
	};

	/**
	 * A public read-only list of all the '<em><b>Date Time Display Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static final List<DateTimeDisplayType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Date Time Display Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static DateTimeDisplayType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			DateTimeDisplayType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Date Time Display Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static DateTimeDisplayType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			DateTimeDisplayType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Date Time Display Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static DateTimeDisplayType get(int value) {
		switch (value) {
		case TIME_AND_DATE_VALUE:
			return TIME_AND_DATE;
		case TIME_ONLY_VALUE:
			return TIME_ONLY;
		case DATE_ONLY_VALUE:
			return DATE_ONLY;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private DateTimeDisplayType(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getLiteral() {
		return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}

} // DateTimeDisplayType
