/********************************************************************************
 * Copyright (c) 2011 Eike Stepper (Berlin, Germany) and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eike Stepper - initial API and implementation
 ********************************************************************************/
package org.eclipse.emf.ecp.internal.ui.composites;

import org.eclipse.emf.ecp.core.util.ECPProperties;
import org.eclipse.emf.ecp.internal.ui.dialogs.PropertyDialog;
import org.eclipse.emf.ecp.internal.ui.messages.Messages;
import org.eclipse.emf.ecp.internal.ui.model.PropertiesContentProvider;
import org.eclipse.emf.ecp.internal.ui.model.PropertiesLabelProvider;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Eike Stepper
 */
public class PropertiesComposite extends Composite {
	private final TableViewer tableViewer;

	private final boolean editable;

	public PropertiesComposite(Composite parent, boolean editable, final ECPProperties properties) {
		super(parent, SWT.NONE);
		this.editable = editable;
		setLayout(new GridLayout(1, false));

		int style = SWT.BORDER | SWT.FULL_SELECTION;
		if (!editable) {
			style |= SWT.READ_ONLY;
		}

		tableViewer = new TableViewer(this, style);
		final Table table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn = tableViewerColumn.getColumn();
		tblclmnNewColumn.setWidth(122);
		tblclmnNewColumn.setText(Messages.PropertiesComposite_TableColumnName_Key);

		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn_1 = tableViewerColumn_1.getColumn();
		tblclmnNewColumn_1.setWidth(314);
		tblclmnNewColumn_1.setText(Messages.PropertiesComposite_TableColumnName_Value);

		tableViewer.setLabelProvider(new PropertiesLabelProvider());
		tableViewer.setContentProvider(new PropertiesContentProvider());
		tableViewer.setSorter(new ViewerSorter());
		tableViewer.setInput(properties);

		if (editable) {
			Composite buttonBar = new Composite(this, SWT.NONE);
			GridLayout gl_buttonBar = new GridLayout(3, false);
			gl_buttonBar.marginWidth = 0;
			gl_buttonBar.marginHeight = 0;
			buttonBar.setLayout(gl_buttonBar);

			Button addButton = new Button(buttonBar, SWT.NONE);
			addButton.setText(Messages.PropertiesComposite_AddProperty);
			addButton.addSelectionListener(new SelectionListener() {
				public void widgetSelected(SelectionEvent e) {
					PropertyDialog dialog = new PropertyDialog(table.getShell());
					if (dialog.open() == PropertyDialog.OK) {
						String key = dialog.getKey();
						String value = dialog.getValue();
						properties.addProperty(key, value);
					}
				}

				public void widgetDefaultSelected(SelectionEvent e) {
					widgetSelected(e);
				}
			});

			Button editButton = new Button(buttonBar, SWT.NONE);
			editButton.setText(Messages.PropertiesComposite_EditProperty);
			editButton.addSelectionListener(new SelectionListener() {
				public void widgetSelected(SelectionEvent e) {
					IStructuredSelection selection = (IStructuredSelection) tableViewer.getSelection();

					@SuppressWarnings("unchecked")
					Map.Entry<String, String> property = (Entry<String, String>) selection.getFirstElement();

					PropertyDialog dialog = new PropertyDialog(table.getShell(), false, property.getKey(), property
						.getValue());
					if (dialog.open() == PropertyDialog.OK) {
						properties.addProperty(dialog.getKey(), dialog.getValue());
					}
				}

				public void widgetDefaultSelected(SelectionEvent e) {
					// Do nothing
				}
			});

			Button removeButton = new Button(buttonBar, SWT.NONE);
			removeButton.setText(Messages.PropertiesComposite_RemoveProperty);
			removeButton.addSelectionListener(new SelectionListener() {
				public void widgetSelected(SelectionEvent e) {
					IStructuredSelection selection = (IStructuredSelection) tableViewer.getSelection();

					@SuppressWarnings("unchecked")
					Map.Entry<String, String> property = (Entry<String, String>) selection.getFirstElement();

					properties.removeProperty(property.getKey());
				}

				public void widgetDefaultSelected(SelectionEvent e) {
					// Do nothing
				}
			});
		}
	}

	public final TableViewer getTableViewer() {
		return tableViewer;
	}

	public final boolean isEditable() {
		return editable;
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
