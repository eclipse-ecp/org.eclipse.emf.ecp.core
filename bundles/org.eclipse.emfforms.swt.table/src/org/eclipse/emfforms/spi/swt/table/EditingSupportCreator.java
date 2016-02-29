/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.table;

import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;

/**
 * Interface for creating the {@link EditingSupport}.
 * 
 * @author Johannes Faltermeier
 *
 */
public interface EditingSupportCreator {

	/**
	 * Create the editing support which will be set on a column of the given {@link TableViewer}.
	 * 
	 * @param columnViewer the viewer
	 * @return the {@link EditingSupport}
	 */
	EditingSupport createEditingSupport(TableViewer columnViewer);

}
