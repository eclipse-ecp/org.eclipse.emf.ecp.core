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
package org.eclipse.emf.ecp.view.template.style.textControlEnablement.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecp.view.template.style.textControlEnablement.model.VTTextControlEnablementFactory;
import org.eclipse.emf.ecp.view.template.style.textControlEnablement.model.VTTextControlEnablementPackage;
import org.eclipse.emf.ecp.view.template.style.textControlEnablement.model.VTTextControlEnablementStyleProperty;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class VTTextControlEnablementFactoryImpl extends EFactoryImpl implements VTTextControlEnablementFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static VTTextControlEnablementFactory init() {
		try {
			final VTTextControlEnablementFactory theTextControlEnablementFactory = (VTTextControlEnablementFactory) EPackage.Registry.INSTANCE
				.getEFactory(VTTextControlEnablementPackage.eNS_URI);
			if (theTextControlEnablementFactory != null) {
				return theTextControlEnablementFactory;
			}
		} catch (final Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new VTTextControlEnablementFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public VTTextControlEnablementFactoryImpl() {
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
		case VTTextControlEnablementPackage.TEXT_CONTROL_ENABLEMENT_STYLE_PROPERTY:
			return createTextControlEnablementStyleProperty();
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
	public VTTextControlEnablementStyleProperty createTextControlEnablementStyleProperty() {
		final VTTextControlEnablementStylePropertyImpl textControlEnablementStyleProperty = new VTTextControlEnablementStylePropertyImpl();
		return textControlEnablementStyleProperty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VTTextControlEnablementPackage getTextControlEnablementPackage() {
		return (VTTextControlEnablementPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static VTTextControlEnablementPackage getPackage() {
		return VTTextControlEnablementPackage.eINSTANCE;
	}

} // VTTextControlEnablementFactoryImpl
