/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.spi.view.migrator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.ecore.EPackage;

/**
 * Utility class to migrate the view name space uri in a file.
 *
 * @author Lucas Koehler
 * @since 1.17
 */
public final class ViewNsMigrationUtil {

	// Utility class should not be instantiated
	private ViewNsMigrationUtil() {
	}

	private static final Pattern VIEW_NS_URI_PATTERN = Pattern
		.compile("http://org/eclipse/emf/ecp/view/model(/[0-9]+)?"); //$NON-NLS-1$

	/**
	 * If the given file references the view ecore, migrate the view ecore
	 * namespace URI of the template model to the namespace URI of the registered version of the view ecore.
	 * This is necessary for domain model reference selectors to be compatible to the view models of the current
	 * version. Thereby, we assume that view models use the registered version of the view ecore because otherwise they
	 * could not be used in the current runtime environment.
	 * <p>
	 * <strong>Note:</strong> If no migration is necessary, nothing is written to the output writer.
	 *
	 * @param file The {@link File} containing the template model
	 * @return whether the namespace uri was migrated
	 * @throws IOException if something goes wrong reading or writing the template model file
	 */
	public static boolean migrateViewEcoreNsUri(File file) throws IOException {
		return migrateViewEcoreNsUri(file, file);
	}

	/**
	 * Checks whether the view name space uri of the given file needs to be migrated.
	 *
	 * @param file The file to check
	 * @return <code>true</code> if no migration is necessary and <code>false</code> if the name space uri needs
	 *         to be migrated.
	 * @throws IOException if the file cannot be read
	 */
	public static boolean checkMigration(File file) throws IOException {
		final FileReader fileReader = new FileReader(file);
		final String templateModelString = readFile(fileReader);
		fileReader.close();
		final String viewNs = extractViewNsUri(new StringReader(templateModelString));
		final String registeredViewNsUri = getRegisteredViewNsUri();

		final boolean needsMigration = needsMigration(viewNs, registeredViewNsUri);
		return !needsMigration;
	}

	/**
	 * @param viewNs The view package's name space uri of the file
	 * @param registeredViewNsUri The name space uri of the registered view package.
	 * @return Whether a migration is necessary
	 */
	private static boolean needsMigration(final String viewNs, final String registeredViewNsUri) {
		return viewNs != null && registeredViewNsUri != null && !viewNs.equals(registeredViewNsUri);
	}

	/**
	 * Inner method to migrate the view ecore namespace uri that allows using two different files for input and output.
	 * Two different files are mainly useful for testing.
	 *
	 * @param inputFile The {@link File} containing the template model
	 * @param outputFile The {@link File} containing the migrated template model if any migration as necessary
	 * @return whether the namespace uri was migrated
	 * @throws IOException if something goes wrong reading or writing the template model file
	 * @see {@link #migrateViewEcoreNsUri(File)}
	 */
	static boolean migrateViewEcoreNsUri(File inputFile, File outputFile) throws IOException {
		final FileReader fileReader = new FileReader(inputFile);
		final String templateModelString = readFile(fileReader);
		fileReader.close();
		final String viewNs = extractViewNsUri(new StringReader(templateModelString));

		final String registeredViewNsUri = getRegisteredViewNsUri();

		if (needsMigration(viewNs, registeredViewNsUri)) {
			// NS URI migration needed
			final String updatedModel = templateModelString.toString().replaceAll(viewNs, registeredViewNsUri);

			final FileWriter writer = new FileWriter(outputFile, false);
			writer.write(updatedModel);
			writer.close();
			return true;
		}
		return false;
	}

	/**
	 * @return The whole file read into one String.
	 */
	private static String readFile(Reader stringReader) throws IOException {
		final StringBuilder builder = new StringBuilder();
		final BufferedReader bufferedReader = new BufferedReader(stringReader);
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			builder.append(line);
			builder.append('\n');
		}
		bufferedReader.close();
		return builder.toString();
	}

	/**
	 * @return The namespace URI of the registered view ecore, or <code>null</code> if it is not registered
	 */
	private static String getRegisteredViewNsUri() {
		String registeredViewNsUri = null;
		for (final String packageNsUri : EPackage.Registry.INSTANCE.keySet()) {
			final Matcher matcher = VIEW_NS_URI_PATTERN.matcher(packageNsUri);
			if (matcher.matches()) {
				registeredViewNsUri = packageNsUri;
				break;
			}
		}
		return registeredViewNsUri;
	}

	/**
	 * @return The view ecore's namespace uri used in the template model, or <code>null</code> if the template model
	 *         does not reference the view ecore.
	 */
	private static String extractViewNsUri(final Reader reader) throws IOException {
		final NameSpaceHandler handler = new NameSpaceHandler();
		SAXUtil.executeContentHandler(reader, handler);
		reader.close();
		final List<String> nsURIs = handler.getNsURIs();
		String viewNs = null;
		for (final String nsUri : nsURIs) {
			final Matcher matcher = VIEW_NS_URI_PATTERN.matcher(nsUri);
			if (matcher.matches()) {
				viewNs = nsUri;
				break;
			}
		}
		return viewNs;
	}
}
