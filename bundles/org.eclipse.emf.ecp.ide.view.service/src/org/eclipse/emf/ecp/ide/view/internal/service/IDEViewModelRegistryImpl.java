/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.ide.view.internal.service;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.ecp.ide.view.service.IDEViewModelRegistry;
import org.eclipse.emf.ecp.ide.view.service.ViewModelEditorCallback;
import org.eclipse.emf.ecp.view.spi.model.VView;

/**
 * An implementation of the {@link IDEViewModelRegistry}.
 * @author Eugen Neufeld
 *
 */
public class IDEViewModelRegistryImpl implements IDEViewModelRegistry {

	private Map<VView, ViewModelEditorCallback> viewModelViewModelEditorMapping = new LinkedHashMap<>();

	private Map<String, Set<VView>> ecoreViewMapping = new LinkedHashMap<>();

	@Override
	public void register(String ecorePath, VView viewModel) {
		if (!ecoreViewMapping.containsKey(ecorePath)) {
			ecoreViewMapping.put(ecorePath, new LinkedHashSet<VView>());
			addECoreChangeListener(ecorePath);
		}
		ecoreViewMapping.get(ecorePath).add(viewModel);
	}

	private void addECoreChangeListener(final String ecorePath) {
		ResourcesPlugin.getWorkspace().addResourceChangeListener(
				new IResourceChangeListener() {

					@Override
					public void resourceChanged(IResourceChangeEvent event) {
						IResourceDelta delta = event.getDelta();
						while (delta.getAffectedChildren().length != 0) {
							delta = delta.getAffectedChildren()[0];
						}
						if (ecorePath.contains(delta.getResource()
								.getFullPath().toString())) {
							for (VView view : ecoreViewMapping.get(ecorePath)) {
								ViewModelEditorCallback viewModelEditorCallback = viewModelViewModelEditorMapping
										.get(view);
								if (viewModelEditorCallback == null) {
									continue;
								}
								viewModelEditorCallback.reloadViewModel();
							}
						}
					}
				});
	}

	@Override
	public void unregister(String registeredEcorePath, VView viewModel) {
		if (ecoreViewMapping.containsKey(registeredEcorePath)) {
			ecoreViewMapping.get(registeredEcorePath).remove(viewModel);
		}
	}

	@Override
	public void registerViewModelEditor(VView viewModel,
			ViewModelEditorCallback viewModelEditor) {
		viewModelViewModelEditorMapping.put(viewModel, viewModelEditor);
	}

	@Override
	public void unregisterViewModelEditor(VView viewModel,
			ViewModelEditorCallback viewModelEditor) {
		viewModelViewModelEditorMapping.remove(viewModel);
	}

}
