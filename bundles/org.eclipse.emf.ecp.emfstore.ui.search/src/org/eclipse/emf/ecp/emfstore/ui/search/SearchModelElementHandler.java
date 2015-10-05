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
 *
 *******************************************************************************/

package org.eclipse.emf.ecp.emfstore.ui.search;

import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.emfstore.core.internal.EMFStoreProvider;
import org.eclipse.emf.ecp.spi.core.InternalProject;
import org.eclipse.emf.ecp.spi.core.InternalProvider;
import org.eclipse.emf.ecp.spi.ui.util.ECPHandlerHelper;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 *
 * @author Eugen Neufeld
 *         This class shows the search model element dialog for emfstore projects
 */
public class SearchModelElementHandler extends AbstractHandler {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(event);
		final InternalProject project = (InternalProject) ECPUtil.getECPProjectManager().getProject(
			selection.getFirstElement());
		if (project == null) {
			MessageDialog.openInformation(HandlerUtil.getActiveShell(event),
				Messages.SearchModelElementHandler_Information,
				Messages.SearchModelElementHandler_YouMustSelectProject);
			return null;
		}
		InternalProvider internalProvider = project.getProvider();
		if (internalProvider == null) {
			return null;
		}
		internalProvider = (InternalProvider) ECPUtil.getResolvedElement(internalProvider);
		if (!EMFStoreProvider.NAME.equals(internalProvider.getName())) {
			return null;
		}
		final ESLocalProject projectSpace = ((EMFStoreProvider) internalProvider).getProjectSpace(project);

		if (projectSpace == null) {
			return null;
		}

		final Set<EObject> eObjects = projectSpace.getAllModelElements();

		final ComposedAdapterFactory composedAdapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new ReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		final AdapterFactoryLabelProvider adapterFactoryLabelProvider = new AdapterFactoryLabelProvider(
			composedAdapterFactory);
		final ElementListSelectionDialog dialog = new ElementListSelectionDialog(HandlerUtil.getActiveShell(event),
			adapterFactoryLabelProvider);
		dialog.setElements(eObjects.toArray());
		dialog.setMultipleSelection(false);
		dialog.setMessage(Messages.SearchModelElementHandler_EnterModelName);
		dialog.setTitle(Messages.SearchModelElementHandler_SearchModelElement);
		if (dialog.open() == Window.OK) {
			final Object[] selections = dialog.getResult();

			if (selections != null && selections.length == 1 && selections[0] instanceof EObject) {
				ECPHandlerHelper.openModelElement(selections[0],
					project);
			}
		}
		adapterFactoryLabelProvider.dispose();
		composedAdapterFactory.dispose();

		return null;
	}

}
