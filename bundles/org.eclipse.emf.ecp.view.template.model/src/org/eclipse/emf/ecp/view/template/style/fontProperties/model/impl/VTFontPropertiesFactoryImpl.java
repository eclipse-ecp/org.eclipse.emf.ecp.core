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
package org.eclipse.emf.ecp.view.template.style.fontProperties.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesFactory;
import org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesPackage;
import org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesStyleProperty;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class VTFontPropertiesFactoryImpl extends EFactoryImpl implements VTFontPropertiesFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static VTFontPropertiesFactory init() {
		try {
			final VTFontPropertiesFactory theFontPropertiesFactory = (VTFontPropertiesFactory) EPackage.Registry.INSTANCE
				.getEFactory(VTFontPropertiesPackage.eNS_URI);
			if (theFontPropertiesFactory != null) {
				return theFontPropertiesFactory;
			}
		} catch (final Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new VTFontPropertiesFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public VTFontPropertiesFactoryImpl() {
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
		case VTFontPropertiesPackage.FONT_PROPERTIES_STYLE_PROPERTY:
			return createFontPropertiesStyleProperty();
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
	public VTFontPropertiesStyleProperty createFontPropertiesStyleProperty() {
		final VTFontPropertiesStylePropertyImpl fontPropertiesStyleProperty = new VTFontPropertiesStylePropertyImpl();
		return fontPropertiesStyleProperty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VTFontPropertiesPackage getFontPropertiesPackage() {
		return (VTFontPropertiesPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static VTFontPropertiesPackage getPackage() {
		return VTFontPropertiesPackage.eINSTANCE;
	}

} // VTFontPropertiesFactoryImpl
