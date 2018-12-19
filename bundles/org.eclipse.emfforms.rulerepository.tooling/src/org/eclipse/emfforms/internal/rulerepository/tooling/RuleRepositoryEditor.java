/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.rulerepository.tooling;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.ide.spi.util.EcoreHelper;
import org.eclipse.emf.ecp.ide.spi.util.ViewModelHelper;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.reporting.StatusReport;
import org.eclipse.emfforms.internal.editor.Activator;
import org.eclipse.emfforms.internal.editor.toolbaractions.LoadEcoreAction;
import org.eclipse.emfforms.spi.editor.GenericEditor;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PartInitException;

/**
 * RuleRepositoryEditor.
 *
 * @author Eugen Neufeld
 *
 */
@SuppressWarnings("restriction")
public class RuleRepositoryEditor extends GenericEditor {

	/**
	 * Returns the toolbar actions for this editor.
	 *
	 * @return A list of actions to show in the Editor's Toolbar
	 */
	@Override
	protected List<Action> getToolbarActions() {
		final List<Action> result = new LinkedList<Action>();

		result.add(new LoadEcoreAction(getResourceSet(), "Load ViewModel")); //$NON-NLS-1$

		// result.addAll(readToolbarActions());
		return result;
	}

	private void registerEcore(ResourceSet resourceSet) throws IOException {
		for (final Resource resource : resourceSet.getResources()) {
			if (resource.getContents().isEmpty()) {
				continue;
			}
			if (!VView.class.isInstance(resource.getContents().get(0))) {
				continue;
			}
			for (final String ecorePath : ViewModelHelper.getEcorePaths(resource)) {
				if (ecorePath == null) {
					return;
				}
				EcoreHelper.registerEcore(ecorePath);
			}
		}
		// resolve all proxies
		EcoreUtil.resolveAll(resourceSet);
	}

	@Override
	protected ResourceSet loadResource(IEditorInput editorInput) {
		try {
			final ResourceSet result = super.loadResource(editorInput);
			registerEcore(result);
			return super.loadResource(editorInput);
		} catch (final IOException | PartInitException ex) {
			Activator.getDefault().getReportService().report(
				new StatusReport(new Status(IStatus.ERROR, Activator.PLUGIN_ID, ex.getMessage(), ex)));
		}
		return null;
	}

	@Override
	public void dispose() {
		for (final Resource resource : getResourceSet().getResources()) {
			if (resource.getContents().isEmpty()) {
				continue;
			}
			if (!VView.class.isInstance(resource.getContents().get(0))) {
				continue;
			}
			for (final String ecorePath : ViewModelHelper.getEcorePaths(resource)) {
				if (ecorePath == null) {
					return;
				}
				EcoreHelper.unregisterEcore(ecorePath);
			}
		}
		super.dispose();
	}
}
