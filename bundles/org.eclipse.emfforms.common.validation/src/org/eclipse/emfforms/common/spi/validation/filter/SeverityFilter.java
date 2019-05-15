/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Mat Hansen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.common.spi.validation.filter;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;

/**
 * Convenience class to filter {@link Diagnostic}s based on their severity.
 * See ValidationService#registerValidationFilter(ValidationFilter).
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 */
public class SeverityFilter implements DiagnosticFilter {

	private final int minimumSeverity;

	/**
	 * Constructor with default severity.
	 *
	 * @param minimumSeverity the minimum severity to pass, i.e. Diagnostic.WARNING
	 */
	public SeverityFilter(int minimumSeverity) {
		this.minimumSeverity = minimumSeverity;
	}

	@Override
	public boolean ignoreDiagnostic(EObject eObject, Diagnostic diagnostic) {
		return diagnostic.getSeverity() < minimumSeverity;
	}

}
