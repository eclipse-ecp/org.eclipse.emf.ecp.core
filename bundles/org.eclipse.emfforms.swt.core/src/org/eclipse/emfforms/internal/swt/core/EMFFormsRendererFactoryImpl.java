/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.swt.core;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.spi.swt.core.AbstractAdditionalSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
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

	private final Set<EMFFormsRendererService<VElement>> rendererServices = new CopyOnWriteArraySet<EMFFormsRendererService<VElement>>();
	private final Set<EMFFormsAdditionalRendererService<VElement>> additionalRendererServices = new CopyOnWriteArraySet<EMFFormsAdditionalRendererService<VElement>>();

	/**
	 * Called by the initializer to add an {@link EMFFormsRendererService}.
	 *
	 * @param rendererService The EMFFormsRendererService to add
	 */
	public void addEMFFormsRendererService(EMFFormsRendererService<VElement> rendererService) {
		rendererServices.add(rendererService);
	}

	/**
	 * Called by the initializer to remove an {@link EMFFormsRendererService}.
	 *
	 * @param rendererService The EMFFormsRendererService to remove
	 */
	public void removeEMFFormsRendererService(EMFFormsRendererService<VElement> rendererService) {
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
	protected void removeEMFFormsAdditionalRendererService(
		EMFFormsAdditionalRendererService<VElement> rendererService) {
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
		if (rendererInstance == null) {
			throw new EMFFormsNoRendererException(String.format(
				"No fitting EMFFormsRendererService for %1$s available!", vElement.eClass().getName())); //$NON-NLS-1$
		}
		try {
			rendererInstance.init();
			// BEGIN SUPRESS CATCH EXCEPTION
		} catch (final RuntimeException e) {
			throw new EMFFormsNoRendererException(e);
		}
		// END SUPRESS CATCH EXCEPTION
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
			if (rendererService.isApplicable(vElement, viewModelContext)) {
				final Collection<AbstractAdditionalSWTRenderer<VElement>> rendererInstances = rendererService
					.getRendererInstances(
						vElement, viewModelContext);
				for (final AbstractAdditionalSWTRenderer<VElement> rendererInstance : rendererInstances) {
					rendererInstance.init();
					renderers.add(rendererInstance);
				}
			}
		}

		return renderers;
	}

	/**
	 * Method for Tests to clear all services.
	 */
	public void clearRenderers() {
		rendererServices.clear();
	}
}
