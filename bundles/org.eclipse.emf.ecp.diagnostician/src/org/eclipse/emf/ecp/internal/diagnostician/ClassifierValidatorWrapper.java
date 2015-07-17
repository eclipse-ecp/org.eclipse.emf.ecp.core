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
package org.eclipse.emf.ecp.internal.diagnostician;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.diagnostician.ECPValidator;

/**
 * Wraps {@link ECPValidator}s registered for the same {@link EClassifier}.
 *
 * @author jfaltermeier
 *
 */
public class ClassifierValidatorWrapper extends ECPValidator {

	private final Set<EClassifier> classifier;
	private final Set<ECPValidator> validators;

	/**
	 * Constructor.
	 *
	 * @param classifier the classifier
	 * @param validators the wrapped validators.
	 */
	public ClassifierValidatorWrapper(EClassifier classifier, Set<ECPValidator> validators) {
		this.classifier = new LinkedHashSet<EClassifier>();
		this.classifier.add(classifier);
		this.validators = validators;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.diagnostician.ECPValidator#getValidatedEClassifier()
	 */
	@Override
	public Set<EClassifier> getValidatedEClassifier() {
		return classifier;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.diagnostician.ECPValidator#validate(org.eclipse.emf.ecore.EClass,
	 *      org.eclipse.emf.ecore.EObject, org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 */
	@Override
	public boolean validate(EClass eClass, EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = true;
		final Iterator<ECPValidator> iterator = validators.iterator();
		while (iterator.hasNext()) {
			final boolean validationResult = iterator.next().validate(eClass, eObject, diagnostics, context);
			if (!validationResult) {
				result = validationResult;
			}
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.diagnostician.ECPValidator#validate(org.eclipse.emf.ecore.EDataType, java.lang.Object,
	 *      org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 */
	@Override
	public boolean validate(EDataType eDataType, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = true;
		final Iterator<ECPValidator> iterator = validators.iterator();
		while (iterator.hasNext()) {
			final boolean validationResult = iterator.next().validate(eDataType, value, diagnostics, context);
			if (!validationResult) {
				result = validationResult;
			}
		}
		return result;
	}
}
