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
package org.eclipse.emf.ecp.internal.graphiti.feature;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.features.context.impl.AddContext;
import org.eclipse.graphiti.features.impl.AbstractAddFeature;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

public class LoadProjectFeature extends AbstractAddFeature {

	private static final int EOBJECTWIDTH = 100;
	private static final int EOBJECTHEIGHT = 50;
	private final ECPProject project;
	private static final int DEFAULT_OFFSET_X = 20;
	private static final int DEFAULT_OFFSET_Y = 20;

	public LoadProjectFeature(IFeatureProvider fp, ECPProject project) {
		super(fp);
		this.project = project;
	}

	public boolean canAdd(IAddContext context) {
		return true;
	}

	public PictogramElement add(IAddContext context) {
		final Diagram createDiagram = (Diagram) context.getTargetContainer();
		// Object object=getBusinessObjectForPictogramElement(createDiagram);

		int currentXOffset = DEFAULT_OFFSET_X;
		for (final Object rootObject : project.getContents()) {
			if (!EObject.class.isInstance(rootObject)) {
				continue;
			}
			final int maxWidth = createEObjectAddContext(createDiagram,
				(EObject) rootObject, currentXOffset, 0);
			final int nextXOffset = EOBJECTWIDTH + DEFAULT_OFFSET_X;
			if (maxWidth > nextXOffset) {
				currentXOffset += maxWidth + DEFAULT_OFFSET_X;
			} else {
				currentXOffset += nextXOffset;
			}
		}
		for (final Object rootObject : project.getContents()) {
			if (!EObject.class.isInstance(rootObject)) {
				continue;
			}
			final EObject eObject = (EObject) rootObject;
			for (final EObject crossReference : eObject.eCrossReferences()) {
				final ContainerShape shapeFrom = (ContainerShape) getFeatureProvider()
					.getPictogramElementForBusinessObject(eObject);
				final ContainerShape shapeTo = (ContainerShape) getFeatureProvider()
					.getPictogramElementForBusinessObject(crossReference);
				if (shapeFrom == null || shapeTo == null) {
					continue;
				}
				final AddConnectionContext referenceConnection = new AddConnectionContext(
					shapeFrom.getAnchors().get(0), shapeTo.getAnchors()
						.get(0));
				getFeatureProvider().addIfPossible(referenceConnection);
			}
			createCrossReferences(eObject.eContents());
		}

		return null;
	}

	private ContainerShape getContainerShape(EObject eObject) {
		final PictogramElement[] fromShapes = getFeatureProvider()
			.getAllPictogramElementsForBusinessObject(eObject);
		ContainerShape shapeFrom = null;
		for (int i = 0; i < fromShapes.length; i++) {
			if (fromShapes[i].getLink().getBusinessObjects().get(0) == eObject) {
				shapeFrom = (ContainerShape) fromShapes[i];
				break;
			}
		}
		return shapeFrom;
	}

	private void createCrossReferences(EList<EObject> elements) {
		for (final EObject eObject : elements) {
			for (final EObject crossReference : eObject.eCrossReferences()) {

				final ContainerShape shapeFrom = getContainerShape(eObject);
				final ContainerShape shapeTo = getContainerShape(crossReference);
				final AddConnectionContext referenceConnection = new AddConnectionContext(
					shapeFrom.getAnchors().get(0), shapeTo.getAnchors()
						.get(0));
				getFeatureProvider().addIfPossible(referenceConnection);
			}
			createCrossReferences(eObject.eContents());
		}
	}

	private int createEObjectAddContext(Diagram createDiagram,
		EObject rootObject, int widthOffset, int heightOffset) {
		final AddContext addContext = new AddContext();
		addContext.setNewObject(rootObject);
		addContext.setTargetContainer(createDiagram);
		int currentXOffset = widthOffset;
		addContext.setLocation(currentXOffset, DEFAULT_OFFSET_Y + heightOffset);
		getFeatureProvider().addIfPossible(addContext);
		int maxWidth = rootObject.eContents().size()
			* (DEFAULT_OFFSET_X + EOBJECTWIDTH);
		if (maxWidth == 0) {
			maxWidth = EOBJECTWIDTH;
		}

		for (final EObject eObejct : rootObject.eContents()) {
			final int thisMaxWidth = createEObjectAddContext(createDiagram, eObejct,
				currentXOffset, DEFAULT_OFFSET_Y + heightOffset
					+ EOBJECTHEIGHT);
			final int nextXOffset = EOBJECTWIDTH + DEFAULT_OFFSET_X;
			if (thisMaxWidth > nextXOffset) {
				maxWidth = thisMaxWidth;
				currentXOffset += maxWidth + DEFAULT_OFFSET_X;
			} else {
				currentXOffset += nextXOffset;
			}

			final ContainerShape shapeFrom = getContainerShape(rootObject);
			final ContainerShape shapeTo = getContainerShape(eObejct);
			if (shapeTo != null) {
				final AddConnectionContext containmentConnection = new AddConnectionContext(
					shapeFrom.getAnchors().get(0), shapeTo.getAnchors()
						.get(0));
				containmentConnection.setNewObject(eObejct.eContainmentFeature());
				getFeatureProvider().addIfPossible(containmentConnection);
			}
		}
		return maxWidth;
	}

}
