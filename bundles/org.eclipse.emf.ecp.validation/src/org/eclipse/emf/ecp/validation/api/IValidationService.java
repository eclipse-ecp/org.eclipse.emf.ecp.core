/*******************************************************************************
 * Copyright (c) 2008-2012 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.ecp.validation.api;

import java.util.Collection;
import java.util.Set;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;

/**
 * Validation service interface.
 *
 * @author emueller
 */
public interface IValidationService {

	/**
	 * Validates the given {@link EObject}s.
	 *
	 * @param eObjects
	 *            a collection of {@link EObject}s to be validated
	 * @return the set of affected elements
	 */
	Set<EObject> validate(Collection<EObject> eObjects);

	/**
	 * Validates the given {@link EObject}.
	 *
	 * @param eObject
	 *            the {@link EObject} to be validated
	 * @return the set of affected elements
	 */
	Set<EObject> validate(EObject eObject);

	/**
	 * Returns the severity for the given {@link EObject}.
	 *
	 * @param eObject
	 *            the {@link EObject} whose severity should be returned
	 * @return the severity for the given {@link EObject}
	 */
	Diagnostic getDiagnostic(Object eObject);

	/**
	 * Returns the highest severity.
	 *
	 * @return the highest severity
	 */
	Diagnostic getRootDiagnostic();

	/**
	 * Removes the severity of the given {@link EObject}.
	 *
	 * @param eObject
	 *            the {@link EObject} whose severity should be removed
	 */
	void remove(EObject eObject);
}
