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
 * Martin Fleck - bug 495190: add tree-master-detail customization
 ******************************************************************************/
package org.eclipse.emfforms.internal.editor.genmodel;

import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emfforms.spi.editor.GenericEditor;
import org.eclipse.emfforms.spi.swt.treemasterdetail.TreeMasterDetailComposite;
import org.eclipse.emfforms.spi.swt.treemasterdetail.TreeMasterDetailSWTFactory;
import org.eclipse.emfforms.spi.swt.treemasterdetail.util.CreateElementCallback;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

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

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.editor.GenericEditor#createTreeMasterDetail(org.eclipse.swt.widgets.Composite,
	 *      java.lang.Object, org.eclipse.emfforms.spi.swt.treemasterdetail.util.CreateElementCallback)
	 */
	@Override
	protected TreeMasterDetailComposite createTreeMasterDetail(Composite composite, Object editorInput,
		CreateElementCallback createElementCallback) {
		return TreeMasterDetailSWTFactory.createTreeMasterDetail(composite, SWT.NONE, editorInput,
			new GenModelEditorTMDCustomization(createElementCallback, (Notifier) editorInput));
	}

}
