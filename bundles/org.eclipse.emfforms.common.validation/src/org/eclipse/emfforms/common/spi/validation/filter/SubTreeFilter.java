/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
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

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emfforms.common.Optional;

/**
 * Interface that allows to skip the validation of sub trees. Only applicable for
 * ValidationService#validate(java.util.Iterator) in conjunction with a EMF TreeIterator.
 * See ValidationService#registerValidationFilter(ValidationFilter).
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 */
public interface SubTreeFilter extends ValidationFilter {

	/**
	 * Return true if the eContents (subtree) of the given {@link EObject} should not be validated.
	 *
	 * Note: this is only applicable for ValidationService#validate(java.util.Iterator)
	 * in conjunction with a EMF TreeIterator.
	 *
	 * @param eObject the parent {@link EObject}
	 * @param diagnostic the {@link Diagnostic} for the {@link EObject} in case it has been validated.
	 * @return true, if the subtree of the given {@link EObject} should be skipped.
	 */
	boolean skipSubtree(EObject eObject, Optional<Diagnostic> diagnostic);

}
