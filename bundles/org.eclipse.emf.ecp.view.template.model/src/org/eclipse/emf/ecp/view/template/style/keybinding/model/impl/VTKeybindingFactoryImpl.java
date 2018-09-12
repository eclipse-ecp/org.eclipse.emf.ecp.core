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
package org.eclipse.emf.ecp.view.template.style.keybinding.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecp.view.template.style.keybinding.model.VTKeyBinding;
import org.eclipse.emf.ecp.view.template.style.keybinding.model.VTKeyBindings;
import org.eclipse.emf.ecp.view.template.style.keybinding.model.VTKeybindingFactory;
import org.eclipse.emf.ecp.view.template.style.keybinding.model.VTKeybindingPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class VTKeybindingFactoryImpl extends EFactoryImpl implements VTKeybindingFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static VTKeybindingFactory init() {
		try {
			final VTKeybindingFactory theKeybindingFactory = (VTKeybindingFactory) EPackage.Registry.INSTANCE
				.getEFactory(VTKeybindingPackage.eNS_URI);
			if (theKeybindingFactory != null) {
				return theKeybindingFactory;
			}
		} catch (final Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new VTKeybindingFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public VTKeybindingFactoryImpl() {
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
		case VTKeybindingPackage.KEY_BINDINGS:
			return createKeyBindings();
		case VTKeybindingPackage.KEY_BINDING:
			return createKeyBinding();
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
	public VTKeyBindings createKeyBindings() {
		final VTKeyBindingsImpl keyBindings = new VTKeyBindingsImpl();
		return keyBindings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VTKeyBinding createKeyBinding() {
		final VTKeyBindingImpl keyBinding = new VTKeyBindingImpl();
		return keyBinding;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VTKeybindingPackage getKeybindingPackage() {
		return (VTKeybindingPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static VTKeybindingPackage getPackage() {
		return VTKeybindingPackage.eINSTANCE;
	}

} // VTKeybindingFactoryImpl
