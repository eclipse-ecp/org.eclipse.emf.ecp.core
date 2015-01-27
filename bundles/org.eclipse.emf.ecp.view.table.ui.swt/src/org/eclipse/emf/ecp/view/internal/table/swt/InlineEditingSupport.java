/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.table.swt;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ObservableValueEditingSupport;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * Editing support that uses JFace Data Binding to control the editing
 * lifecycle. The standard EditingSupport get/setValue(...) lifecycle is not
 * used.
 *
 * @since 3.3
 */
public class InlineEditingSupport extends
	ObservableValueEditingSupport {
	private final CellEditor cellEditor;
	private final IValueProperty valueProperty;

	public InlineEditingSupport(ColumnViewer viewer, DataBindingContext dbc, IValueProperty valueProperty) {

		super(viewer, dbc);
		this.valueProperty = valueProperty;
		cellEditor = new TextCellEditor((Composite) viewer.getControl());
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return cellEditor;
	}

	@Override
	protected IObservableValue doCreateCellEditorObservable(
		CellEditor cellEditor) {
		return SWTObservables.observeText(cellEditor.getControl(), SWT.Modify);
	}

	@Override
	protected IObservableValue doCreateElementObservable(Object element, ViewerCell cell) {
		return valueProperty.observe(element);
	}

}
