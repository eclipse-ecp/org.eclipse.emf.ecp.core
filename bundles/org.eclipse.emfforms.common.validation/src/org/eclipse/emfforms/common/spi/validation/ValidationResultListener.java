/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Mat Hansen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.common.spi.validation;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;

/**
 * Diagnostic listener which can be used to subscribe to {@link Diagnostic} changes during model validation.
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 */
public interface ValidationResultListener {

	/**
	 * Callback which is invoked for every validation result.
	 *
	 * @param eObject the eObject which has been validated
	 * @param diagnostic the {@link Diagnostic} result of the validation
	 */
	void onValidate(EObject eObject, Diagnostic diagnostic);

	/**
	 * Callback which is invoked for every validation result which passed filtering.
	 * Validation result has been added to the validator output at this point.
	 *
	 * Note: this method is not triggered by {@link ValidationService#validate(EObject)}.
	 *
	 * @param eObject the eObject which has been validated
	 * @param diagnostic the {@link Diagnostic} result of the validation
	 */
	void afterValidate(EObject eObject, Diagnostic diagnostic);

}