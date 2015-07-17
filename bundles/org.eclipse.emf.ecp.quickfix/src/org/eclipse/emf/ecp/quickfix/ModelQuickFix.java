/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 */
package org.eclipse.emf.ecp.quickfix;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;

/**
 * Interface for model quick fixes.
 *
 * @author Alexandra Buzila
 *
 */
public interface ModelQuickFix {

	/** Priority for fixes that are not applicable to a given diagnostic. */
	double NOT_APPLICABLE = 0;

	/**
	 * Checks whether the quick fix is applicable for the provided Diagnostic.
	 *
	 * @param diagnostic - the diagnostic of the problem
	 * @return the priority of the fix, higher priority will be preferred
	 * */
	double isApplicable(Diagnostic diagnostic);

	/**
	 * Applies the fix to the {@link EObject}.
	 *
	 * @param target - the {@link EObject} that should be fixed
	 * @throws ModelQuickFixException if something went wrong while applying the fix.
	 */
	void applyFix(EObject target) throws ModelQuickFixException;

	/**
	 * @param diagnostic the diagnostic for which the label should be returned
	 * @return the label for the quick fix, for the given diagnostic
	 */
	String getLabel(Diagnostic diagnostic);

}
