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

import org.eclipse.emfforms.common.Optional;
import org.eclipse.jface.viewers.AbstractTableViewer;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ViewerComparator;

/**
 * The TableViewerSWTCustomization is used by the {@link TableViewerComposite} to create the UI with its behaviour.
 *
 * @author Alexandra Buzila
 * @author Johannes Faltermeier
 *
 * @param <T> the TableViewer implementation to use
 */
public interface TableViewerSWTCustomization<T extends AbstractTableViewer>
	extends TableViewerCompositeBuilder, TableViewerCreator<T>, ButtonBarBuilder,
	DNDProvider {

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
	 * Returns the table configuration which will be used to configure the table instance.
	 * May be null as long as the configuration is incomplete.
	 *
	 * @return the {@link TableConfiguration}
	 */
	TableConfiguration getTableConfiguration();

	/**
	 * Returns the column configurations which will be used to configure columns.
	 *
	 * @return the {@link ColumnConfiguration}
	 */
	List<ColumnConfiguration> getColumnConfigurations();

}
