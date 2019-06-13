/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.ide.spi.util;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Descriptor;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap.ValueListIterator;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xml.type.AnyType;
import org.eclipse.emf.ecp.ide.internal.Activator;
import org.eclipse.emf.ecp.internal.ide.util.messages.Messages;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;

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
		if (selectedEcore != null
			&& !view.getEcorePaths().contains(selectedEcore.getFullPath().toString())) {
			view.getEcorePaths().add(selectedEcore.getFullPath().toString());
		}

		// Save the contents of the resource to the file system.
		resource.save(Collections.singletonMap(XMLResource.OPTION_ENCODING, "UTF-8")); //$NON-NLS-1$

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
		return new ViewLoader().loadView(file, registeredEcores);
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

	/**
	 * Extract the list of Ecore paths from a view model resource.
	 *
	 * @param resource the resource to extract the paths from
	 * @return list of Ecore paths
	 *
	 * @since 1.17
	 */
	public static List<String> getEcorePaths(Resource resource) {
		if (resource == null || resource.getContents().isEmpty()) {
			return Collections.emptyList();
		}
		final EObject eObject = resource.getContents().get(0);
		if (VView.class.isInstance(eObject)) {
			return VView.class.cast(eObject).getEcorePaths();
		}
		if (AnyType.class.isInstance(eObject)) {
			/* view model has older ns uri */
			// up to 1.16.0
			final FeatureMap anyAttribute = AnyType.class.cast(eObject).getAnyAttribute();
			for (int i = 0; i < anyAttribute.size(); i++) {
				final EStructuralFeature feature = anyAttribute.getEStructuralFeature(i);
				if ("ecorePath".equals(feature.getName())) { //$NON-NLS-1$
					return Arrays.asList(new String[] { (String) anyAttribute.getValue(i) });
				}
			}

			// from 1.17.0
			final FeatureMap any = AnyType.class.cast(eObject).getAny();
			final List<String> ecorePaths = new LinkedList<String>();
			for (int i = 0; i < any.size(); i++) {
				final EStructuralFeature feature = any.getEStructuralFeature(i);
				if ("ecorePaths".equals(feature.getName())) { //$NON-NLS-1$
					final AnyType listType = (AnyType) any.getValue(i);
					final FeatureMap mixed = listType.getMixed();

					// Use iterator to avoid IndexOutOfBounce exceptions for empty ecore paths
					final ValueListIterator<Object> iterator = mixed.valueListIterator();
					if (iterator.hasNext()) {
						ecorePaths.add((String) iterator.next());
					}
				}
			}
			return ecorePaths;
		}
		return Collections.emptyList();
	}

	/**
	 * Helper class for encapsulating view loading functionality.
	 */
	public static class ViewLoader {
		/**
		 * Loads the view denoted by the given file. Also tries to register referenced Ecores.
		 *
		 * @param file the view to load
		 * @param registeredEcores a collection to which all Ecores which are successfully registered are added.
		 * @return the {@link VView} denoted by the given file.
		 * @throws IOException if something goes wrong during loading or registering
		 */
		public VView loadView(IFile file, Collection<String> registeredEcores) throws IOException {
			final String path = getPath(file);
			final VView view = loadView(path);
			registerReferencedEcores(view, path, registeredEcores);
			if (view != null && !viewIsResolved(view)) {
				EcoreUtil.resolveAll(view);
			}
			return view;
		}

		/**
		 * Returns the path string of the given file.
		 *
		 * @param file the {@link IFile} for which the path string shall be determined.
		 * @return The determined string path.
		 */
		protected String getPath(IFile file) {
			return file.getLocation().toString();
		}

		/**
		 * Loads the view denoted by the given path.
		 *
		 * @param path the path denoting the {@link VView}
		 * @return the loaded {@link VView}
		 */
		protected VView loadView(String path) {
			final ResourceSet resourceSet = new ResourceSetImpl();
			final URI fileURI = URI.createFileURI(path);
			final Resource resource = resourceSet.getResource(fileURI, true);
			if (resource != null) {
				return (VView) resource.getContents().get(0);
			}
			return null;
		}

		/**
		 * Registers the referenced Ecores of the given view.
		 *
		 * @param view the {@link VView} which possibly references Ecores.
		 * @param viewLocation the location of the given view. Used for error reporting.
		 * @param registeredEcores a collection to which all Ecores which are successfully registered are added.
		 * @throws IOException if something goes wrong during loading or registering
		 */
		protected void registerReferencedEcores(VView view, String viewLocation, Collection<String> registeredEcores)
			throws IOException {
			if (view == null || view.getEcorePaths() == null) {
				return;
			}
			for (final String ecorePath : view.getEcorePaths()) {
				if (!ecoreExistsInWorkspace(ecorePath)) {
					final String message = MessageFormat.format(Messages.ViewModelHelper_couldNotFindEcorePath_message,
						ecorePath, getViewNameAndLocation(view, viewLocation));
					getReportService()
						.report(new AbstractReport(message, IStatus.WARNING));
					continue;
				}
				registerEcore(ecorePath);
				registeredEcores.add(ecorePath);
			}
		}

		/**
		 * Returns a string representation of the view and its location.
		 *
		 * @param view the {@link VView}.
		 * @param viewLocation the location.
		 * @return a string representation of the view and its location
		 */
		protected String getViewNameAndLocation(VView view, String viewLocation) {
			return getViewName(view)
				.map(viewName -> MessageFormat.format(Messages.ViewModelHelper_couldNotFindEcorePath_nameAndLocation,
					viewName, viewLocation))
				.orElse(viewLocation);
		}

		/**
		 * Determines a name for the given view.
		 *
		 * @param view the [@link VView}.
		 * @return an optional possibly containing a determined name, empty otherwise.
		 */
		protected Optional<String> getViewName(VView view) {
			if (view.getLabel() != null && !view.getLabel().isEmpty()) {
				return Optional.of(view.getLabel());
			}
			if (view.getName() != null && !view.getName().isEmpty()) {
				return Optional.of(view.getName());
			}
			if (view.getRootEClass() != null && view.getRootEClass().getName() != null) {
				return Optional.of(view.getRootEClass().getName());
			}
			return Optional.empty();
		}

		/**
		 * Indicates whether the Ecore denoted by the path exists in the workspace.
		 *
		 * @param ecorePath the potential path to an Ecore
		 * @return {@code true} if an Ecore exists at the path in the workspace, {@code false} otherwise.
		 */
		protected boolean ecoreExistsInWorkspace(String ecorePath) {
			return ResourcesPlugin.getWorkspace().getRoot().findMember(ecorePath) != null;
		}

		/**
		 * Returns the service used for error reporting.
		 *
		 * @return the {@link ReportService}
		 */
		protected ReportService getReportService() {
			return Activator.getDefault().getReportService();
		}

		/**
		 * Try to register the Ecore denoted by the path.
		 *
		 * @param ecorePath the path to the Ecore in the workspace which shall be registered.
		 * @throws IOException if something goes wrong during registering.
		 */
		protected void registerEcore(String ecorePath) throws IOException {
			EcoreHelper.registerEcore(ecorePath);
		}
	}
}
