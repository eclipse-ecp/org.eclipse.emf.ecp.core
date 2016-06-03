/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.template.tooling.editor;

import java.io.IOException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecp.internal.ide.util.EcoreHelper;
import org.eclipse.emf.ecp.view.template.internal.tooling.Activator;
import org.eclipse.emf.ecp.view.template.internal.tooling.Messages;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplate;
import org.eclipse.emfforms.spi.editor.GenericEditor;
import org.eclipse.emfforms.spi.swt.treemasterdetail.TreeMasterDetailComposite;
import org.eclipse.emfforms.spi.swt.treemasterdetail.util.CreateElementCallback;
import org.eclipse.emfforms.spi.swt.treemasterdetail.util.RootObject;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;

/**
 * EditorPart for the Template Model Editor.
 *
 * @author Eugen Neufeld
 *
 */
@SuppressWarnings("restriction")
public class TemplateModelEditorPart extends GenericEditor {

	private VTViewTemplate template;
	private TreeMasterDetailComposite treeMasterDetail;

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);
		super.setPartName(input.getName());

		final FileEditorInput fei = (FileEditorInput) getEditorInput();

		final ResourceSet resourceSet = new ResourceSetImpl();
		try {
			final Resource resource = resourceSet.createResource(URI.createURI(fei.getURI().toURL().toExternalForm()));
			resource.load(null);
			final EList<EObject> resourceContents = resource.getContents();
			if (resourceContents.size() > 0 && VTViewTemplate.class.isInstance(resourceContents.get(0))) {
				final VTViewTemplate template = (VTViewTemplate) resourceContents.get(0);
				for (final String ecorePath : template.getReferencedEcores()) {
					EcoreHelper.registerEcore(ecorePath);
				}
			} else {
				throw new PartInitException(Messages.TemplateModelEditorPart_initError);
			}

		} catch (final IOException e) {
			Activator.log(e);
			throw new PartInitException(Messages.TemplateModelEditorPart_initError, e);
		}

	}

	@Override
	protected Object modifyEditorInput(ResourceSet resourceSet) {
		/* this access is save, otherwise we would have thrown a part init exception in init */
		template = VTViewTemplate.class.cast(resourceSet.getResources().get(0).getContents().get(0));
		return new RootObject(template);
	}

	@Override
	protected TreeMasterDetailComposite createTreeMasterDetail(Composite composite, Object editorInput,
		CreateElementCallback createElementCallback) {
		treeMasterDetail = super.createTreeMasterDetail(composite, editorInput, createElementCallback);
		return treeMasterDetail;
	}

	@Override
	public void dispose() {
		if (template != null) {
			for (final String ecorePath : template.getReferencedEcores()) {
				EcoreHelper.unregisterEcore(ecorePath);
			}
		}
		super.dispose();
	}

	/**
	 * Gives access to the template model which is the input of the editor.
	 *
	 * @return the {@link VTViewTemplate}
	 */
	public VTViewTemplate getTemplate() {
		return template;
	}

	/**
	 * The given element will be revealed in the tree of the editor.
	 *
	 * @param objectToReveal the object to reveal
	 */
	public void reveal(EObject objectToReveal) {
		treeMasterDetail.getSelectionProvider().refresh();
		treeMasterDetail.getSelectionProvider().reveal(objectToReveal);
		treeMasterDetail.setSelection(new StructuredSelection(objectToReveal));
	}

}
