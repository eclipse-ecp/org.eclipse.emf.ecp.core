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

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.graphiti.internal.integration.ECPTutorialToolBehaviorProvider;
import org.eclipse.emf.ecp.spi.core.InternalProject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.graphiti.features.context.IAddConnectionContext;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICreateConnectionContext;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.mm.GraphicsAlgorithmContainer;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ConnectionDecorator;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.pattern.AbstractConnectionPattern;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;
import org.eclipse.graphiti.util.ColorConstant;
import org.eclipse.graphiti.util.IColorConstant;

public class ReferencePattern extends AbstractConnectionPattern {
	@Override
	public boolean canCreate(ICreateConnectionContext context) {
		final PictogramElement targetPictogramElement = context
			.getTargetPictogramElement();
		if (targetPictogramElement == getDiagram()) {
			return false;
		}
		final EObject source = (EObject) getBusinessObjectForPictogramElement(context
			.getSourcePictogramElement());
		final EObject target = (EObject) getBusinessObjectForPictogramElement(targetPictogramElement);
		if (target == null) {
			return false;
		}
		// if (target.eContainer() == source) {
		// return false;
		// }
		return setReference(source, target, context, true);
	}

	private boolean setReference(EObject source, EObject target, IContext context, boolean newEdge) {
		for (final EReference eReference : source.eClass().getEAllReferences()) {
			if (eReference.getEReferenceType().isInstance(target)) {
				if (eReference.isContainment() || eReference.isContainer()
					|| eReference.isTransient() || eReference.isDerived()
					|| eReference.isVolatile()) {
					continue;
				}
				if (eReference.isMany()
					&& eReference.getUpperBound() != -1
					&& eReference.getUpperBound() <= ((List) source
						.eGet(eReference)).size()) {
					continue;
				}
				if (newEdge) {
					if (eReference.isMany()) {
						final List<?> list = (List<?>) source.eGet(eReference);
						if (list.contains(target)) {
							continue;
						}
					}
					else if (source.eGet(eReference).equals(target)) {
						continue;
					}
				}
				context.putProperty("reference", eReference);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean canStartConnection(ICreateConnectionContext context) {
		final PictogramElement sourceElement = context.getSourcePictogramElement();
		if (sourceElement == getDiagram()) {
			return false;
		}
		final EObject source = (EObject) getBusinessObjectForPictogramElement(sourceElement);
		if (source == null) {
			return false;
		}

		for (final EReference reference : source.eClass().getEAllReferences()) {
			if (!reference.isContainment() && !reference.isContainer()
				&& !reference.isTransient() && !reference.isDerived()
				&& !reference.isVolatile()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void attachedToSource(ICreateConnectionContext context) {
		super.attachedToSource(context);
		final PictogramElement sourceElement = context.getSourcePictogramElement();
		if (sourceElement == getDiagram()) {
			return;
		}
		final EObject source = (EObject) getBusinessObjectForPictogramElement(sourceElement);
		if (source == null) {
			return;
		}

		for (final EReference eReference : source.eClass().getEAllReferences()) {
			if (eReference.isContainment() || eReference.isContainer()
				|| eReference.isTransient() || eReference.isDerived()
				|| eReference.isVolatile()) {
				continue;
			}

			final EClass referenceClass = eReference.getEReferenceType();
			final ECPProject project = ECPUtil.getECPProjectManager()
				.getProject(getBusinessObjectForPictogramElement(getDiagram()));
			final InternalProject internalProject = (InternalProject) project;
			final Iterator<EObject> referenceIterator = internalProject
				.getReferenceCandidates(source, eReference);

			// Mark the allowed squares using decorators
			final ECPTutorialToolBehaviorProvider toolBehaviorProvider = (ECPTutorialToolBehaviorProvider) getFeatureProvider()
				.getDiagramTypeProvider().getCurrentToolBehaviorProvider();

			while (referenceIterator.hasNext()) {
				final EObject eObject = referenceIterator.next();
				if (eReference.isMany()) {
					final List<?> objects = (List<?>) source.eGet(eReference);
					if (objects.contains(eObject)) {
						continue;
					}
				}
				else {
					final Object object = source.eGet(eReference);
					if (eObject.equals(object)) {
						continue;
					}
				}
				toolBehaviorProvider.allowEObject(eObject);
				final PictogramElement pe = getFeatureProvider()
					.getPictogramElementForBusinessObject(eObject);
				getDiagramBehavior().refreshRenderingDecorators(pe);
			}
			toolBehaviorProvider.allowEObject(null);
		}
	}

	@Override
	public void canceledAttaching(ICreateConnectionContext context) {
		// TODO Auto-generated method stub
		super.canceledAttaching(context);
		getDiagramBehavior().refresh();
	}

	@Override
	public Connection create(ICreateConnectionContext context) {
		final EObject source = (EObject) getBusinessObjectForPictogramElement(context
			.getSourcePictogramElement());
		final EObject target = (EObject) getBusinessObjectForPictogramElement(context
			.getTargetPictogramElement());
		final EReference eReference = (EReference) context.getProperty("reference");

		final Object o = getBusinessObjectForPictogramElement(getDiagram());
		final ECPProject project = ECPUtil.getECPProjectManager().getProject(o);
		final EditingDomain editingDomain = project.getEditingDomain();
		Command command = null;
		if (eReference.isMany()) {
			command = AddCommand.create(editingDomain, source, eReference,
				target);
		} else {
			command = SetCommand.create(editingDomain, source, eReference,
				target);
		}
		editingDomain.getCommandStack().execute(command);

		final AddConnectionContext addContext = new AddConnectionContext(
			context.getSourceAnchor(), context.getTargetAnchor());
		addContext.setNewObject(eReference);
		final Connection newConnection = (Connection) getFeatureProvider()
			.addIfPossible(addContext);

		return newConnection;
	}

	@Override
	public String getCreateName() {
		return "Edge";
	}

	@Override
	public String getCreateImageId() {
		return "reference";
	}

	private static final IColorConstant FOREGROUND = new ColorConstant(255,
		131, 167);

	@Override
	public PictogramElement add(IAddContext context) {
		final IAddConnectionContext addConContext = (IAddConnectionContext) context;

		final IPeCreateService peCreateService = Graphiti.getPeCreateService();

		// CONNECTION WITH POLYLINE
		final Connection connection = peCreateService
			.createFreeFormConnection(getDiagram());

		connection.setStart(addConContext.getSourceAnchor());
		connection.setEnd(addConContext.getTargetAnchor());

		final IGaService gaService = Graphiti.getGaService();
		final Polyline polyline = gaService.createPolyline(connection);
		polyline.setLineWidth(2);
		polyline.setForeground(manageColor(FOREGROUND));

		// add dynamic text decorator for the association name
		final ConnectionDecorator textDecorator = peCreateService
			.createConnectionDecorator(connection, true, 0.5, true);
		final Text text = gaService.createDefaultText(getDiagram(), textDecorator);
		text.setForeground(manageColor(IColorConstant.BLACK));
		gaService.setLocation(text, 10, 0);

		// set reference name in the text decorator
		EReference eReference = (EReference) context.getNewObject();
		if (eReference == null) {
			eReference = (EReference) context.getProperty("reference");
		}
		text.setValue(eReference.getName());
		// create link and wire it
		// link(connection, eReference);
		// add static graphical decorator (composition and navigable)
		final ConnectionDecorator cd = peCreateService.createConnectionDecorator(
			connection, false, 1.0, true);
		createArrow(cd);

		return connection;
	}

	private Polyline createArrow(GraphicsAlgorithmContainer gaContainer) {
		final IGaService gaService = Graphiti.getGaService();
		final Polyline polyline = gaService.createPolyline(gaContainer, new int[] {
			-15, 10, 0, 0, -15, -10 });
		polyline.setForeground(manageColor(FOREGROUND));
		polyline.setLineWidth(2);
		return polyline;
	}

	@Override
	public boolean canAdd(IAddContext context) {
		// return true if given business object is an EReference
		// note, that the context must be an instance of IAddConnectionContext
		if (!IAddConnectionContext.class.isInstance(context)) {
			return false;
		}
		final IAddConnectionContext connectionContext = (IAddConnectionContext) context;
		final EObject sourceObject = (EObject) getFeatureProvider()
			.getBusinessObjectForPictogramElement(
				(PictogramElement) connectionContext.getSourceAnchor()
					.eContainer());
		final EObject targetObject = (EObject) getFeatureProvider()
			.getBusinessObjectForPictogramElement(
				(PictogramElement) connectionContext.getTargetAnchor()
					.eContainer());
		return setReference(sourceObject, targetObject, context, false);
		// return sourceObject.eCrossReferences().contains(targetObject);

	}

}
