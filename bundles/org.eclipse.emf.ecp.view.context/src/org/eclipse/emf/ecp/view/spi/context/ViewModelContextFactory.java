/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.context;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.internal.context.ViewModelContextImpl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;

/**
 * This Factory can be used to instantiate {@link ViewModelContext ViewModelContexts}.
 * 
 * @author Eugen Neufeld
 * @since 1.2
 * 
 */
public final class ViewModelContextFactory {
	/**
	 * The singleton instance of the factory.
	 */
	public static final ViewModelContextFactory INSTANCE = new ViewModelContextFactory();

	private ViewModelContextFactory() {
	}

	/**
	 * Instantiates a new view model context.
	 * 
	 * @param view the view
	 * @param domainObject the domain object
	 * @return the created {@link ViewModelContext}
	 */
	public ViewModelContext createViewModelContext(VElement view, EObject domainObject) {
		return new ViewModelContextImpl(view, domainObject);
	}

	/**
	 * Instantiates a new view model context with specific services.
	 * 
	 * @param view the view
	 * @param domainObject the domain object
	 * @param modelServices an array of services to use in the {@link ViewModelContext}
	 * @return the created {@link ViewModelContext}
	 */
	public ViewModelContext createViewModelContext(VElement view, EObject domainObject,
		ViewModelService... modelServices) {
		return new ViewModelContextImpl(view, domainObject, modelServices);
	}

	/**
	 * Instantiates a new view model context.
	 * 
	 * @param view the view
	 * @param domainObject the domain object
	 * @param parentContext the parent {@link ViewModelContext}
	 * @param parentVElement the parent {@link VElement}
	 * @return the created {@link ViewModelContext}
	 * @since 1.5
	 */
	public ViewModelContext createViewModelContext(VView view, EObject domainObject,
		ViewModelContext parentContext, VElement parentVElement) {
		return new ViewModelContextImpl(view, domainObject, parentContext, parentVElement);
	}

	/**
	 * Instantiates a new view model context with specific services.
	 * 
	 * @param view the view
	 * @param domainObject the domain object
	 * @param parentContext the parent {@link ViewModelContext}
	 * @param parentVElement the parent {@link VElement}
	 * @param modelServices an array of services to use in the {@link ViewModelContext}
	 * @return the created {@link ViewModelContext}
	 * @since 1.5
	 */
	public ViewModelContext createViewModelContext(VView view, EObject domainObject,
		ViewModelContext parentContext, VElement parentVElement,
		ViewModelService... modelServices) {
		return new ViewModelContextImpl(view, domainObject, parentContext, parentVElement, modelServices);
	}
}
