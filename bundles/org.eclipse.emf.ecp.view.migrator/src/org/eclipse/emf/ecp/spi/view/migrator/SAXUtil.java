/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.spi.view.migrator;

import java.io.IOException;
import java.io.Reader;

import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * Util class for SAX related methods.
 *
 * @author Johannes Faltermeier
 * @since 1.8
 *
 */
public final class SAXUtil {

	private SAXUtil() {
	}

	/**
	 * Creates an {@link XMLReader} based on the given model-reader and sets the given content handler.
	 *
	 * @param modelReader the input
	 * @param contentHandler the {@link ContentHandler}
	 */
	public static void executeContentHandler(Reader modelReader, final ContentHandler contentHandler) {
		try {
			final XMLReader xmlReader = XMLReaderFactory.createXMLReader();
			xmlReader.setContentHandler(contentHandler);
			xmlReader.parse(new InputSource(modelReader));
		} catch (final SAXException e) {
			// do nothing. we use this as a break during parsing
		} catch (final IOException ex) {
			log(ex);
		} finally {
			try {
				if (modelReader != null) {
					modelReader.close();
				}
			} catch (final IOException e) {
				log(e);
			}
		}
	}

	private static void log(Throwable ex) {
		final Bundle bundle = FrameworkUtil.getBundle(SAXUtil.class);
		if (bundle == null) {
			return;
		}
		final BundleContext bundleContext = bundle.getBundleContext();
		final ServiceReference<ReportService> serviceReference = bundleContext.getServiceReference(ReportService.class);
		if (serviceReference == null) {
			return;
		}
		final ReportService reportService = bundleContext.getService(serviceReference);
		reportService.report(new AbstractReport(ex));
		bundleContext.ungetService(serviceReference);
	}
}
