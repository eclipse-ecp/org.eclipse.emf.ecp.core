/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.quickfix;

import java.util.List;

import org.eclipse.emf.common.util.Diagnostic;

/**
 * Registry for model quick fixes.
 *
 * @author Alexandra Buzila
 *
 */
public interface ModelQuickFixRegistry {

	/**
	 * Register a {@link ModelQuickFix} implementation.
	 *
	 * @param modelQuickFix - the implementation to register
	 */
	void registerModelQuickFix(ModelQuickFix modelQuickFix);

	/**
	 * @param diagnostic - the diagnostic
	 * @return a list of {@link ModelQuickFix} objects, applicable for the given diagnostic. The list is
	 *         ordered by the priority of the quick fixes, in descending order.
	 */
	List<ModelQuickFix> getApplicableModelQuickFixes(Diagnostic diagnostic);

	/** @return all the quick fixes contained in the registry. */
	List<ModelQuickFix> getAllModelQuickFixes();

}