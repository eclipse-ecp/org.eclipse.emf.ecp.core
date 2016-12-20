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

/**
 * The IViewProvider provides views from the workspace.
 *
 * @author Eugen Neufeld
 *
 */
@Component(service = { IViewProvider.class, PreviewWorkspaceViewProvider.class })
public class PreviewWorkspaceViewProvider implements IViewProvider {

	private final Map<IPath, VView> trackedPaths = new LinkedHashMap<IPath, VView>();

	/**
	 * Add a new view model path to the list of available views in the preview.
	 *
	 * @param path The {@link IPath} to load
	 */
	public void addViewModel(IPath path) {
		trackedPaths.put(path, loadView(path));
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

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.provider.IViewProvider#canProvideViewModel(org.eclipse.emf.ecore.EObject,
	 *      org.eclipse.emf.ecp.view.spi.model.VViewModelProperties)
	 */
	public double canProvideViewModel(EObject eObject, VViewModelProperties properties) {
		for (final VView view : trackedPaths.values()) {
			if (view.getRootEClass().equals(eObject.eClass())) {
				return 10;
			}
		}
		return NOT_APPLICABLE;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.provider.IViewProvider#provideViewModel(org.eclipse.emf.ecore.EObject,
	 *      org.eclipse.emf.ecp.view.spi.model.VViewModelProperties)
	 */
	public VView provideViewModel(EObject eObject, VViewModelProperties properties) {
		for (final VView view : trackedPaths.values()) {
			if (view.getRootEClass().equals(eObject.eClass())) {
				return view;
			}
		}
		return null;
	}

}
