/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Clemens Elflein - initial API and implementation
 * Johannes Faltermeier - reconcile genmodel
 ******************************************************************************/
package org.eclipse.emfforms.internal.editor.genmodel;

import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emfforms.spi.editor.GenericEditor;

/**
 * The Genmodel Editor.
 *
 * @author Clemens Elflein
 * @author Johannes Faltermeier
 */
public class GenModelEditor extends GenericEditor {

	@Override
	public String getEditorTitle() {
		return "Genmodel Editor";
	}

	@Override
	protected Object modifyEditorInput(ResourceSet resourceSet) {
		final Resource resource = resourceSet.getResources().get(0);
		if (!resource.getContents().isEmpty() && resource.getContents().get(0) instanceof GenModel) {
			final GenModel genModel = (GenModel) resource.getContents().get(0);
			genModel.reconcile();
			genModel.setCanGenerate(true);
		}
		return super.modifyEditorInput(resourceSet);
	}
}
