/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emf.ecp.edit.spi.swt.table;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.jface.databinding.viewers.CellEditorProperties;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

/**
 * A String cell editor which displays strings.
 *
 * @author Eugen Neufeld
 * @since 1.5
 *
 */
public class StringCellEditor extends StringBasedCellEditor {

	private EStructuralFeature eStructuralFeature;

	/**
	 * Default constructor.
	 */
	public StringCellEditor() {
		super();
	}

	/**
	 * A constructor which takes only a parent.
	 *
	 * @param parent the {@link Composite} to use as a parent.
	 */
	public StringCellEditor(Composite parent) {
		super(parent);
	}

	/**
	 * A constructor which takes the parent and the style.
	 *
	 * @param parent the {@link Composite} to use as a parent
	 * @param style the Style to set
	 */
	public StringCellEditor(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	public IValueProperty<CellEditor, String> getValueProperty() {
		return CellEditorProperties.control().value(WidgetProperties.text(SWT.FocusOut));
	}

	@Override
	public void instantiate(EStructuralFeature feature, ViewModelContext viewModelContext) {
		eStructuralFeature = feature;
	}

	@Override
	public String getFormatedString(Object value) {
		if (value == null) {
			return ""; //$NON-NLS-1$
		}
		return String.valueOf(value);
	}

	@Override
	public int getColumnWidthWeight() {
		return 100;
	}

	@Override
	public UpdateValueStrategy getTargetToModelStrategy(DataBindingContext databindingContext) {
		return withPreSetValidation(eStructuralFeature, new UpdateValueStrategy() {

			@Override
			public Object convert(Object value) {
				if ("".equals(value)) { //$NON-NLS-1$
					value = null;
				}
				if (value == null && eStructuralFeature.isUnsettable()) {
					return SetCommand.UNSET_VALUE;
				}
				return super.convert(value);
			}

		});
	}

	@Override
	public UpdateValueStrategy getModelToTargetStrategy(DataBindingContext databindingContext) {
		return null;
	}

	@Override
	public void setEditable(boolean editable) {
		if (text != null) {
			text.setEditable(editable);
		}
	}

	@Override
	public Image getImage(Object value) {
		return null;
	}

	@Override
	public int getMinWidth() {
		return 0;
	}

}
