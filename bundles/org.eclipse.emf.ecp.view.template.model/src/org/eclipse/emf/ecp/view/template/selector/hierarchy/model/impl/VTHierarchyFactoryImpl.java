/**
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.selector.hierarchy.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecp.view.template.selector.hierarchy.model.VTHierarchyFactory;
import org.eclipse.emf.ecp.view.template.selector.hierarchy.model.VTHierarchyPackage;
import org.eclipse.emf.ecp.view.template.selector.hierarchy.model.VTHierarchySelector;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class VTHierarchyFactoryImpl extends EFactoryImpl implements VTHierarchyFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static VTHierarchyFactory init() {
		try {
			final VTHierarchyFactory theHierarchyFactory = (VTHierarchyFactory) EPackage.Registry.INSTANCE
				.getEFactory(VTHierarchyPackage.eNS_URI);
			if (theHierarchyFactory != null) {
				return theHierarchyFactory;
			}
		} catch (final Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new VTHierarchyFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public VTHierarchyFactoryImpl() {
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
		switch (eClass.getClassifierID()) {
		case VTHierarchyPackage.HIERARCHY_SELECTOR:
			return createHierarchySelector();
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
	public VTHierarchySelector createHierarchySelector() {
		final VTHierarchySelectorImpl hierarchySelector = new VTHierarchySelectorImpl();
		return hierarchySelector;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VTHierarchyPackage getHierarchyPackage() {
		return (VTHierarchyPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static VTHierarchyPackage getPackage() {
		return VTHierarchyPackage.eINSTANCE;
	}

} // VTHierarchyFactoryImpl
