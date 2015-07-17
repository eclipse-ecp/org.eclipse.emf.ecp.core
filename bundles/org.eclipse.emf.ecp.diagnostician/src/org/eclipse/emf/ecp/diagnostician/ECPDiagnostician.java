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

import java.util.Map;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecp.internal.diagnostician.ECPValidatorRegistry;

/**
 * The {@link ECPDiagnostician} will invoke the registered {@link org.eclipse.emf.ecp.diagnostician.ECPValidator
 * ECPValidators}.
 *
 * @author jfaltermeier
 *
 */
public final class ECPDiagnostician {

	/**
	 * The instance of the {@link ECPDiagnostician}.
	 */
	public static final ECPDiagnostician INSTANCE = new ECPDiagnostician();

	private final Diagnostician diagnostician;

	private ECPDiagnostician() {
		diagnostician = new Diagnostician(ECPValidatorRegistry.INSTANCE);
	}

	/**
	 * Validates the given {@link EObject}.
	 *
	 * @param eObject the object to validate.
	 * @return the diagnostic
	 */
	public Diagnostic validate(EObject eObject) {
		return diagnostician.validate(eObject);
	}

	/**
	 * Validates the given {@link EObject}.
	 *
	 * @param eObject the object to validate.
	 * @param contextEntries context entries that may be needed for the validation
	 * @return the diagnostic
	 */
	public Diagnostic validate(EObject eObject, Map<?, ?> contextEntries) {
		return diagnostician.validate(eObject, contextEntries);
	}

	/**
	 * Whether the diagnostician can validate the given object.
	 *
	 * @param eObject the object to check
	 * @return <code>true</code> if a validator is registered for the object, <code>false</code> otherwise
	 */
	public boolean canValidate(EObject eObject) {
		return ECPValidatorRegistry.INSTANCE.hasValidator(eObject.eClass());
	}
}
