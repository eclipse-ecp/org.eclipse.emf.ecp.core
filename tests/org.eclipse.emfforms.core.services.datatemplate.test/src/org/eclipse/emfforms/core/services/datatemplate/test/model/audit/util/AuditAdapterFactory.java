/**
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emfforms.core.services.datatemplate.test.model.audit.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AbstractSubUser;
import org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AdminUser;
import org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AuditPackage;
import org.eclipse.emfforms.core.services.datatemplate.test.model.audit.GuestUser;
import org.eclipse.emfforms.core.services.datatemplate.test.model.audit.PrivilegedUser;
import org.eclipse.emfforms.core.services.datatemplate.test.model.audit.RegisteredUser;
import org.eclipse.emfforms.core.services.datatemplate.test.model.audit.User;
import org.eclipse.emfforms.core.services.datatemplate.test.model.audit.UserGroup;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AuditPackage
 * @generated
 */
public class AuditAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static AuditPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AuditAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = AuditPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance
	 * object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AuditSwitch<Adapter> modelSwitch = new AuditSwitch<Adapter>() {
			@Override
			public Adapter caseUser(User object) {
				return createUserAdapter();
			}
			@Override
			public Adapter casePrivilegedUser(PrivilegedUser object) {
				return createPrivilegedUserAdapter();
			}
			@Override
			public Adapter caseGuestUser(GuestUser object) {
				return createGuestUserAdapter();
			}
			@Override
			public Adapter caseRegisteredUser(RegisteredUser object) {
				return createRegisteredUserAdapter();
			}
			@Override
			public Adapter caseAdminUser(AdminUser object) {
				return createAdminUserAdapter();
			}
			@Override
			public Adapter caseUserGroup(UserGroup object) {
				return createUserGroupAdapter();
			}
			@Override
			public Adapter caseAbstractSubUser(AbstractSubUser object) {
				return createAbstractSubUserAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.User <em>User</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.User
	 * @generated
	 */
	public Adapter createUserAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.PrivilegedUser <em>Privileged User</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.PrivilegedUser
	 * @generated
	 */
	public Adapter createPrivilegedUserAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.GuestUser <em>Guest User</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.GuestUser
	 * @generated
	 */
	public Adapter createGuestUserAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.RegisteredUser <em>Registered User</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.RegisteredUser
	 * @generated
	 */
	public Adapter createRegisteredUserAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AdminUser <em>Admin User</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AdminUser
	 * @generated
	 */
	public Adapter createAdminUserAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.UserGroup <em>User Group</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.UserGroup
	 * @generated
	 */
	public Adapter createUserGroupAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AbstractSubUser <em>Abstract Sub User</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AbstractSubUser
	 * @generated
	 */
	public Adapter createAbstractSubUserAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} // AuditAdapterFactory
