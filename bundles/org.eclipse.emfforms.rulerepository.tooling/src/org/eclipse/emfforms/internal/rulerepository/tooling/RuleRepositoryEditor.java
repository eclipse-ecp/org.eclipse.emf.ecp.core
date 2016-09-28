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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.xml.type.AnyType;
import org.eclipse.emf.ecp.internal.ide.util.EcoreHelper;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emfforms.internal.editor.toolbaractions.LoadEcoreAction;
import org.eclipse.emfforms.spi.editor.GenericEditor;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IEditorInput;

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
			final String ecorePath = getEcorePath(resource);
			if (ecorePath == null) {
				return;
			}
			EcoreHelper.registerEcore(ecorePath);
		}
		// resolve all proxies
		EcoreUtil.resolveAll(resourceSet);
	}

	private String getEcorePath(Resource resource) {
		if (resource == null || resource.getContents().isEmpty()) {
			return null;
		}
		final EObject eObject = resource.getContents().get(0);
		if (VView.class.isInstance(eObject)) {
			return VView.class.cast(eObject).getEcorePath();
		}
		if (AnyType.class.isInstance(eObject)) {
			/* view model has older ns uri */
			final FeatureMap anyAttribute = AnyType.class.cast(eObject).getAnyAttribute();
			for (int i = 0; i < anyAttribute.size(); i++) {
				final EStructuralFeature feature = anyAttribute.getEStructuralFeature(i);
				if ("ecorePath".equals(feature.getName())) { //$NON-NLS-1$
					return (String) anyAttribute.getValue(i);
				}
			}
		}
		return null;
	}

	@Override
	protected ResourceSet loadResource(IEditorInput editorInput) {
		final ResourceSet result = super.loadResource(editorInput);
		try {
			registerEcore(result);
		} catch (final IOException ex) {
			ex.printStackTrace();
		}
		return super.loadResource(editorInput);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.editor.GenericEditor#dispose()
	 */
	@Override
	public void dispose() {
		for (final Resource resource : getResourceSet().getResources()) {
			if (resource.getContents().isEmpty()) {
				continue;
			}
			if (!VView.class.isInstance(resource.getContents().get(0))) {
				continue;
			}
			final String ecorePath = getEcorePath(resource);
			if (ecorePath == null) {
				return;
			}
			EcoreHelper.unregisterEcore(ecorePath);
		}
		super.dispose();
	}
}
