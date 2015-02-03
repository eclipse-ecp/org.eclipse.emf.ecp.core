/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas - initial API and implementation
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.edapt;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecp.view.migrator.ViewModelMigrationException;
import org.eclipse.emf.ecp.view.migrator.ViewModelMigrator;
import org.eclipse.emf.edapt.migration.MigrationException;
import org.eclipse.emf.edapt.migration.execution.Migrator;
import org.eclipse.emf.edapt.migration.execution.MigratorRegistry;
import org.eclipse.emf.edapt.spi.history.Change;
import org.eclipse.emf.edapt.spi.history.History;
import org.eclipse.emf.edapt.spi.history.HistoryFactory;
import org.eclipse.emf.edapt.spi.history.MigrationChange;
import org.eclipse.emf.edapt.spi.history.Release;
import org.eclipse.emf.edapt.spi.migration.MigrationPlugin;
import org.osgi.framework.Bundle;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * A {@link ViewModelMigrator} using edapt.
 *
 * @author Lucas
 * @author jfaltermeier
 *
 */
public class EdaptViewModelMigrator implements ViewModelMigrator {

	private static final String ECORE_NS_URI = "http://www.eclipse.org/emf/2002/Ecore"; //$NON-NLS-1$

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.migrator.ViewModelMigrator#checkMigration(org.eclipse.emf.common.util.URI)
	 */
	@Override
	public boolean checkMigration(final URI resourceURI) {
		// 1. get all namespace uris from the root element of the resource
		final List<String> nsUris = getNamespaceURIs(resourceURI);

		// 2. get the release for every NS that has a migrator registered AND check if every release is the latest one.
		boolean allReleasesAreLatest = true;
		final List<Release> releases = new ArrayList<Release>();
		for (final String nsUri : nsUris) {
			final Migrator migrator = MigratorRegistry.getInstance()
				.getMigrator(nsUri);
			if (migrator == null) {
				// no migrator registered. assume all is fine
				continue;
			}
			final Release nsRelease = getReleaseFromMigrator(nsUri, migrator);
			releases.add(nsRelease);
			if (!migrator.getLatestRelease().equals(nsRelease)) {
				allReleasesAreLatest = false;
				break;
			}
		}
		return allReleasesAreLatest;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.migrator.ViewModelMigrator#performMigration(org.eclipse.emf.common.util.URI)
	 */
	@Override
	public void performMigration(final URI resourceURI) throws ViewModelMigrationException {
		final History history = HistoryFactory.eINSTANCE.createHistory();
		final Release sourceRelease = HistoryFactory.eINSTANCE.createRelease();
		final List<Release> targetReleases = new ArrayList<Release>();
		/*
		 * Analyse the domain model of the view. Generate a history for ecores which have no registered history and add
		 * them to source release. Mapping contains information about which uris from the domain have histories and
		 * which have no history.
		 */
		final NSURIMapping domainMapping = addChangesForDomainModel(resourceURI, sourceRelease);

		/* Get NS URIs with a registered history (domain and view model) */
		final List<String> nsUris = getNamespaceURIsWithHistory(resourceURI, domainMapping);

		/* Combine the changes from the histories in one big history */
		orderHistoriesAndCombineChanges(sourceRelease, targetReleases, nsUris);

		/* add releases to history */
		history.getReleases().add(sourceRelease);
		history.getReleases().addAll(targetReleases);
		history.getReleases().add(HistoryFactory.eINSTANCE.createRelease()); // expected by history editor

		try {
			/* Save history file */
			final URI uri = saveHistoryFile(history);

			/* Trigger the migration */
			final Migrator migrator = new Migrator(uri, new CustomMigrationClassLoader());
			final Release release = migrator.getRelease(0);
			migrator.migrateAndSave(Collections.singletonList(resourceURI), release, null,
				new NullProgressMonitor());

		} catch (final IOException ex) {
			throw new ViewModelMigrationException(ex);
		} catch (final MigrationException ex) {
			throw new ViewModelMigrationException(ex);
		} finally {
			reregisterExtensionMigrators();
		}
	}

	/**
	 * @param history
	 * @return
	 * @throws IOException
	 */
	private URI saveHistoryFile(final History history) throws IOException {
		final File historyFile = File.createTempFile("EMFFormsMigration", ".history"); //$NON-NLS-1$//$NON-NLS-2$
		historyFile.deleteOnExit();
		final URI uri = URI.createFileURI(historyFile.getAbsolutePath());
		final Resource resource = new ResourceSetImpl().createResource(uri);
		resource.getContents().add(history);
		resource.save(null);
		return uri;
	}

	/**
	 * @param history
	 * @param sourceRelease
	 * @param targetReleases
	 * @param nsUris
	 */
	private void orderHistoriesAndCombineChanges(final Release sourceRelease,
		final List<Release> targetReleases, final List<String> nsUris) {
		/* build a dependency tree for all view models with a history */
		final PackageDependencyTree viewModelPackageTree = new PackageDependencyTree();
		final Map<String, Migrator> nsURIToMigratorMap = new LinkedHashMap<String, Migrator>();
		final Map<String, String> latestToCurrentNSURIMap = new LinkedHashMap<String, String>();
		for (final String nsUri : nsUris) {
			final org.eclipse.emf.edapt.migration.execution.Migrator migrator = MigratorRegistry.getInstance()
				.getMigrator(nsUri);
			nsURIToMigratorMap.put(nsUri, migrator);
			/* get available uri from history */
			for (final String migratorNSURI : migrator.getNsURIs()) {
				if (Registry.INSTANCE.containsKey(migratorNSURI)) {
					viewModelPackageTree.addPackage(migratorNSURI);
					if (latestToCurrentNSURIMap.containsKey(migratorNSURI)) {
						final String string = latestToCurrentNSURIMap.get(migratorNSURI);
						final String olderNSURI = string.compareTo(nsUri) < 0 ? string : nsUri;
						latestToCurrentNSURIMap.put(migratorNSURI, olderNSURI);
					} else {
						latestToCurrentNSURIMap.put(migratorNSURI, nsUri);
					}
					break;
				}
			}
		}

		/* using the dependency tree, combine changes from all registered histories based on the release label. */
		final Map<String, List<Change>> sourceReleaseNameToChangesMap = new LinkedHashMap<String, List<Change>>();
		final Map<String, List<Change>> targetReleaseNameToChangesMap = new LinkedHashMap<String, List<Change>>();
		final Iterator<Set<String>> viewModelPackageIterator = viewModelPackageTree.getIerator();
		while (viewModelPackageIterator.hasNext()) {
			final Set<String> latestURIs = viewModelPackageIterator.next();
			for (final String latestURI : latestURIs) {
				if (!latestToCurrentNSURIMap.keySet().contains(latestURI)) {
					continue;
				}
				final String nsUri = latestToCurrentNSURIMap.get(latestURI);
				final org.eclipse.emf.edapt.migration.execution.Migrator migrator = nsURIToMigratorMap
					.get(nsUri);

				final Release nsRelease = getReleaseFromMigrator(nsUri, migrator);

				// add changes to source release
				final int sourceIndex = nsRelease.getNumber();
				for (int i = 0; i <= sourceIndex; i++) {
					final Release currentRelease = migrator.getRelease(i);
					if (!sourceReleaseNameToChangesMap.containsKey(currentRelease.getLabel())) {
						sourceReleaseNameToChangesMap.put(currentRelease.getLabel(), new ArrayList<Change>());
					}
					sourceReleaseNameToChangesMap.get(currentRelease.getLabel()).addAll(currentRelease.getChanges());

				}

				// add changes to target release
				final int targetIndex = migrator.getLatestRelease().getNumber();
				if (targetIndex > sourceIndex) {
					for (int i = sourceIndex + 1; i <= targetIndex; i++) {
						final Release currentRelease = migrator.getRelease(i);
						if (!targetReleaseNameToChangesMap.containsKey(currentRelease.getLabel())) {
							targetReleaseNameToChangesMap.put(currentRelease.getLabel(), new ArrayList<Change>());
						}
						targetReleaseNameToChangesMap.get(currentRelease.getLabel())
							.addAll(currentRelease.getChanges());
					}
				}
			}
		}

		/* Based on combined changes from all histories insert the changed to source release and target releases */
		fillReleases(sourceRelease, targetReleases, sourceReleaseNameToChangesMap, targetReleaseNameToChangesMap);
	}

	/**
	 * @param sourceRelease
	 * @param targetReleases
	 * @param sourceReleaseNameToChangesMap
	 * @param targetReleaseNameToChangesMap
	 */
	private void fillReleases(final Release sourceRelease, final List<Release> targetReleases,
		final Map<String, List<Change>> sourceReleaseNameToChangesMap,
		final Map<String, List<Change>> targetReleaseNameToChangesMap) {
		final List<String> sourceReleaseNames = new ArrayList<String>(sourceReleaseNameToChangesMap.keySet());
		final List<String> targetReleaseNames = new ArrayList<String>(targetReleaseNameToChangesMap.keySet());
		Collections.sort(sourceReleaseNames);
		Collections.sort(targetReleaseNames);

		for (final String release : sourceReleaseNames) {
			final List<Change> list = sourceReleaseNameToChangesMap.get(release);
			sourceRelease.getChanges().addAll(list);
			sourceRelease.setDate(new Date());
			sourceRelease.setLabel("source"); //$NON-NLS-1$
		}

		for (final String release : targetReleaseNames) {
			final Release targetRelease = HistoryFactory.eINSTANCE.createRelease();
			targetRelease.setDate(new Date());
			targetRelease.setLabel(release);
			targetReleases.add(targetRelease);
			final List<Change> list = targetReleaseNameToChangesMap.get(release);
			targetRelease.getChanges().addAll(list);

			// move custom migrations to end of target release, because every custom migration issues a validation.
			// TODO it would be great if we could config edapt to not validate after each custom migration.
			final List<Integer> indexes = new ArrayList<Integer>();
			for (int i = 0; i < targetRelease.getChanges().size(); i++) {
				final Change currentChange = targetRelease.getChanges().get(i);
				if (MigrationChange.class.isInstance(currentChange)) {
					indexes.add(i);
				}
			}
			final int lastIndex = targetRelease.getChanges().size() - 1;
			for (int i = indexes.size() - 1; i > -1; i--) {
				final Change changeToMove = targetRelease.getChanges().get(indexes.get(i));
				targetRelease.getChanges().move(lastIndex, changeToMove);
			}
		}
	}

	/**
	 * @param resourceURI
	 * @param domainMapping
	 * @return
	 */
	private List<String> getNamespaceURIsWithHistory(final URI resourceURI, final NSURIMapping domainMapping) {
		final List<String> nsUris = getNamespaceURIs(resourceURI);
		for (final String nsURI : domainMapping.getNsURIsWithGeneratedHistory()) {
			nsUris.remove(nsURI);
		}
		nsUris.addAll(domainMapping.getNsURIsWithHistory());
		return nsUris;
	}

	/**
	 * Returns the ns uris for which a history was created.
	 *
	 * @param resourceURI
	 * @return
	 */
	private NSURIMapping addChangesForDomainModel(URI resourceURI, Release release) {
		// get rootpackage nsuri from view
		// check if package is available in registry -> generate history
		final Set<String> nsURIsWithHistory = new LinkedHashSet<String>();
		final Set<String> nsURIsWithGeneratedHistory = new LinkedHashSet<String>();

		String rootPackageNsUri = getRootPackageURI(resourceURI);
		final PackageDependencyTree packageTree = new PackageDependencyTree();
		packageTree.addPackage(ECORE_NS_URI);
		final Migrator rootMigrator = MigratorRegistry.getInstance().getMigrator(rootPackageNsUri);
		if (rootMigrator != null) {
			for (final String migratorNSURI : rootMigrator.getNsURIs()) {
				if (Registry.INSTANCE.containsKey(migratorNSURI)) {
					nsURIsWithHistory.add(rootPackageNsUri);
					rootPackageNsUri = migratorNSURI;
					break;
				}
			}
		}
		packageTree.addPackage(rootPackageNsUri);

		final List<EPackage> rootPackages = new ArrayList<EPackage>();
		final Iterator<Set<String>> packageIterator = packageTree.getIerator();

		while (packageIterator.hasNext()) {
			final Set<String> nsURIs = packageIterator.next();
			for (final String nsURI : nsURIs) {
				/* there is no history available from the extensionpoint */
				if (MigratorRegistry.getInstance().getMigrator(nsURI) == null) {
					final EPackage ePackage = Registry.INSTANCE.getEPackage(nsURI);
					rootPackages.add(ePackage);
					nsURIsWithGeneratedHistory.add(nsURI);
				} else {
					nsURIsWithHistory.add(nsURI);
				}
			}
		}

		@SuppressWarnings("restriction")
		final org.eclipse.emf.edapt.history.recorder.HistoryGenerator rootGenerator =
			new org.eclipse.emf.edapt.history.recorder.HistoryGenerator(rootPackages);
		@SuppressWarnings("restriction")
		final List<Change> rootChanges = rootGenerator.generate().getFirstRelease().getChanges();
		release.getChanges().addAll(rootChanges);
		return new NSURIMapping(nsURIsWithHistory, nsURIsWithGeneratedHistory);
	}

	/**
	 * @param nsUri The name space URI of the package whose {@link Release} is wanted.
	 * @param migrator The {@link Migrator} which should provide the {@link Release}.
	 * @return
	 */
	private Release getReleaseFromMigrator(final String nsUri, final Migrator migrator) {
		final Map<String, Set<Release>> releaseMap = migrator.getReleaseMap();
		final Set<Release> nsReleases = releaseMap.get(nsUri);
		// TODO: what happens if there's more than one release per NS although that should not happen?
		final Release nsRelease = nsReleases.iterator().next();
		return nsRelease;
	}

	/**
	 * @return the namespaces of all models used in the given resource.
	 */
	private List<String> getNamespaceURIs(URI resourceURI) {
		@SuppressWarnings("restriction")
		final File file = org.eclipse.emf.edapt.common.URIUtils.getJavaFile(resourceURI);
		// read all namespaces from root element with SAX
		final NameSpaceHandler handler = new NameSpaceHandler();
		executeContentHandler(file, handler);

		return handler.getNsURIs();
	}

	/**
	 * @return the namespaces of all models used in the given resource.
	 */
	private String getRootPackageURI(URI resourceURI) {
		@SuppressWarnings("restriction")
		final File file = org.eclipse.emf.edapt.common.URIUtils.getJavaFile(resourceURI);
		// read root package namespace uri with SAX
		final RootPackageHandler handler = new RootPackageHandler();
		executeContentHandler(file, handler);

		return handler.getRootPackageURI();
	}

	/**
	 * @param file
	 * @param contentHandler
	 */
	private void executeContentHandler(File file, final DefaultHandler contentHandler) {
		FileReader fileReader = null;
		try {
			final XMLReader reader = XMLReaderFactory.createXMLReader();
			reader.setContentHandler(contentHandler);

			fileReader = new FileReader(file);

			reader.parse(new InputSource(fileReader));
		} catch (final SAXException e) {
			// do nothing
		} catch (final IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (fileReader != null) {
					fileReader.close();
				}
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}

	/** Register all migrators from extensions. */
	private void reregisterExtensionMigrators() {
		final IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		final IConfigurationElement[] configurationElements = extensionRegistry
			.getConfigurationElementsFor("org.eclipse.emf.edapt.migrators"); //$NON-NLS-1$

		for (final IConfigurationElement configurationElement : configurationElements) {
			registerExtensionMigrator(configurationElement);
		}
	}

	/** Register migrator for one extension. */
	@SuppressWarnings("restriction")
	private void registerExtensionMigrator(
		IConfigurationElement configurationElement) {

		final String migrationPath = configurationElement.getAttribute("path"); //$NON-NLS-1$

		final IContributor contributor = configurationElement.getContributor();
		final String bundleName = contributor.getName();
		final Bundle bundle = Platform.getBundle(bundleName);
		final URI migratorURI = URI.createPlatformPluginURI("/" + bundleName + "/" //$NON-NLS-1$ //$NON-NLS-2$
			+ migrationPath, true);
		try {
			MigratorRegistry.getInstance().registerMigrator(migratorURI,
				new org.eclipse.emf.edapt.internal.migration.execution.BundleClassLoader(bundle));
		} catch (final MigrationException e) {
			org.eclipse.emf.edapt.common.LoggingUtils.logError(MigrationPlugin.getPlugin(), e);
		}
	}

	/** Content handler for extraction of namespace URIs from a view model using SAX. */
	private static class NameSpaceHandler extends DefaultHandler {

		/** Namespace URIs. */
		private final List<String> namespaceURIs = new ArrayList<String>();

		/**
		 * {@inheritDoc}
		 *
		 * @see org.xml.sax.helpers.DefaultHandler#startPrefixMapping(java.lang.String, java.lang.String)
		 */
		@Override
		public void startPrefixMapping(String prefix, String uri) throws SAXException {
			super.startPrefixMapping(prefix, uri);
			if (!uri.equals(ExtendedMetaData.XMI_URI) && !uri.equals(ExtendedMetaData.XML_SCHEMA_URI)
				&& !uri.equals(ExtendedMetaData.XSI_URI)) {
				namespaceURIs.add(uri);
			}
		}

		/** Returns the namespace URIs. */
		public List<String> getNsURIs() {
			return namespaceURIs;
		}
	}

	/** Content handler for extraction of the root package namespace URI using SAX. */
	private static class RootPackageHandler extends DefaultHandler {

		/** The root package's namespace URI. */
		private String rootPackageURI = ""; //$NON-NLS-1$

		/**
		 * {@inheritDoc}
		 *
		 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String,
		 *      org.xml.sax.Attributes)
		 */
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			super.startElement(uri, localName, qName, attributes);
			if (localName.equals("rootEClass")) { //$NON-NLS-1$
				final String rawUri = attributes.getValue("href"); //$NON-NLS-1$
				rootPackageURI = rawUri.split("#")[0]; //$NON-NLS-1$
				throw new SAXException();
			}
		}

		/** Returns the root package's namespace URI. */
		public String getRootPackageURI() {
			return rootPackageURI;
		}
	}

	/**
	 * Mapping for domain nsURIs.
	 *
	 * @author jfaltermeier
	 *
	 */
	private static class NSURIMapping {
		private final Set<String> nsURIsWithHistory;
		private final Set<String> nsURIsWithGeneratedHistory;

		public NSURIMapping(Set<String> nsURIsWithHistory, Set<String> nsURIsWithGeneratedHistory) {
			this.nsURIsWithHistory = nsURIsWithHistory;
			this.nsURIsWithGeneratedHistory = nsURIsWithGeneratedHistory;
		}

		public Set<String> getNsURIsWithHistory() {
			return nsURIsWithHistory;
		}

		public Set<String> getNsURIsWithGeneratedHistory() {
			return nsURIsWithGeneratedHistory;
		}
	}
}
