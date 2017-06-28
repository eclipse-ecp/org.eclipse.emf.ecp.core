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
package org.eclipse.emfforms.common.spi.validation.filter;

import org.eclipse.emf.ecore.EObject;

/**
 * Filter interface which allows to skip validation for certain {@link EObject}s
 * or to ignore Diagnostics reported by EValidators and Validators.
 * See ValidationService#registerValidationFilter(ValidationFilter).
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 */
public interface ObjectFilter extends ValidationFilter {

	/**
	 * Return true if the given {@link EObject} should NOT be validated.
	 *
	 * @param eObject the {@link EObject} about to be validated
	 * @return true if the given {@link EObject} should be skipped during validation.
	 *         Note: if you use ValidationService#validate(java.util.Iterator) in conjunction with a
	 *         TreeIterator all eContents of the given {@link EObject} will be skipped as well.
	 */
	boolean skipValidation(EObject eObject);

}
