/**
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emfforms.coffee.model.coffee.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emfforms.coffee.model.coffee.Activity;
import org.eclipse.emfforms.coffee.model.coffee.BrewingUnit;
import org.eclipse.emfforms.coffee.model.coffee.CoffeeFactory;
import org.eclipse.emfforms.coffee.model.coffee.CoffeePackage;
import org.eclipse.emfforms.coffee.model.coffee.ControlUnit;
import org.eclipse.emfforms.coffee.model.coffee.Dimension;
import org.eclipse.emfforms.coffee.model.coffee.DipTray;
import org.eclipse.emfforms.coffee.model.coffee.Display;
import org.eclipse.emfforms.coffee.model.coffee.Machine;
import org.eclipse.emfforms.coffee.model.coffee.ManufactoringProcess;
import org.eclipse.emfforms.coffee.model.coffee.Processor;
import org.eclipse.emfforms.coffee.model.coffee.RAM;
import org.eclipse.emfforms.coffee.model.coffee.RamType;
import org.eclipse.emfforms.coffee.model.coffee.SocketConnectorType;
import org.eclipse.emfforms.coffee.model.coffee.WaterTank;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class CoffeeFactoryImpl extends EFactoryImpl implements CoffeeFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static CoffeeFactory init() {
		try {
			final CoffeeFactory theCoffeeFactory = (CoffeeFactory) EPackage.Registry.INSTANCE
				.getEFactory(CoffeePackage.eNS_URI);
			if (theCoffeeFactory != null) {
				return theCoffeeFactory;
			}
		} catch (final Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new CoffeeFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public CoffeeFactoryImpl() {
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
		case CoffeePackage.MACHINE:
			return createMachine();
		case CoffeePackage.CONTROL_UNIT:
			return createControlUnit();
		case CoffeePackage.BREWING_UNIT:
			return createBrewingUnit();
		case CoffeePackage.DIP_TRAY:
			return createDipTray();
		case CoffeePackage.WATER_TANK:
			return createWaterTank();
		case CoffeePackage.PROCESSOR:
			return createProcessor();
		case CoffeePackage.RAM:
			return createRAM();
		case CoffeePackage.ACTIVITY:
			return createActivity();
		case CoffeePackage.DIMENSION:
			return createDimension();
		case CoffeePackage.DISPLAY:
			return createDisplay();
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
		case CoffeePackage.SOCKET_CONNECTOR_TYPE:
			return createSocketConnectorTypeFromString(eDataType, initialValue);
		case CoffeePackage.MANUFACTORING_PROCESS:
			return createManufactoringProcessFromString(eDataType, initialValue);
		case CoffeePackage.RAM_TYPE:
			return createRamTypeFromString(eDataType, initialValue);
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
		case CoffeePackage.SOCKET_CONNECTOR_TYPE:
			return convertSocketConnectorTypeToString(eDataType, instanceValue);
		case CoffeePackage.MANUFACTORING_PROCESS:
			return convertManufactoringProcessToString(eDataType, instanceValue);
		case CoffeePackage.RAM_TYPE:
			return convertRamTypeToString(eDataType, instanceValue);
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
	public Machine createMachine() {
		final MachineImpl machine = new MachineImpl();
		return machine;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ControlUnit createControlUnit() {
		final ControlUnitImpl controlUnit = new ControlUnitImpl();
		return controlUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public BrewingUnit createBrewingUnit() {
		final BrewingUnitImpl brewingUnit = new BrewingUnitImpl();
		return brewingUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public DipTray createDipTray() {
		final DipTrayImpl dipTray = new DipTrayImpl();
		return dipTray;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public WaterTank createWaterTank() {
		final WaterTankImpl waterTank = new WaterTankImpl();
		return waterTank;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Processor createProcessor() {
		final ProcessorImpl processor = new ProcessorImpl();
		return processor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public RAM createRAM() {
		final RAMImpl ram = new RAMImpl();
		return ram;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Activity createActivity() {
		final ActivityImpl activity = new ActivityImpl();
		return activity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Dimension createDimension() {
		final DimensionImpl dimension = new DimensionImpl();
		return dimension;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Display createDisplay() {
		final DisplayImpl display = new DisplayImpl();
		return display;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public SocketConnectorType createSocketConnectorTypeFromString(EDataType eDataType, String initialValue) {
		final SocketConnectorType result = SocketConnectorType.get(initialValue);
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
	public String convertSocketConnectorTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public ManufactoringProcess createManufactoringProcessFromString(EDataType eDataType, String initialValue) {
		final ManufactoringProcess result = ManufactoringProcess.get(initialValue);
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
	public String convertManufactoringProcessToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public RamType createRamTypeFromString(EDataType eDataType, String initialValue) {
		final RamType result = RamType.get(initialValue);
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
	public String convertRamTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public CoffeePackage getCoffeePackage() {
		return (CoffeePackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static CoffeePackage getPackage() {
		return CoffeePackage.eINSTANCE;
	}

} // CoffeeFactoryImpl
