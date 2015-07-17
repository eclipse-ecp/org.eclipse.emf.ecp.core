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
package org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.VTDomainModelReferenceSelector;
import org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.VTDomainmodelreferenceFactory;
import org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.VTDomainmodelreferencePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class VTDomainmodelreferenceFactoryImpl extends EFactoryImpl implements VTDomainmodelreferenceFactory
{
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static VTDomainmodelreferenceFactory init()
	{
		try
		{
			final VTDomainmodelreferenceFactory theDomainmodelreferenceFactory = (VTDomainmodelreferenceFactory) EPackage.Registry.INSTANCE
				.getEFactory(VTDomainmodelreferencePackage.eNS_URI);
			if (theDomainmodelreferenceFactory != null)
			{
				return theDomainmodelreferenceFactory;
			}
		} catch (final Exception exception)
		{
			EcorePlugin.INSTANCE.log(exception);
		}
		return new VTDomainmodelreferenceFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public VTDomainmodelreferenceFactoryImpl()
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
		case VTDomainmodelreferencePackage.DOMAIN_MODEL_REFERENCE_SELECTOR:
			return createDomainModelReferenceSelector();
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
	public VTDomainModelReferenceSelector createDomainModelReferenceSelector()
	{
		final VTDomainModelReferenceSelectorImpl domainModelReferenceSelector = new VTDomainModelReferenceSelectorImpl();
		return domainModelReferenceSelector;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VTDomainmodelreferencePackage getDomainmodelreferencePackage()
	{
		return (VTDomainmodelreferencePackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static VTDomainmodelreferencePackage getPackage()
	{
		return VTDomainmodelreferencePackage.eINSTANCE;
	}

} // VTDomainmodelreferenceFactoryImpl
