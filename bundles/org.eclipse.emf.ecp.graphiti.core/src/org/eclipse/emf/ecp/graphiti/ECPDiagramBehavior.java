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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.ui.editor.DefaultPersistencyBehavior;
import org.eclipse.graphiti.ui.editor.DefaultUpdateBehavior;
import org.eclipse.graphiti.ui.editor.DiagramBehavior;
import org.eclipse.graphiti.ui.editor.IDiagramContainerUI;
import org.eclipse.ui.IWorkbenchPart;

public class ECPDiagramBehavior extends DiagramBehavior {

	private final Diagram diagram;
	private final EObject businessObject;

	public ECPDiagramBehavior(IDiagramContainerUI diagramContainer, Diagram diagram, EObject businessObject) {
		super(diagramContainer);
		this.diagram = diagram;
		this.businessObject = businessObject;
	}

	@Override
	protected DefaultUpdateBehavior createUpdateBehavior() {
		return new UpdateBehavior(this);

	}

	@Override
	protected DefaultPersistencyBehavior createPersistencyBehavior() {
		return new DefaultPersistencyBehavior(this) {

			@Override
			public Diagram loadDiagram(URI uri) {
				return diagram;
			}

		};
	}

	@Override
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		getEditingDomain().getCommandStack().execute(
			new RecordingCommand(getEditingDomain()) {

				@Override
				protected void doExecute() {
					getDiagramTypeProvider().getFeatureProvider().link(
						getDiagramTypeProvider().getDiagram(),
						businessObject);
				}
			});
	}

	@Override
	public void initDefaultBehaviors() {
		// TODO Auto-generated method stub
		super.initDefaultBehaviors();
	}

	@Override
	public void setParentPart(IWorkbenchPart parentPart) {
		// TODO Auto-generated method stub
		super.setParentPart(parentPart);
	}

	@Override
	public void editingDomainInitialized() {
		// TODO Auto-generated method stub
		super.editingDomainInitialized();
	}

	@Override
	public void disableAdapters() {
		// TODO Auto-generated method stub
		super.disableAdapters();
	}

	@Override
	public void enableAdapters() {
		// TODO Auto-generated method stub
		super.enableAdapters();
	}

	public Diagram getDiagram() {
		return diagram;
	}

}
