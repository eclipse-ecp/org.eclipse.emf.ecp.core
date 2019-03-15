/*******************************************************************************
 * Copyright (c) 2019 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.swt.selection;

import org.eclipse.jface.viewers.IPostSelectionProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;

/**
 * A provider of no selection, ever.
 *
 * @since 1.21
 */
public final class NullSelectionProvider implements IPostSelectionProvider {

	private static final ISelection EMPTY = new ISelection() {
		@Override
		public boolean isEmpty() {
			return true;
		}
	};

	/** There needs only be one instance of this class, and this is it. */
	public static final NullSelectionProvider INSTANCE = new NullSelectionProvider();

	/**
	 * Initializes me.
	 */
	private NullSelectionProvider() {
		super();
	}

	@Override
	public ISelection getSelection() {
		return EMPTY;
	}

	@Override
	public void setSelection(ISelection selection) {
		// Selection never changes
	}

	@Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		// Selection never changes
	}

	@Override
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		// Selection never changes
	}

	@Override
	public void addPostSelectionChangedListener(ISelectionChangedListener listener) {
		// Selection never changes
	}

	@Override
	public void removePostSelectionChangedListener(ISelectionChangedListener listener) {
		// Selection never changes
	}

}
