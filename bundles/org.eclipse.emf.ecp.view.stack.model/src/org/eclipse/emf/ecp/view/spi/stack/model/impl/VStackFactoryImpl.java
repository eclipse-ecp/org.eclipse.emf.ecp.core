/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.stack.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecp.view.spi.stack.model.VStackFactory;
import org.eclipse.emf.ecp.view.spi.stack.model.VStackItem;
import org.eclipse.emf.ecp.view.spi.stack.model.VStackLayout;
import org.eclipse.emf.ecp.view.spi.stack.model.VStackPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class VStackFactoryImpl extends EFactoryImpl implements VStackFactory
{
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static VStackFactory init()
	{
		try
		{
			final VStackFactory theStackFactory = (VStackFactory) EPackage.Registry.INSTANCE
				.getEFactory(VStackPackage.eNS_URI);
			if (theStackFactory != null)
			{
				return theStackFactory;
			}
		} catch (final Exception exception)
		{
			EcorePlugin.INSTANCE.log(exception);
		}
		return new VStackFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public VStackFactoryImpl()
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
		case VStackPackage.STACK_LAYOUT:
			return createStackLayout();
		case VStackPackage.STACK_ITEM:
			return createStackItem();
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
	public VStackLayout createStackLayout()
	{
		final VStackLayoutImpl stackLayout = new VStackLayoutImpl();
		return stackLayout;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VStackItem createStackItem()
	{
		final VStackItemImpl stackItem = new VStackItemImpl();
		return stackItem;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VStackPackage getStackPackage()
	{
		return (VStackPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static VStackPackage getPackage()
	{
		return VStackPackage.eINSTANCE;
	}

} // VStackFactoryImpl
