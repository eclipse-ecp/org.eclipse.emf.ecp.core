/**
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate;
import org.eclipse.emf.ecp.view.template.model.VTStyle;
import org.eclipse.emf.ecp.view.template.model.VTTemplateFactory;
import org.eclipse.emf.ecp.view.template.model.VTTemplatePackage;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplate;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class VTTemplateFactoryImpl extends EFactoryImpl implements VTTemplateFactory
{
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static VTTemplateFactory init()
	{
		try
		{
			final VTTemplateFactory theTemplateFactory = (VTTemplateFactory) EPackage.Registry.INSTANCE
				.getEFactory(VTTemplatePackage.eNS_URI);
			if (theTemplateFactory != null)
			{
				return theTemplateFactory;
			}
		} catch (final Exception exception)
		{
			EcorePlugin.INSTANCE.log(exception);
		}
		return new VTTemplateFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public VTTemplateFactoryImpl()
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
		case VTTemplatePackage.VIEW_TEMPLATE:
			return createViewTemplate();
		case VTTemplatePackage.CONTROL_VALIDATION_TEMPLATE:
			return createControlValidationTemplate();
		case VTTemplatePackage.STYLE:
			return createStyle();
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
	public VTViewTemplate createViewTemplate()
	{
		final VTViewTemplateImpl viewTemplate = new VTViewTemplateImpl();
		return viewTemplate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VTControlValidationTemplate createControlValidationTemplate()
	{
		final VTControlValidationTemplateImpl controlValidationTemplate = new VTControlValidationTemplateImpl();
		return controlValidationTemplate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VTStyle createStyle()
	{
		final VTStyleImpl style = new VTStyleImpl();
		return style;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VTTemplatePackage getTemplatePackage()
	{
		return (VTTemplatePackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static VTTemplatePackage getPackage()
	{
		return VTTemplatePackage.eINSTANCE;
	}

} // VTTemplateFactoryImpl
