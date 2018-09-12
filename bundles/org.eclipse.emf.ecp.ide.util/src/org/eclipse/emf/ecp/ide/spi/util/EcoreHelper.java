/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 * Johannes Faltermeier - refactorings
 ******************************************************************************/
package org.eclipse.emf.ecp.ide.spi.util;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecp.ide.internal.Activator;
import org.eclipse.emf.ecp.internal.ide.util.messages.Messages;

/**
 * Helper methods for dealing with ecores.
 *
 * @author Alexandra Buzila
 * @since 1.13
 *
 */
public final class EcoreHelper {

	/**
	 * ResourceSet which overrides the getResource and double checks for registry.
	 *
	 * @author Eugen Neufeld
	 *
	 */
	private static final class EMFFormsResourceSetImpl extends ResourceSetImpl {
		private final String ecorePath;

		/**
		 * @param ecorePath
		 */
		private EMFFormsResourceSetImpl(String ecorePath) {
			this.ecorePath = ecorePath;
		}

		// BEGIN COMPLEX CODE
		// COPIED FROM SUPERCLASS
		@Override
		public Resource getResource(URI uri, boolean loadOnDemand) {
			// BEGIN CHANGES (en)
			if (uri.isPlatform()) {
				if (!WORKSPACEURI_REFERENCEDBY.containsKey(uri.toString())) {
					WORKSPACEURI_REFERENCEDBY.put(uri.toString(), new LinkedHashSet<String>());
				}
				WORKSPACEURI_REFERENCEDBY.get(uri.toString()).add(ECOREPATH_TO_WORKSPACEURI.get(ecorePath));
			}
			// END CHANGES
			if (resourceLocator != null) {
				return resourceLocator.getResource(uri, loadOnDemand);
			}

			final Map<URI, Resource> map = getURIResourceMap();
			if (map != null) {
				final Resource resource = map.get(uri);
				if (resource != null) {
					if (loadOnDemand && !resource.isLoaded()) {
						demandLoadHelper(resource);
					}
					return resource;
				}
			}

			final URIConverter theURIConverter = getURIConverter();
			final URI normalizedURI = theURIConverter.normalize(uri);
			for (final Resource resource : getResources()) {
				if (theURIConverter.normalize(resource.getURI()).equals(normalizedURI)) {
					if (loadOnDemand && !resource.isLoaded()) {
						demandLoadHelper(resource);
					}

					if (map != null) {
						map.put(uri, resource);
					}
					return resource;
				}
			}

			final Resource delegatedResource = delegatedGetResource(uri, loadOnDemand);
			if (delegatedResource != null) {
				if (map != null) {
					map.put(uri, delegatedResource);
				}
				return delegatedResource;
			}

			if (loadOnDemand) {
				final Resource resource = demandCreateResource(uri);
				if (resource == null) {
					throw new RuntimeException(
						"Cannot create a resource for '" + uri + "'; a registered resource factory is needed"); //$NON-NLS-1$//$NON-NLS-2$
				}

				demandLoadHelper(resource);
				// BEGIN CHANGES (en)
				// custom recheck whether resource is known in the registry
				// and if so use it instead of a custom loaded one
				final Resource delegatedGetResource = delegatedGetResource(
					URI.createURI(((EPackage) resource.getContents().get(0)).getNsURI()), loadOnDemand);
				final Resource resultResource = delegatedGetResource != null ? delegatedGetResource : resource;
				if (delegatedGetResource != null) {
					getResources().remove(resource);
				}
				if (map != null) {
					map.put(uri, resultResource);
				}
				return resultResource;
				// END CHANGES
			}

			return null;
		}
		// END COMPLEX CODE
	}

	/**
	 * Contains mapping between an ecore path and the corresponding platform resource URI.
	 */
	private static final Map<String, String> ECOREPATH_TO_WORKSPACEURI = new HashMap<String, String>();

	/**
	 * Contains mapping between an platform resource URI and the URIs which reference it.
	 */
	private static final Map<String, Set<String>> WORKSPACEURI_REFERENCEDBY = new HashMap<String, Set<String>>();
	/**
	 * URIs which are still in use although it was requested to be removed.
	 */
	private static final Set<String> URIS_HOLD = new LinkedHashSet<String>();
	/**
	 * A set of all namespace uris that were registerd by the tooling.
	 */
	private static final Set<String> ALL_NSURIS_REGISTERED_BY_TOOLING = new HashSet<String>();

	/** Local EPackage Registry. */
	private static final Map<String, EPackage> WORKSPACEURI_TO_REGISTEREDPACKAGE = new LinkedHashMap<String, EPackage>();

	private EcoreHelper() {
	}

	/**
	 * Put an ecore's {@link EPackage} into the {@link org.eclipse.emf.ecore.EPackage.Registry}. Subsequently, register
	 * all referenced ecores.
	 *
	 * @param ecorePath - path to the ecore
	 * @throws IOException if resource cannot be loaded
	 *
	 */
	public static void registerEcore(final String ecorePath) throws IOException {
		if (ecorePath == null) {
			return;
		}

		// actually load the ecore reusing already loaded packages from the workspace
		final ResourceSet physicalResourceSet = new EMFFormsResourceSetImpl(ecorePath);
		initResourceSet(physicalResourceSet, true);
		final URI uri = URI.createPlatformResourceURI(ecorePath, false);
		ECOREPATH_TO_WORKSPACEURI.put(ecorePath, uri.toString());
		final Resource r = physicalResourceSet.createResource(uri);

		loadResource(ecorePath, r);
		// resolve the proxies
		EcoreUtil.resolveAll(physicalResourceSet);
		// remove self reference
		if (WORKSPACEURI_REFERENCEDBY.containsKey(uri.toString())) {
			WORKSPACEURI_REFERENCEDBY.get(uri.toString()).remove(uri.toString());
		}
		convertRsToVirtual(ecorePath, physicalResourceSet);

	}

	private static void convertRsToVirtual(String ecorePath, final ResourceSet physicalResourceSet) {
		final ResourceSetImpl virtualResourceSet = new ResourceSetImpl();
		for (final Resource physicalResource : physicalResourceSet.getResources()) {

			final EPackage ePackage = getEPackage(physicalResource);
			if (ePackage == null) {
				continue;
			}

			if (isContainedInPackageRegistry(ePackage.getNsURI())) {
				if (!ALL_NSURIS_REGISTERED_BY_TOOLING.contains(ePackage.getNsURI())) {
					Activator.log(
						IStatus.INFO,
						String.format(
							"Tooling Registered Packages don't contain package with URI %1$s.", ePackage.getNsURI())); //$NON-NLS-1$
					registerSubpackages(ePackage);
					continue;
				}
				final EPackage registeredPackage = EPackage.Registry.INSTANCE.getEPackage(ePackage.getNsURI());
				if (EcoreUtil.equals(ePackage, registeredPackage)) {
					Activator.log(IStatus.INFO,
						String.format("Another package with same URI is already registered: %1$s.", registeredPackage)); //$NON-NLS-1$
					registerSubpackages(ePackage);
					continue;
				}
			}
			updateRegistryAndLocalCache(ePackage, physicalResource, virtualResourceSet);
		}
	}

	/**
	 * Wraps loading a {@link Resource} in order to catch thrown IOExceptions and rethrow them with more informative
	 * messages.
	 *
	 * @param ecorePath The path of the resource. Needed for informative messages
	 * @param resource The {@link Resource} to load
	 * @throws IOException if the loading of the Resource fails
	 */
	protected static void loadResource(String ecorePath, final Resource resource) throws IOException {
		try {
			resource.load(null);
		} catch (final IOException e) {
			if (e.getMessage().contains("does not exist")) { //$NON-NLS-1$
				throw new IOException(MessageFormat.format(
					Messages.EcoreHelper_invalidEcorePath,
					ecorePath), e.getCause());
			}
			throw new IOException(
				MessageFormat.format(Messages.EcoreHelper_invalidEcore,
					ecorePath, e.getMessage()),
				e.getCause());
		}
	}

	/**
	 * Returns the first {@link EPackage} contained in the resource, as returned by {@link Resource#getContents()}.
	 *
	 * @param resource the container resource
	 * @return the first {@link EPackage} contained in the resource or <code>null</code> if no EPackage can be found
	 */
	private static EPackage getEPackage(Resource resource) {
		for (final EObject eObject : resource.getContents()) {
			if (eObject instanceof EPackage) {
				return (EPackage) eObject;
			}
		}
		return null;
	}

	private static void updateRegistryAndLocalCache(EPackage ePackage,
		Resource oldResource, ResourceSet newResourceSet) {
		if (ePackage.getNsURI() == null || ePackage.getNsURI().isEmpty()) {
			Activator.log(IStatus.WARNING,
				String.format("Package does not have a valid Ns URI [%1$s].", ePackage)); //$NON-NLS-1$
			return;
		}
		final String platformResourceURI = oldResource.getURI().toString();
		oldResource.getContents().remove(ePackage);
		final Resource virtualResource = newResourceSet.createResource(URI.createURI(ePackage.getNsURI()));
		virtualResource.getContents().add(ePackage);
		EPackage.Registry.INSTANCE.put(ePackage.getNsURI(), ePackage);
		ALL_NSURIS_REGISTERED_BY_TOOLING.add(ePackage.getNsURI());
		Activator.log(IStatus.INFO, String.format("Registered Package with URI %1$s.", ePackage.getNsURI())); //$NON-NLS-1$
		WORKSPACEURI_TO_REGISTEREDPACKAGE.put(platformResourceURI, ePackage);
		Activator.log(IStatus.INFO,
			String.format("Mapped Package with URI %1$s to %2$s.", ePackage.getNsURI(), platformResourceURI)); //$NON-NLS-1$
		registerSubpackages(ePackage);
	}

	/**
	 * @param ePackage - the EPackage whose subpackages need to be registered
	 */
	private static void registerSubpackages(EPackage ePackage) {
		for (final EPackage subpackage : ePackage.getESubpackages()) {
			if (EPackage.Registry.INSTANCE.containsKey(subpackage.getNsURI())) {
				continue;
			}
			EPackage.Registry.INSTANCE.put(subpackage.getNsURI(), subpackage);
			Activator.log(IStatus.INFO,
				String.format("Register subpackage %1$s of package %2$s.", subpackage.getNsURI(), ePackage.getNsURI())); //$NON-NLS-1$
			registerSubpackages(subpackage);
		}
	}

	/**
	 * <p>
	 * Returns the path for all ecores for which
	 * <p>
	 * <p>
	 * a) the given ecore is dependent on.
	 * </p>
	 * <p>
	 * b) the uri is a platform resource URI, meaning the ecore is available in the workspace.
	 * </p>
	 *
	 * @param ecorePath the path
	 * @return the ecore nsuris
	 */
	public static Set<String> getOtherRelatedWorkspacePaths(String ecorePath) {
		final Set<String> result = new LinkedHashSet<String>();
		if (ecorePath == null) {
			return result;
		}
		try {
			final ResourceSet physicalResourceSet = new ResourceSetImpl();
			initResourceSet(physicalResourceSet, false);
			final URI uri = URI.createPlatformResourceURI(ecorePath, false);
			final Resource tempResource = physicalResourceSet.createResource(uri);
			tempResource.load(null);
			// resolve the proxies
			EcoreUtil.resolveAll(physicalResourceSet);
			for (final Resource physicalResource : physicalResourceSet.getResources()) {
				if (physicalResource.getContents().size() == 0) {
					continue;
				}
				if (!physicalResource.getURI().isPlatformResource()) {
					continue;
				}
				result.add(physicalResource.getURI().toString());
			}
		} catch (final IOException ex) {
			Activator.log(IStatus.INFO,
				String.format("Error while loading %1$s.", ecorePath)); //$NON-NLS-1$
		}
		return result;
	}

	private static boolean isContainedInPackageRegistry(String nsURI) {
		final Registry instance = EPackage.Registry.INSTANCE;
		return instance.containsKey(nsURI);
	}

	/**
	 * Remove the ecore's {@link EPackage} from the {@link org.eclipse.emf.ecore.EPackage.Registry}.
	 * It also removes the packages of referenced ecores.
	 *
	 * @param ecorePath - the path of the ecore to be removed.
	 *
	 */
	public static void unregisterEcore(String ecorePath) {
		if (ecorePath == null || !ECOREPATH_TO_WORKSPACEURI.containsKey(ecorePath)) {
			return;
		}
		final String uriToUnregister = ECOREPATH_TO_WORKSPACEURI.remove(ecorePath);

		final Set<String> referencedBy = WORKSPACEURI_REFERENCEDBY.get(uriToUnregister);
		URIS_HOLD.add(uriToUnregister);
		if (ECOREPATH_TO_WORKSPACEURI.size() == 0) {
			URIS_HOLD.addAll(WORKSPACEURI_REFERENCEDBY.keySet());
		}

		final Set<String> workspaceURIsToRemove = new LinkedHashSet<String>();
		boolean addedToDelete = false;
		if (referencedBy == null || referencedBy.size() == 0) {
			workspaceURIsToRemove.add(uriToUnregister);
			addedToDelete = true;
		} else if (URIS_HOLD.containsAll(referencedBy)) {
			for (final String refed : referencedBy) {
				final Set<String> toDeleteRefedBy = WORKSPACEURI_REFERENCEDBY.get(refed);
				if (URIS_HOLD.containsAll(toDeleteRefedBy)) {
					workspaceURIsToRemove.add(refed);
					addedToDelete = true;
				}
			}
			if (workspaceURIsToRemove.containsAll(referencedBy)) {
				workspaceURIsToRemove.add(uriToUnregister);
				addedToDelete = true;
			}
		}
		if (addedToDelete) {
			cleanAndRecheckRemovedURIs(workspaceURIsToRemove);
		}

		unregisterEcore(workspaceURIsToRemove);
	}

	private static void cleanAndRecheckRemovedURIs(final Set<String> workspaceURIsToRemove) {
		boolean addedToDelete = true;
		while (addedToDelete) {
			addedToDelete = false;
			for (final String toDelete : workspaceURIsToRemove) {
				URIS_HOLD.remove(toDelete);
				WORKSPACEURI_REFERENCEDBY.remove(toDelete);
				for (final String key : WORKSPACEURI_REFERENCEDBY.keySet()) {
					WORKSPACEURI_REFERENCEDBY.get(key).remove(toDelete);
				}
			}
			// recheck URIs_HOLD
			for (final String uri : URIS_HOLD) {
				final Set<String> toDeleteRefedBy = WORKSPACEURI_REFERENCEDBY.get(uri);
				if (toDeleteRefedBy == null || toDeleteRefedBy.size() == 0) {
					workspaceURIsToRemove.add(uri);
					addedToDelete = true;
				}
			}
		}
	}

	/**
	 * Remove the ecore's {@link EPackage} from the {@link org.eclipse.emf.ecore.EPackage.Registry}.
	 * It also removes the packages of referenced ecores (if needed).
	 *
	 */
	private static void unregisterEcore(Set<String> workspaceURIsToRemove) {
		// unregister no longer needed workspace URIs
		for (final String toRemove : workspaceURIsToRemove) {
			final EPackage pkgToRemove = WORKSPACEURI_TO_REGISTEREDPACKAGE.remove(toRemove);
			if (pkgToRemove == null) {
				continue;
			}
			EPackage.Registry.INSTANCE.remove(pkgToRemove.getNsURI());
			ALL_NSURIS_REGISTERED_BY_TOOLING.remove(pkgToRemove.getNsURI());
			Activator.log(IStatus.INFO,
				String.format("Unregister package %1$s.", pkgToRemove.getNsURI())); //$NON-NLS-1$
			unregisterSubpackages(pkgToRemove);
		}
	}

	/**
	 * @param ePackage - the EPackage whose subpackages need to be unregistered
	 */
	private static void unregisterSubpackages(EPackage ePackage) {
		for (final EPackage subpackage : ePackage.getESubpackages()) {
			EPackage.Registry.INSTANCE.remove(subpackage.getNsURI());
			Activator.log(IStatus.INFO,
				String
					.format("Unregister subpackage %1$s of package %2$s.", subpackage.getNsURI(), ePackage.getNsURI())); //$NON-NLS-1$
			unregisterSubpackages(subpackage);
		}
	}

	/**
	 * @return the EPackages which are registered in the EPackage registry by default (without the ones registered
	 *         during runtime by the tooling).
	 */
	public static Object[] getDefaultPackageRegistryContents() {

		final Set<String> packages = new HashSet<String>();
		packages.addAll(EPackage.Registry.INSTANCE.keySet());
		packages.removeAll(ALL_NSURIS_REGISTERED_BY_TOOLING);
		return packages.toArray();
	}

	/*
	 * If the Ecore.ecore is referenced with a resource uri in an ecore-file, the ecore editor is able to load this file
	 * even if the Ecore.ecore is not available in the workspace. Therefore those ecores should be regarded as valid.
	 * Hence we need to remap the resource uri to a plugin uri if the Ecore.ecore is not available in the workspace
	 * in order to resolve the proxy.
	 * Moreover when a new ecore is loaded we need to prevent EMF to resolve proxies to other platform-resource URIs
	 * with the package registry
	 */
	private static void initResourceSet(final ResourceSet resourceSet, boolean withLocalRegistry) {
		if (withLocalRegistry) {
			resourceSet.getPackageRegistry().putAll(WORKSPACEURI_TO_REGISTEREDPACKAGE);
			Activator.log(IStatus.INFO, "Added map of platformuri to epackage to resourceset package registry."); //$NON-NLS-1$
		}

		// needed to be able to resolve resource paths to plugin paths and thus load referenced ecores
		resourceSet.getURIConverter().getURIMap().putAll(EcorePlugin.computePlatformURIMap(true));

		resourceSet.getLoadOptions().put(XMLResource.OPTION_DEFER_IDREF_RESOLUTION, Boolean.TRUE);
	}

}
