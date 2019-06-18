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
package org.eclipse.emf.ecp.view.spi.swt.selection;

import org.eclipse.jface.viewers.ISelectionProvider;

/**
 * A selection provider for master-detail views in which both the master
 * and the detail parts may provide a selection. The provider's selection
 * is the selection of either the master or the detail part, whichever is
 * currently active (usually meaning that it last had the input focus).
 * The detail provider can change as the master-detail UI creates/activates/destroys
 * different detail views, and there needs not always be a detail. Moreover,
 * the detail is free to provide different kind of selection (e.g., text vs. structured)
 * to the master.
 *
 * @since 1.21
 */
public interface IMasterDetailSelectionProvider extends ISelectionProvider {

	/**
	 * Set the active detail selection provider, or {@code null} if either
	 * there is no detail or it is not active (in which case the master
	 * selection provider is deemed active).
	 *
	 * @param selectionProvider the detail selection provider to activate,
	 *            or {@code null} to active the implicit master selection provider
	 */
	void setDetailSelectionProvider(ISelectionProvider selectionProvider);
}
