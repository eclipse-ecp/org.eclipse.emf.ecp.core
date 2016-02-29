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
package org.eclipse.emfforms.internal.swt.table.util;

import org.eclipse.emfforms.spi.swt.table.CellLabelProviderFactory;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.TableViewer;

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
	public CellLabelProvider createCellLabelProvider(TableViewer table) {
		return provider;
	}

}
