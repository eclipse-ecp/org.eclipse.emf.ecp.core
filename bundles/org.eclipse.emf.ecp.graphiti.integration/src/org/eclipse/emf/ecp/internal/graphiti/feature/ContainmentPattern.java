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
import org.eclipse.graphiti.features.context.ICreateConnectionContext;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.mm.GraphicsAlgorithmContainer;
import org.eclipse.graphiti.mm.algorithms.Polygon;
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

public class ContainmentPattern extends AbstractConnectionPattern {
	private static final IColorConstant FOREGROUND = new ColorConstant(98, 131,
			167);

	@Override
	public PictogramElement add(IAddContext context) {
		IAddConnectionContext addConContext = (IAddConnectionContext) context;

		IPeCreateService peCreateService = Graphiti.getPeCreateService();

		// CONNECTION WITH POLYLINE
		Connection connection = peCreateService
				.createFreeFormConnection(getDiagram());

		connection.setStart(addConContext.getSourceAnchor());
		connection.setEnd(addConContext.getTargetAnchor());

		IGaService gaService = Graphiti.getGaService();
		Polyline polyline = gaService.createPolyline(connection);
		polyline.setLineWidth(2);
		polyline.setForeground(manageColor(FOREGROUND));

		// add dynamic text decorator for the association name
		ConnectionDecorator textDecorator = peCreateService
				.createConnectionDecorator(connection, true, 0.5, true);
		Text text = gaService.createDefaultText(getDiagram(), textDecorator);
		text.setForeground(manageColor(IColorConstant.BLACK));
		gaService.setLocation(text, 10, 0);

		// set reference name in the text decorator
//		EReference eReference = (EReference) context.getNewObject();
		EReference eReference = (EReference) context.getProperty("containment");
		text.setValue(eReference.getName());
		// create link and wire it
//		link(connection, eReference);
		ConnectionDecorator cd = peCreateService.createConnectionDecorator(
				connection, false, 0.0, true);
		createArrow(cd);

		return connection;
	}

	@Override
	public void attachedToSource(ICreateConnectionContext context) {
		// TODO Auto-generated method stub
		super.attachedToSource(context);
		PictogramElement sourceElement = context.getSourcePictogramElement();
		if (sourceElement == getDiagram())
			return;
		EObject source = (EObject) getBusinessObjectForPictogramElement(sourceElement);
		if (source == null)
			return;
		for (EReference eReference : source.eClass().getEAllContainments()) {
			
		EClass referenceClass=eReference.getEReferenceType();
		ECPProject project=ECPUtil.getECPProjectManager().getProject(getBusinessObjectForPictogramElement(getDiagram()));
		InternalProject internalProject=(InternalProject)project;
		Iterator<EObject> referenceIterator=internalProject.getReferenceCandidates(source, eReference);
		
		// Mark the allowed squares using decorators
		ECPTutorialToolBehaviorProvider toolBehaviorProvider = (ECPTutorialToolBehaviorProvider) getFeatureProvider()
				.getDiagramTypeProvider().getCurrentToolBehaviorProvider();
		while(referenceIterator.hasNext()) {
			EObject eObject=referenceIterator.next();
			if(eReference.isMany()){
				List<?> objects=(List<?>) source.eGet(eReference);
				if(objects.contains(eObject))
					continue;
			}
			else{
				Object object=source.eGet(eReference);
				if(eObject.equals(object))
					continue;
			}
			toolBehaviorProvider.allowEObject(eObject);
			PictogramElement pe = getFeatureProvider()
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
	public boolean canStartConnection(ICreateConnectionContext context) {
		PictogramElement sourceElement = context.getSourcePictogramElement();
		if (sourceElement == getDiagram())
			return false;
		EObject source = (EObject) getBusinessObjectForPictogramElement(sourceElement);
		if (source == null)
			return false;
		return !source.eClass().getEAllContainments().isEmpty();
	}

	@Override
	public boolean canCreate(ICreateConnectionContext context) {
		PictogramElement targetPictogramElement = context
				.getTargetPictogramElement();
		if (targetPictogramElement == getDiagram())
			return false;
		EObject source = (EObject) getBusinessObjectForPictogramElement(context
				.getSourcePictogramElement());
		EObject target = (EObject) getBusinessObjectForPictogramElement(targetPictogramElement);
		if (target == null) {
			return false;
		}
		if (target.eContainer() == source) {
			return false;
		}
		for (EReference eReference : source.eClass().getEAllContainments()) {
			if (eReference.getEReferenceType().isInstance(target)) {
				if (eReference.isMany()
						&& eReference.getUpperBound() != -1
						&& eReference.getUpperBound() <= ((List) source
								.eGet(eReference)).size()) {
					return false;
				}
				context.putProperty("containment", eReference);
				return true;
			}
		}
		return false;
	}

	@Override
	public Connection create(ICreateConnectionContext context) {
		EObject source = (EObject) getBusinessObjectForPictogramElement(context
				.getSourcePictogramElement());
		EObject target = (EObject) getBusinessObjectForPictogramElement(context
				.getTargetPictogramElement());
		EReference eReference = (EReference) context.getProperty("containment");

		Object o = getBusinessObjectForPictogramElement(getDiagram());
		final ECPProject project = ECPUtil.getECPProjectManager().getProject(o);
		EditingDomain editingDomain = project.getEditingDomain();
		Command command = null;
		if (eReference.isMany()) {
			command = AddCommand.create(editingDomain, source, eReference,
					target);
		} else {
			command = SetCommand.create(editingDomain, source, eReference,
					target);
		}
		editingDomain.getCommandStack().execute(command);

		AddConnectionContext addContext = new AddConnectionContext(
				context.getSourceAnchor(), context.getTargetAnchor());
		 addContext.setNewObject(eReference);
		Connection newConnection = (Connection) getFeatureProvider()
				.addIfPossible(addContext);

		return newConnection;
	}

	@Override
	public String getCreateName() {
		return "Containment Edge";
	}

	@Override
	public String getCreateImageId() {
		return "containment";
	}

	private Polyline createArrow(GraphicsAlgorithmContainer gaContainer) {
		IGaService gaService = Graphiti.getGaService();
		Polygon polygon = gaService.createPolygon(gaContainer, new int[] { -10,
				-10, -20, 0, -10, 10, 0, 0 });
		polygon.setBackground(manageColor(FOREGROUND));
		polygon.setFilled(true);
		polygon.setLineVisible(false);
		return polygon;
	}

	public boolean canAdd(IAddContext context) {
		// return true if given business object is an EReference
		// note, that the context must be an instance of IAddConnectionContext
		if (!IAddConnectionContext.class.isInstance(context))
			return false;
		IAddConnectionContext connectionContext = (IAddConnectionContext) context;
		EObject sourceObject = (EObject) getFeatureProvider()
				.getBusinessObjectForPictogramElement(
						(PictogramElement) connectionContext.getSourceAnchor()
								.eContainer());
		EObject targetObject = (EObject) getFeatureProvider()
				.getBusinessObjectForPictogramElement(
						(PictogramElement) connectionContext.getTargetAnchor()
								.eContainer());
		return targetObject.eContainer() == sourceObject;

	}
	
	
}
