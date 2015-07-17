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
package org.eclipse.emf.ecp.view.template.style.tableValidation.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationFactory;
import org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationPackage;
import org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationStyleProperty;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class VTTableValidationFactoryImpl extends EFactoryImpl implements VTTableValidationFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static VTTableValidationFactory init() {
		try {
			final VTTableValidationFactory theTableValidationFactory = (VTTableValidationFactory) EPackage.Registry.INSTANCE
				.getEFactory(VTTableValidationPackage.eNS_URI);
			if (theTableValidationFactory != null) {
				return theTableValidationFactory;
			}
		} catch (final Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new VTTableValidationFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public VTTableValidationFactoryImpl() {
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
		case VTTableValidationPackage.TABLE_VALIDATION_STYLE_PROPERTY:
			return createTableValidationStyleProperty();
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
	public VTTableValidationStyleProperty createTableValidationStyleProperty() {
		final VTTableValidationStylePropertyImpl tableValidationStyleProperty = new VTTableValidationStylePropertyImpl();
		return tableValidationStyleProperty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VTTableValidationPackage getTableValidationPackage() {
		return (VTTableValidationPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static VTTableValidationPackage getPackage() {
		return VTTableValidationPackage.eINSTANCE;
	}

} // VTTableValidationFactoryImpl
