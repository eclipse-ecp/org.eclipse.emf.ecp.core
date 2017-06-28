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

import java.util.List;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;

/**
 * Interface for custom validators to be registered on a {@link ValidationService}.
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 */
public interface Validator {

	/**
	 * Method is called by the {@link ValidationService} to retrieve addition validation
	 * information for the given {@link EObject}.
	 *
	 * @param eObject the {@link EObject} to validate
	 * @return the List of additional {@link Diagnostic Diagnostics} for the {@link EObject}
	 */
	// TODO possibly refactor interface to void validate(EObject eObject, List<Diagnostic>);
	List<Diagnostic> validate(EObject eObject);

}
