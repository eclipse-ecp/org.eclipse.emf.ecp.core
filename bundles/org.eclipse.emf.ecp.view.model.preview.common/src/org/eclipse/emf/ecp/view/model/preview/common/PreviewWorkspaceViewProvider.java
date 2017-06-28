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
package org.eclipse.emf.ecp.view.model.preview.common;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;
import org.eclipse.emf.ecp.view.spi.provider.IViewProvider;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * The IViewProvider provides views from the workspace.
 *
 * @author Eugen Neufeld
 *
 */
@Component(service = { IViewProvider.class, PreviewWorkspaceViewProvider.class })
public class PreviewWorkspaceViewProvider implements IViewProvider {

	private final Map<IPath, VView> trackedPaths = new LinkedHashMap<IPath, VView>();
	private IResourceChangeListener viewResourceChangeListener;

	/** Constructor. */
	public PreviewWorkspaceViewProvider() {
		addViewResourceChangeListener();
	}

	private void addViewResourceChangeListener() {
		viewResourceChangeListener = new IResourceChangeListener() {
			@Override
			public void resourceChanged(IResourceChangeEvent event) {
				if (trackedPaths.isEmpty()) {
					return;
				}
				IResourceDelta delta = event.getDelta();
				if (delta != null) {
					while (delta.getAffectedChildren().length != 0) {
						delta = delta.getAffectedChildren()[0];
					}
					for (final IPath path : trackedPaths.keySet()) {
						if (delta.getResource().getFullPath().equals(path)) {
							// reload view
							final VView view = loadView(path);
							trackedPaths.put(path, view);
						}
					}
				}
			}
		};
		ResourcesPlugin.getWorkspace().addResourceChangeListener(viewResourceChangeListener);
	}

	/**
	 * Add a new view model path to the list of available views in the preview.
	 *
	 * @param path The {@link IPath} to load
	 */
	public void addViewModel(final IPath path) {
		final VView view = loadView(path);
		trackedPaths.put(path, view);
	}

	private VView loadView(IPath path) {
		final ResourceSet resourceSet = new ResourceSetImpl();
		final Resource resource = resourceSet
			.createResource(URI.createPlatformResourceURI(path.toOSString(), true));
		try {
			resource.load(null);
			final EObject eObject = resource.getContents().get(0);
			if (!VView.class.isInstance(eObject)) {
				throw new IllegalArgumentException(
					"The provided path " + path.toString() + " doesn't contain a VView."); //$NON-NLS-1$//$NON-NLS-2$
			}
			return (VView) eObject;
		} catch (final IOException ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	/**
	 * Remove a view model path from the list of available views in the preview.
	 *
	 * @param path The {@link IPath} to remove
	 */
	public void removeViewModel(IPath path) {
		unloadView(path);
		trackedPaths.remove(path);
	}

	private void unloadView(IPath path) {
		final VView view = trackedPaths.get(path);
		view.eResource().unload();
	}

	@Override
	public double canProvideViewModel(EObject eObject, VViewModelProperties properties) {
		for (final VView view : trackedPaths.values()) {
			if (view.getRootEClass().equals(eObject.eClass())) {
				return 10;
			}
		}
		return NOT_APPLICABLE;
	}

	@Override
	public VView provideViewModel(EObject eObject, VViewModelProperties properties) {
		for (final VView view : trackedPaths.values()) {
			if (view.getRootEClass().equals(eObject.eClass())) {
				return view;
			}
		}
		return null;
	}

	/** Clean the used resources. */
	@Deactivate
	public void dispose() {
		if (viewResourceChangeListener != null) {
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(viewResourceChangeListener);
		}
	}

}
