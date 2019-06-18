/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * johannes - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.spi.view.migrator;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Content handler for extraction of namespace URIs from a view model using SAX.
 *
 * @since 1.8
 */
public class NameSpaceHandler extends DefaultHandler {

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

	/** @return the namespace URIs. */
	public List<String> getNsURIs() {
		return namespaceURIs;
	}
}