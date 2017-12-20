/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.ide.spi.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Descriptor;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;

/**
 * Helper class for view model objects.
 *
 * @author Alexandra Buzila
 *
 * @since 1.13
 */
public final class ViewModelHelper {

	private ViewModelHelper() {
	}

	/**
	 * Creates a new view model file.
	 *
	 * @param modelFile the file in which the view should be saved
	 * @param selectedEClass the <em>Root EClass</em> for the new {@link VView}
	 * @param selectedEcore the ecore containing the <em>selectedEClass</em>. If the <em>selectedEcore</em> is null,
	 *            then the <em>selectedEClass</em> must come from an EPackage which is registered by default in
	 *            the
	 *            package registry.
	 *
	 * @return the newly created {@link VView}
	 *
	 * @throws IOException when something goes wrong while loading or saving the resource
	 *
	 */
	public static VView createViewModel(IFile modelFile, EClass selectedEClass, IFile selectedEcore)
		throws IOException {

		AdapterFactory adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		adapterFactory = new ComposedAdapterFactory(new AdapterFactory[] { adapterFactory,
			new ReflectiveItemProviderAdapterFactory() });
		final AdapterFactoryEditingDomain domain = new AdapterFactoryEditingDomain(adapterFactory,
			new BasicCommandStack());

		// create resource for the view
		final URI fileURI = URI.createPlatformResourceURI(modelFile.getFullPath().toString(), true);
		final Resource resource = domain.createResource(fileURI.toString());

		// Add the initial model object to the contents.
		final VView view = VViewPackage.eINSTANCE.getViewFactory().createView();
		if (view == null) {
			return null;
		}
		resource.getContents().add(view);

		// Add the selected EClass as the VView's RootEClass
		//
		// get the EClass from the registry, to ensure it has the correct href
		final EPackage ePackage = selectedEClass.getEPackage();

		final Registry instance = org.eclipse.emf.ecore.EPackage.Registry.INSTANCE;
		final Object ePackageObject = instance.get(ePackage.getNsURI());
		EPackage ep;
		if (EPackage.Descriptor.class.isInstance(ePackageObject)) {
			final Descriptor descriptor = EPackage.Descriptor.class.cast(ePackageObject);
			ep = descriptor.getEPackage();
		} else if (EPackage.class.isInstance(ePackageObject)) {
			ep = (EPackage) ePackageObject;
		} else {
			ep = null;
		}
		if (ep == null && selectedEcore != null) {
			EcoreHelper.registerEcore(selectedEcore.getFullPath().toString());
			ep = (EPackage) instance.get(ePackage.getNsURI());
		}

		final EClass ec = (EClass) ep.getEClassifier(selectedEClass.getName());

		view.setRootEClass(ec);
		view.setName(selectedEClass.getName());
		// Update the VView-EClass mapping
		if (selectedEcore != null) {
			view.setEcorePath(selectedEcore.getFullPath().toString());
		}

		// Save the contents of the resource to the file system.
		final Map<Object, Object> options = new HashMap<Object, Object>();
		resource.save(options);

		return view;
	}

	/**
	 * Tries to load a view from the given file.
	 *
	 * @param file the {@link IFile} that contains the view model to be loaded
	 * @param registeredEcores a {@link Collection} that will contain the paths of all
	 *            Ecores that are necessary to load the view. call
	 * @return the {@link VView}. Note that view resolution may fail, so callers should check
	 *         whether the view has been resolved successfully
	 * @throws IOException in case an error occurs while loading the view
	 */
	public static VView loadView(IFile file, Collection<String> registeredEcores) throws IOException {
		final String path = file.getLocation().toString();
		final VView view = loadView(path);
		if (view != null && !viewIsResolved(view)) {
			return tryResolve(view, path, registeredEcores);
		}

		return view;
	}

	private static VView tryResolve(VView view, String path, Collection<String> registeredEcores) throws IOException {
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
		if (reloadView != null && !viewIsResolved(reloadView)) {
			EcoreUtil.resolveAll(reloadView);
		}
		return reloadView;
	}

	/**
	 * Check whether the given view has been resolved, i.e. whether it is a proxy or not
	 *
	 * @param view the {@link VView} to be checked
	 * @return {@code true}, if the view is not a proxy, {@code false} otherwise
	 */
	public static boolean viewIsResolved(VView view) {
		return !view.getRootEClass().eIsProxy();
	}

	private static VView loadView(String path) {
		final ResourceSet resourceSet = new ResourceSetImpl();
		final URI fileURI = URI.createFileURI(path);
		final Resource resource = resourceSet.getResource(fileURI, true);
		if (resource != null) {
			return (VView) resource.getContents().get(0);
		}
		return null;
	}
}
