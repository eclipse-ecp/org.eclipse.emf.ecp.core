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

import org.eclipse.jface.viewers.AbstractTableViewer;
import org.eclipse.jface.viewers.CellLabelProvider;

/**
 * This factory is used to create {@link CellLabelProvider}s.
 *
 * @author Johannes Faltermeier
 *
 */
public interface CellLabelProviderFactory {

	/**
	 * Creates the cell label provider.
	 *
	 * @param table the table viewer
	 * @return the provider
	 */
	CellLabelProvider createCellLabelProvider(AbstractTableViewer table);

}
