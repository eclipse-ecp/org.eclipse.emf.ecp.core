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

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.spi.ui.util.ECPHandlerHelper;
import org.eclipse.emf.edit.command.ChangeCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.graphiti.datatypes.IDimension;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.pattern.AbstractPattern;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;
import org.eclipse.graphiti.util.ColorConstant;
import org.eclipse.graphiti.util.IColorConstant;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class EObjectPattern extends AbstractPattern {
	private static final IColorConstant FOREGROUND = new ColorConstant(98, 131,
		167);

	private static final IColorConstant BACKGROUND = new ColorConstant(187,
		218, 247);
	private static final IColorConstant TEXT_FOREGROUND = IColorConstant.BLACK;

	public EObjectPattern() {
		super(null);
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		return context.getTargetContainer() instanceof Diagram;
	}

	@Override
	public Object[] create(ICreateContext context) {
		final Object o = getBusinessObjectForPictogramElement(getDiagram());
		final ECPProject project = ECPUtil.getECPProjectManager().getProject(o);
		final EObject createdEObject = ECPHandlerHelper.addModelElement(project, getShell(), false);
		// Add model element to resource.
		// We add the model element to the resource of the diagram for
		// simplicity's sake. Normally, a customer would use its own
		// model persistence layer for storing the business model separately.
		final EditingDomain editingDomain = project.getEditingDomain();
		editingDomain.getCommandStack().execute(
			new ChangeCommand(createdEObject) {

				@Override
				protected void doExecute() {
					project.getContents().add(createdEObject);
				}
			});

		// do the add
		addGraphicalRepresentation(context, createdEObject);

		// return newly created business object(s)
		return new Object[] { createdEObject };
	}

	@Override
	public String getCreateName() {
		return "Node";
	}

	@Override
	public String getCreateImageId() {
		return "addEObject";
	}

	@Override
	public boolean isMainBusinessObjectApplicable(Object mainBusinessObject) {
		return mainBusinessObject instanceof EObject && !EReference.class.isInstance(mainBusinessObject);
		// && !EAttribute.class.isInstance(mainBusinessObject)
	}

	@Override
	protected boolean isPatternControlled(PictogramElement pictogramElement) {
		final Object domainObject = getBusinessObjectForPictogramElement(pictogramElement);
		return isMainBusinessObjectApplicable(domainObject);
	}

	@Override
	protected boolean isPatternRoot(PictogramElement pictogramElement) {
		final Object domainObject = getBusinessObjectForPictogramElement(pictogramElement);
		return isMainBusinessObjectApplicable(domainObject);
	}

	@Override
	public boolean canAdd(IAddContext context) {
		return context.getNewObject() instanceof EObject && !EReference.class.isInstance(context.getNewObject());
	}

	@Override
	public PictogramElement add(IAddContext context) {

		final EObject addedClass = (EObject) context.getNewObject();

		final ContainerShape container = context.getTargetContainer();

		// CONTAINER SHAPE WITH ROUNDED RECTANGLE
		final IPeCreateService peCreateService = Graphiti.getPeCreateService();

		final ContainerShape containerShape = peCreateService.createContainerShape(
			container, true);

		// define a default size for the shape
		final int width = context.getWidth() <= 0 ? 100 : context.getWidth();
		final int height = context.getHeight() <= 0 ? 50 : context.getHeight();
		final IGaService gaService = Graphiti.getGaService();
		RoundedRectangle roundedRectangle; // need to access it later

		{
			// create and set graphics algorithm
			roundedRectangle = gaService.createRoundedRectangle(containerShape,
				5, 5);
			roundedRectangle.setForeground(manageColor(FOREGROUND));
			roundedRectangle.setBackground(manageColor(BACKGROUND));

			roundedRectangle.setLineWidth(2);
			gaService.setLocationAndSize(roundedRectangle, context.getX(),
				context.getY(), width, height);

			// create link and wire it
			link(containerShape, addedClass);
		}

		// SHAPE WITH LINE
		{
			// create shape for line
			final Shape shape = peCreateService.createShape(containerShape, false);

			// create and set graphics algorithm
			final Polyline polyline = gaService.createPolyline(shape, new int[] { 0,
				20, width, 20 });
			polyline.setForeground(manageColor(FOREGROUND));

			polyline.setLineWidth(2);
		}

		// SHAPE WITH TEXT
		{
			final ComposedAdapterFactory composedAdapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
				new ReflectiveItemProviderAdapterFactory(),
				new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
			final AdapterFactoryItemDelegator adapterFactoryItemDelegator = new AdapterFactoryItemDelegator(
				composedAdapterFactory);

			// Single Text
			// create shape for text
			final Shape shape = peCreateService.createShape(containerShape, false);
			//
			// create and set text graphics algorithm
			final Text text = gaService.createText(shape,
				adapterFactoryItemDelegator.getText(addedClass));
			text.setForeground(manageColor(TEXT_FOREGROUND));
			text.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
			// vertical alignment has as default value "center"
			text.setFont(gaService.manageDefaultFont(getDiagram(), false, true));
			gaService.setLocationAndSize(text, 0, 0, width, 20);

			// create link and wire it
			// link(shape, addedClass);
			composedAdapterFactory.dispose();
		}
		// ADD ANCHOR(S)
		{
			peCreateService.createChopboxAnchor(containerShape);
		}

		layoutPictogramElement(containerShape);
		return containerShape;
	}

	@Override
	public IReason updateNeeded(IUpdateContext context) {
		// retrieve name from pictogram model
		String pictogramName = null;
		final PictogramElement pictogramElement = context.getPictogramElement();
		if (pictogramElement instanceof ContainerShape) {
			final ContainerShape cs = (ContainerShape) pictogramElement;
			for (final Shape shape : cs.getChildren()) {
				if (shape.getGraphicsAlgorithm() instanceof Text) {
					final Text text = (Text) shape.getGraphicsAlgorithm();
					pictogramName = text.getValue();
				}
			}
		}

		final ComposedAdapterFactory composedAdapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new ReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		final AdapterFactoryItemDelegator adapterFactoryItemDelegator = new AdapterFactoryItemDelegator(
			composedAdapterFactory);

		// retrieve name from business model
		String businessName = null;
		final Object bo = getBusinessObjectForPictogramElement(pictogramElement);
		if (bo instanceof EObject) {
			businessName = adapterFactoryItemDelegator.getText(bo);
		}
		composedAdapterFactory.dispose();
		// update needed, if names are different
		final boolean updateNameNeeded = pictogramName == null && businessName != null || pictogramName != null
			&& !pictogramName
				.equals(businessName);
		if (updateNameNeeded) {
			return Reason.createTrueReason("Name is out of date");
		} else {
			return Reason.createFalseReason();
		}
	}

	@Override
	public boolean update(IUpdateContext context) {
		// retrieve name from business model
		String businessName = null;
		final PictogramElement pictogramElement = context.getPictogramElement();
		final Object bo = getBusinessObjectForPictogramElement(pictogramElement);
		final ComposedAdapterFactory composedAdapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new ReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		final AdapterFactoryItemDelegator adapterFactoryItemDelegator = new AdapterFactoryItemDelegator(
			composedAdapterFactory);
		if (bo instanceof EObject) {
			businessName = adapterFactoryItemDelegator.getText(bo);
		}
		composedAdapterFactory.dispose();

		// Set name in pictogram model
		if (pictogramElement instanceof ContainerShape) {
			final ContainerShape cs = (ContainerShape) pictogramElement;
			for (final Shape shape : cs.getChildren()) {
				if (shape.getGraphicsAlgorithm() instanceof Text) {
					final Text text = (Text) shape.getGraphicsAlgorithm();
					text.setValue(businessName);
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public boolean layout(ILayoutContext context) {
		boolean anythingChanged = false;
		final ContainerShape containerShape = (ContainerShape) context
			.getPictogramElement();
		final GraphicsAlgorithm containerGa = containerShape.getGraphicsAlgorithm();

		// // height
		// if (containerGa.getHeight() < MIN_HEIGHT) {
		// containerGa.setHeight(MIN_HEIGHT);
		// anythingChanged = true;
		// }
		//
		// // width
		// if (containerGa.getWidth() < MIN_WIDTH) {
		// containerGa.setWidth(MIN_WIDTH);
		// anythingChanged = true;
		// }

		final int containerWidth = containerGa.getWidth();

		for (final Shape shape : containerShape.getChildren()) {
			final GraphicsAlgorithm graphicsAlgorithm = shape.getGraphicsAlgorithm();
			final IGaService gaService = Graphiti.getGaService();
			final IDimension size = gaService.calculateSize(graphicsAlgorithm);
			if (containerWidth != size.getWidth()) {
				if (graphicsAlgorithm instanceof Polyline) {
					final Polyline polyline = (Polyline) graphicsAlgorithm;
					final Point secondPoint = polyline.getPoints().get(1);
					final Point newSecondPoint = gaService.createPoint(
						containerWidth, secondPoint.getY());
					polyline.getPoints().set(1, newSecondPoint);
					anythingChanged = true;
				} else {
					gaService.setWidth(graphicsAlgorithm, containerWidth);
					anythingChanged = true;
				}
			}
		}
		return anythingChanged;
	}

	/**
	 * Returns the currently active Shell.
	 *
	 * @return The currently active Shell.
	 */
	private static Shell getShell() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
	}
}
