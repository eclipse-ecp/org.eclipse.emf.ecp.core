/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
