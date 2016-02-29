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
package org.eclipse.emf.ecp.spi.view.migrator.string;

import java.io.StringReader;
import java.util.List;

import org.eclipse.emf.ecp.spi.view.migrator.NameSpaceHandler;
import org.eclipse.emf.ecp.spi.view.migrator.SAXUtil;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Util methods related to string view model migration.
 *
 * @author Johannes Faltermeier
 * @since 1.8
 *
 */
public final class StringViewModelMigratorUtil {

	private StringViewModelMigratorUtil() {
	}

	/**
	 * Parses the given view model string and return the used namespace uris.
	 * 
	 * @param serializedViewModel the view model as a string
	 * @return the uris
	 */
	public static List<String> getNamespaceURIs(String serializedViewModel) {
		final NameSpaceHandler handler = new NameSpaceHandler();
		executeContentHandler(serializedViewModel, handler);
		return handler.getNsURIs();
	}

	private static void executeContentHandler(String serializedViewModel, final DefaultHandler contentHandler) {
		SAXUtil.executeContentHandler(new StringReader(serializedViewModel), contentHandler);
	}

}
