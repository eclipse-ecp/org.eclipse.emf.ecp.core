package org.eclipse.emf.ecp.internal.ui.view.renderer;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.edit.ECPControl;
import org.eclipse.emf.ecp.edit.ECPControlContext;
import org.eclipse.emf.ecp.edit.internal.swt.util.SWTControl;
import org.eclipse.emf.ecp.view.model.Renderable;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

// TODO: utility which is not in usgae
public abstract class RendererLeaf<R extends Renderable> extends Node<R> {

	private ECPControl ecpControl;
	
	public RendererLeaf(
			R model, 
			ECPControlContext controlContext,
			ECPControl ecpControl) {
		super(model);
		this.setEcpControl(ecpControl);
	}

	@Override
	public void addChild(Node node) {
		// TODO
		throw new UnsupportedOperationException();
	}
	
	@Override
	public List<Node> getChildren() {
		// TODO docs
		throw new UnsupportedOperationException();
	}
	
	public boolean canValidate() {
		return getEcpControl() != null;
	}

	public void validationChanged(Map<EObject, Set<Diagnostic>> affectedObjects) {
		
		if (!canValidate()) {
			return;
		}
		
		getEcpControl().resetValidation();
		if (affectedObjects.containsKey(getRenderable())) { 
			for (Diagnostic diagnostic : affectedObjects.get(getRenderable())) {
				getEcpControl().handleValidation(diagnostic);
			}
		}
	}
	
	@Override
	public boolean isLeaf() {
		return true;
	}

	public ECPControl getEcpControl() {
		return ecpControl;
	}

	public void setEcpControl(ECPControl ecpControl) {
		this.ecpControl = ecpControl;
	}
}
