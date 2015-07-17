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
package org.eclipse.emf.ecp.view.spi.mappingdmr.model.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingDomainModelReference;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingdmrPackage;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides
 * an adapter <code>createXXX</code> method for each class of the model. <!--
 * end-user-doc -->
 *
 * @see org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingdmrPackage
 * @generated
 */
public class MappingdmrAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected static VMappingdmrPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	public MappingdmrAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = VMappingdmrPackage.eINSTANCE;
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
	protected MappingdmrSwitch<Adapter> modelSwitch = new MappingdmrSwitch<Adapter>() {
		@Override
		public Adapter caseMappingDomainModelReference(
			VMappingDomainModelReference object) {
			return createMappingDomainModelReferenceAdapter();
		}

		@Override
		public Adapter caseDomainModelReference(VDomainModelReference object) {
			return createDomainModelReferenceAdapter();
		}

		@Override
		public Adapter caseFeaturePathDomainModelReference(
			VFeaturePathDomainModelReference object) {
			return createFeaturePathDomainModelReferenceAdapter();
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
	 * {@link org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingDomainModelReference
	 * <em>Mapping Domain Model Reference</em>}'. <!-- begin-user-doc --> This
	 * default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingDomainModelReference
	 * @generated
	 */
	public Adapter createMappingDomainModelReferenceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class ' {@link org.eclipse.emf.ecp.view.spi.model.VDomainModelReference
	 * <em>Domain Model Reference</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.emf.ecp.view.spi.model.VDomainModelReference
	 * @generated
	 */
	public Adapter createDomainModelReferenceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference
	 * <em>Feature Path Domain Model Reference</em>}'. <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore
	 * cases; it's useful to ignore a case when inheritance will catch all the
	 * cases anyway. <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference
	 * @generated
	 */
	public Adapter createFeaturePathDomainModelReferenceAdapter() {
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

} // MappingdmrAdapterFactory
