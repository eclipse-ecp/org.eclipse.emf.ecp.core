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
package org.eclipse.emf.ecp.ui.validation;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EClassifier;

/**
 * Service for propagating validation results.
 *
 * @author jfaltermeier
 *
 */
public interface ECPValidationResultService {

	/**
	 * Passes the given diagnostic to all registered {@link ECPValidationResultServiceListener}s.
	 *
	 * @param diagnostic the diagnostic to display
	 */
	void setResult(Diagnostic diagnostic);

	/**
	 * Passes the given diagnostics to all registered {@link ECPValidationResultServiceListener}s.
	 *
	 * @param diagnostic the diagnostics to display
	 */
	void setResult(Diagnostic[] diagnostic);

	/**
	 * Passes the given diagnostics to all registered {@link ECPValidationResultServiceListener}s.
	 *
	 * @param diagnostic the diagnostics to display
	 */
	void setResult(List<Diagnostic> diagnostic);

	/**
	 * Registers a listener that gets informed whenever the input changes.
	 *
	 * @param listener the listener to be registered
	 */
	void register(ECPValidationResultServiceListener listener);

	/**
	 * Registers a listener that gets informed whenever there is a validation result for an object of a type from the
	 * given set of {@link EClassifier}s.
	 *
	 * @param listener the listener to be registered
	 * @param classifiersOfInterest the set of {@link EClassifier}s
	 */
	void register(ECPValidationResultServiceListener listener, Set<EClassifier> classifiersOfInterest);

	/**
	 * Deregisters a listener.
	 *
	 * @param listener the listener to be deregistered
	 */
	void deregister(ECPValidationResultServiceListener listener);

	/**
	 * Listener interface for getting informed on input changes of the validation view.
	 *
	 * @author jfaltermeier
	 *
	 */
	public interface ECPValidationResultServiceListener {
		/**
		 * Called whenever the input changes.
		 *
		 * @param diagnostic the diagnostic. May be an array a list or a single Diagnostic.
		 */
		void resultChanged(Object diagnostic);
	}

}
