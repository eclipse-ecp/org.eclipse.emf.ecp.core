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
package org.eclipse.emfforms.internal.swt.core.di;

import java.util.Optional;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.suppliers.ExtendedObjectSupplier;
import org.eclipse.emf.ecp.view.model.common.AbstractRenderer;
import org.eclipse.emf.ecp.view.model.common.di.annotations.Renderer;
import org.eclipse.emf.ecp.view.spi.common.di.ContextBasedObjectSupplier;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.osgi.service.component.annotations.Component;

/**
 * Eclipse DI supplier of the renderer dependency. Resolvable only in a context
 * that has both the {@link VElement} and the {@link ViewModelContext} available.
 */
@Component(name = "rendererSupplier", service = ExtendedObjectSupplier.class, property = "dependency.injection.annotation=org.eclipse.emf.ecp.view.model.common.di.annotations.Renderer")
public class RendererSupplier extends ContextBasedObjectSupplier<Renderer, AbstractSWTRenderer<? extends VElement>> {

	/**
	 * Initializes me.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public RendererSupplier() {
		super(Renderer.class, (Class) AbstractSWTRenderer.class);
	}

	@Override
	protected boolean checkDependencies(Renderer qualifier,
		Class<? extends AbstractSWTRenderer<? extends VElement>> requestedType, IEclipseContext context) {

		return context.containsKey(VElement.class) && context.containsKey(ViewModelContext.class);
	}

	@Override
	protected Optional<? extends AbstractSWTRenderer<? extends VElement>> compute(Renderer qualifier,
		Class<? extends AbstractSWTRenderer<? extends VElement>> requestedType, IEclipseContext context) {

		final VElement viewModelElement = context.get(VElement.class);
		final ViewModelContext viewContext = context.get(ViewModelContext.class);

		final AbstractRenderer<?> result = AbstractRenderer.getRenderer(viewModelElement, viewContext);
		return Optional.ofNullable(result)
			.filter(requestedType::isInstance).map(requestedType::cast);
	}

}
