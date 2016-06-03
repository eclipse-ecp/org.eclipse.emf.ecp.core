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

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

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

	private static Set<Composite> requestedLayouts = Collections.synchronizedSet(new LinkedHashSet<Composite>());
	private static Thread thread;

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
				int oldBarHeight = 0;
				int barHeight = 0;
				for (final ExpandItem item : bar.getItems()) {
					final Control itemControl = item.getControl();
					if (itemControl != null) {
						oldBarHeight += item.getHeight();
						final int height = itemControl.computeSize(bar.getSize().x, SWT.DEFAULT, true).y;
						barHeight += height;
						item.setHeight(height);
					}
				}
				if (bar.getItemCount() > 0) {
					/* only update layout data when there is at least one item */
					updateLayoutData(bar.getLayoutData(), oldBarHeight, barHeight);
				}
			}

			if (parent.getParent() == null) {
				layoutDelayed(parent);
			}

			if (Shell.class.isInstance(parent)) {
				layoutDelayed(parent);
			}

			parent = parent.getParent();
		}

	}

	/**
	 * <p>
	 * This method will collect layoutrequest that happen in the same 200ms. When there are multiple layoutrequest for
	 * the same composite in this time frame, the composite will only be layouted once.
	 * </p>
	 * <p>
	 * This will help to improve performance as layout request are usually expensive. Also it might be quite common that
	 * e.g. multiple hide rules are triggered by the same condition.
	 * </p>
	 *
	 * @param parent the composite to layout
	 */
	private static synchronized void layoutDelayed(Composite parent) {
		requestedLayouts.add(parent);
		if (thread != null) {
			return;
		}
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(200);
				} catch (final InterruptedException ex) {
					/* silent */
				}
				final Set<Composite> toLayout = requestedLayouts;
				requestedLayouts = Collections.synchronizedSet(new LinkedHashSet<Composite>());
				thread = null;
				for (final Composite composite : toLayout) {
					if (composite.isDisposed()) {
						continue;
					}
					composite.getDisplay().asyncExec(new Runnable() {
						@Override
						public void run() {
							composite.layout(true, true);
						}
					});
				}
			}
		});
		thread.start();
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