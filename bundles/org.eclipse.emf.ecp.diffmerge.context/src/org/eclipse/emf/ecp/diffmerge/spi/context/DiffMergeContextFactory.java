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

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.diffmerge.internal.context.DiffMergeModelContextImpl;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
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
	 * @param target
	 *            the domain object
	 * @param left the first object
	 * @param right the second object
	 * @return the created {@link DiffMergeModelContext}
	 */
	public DiffMergeModelContext createViewModelContext(VElement view, EObject target, EObject left,
		EObject right) {
		return new DiffMergeModelContextImpl(view, target, left, right);
	}

	/**
	 * Instantiates a new view model context with specific services.
	 *
	 * @param view
	 *            the view
	 * @param target
	 *            the domain object
	 * @param left the first object
	 * @param right the second object
	 * @param modelServices
	 *            an array of services to use in the {@link DiffMergeModelContext }
	 * @return the created {@link DiffMergeModelContext}
	 */
	public DiffMergeModelContext createViewModelContext(VElement view,
		EObject target, EObject left, EObject right, ViewModelService... modelServices) {
		return new DiffMergeModelContextImpl(view, target, left, right, modelServices);
	}

	/**
	 * Instantiates a new view model context.
	 *
	 * @param view
	 *            the view
	 * @param target
	 *            the domain object
	 * @param left the first object
	 * @param right the second object
	 * @param mergedControls the set of already merged domain references
	 * @return the created {@link DiffMergeModelContext}
	 */
	public DiffMergeModelContext createViewModelContext(VElement view, EObject target, EObject left,
		EObject right, Set<VDomainModelReference> mergedControls) {
		return new DiffMergeModelContextImpl(view, target, left, right, mergedControls);
	}

	/**
	 * Instantiates a new view model context with specific services.
	 *
	 * @param view
	 *            the view
	 * @param target
	 *            the domain object
	 * @param left the first object
	 * @param right the second object
	 * @param mergedControls the set of already merged domain references
	 * @param modelServices
	 *            an array of services to use in the {@link DiffMergeModelContext }
	 * @return the created {@link DiffMergeModelContext}
	 */
	public DiffMergeModelContext createViewModelContext(VElement view,
		EObject target, EObject left, EObject right, Set<VDomainModelReference> mergedControls,
		ViewModelService... modelServices) {
		return new DiffMergeModelContextImpl(view, target, left, right, mergedControls, modelServices);
	}
}
