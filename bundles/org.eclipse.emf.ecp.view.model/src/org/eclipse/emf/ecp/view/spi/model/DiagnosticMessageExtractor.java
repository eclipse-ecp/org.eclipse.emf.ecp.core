/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
		if (diagnostic.getChildren() != null && diagnostic.getChildren().size() == 0) {
			return diagnostic.getMessage();
		}
		final StringBuilder sb = new StringBuilder();
		for (final Diagnostic childDiagnostic : diagnostic.getChildren()) {
			if (sb.length() > 0) {
				sb.append("\n"); //$NON-NLS-1$
			}
			sb.append(childDiagnostic.getMessage());
		}
		return sb.toString();
	}

	/**
	 * Extract the message to display from a collection of {@link Diagnostic Diagnostics}. If the severity of the
	 * Diagnostic is {@link Diagnostic#OK} then it is skipped.
	 *
	 * @param diagnostics The Collection of {@link Diagnostic Diagnostics} to get the message for
	 * @return The compound message for all {@link Diagnostic Diagnostics}
	 */
	public static String getMessage(Collection<Diagnostic> diagnostics) {
		final List<Diagnostic> diagnosticList = new ArrayList<Diagnostic>(diagnostics);
		Collections.sort(diagnosticList, new Comparator<Diagnostic>() {

			@Override
			public int compare(Diagnostic o1, Diagnostic o2) {
				if (o1.getSeverity() != o2.getSeverity()) {
					// highest first
					return o2.getSeverity() - o1.getSeverity();
				}
				return o1.getMessage().compareTo(o2.getMessage());
			}
		});

		final StringBuilder sb = new StringBuilder();
		for (final Diagnostic diagnostic : diagnosticList) {
			if (diagnostic.getSeverity() == Diagnostic.OK) {
				continue;
			}
			if (sb.length() > 0) {
				sb.append("\n"); //$NON-NLS-1$
			}
			sb.append(getMessage(diagnostic));
		}
		return sb.toString();
	}
}
