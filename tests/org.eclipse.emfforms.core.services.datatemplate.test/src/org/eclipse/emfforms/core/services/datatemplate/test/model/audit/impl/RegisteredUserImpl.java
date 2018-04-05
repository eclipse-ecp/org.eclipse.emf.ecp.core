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
package org.eclipse.emfforms.core.services.datatemplate.test.model.audit.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AuditPackage;
import org.eclipse.emfforms.core.services.datatemplate.test.model.audit.RegisteredUser;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Registered User</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class RegisteredUserImpl extends UserImpl implements RegisteredUser {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RegisteredUserImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AuditPackage.Literals.REGISTERED_USER;
	}

} // RegisteredUserImpl
