/*******************************************************************************
 * Copyright (c) 2019 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.ide.builder;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.ui.MarkerHelper;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.ui.util.EditUIMarkerHelper;
import org.eclipse.emfforms.ide.internal.builder.ValidationBuilder;

/**
 * Default implementation of the {@link MarkerHelper} that accounts for the fact
 * that the {@link ValidationServiceDelegate} unloads its resource set before markers
 * are created, so that objects in the {@linkplain Diagnostic diagnostics} are
 * proxies by the time markers are created.
 */
public class DefaultMarkerHelper extends EditUIMarkerHelper {

	/**
	 * Initializes me.
	 */
	public DefaultMarkerHelper() {
		super();
	}

	@Override
	protected String getMarkerID() {
		return ValidationBuilder.MARKER_ID;
	}

	@Override
	protected IFile getFile(Object datum) {
		return datum instanceof EObject && ((EObject) datum).eIsProxy()
			? getFile(EcoreUtil.getURI((EObject) datum))
			: super.getFile(datum);
	}

	@Override
	protected void createMarkers(IResource resource, Diagnostic diagnostic, Diagnostic parentDiagnostic)
		throws CoreException {

		if (diagnostic.getChildren().isEmpty()) {
			super.createMarkers(resource, diagnostic, parentDiagnostic);
		} else {
			// From the ValidationServiceDelegate, we can get more than two levels of nesting
			for (final Diagnostic next : diagnostic.getChildren()) {
				createMarkers(resource, next, diagnostic);
			}
		}
	}

	/**
	 * Add attributes to store the URIs of the problematic object and the feature (if any).
	 * Match exactly the specification or main and related URIs attributes expected by EMF's
	 * marker utility.
	 */
	@Override
	protected void adjustMarker(IMarker marker, Diagnostic diagnostic, Diagnostic parentDiagnostic)
		throws CoreException {

		// The first URI is not encoded because there is only one, so spaces in the string don't
		// matter. But the related URIs are a list separated by spaces, so they are encoded and
		// will be decoded by the MarkerUtil as needed
		String uri = null;
		final StringBuilder relatedURIs = new StringBuilder();

		for (final Object next : diagnostic.getData()) {
			if (next instanceof EObject) {
				final EObject eObject = (EObject) next;
				if (uri == null) {
					uri = EcoreUtil.getURI(eObject).toString();
				} else {
					if (relatedURIs.length() > 0) {
						relatedURIs.append(' '); // Space to separate encoded URIs
					}
					relatedURIs.append(URI.encodeFragment(EcoreUtil.getURI(eObject).toString(), false));
				}
			}
		}

		if (uri != null) {
			marker.setAttribute(EValidator.URI_ATTRIBUTE, uri);
		}
		if (relatedURIs.length() > 0) {
			marker.setAttribute(EValidator.RELATED_URIS_ATTRIBUTE, relatedURIs.toString());
		}

		super.adjustMarker(marker, diagnostic, parentDiagnostic);
	}

}
