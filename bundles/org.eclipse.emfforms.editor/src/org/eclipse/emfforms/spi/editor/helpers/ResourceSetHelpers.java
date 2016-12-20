/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Clemens Elflein - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.editor.helpers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecp.view.model.common.edit.provider.CustomReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.ecp.view.spi.model.reporting.StatusReport;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emfforms.internal.editor.Activator;

/**
 * The Class ResourceSetHelpers.
 * It is a utility class to perform operations on ResourceSet objects.
 */
public final class ResourceSetHelpers {

	private ResourceSetHelpers() {
	}

	/**
	 * Save all changes in a ResourceSet.
	 *
	 * @param resourceSet the resource set
	 * @return true, if successful
	 */
	public static boolean save(ResourceSet resourceSet) {
		try {
			for (final Resource resource : resourceSet.getResources()) {
				resource.save(null);
			}
			return true;
		} catch (final IOException ex) {
			Activator.getDefault().getReportService().report(
				new StatusReport(new Status(IStatus.ERROR, Activator.PLUGIN_ID, ex.getMessage(), ex)));
		}
		return false;
	}

	/**
	 * Load resource set with proxies.
	 *
	 * @param resourceURI the resource uri (= File to load)
	 * @param commandStack the command stack
	 * @return the resource set
	 */
	public static ResourceSet loadResourceSetWithProxies(URI resourceURI,
		BasicCommandStack commandStack) {
		// Create a ResourceSet and add the requested Resource
		final ResourceSet resourceSet = createResourceSet(commandStack);
		return loadResourceWithProxies(resourceURI, resourceSet);
	}

	/**
	 * Load resource set with proxies.
	 *
	 * @param resourceURI the resource uri (= File to load)
	 * @param resourceSet the resource set
	 * @return the resource set
	 * @since 1.10
	 */
	public static ResourceSet loadResourceWithProxies(URI resourceURI, final ResourceSet resourceSet) {
		if (addResourceToSet(resourceSet, resourceURI)) {
			return resourceSet;
		}
		return null;
	}

	/**
	 * Creates a ResourceSet with a CommandStack attached to it.
	 *
	 * @param commandStack the command stack
	 * @return the resource set
	 * @since 1.10
	 */
	public static ResourceSet createResourceSet(CommandStack commandStack) {
		final AdapterFactoryEditingDomain domain = new AdapterFactoryEditingDomain(
			new ComposedAdapterFactory(
				new AdapterFactory[] {
					new CustomReflectiveItemProviderAdapterFactory(),
					new ComposedAdapterFactory(
						ComposedAdapterFactory.Descriptor.Registry.INSTANCE) }),
			commandStack);
		return createResourceSet(domain);
	}

	/**
	 * Creates a ResourceSet with the given EditingDomain.
	 *
	 * @param domain the editing domain
	 * @return the resource set
	 * @since 1.11
	 */
	public static ResourceSet createResourceSet(final AdapterFactoryEditingDomain domain) {
		final ResourceSet resourceSet = domain.getResourceSet();
		resourceSet.eAdapters().add(
			new AdapterFactoryEditingDomain.EditingDomainProvider(domain));
		return resourceSet;
	}

	/**
	 * Loads a resource from resourceURI and adds it to the resourceSet.
	 *
	 * @param resourceSet the resource set
	 * @param resourceURI the resource uri
	 * @return true, if successful
	 */
	public static boolean addResourceToSet(ResourceSet resourceSet,
		URI resourceURI) {

		final Map<Object, Object> loadOptions = new HashMap<Object, Object>();
		loadOptions.put(XMLResource.OPTION_RECORD_UNKNOWN_FEATURE,
			Boolean.TRUE);

		return addResourceToSet(resourceSet, resourceURI, loadOptions);
	}

	/**
	 * Loads a resource from resourceURI and adds it to the resourceSet.
	 *
	 * @param resourceSet the resource set
	 * @param resourceURI the resource uri
	 * @param loadOptions the resource load options
	 * @return true, if successful
	 * @since 1.10
	 */
	public static boolean addResourceToSet(ResourceSet resourceSet, URI resourceURI,
		final Map<Object, Object> loadOptions) {
		try {
			resourceSet.createResource(resourceURI).load(loadOptions);

			// resolve all proxies
			int rsSize = resourceSet.getResources().size();

			EcoreUtil.resolveAll(resourceSet);
			while (rsSize != resourceSet.getResources().size()) {
				EcoreUtil.resolveAll(resourceSet);
				rsSize = resourceSet.getResources().size();
			}
			return true;
		} catch (final IOException ex) {
			Activator.getDefault().getReportService().report(
				new StatusReport(new Status(IStatus.ERROR, Activator.PLUGIN_ID, ex.getMessage(), ex)));
		}
		return false;
	}

	/**
	 * Find all of type in resource set.
	 *
	 * @param object the object
	 * @param type the type
	 * @param includeEcorePackage the include ecore package
	 * @return the list
	 */
	public static List<?> findAllOfTypeInResourceSet(EObject object,
		EClassifier type, boolean includeEcorePackage) {
		return ResourceSetHelpers.findAllOf(
			object.eResource().getResourceSet(), type.getInstanceClass(),
			includeEcorePackage);
	}

	/**
	 * Find all of type in resource set.
	 *
	 * @param <T> the generic type
	 * @param object the object
	 * @param clazz the clazz
	 * @param includeEcorePackage the include ecore package
	 * @return the list
	 */
	public static <T> List<T> findAllOfTypeInResourceSet(EObject object,
		Class<T> clazz, boolean includeEcorePackage) {
		return ResourceSetHelpers
			.findAllOf(object.eResource().getResourceSet(), clazz,
				includeEcorePackage);
	}

	/**
	 * Find all of type in the ResourceSet.
	 *
	 * @param <T> the generic type
	 * @param resourceSet the resource set
	 * @param clazz the clazz
	 * @param includeEcorePackage the include ecore package
	 * @return the list
	 */
	public static <T> List<T> findAllOf(ResourceSet resourceSet,
		Class<T> clazz, boolean includeEcorePackage) {
		final List<T> result = new ArrayList<T>();

		// Iterate through all EObjects in every Resource in the set and return
		// all Objects of Class clazz.
		if (resourceSet != null) {
			for (final Resource resource : resourceSet.getResources()) {
				final TreeIterator<EObject> objectIterator = resource
					.getAllContents();
				while (objectIterator.hasNext()) {
					final EObject o = objectIterator.next();
					if (o != null && clazz.isInstance(o)) {
						result.add(clazz.cast(o));
					}
				}
			}
		}

		if (includeEcorePackage) {
			// Find all EDatatypes in the ECore-Package.
			final TreeIterator<EObject> objectIterator = EcorePackage.eINSTANCE
				.eAllContents();
			while (objectIterator.hasNext()) {
				final EObject o = objectIterator.next();
				if (o != null && clazz.isInstance(o)) {
					result.add(clazz.cast(o));
				}
			}
		}

		return result;
	}
}
