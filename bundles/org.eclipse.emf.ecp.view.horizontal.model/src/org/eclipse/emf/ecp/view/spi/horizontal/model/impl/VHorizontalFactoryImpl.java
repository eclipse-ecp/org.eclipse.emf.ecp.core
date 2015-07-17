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
package org.eclipse.emf.ecp.view.spi.horizontal.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecp.view.spi.horizontal.model.VHorizontalFactory;
import org.eclipse.emf.ecp.view.spi.horizontal.model.VHorizontalLayout;
import org.eclipse.emf.ecp.view.spi.horizontal.model.VHorizontalPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 *
 * @since 1.2
 *        <!-- end-user-doc -->
 *
 * @generated
 */
public class VHorizontalFactoryImpl extends EFactoryImpl implements VHorizontalFactory
{
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static VHorizontalFactory init()
	{
		try
		{
			final VHorizontalFactory theHorizontalFactory = (VHorizontalFactory) EPackage.Registry.INSTANCE
				.getEFactory(VHorizontalPackage.eNS_URI);
			if (theHorizontalFactory != null)
			{
				return theHorizontalFactory;
			}
		} catch (final Exception exception)
		{
			EcorePlugin.INSTANCE.log(exception);
		}
		return new VHorizontalFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public VHorizontalFactoryImpl()
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
		case VHorizontalPackage.HORIZONTAL_LAYOUT:
			return createHorizontalLayout();
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
	public VHorizontalLayout createHorizontalLayout()
	{
		final VHorizontalLayoutImpl horizontalLayout = new VHorizontalLayoutImpl();
		return horizontalLayout;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VHorizontalPackage getHorizontalPackage()
	{
		return (VHorizontalPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static VHorizontalPackage getPackage()
	{
		return VHorizontalPackage.eINSTANCE;
	}

} // VHorizontalFactoryImpl
