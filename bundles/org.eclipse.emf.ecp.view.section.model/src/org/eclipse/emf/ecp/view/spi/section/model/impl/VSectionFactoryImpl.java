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
package org.eclipse.emf.ecp.view.spi.section.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecp.view.spi.section.model.VSection;
import org.eclipse.emf.ecp.view.spi.section.model.VSectionFactory;
import org.eclipse.emf.ecp.view.spi.section.model.VSectionPackage;
import org.eclipse.emf.ecp.view.spi.section.model.VSectionedArea;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class VSectionFactoryImpl extends EFactoryImpl implements VSectionFactory
{
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static VSectionFactory init()
	{
		try
		{
			final VSectionFactory theSectionFactory = (VSectionFactory) EPackage.Registry.INSTANCE
				.getEFactory(VSectionPackage.eNS_URI);
			if (theSectionFactory != null)
			{
				return theSectionFactory;
			}
		} catch (final Exception exception)
		{
			EcorePlugin.INSTANCE.log(exception);
		}
		return new VSectionFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public VSectionFactoryImpl()
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
		case VSectionPackage.SECTIONED_AREA:
			return createSectionedArea();
		case VSectionPackage.SECTION:
			return createSection();
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
	public VSectionedArea createSectionedArea()
	{
		final VSectionedAreaImpl sectionedArea = new VSectionedAreaImpl();
		return sectionedArea;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VSection createSection()
	{
		final VSectionImpl section = new VSectionImpl();
		return section;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VSectionPackage getSectionPackage()
	{
		return (VSectionPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static VSectionPackage getPackage()
	{
		return VSectionPackage.eINSTANCE;
	}

} // VSectionFactoryImpl
