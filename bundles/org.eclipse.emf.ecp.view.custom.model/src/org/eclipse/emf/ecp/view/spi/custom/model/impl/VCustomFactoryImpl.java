/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.custom.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecp.view.spi.custom.model.VCustomControl;
import org.eclipse.emf.ecp.view.spi.custom.model.VCustomDomainModelReference;
import org.eclipse.emf.ecp.view.spi.custom.model.VCustomFactory;
import org.eclipse.emf.ecp.view.spi.custom.model.VCustomPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class VCustomFactoryImpl extends EFactoryImpl implements VCustomFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static VCustomFactory init() {
		try
		{
			final VCustomFactory theCustomFactory = (VCustomFactory) EPackage.Registry.INSTANCE
				.getEFactory(VCustomPackage.eNS_URI);
			if (theCustomFactory != null)
			{
				return theCustomFactory;
			}
		} catch (final Exception exception)
		{
			EcorePlugin.INSTANCE.log(exception);
		}
		return new VCustomFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public VCustomFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID())
		{
		case VCustomPackage.CUSTOM_CONTROL:
			return createCustomControl();
		case VCustomPackage.CUSTOM_DOMAIN_MODEL_REFERENCE:
			return createCustomDomainModelReference();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VCustomControl createCustomControl()
	{
		final VCustomControlImpl customControl = new VCustomControlImpl();
		return customControl;
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VCustomDomainModelReference createCustomDomainModelReference()
	{
		final VCustomDomainModelReferenceImpl customDomainModelReference = new VCustomDomainModelReferenceImpl();
		return customDomainModelReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VCustomPackage getCustomPackage() {
		return (VCustomPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static VCustomPackage getPackage() {
		return VCustomPackage.eINSTANCE;
	}

} // VCustomFactoryImpl
