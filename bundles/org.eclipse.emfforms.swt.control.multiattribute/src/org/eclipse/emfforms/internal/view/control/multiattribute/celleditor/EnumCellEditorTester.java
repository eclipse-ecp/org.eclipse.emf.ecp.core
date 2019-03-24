/*******************************************************************************
 * Copyright (c) 2019 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.view.control.multiattribute.celleditor;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emfforms.spi.view.control.multiattribute.celleditor.MultiAttributeSWTRendererCellEditorTester;

/**
 * Cell editor tester that votes for a cell editor for attributes of {@link EEnum} type.
 */
public class EnumCellEditorTester implements MultiAttributeSWTRendererCellEditorTester {

	@Override
	public double isApplicable(EObject eObject, EAttribute multiAttribute,
		ViewModelContext viewModelContext) {

		final EDataType dataType = multiAttribute.getEAttributeType();

		if (dataType instanceof EEnum) {
			return 2.0;
		}

		return NOT_APPLICABLE;
	}

}
