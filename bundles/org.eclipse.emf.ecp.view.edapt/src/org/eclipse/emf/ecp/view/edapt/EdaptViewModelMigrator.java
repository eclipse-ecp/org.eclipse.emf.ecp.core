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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
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
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecp.spi.view.migrator.NameSpaceHandler;
import org.eclipse.emf.ecp.spi.view.migrator.SAXUtil;
import org.eclipse.emf.ecp.spi.view.migrator.string.StringViewModelMigrator;
import org.eclipse.emf.ecp.spi.view.migrator.string.StringViewModelMigratorUtil;
import org.eclipse.emf.ecp.view.migrator.ViewModelMigrationException;
import org.eclipse.emf.ecp.view.migrator.ViewModelMigrator;
import org.eclipse.emf.ecp.view.spi.model.util.VViewResourceFactoryImpl;
import org.eclipse.emf.edapt.common.IResourceSetFactory;
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
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A {@link ViewModelMigrator} using edapt.
 *
 * @author Lucas
 * @author jfaltermeier
 *
 */
public class EdaptViewModelMigrator implements ViewModelMigrator, StringViewModelMigrator {

	private static final String ECORE_NS_URI = "http://www.eclipse.org/emf/2002/Ecore"; //$NON-NLS-1$

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.spi.view.migrator.string.StringViewModelMigrator#checkMigration(java.lang.String)
	 * @since 1.8
	 */
	@Override
	public boolean checkMigration(String serializedViewModel) {
		return checkMigration(StringViewModelMigratorUtil.getNamespaceURIs(serializedViewModel));
	}

	@Override
	public boolean checkMigration(final URI resourceURI) {
		return checkMigration(getNamespaceURIs(resourceURI));
	}

	private boolean checkMigration(final List<String> nsUris) {
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
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.spi.view.migrator.string.StringViewModelMigrator#performMigration(java.lang.String)
	 * @since 1.8
	 */
	@Override
	public String performMigration(String serializedViewModel) throws ViewModelMigrationException {
		PrintWriter printWriter = null;
		File tempViewModelFile = null;
		Scanner scanner = null;
		try {
			tempViewModelFile = File.createTempFile("view", ".view"); //$NON-NLS-1$//$NON-NLS-2$
			tempViewModelFile.deleteOnExit();
			printWriter = new PrintWriter(tempViewModelFile);
			printWriter.print(serializedViewModel);
			printWriter.flush();
			printWriter.close();
			performMigration(URI.createFileURI(tempViewModelFile.getAbsolutePath()));
			scanner = new Scanner(tempViewModelFile, "UTF-8"); //$NON-NLS-1$
			return scanner.useDelimiter("\\A").next(); //$NON-NLS-1$
		} catch (final IOException ex) {
			throw new ViewModelMigrationException(ex);
		} finally {
			if (printWriter != null) {
				printWriter.close();
			}
			if (scanner != null) {
				scanner.close();
			}
			if (tempViewModelFile != null) {
				tempViewModelFile.delete();
			}
		}
	}

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
			migrator.setResourceSetFactory(new IResourceSetFactory() {
				@Override
				public ResourceSet createResourceSet() {
					final ResourceSetImpl resourceSet = new ResourceSetImpl();
					resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
						Resource.Factory.Registry.DEFAULT_EXTENSION, new VViewResourceFactoryImpl());
					return resourceSet;
				}
			});
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
		// historyFile.deleteOnExit();
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
		final PackageDependencyGraph viewModelPackageGraph = new PackageDependencyGraph();
		final Map<String, Migrator> nsURIToMigratorMap = new LinkedHashMap<String, Migrator>();
		final Map<String, String> latestToCurrentNSURIMap = new LinkedHashMap<String, String>();
		for (final String nsUri : nsUris) {
			final org.eclipse.emf.edapt.migration.execution.Migrator migrator = MigratorRegistry.getInstance()
				.getMigrator(nsUri);
			nsURIToMigratorMap.put(nsUri, migrator);
			/* get available uri from history */
			for (final String migratorNSURI : migrator.getNsURIs()) {
				if (Registry.INSTANCE.containsKey(migratorNSURI)) {
					viewModelPackageGraph.addPackage(migratorNSURI);
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
		final Iterator<Set<String>> viewModelPackageIterator = viewModelPackageGraph.getIerator();
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

		// FIXME remove hack for multi segment history
		// final Migrator multiSegmentMigrator = MigratorRegistry.getInstance()
		// .getMigrator("http://org/eclipse/emfforms/view/multisegment/model/200"); //$NON-NLS-1$
		// final Release multiSegmentRelease = multiSegmentMigrator.getRelease(0);
		// targetReleaseNameToChangesMap.get(multiSegmentRelease.getLabel());
		// if (targetReleaseNameToChangesMap.get(multiSegmentRelease.getLabel()) == null) {
		// targetReleaseNameToChangesMap.put(multiSegmentRelease.getLabel(), new ArrayList<Change>());
		// }
		// targetReleaseNameToChangesMap.get(multiSegmentRelease.getLabel()).addAll(multiSegmentRelease.getChanges());

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
		// FIXME hack
		nsUris.add("http://org/eclipse/emfforms/view/multisegment/model/1110"); //$NON-NLS-1$
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

		final PackageDependencyGraph domainModelPackageGraph = new PackageDependencyGraph();
		domainModelPackageGraph.addPackage(ECORE_NS_URI);
		final Set<String> rootPackageURIs = getRootPackageURI(resourceURI);
		for (String rootPackageNsUri : rootPackageURIs) {
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
			domainModelPackageGraph.addPackage(rootPackageNsUri);
		}

		final List<EPackage> rootPackages = new ArrayList<EPackage>();
		final Iterator<Set<String>> packageIterator = domainModelPackageGraph.getIerator();

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
		final org.eclipse.emf.edapt.history.recorder.HistoryGenerator rootGenerator = new org.eclipse.emf.edapt.history.recorder.HistoryGenerator(
			rootPackages);
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
		final File file = org.eclipse.emf.edapt.internal.common.URIUtils.getJavaFile(resourceURI);
		// read all namespaces from root element with SAX
		final NameSpaceHandler handler = new NameSpaceHandler();
		executeContentHandler(file, handler);

		return handler.getNsURIs();
	}

	/**
	 * @return the namespaces of all models used in the given resource.
	 */
	private Set<String> getRootPackageURI(URI resourceURI) {
		@SuppressWarnings("restriction")
		final File file = org.eclipse.emf.edapt.internal.common.URIUtils.getJavaFile(resourceURI);
		// read root package namespace uri with SAX
		final RootPackageHandler handler = new RootPackageHandler();
		executeContentHandler(file, handler);
		if (handler.foundRootEClass()) {
			return Collections.singleton(handler.getRootPackageURI());
		}
		final RootPackageCalculationHandler calcHandler = new RootPackageCalculationHandler();
		executeContentHandler(file, calcHandler);
		return calcHandler.getUsedPackages();
	}

	private static void executeContentHandler(File file, final DefaultHandler contentHandler) {
		try {
			SAXUtil.executeContentHandler(new FileReader(file), contentHandler);
		} catch (final FileNotFoundException ex) {
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
				new org.eclipse.emf.edapt.internal.migration.execution.internal.BundleClassLoader(bundle));
		} catch (final MigrationException e) {
			org.eclipse.emf.edapt.internal.common.LoggingUtils.logError(MigrationPlugin.getPlugin(), e);
		}
	}

	/** Content handler for extraction of the root package namespace URI using SAX. */
	private static class RootPackageHandler extends DefaultHandler {

		/** The root package's namespace URI. */
		private String rootPackageURI = ""; //$NON-NLS-1$
		private boolean rootEClassFound;

		/**
		 * {@inheritDoc}
		 *
		 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String,
		 *      org.xml.sax.Attributes)
		 */
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes)
			throws SAXException {
			super.startElement(uri, localName, qName, attributes);
			if (localName.equals("rootEClass")) { //$NON-NLS-1$
				final String rawUri = attributes.getValue("href"); //$NON-NLS-1$
				rootPackageURI = rawUri.split("#")[0]; //$NON-NLS-1$
				rootEClassFound = true;
				throw new SAXException();
			}
		}

		/**
		 * Whether an element with the rootEClass attribute has been found.
		 *
		 * @return <code>true</code> if found, <code>false</code> otherwise
		 */
		public boolean foundRootEClass() {
			return rootEClassFound;
		}

		/** Returns the root package's namespace URI. */
		public String getRootPackageURI() {
			return rootPackageURI;
		}
	}

	/**
	 * Content handler for finding all used NS-URIs in a view model.
	 *
	 * @author jfaltermeier
	 *
	 */
	private static class RootPackageCalculationHandler extends DefaultHandler {

		/** The root package's namespace URI. */
		private final Set<String> packages = new LinkedHashSet<String>();

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes)
			throws SAXException {
			super.startElement(uri, localName, qName, attributes);
			final String href = attributes.getValue("href"); //$NON-NLS-1$
			final String type = attributes.getValue("xsi:type"); //$NON-NLS-1$
			if (href == null || type == null) {
				return;
			}
			if (!type.endsWith("EAttribute") && !type.endsWith("EReference")) { //$NON-NLS-1$ //$NON-NLS-2$
				return;
			}
			packages.add(href.split("#")[0]); //$NON-NLS-1$
		}

		/** Returns the root package's namespace URI. */
		public Set<String> getUsedPackages() {
			return packages;
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

		/**
		 * Default constructor.
		 *
		 * @param nsURIsWithHistory ns uris of models with a history available at the extension point
		 * @param nsURIsWithGeneratedHistory ns uris of models for which we generated a history
		 */
		NSURIMapping(Set<String> nsURIsWithHistory, Set<String> nsURIsWithGeneratedHistory) {
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
