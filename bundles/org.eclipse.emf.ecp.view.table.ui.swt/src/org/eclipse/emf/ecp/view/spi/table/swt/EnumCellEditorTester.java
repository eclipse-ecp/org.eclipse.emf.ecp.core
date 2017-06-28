/*******************************************************************************
 * Copyright (c) 2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.swt;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditorTester;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;

/**
 * Tester for the {@link EnumCellEditor}.
 *
 * @since 1.13
 */
public class EnumCellEditorTester implements ECPCellEditorTester {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditorTester#isApplicable(org.eclipse.emf.ecore.EObject,
	 *      org.eclipse.emf.ecore.EStructuralFeature, org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public int isApplicable(EObject eObject, EStructuralFeature eStructuralFeature, ViewModelContext viewModelContext) {
		if (eStructuralFeature.isMany()) {
			return NOT_APPLICABLE;
		}

		if (!EAttribute.class.isInstance(eStructuralFeature)) {
			return NOT_APPLICABLE;
		}

		final EDataType attributeType = EAttribute.class.cast(eStructuralFeature).getEAttributeType();
		if (!EEnum.class.isInstance(attributeType)) {
			return NOT_APPLICABLE;
		}

		return 5;
	}
}
