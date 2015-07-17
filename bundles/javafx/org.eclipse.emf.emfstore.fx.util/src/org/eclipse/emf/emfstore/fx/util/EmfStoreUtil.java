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
package org.eclipse.emf.emfstore.fx.util;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESWorkspaceProvider;
import org.eclipse.emf.emfstore.common.model.ESModelElementId;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.internal.common.model.ModelFactory;
import org.eclipse.emf.emfstore.internal.common.model.impl.ESModelElementIdImpl;

/**
 * This class provides utility methods to access the emf store.
 *
 * @author Lucas Koehler
 *
 */
@SuppressWarnings("restriction")
public final class EmfStoreUtil {

	private EmfStoreUtil() {
	}

	/**
	 * @param projectId the id of the local project
	 * @return the {@link ESLocalProject} for the given id, <code>null</code> if no project was found.
	 */
	public static ESLocalProject getLocalProjectForId(String projectId) {
		final List<ESLocalProject> projects = ESWorkspaceProvider.INSTANCE.getWorkspace().getLocalProjects();
		ESLocalProject project = null;
		for (final ESLocalProject p : projects) {
			if (p.getLocalProjectId().getId().equals(projectId)) {
				project = p;
				break;
			}
		}
		return project;
	}

	/**
	 * @param project the {@link ESLocalProject} which contains the the model object.
	 * @param objectId the id of the model object.
	 * @return the model object, <code>null</code> if no mode object could be found.
	 */
	public static EObject getModelObjectForId(ESLocalProject project, String objectId) {
		final ModelElementId mEId = ModelFactory.eINSTANCE.createModelElementId();
		mEId.setId(objectId);
		final ESModelElementId modelElementId = new ESModelElementIdImpl(mEId);
		return project.getModelElement(modelElementId);
	}

	/**
	 * @param modelObject the model object.
	 * @return the {@link ESLocalProject} which contains the model object.
	 */
	public static ESLocalProject getLocalProjectforModelObject(EObject modelObject) {
		return ESWorkspaceProvider.INSTANCE.getWorkspace().getLocalProject(modelObject);
	}
}
