/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.index.test.example.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecp.view.index.test.example.Child;
import org.eclipse.emf.ecp.view.index.test.example.ExampleFactory;
import org.eclipse.emf.ecp.view.index.test.example.ExamplePackage;
import org.eclipse.emf.ecp.view.index.test.example.Intermediate;
import org.eclipse.emf.ecp.view.index.test.example.IntermediateTarget;
import org.eclipse.emf.ecp.view.index.test.example.Root;
import org.eclipse.emf.ecp.view.index.test.example.Target;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class ExampleFactoryImpl extends EFactoryImpl implements ExampleFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static ExampleFactory init() {
		try {
			final ExampleFactory theExampleFactory = (ExampleFactory) EPackage.Registry.INSTANCE
				.getEFactory(ExamplePackage.eNS_URI);
			if (theExampleFactory != null) {
				return theExampleFactory;
			}
		} catch (final Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ExampleFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public ExampleFactoryImpl() {
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
		case ExamplePackage.ROOT:
			return createRoot();
		case ExamplePackage.INTERMEDIATE:
			return createIntermediate();
		case ExamplePackage.CONTAINER:
			return createContainer();
		case ExamplePackage.CHILD:
			return createChild();
		case ExamplePackage.INTERMEDIATE_TARGET:
			return createIntermediateTarget();
		case ExamplePackage.TARGET:
			return createTarget();
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
	public Root createRoot() {
		final RootImpl root = new RootImpl();
		return root;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Intermediate createIntermediate() {
		final IntermediateImpl intermediate = new IntermediateImpl();
		return intermediate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public org.eclipse.emf.ecp.view.index.test.example.Container createContainer() {
		final ContainerImpl container = new ContainerImpl();
		return container;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Child createChild() {
		final ChildImpl child = new ChildImpl();
		return child;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public IntermediateTarget createIntermediateTarget() {
		final IntermediateTargetImpl intermediateTarget = new IntermediateTargetImpl();
		return intermediateTarget;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Target createTarget() {
		final TargetImpl target = new TargetImpl();
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ExamplePackage getExamplePackage() {
		return (ExamplePackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ExamplePackage getPackage() {
		return ExamplePackage.eINSTANCE;
	}

} // ExampleFactoryImpl
