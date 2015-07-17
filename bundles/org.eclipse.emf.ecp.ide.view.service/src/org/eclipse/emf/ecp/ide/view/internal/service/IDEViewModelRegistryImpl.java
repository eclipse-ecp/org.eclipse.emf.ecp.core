/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
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

import java.io.IOException;
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
import org.eclipse.emf.ecp.internal.ide.util.EcoreHelper;
import org.eclipse.emf.ecp.view.spi.model.VView;

/**
 * An implementation of the {@link IDEViewModelRegistry}.
 *
 * @author Eugen Neufeld
 * @author Alexandra Buzila
 */
public class IDEViewModelRegistryImpl implements IDEViewModelRegistry {

	private final Map<VView, ViewModelEditorCallback> viewModelViewModelEditorMapping = new LinkedHashMap<VView, ViewModelEditorCallback>();

	private final Map<String, Set<VView>> ecoreViewMapping = new LinkedHashMap<String, Set<VView>>();

	private final Map<VView, String> viewModelviewModelFileMapping = new LinkedHashMap<VView, String>();

	private final Map<String, IResourceChangeListener> resourceChangeListeners = new LinkedHashMap<String, IResourceChangeListener>();

	@Override
	public void register(String ecorePath, VView viewModel) {
		if (!ecoreViewMapping.containsKey(ecorePath) || ecoreViewMapping.get(ecorePath).isEmpty()) {
			if (ecoreViewMapping.get(ecorePath) == null) {
				ecoreViewMapping.put(ecorePath, new LinkedHashSet<VView>());
			}
			if (!resourceChangeListeners.containsKey(ecorePath)) {
				addECoreChangeListener(ecorePath);
			}
		}
		ecoreViewMapping.get(ecorePath).add(viewModel);
	}

	private void addECoreChangeListener(final String ecorePath) {
		final IResourceChangeListener listener = new IResourceChangeListener() {
			@Override
			public void resourceChanged(IResourceChangeEvent event) {
				IResourceDelta delta = event.getDelta();
				if (delta != null) {
					while (delta.getAffectedChildren().length != 0) {
						delta = delta.getAffectedChildren()[0];
					}
					for (final VView view : ecoreViewMapping.get(ecorePath)) {
						final String ecorePath = view.getEcorePath();
						if (ecorePath == null) {
							return;
						}
						if (delta.getResource().getFullPath().toString().contains(ecorePath)) {
							final ViewModelEditorCallback viewModelEditorCallback = viewModelViewModelEditorMapping
								.get(view);
							if (viewModelEditorCallback == null) {
								continue;
							}
							viewModelEditorCallback.signalEcoreOutOfSync();
						}
					}
				}
			}
		};
		ResourcesPlugin.getWorkspace().addResourceChangeListener(listener);
		resourceChangeListeners.put(ecorePath, listener);
	}

	@Override
	public void unregister(String registeredEcorePath, VView viewModel) {
		if (ecoreViewMapping.containsKey(registeredEcorePath)) {
			ecoreViewMapping.get(registeredEcorePath).remove(viewModel);
			if (ecoreViewMapping.get(registeredEcorePath).size() == 0)
			{
				final IResourceChangeListener listener = resourceChangeListeners.get(registeredEcorePath);
				if (listener != null) {
					ResourcesPlugin.getWorkspace().removeResourceChangeListener(listener);
					resourceChangeListeners.remove(registeredEcorePath);
				}
			}
		}
	}

	@Override
	public void registerViewModelEditor(VView viewModel, ViewModelEditorCallback viewModelEditor) throws IOException {

		viewModelViewModelEditorMapping.put(viewModel, viewModelEditor);
		// final String ecorePath = viewModel.getEcorePath();
		// if (ecorePath == null) {
		// return;
		// }
		// EcoreHelper.registerEcore(ecorePath);

	}

	@Override
	public void unregisterViewModelEditor(VView viewModel, ViewModelEditorCallback viewModelEditor) {
		viewModelViewModelEditorMapping.remove(viewModel);
		final String ecorePath = viewModel.getEcorePath();
		if (ecorePath != null) {
			EcoreHelper.unregisterEcore(ecorePath);
		}
	}

	@Override
	public void registerViewModel(VView view, String path) {
		if (!viewModelviewModelFileMapping.containsKey(view)) {
			viewModelviewModelFileMapping.put(view, path);
		}
	}
}
