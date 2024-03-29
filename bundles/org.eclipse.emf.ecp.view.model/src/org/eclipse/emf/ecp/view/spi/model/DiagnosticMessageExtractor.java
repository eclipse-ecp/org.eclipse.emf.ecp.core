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
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.StringJoiner;

import org.eclipse.emf.common.util.Diagnostic;

/**
 * This Util allows to extract messages from Diagnostics.
 *
 * @author Eugen Neufeld
 * @since 1.5
 *
 */
public final class DiagnosticMessageExtractor {
	private DiagnosticMessageExtractor() {
		// Util class constructor
	}

	/**
	 * Extract the message to display from a single {@link Diagnostic}. If the severity of the Diagnostic is
	 * {@link Diagnostic#OK} then the message is empty.
	 *
	 * @param diagnostic The {@link Diagnostic} to get the message for
	 * @return The message or an empty string if the severity of the {@link Diagnostic} is ok.
	 */

	public static String getMessage(Diagnostic diagnostic) {
		if (diagnostic.getSeverity() == Diagnostic.OK) {
			return ""; //$NON-NLS-1$
		}
		if (diagnostic.getChildren() != null && diagnostic.getChildren().isEmpty()) {
			return diagnostic.getMessage();
		}
		final StringJoiner sb = new StringJoiner("\n"); //$NON-NLS-1$
		for (final Diagnostic childDiagnostic : diagnostic.getChildren()) {
			sb.add(childDiagnostic.getMessage());
		}
		return sb.toString();
	}

	private static final Comparator<Diagnostic> HIGEST_SEVERITY_FIRST = Comparator //
		.comparingInt(Diagnostic::getSeverity).reversed() // highest first
		.thenComparing(Diagnostic::getMessage);

	/**
	 * Extract the message to display from a collection of {@link Diagnostic Diagnostics}. If the severity of the
	 * Diagnostic is {@link Diagnostic#OK} then it is skipped.
	 *
	 * @param diagnostics The Collection of {@link Diagnostic Diagnostics} to get the message for
	 * @return The compound message for all {@link Diagnostic Diagnostics}
	 */
	public static String getMessage(Collection<Diagnostic> diagnostics) {
		final List<Diagnostic> diagnosticList = new ArrayList<>(diagnostics);
		diagnosticList.sort(HIGEST_SEVERITY_FIRST);

		final StringJoiner sb = new StringJoiner("\n"); //$NON-NLS-1$
		for (final Diagnostic diagnostic : diagnosticList) {
			if (diagnostic.getSeverity() != Diagnostic.OK) {
				sb.add(getMessage(diagnostic));
			}
		}
		return sb.toString();
	}
}
