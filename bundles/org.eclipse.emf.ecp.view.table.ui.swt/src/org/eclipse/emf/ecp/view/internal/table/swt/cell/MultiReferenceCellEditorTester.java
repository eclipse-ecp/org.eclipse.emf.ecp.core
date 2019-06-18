/*******************************************************************************
 * Copyright (c) 2017 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emf.ecp.view.internal.table.swt.cell;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditorTester;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;

/**
 * Tester for the {@link MultiReferenceCellEditor }.
 *
 * @since 1.18
 */
public class MultiReferenceCellEditorTester implements ECPCellEditorTester {

	@Override
	public int isApplicable(EObject eObject, EStructuralFeature eStructuralFeature, ViewModelContext viewModelContext) {
		if (!eStructuralFeature.isMany()) {
			return NOT_APPLICABLE;
		}

		if (EAttribute.class.isInstance(eStructuralFeature)) {
			return NOT_APPLICABLE;
		}

		return 5;
	}
}
