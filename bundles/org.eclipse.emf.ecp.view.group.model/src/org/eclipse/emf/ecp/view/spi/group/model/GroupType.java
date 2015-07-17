/**
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.group.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Type</b></em>',
 * and utility methods for working with them.
 *
 * @since 1.4
 *        <!-- end-user-doc -->
 *
 * @see org.eclipse.emf.ecp.view.spi.group.model.VGroupPackage#getGroupType()
 * @model
 * @generated
 */
public enum GroupType implements Enumerator
{
	/**
	 * The '<em><b>Normal</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #NORMAL_VALUE
	 * @generated
	 * @ordered
	 */
	NORMAL(0, "Normal", "Normal"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Embedded</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #EMBEDDED_VALUE
	 * @generated
	 * @ordered
	 */
	EMBEDDED(1, "Embedded", "Embedded"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Collapsible</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #COLLAPSIBLE_VALUE
	 * @generated
	 * @ordered
	 */
	COLLAPSIBLE(2, "Collapsible", "Collapsible"); //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Normal</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Normal</b></em>' literal object isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @see #NORMAL
	 * @model name="Normal"
	 * @generated
	 * @ordered
	 */
	public static final int NORMAL_VALUE = 0;

	/**
	 * The '<em><b>Embedded</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Embedded</b></em>' literal object isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @see #EMBEDDED
	 * @model name="Embedded"
	 * @generated
	 * @ordered
	 */
	public static final int EMBEDDED_VALUE = 1;

	/**
	 * The '<em><b>Collapsible</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Collapsible</b></em>' literal object isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @see #COLLAPSIBLE
	 * @model name="Collapsible"
	 * @generated
	 * @ordered
	 */
	public static final int COLLAPSIBLE_VALUE = 2;

	/**
	 * An array of all the '<em><b>Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private static final GroupType[] VALUES_ARRAY =
		new GroupType[]
		{
			NORMAL,
			EMBEDDED,
			COLLAPSIBLE,
		};

	/**
	 * A public read-only list of all the '<em><b>Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static final List<GroupType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static GroupType get(String literal)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			final GroupType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static GroupType getByName(String name)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			final GroupType result = VALUES_ARRAY[i];
			if (result.getName().equals(name))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static GroupType get(int value)
	{
		switch (value)
		{
		case NORMAL_VALUE:
			return NORMAL;
		case EMBEDDED_VALUE:
			return EMBEDDED;
		case COLLAPSIBLE_VALUE:
			return COLLAPSIBLE;
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
	private GroupType(int value, String name, String literal)
	{
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
	public int getValue()
	{
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getName()
	{
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getLiteral()
	{
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
	public String toString()
	{
		return literal;
	}

} // GroupType
