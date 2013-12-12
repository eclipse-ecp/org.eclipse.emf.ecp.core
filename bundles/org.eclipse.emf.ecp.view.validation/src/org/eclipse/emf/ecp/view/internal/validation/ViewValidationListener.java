/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.validation;

import java.util.Set;

import org.eclipse.emf.common.util.Diagnostic;

/**
 * Listens for new validation results.
 * 
 * @author jfaltermeier
 * 
 */
public interface ViewValidationListener {

	/**
	 * Returns validation results if the validation severity is higher than {@link Diagnostic#OK}. If there are no
	 * severities higher than OK an empty Set is returned.
	 * 
	 * @param validationResults all diagnostics
	 */
	void onNewValidation(Set<Diagnostic> validationResults);
}
