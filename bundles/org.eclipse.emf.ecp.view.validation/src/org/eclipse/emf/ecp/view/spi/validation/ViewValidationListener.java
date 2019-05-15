/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.validation;

import java.util.Set;

import org.eclipse.emf.common.util.Diagnostic;

/**
 * Listens for new validation results.
 *
 * @author jfaltermeier
 * @since 1.5
 *
 */
public interface ViewValidationListener {

	// TODO this comment doesn't really fit to the actual functionality (imho)
	/**
	 * Returns validation results if the validation severity is higher than {@link Diagnostic#OK}. If there are no
	 * severities higher than OK an empty Set is returned.
	 *
	 * @param validationResults all diagnostics
	 */
	void onNewValidation(Set<Diagnostic> validationResults);
}
