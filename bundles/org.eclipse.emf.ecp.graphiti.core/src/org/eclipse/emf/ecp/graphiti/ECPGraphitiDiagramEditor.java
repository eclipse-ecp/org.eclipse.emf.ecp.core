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
package org.eclipse.emf.ecp.graphiti;

import org.eclipse.graphiti.ui.editor.DiagramBehavior;
import org.eclipse.graphiti.ui.editor.DiagramEditor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;

public abstract class ECPGraphitiDiagramEditor extends DiagramEditor {

	public ECPGraphitiDiagramEditor() {
		super();
	}

	protected GraphitiDiagramEditorInput input;

	@Override
	public void init(IEditorSite site, IEditorInput input)
		throws PartInitException {
		this.input = (GraphitiDiagramEditorInput) input;
		super.init(site, input);
	}

	@Override
	protected DiagramBehavior createDiagramBehavior() {
		final ECPDiagramBehavior diagramBehavior = new ECPDiagramBehavior(this, input.getDiagram(),
			input.getBusinessObject());
		diagramBehavior.setParentPart(this);
		diagramBehavior.initDefaultBehaviors();

		return diagramBehavior;
	}

}
