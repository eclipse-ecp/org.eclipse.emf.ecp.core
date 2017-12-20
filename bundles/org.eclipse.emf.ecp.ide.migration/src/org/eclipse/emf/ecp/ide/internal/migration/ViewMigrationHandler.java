/*******************************************************************************
 * Copyright (c) 2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.ide.internal.migration;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecp.ide.spi.util.EcoreHelper;
import org.eclipse.emf.ecp.ide.spi.util.ViewModelHelper;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;
import org.eclipse.emf.ecp.view.spi.model.util.ViewModelPropertiesHelper;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.common.internal.validation.ValidationServiceImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Executes a simple XPath-based transformation for migrating the namespace
 * fragments of a given view model file.
 *
 */
public class ViewMigrationHandler {

	private static ExecutorService executorService = Executors.newFixedThreadPool(1);

	private final String oldNamespaceFragment;
	private final String newNamespaceFragment;

	/**
	 * Default constructor.
	 *
	 * @param oldNamespaceFragment the value of the namespace fragment to be replaced
	 * @param newNamespaceFragment the new namespace fragment to replace the old with
	 */
	public ViewMigrationHandler(String oldNamespaceFragment, String newNamespaceFragment) {
		this.oldNamespaceFragment = oldNamespaceFragment;
		this.newNamespaceFragment = newNamespaceFragment;
	}

	/**
	 * Execute the migration for all given files.
	 *
	 * @param files the set of files to be migrated
	 * @param monitor a {@link SubMonitor} that allows for reporting progress
	 * @return a map of file names containing the view models to be migrated
	 *         to the respective {@link Diagnostic}s which have been produced
	 *         while loading the views
	 *
	 * @throws ViewMigrationException in case the migration of the view fails
	 */
	public Map<String, Optional<Diagnostic>> execute(Set<IFile> files, SubMonitor monitor)
		throws ViewMigrationException {
		final SubMonitor subMonitor = SubMonitor.convert(monitor, files.size());
		final Map<String, Optional<Diagnostic>> diagnostics = new LinkedHashMap<String, Optional<Diagnostic>>();

		for (final IFile file : files) {
			try {
				final Optional<Diagnostic> diagnostic = execute(file);
				diagnostics.put(file.getName(), diagnostic);
				// BEGIN SUPRESS CATCH EXCEPTION
			} catch (final Exception throwable) {
				Activator.log(throwable);
				// END SUPRESS CATCH EXCEPTION
				continue;
			}
			subMonitor.worked(1);
		}

		return diagnostics;
	}

	/**
	 * Execute the migration for a single file and validate it.
	 *
	 * @param file the file to be migrated
	 * @return the validation result after the file has been migrated, or {@link Optional#empty}
	 *         if the view could not be resolved
	 *
	 * @throws ViewMigrationException in case the migration of the view fails
	 */
	public Optional<Diagnostic> execute(final IFile file) throws ViewMigrationException {

		try {
			file.refreshLocal(0, new NullProgressMonitor());
			final TransformerFactory transformerFactory = TransformerFactory.newInstance();
			final Transformer transformer = transformerFactory.newTransformer();
			final DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			final DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			final Document doc = docBuilder.parse(file.getContents());

			final XPathFactory xPathfactory = XPathFactory.newInstance();
			final XPath xpath = xPathfactory.newXPath();

			// select all elements with a href attribute
			final XPathExpression expr = xpath.compile("//*[@href]"); //$NON-NLS-1$
			final NodeList elements = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

			for (int i = 0; i < elements.getLength(); i++) {
				final Node item = elements.item(i);
				final Node href = item.getAttributes().getNamedItem("href"); //$NON-NLS-1$
				href.setNodeValue(href.getNodeValue().replace(oldNamespaceFragment, newNamespaceFragment));
			}

			final DOMSource source = new DOMSource(doc);

			final PipedInputStream pis = new PipedInputStream();
			final PipedOutputStream pos = new PipedOutputStream();
			pos.connect(pis);
			final StreamResult result = new StreamResult(pos);
			final Future<Void> future = executorService.submit(new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					try {
						file.setContents(pis, true, true, new NullProgressMonitor());
					} finally {
						pis.close();
					}
					return null;
				}
			});
			try {
				transformer.transform(source, result);
			} finally {
				pos.close();
			}

			// block per file, could be optimized
			future.get();

			return checkView(file);
		} catch (final SAXException ex) {
			throw new ViewMigrationException(ex);
		} catch (final TransformerConfigurationException ex) {
			throw new ViewMigrationException(ex);
		} catch (final ParserConfigurationException ex) {
			throw new ViewMigrationException(ex);
		} catch (final IOException ex) {
			throw new ViewMigrationException(ex);
		} catch (final XPathExpressionException ex) {
			throw new ViewMigrationException(ex);
		} catch (final TransformerException ex) {
			throw new ViewMigrationException(ex);
		} catch (final CoreException ex) {
			throw new ViewMigrationException(ex);
		} catch (final InterruptedException ex) {
			throw new ViewMigrationException(ex);
		} catch (final ExecutionException ex) {
			throw new ViewMigrationException(ex);
		}
	}

	private Optional<Diagnostic> checkView(IFile file) throws IOException {
		final LinkedHashSet<String> ecores = new LinkedHashSet<String>();
		final VView view = ViewModelHelper.loadView(file, ecores);
		try {
			if (view != null) {
				final VViewModelProperties properties = ViewModelPropertiesHelper.getInhertitedPropertiesOrEmpty(view);
				view.setLoadingProperties(properties);
				final ValidationServiceImpl validationService = new ValidationServiceImpl();
				return Optional.of(validationService.validate(view));
			}
			return Optional.empty();
		} finally {
			for (final String registeredEcore : ecores) {
				EcoreHelper.unregisterEcore(registeredEcore);
			}
		}
	}
}
