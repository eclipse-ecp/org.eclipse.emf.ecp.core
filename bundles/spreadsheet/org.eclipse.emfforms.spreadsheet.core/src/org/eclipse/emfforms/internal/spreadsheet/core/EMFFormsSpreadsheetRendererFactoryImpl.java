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
package org.eclipse.emfforms.internal.spreadsheet.core;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsAbstractSpreadsheetRenderer;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetRendererFactory;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetRendererService;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsNoRendererException;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * Implementation of the EMFFormsSpreadsheetRendererFactory.
 * 
 * @author Eugen Neufeld
 */
@Component
public class EMFFormsSpreadsheetRendererFactoryImpl implements EMFFormsSpreadsheetRendererFactory {

	private final Set<EMFFormsSpreadsheetRendererService<VElement>> rendererServices = new LinkedHashSet<EMFFormsSpreadsheetRendererService<VElement>>();

	@Override
	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	public void addEMFFormsSpreadsheetRendererService(EMFFormsSpreadsheetRendererService<VElement> spreadsheetRendererService) {
		rendererServices.add(spreadsheetRendererService);
	}

	@Override
	public void removeEMFFormsSpreadsheetRendererService(EMFFormsSpreadsheetRendererService<VElement> spreadsheetRendererService) {
		rendererServices.remove(spreadsheetRendererService);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetRendererFactory#getRendererInstance(org.eclipse.emf.ecp.view.spi.model.VElement,
	 *      org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public <VELEMENT extends VElement> EMFFormsAbstractSpreadsheetRenderer<VElement> getRendererInstance(VELEMENT vElement,
		ViewModelContext viewModelContext) throws EMFFormsNoRendererException {
		EMFFormsSpreadsheetRendererService<VElement> bestFitting = null;
		double highestPriority = Double.NEGATIVE_INFINITY;
		for (final EMFFormsSpreadsheetRendererService<VElement> rendererService : rendererServices) {
			final double currentPriority = rendererService.isApplicable(vElement, viewModelContext);
			if (currentPriority > highestPriority) {
				highestPriority = currentPriority;
				bestFitting = rendererService;
			}
		}
		if (bestFitting == null)
		{
			throw new EMFFormsNoRendererException(String.format(
				"No fitting renderer for %1$s found!", vElement.eClass().getName())); //$NON-NLS-1$
		}
		return bestFitting.getRendererInstance(vElement, viewModelContext);
	}

}
