/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.style.mandatory.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecp.view.template.style.mandatory.model.VTMandatoryFactory;
import org.eclipse.emf.ecp.view.template.style.mandatory.model.VTMandatoryPackage;
import org.eclipse.emf.ecp.view.template.style.mandatory.model.VTMandatoryStyleProperty;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class VTMandatoryFactoryImpl extends EFactoryImpl implements VTMandatoryFactory
{
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static VTMandatoryFactory init()
	{
		try
		{
			final VTMandatoryFactory theMandatoryFactory = (VTMandatoryFactory) EPackage.Registry.INSTANCE
				.getEFactory(VTMandatoryPackage.eNS_URI);
			if (theMandatoryFactory != null)
			{
				return theMandatoryFactory;
			}
		} catch (final Exception exception)
		{
			EcorePlugin.INSTANCE.log(exception);
		}
		return new VTMandatoryFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public VTMandatoryFactoryImpl()
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
		case VTMandatoryPackage.MANDATORY_STYLE_PROPERTY:
			return createMandatoryStyleProperty();
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
	public VTMandatoryStyleProperty createMandatoryStyleProperty()
	{
		final VTMandatoryStylePropertyImpl mandatoryStyleProperty = new VTMandatoryStylePropertyImpl();
		return mandatoryStyleProperty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VTMandatoryPackage getMandatoryPackage()
	{
		return (VTMandatoryPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static VTMandatoryPackage getPackage()
	{
		return VTMandatoryPackage.eINSTANCE;
	}

} // VTMandatoryFactoryImpl
