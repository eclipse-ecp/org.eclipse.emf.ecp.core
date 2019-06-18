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
package org.eclipse.emf.ecp.view.internal.validation;

import java.util.function.Predicate;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emfforms.spi.localization.EMFFormsLocalizationService;

/**
 * A specialized diagnostic indicating a reporting threshold that was reached,
 * representing all of the unreported problems.
 */
final class ThresholdDiagnostic extends BasicDiagnostic {

	/**
	 * Initializes me.
	 *
	 * @param severity the problem severity
	 * @param message the message
	 */
	private ThresholdDiagnostic(int severity, String message) {
		super(severity, Activator.PLUGIN_ID, 0, message, null);
	}

	//
	// Nested types
	//

	/**
	 * A factory of (pooled) threshold diagnostics.
	 */
	static final class Factory {
		private final EMFFormsLocalizationService l10n;

		private ThresholdDiagnostic errorInstance;
		private ThresholdDiagnostic warningInstance;
		private ThresholdDiagnostic infoInstance;

		/**
		 * Initializes me with the localization service from which to get messages.
		 *
		 * @param l10n the localization service for access to messages
		 */
		Factory(EMFFormsLocalizationService l10n) {
			super();

			this.l10n = l10n;
		}

		/**
		 * Get the threshold diagnostic representing unreported problems of the
		 * given {@code severity} and less.
		 *
		 * @param severity the maximal unreported problem severity
		 * @return the threshold diagnostic for that {@code severity}
		 */
		ThresholdDiagnostic get(int severity) {
			switch (severity) {
			case Diagnostic.CANCEL:
			case Diagnostic.ERROR:
				if (errorInstance == null) {
					errorInstance = create(Diagnostic.ERROR);
				}
				return errorInstance;
			case Diagnostic.WARNING:
				if (warningInstance == null) {
					warningInstance = create(Diagnostic.WARNING);
				}
				return warningInstance;
			case Diagnostic.INFO:
				if (infoInstance == null) {
					infoInstance = create(Diagnostic.INFO);
				}
				return infoInstance;
			default:
				throw new IllegalArgumentException("severity: " + severity); //$NON-NLS-1$
			}
		}

		private ThresholdDiagnostic create(int severity) {
			return new ThresholdDiagnostic(severity,
				l10n.getString(ThresholdDiagnostic.class, "ValidationServiceImpl_moreProblems")); //$NON-NLS-1$
		}

		/**
		 * Obtain a predicate matching diagnostics that are not threshold diagnostic
		 * placeholders.
		 *
		 * @return a not-a-threshold-diagnostic filter
		 */
		Predicate<Diagnostic> notThresholdDiagnostic() {
			final Predicate<Diagnostic> isThreshold = ThresholdDiagnostic.class::isInstance;
			return isThreshold.negate();
		}

	}

}
