/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.validation;

import java.util.List;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;

/**
 * The ValidationService calls the providers after the validation with emf. By providing an own provider, one can extend
 * the EMF validation by providing additional validation rules.
 * 
 * @author Eugen Neufeld
 * 
 */
public interface ValidationProvider {

	/**
	 * Method is called by the {@link ValidationService} to get addition validation information for an {@link EObject}.
	 * 
	 * @param eObject the {@link EObject} to validate
	 * @return the List of additional {@link Diagnostic Diagnostics} for the {@link EObject}
	 */
	List<Diagnostic> validate(EObject eObject);
}
