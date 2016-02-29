/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * mborkowski - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.editor.genmodel.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecp.view.spi.model.reporting.StatusReport;
import org.eclipse.emfforms.internal.editor.genmodel.Activator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * This class is a utility class internally used for modifying plugin.xml files.
 */
public final class PluginXmlUtil {
	private PluginXmlUtil() {
	}

	/**
	 * Adds an editor extension point to the given {@link IFile}.
	 *
	 * @param file the file to modify
	 * @param editorClass the editor class to register
	 * @param defaultEditor whether to register it as a default editor
	 * @param extensions the extensions to register for (comma-separated)
	 * @param icon the icon the register
	 * @param id the ID of the extension contribution
	 * @param name the user-readable name of the editor
	 */
	public static void addEditorExtensionPoint(IFile file, String editorClass, boolean defaultEditor,
		String extensions, String icon, String id, String name) {

		final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = documentBuilderFactory.newDocumentBuilder();
		} catch (final ParserConfigurationException ex) {
			Activator.getDefault().getReportService().report(
				new StatusReport(new Status(IStatus.ERROR, Activator.PLUGIN_ID, ex.getMessage(), ex)));
			return;
		}

		Document document;
		try {
			document = builder.parse(file.getContents());
		} catch (final SAXException ex) {
			Activator.getDefault().getReportService().report(
				new StatusReport(new Status(IStatus.ERROR, Activator.PLUGIN_ID, ex.getMessage(), ex)));
			return;
		} catch (final IOException ex) {
			Activator.getDefault().getReportService().report(
				new StatusReport(new Status(IStatus.ERROR, Activator.PLUGIN_ID, ex.getMessage(), ex)));
			return;
		} catch (final CoreException ex) {
			Activator.getDefault().getReportService().report(
				new StatusReport(new Status(IStatus.ERROR, Activator.PLUGIN_ID, ex.getMessage(), ex)));
			return;
		}

		final Element plugin = document.getDocumentElement();
		final Element editor = document.createElement("editor");
		editor.setAttribute("class", editorClass);
		editor.setAttribute("default", Boolean.toString(defaultEditor));
		editor.setAttribute("extensions", extensions);
		editor.setAttribute("icon", icon);
		editor.setAttribute("id", id);
		editor.setAttribute("name", name);
		final Element extension = document.createElement("extension");
		extension.setAttribute("point", "org.eclipse.ui.editors");
		plugin.appendChild(extension);
		extension.appendChild(editor);

		final TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = transformerFactory.newTransformer();
		} catch (final TransformerConfigurationException ex) {
			Activator.getDefault().getReportService().report(
				new StatusReport(new Status(IStatus.ERROR, Activator.PLUGIN_ID, ex.getMessage(), ex)));
			return;
		}
		final DOMSource domSource = new DOMSource(document);
		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			transformer.transform(domSource, new StreamResult(bos));
		} catch (final TransformerException ex) {
			Activator.getDefault().getReportService().report(
				new StatusReport(new Status(IStatus.ERROR, Activator.PLUGIN_ID, ex.getMessage(), ex)));
			return;
		}
		try {
			file.setContents(new ByteArrayInputStream(bos.toByteArray()), false, true, new NullProgressMonitor());
		} catch (final CoreException ex) {
			Activator.getDefault().getReportService().report(
				new StatusReport(new Status(IStatus.ERROR, Activator.PLUGIN_ID, ex.getMessage(), ex)));
			return;
		}
	}
}
