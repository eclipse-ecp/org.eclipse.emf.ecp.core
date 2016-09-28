/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila- initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.core.util;

import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * Control used for displaying a popup shell.
 *
 * @author Alexandra Buzila
 * @since 1.10
 *
 */
public class PopupWindow {
	private Composite content;
	private final Shell shell;

	/**
	 * Creates a new resizable popup window, with size and location relative to the reference control.
	 *
	 * @param control the reference control
	 * @param maxHeight the maxHeight of the window
	 */
	public PopupWindow(final Control control, int maxHeight) {
		this(control, maxHeight, SWT.RESIZE);
	}

	/**
	 * Creates a new resizable popup window, with size and location relative to the reference control.
	 *
	 * @param control the reference control
	 * @param maxHeight the maximum height of the window
	 * @param style the style of the window
	 */
	public PopupWindow(final Control control, int maxHeight, int style) {
		Assert.isNotNull(control);
		shell = new Shell(control.getShell(), style);
		configurePopupWindow(control, maxHeight, false);
	}

	/**
	 * Creates a new resizable popup window, with size and location relative to the reference control.
	 *
	 * @param control the reference control
	 * @param maxHeight the maximum height of the window
	 * @param style the style of the window
	 * @param stretchUp If the window does not fit on screen vertically, it will stretch up to reach the maxHeight
	 */
	public PopupWindow(final Control control, int maxHeight, int style, boolean stretchUp) {
		Assert.isNotNull(control);
		shell = new Shell(control.getShell(), style);
		configurePopupWindow(control, maxHeight, stretchUp);
	}

	/**
	 * Configures the site, layout and location of the popup.
	 *
	 * @param control the reference control
	 * @param maxHeight the maximum height of the window
	 * @param stretchUp If the window does not fit on screen vertically, it will stretch up to reach the maxHeight
	 */
	protected void configurePopupWindow(final Control control, int maxHeight, boolean stretchUp) {
		final Point location = control.toDisplay(0, 0);
		final Shell parentShell = control.getShell();
		final Rectangle clientArea = control.getShell().getClientArea();
		final Point bottomRight = parentShell.toDisplay(clientArea.width, clientArea.height);
		final int distanceToScreenBottom = bottomRight.y - location.y;
		int verticalMoveUp = 0;
		int shellHeight = maxHeight;
		if (distanceToScreenBottom < maxHeight) {
			if (!stretchUp) {
				shellHeight = Math.min(maxHeight, distanceToScreenBottom);
			} else {
				verticalMoveUp = maxHeight - distanceToScreenBottom;
			}

		}
		shell.setSize(control.getSize().x, shellHeight);
		shell.setLayout(new FillLayout());
		shell.setLocation(location.x - 4, location.y - 4 - verticalMoveUp);// compensate for shell's margins
	}

	/**
	 * Returns the content {@link Composite} of the popup, which clients may use.
	 *
	 * @return the content {@link Composite}.
	 */
	public Composite getContent() {
		if (content == null) {
			content = new Composite(shell, SWT.NONE);
			content.setLayout(new GridLayout());
		}
		return content;
	}

	/**
	 * Opens the popup window.
	 *
	 * @see Shell#open()
	 */
	public void open() {
		shell.open();
	}

	/**
	 * Closes the popup window.
	 *
	 * @see Shell#close()
	 */
	public void close() {
		shell.close();
	}
}
