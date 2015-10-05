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
package org.eclipse.emfforms.internal.spreadsheet.core.converter;

import java.io.IOException;
import java.io.StringWriter;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter.ReadableInputStream;
import org.eclipse.emf.ecore.resource.URIConverter.WriteableOutputStream;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

/**
 * Helper class for transforming EObjects to a string and vice versa.
 *
 * @author jfaltermeier
 *
 */
public final class XMIStringConverterHelper {

	private XMIStringConverterHelper() {

	}

	/**
	 * Uses EMF serialization to transform the object to a string.
	 *
	 * @param eObject the object
	 * @return the string
	 * @throws IOException in case of an error during save
	 */
	public static String getSerializedEObject(EObject eObject) throws IOException {
		final ResourceSet rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl()); //$NON-NLS-1$
		final Resource resource = rs.createResource(URI.createURI("VIRTAUAL_URI")); //$NON-NLS-1$
		resource.getContents().add(EcoreUtil.copy(eObject));

		final StringWriter sw = new StringWriter();
		final WriteableOutputStream os = new WriteableOutputStream(sw, "UTF-8"); //$NON-NLS-1$

		resource.save(os, null);
		final String value = sw.getBuffer().toString();
		return value;
	}

	/**
	 * Gets an EObject from an XMI string.
	 *
	 * @param object the XMI string
	 * @return the object
	 * @throws IOException in case the object could not be deserialized
	 */
	public static EObject deserializeObject(String object) throws IOException {
		final ResourceSet rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl()); //$NON-NLS-1$
		final Resource resource = rs.createResource(URI.createURI("VIRTAUAL_URI")); //$NON-NLS-1$

		final ReadableInputStream is = new ReadableInputStream(object, "UTF-8"); //$NON-NLS-1$
		resource.load(is, null);
		return resource.getContents().get(0);
	}

}
