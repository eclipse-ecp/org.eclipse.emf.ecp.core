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
package org.eclipse.emfforms.spi.swt.table;

import java.util.List;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.graphics.Image;

/**
 * The TableViewerSWTCustomization is used by the {@link TableViewerComposite} to create the UI with its behaviour.
 *
 * @author Alexandra Buzila
 * @author Johannes Faltermeier
 *
 */
public interface TableViewerSWTCustomization
	extends TableViewerCompositeBuilder, TableViewerCreator, ButtonBarBuilder, DNDProvider {

	/**
	 * Returns the comparator to use.
	 *
	 * @return the {@link ViewerComparator} which will be set on the viewer, if present
	 */
	Optional<ViewerComparator> getComparator();

	/**
	 * Returns the content provider to use.
	 *
	 * @return the {@link IContentProvider} which will be set on the viewer
	 */
	IContentProvider createContentProvider();

	/**
	 * Returns the column descriptions which will be used to create actual columns.
	 *
	 * @return the {@link ColumnDescription ColumnDescriptions}
	 */
	List<ColumnDescription> getColumns();

	/**
	 * A ColumnDescription is used to describe how a viewer column shall be created.
	 *
	 * @author Johannes Faltermeier
	 *
	 */
	interface ColumnDescription {

		/**
		 * <code>true</code> if resizeable, <code>false</code> otherwise.
		 *
		 * @return whether the column is resizeable
		 */
		boolean isResizeable();

		/**
		 * <code>true</code> if moveable, <code>false</code> otherwise.
		 *
		 * @return whether the column is moveable
		 */
		boolean isMoveable();

		/**
		 * The SWT style bits which will be used to create the column.
		 *
		 * @return the SWT style bits for the column
		 */
		int getStyleBits();

		/**
		 * The weight of the column.
		 *
		 * @return the weight of the column
		 */
		int getWeight();

		/**
		 * The minimal width of the column.
		 *
		 * @return the min width of the column in pixels
		 */
		int getMinWidth();

		/**
		 * The header text for the column.
		 *
		 * @return the column header text
		 */
		IObservableValue getColumnText();

		/**
		 * The column header tooltip text.
		 *
		 * @return the column header tooltip
		 */
		IObservableValue getColumnTooltip();

		/**
		 * The cell label provider which will be set on the column.
		 *
		 * @return the label provider
		 */
		CellLabelProvider createLabelProvider();

		/**
		 * Called to setup the {@link EditingSupport} for the viewer.
		 * 
		 * @param columnViewer the {@link TableViewer}
		 * @return the editing support for the column, if present
		 */
		Optional<EditingSupport> createEditingSupport(TableViewer columnViewer);

		/**
		 * The image of the column.
		 * 
		 * @return the column image, if present
		 */
		Optional<Image> getColumnImage();

	}

}
