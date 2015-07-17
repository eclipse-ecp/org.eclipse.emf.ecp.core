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
package org.eclipse.emf.ecp.view.spi.compoundcontrol.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecp.view.spi.compoundcontrol.model.VCompoundControl;
import org.eclipse.emf.ecp.view.spi.compoundcontrol.model.VCompoundcontrolFactory;
import org.eclipse.emf.ecp.view.spi.compoundcontrol.model.VCompoundcontrolPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class VCompoundcontrolFactoryImpl extends EFactoryImpl implements VCompoundcontrolFactory
{
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static VCompoundcontrolFactory init()
	{
		try
		{
			final VCompoundcontrolFactory theCompoundcontrolFactory = (VCompoundcontrolFactory) EPackage.Registry.INSTANCE
				.getEFactory(VCompoundcontrolPackage.eNS_URI);
			if (theCompoundcontrolFactory != null)
			{
				return theCompoundcontrolFactory;
			}
		} catch (final Exception exception)
		{
			EcorePlugin.INSTANCE.log(exception);
		}
		return new VCompoundcontrolFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public VCompoundcontrolFactoryImpl()
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
		case VCompoundcontrolPackage.COMPOUND_CONTROL:
			return createCompoundControl();
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
	public VCompoundControl createCompoundControl()
	{
		final VCompoundControlImpl compoundControl = new VCompoundControlImpl();
		return compoundControl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VCompoundcontrolPackage getCompoundcontrolPackage()
	{
		return (VCompoundcontrolPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static VCompoundcontrolPackage getPackage()
	{
		return VCompoundcontrolPackage.eINSTANCE;
	}

} // VCompoundcontrolFactoryImpl
