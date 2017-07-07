/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.editor.genmodel.toolbaractions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emfforms.internal.editor.genmodel.Messages;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.widgets.Display;

/**
 * The ToolbarAction allowing the User to generate Java code from the EcoreEditor.
 *
 * @author Alexandra Buzila
 *
 */
public class GenerateEcoreEditorJavaCodeAction extends GenerateJavaCodeAction {

	@Override
	public boolean canExecute(Object object) {
		if (!ResourceSet.class.isInstance(object)) {
			return false;
		}
		// We can execute our Action only if the ResourceSet contains an EPackage
		return getEPackage((ResourceSet) object) != null;

	}

	@Override
	public Action getAction(Object currentObject, ISelectionProvider selectionProvider) {
		final ResourceSet resourceSet = (ResourceSet) currentObject;
		return new CreateEcoreJavaCodeAction(resourceSet, selectionProvider);
	}

	@Override
	protected GenModel getGenModel(ResourceSet resourceSet) {
		final EPackage ePackage = getEPackage(resourceSet);
		if (ePackage == null) {
			return null;
		}
		final URI resourceURI = ePackage.eResource().getURI();
		final URI uri = ePackage.eResource().getResourceSet().getURIConverter().normalize(resourceURI);
		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		final IFile file = root.getFile(new Path(uri.toPlatformString(true)));
		final IContainer parent = file.getParent();
		return getGenModelFile(parent, resourceSet, ePackage);
	}

	private GenModel getGenModelFile(IContainer parent, ResourceSet resourceSet, EPackage ePackage) {
		for (final IResource resource : getGenModelFiles(parent)) {
			final Resource emfResource = resourceSet
				.getResource(URI.createPlatformResourceURI(resource.getFullPath().toString(), true), true);
			if (emfResource.getContents().isEmpty() || !GenModel.class.isInstance(emfResource.getContents().get(0))) {
				continue;
			}
			final GenModel genModel = (GenModel) emfResource.getContents().get(0);
			for (final GenPackage genPackage : genModel.getGenPackages()) {
				if (ePackage.getNsURI() != null && genPackage.getEcorePackage() != null
					&& ePackage.getNsURI().equals(genPackage.getEcorePackage().getNsURI())) {
					resourceSet.getResources().remove(emfResource);
					return genModel;
				}
			}
		}
		return null;
	}

	private List<IResource> getGenModelFiles(IContainer parent) {
		final List<IResource> genModelFiles = new ArrayList<IResource>();
		try {
			for (final IResource member : parent.members()) {
				if (member.getFileExtension().equals("genmodel")) { //$NON-NLS-1$
					genModelFiles.add(member);
				}
			}
		} catch (final CoreException ex) {
			// ignore
		}
		return genModelFiles;
	}

	/**
	 * Returns the first {@link EPackage} object found in the first resource of the given {@link ResourceSet}.
	 *
	 * @param resourceSet the {@link ResourceSet} to check
	 * @return the {@link EPackage} or <code>null</code> if none was found
	 */
	protected EPackage getEPackage(ResourceSet resourceSet) {
		if (resourceSet.getResources().isEmpty()) {
			return null;
		}
		final Resource topResource = resourceSet.getResources().get(0);
		if (!topResource.getContents().isEmpty() && EPackage.class.isInstance(topResource.getContents().get(0))) {
			return (EPackage) topResource.getContents().get(0);
		}
		return null;
	}

	/**
	 * ToolbarAction to generate Java Code from the EcoreEditor.
	 */
	class CreateEcoreJavaCodeAction extends CreateJavaCodeAction {

		private final ResourceSet resourceSet;

		/**
		 * Constructor.
		 *
		 * @param resourceSet the {@link ResourceSet} containing the genModel file
		 * @param selectionProvider the {@link ISelectionProvider}
		 */
		CreateEcoreJavaCodeAction(ResourceSet resourceSet, ISelectionProvider selectionProvider) {
			super(selectionProvider);
			this.resourceSet = resourceSet;
		}

		/**
		 * Constructor.
		 *
		 * @param resourceSet the {@link ResourceSet} containing the genModel file
		 * @param text the string used as the text for the action, or null if there is no text
		 * @param types the project types
		 * @param selectionProvider the {@link ISelectionProvider}
		 */
		CreateEcoreJavaCodeAction(ResourceSet resourceSet, String text, Object[] types,
			ISelectionProvider selectionProvider) {
			super(text, types, selectionProvider);
			this.resourceSet = resourceSet;
		}

		@Override
		public void run() {
			final GenModel genModel = GenerateEcoreEditorJavaCodeAction.this.getGenModel(resourceSet);
			if (genModel == null) {
				showNoGenModelDialog();
				return;
			}
			setGenModel(genModel);
			super.run();
		}

		@Override
		protected Action getJavaCodeAction(String text, Object[] types) {
			return new CreateEcoreJavaCodeAction(resourceSet, text, types, getSelectionProvider());
		}

		private void showNoGenModelDialog() {
			MessageDialog.openConfirm(Display.getCurrent().getActiveShell(),
				Messages.GenerateEcoreEditorJavaCodeAction_noGenModelDialogTitle,
				Messages.GenerateEcoreEditorJavaCodeAction_noGenModelDialogMessage);
		}

	}
}
