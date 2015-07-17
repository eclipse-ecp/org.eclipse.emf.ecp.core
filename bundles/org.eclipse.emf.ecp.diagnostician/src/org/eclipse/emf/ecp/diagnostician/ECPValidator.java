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
package org.eclipse.emf.ecp.diagnostician;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator;

/**
 * Abstract Class defining an {@link EValidator} that can be used with the ECP validation. Users should override
 * the {@link ECPValidator#validate(EClass, EObject, DiagnosticChain, Map)} and/or
 * {@link ECPValidator#validate(EDataType, Object, DiagnosticChain, Map)} methods to add their validations.
 *
 * @author jfaltermeier
 *
 */
public abstract class ECPValidator implements EValidator {

	/**
	 * Returns the {@link EClassifier}s which can be validated.
	 *
	 * @return the eclassifiers
	 */
	public abstract Set<EClassifier> getValidatedEClassifier();

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecore.EValidator#validate(org.eclipse.emf.ecore.EObject,
	 *      org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 */
	@Override
	public boolean validate(EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate(eObject.eClass(), eObject, diagnostics, context);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecore.EValidator#validate(org.eclipse.emf.ecore.EClass, org.eclipse.emf.ecore.EObject,
	 *      org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 */
	@Override
	public boolean validate(EClass eClass, EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecore.EValidator#validate(org.eclipse.emf.ecore.EDataType, java.lang.Object,
	 *      org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 */
	@Override
	public boolean validate(EDataType eDataType, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * Creates a diagnostic.
	 *
	 * @param severity the severity
	 * @param source the source of the validation
	 * @param code unique code
	 * @param message the message describing the validation error
	 * @param data the data.
	 * @param context the context
	 * @return a diagnostic with the given attributes
	 */
	protected Diagnostic createDiagnostic(int severity, String source, int code, String message, Object[] data,
		Map<Object, Object> context) {
		return new BasicDiagnostic(severity, source, code, message, data);
	}

	/**
	 * Creates a diagnostic with child diagnostics.
	 *
	 * @param source the source of the validation
	 * @param code unique code
	 * @param message the message describing the validation error
	 * @param data the data.
	 * @param context the context
	 * @param childDiagnostics the child diagnostics
	 * @return a diagnostic with the given attributes
	 */
	protected Diagnostic createDiagnostic(String source, int code, String message, Object[] data,
		Map<Object, Object> context, List<Diagnostic> childDiagnostics) {
		return new BasicDiagnostic(source, code, childDiagnostics, message, data);
	}
}
