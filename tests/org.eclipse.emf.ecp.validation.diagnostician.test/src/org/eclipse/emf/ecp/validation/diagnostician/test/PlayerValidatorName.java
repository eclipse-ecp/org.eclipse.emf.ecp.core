/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.validation.diagnostician.test;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.diagnostician.ECPValidator;
import org.eclipse.emf.emfstore.bowling.BowlingPackage;

/**
 * @author jfaltermeier
 *
 */
public class PlayerValidatorName extends ECPValidator {

	private int hitCount;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.diagnostician.ECPValidator#getValidatedEClassifier()
	 */
	@Override
	public Set<EClassifier> getValidatedEClassifier() {
		final Set<EClassifier> classifiers = new LinkedHashSet<EClassifier>();
		classifiers.add(BowlingPackage.eINSTANCE.getPlayer());
		return classifiers;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.diagnostician.ECPValidator#validate(org.eclipse.emf.ecore.EClass,
	 *      org.eclipse.emf.ecore.EObject, org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 */
	@Override
	public boolean validate(EClass eClass, EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
		hitCount++;
		diagnostics.add(createDiagnostic(Diagnostic.ERROR, "source", 0, "Name", new Object[] { eObject,
			BowlingPackage.eINSTANCE.getPlayer_Name() }, context));
		return false;
	}

	public int getHitCount() {
		return hitCount;
	}

}
