/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.util.e4.fx;

import java.util.List;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESWorkspaceProvider;
import org.eclipse.emf.emfstore.common.model.ESModelElementId;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.internal.common.model.ModelFactory;
import org.eclipse.emf.emfstore.internal.common.model.impl.ESModelElementIdImpl;

/**
 * @author Lucas
 *
 */
@SuppressWarnings("restriction")
public class ModelElementOpenerFX {

	private static final String partId = "org.eclipse.emf.ecp.application.e4.fx.partdescriptor.editor"; //$NON-NLS-1$
	private static final String MODEL_ELEMENT_ID_PERSIST_KEY = "modelElementId"; //$NON-NLS-1$
	private static final String PROJECT_ID_PERSIST_KEY = "projectId"; //$NON-NLS-1$

	/**
	 * Opens a model element in {@link ECPE4Editor}. {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.ui.util.ECPModelElementOpener#openModelElement(java.lang.Object,
	 *      org.eclipse.emf.ecp.core.ECPProject)
	 */
	public static void openModelElement(Object modelElement) {
		final EPartService partService = getEPartService();
		for (final MPart existingPart : partService.getParts()) {
			if (!partId.equals(existingPart.getElementId())) {
				continue;
			}

			if (existingPart.getContext() == null) {
				final List<ESLocalProject> projects = ESWorkspaceProvider.INSTANCE.getWorkspace().getLocalProjects();
				ESLocalProject project = null;
				final String projectId = existingPart.getPersistedState().get(PROJECT_ID_PERSIST_KEY);
				for (final ESLocalProject p : projects) {
					if (p.getLocalProjectId().getId().equals(projectId)) {
						project = p;
						break;
					}
				}
				if (project != null) {
					final String modelElementIdString = existingPart.getPersistedState().get(
						MODEL_ELEMENT_ID_PERSIST_KEY);
					final ModelElementId mEId = ModelFactory.eINSTANCE.createModelElementId();
					mEId.setId(modelElementIdString);
					final ESModelElementId modelElementId = new ESModelElementIdImpl(mEId);
					final EObject persistedModelElement = project.getModelElement(modelElementId);
					if (modelElement.equals(persistedModelElement)) {
						if (!existingPart.isVisible() || !existingPart.isOnTop()) {
							partService.showPart(existingPart, PartState.ACTIVATE);
						}
						return;
					}
					continue;
				}
			}

			if (existingPart.getContext().get("ecpEditorInput") == modelElement) {
				if (!existingPart.isVisible() || !existingPart.isOnTop()) {
					partService.showPart(existingPart, PartState.ACTIVATE);
				}
				return;
			}
		}

		final MPart part = partService.createPart(partId);
		partService.showPart(part, PartState.ACTIVATE);
		part.getContext().set("ecpEditorInput", modelElement);
	}

	private static EPartService getEPartService() {
		final MApplication currentApplication = E4Workbench.getServiceContext().get(IWorkbench.class).getApplication();
		final IEclipseContext selectedWindowContext = currentApplication.getSelectedElement().getContext();
		return selectedWindowContext.get(EPartService.class);
	}
}
