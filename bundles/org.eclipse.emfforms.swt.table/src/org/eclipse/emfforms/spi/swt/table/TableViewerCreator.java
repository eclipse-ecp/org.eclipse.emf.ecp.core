/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * johannes - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.table;

import org.eclipse.jface.viewers.AbstractTableViewer;
import org.eclipse.swt.widgets.Composite;

/**
 * The {@link TableViewerCreator} is used to create a {@link AbstractTableViewer} on a parent composite.
 *
 * @param <T> the type of the viewer. This must extend an {@link AbstractTableViewer}
 *
 * @author Johannes Faltermeier
 *
 */
public interface TableViewerCreator<T extends AbstractTableViewer> {

	/**
	 * Creates the {@link AbstractTableViewer}.
	 *
	 * @param parent the parent {@link Composite}
	 * @return the viewer
	 */
	T createTableViewer(Composite parent);

}