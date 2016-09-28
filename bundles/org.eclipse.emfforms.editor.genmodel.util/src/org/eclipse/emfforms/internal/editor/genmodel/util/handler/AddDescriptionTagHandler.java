/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.editor.genmodel.util.handler;

import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emfforms.editor.genmodel.util.GenModelUtil;
import org.eclipse.emfforms.internal.editor.genmodel.util.Activator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Handler for adding description placeholders to a {@link GenModel}.
 *
 * @author Johannes Faltermeier
 *
 */
public class AddDescriptionTagHandler extends AbstractHandler {

	private static final String DESCRIPTION_PLACEHOLDER = "<DESCRIPTION-PLACEHOLDER>"; //$NON-NLS-1$

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			final ISelection currentSelection = HandlerUtil.getCurrentSelection(event);
			if (!IStructuredSelection.class.isInstance(currentSelection)) {
				Activator.log("Unknown selection"); //$NON-NLS-1$
				return null;
			}
			final Object selectedElement = IStructuredSelection.class.cast(currentSelection).getFirstElement();
			if (!IFile.class.isInstance(selectedElement)) {
				Activator.log("Selection is not a file"); //$NON-NLS-1$
				return null;
			}
			final IFile file = IFile.class.cast(selectedElement);
			final URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), false);
			final ResourceSet resourceSet = new ResourceSetImpl();
			final Resource resource = resourceSet.createResource(uri);
			resource.load(null);
			if (resource.getContents().size() != 1) {
				Activator.log("Multiple contents detected"); //$NON-NLS-1$
				return null;
			}
			final EObject resourceContents = resource.getContents().get(0);
			if (!GenModel.class.isInstance(resourceContents)) {
				Activator.log("Selection is not a genmodel"); //$NON-NLS-1$
				return null;
			}
			final GenModel genModel = GenModel.class.cast(resourceContents);
			GenModelUtil.addDescriptionTags(genModel, DESCRIPTION_PLACEHOLDER);
			for (final Resource r : resourceSet.getResources()) {
				try {
					r.save(null);
				} catch (final IOException ex) {
					Activator.log(MessageFormat.format("Could not save resource with URI {0}.", resource.getURI()), ex); //$NON-NLS-1$
				}
			}
		} catch (final IOException ex) {
			Activator.log("Could not load GenModel", ex); //$NON-NLS-1$
		}
		return null;
	}

}
