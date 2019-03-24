/*******************************************************************************
 * Copyright (c) 2019 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.ide.ecore.internal.builder;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emfforms.bazaar.Create;
import org.eclipse.emfforms.bazaar.Precondition;
import org.eclipse.emfforms.bazaar.StaticBid;
import org.eclipse.emfforms.ide.builder.BuilderConstants;
import org.eclipse.emfforms.ide.builder.ValidationDelegate;
import org.eclipse.emfforms.ide.builder.ValidationDelegateProvider;
import org.eclipse.emfforms.ide.builder.ValidationServiceDelegate;
import org.osgi.service.component.annotations.Component;

/**
 * Validation delegate provider for Ecore models.
 */
@Component
@StaticBid(bid = 10.0)
@Precondition(key = BuilderConstants.CONTENT_TYPE, value = EcorePackage.eCONTENT_TYPE)
public class EcoreValidationDelegateProvider implements ValidationDelegateProvider {

	/**
	 * Initializes me.
	 */
	public EcoreValidationDelegateProvider() {
		super();
	}

	/**
	 * Create the validation delegate.
	 *
	 * @return the validation delegate
	 */
	@Create
	public ValidationDelegate createValidationDelegate() {
		return new ValidationServiceDelegate();
	}
}
