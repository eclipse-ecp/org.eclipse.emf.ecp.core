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
package org.eclipse.emf.ecp.view.spi.group.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecp.view.spi.group.model.GroupLabelAlignment;
import org.eclipse.emf.ecp.view.spi.group.model.GroupType;
import org.eclipse.emf.ecp.view.spi.group.model.VGroup;
import org.eclipse.emf.ecp.view.spi.group.model.VGroupFactory;
import org.eclipse.emf.ecp.view.spi.group.model.VGroupPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class VGroupFactoryImpl extends EFactoryImpl implements VGroupFactory
{
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static VGroupFactory init()
	{
		try
		{
			final VGroupFactory theGroupFactory = (VGroupFactory) EPackage.Registry.INSTANCE
				.getEFactory(VGroupPackage.eNS_URI);
			if (theGroupFactory != null)
			{
				return theGroupFactory;
			}
		} catch (final Exception exception)
		{
			EcorePlugin.INSTANCE.log(exception);
		}
		return new VGroupFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public VGroupFactoryImpl()
	{
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass)
	{
		switch (eClass.getClassifierID())
		{
		case VGroupPackage.GROUP:
			return createGroup();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue)
	{
		switch (eDataType.getClassifierID())
		{
		case VGroupPackage.GROUP_TYPE:
			return createGroupTypeFromString(eDataType, initialValue);
		case VGroupPackage.GROUP_LABEL_ALIGNMENT:
			return createGroupLabelAlignmentFromString(eDataType, initialValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue)
	{
		switch (eDataType.getClassifierID())
		{
		case VGroupPackage.GROUP_TYPE:
			return convertGroupTypeToString(eDataType, instanceValue);
		case VGroupPackage.GROUP_LABEL_ALIGNMENT:
			return convertGroupLabelAlignmentToString(eDataType, instanceValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VGroup createGroup()
	{
		final VGroupImpl group = new VGroupImpl();
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.4
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public GroupType createGroupTypeFromString(EDataType eDataType, String initialValue)
	{
		final GroupType result = GroupType.get(initialValue);
		if (result == null)
		{
			throw new IllegalArgumentException(
				"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.4
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public String convertGroupTypeToString(EDataType eDataType, Object instanceValue)
	{
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public GroupLabelAlignment createGroupLabelAlignmentFromString(EDataType eDataType, String initialValue)
	{
		final GroupLabelAlignment result = GroupLabelAlignment.get(initialValue);
		if (result == null)
		{
			throw new IllegalArgumentException(
				"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public String convertGroupLabelAlignmentToString(EDataType eDataType, Object instanceValue)
	{
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VGroupPackage getGroupPackage()
	{
		return (VGroupPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static VGroupPackage getPackage()
	{
		return VGroupPackage.eINSTANCE;
	}

} // VGroupFactoryImpl
