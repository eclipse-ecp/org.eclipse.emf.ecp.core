/*******************************************************************************
 * Copyright (c) 2019 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.common.di.service.impl;

import java.util.Optional;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.suppliers.ExtendedObjectSupplier;
import org.eclipse.emf.ecp.view.model.common.di.annotations.ViewService;
import org.eclipse.emf.ecp.view.spi.common.di.ContextBasedObjectSupplier;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.osgi.service.component.annotations.Component;

/**
 * Eclipse DI supplier of the {@linkplain ViewService view-model service} dependency.
 * Resolvable only in a context that has a {@link ViewModelContext} available.
 *
 * @see ViewService
 */
@Component(name = "viewServiceSupplier", service = ExtendedObjectSupplier.class, property = "dependency.injection.annotation=org.eclipse.emf.ecp.view.model.common.di.annotations.ViewService")
public class ViewServiceSupplier extends ContextBasedObjectSupplier<ViewService, Object> {

	/**
	 * Initializes me.
	 */
	public ViewServiceSupplier() {
		super(ViewService.class, Object.class);
	}

	@Override
	protected boolean checkDependencies(ViewService qualifier, Class<?> requestedType, IEclipseContext context) {
		final ViewModelContext viewContext = context.get(ViewModelContext.class);
		return viewContext != null;
	}

	@Override
	protected Optional<?> compute(ViewService qualifier, Class<?> requestedType, IEclipseContext context) {
		Optional<?> result;

		final ViewModelContext viewContext = context.get(ViewModelContext.class);
		if (!viewContext.hasService(requestedType)) {
			// Maybe we can get it from the Eclipse context
			result = Optional.ofNullable(context.get(requestedType));
		} else {
			result = Optional.ofNullable(viewContext.getService(requestedType));
		}

		return result;
	}

}
