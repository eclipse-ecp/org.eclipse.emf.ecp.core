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
package org.eclipse.emfforms.spi.swt.core.layout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Shell;

/**
 * Util class for common SWT-related layout tasks.
 *
 * @author jfaltermeier
 * @since 1.8
 *
 */
public final class EMFFormsSWTLayoutUtil {

	private EMFFormsSWTLayoutUtil() {
	}

	/**
	 * This methods helps to update the size of a parent composite when the size of a child has changed. This is needed
	 * for {@link ScrolledComposite} and {@link ExpandBar}.
	 *
	 * @param control the control with a changed size.
	 */
	public static void adjustParentSize(Control control) {
		if (control.isDisposed()) {
			return;
		}
		Composite parent = control.getParent();
		while (parent != null) {

			if (ScrolledComposite.class.isInstance(parent)) {
				final ScrolledComposite scrolledComposite = ScrolledComposite.class.cast(parent);
				final Control content = scrolledComposite.getContent();
				if (content == null) {
					return;
				}
				final Point point = content.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				scrolledComposite.setMinSize(point);
			} else if (ExpandBar.class.isInstance(parent)) {
				final ExpandBar bar = ExpandBar.class.cast(parent);
				final ExpandItem item = bar.getItem(0);
				final Control itemControl = item.getControl();
				if (itemControl != null) {
					final int oldHeight = item.getHeight();
					final int height = itemControl.computeSize(bar.getSize().x, SWT.DEFAULT, true).y;
					updateLayoutData(bar.getLayoutData(), oldHeight, height);
					item.setHeight(height);
				}
			}

			if (parent.getParent() == null) {
				parent.layout(true, true);
			}

			if (Shell.class.isInstance(parent)) {
				parent.layout(true, true);
			}

			parent = parent.getParent();
		}

	}

	private static void updateLayoutData(final Object layoutData, int oldHeight, int newHeight) {
		if (layoutData instanceof GridData) {
			final GridData gridData = (GridData) layoutData;
			if (gridData.heightHint == -1) {
				return;
			}
			final int heightHint = gridData.heightHint - oldHeight + newHeight;
			gridData.heightHint = heightHint;

		}
	}

}