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
package org.eclipse.emf.ecp.view.internal.context;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.IContextFunction;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext;
import org.osgi.service.component.annotations.Component;

/**
 * A context function that casts the {@link EMFFormsViewContext} as a
 * {@link ViewModelContext}.
 *
 * @since 1.22
 */
@Component(name = "viewModelContextCF", service = IContextFunction.class, property = "service.context.key=org.eclipse.emf.ecp.view.spi.context.ViewModelContext")
public class ViewModelContextContextFunction extends ContextFunction {

	@Override
	public Object compute(IEclipseContext context, String contextKey) {
		final EMFFormsViewContext result = context.get(EMFFormsViewContext.class);
		return result instanceof ViewModelContext ? result : null;
	}

}
