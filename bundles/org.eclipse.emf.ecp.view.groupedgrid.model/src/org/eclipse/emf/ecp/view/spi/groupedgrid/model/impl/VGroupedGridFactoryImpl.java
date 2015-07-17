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
package org.eclipse.emf.ecp.view.spi.groupedgrid.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecp.view.spi.groupedgrid.model.VGroup;
import org.eclipse.emf.ecp.view.spi.groupedgrid.model.VGroupedGrid;
import org.eclipse.emf.ecp.view.spi.groupedgrid.model.VGroupedGridFactory;
import org.eclipse.emf.ecp.view.spi.groupedgrid.model.VGroupedGridPackage;
import org.eclipse.emf.ecp.view.spi.groupedgrid.model.VRow;
import org.eclipse.emf.ecp.view.spi.groupedgrid.model.VSpan;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 * @since 1.2
 */
public class VGroupedGridFactoryImpl extends EFactoryImpl implements VGroupedGridFactory
{
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static VGroupedGridFactory init()
	{
		try
		{
			final VGroupedGridFactory theGroupedGridFactory = (VGroupedGridFactory) EPackage.Registry.INSTANCE
				.getEFactory(VGroupedGridPackage.eNS_URI);
			if (theGroupedGridFactory != null)
			{
				return theGroupedGridFactory;
			}
		} catch (final Exception exception)
		{
			EcorePlugin.INSTANCE.log(exception);
		}
		return new VGroupedGridFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public VGroupedGridFactoryImpl()
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
		case VGroupedGridPackage.GROUPED_GRID:
			return createGroupedGrid();
		case VGroupedGridPackage.GROUP:
			return createGroup();
		case VGroupedGridPackage.ROW:
			return createRow();
		case VGroupedGridPackage.SPAN:
			return createSpan();
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
	public VGroupedGrid createGroupedGrid()
	{
		final VGroupedGridImpl groupedGrid = new VGroupedGridImpl();
		return groupedGrid;
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
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VRow createRow()
	{
		final VRowImpl row = new VRowImpl();
		return row;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VSpan createSpan()
	{
		final VSpanImpl span = new VSpanImpl();
		return span;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VGroupedGridPackage getGroupedGridPackage()
	{
		return (VGroupedGridPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static VGroupedGridPackage getPackage()
	{
		return VGroupedGridPackage.eINSTANCE;
	}

} // VGroupedGridFactoryImpl
