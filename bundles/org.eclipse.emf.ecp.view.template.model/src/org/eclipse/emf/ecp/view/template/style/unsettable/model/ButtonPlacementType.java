/**
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.style.unsettable.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Button Placement Type</b></em>',
 * and utility methods for working with them.
 * 
 * @since 1.14
 *        <!-- end-user-doc -->
 *
 * @see org.eclipse.emf.ecp.view.template.style.unsettable.model.VTUnsettablePackage#getButtonPlacementType()
 * @model
 * @generated
 */
public enum ButtonPlacementType implements Enumerator {
	/**
	 * The '<em><b>RIGHT OF LABEL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #RIGHT_OF_LABEL_VALUE
	 * @generated
	 * @ordered
	 */
	RIGHT_OF_LABEL(0, "RIGHT_OF_LABEL", "RIGHT_OF_LABEL"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>LEFT OF LABEL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #LEFT_OF_LABEL_VALUE
	 * @generated
	 * @ordered
	 */
	LEFT_OF_LABEL(1, "LEFT_OF_LABEL", "LEFT_OF_LABEL"); //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>RIGHT OF LABEL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>RIGHT OF LABEL</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @see #RIGHT_OF_LABEL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int RIGHT_OF_LABEL_VALUE = 0;

	/**
	 * The '<em><b>LEFT OF LABEL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>LEFT OF LABEL</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @see #LEFT_OF_LABEL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int LEFT_OF_LABEL_VALUE = 1;

	/**
	 * An array of all the '<em><b>Button Placement Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private static final ButtonPlacementType[] VALUES_ARRAY = new ButtonPlacementType[] {
		RIGHT_OF_LABEL,
		LEFT_OF_LABEL,
	};

	/**
	 * A public read-only list of all the '<em><b>Button Placement Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static final List<ButtonPlacementType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Button Placement Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ButtonPlacementType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			final ButtonPlacementType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Button Placement Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ButtonPlacementType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			final ButtonPlacementType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Button Placement Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ButtonPlacementType get(int value) {
		switch (value) {
		case RIGHT_OF_LABEL_VALUE:
			return RIGHT_OF_LABEL;
		case LEFT_OF_LABEL_VALUE:
			return LEFT_OF_LABEL;
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
	private ButtonPlacementType(int value, String name, String literal) {
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

} // ButtonPlacementType
