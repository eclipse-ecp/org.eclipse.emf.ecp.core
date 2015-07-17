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
package org.eclipse.emf.ecp.edit.spi.swt.table;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.swt.graphics.Image;

/**
 * A common super interface for all CellEditors contributed to ECP.
 *
 * @author Eugen Neufeld
 * @since 1.5
 *
 */
public interface ECPCellEditor {
	/**
	 * RAP theming variable.
	 */
	String CUSTOM_VARIANT = "org.eclipse.rap.rwt.customVariant"; //$NON-NLS-1$

	/**
	 * Returns the {@link IValueProperty} for this cell editor which is used by the table to create an
	 * {@link org.eclipse.core.databinding.observable.value.IObservableValue IObservableValue}.
	 *
	 * @return the {@link IValueProperty} for this cell editor
	 */
	IValueProperty getValueProperty();

	/**
	 * Instantiates this cell editor. This allows the cell editor to use
	 * {@link org.eclipse.emf.ecp.view.spi.context.ViewModelService ViewModelServices}.
	 *
	 * @param feature the {@link EStructuralFeature} displayed in this cell editor
	 * @param viewModelContext the {@link ViewModelContext} used for the current view
	 */
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
	 * This returns the Image which will be shown in the table when no cell editor is open.
	 *
	 * @param value the Object to get the Image for
	 * @return the image
	 */
	Image getImage(Object value);

	/**
	 * The returned value is used for layouting the table columns. The value is a relative column weight. A column
	 * containing text has a weight of 100. Please consider this when defining you weight. E.g if you return 200 your
	 * column will be twice the width of a text column.
	 *
	 * @return the relative column width
	 */
	int getColumnWidthWeight();

	/**
	 * This {@link UpdateValueStrategy} will be used as the target to model strategy during data binding.
	 *
	 * @param databindingContext The {@link DataBindingContext} used by this strategy
	 * @return the strategy
	 * @since 1.6
	 */
	UpdateValueStrategy getTargetToModelStrategy(DataBindingContext databindingContext);

	/**
	 * This {@link UpdateValueStrategy} will be used as the model to target strategy during data binding.
	 *
	 * @param databindingContext The {@link DataBindingContext} used by this strategy
	 * @return the strategy
	 * @since 1.6
	 */
	UpdateValueStrategy getModelToTargetStrategy(DataBindingContext databindingContext);

	/**
	 * Sets editable state of the cell editor.
	 *
	 * @param editable <code>true</code> if editable, <code>false</code> otherwise
	 */
	void setEditable(boolean editable);

	/**
	 * Returns the minimum width of the cell editor.
	 *
	 * @return the minimum width
	 * @since 1.6
	 */
	int getMinWidth();
}
