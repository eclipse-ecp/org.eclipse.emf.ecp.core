/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.keyattributedmr.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecp.view.spi.keyattributedmr.model.VKeyAttributeDomainModelReference;
import org.eclipse.emf.ecp.view.spi.keyattributedmr.model.VKeyattributedmrFactory;
import org.eclipse.emf.ecp.view.spi.keyattributedmr.model.VKeyattributedmrPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 *
 * @generated
 */
public class VKeyattributedmrFactoryImpl extends EFactoryImpl implements
	VKeyattributedmrFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	public static VKeyattributedmrFactory init() {
		try
		{
			final VKeyattributedmrFactory theKeyattributedmrFactory = (VKeyattributedmrFactory) EPackage.Registry.INSTANCE
				.getEFactory(VKeyattributedmrPackage.eNS_URI);
			if (theKeyattributedmrFactory != null)
			{
				return theKeyattributedmrFactory;
			}
		} catch (final Exception exception)
		{
			EcorePlugin.INSTANCE.log(exception);
		}
		return new VKeyattributedmrFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	public VKeyattributedmrFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID())
		{
		case VKeyattributedmrPackage.KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE:
			return createKeyAttributeDomainModelReference();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VKeyAttributeDomainModelReference createKeyAttributeDomainModelReference() {
		final VKeyAttributeDomainModelReferenceImpl keyAttributeDomainModelReference = new VKeyAttributeDomainModelReferenceImpl();
		return keyAttributeDomainModelReference;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VKeyattributedmrPackage getKeyattributedmrPackage() {
		return (VKeyattributedmrPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static VKeyattributedmrPackage getPackage() {
		return VKeyattributedmrPackage.eINSTANCE;
	}

} // VKeyattributedmrFactoryImpl
