/**
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.model.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VAttachment;
import org.eclipse.emf.ecp.view.spi.model.VContainedContainer;
import org.eclipse.emf.ecp.view.spi.model.VContainedElement;
import org.eclipse.emf.ecp.view.spi.model.VContainer;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDiagnostic;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides
 * an adapter <code>createXXX</code> method for each class of the model. <!--
 * end-user-doc -->
 *
 * @see org.eclipse.emf.ecp.view.spi.model.VViewPackage
 * @generated
 * @since 1.2
 */
public class ViewAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected static VViewPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	public ViewAdapterFactory() {
		if (modelPackage == null)
		{
			modelPackage = VViewPackage.eINSTANCE;
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
		if (object == modelPackage)
		{
			return true;
		}
		if (object instanceof EObject)
		{
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
	protected ViewSwitch<Adapter> modelSwitch = new ViewSwitch<Adapter>()
	{
		@Override
		public Adapter caseDiagnostic(VDiagnostic object)
		{
			return createDiagnosticAdapter();
		}

		@Override
		public Adapter caseAttachment(VAttachment object)
		{
			return createAttachmentAdapter();
		}

		@Override
		public Adapter caseDomainModelReference(VDomainModelReference object)
		{
			return createDomainModelReferenceAdapter();
		}

		@Override
		public Adapter caseFeaturePathDomainModelReference(VFeaturePathDomainModelReference object)
		{
			return createFeaturePathDomainModelReferenceAdapter();
		}

		@Override
		public Adapter caseElement(VElement object)
		{
			return createElementAdapter();
		}

		@Override
		public Adapter caseView(VView object)
		{
			return createViewAdapter();
		}

		@Override
		public Adapter caseContainedElement(VContainedElement object)
		{
			return createContainedElementAdapter();
		}

		@Override
		public Adapter caseContainer(VContainer object)
		{
			return createContainerAdapter();
		}

		@Override
		public Adapter caseContainedContainer(VContainedContainer object)
		{
			return createContainedContainerAdapter();
		}

		@Override
		public Adapter caseControl(VControl object)
		{
			return createControlAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object)
		{
			return createEObjectAdapter();
		}
	};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject) target);
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emf.ecp.view.spi.model.VElement
	 * <em>Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.emf.ecp.view.spi.model.VElement
	 * @generated
	 */
	public Adapter createElementAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emf.ecp.view.spi.model.VDiagnostic
	 * <em>Diagnostic</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.emf.ecp.view.spi.model.VDiagnostic
	 * @generated
	 */
	public Adapter createDiagnosticAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class ' {@link org.eclipse.emf.ecp.view.spi.model.VView <em>View</em>}'.
	 * <!--
	 * begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.emf.ecp.view.spi.model.VView
	 * @generated
	 */
	public Adapter createViewAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emf.ecp.view.spi.model.VContainedElement
	 * <em>Contained Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.emf.ecp.view.spi.model.VContainedElement
	 * @generated
	 */
	public Adapter createContainedElementAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class ' {@link org.eclipse.emf.ecp.view.spi.model.VControl
	 * <em>Control</em>}'.
	 * <!--
	 * begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.emf.ecp.view.spi.model.VControl
	 * @generated
	 */
	public Adapter createControlAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emf.ecp.view.spi.model.VContainer
	 * <em>Container</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.emf.ecp.view.spi.model.VContainer
	 * @generated
	 */
	public Adapter createContainerAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emf.ecp.view.spi.model.VContainedContainer
	 * <em>Contained Container</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.emf.ecp.view.spi.model.VContainedContainer
	 * @generated
	 * @since 1.4
	 */
	public Adapter createContainedContainerAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emf.ecp.view.spi.model.VDomainModelReference
	 * <em>Domain Model Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.emf.ecp.view.spi.model.VDomainModelReference
	 * @generated
	 */
	public Adapter createDomainModelReferenceAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference
	 * <em>Feature Path Domain Model Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference
	 * @generated
	 */
	public Adapter createFeaturePathDomainModelReferenceAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emf.ecp.view.spi.model.VAttachment
	 * <em>Attachment</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.emf.ecp.view.spi.model.VAttachment
	 * @generated
	 */
	public Adapter createAttachmentAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} // ViewAdapterFactory
