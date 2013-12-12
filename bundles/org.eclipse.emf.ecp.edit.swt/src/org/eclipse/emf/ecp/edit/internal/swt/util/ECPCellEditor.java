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
 * 
 *******************************************************************************/
package org.eclipse.emf.ecp.edit.internal.swt.util;

import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;

/**
 * A common super interface for all CellEditors contributed to ECP.
 * 
 * @author Eugen Neufeld
 * 
 */
public interface ECPCellEditor {
	/**
	 * RAP theming variable.
	 */
	String CUSTOM_VARIANT = "org.eclipse.rap.rwt.customVariant"; //$NON-NLS-1$

	IValueProperty getValueProperty();

	void instantiate(EStructuralFeature feature, ViewModelContext viewModelContext);

	int getStyle();

	/**
	 * This returns the String which will be shown in the table when no cell editor is open.
	 * 
	 * @param value the Object to get the formated String for
	 * @return the formated String
	 */
	String getFormatedString(Object value);

	/**
	 * The returned value is used for layouting the table columns. The value is a relative column weight. A column
	 * containing text has a weight of 100. Please consider this when defining you weight. E.g if you return 200 your
	 * column will be twice the width of a text column.
	 * 
	 * @return the relative column width
	 */
	int getColumnWidthWeight();

	UpdateValueStrategy getTargetToModelStrategy();

	UpdateValueStrategy getModelToTargetStrategy();
}
