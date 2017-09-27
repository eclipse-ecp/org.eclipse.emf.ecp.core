/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.common.prevalidation;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EDataType;

/**
 * A {@link PreSetValidator} validates a value against a given data type, before it gets set in the model.
 *
 */
public interface PreSetValidator {

	/**
	 * Validates the object in the given context, optionally producing diagnostics.
	 *
	 * @param eDataType the {@link org.eclipse.emf.ecore.EDataType EDataType} to validate the value against
	 * @param value the value to be validated
	 * @param diagnostics a place to accumulate diagnostics; if it's <code>null</code>, no diagnostics should be
	 *            produced.
	 * @param context a place to cache information, if it's <code>null</code>, no cache is supported.
	 * @return whether the object is valid.
	 */
	boolean validate(EDataType eDataType, Object value, DiagnosticChain diagnostics, Map<Object, Object> context);
}
