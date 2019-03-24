/*******************************************************************************
 * Copyright (c) 2019 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.ide.builder;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.ui.MarkerHelper;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
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
}
