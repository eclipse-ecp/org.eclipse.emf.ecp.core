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
package org.eclipse.emf.ecp.view.treemasterdetail.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecp.view.treemasterdetail.model.VTreeMasterDetail;
import org.eclipse.emf.ecp.view.treemasterdetail.model.VTreeMasterDetailFactory;
import org.eclipse.emf.ecp.view.treemasterdetail.model.VTreeMasterDetailPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class VTreeMasterDetailFactoryImpl extends EFactoryImpl implements VTreeMasterDetailFactory
{
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static VTreeMasterDetailFactory init()
	{
		try
		{
			VTreeMasterDetailFactory theTreeMasterDetailFactory = (VTreeMasterDetailFactory) EPackage.Registry.INSTANCE
				.getEFactory(VTreeMasterDetailPackage.eNS_URI);
			if (theTreeMasterDetailFactory != null)
			{
				return theTreeMasterDetailFactory;
			}
		} catch (Exception exception)
		{
			EcorePlugin.INSTANCE.log(exception);
		}
		return new VTreeMasterDetailFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public VTreeMasterDetailFactoryImpl()
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
		case VTreeMasterDetailPackage.TREE_MASTER_DETAIL:
			return createTreeMasterDetail();
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
	public VTreeMasterDetail createTreeMasterDetail()
	{
		VTreeMasterDetailImpl treeMasterDetail = new VTreeMasterDetailImpl();
		return treeMasterDetail;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public VTreeMasterDetailPackage getTreeMasterDetailPackage()
	{
		return (VTreeMasterDetailPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static VTreeMasterDetailPackage getPackage()
	{
		return VTreeMasterDetailPackage.eINSTANCE;
	}

} // VTreeMasterDetailFactoryImpl
