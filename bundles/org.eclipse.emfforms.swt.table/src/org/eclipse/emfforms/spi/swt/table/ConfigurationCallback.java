/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Mat Hansen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.table;

import org.eclipse.jface.viewers.AbstractTableViewer;
import org.eclipse.jface.viewers.ViewerColumn;

/**
 * Interface for ConfigurationCallbacks.
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 *
 * @param <V> the TableViewer implementation to use
 * @param <C> the {@link ViewerColumn} implementation to use
 */
public interface ConfigurationCallback<V extends AbstractTableViewer, C extends ViewerColumn> {

	/**
	 * Setup a custom configuration for the given viewer.
	 *
	 * @param config the {@link ColumnConfiguration}
	 * @param tableViewer the table viewer
	 * @param viewerColumn the viewer column
	 */
	void configure(ColumnConfiguration config, V tableViewer, C viewerColumn);

}