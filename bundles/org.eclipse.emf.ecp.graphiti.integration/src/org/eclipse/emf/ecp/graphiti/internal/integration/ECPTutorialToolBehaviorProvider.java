/*******************************************************************************
 * Copyright (c) 2011-2012 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 * 
 *******************************************************************************/
package org.eclipse.emf.ecp.graphiti.internal.integration;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.internal.graphiti.feature.ECPDoubleClickFeature;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.context.IDoubleClickContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.tb.BorderDecorator;
import org.eclipse.graphiti.tb.DefaultToolBehaviorProvider;
import org.eclipse.graphiti.tb.IDecorator;
import org.eclipse.graphiti.util.IColorConstant;

public class ECPTutorialToolBehaviorProvider extends DefaultToolBehaviorProvider {

	private EObject allowedEObject;

	public ECPTutorialToolBehaviorProvider(
		IDiagramTypeProvider diagramTypeProvider) {
		super(diagramTypeProvider);
	}

	@Override
	public ICustomFeature getDoubleClickFeature(IDoubleClickContext context) {
		final ICustomFeature customFeature =
			new ECPDoubleClickFeature(getFeatureProvider());
		// canExecute() tests especially if the context contains a EClass
		if (customFeature.canExecute(context)) {
			return customFeature;
		}

		return super.getDoubleClickFeature(context);
	}

	@Override
	public IDecorator[] getDecorators(PictogramElement pe) {
		final Object object = getFeatureProvider().getBusinessObjectForPictogramElement(pe);
		// Check if the business object of the given pictogram element (the
		// square) is one of the allowed squares (see CreateChessMoveFeature)
		// for a move in progress
		if (object instanceof EObject) {
			if (object.equals(allowedEObject)) {
				// Yes --> decorate with an orange border
				final BorderDecorator decorator = new BorderDecorator();
				decorator.setBorderColor(IColorConstant.ORANGE);
				decorator.setBorderWidth(2);
				return new IDecorator[] { decorator };
			}
		}
		return super.getDecorators(pe);
	}

	public void allowEObject(EObject eObject) {
		allowedEObject = eObject;
	}
}
