/**
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.style.unsettable.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecp.view.template.style.unsettable.model.ButtonAlignmentType;
import org.eclipse.emf.ecp.view.template.style.unsettable.model.ButtonPlacementType;
import org.eclipse.emf.ecp.view.template.style.unsettable.model.VTUnsettableFactory;
import org.eclipse.emf.ecp.view.template.style.unsettable.model.VTUnsettablePackage;
import org.eclipse.emf.ecp.view.template.style.unsettable.model.VTUnsettableStyleProperty;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class VTUnsettableFactoryImpl extends EFactoryImpl implements VTUnsettableFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static VTUnsettableFactory init() {
		try {
			final VTUnsettableFactory theUnsettableFactory = (VTUnsettableFactory) EPackage.Registry.INSTANCE
				.getEFactory(VTUnsettablePackage.eNS_URI);
			if (theUnsettableFactory != null) {
				return theUnsettableFactory;
			}
		} catch (final Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new VTUnsettableFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public VTUnsettableFactoryImpl() {
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
		case VTUnsettablePackage.UNSETTABLE_STYLE_PROPERTY:
			return createUnsettableStyleProperty();
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
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
		case VTUnsettablePackage.BUTTON_ALIGNMENT_TYPE:
			return createButtonAlignmentTypeFromString(eDataType, initialValue);
		case VTUnsettablePackage.BUTTON_PLACEMENT_TYPE:
			return createButtonPlacementTypeFromString(eDataType, initialValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
		case VTUnsettablePackage.BUTTON_ALIGNMENT_TYPE:
			return convertButtonAlignmentTypeToString(eDataType, instanceValue);
		case VTUnsettablePackage.BUTTON_PLACEMENT_TYPE:
			return convertButtonPlacementTypeToString(eDataType, instanceValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public VTUnsettableStyleProperty createUnsettableStyleProperty() {
		final VTUnsettableStylePropertyImpl unsettableStyleProperty = new VTUnsettableStylePropertyImpl();
		return unsettableStyleProperty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ButtonAlignmentType createButtonAlignmentTypeFromString(EDataType eDataType, String initialValue) {
		final ButtonAlignmentType result = ButtonAlignmentType.get(initialValue);
		if (result == null) {
			throw new IllegalArgumentException(
				"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertButtonAlignmentTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ButtonPlacementType createButtonPlacementTypeFromString(EDataType eDataType, String initialValue) {
		final ButtonPlacementType result = ButtonPlacementType.get(initialValue);
		if (result == null) {
			throw new IllegalArgumentException(
				"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertButtonPlacementTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public VTUnsettablePackage getUnsettablePackage() {
		return (VTUnsettablePackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static VTUnsettablePackage getPackage() {
		return VTUnsettablePackage.eINSTANCE;
	}

} // VTUnsettableFactoryImpl
