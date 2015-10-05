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
 * {@link MultiAttributeSWTRendererCellEditorTester Tester} for StringCellEditor.
 * 
 * @author Johannes Faltermeier
 *
 */
public class StringCellEditorTester implements MultiAttributeSWTRendererCellEditorTester {

	@Override
	public double isApplicable(EObject eObject, EAttribute multiAttribute,
		ViewModelContext viewModelContext) {
		return 0d;
	}

}
