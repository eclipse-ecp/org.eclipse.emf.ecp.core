/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecp.edit.internal.swt.table;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditorTester;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;

/**
 * An {@link ECPCellEditorTester} testing whether a StringCellEditor is applicable.
 *
 * @author Eugen Neufeld
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
