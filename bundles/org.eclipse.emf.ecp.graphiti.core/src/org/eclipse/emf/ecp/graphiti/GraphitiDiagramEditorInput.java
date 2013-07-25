package org.eclipse.emf.ecp.graphiti;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.ui.editor.IDiagramEditorInput;
import org.eclipse.graphiti.ui.services.GraphitiUi;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class GraphitiDiagramEditorInput implements IDiagramEditorInput, IEditorInput {

	private final Diagram diagram;
	private final EObject businessObject;

	public GraphitiDiagramEditorInput(Diagram diagram, EObject businessObject) {
		this.diagram = diagram;
		this.businessObject = businessObject;
	}

	public Diagram getDiagram() {
		return diagram;
	}

	public EObject getBusinessObject() {
		return businessObject;
	}

	public String getProviderId() {
		return GraphitiUi.getExtensionManager().getDiagramTypeProviderId(diagram.getDiagramTypeId());
	}

	public void setProviderId(String providerId) {

	}

	public String getUriString() {
		return null;
	}

	public URI getUri() {
		return null;
	}

	public void updateUri(URI newURI) {

	}

	public Object getAdapter(Class adapter) {
		return null;
	}

	public boolean exists() {
		return false;
	}

	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	public String getName() {
		return null;
	}

	public IPersistableElement getPersistable() {
		return null;
	}

	public String getToolTipText() {
		return null;
	}

}
