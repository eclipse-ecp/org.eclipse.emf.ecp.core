/*******************************************************************************
 * Copyright (c) 2019 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.edit.spi.swt.table;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.CellLabelProvider;

/**
 * Optional interface (either implemented or provided as an {@linkplain IAdaptable adapter})
 * for a cell that supports filtering by its text content. This allows for complex cell
 * renderers, for example, to avoid costly updates via the
 * {@link CellLabelProvider#update(org.eclipse.jface.viewers.ViewerCell)} API that do more
 * than just render text.
 *
 * @since 1.21
 */
public interface ECPFilterableCell {

	/**
	 * Query the text to filter on.
	 *
	 * @param object the object to be filtered
	 * @return the text to filter on, or an empty string if none
	 */
	String getFilterableText(Object object);

}
