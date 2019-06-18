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

import java.util.function.Supplier;

import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.widgets.Control;

/**
 * A focus listener that switches the master and detail inputs of
 * a master-detail selection provider.
 *
 * @since 1.21
 */
public final class MasterDetailFocusAdapter implements FocusListener {

	private final IMasterDetailSelectionProvider selectionProvider;
	private final Supplier<? extends Control> detailSupplier;

	/**
	 * Initializes me with the selection provider and a supplier to query for what
	 * is the detail control to check for focus.
	 *
	 * @param selectionProvider the selection provider
	 * @param detailSupplier the detail control supplier
	 */
	public MasterDetailFocusAdapter(IMasterDetailSelectionProvider selectionProvider,
		Supplier<? extends Control> detailSupplier) {

		super();

		this.selectionProvider = selectionProvider;
		this.detailSupplier = detailSupplier;
	}

	@Override
	public void focusGained(FocusEvent e) {
		selectionProvider.setDetailSelectionProvider(null);
	}

	@Override
	public void focusLost(FocusEvent e) {
		// We cannot yet know which control will gain focus
		e.display.asyncExec(() -> {
			// Does the detail now have focus?
			final Control detail = detailSupplier.get();
			if (detail != null && !detail.isDisposed() && isAncestorFocus(detail)) {

				// TODO: Some kind of API for getting a selection provider for the detail control
				selectionProvider.setDetailSelectionProvider(NullSelectionProvider.INSTANCE);
			}
		});
	}

	private boolean isAncestorFocus(Control control) {
		boolean result = false;

		for (Control focus = control.getDisplay().getFocusControl(); !result
			&& focus != null; focus = focus.getParent()) {

			result = focus == control;
		}

		return result;
	}

}
