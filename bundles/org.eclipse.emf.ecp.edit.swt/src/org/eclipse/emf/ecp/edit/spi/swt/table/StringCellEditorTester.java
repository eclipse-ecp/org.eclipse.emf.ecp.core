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
 * Eugen Neufeld - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecp.edit.spi.swt.table;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;

/**
 * An {@link ECPCellEditorTester} testing whether a StringCellEditor is applicable.
 *
 * @author Eugen Neufeld
 * @since 1.14
 *
 */
public class StringCellEditorTester implements ECPCellEditorTester {

	/**
	 * {@inheritDoc}
	 *
	 * @see ECPCellEditorTester#isApplicable(EObject, EStructuralFeature,
	 *      org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public int isApplicable(EObject eObject, EStructuralFeature eStructuralFeature, ViewModelContext viewModelContext) {
		return 0;
	}

}
