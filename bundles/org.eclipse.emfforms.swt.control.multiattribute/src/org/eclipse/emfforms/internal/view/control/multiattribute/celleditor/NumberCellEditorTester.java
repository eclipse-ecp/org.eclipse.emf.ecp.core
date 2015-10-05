/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.view.control.multiattribute.celleditor;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emfforms.spi.view.control.multiattribute.celleditor.MultiAttributeSWTRendererCellEditorTester;

/**
 * {@link MultiAttributeSWTRendererCellEditorTester Tester} for NumberCellEditor.
 * 
 * @author Johannes Faltermeier
 *
 */
public class NumberCellEditorTester implements MultiAttributeSWTRendererCellEditorTester {

	@Override
	public double isApplicable(EObject eObject, EAttribute multiAttribute,
		ViewModelContext viewModelContext) {
		final Class<?> instanceClass = multiAttribute.getEAttributeType().getInstanceClass();
		if (instanceClass == null) {
			return NOT_APPLICABLE;
		}
		if (Number.class.isAssignableFrom(instanceClass)) {
			return 1d;
		}

		// if the attribute class is an primitive test the primitive types
		if (instanceClass.isPrimitive()) {
			if (int.class.equals(instanceClass)) {
				return 1d;
			} else if (float.class.equals(instanceClass)) {
				return 1d;
			} else if (long.class.equals(instanceClass)) {
				return 1d;
			} else if (double.class.equals(instanceClass)) {
				return 1d;
			} else if (short.class.equals(instanceClass)) {
				return 1d;
			}
		}
		return NOT_APPLICABLE;
	}

}
