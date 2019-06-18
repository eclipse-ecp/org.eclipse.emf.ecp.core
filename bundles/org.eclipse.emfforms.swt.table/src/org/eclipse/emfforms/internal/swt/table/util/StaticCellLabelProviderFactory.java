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
package org.eclipse.emfforms.internal.swt.table.util;

import org.eclipse.emfforms.spi.swt.table.CellLabelProviderFactory;
import org.eclipse.jface.viewers.AbstractTableViewer;
import org.eclipse.jface.viewers.CellLabelProvider;

/**
 * Returns a previously created provider.
 *
 * @author Johannes Faltermeier
 *
 */
public class StaticCellLabelProviderFactory implements CellLabelProviderFactory {

	private final CellLabelProvider provider;

	/**
	 * Constructs a new {@link StaticCellLabelProviderFactory} with the given provider.
	 *
	 * @param provider the provider
	 */
	public StaticCellLabelProviderFactory(CellLabelProvider provider) {
		this.provider = provider;
	}

	@Override
	public CellLabelProvider createCellLabelProvider(AbstractTableViewer table) {
		return provider;
	}

}
