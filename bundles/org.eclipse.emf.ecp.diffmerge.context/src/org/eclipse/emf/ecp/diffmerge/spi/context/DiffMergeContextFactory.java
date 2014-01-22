/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.diffmerge.spi.context;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.diffmerge.internal.context.DiffMergeModelContextImpl;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;
import org.eclipse.emf.ecp.view.spi.model.VElement;

/**
 * This Factory can be used to instantiate {@link DiffMergeModelContext
 * DiffMergeModelContexts}.
 * 
 * @author Eugen Neufeld
 * 
 */
public final class DiffMergeContextFactory {
	/**
	 * The singleton instance of the factory.
	 */
	public static final DiffMergeContextFactory INSTANCE = new DiffMergeContextFactory();

	private DiffMergeContextFactory() {
	}

	/**
	 * Instantiates a new view model context.
	 * 
	 * @param view
	 *            the view
	 * @param domainObject
	 *            the domain object
	 * @param origin1 the first object
	 * @param origin2 the second object
	 * @return the created {@link DiffMergeModelContext}
	 */
	public DiffMergeModelContext createViewModelContext(VElement view, EObject domainObject, EObject origin1,
		EObject origin2) {
		return new DiffMergeModelContextImpl(view, domainObject, origin1, origin2);
	}

	/**
	 * Instantiates a new view model context with specific services.
	 * 
	 * @param view
	 *            the view
	 * @param domainObject
	 *            the domain object
	 * @param origin1 the first object
	 * @param origin2 the second object
	 * @param modelServices
	 *            an array of services to use in the {@link DiffMergeModelContext }
	 * @return the created {@link DiffMergeModelContext}
	 */
	public DiffMergeModelContext createViewModelContext(VElement view,
		EObject domainObject, EObject origin1, EObject origin2, ViewModelService... modelServices) {
		return new DiffMergeModelContextImpl(view, domainObject, origin1, origin2, modelServices);
	}
}
