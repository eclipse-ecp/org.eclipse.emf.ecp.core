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
package org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.VTViewModelElementFactory;
import org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.VTViewModelElementPackage;
import org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.VTViewModelElementSelector;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class VTViewModelElementFactoryImpl extends EFactoryImpl implements VTViewModelElementFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static VTViewModelElementFactory init() {
		try
		{
			final VTViewModelElementFactory theViewModelElementFactory = (VTViewModelElementFactory) EPackage.Registry.INSTANCE
				.getEFactory(VTViewModelElementPackage.eNS_URI);
			if (theViewModelElementFactory != null)
			{
				return theViewModelElementFactory;
			}
		} catch (final Exception exception)
		{
			EcorePlugin.INSTANCE.log(exception);
		}
		return new VTViewModelElementFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public VTViewModelElementFactoryImpl() {
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
		case VTViewModelElementPackage.VIEW_MODEL_ELEMENT_SELECTOR:
			return createViewModelElementSelector();
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
	public VTViewModelElementSelector createViewModelElementSelector() {
		final VTViewModelElementSelectorImpl viewModelElementSelector = new VTViewModelElementSelectorImpl();
		return viewModelElementSelector;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VTViewModelElementPackage getViewModelElementPackage() {
		return (VTViewModelElementPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static VTViewModelElementPackage getPackage() {
		return VTViewModelElementPackage.eINSTANCE;
	}

} // VTViewModelElementFactoryImpl
