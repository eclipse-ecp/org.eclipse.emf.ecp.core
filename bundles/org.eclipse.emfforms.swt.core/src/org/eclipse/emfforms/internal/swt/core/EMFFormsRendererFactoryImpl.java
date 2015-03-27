/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.swt.core;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.swt.AbstractAdditionalSWTRenderer;
import org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.EMFFormsAdditionalRendererService;
import org.eclipse.emfforms.spi.swt.core.EMFFormsNoRendererException;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererFactory;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererService;

/**
 * The default implementation of the EMFFormsRendererFactory.
 *
 * @author Eugen Neufeld
 *
 */
public class EMFFormsRendererFactoryImpl implements EMFFormsRendererFactory {

	private final Set<EMFFormsRendererService<VElement>> rendererServices = new LinkedHashSet<EMFFormsRendererService<VElement>>();
	private final Set<EMFFormsAdditionalRendererService<VElement>> additionalRendererServices = new LinkedHashSet<EMFFormsAdditionalRendererService<VElement>>();

	/**
	 * Called by the initializer to add an {@link EMFFormsRendererService}.
	 *
	 * @param rendererService The EMFFormsRendererService to add
	 */
	protected void addEMFFormsRendererService(EMFFormsRendererService<VElement> rendererService) {
		rendererServices.add(rendererService);
	}

	/**
	 * Called by the initializer to remove an {@link EMFFormsRendererService}.
	 *
	 * @param rendererService The EMFFormsRendererService to remove
	 */
	protected void removeEMFFormsRendererService(EMFFormsRendererService<VElement> rendererService) {
		rendererServices.remove(rendererService);
	}

	/**
	 * Called by the initializer to add an {@link EMFFormsAdditionalRendererService}.
	 *
	 * @param rendererService The EMFFormsAdditionalRendererService to add
	 */
	protected void addEMFFormsAdditionalRendererService(EMFFormsAdditionalRendererService<VElement> rendererService) {
		additionalRendererServices.add(rendererService);
	}

	/**
	 * Called by the initializer to remove an {@link EMFFormsAdditionalRendererService}.
	 *
	 * @param rendererService The EMFFormsAdditionalRendererService to remove
	 */
	protected void removeEMFFormsAdditionalRendererService(EMFFormsAdditionalRendererService<VElement> rendererService) {
		additionalRendererServices.remove(rendererService);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws EMFFormsNoRendererException
	 *
	 * @see EMFFormsRendererFactory#getRendererInstance(VElement,ViewModelContext)
	 */
	@Override
	public <VELEMENT extends VElement> AbstractSWTRenderer<VElement> getRendererInstance(VELEMENT vElement,
		ViewModelContext viewModelContext) throws EMFFormsNoRendererException {
		if (rendererServices.isEmpty()) {
			throw new EMFFormsNoRendererException("No EMFFormsRendererService available!"); //$NON-NLS-1$
		}
		double highestPriority = -1;
		EMFFormsRendererService<VElement> bestService = null;
		for (final EMFFormsRendererService<VElement> rendererService : rendererServices) {
			final double priority = rendererService.isApplicable(vElement, viewModelContext);
			if (priority > highestPriority) {
				highestPriority = priority;
				bestService = rendererService;
			}
		}
		if (bestService == null) {
			throw new EMFFormsNoRendererException(String.format(
				"No fitting EMFFormsRendererService for %1$s available!", vElement.eClass().getName())); //$NON-NLS-1$
		}
		final AbstractSWTRenderer<VElement> rendererInstance = bestService.getRendererInstance(vElement,
			viewModelContext);
		rendererInstance.init();
		return rendererInstance;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see EMFFormsRendererFactory#getAdditionalRendererInstances(VElement, ViewModelContext)
	 */
	@Override
	public Collection<AbstractAdditionalSWTRenderer<VElement>> getAdditionalRendererInstances(VElement vElement,
		ViewModelContext viewModelContext) {
		final Set<AbstractAdditionalSWTRenderer<VElement>> renderers = new LinkedHashSet<AbstractAdditionalSWTRenderer<VElement>>();

		for (final EMFFormsAdditionalRendererService<VElement> rendererService : additionalRendererServices) {
			if (rendererService.isApplicable(vElement)) {
				final AbstractAdditionalSWTRenderer<VElement> rendererInstance = rendererService.getRendererInstance(
					vElement, viewModelContext);
				rendererInstance.init();
				renderers.add(rendererInstance);
			}
		}

		return renderers;
	}
}
