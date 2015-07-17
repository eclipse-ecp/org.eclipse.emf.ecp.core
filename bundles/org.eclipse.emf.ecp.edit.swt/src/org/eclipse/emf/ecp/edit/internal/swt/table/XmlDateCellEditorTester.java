/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.edit.internal.swt.table;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditorTester;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;

/**
 * Tester for xml date cell editor.
 *
 * @author Eugen Neufeld
 *
 */
public class XmlDateCellEditorTester implements ECPCellEditorTester {

	/**
	 * {@inheritDoc}
	 *
	 * @see ECPCellEditorTester#isApplicable(EObject, EStructuralFeature,
	 *      org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public int isApplicable(EObject eObject, EStructuralFeature feature, ViewModelContext viewModelContext) {
		if (EAttribute.class.isInstance(feature)) {
			final Class<?> instanceClass = ((EAttribute) feature).getEAttributeType().getInstanceClass();
			if (instanceClass == null) {
				return NOT_APPLICABLE;
			}
			if (XMLGregorianCalendar.class.isAssignableFrom(instanceClass)) {
				return 1;
			}
		}
		return NOT_APPLICABLE;
	}

}
