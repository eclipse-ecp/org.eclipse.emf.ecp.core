/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Stefan Dirix - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.emf2web.ui.json.internal.handler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.emf2web.controller.GenerationController;
import org.eclipse.emf.ecp.emf2web.exporter.FileGenerationExporter;
import org.eclipse.emf.ecp.emf2web.exporter.GenerationExporter;
import org.eclipse.emf.ecp.emf2web.json.controller.JsonGenerationController;
import org.eclipse.emf.ecp.emf2web.ui.handler.AbstractSchemaExportCommandHandler;
import org.eclipse.emf.ecp.emf2web.ui.json.Activator;
import org.eclipse.emf.ecp.emf2web.ui.json.internal.messages.Messages;
import org.eclipse.emf.ecp.internal.ide.util.EcoreHelper;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * The handler responsible for exporting view models to JSONForms.
 */
@SuppressWarnings("restriction")
public class ExportJSONFormsHandler extends AbstractSchemaExportCommandHandler {

	private final List<String> registeredEcores = new LinkedList<String>();

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		registeredEcores.clear();
		super.execute(event);
		for (final String registeredEcore : registeredEcores) {
			EcoreHelper.unregisterEcore(registeredEcore);
		}
		return null;
	}

	@Override
	protected Collection<VView> getViews(ExecutionEvent event) {
		final IStructuredSelection selection = (IStructuredSelection) HandlerUtil
			.getCurrentSelection(event);

		final List<VView> views = new LinkedList<VView>();
		final List<IFile> failedFiles = new LinkedList<IFile>();

		@SuppressWarnings("unchecked")
		final Iterator<Object> it = selection.iterator();
		while (it.hasNext()) {
			final Object selectedObject = it.next();
			if (selectedObject instanceof IFile) {
				final IFile file = (IFile) selectedObject;
				final String path = file.getLocation().toString();
				VView view = loadView(path);
				if (!viewIsResolved(view)) {
					try {
						view = tryResolve(view, path, registeredEcores);
					} catch (final IOException e) {
						Activator.logException(e);
					}
					if (!viewIsResolved(view)) {
						failedFiles.add(file);
						continue;
					}
				}
				views.add(view);
			}
		}

		if (!failedFiles.isEmpty()) {
			showErrorMessage(failedFiles, views.isEmpty());
			if (views.isEmpty()) {
				return null;
			}
		}

		return views;
	}

	private boolean viewIsResolved(VView view) {
		return !view.getRootEClass().eIsProxy();
	}

	private VView tryResolve(VView view, String path, Collection<String> registeredEcores) throws IOException {
		EcoreUtil.resolveAll(view);
		if (viewIsResolved(view)) {
			return view;
		}

		if (view.getEcorePath() == null
			|| ResourcesPlugin.getWorkspace().getRoot().findMember(view.getEcorePath()) == null) {
			throw new FileNotFoundException(path);
		}

		EcoreHelper.registerEcore(view.getEcorePath());
		registeredEcores.add(view.getEcorePath());
		final VView reloadView = loadView(path);
		if (!viewIsResolved(reloadView)) {
			EcoreUtil.resolveAll(reloadView);
		}
		return reloadView;
	}

	private VView loadView(String path) {
		final ResourceSet resourceSet = new ResourceSetImpl();
		final URI fileURI = URI.createFileURI(path);
		final Resource resource = resourceSet.getResource(fileURI, true);
		if (resource != null) {
			return (VView) resource.getContents().get(0);
		}
		return null;
	}

	private void showErrorMessage(Collection<IFile> files, boolean allFailed) {
		final Iterator<IFile> viewIt = files.iterator();
		String viewNames = viewIt.next().getName();
		while (viewIt.hasNext()) {
			viewNames += ", " + viewIt.next().getName(); //$NON-NLS-1$
		}

		String message = Messages.ExportJSONFormsHandler_ViewsNotResolved + viewNames;
		if (allFailed) {
			message += "\n" + Messages.ExportJSONFormsHandler_ExportCanceled; //$NON-NLS-1$
		} else {
			message += "\n" + Messages.ExportJSONFormsHandler_ViewsSkipped; //$NON-NLS-1$
		}

		MessageDialog.openError(Display.getDefault().getActiveShell(),
			Messages.ExportJSONFormsHandler_ErrorResolvedViews, message);
	}

	@Override
	protected GenerationController getGenerationController() {
		return new JsonGenerationController();
	}

	@Override
	protected GenerationExporter getGenerationExporter() {
		return new FileGenerationExporter();
	}

}
