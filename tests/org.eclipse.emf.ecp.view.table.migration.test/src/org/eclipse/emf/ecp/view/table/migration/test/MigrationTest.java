package org.eclipse.emf.ecp.view.table.migration.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertNotNull;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edapt.history.Change;
import org.eclipse.emf.edapt.history.Release;
import org.eclipse.emf.edapt.history.recorder.HistoryGenerator;
import org.eclipse.emf.edapt.migration.MigrationException;
import org.eclipse.emf.edapt.migration.execution.Migrator;
import org.eclipse.emf.edapt.migration.execution.MigratorRegistry;
import org.junit.Test;

public class MigrationTest {

	private static final String base = "oldView";
	private static final String oldView = base + "oldView.view";
	private static final String migratedView = base
			+ System.currentTimeMillis() + "mig.view";


	@Test
	public void migrate() throws MigrationException, IOException {
		checkMigration(URI.createFileURI(oldView));
		
	}

	private void checkMigration(final URI resourceURI)
			throws MigrationException, IOException {
		final Migrator migrator = MigratorRegistry.getInstance().getMigrator(
				"http://org/eclipse/emf/ecp/view/table/model");
		assertNotNull(migrator);
		addHistoryForEcore(migrator);
		final Release release = migrator.getRelease(0);
		if (!release.isLatestRelease()) {
			performMigration(migrator, resourceURI, release);
		}
	}

	private void addHistoryForEcore(Migrator migrator) {
		EPackage ePackage = Registry.INSTANCE.getEPackage("http://org/eclipse/example/bowling");
		List<EPackage> packages = new ArrayList<EPackage>();
		packages.add(ePackage);
		HistoryGenerator generator = new HistoryGenerator(packages);
		List<Change> changes = generator.generate().getFirstRelease()
				.getChanges();
		Release release = migrator.getRelease(0);
		release.getChanges().addAll(changes);
	}

	private void performMigration(final Migrator migrator,
			final URI resourceURI, final Release release)
			throws MigrationException, IOException {
		ResourceSet resourceSet = migrator.migrateAndLoad(
				Collections.singletonList(resourceURI), release, null,
				new NullProgressMonitor());
		Resource resource = resourceSet.getResource(resourceURI, true);
		EList<EObject> contents = resource.getContents();

		// ResourceSet resourceSet2 = new ResourceSetImpl();
		Resource resource2 = resourceSet.createResource(URI
				.createFileURI(migratedView));
		resource2.getContents().addAll(contents);
		resource2.save(null);
	}

}
