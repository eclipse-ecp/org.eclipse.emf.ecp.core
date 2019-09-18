/**
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.internal.table.swt.cell;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditorTester;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;

/**
 * Single reference cell editor tester.
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 * @author Eugen Neufeld <eneufeld@eclipsesource.com>
 * @since 1.22
 *
 */
public class SingleReferenceCellEditorTester implements ECPCellEditorTester {

	@Override
	public int isApplicable(EObject eObject, EStructuralFeature eStructuralFeature, ViewModelContext viewModelContext) {
		if (!EReference.class.isInstance(eStructuralFeature)) {
			return NOT_APPLICABLE;
		}

		final EReference eReference = EReference.class.cast(eStructuralFeature);
		if (eReference.getUpperBound() == 1) {
			return 10;
		}

		return NOT_APPLICABLE;
	}

}
