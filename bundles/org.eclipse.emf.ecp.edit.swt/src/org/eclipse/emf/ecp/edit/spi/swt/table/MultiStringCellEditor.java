/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Jonas Helming - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecp.edit.spi.swt.table;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.databinding.EMFUpdateValueStrategy;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.CellEditorProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

/**
 * A cell Editor to display List of Strings in a cell editor. it allows to enter a list of string using a
 * separator (by default ";").
 *
 * @author Jonas Helming
 * @since 1.14
 *
 */
public class MultiStringCellEditor extends StringBasedCellEditor {

	/**
	 * @author Jonas Helming
	 *
	 */
	protected final class TargetToModelStrategy extends EMFUpdateValueStrategy {

		@Override
		public Object convert(final Object value) {
			return convertStringToList((String) value);
		}

	}

	/**
	 * Convertes a String into a list of Strings using the separator.
	 *
	 * @param value the String to split
	 * @return the list of sub string
	 */
	protected List<String> convertStringToList(final String value) {
		final boolean emptyStringAtEnd = value.endsWith(getSeparator());
		final String[] split = value.split(getSeparator());
		final List<String> list = new ArrayList<String>();
		for (int i = 0; i < split.length; i++) {
			list.add(split[i]);
		}
		if (emptyStringAtEnd) {
			list.add(""); //$NON-NLS-1$
		}
		return list;
	}

	/**
	 * Returns the separator used to split the input string into entries of the String list.
	 *
	 * @return the separator as a String
	 */
	protected String getSeparator() {
		return ";"; //$NON-NLS-1$
	}

	private EStructuralFeature eStructuralFeature;

	/**
	 * The constructor which only takes a parent composite.
	 *
	 * @param parent the {@link Composite} to use as a parent.
	 */
	public MultiStringCellEditor(Composite parent) {
		super(parent, SWT.NONE);
	}

	/**
	 * A constructor which takes a parent and the style to use, the style is ignored by this cell editor.
	 *
	 * @param parent the {@link Composite} to use as a parent
	 * @param style the SWT style to set
	 */
	public MultiStringCellEditor(Composite parent, int style) {
		super(parent, style);
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditor#getValueProperty()
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public IValueProperty getValueProperty() {
		return CellEditorProperties.control().value(WidgetProperties.text(SWT.FocusOut));
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditor#instantiate(org.eclipse.emf.ecore.EStructuralFeature,
	 *      org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public void instantiate(EStructuralFeature eStructuralFeature, ViewModelContext viewModelContext) {
		this.eStructuralFeature = eStructuralFeature;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditor#getFormatedString(java.lang.Object)
	 */
	@Override
	public String getFormatedString(Object value) {
		if (value == null) {
			return ""; //$NON-NLS-1$
		}
		if (!(value instanceof List)) {
			return ""; //$NON-NLS-1$
		}

		@SuppressWarnings("unchecked")
		final List<String> list = (List<String>) value;
		String string = ""; //$NON-NLS-1$
		int i = 0;
		for (final String subString : list) {
			if (i != 0) {
				string = string + getSeparator();
			}
			string = string + subString;
			i++;
		}
		return string;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditor#getColumnWidthWeight()
	 */
	@Override
	public int getColumnWidthWeight() {
		return 50;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditor#getTargetToModelStrategy(org.eclipse.core.databinding.DataBindingContext)
	 */
	@Override
	public UpdateValueStrategy getTargetToModelStrategy(final DataBindingContext databindingContext) {
		return withPreSetValidation(eStructuralFeature, new TargetToModelStrategy());
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditor#getModelToTargetStrategy(org.eclipse.core.databinding.DataBindingContext)
	 */
	@Override
	public UpdateValueStrategy getModelToTargetStrategy(DataBindingContext databindingContext) {
		return new EMFUpdateValueStrategy() {
			@Override
			public Object convert(Object value) {
				return getFormatedString(value);
			}
		};
	}

	/**
	 * returns the {@link Text} of the cell editor.
	 *
	 * @return a {@link Text}
	 */
	protected Text getText() {
		return text;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditor#setEditable(boolean)
	 */
	@Override
	public void setEditable(boolean editable) {
		if (getText() != null) {
			getText().setEditable(editable);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditor#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object value) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditor#getMinWidth()
	 */
	@Override
	public int getMinWidth() {
		return 0;
	}
}
