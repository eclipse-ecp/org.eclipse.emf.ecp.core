/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.ide.editor.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.xml.type.AnyType;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

/**
 * @author Alexandra Buzila
 *
 */
public class UnknownFeaturesDialog extends Dialog {
	/** The unresolved features. */
	private final Map<EObject, AnyType> objects;
	/** The dialog description. */
	private String description;

	private final String title;

	/**
	 * @param parentShell the shell for creating the dialog
	 * @param title the title of the dialog
	 * @param objects the map of unresolved {@link EObject}s and their corresponding {@link AnyType}
	 */
	protected UnknownFeaturesDialog(Shell parentShell, String title, Map<EObject, AnyType> objects) {
		super(parentShell);
		this.objects = objects;
		this.title = title;
		setDescription("The following features could not be migrated:"); //$NON-NLS-1$

	}

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(title);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		final Composite composite = (Composite) super.createDialogArea(parent);

		final Label label = new Label(composite, SWT.WRAP);
		label.setText(getDescription());

		final TableViewer viewer = new TableViewer(parent, SWT.H_SCROLL
			| SWT.V_SCROLL | SWT.SINGLE | SWT.BORDER);
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(false);
		table.setToolTipText(""); //$NON-NLS-1$
		final Listener tableListener = getTableListener(composite, table);
		table.addListener(SWT.Dispose, tableListener);
		table.addListener(SWT.KeyDown, tableListener);
		table.addListener(SWT.MouseMove, tableListener);
		table.addListener(SWT.MouseHover, tableListener);

		viewer.setContentProvider(ArrayContentProvider.getInstance());
		viewer.setInput(getInput());
		createColumns(parent, viewer);

		final GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		viewer.getControl().setLayoutData(gridData);

		viewer.refresh(true);
		composite.layout();
		return composite;
	}

	private Listener getLabelListener(final Table table) {
		return new Listener() {
			@Override
			public void handleEvent(Event event) {
				final Text label = (Text) event.widget;
				final Shell shell = label.getShell();
				switch (event.type) {
				case SWT.MouseDown:
					final Event e = new Event();
					e.item = (TableItem) label.getData("_TABLEITEM"); //$NON-NLS-1$
					table.setSelection(new TableItem[] { (TableItem) e.item });
					table.notifyListeners(SWT.Selection, e);
					shell.dispose();
					table.setFocus();
					break;
				case SWT.MouseExit:
					shell.dispose();
					break;
				default:
					break;
				}
			}
		};
	}

	private Listener getTableListener(final Composite composite, final Table table) {
		return new TableListener(table, composite);
	}

	/**
	 * @return
	 */
	private List<InputElement> getInput() {
		final List<InputElement> input = new ArrayList<InputElement>();
		for (final Iterator<Entry<EObject, AnyType>> itr = getObjects().entrySet().iterator(); itr
			.hasNext();) {
			final Entry<EObject, AnyType> entry = itr.next();
			final AnyType value = entry.getValue();
			final FeatureMap mixed = value.getMixed();
			for (int i = 0; i < mixed.size(); i++) {
				final AnyType object = (AnyType) mixed.getValue(i);
				final FeatureMap anyAttribute2 = object.getAnyAttribute();
				final FeatureMap mixed2 = object.getMixed();
				for (int i1 = 0; i1 < anyAttribute2.size(); i1++) {
					final String vvalue = (String) anyAttribute2.getValue(i1);
					input.add(new InputElement(object.eClass().getName(), vvalue));
					vvalue.toString();
				}
				for (int i1 = 0; i1 < mixed2.size(); i1++) {
					if (AnyType.class.isInstance(mixed2.getValue(i1))) {
						final String value2 = mixed2.getEStructuralFeature(i1).getName();
						final String object2 = mixed2.getValue(i1).toString();
						input.add(new InputElement(object2, value2));
					}
				}
			}
		}
		return input;
	}

	/**
	 * @param parent
	 * @param viewer
	 */
	private void createColumns(Composite parent, TableViewer viewer) {
		final String[] titles = { "Type", "Name", "" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		final int[] bounds = { 200, 200 };

		for (final TableColumn column : viewer.getTable().getColumns()) {
			column.dispose();
		}

		TableViewerColumn col = createTableViewerColumn(viewer, titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				final InputElement inputEl = (InputElement) element;
				return inputEl.getObjectType();
			}
		});

		col = createTableViewerColumn(viewer, titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				final InputElement inputEl = (InputElement) element;
				return inputEl.getObjectName();
			}
		});
	}

	private TableViewerColumn createTableViewerColumn(TableViewer viewer, String title, int bound, final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,
			SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}

	@Override
	public boolean close() {
		// stuff goes here
		// ....
		return super.close();
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the objects
	 */
	public Map<EObject, AnyType> getObjects() {
		return objects;
	}

	/**
	 * @author Jonas
	 *
	 */
	private final class TableListener implements Listener {
		private final Table table;
		private final Composite composite;
		private Shell tip;
		private Text text;

		/**
		 * @param table
		 * @param composite
		 */
		private TableListener(Table table, Composite composite) {
			this.table = table;
			this.composite = composite;
		}

		@Override
		public void handleEvent(Event event) {
			switch (event.type) {
			case SWT.Dispose: {
				break;
			}
			case SWT.KeyDown: {
				break;
			}
			case SWT.MouseMove: {
				if (tip == null) {
					break;
				}
				tip.dispose();
				tip = null;
				text = null;
				break;
			}
			case SWT.MouseHover: {
				final TableItem item = table.getItem(new Point(event.x, event.y));
				if (item != null) {
					if (tip != null && !tip.isDisposed()) {
						tip.dispose();
					}
					tip = new Shell(composite.getShell(), SWT.ON_TOP | SWT.TOOL | SWT.NO_FOCUS);
					tip.setBackground(composite.getDisplay().getSystemColor(SWT.COLOR_INFO_BACKGROUND));
					final FillLayout layout = new FillLayout();
					layout.marginWidth = 2;
					tip.setLayout(layout);

					text = new Text(tip, SWT.MULTI);
					text.setForeground(composite.getDisplay().getSystemColor(SWT.COLOR_INFO_FOREGROUND));
					text.setBackground(composite.getDisplay().getSystemColor(SWT.COLOR_INFO_BACKGROUND));
					text.setData("_TABLEITEM", item); //$NON-NLS-1$
					text.setText(item.getText());
					text.setEditable(false);

					text.addListener(SWT.MouseExit, getLabelListener(table));
					final Point size = tip.computeSize(SWT.DEFAULT, SWT.DEFAULT);
					final Rectangle rect = item.getBounds(0);
					final Point pt = table.toDisplay(rect.x, rect.y);
					tip.setBounds(pt.x, pt.y, size.x, size.y);
					tip.setVisible(true);
				}
				break;
			}
			default:
				break;
			}
		}
	}

	/** The dialog's TableViewer's input elements. */
	private class InputElement {
		private final String objectType;

		/**
		 * @return the objectType
		 */
		public String getObjectType() {
			return objectType;
		}

		private final String objectName;

		/**
		 * @return the objectName
		 */
		public String getObjectName() {
			return objectName;
		}

		InputElement(String type, String name) {
			objectType = type;
			objectName = name;
		}

	}

}
