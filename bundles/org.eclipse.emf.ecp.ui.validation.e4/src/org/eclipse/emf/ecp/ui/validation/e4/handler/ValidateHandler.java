/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.ui.validation.e4.handler;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.diagnostician.ECPDiagnostician;
import org.eclipse.emf.ecp.ui.validation.ECPValidationResultService;

/**
 * Handler for performing a validation using the {@link ECPDiagnostician}.
 *
 * @author jfaltermeier
 *
 */
public class ValidateHandler {

	@Inject
	private ECPValidationResultService service;

	/**
	 * Performs a validation for currently selected EObject and passes the result to the
	 * {@link ECPValidationResultService}.
	 *
	 * @param object the object to validate
	 */
	@Execute
	public void execute(@Named(IServiceConstants.ACTIVE_SELECTION) @Optional final EObject object) {
		final Diagnostic validate = ECPDiagnostician.INSTANCE.validate(object);
		service.setResult(validate);
	}

	/**
	 * Whether the handler can be executed for the current selection.
	 *
	 * @param object the current selection
	 * @return <code>true</code> if executable, <code>false</code> otherwise
	 */
	@CanExecute
	public boolean canExecute(@Named(IServiceConstants.ACTIVE_SELECTION) @Optional final EObject object) {
		if (object == null) {
			return false;
		}
		return ECPDiagnostician.INSTANCE.canValidate(object);
	}

}
