/**
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.style.labelwidth.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecp.view.template.style.labelwidth.model.VTLabelWidthStyleProperty;
import org.eclipse.emf.ecp.view.template.style.labelwidth.model.VTLabelwidthFactory;
import org.eclipse.emf.ecp.view.template.style.labelwidth.model.VTLabelwidthPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class VTLabelwidthFactoryImpl extends EFactoryImpl implements VTLabelwidthFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static VTLabelwidthFactory init() {
		try {
			final VTLabelwidthFactory theLabelwidthFactory = (VTLabelwidthFactory) EPackage.Registry.INSTANCE
				.getEFactory(VTLabelwidthPackage.eNS_URI);
			if (theLabelwidthFactory != null) {
				return theLabelwidthFactory;
			}
		} catch (final Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new VTLabelwidthFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public VTLabelwidthFactoryImpl() {
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
		case VTLabelwidthPackage.LABEL_WIDTH_STYLE_PROPERTY:
			return createLabelWidthStyleProperty();
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
	public VTLabelWidthStyleProperty createLabelWidthStyleProperty() {
		final VTLabelWidthStylePropertyImpl labelWidthStyleProperty = new VTLabelWidthStylePropertyImpl();
		return labelWidthStyleProperty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VTLabelwidthPackage getLabelwidthPackage() {
		return (VTLabelwidthPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static VTLabelwidthPackage getPackage() {
		return VTLabelwidthPackage.eINSTANCE;
	}

} // VTLabelwidthFactoryImpl
