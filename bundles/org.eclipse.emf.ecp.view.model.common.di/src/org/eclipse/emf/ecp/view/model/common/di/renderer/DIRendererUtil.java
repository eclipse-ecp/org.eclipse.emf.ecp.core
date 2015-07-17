/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * jfaltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.common.di.renderer;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.emf.ecp.view.model.common.di.service.EclipseContextViewService;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;

/**
 * Utility methods for implementing depdendency injection renderers.
 *
 * @author jfaltermeier
 *
 */
public final class DIRendererUtil {

	private DIRendererUtil() {

	}

	/**
	 * Returns the {@link IEclipseContext} for the given {@link VElement}. If there is no context yet, one will be
	 * created.
	 *
	 * @param element the element
	 * @param context the {@link ViewModelContext}
	 * @return the eclipse context
	 */
	public static IEclipseContext getContextForElement(VElement element, ViewModelContext context) {
		final EclipseContextViewService service = context.getService(
			EclipseContextViewService.class);

		if (service.getContext(element) == null) {
			createContext(element, service);
		}

		return service.getContext(element);
	}

	private static void createContext(VElement element, final EclipseContextViewService service) {
		final VElement parent = (VElement) element.eContainer();
		if (service.getContext(parent) == null) {
			createContext(parent, service);
		}
		final IEclipseContext eclipseContext = service.getContext(parent);
		final IEclipseContext createChild = eclipseContext.createChild();
		service.putContext(element, createChild);
	}

	/**
	 * Renders the given pojo.
	 *
	 * @param pojo the renderer pojo
	 * @param element the {@link VElement} to render
	 * @param context the {@link ViewModelContext}
	 * @return the rendering result (Control/Composite)
	 */
	public static Object render(Object pojo, VElement element, ViewModelContext context) {
		final EclipseContextViewService service = context.getService(
			EclipseContextViewService.class);
		ContextInjectionFactory.inject(pojo, service.getContext(element));
		return ContextInjectionFactory.invoke(pojo, Execute.class, service.getContext(element));
	}

}
