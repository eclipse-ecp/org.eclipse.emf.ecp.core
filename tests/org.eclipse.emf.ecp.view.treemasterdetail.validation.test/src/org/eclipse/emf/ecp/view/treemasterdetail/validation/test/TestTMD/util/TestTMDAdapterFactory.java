/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.treemasterdetail.validation.test.TestTMD.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.treemasterdetail.validation.test.TestTMD.ChildLevel1;
import org.eclipse.emf.ecp.view.treemasterdetail.validation.test.TestTMD.ChildLevel2;
import org.eclipse.emf.ecp.view.treemasterdetail.validation.test.TestTMD.Root;
import org.eclipse.emf.ecp.view.treemasterdetail.validation.test.TestTMD.TestTMDPackage;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides
 * an adapter <code>createXXX</code> method for each class of the model. <!--
 * end-user-doc -->
 *
 * @see org.eclipse.emf.ecp.view.treemasterdetail.validation.test.TestTMD.TestTMDPackage
 * @generated
 */
public class TestTMDAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected static TestTMDPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	public TestTMDAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = TestTMDPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc --> This implementation returns <code>true</code> if
	 * the object is either the model's package or is an instance object of the
	 * model. <!-- end-user-doc -->
	 *
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject) object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected TestTMDSwitch<Adapter> modelSwitch = new TestTMDSwitch<Adapter>() {
		@Override
		public Adapter caseRoot(Root object) {
			return createRootAdapter();
		}

		@Override
		public Adapter caseChildLevel1(ChildLevel1 object) {
			return createChildLevel1Adapter();
		}

		@Override
		public Adapter caseChildLevel2(ChildLevel2 object) {
			return createChildLevel2Adapter();
		}

		@Override
		public Adapter defaultCase(EObject object) {
			return createEObjectAdapter();
		}
	};

	/**
	 * Creates an adapter for the <code>target</code>. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param target
	 *            the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject) target);
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.emf.ecp.view.treemasterdetail.validation.test.TestTMD.Root
	 * <em>Root</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a
	 * case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.emf.ecp.view.treemasterdetail.validation.test.TestTMD.Root
	 * @generated
	 */
	public Adapter createRootAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.emf.ecp.view.treemasterdetail.validation.test.TestTMD.ChildLevel1
	 * <em>Child Level1</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.emf.ecp.view.treemasterdetail.validation.test.TestTMD.ChildLevel1
	 * @generated
	 */
	public Adapter createChildLevel1Adapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.emf.ecp.view.treemasterdetail.validation.test.TestTMD.ChildLevel2
	 * <em>Child Level2</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.emf.ecp.view.treemasterdetail.validation.test.TestTMD.ChildLevel2
	 * @generated
	 */
	public Adapter createChildLevel2Adapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case. <!-- begin-user-doc --> This
	 * default implementation returns null. <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} // TestTMDAdapterFactory
