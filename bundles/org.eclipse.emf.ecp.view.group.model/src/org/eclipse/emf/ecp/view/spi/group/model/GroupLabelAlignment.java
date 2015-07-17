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
 * A representation of the literals of the enumeration '<em><b>Label Alignment</b></em>',
 * and utility methods for working with them.
 *
 * @since 1.3
 *        <!-- end-user-doc -->
 *
 * @see org.eclipse.emf.ecp.view.spi.group.model.VGroupPackage#getGroupLabelAlignment()
 * @model
 * @generated
 */
public enum GroupLabelAlignment implements Enumerator
{
	/**
	 * The '<em><b>Label Aligned</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #LABEL_ALIGNED_VALUE
	 * @generated
	 * @ordered
	 */
	LABEL_ALIGNED(0, "LabelAligned", "LabelAligned"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Input Aligned</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #INPUT_ALIGNED_VALUE
	 * @generated
	 * @ordered
	 */
	INPUT_ALIGNED(1, "InputAligned", "InputAligned"); //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Label Aligned</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Label Aligned</b></em>' literal object isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @see #LABEL_ALIGNED
	 * @model name="LabelAligned"
	 * @generated
	 * @ordered
	 */
	public static final int LABEL_ALIGNED_VALUE = 0;

	/**
	 * The '<em><b>Input Aligned</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Input Aligned</b></em>' literal object isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @see #INPUT_ALIGNED
	 * @model name="InputAligned"
	 * @generated
	 * @ordered
	 */
	public static final int INPUT_ALIGNED_VALUE = 1;

	/**
	 * An array of all the '<em><b>Label Alignment</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private static final GroupLabelAlignment[] VALUES_ARRAY =
		new GroupLabelAlignment[]
		{
			LABEL_ALIGNED,
			INPUT_ALIGNED,
		};

	/**
	 * A public read-only list of all the '<em><b>Label Alignment</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static final List<GroupLabelAlignment> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Label Alignment</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static GroupLabelAlignment get(String literal)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			final GroupLabelAlignment result = VALUES_ARRAY[i];
			if (result.toString().equals(literal))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Label Alignment</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static GroupLabelAlignment getByName(String name)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			final GroupLabelAlignment result = VALUES_ARRAY[i];
			if (result.getName().equals(name))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Label Alignment</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static GroupLabelAlignment get(int value)
	{
		switch (value)
		{
		case LABEL_ALIGNED_VALUE:
			return LABEL_ALIGNED;
		case INPUT_ALIGNED_VALUE:
			return INPUT_ALIGNED;
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
	private GroupLabelAlignment(int value, String name, String literal)
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

} // GroupLabelAlignment
