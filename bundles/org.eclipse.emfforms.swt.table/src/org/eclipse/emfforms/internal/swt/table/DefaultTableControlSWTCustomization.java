/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.swt.table;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.spi.swt.table.ButtonBarBuilder;
import org.eclipse.emfforms.spi.swt.table.DNDProvider;
import org.eclipse.emfforms.spi.swt.table.DefaultButtonBarBuilder;
import org.eclipse.emfforms.spi.swt.table.NewElementCreator;
import org.eclipse.emfforms.spi.swt.table.TableViewerCompositeBuilder;
import org.eclipse.emfforms.spi.swt.table.TableViewerCreator;
import org.eclipse.emfforms.spi.swt.table.TableViewerSWTCustomization;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.viewers.AbstractTableViewer;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

/**
 * The default implementation of the {@link TableViewerSWTCustomization}.
 *
 * @author Alexandra Buzila
 * @author Johannes Faltermeier
 *
 */
public class DefaultTableControlSWTCustomization implements TableViewerSWTCustomization {

	private final List<ColumnDescription> columns = new ArrayList<TableViewerSWTCustomization.ColumnDescription>();

	private TableViewerCompositeBuilder tableViewerCompositeBuilder = new DefaultTableViewerCompositeBuilder();

	private TableViewerCreator tableViewerCreator = new TableViewerCreator() {

		@Override
		public TableViewer createTableViewer(Composite parent) {
			final TableViewer tableViewer = new TableViewer(parent,
				SWT.MULTI | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
			tableViewer.getTable().setHeaderVisible(true);
			tableViewer.getTable().setLinesVisible(true);
			return tableViewer;
		}
	};

	private DNDProvider dndProvider = new NoOpDNDProvider();

	private ViewerComparator viewerComparator;

	private IContentProvider contentProvider = new ObservableListContentProvider();

	private ButtonBarBuilder buttonBarBuilder = new DefaultButtonBarBuilder();

	@Override
	public void createCompositeLayout(Composite parent) {
		tableViewerCompositeBuilder.createCompositeLayout(parent);
	}

	@Override
	public Optional<Label> getTitleLabel() {
		return tableViewerCompositeBuilder.getTitleLabel();
	}

	@Override
	public Optional<List<Control>> getValidationControls() {
		return tableViewerCompositeBuilder.getValidationControls();
	}

	@Override
	public Optional<Composite> getButtonComposite() {
		return tableViewerCompositeBuilder.getButtonComposite();
	}

	@Override
	public Composite getViewerComposite() {
		return tableViewerCompositeBuilder.getViewerComposite();
	}

	@Override
	public AbstractTableViewer createTableViewer(Composite parent) {
		return tableViewerCreator.createTableViewer(parent);
	}

	@Override
	public Optional<ViewerComparator> getComparator() {
		return Optional.ofNullable(viewerComparator);
	}

	@Override
	public IContentProvider createContentProvider() {
		return contentProvider;
	}

	@Override
	public void fillButtonComposite(Composite buttonComposite, AbstractTableViewer viewer) {
		buttonBarBuilder.fillButtonComposite(buttonComposite, viewer);
	}

	@Override
	public Object createNewElement(Button button) {
		return buttonBarBuilder.createNewElement(button);
	}

	@Override
	public List<ColumnDescription> getColumns() {
		return columns;
	}

	/**
	 * Allows the exchange the default {@link TableViewerCompositeBuilder}.
	 *
	 * @param builder the {@link TableViewerCompositeBuilder}
	 */
	public void setTableViewerCompositeBuilder(TableViewerCompositeBuilder builder) {
		tableViewerCompositeBuilder = builder;
	}

	/**
	 * Allows the exchange the default {@link TableViewerCreator}.
	 *
	 * @param creator the {@link TableViewerCreator}
	 */
	public void setTableViewerCreator(TableViewerCreator creator) {
		tableViewerCreator = creator;
	}

	/**
	 * Allows the exchange the default {@link ViewerComparator}.
	 *
	 * @param comparator the {@link ViewerComparator}
	 */
	public void setViewerComparator(ViewerComparator comparator) {
		viewerComparator = comparator;
	}

	/**
	 * Allows the exchange the default {@link IContentProvider}.
	 *
	 * @param provider the {@link IContentProvider}
	 */
	public void setContentProvider(IContentProvider provider) {
		contentProvider = provider;

	}

	/**
	 * Allows the exchange the default {@link ButtonBarBuilder}.
	 *
	 * @param builder the {@link ButtonBarBuilder}
	 */
	public void setButtonBarBuilder(ButtonBarBuilder builder) {
		buttonBarBuilder = builder;
	}

	/**
	 * Adds a column to the table.
	 *
	 * @param columnDescription the
	 *            {@link org.eclipse.emfforms.spi.swt.table.TableViewerSWTCustomization.ColumnDescription
	 *            ColumnDescription}
	 */
	public void addColumn(ColumnDescription columnDescription) {
		columns.add(columnDescription);
	}

	/**
	 * Allows the exchange the default {@link NewElementCreator}. This may only be used if the
	 * {@link #setButtonBarBuilder(ButtonBarBuilder) button bar builder} was not exchanged.
	 *
	 * @param creator the {@link NewElementCreator}
	 */
	public void setNewElementCreator(NewElementCreator<Object, Button> creator) {
		if (!DefaultButtonBarBuilder.class.isInstance(buttonBarBuilder)) {
			throw new IllegalArgumentException("Can only be used with the DefaultButtonBarBuilder"); //$NON-NLS-1$
		}
		DefaultButtonBarBuilder.class.cast(buttonBarBuilder).setCreator(creator);
	}

	/**
	 * Allows the exchange the default {@link DNDProvider}.
	 *
	 * @param provider the {@link DNDProvider}
	 */
	public void setDND(DNDProvider provider) {
		dndProvider = provider;
	}

	@Override
	public boolean hasDND() {
		return dndProvider.hasDND();
	}

	@Override
	public int getDragOperations() {
		return dndProvider.getDragOperations();
	}

	@Override
	public Transfer[] getDragTransferTypes() {
		return dndProvider.getDragTransferTypes();
	}

	@Override
	public DragSourceListener getDragListener(AbstractTableViewer tableViewer) {
		return dndProvider.getDragListener(tableViewer);
	}

	@Override
	public int getDropOperations() {
		return dndProvider.getDropOperations();
	}

	@Override
	public Transfer[] getDropTransferTypes() {
		return dndProvider.getDropTransferTypes();
	}

	@Override
	public DropTargetListener getDropListener(AbstractTableViewer tableViewer) {
		return dndProvider.getDropListener(tableViewer);
	}

	/**
	 * {@link DNDProvider} for NO D&D support.
	 *
	 * @author Johannes Faltermeier
	 *
	 */
	private final class NoOpDNDProvider implements DNDProvider {
		@Override
		public boolean hasDND() {
			return false;
		}

		@Override
		public Transfer[] getDropTransferTypes() {
			throw new UnsupportedOperationException();
		}

		@Override
		public int getDropOperations() {
			throw new UnsupportedOperationException();
		}

		@Override
		public DropTargetListener getDropListener(AbstractTableViewer tableViewer) {
			throw new UnsupportedOperationException();
		}

		@Override
		public Transfer[] getDragTransferTypes() {
			throw new UnsupportedOperationException();
		}

		@Override
		public int getDragOperations() {
			throw new UnsupportedOperationException();
		}

		@Override
		public DragSourceListener getDragListener(AbstractTableViewer tableViewer) {
			throw new UnsupportedOperationException();
		}
	}

}
