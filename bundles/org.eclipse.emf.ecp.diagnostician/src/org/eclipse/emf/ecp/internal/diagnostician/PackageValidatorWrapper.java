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

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecp.diagnostician.ECPValidator;

/**
 * Wrapper for {@link ECPValidator}s that validate different {@link EClass}es from the same
 * {@link org.eclipse.emf.ecore.EPackage EPackage}.
 *
 * @author jfaltermeier
 *
 */
public class PackageValidatorWrapper implements EValidator {

	private final Map<EClassifier, ECPValidator> classifierToValidatorMap;

	/**
	 * Constructor.
	 *
	 * @param classifierToValidatorMap map from Classifier to its validator
	 */
	public PackageValidatorWrapper(Map<EClassifier, ECPValidator> classifierToValidatorMap) {
		this.classifierToValidatorMap = classifierToValidatorMap;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecore.EValidator#validate(org.eclipse.emf.ecore.EObject,
	 *      org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 */
	@Override
	public boolean validate(EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
		final ECPValidator ecpValidator = classifierToValidatorMap.get(eObject.eClass());
		if (ecpValidator == null) {
			return true;
		}
		return ecpValidator.validate(eObject, diagnostics, context);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecore.EValidator#validate(org.eclipse.emf.ecore.EClass, org.eclipse.emf.ecore.EObject,
	 *      org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 */
	@Override
	public boolean validate(EClass eClass, EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
		final ECPValidator ecpValidator = classifierToValidatorMap.get(eClass);
		if (ecpValidator == null) {
			return true;
		}
		return ecpValidator.validate(eClass, eObject, diagnostics, context);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecore.EValidator#validate(org.eclipse.emf.ecore.EDataType, java.lang.Object,
	 *      org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 */
	@Override
	public boolean validate(EDataType eDataType, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
		final ECPValidator ecpValidator = classifierToValidatorMap.get(eDataType);
		if (ecpValidator == null) {
			return true;
		}
		return ecpValidator.validate(eDataType, value, diagnostics, context);
	}

}
