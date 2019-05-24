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
package org.eclipse.emf.ecp.view.spi.swt.layout;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * <p>
 * A specialization of the {@link StackLayout} that behaves more like the layout
 * of the {@code PageBook} control that implements paged views in Eclipse.
 * Most importantly, it does not constrain all of its "page" controls to have
 * the size of the largest among them.
 * </p>
 * <p>
 * <strong>Note</strong> that as this class is a kind of {@link StackLayout}, it
 * exposes a public {@link StackLayout#topControl} field. Clients should not
 * access or modify this field directly but instead use the provided
 * {@link #getCurrentPage()} and {@link #showPage(Control)} APIs.
 * </p>
 *
 * @since 1.22
 */
public class PageLayout extends StackLayout {

	private final Composite composite;
	private final Set<Control> pages = new LinkedHashSet<Control>();
	private final DisposeListener disposeListener = this::childDisposed;

	/**
	 * Initializes me with the composite for which I provide layout.
	 *
	 * @param composite my composite
	 */
	public PageLayout(Composite composite) {
		super();

		this.composite = composite;
		composite.setLayout(this);
	}

	/**
	 * Show the given {@code control} as the current page and re-compute the
	 * composite's layout.
	 *
	 * @param control the control to show (must not be {@code null})
	 *
	 * @throws NullPointerException if the {@code control} is {@code null}
	 * @throws IllegalArgumentException if the {@code control} is not a child of my composite
	 */
	public void showPage(Control control) {
		if (control.getParent() != composite) {
			throw new IllegalArgumentException("control is not in the composite"); //$NON-NLS-1$
		}

		if (topControl != control) {
			final Control[] children = composite.getChildren();
			for (int i = 0; i < children.length; i++) {
				children[i].setVisible(children[i] == control);
			}

			if (pages.add(control)) {
				control.addDisposeListener(disposeListener);
			}

			topControl = control;
			composite.layout();
		}
	}

	/**
	 * Query what is the current page that I am showing.
	 *
	 * @return my current page, or {@code null} if none is current
	 */
	public Control getCurrentPage() {
		return topControl;
	}

	@Override
	protected Point computeSize(Composite composite, int wHint, int hHint, boolean flushCache) {
		Control currentPage = topControl;
		if (currentPage == null) {
			// Just take the first control
			currentPage = firstValidPage();
		}

		int width = 0;
		int height = 0;

		if (currentPage != null) {
			final Point size = currentPage.computeSize(wHint, hHint, flushCache);
			width = size.x;
			height = size.y;
		}

		width = width + 2 * marginWidth;
		height = height + 2 * marginHeight;

		if (wHint != SWT.DEFAULT) {
			width = wHint;
		}
		if (hHint != SWT.DEFAULT) {
			height = hHint;
		}

		return new Point(width, height);
	}

	private Control firstValidPage() {
		final Control[] children = composite.getChildren();
		for (int i = 0; i < children.length; i++) {
			final Control next = children[i];
			// We use this when looking for a control that is not the top
			// control, so filter that
			if (!next.isDisposed() && next != topControl) {
				return next;
			}
		}
		return null;
	}

	private void childDisposed(DisposeEvent event) {
		if (pages.remove(event.widget) && topControl == event.widget && !composite.isDisposed()) {
			// Current page was disposed. Oops
			final Control toShow = firstValidPage();
			if (toShow != null) {
				showPage(toShow);
			}
		}
	}

}
